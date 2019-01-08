/**
 * 
 */
package com.experian.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.experian.dto.ExperianFileRequest;
import com.experian.dto.FileUploadResponseList;
import com.experian.dto.aiml.request.AimlQualityScoreRequest;
import com.experian.dto.aiml.request.AimlTaxationRequest;
import com.experian.dto.aiml.response.AimlFileFinalResponse;
import com.experian.dto.aiml.response.AimlFileResponse;
import com.experian.dto.aiml.response.AimlQualityScoreResponse;
import com.experian.dto.aiml.response.AimlTaxationResponse;
import com.experian.dto.neo4j.RequirementStatement;
import com.experian.dto.neo4j.RequirementSuggestions;
import com.experian.dto.neo4j.request.TaxationBasedSuggestionRequest;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.WordCategoryResponse;
import com.experian.exception.FileStorageException;
import com.experian.exception.MyFileNotFoundException;
import com.experian.mapper.ExperianAIMLMapper;
import com.experian.mapper.ExperianNeo4JMapper;
import com.experian.properties.FileStorageProperties;

/**
 * @author manchanda.a
 *
 */
@Service
public class FileStorageService {
	private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
	
	private final Path fileStorageLocation;
	
	@Autowired
	private ExternalService externalService;
	
	@Autowired
	private ExperianAIMLMapper aimlMapper;
	
	@Autowired
	private ServiceHelper helper;
	
	@Autowired
	private ExperianNeo4JMapper neo4jMapper;
	
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    /**
     * This method will temporarly save file to local folder.
     * @param file
     * @return
     */
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        logger.debug("File name is "+fileName);
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
        	logger.error("Caught error while storing file"+ex);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    /**
     * This method will read file from path and then save data into database.
     * @param fileName
     */
    public FileUploadResponseList readFile(String fileName) {
    	try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
            	// it will read data from file and convert it to list of object.
            	ExperianFileRequest experianFileRequest = readFileData(resource);
            	
            	// it will call neo4j service to get master data.
            	WordCategoryResponse wordCategoryResponse = externalService.getWordCategoryFromNeo4j();
            	logger.debug("Word category response ", wordCategoryResponse.getWordCategory().toString());
            	
            	// it will call AI/ML Service to get taxation .
            	AimlTaxationRequest aimlTaxationRequest = aimlMapper.mapBatchRequestToAIMLTaxationRequest(wordCategoryResponse, experianFileRequest);
            	AimlTaxationResponse aimlTaxationResponse = externalService.processFileToAimlToGetTaxation(aimlTaxationRequest);
            	logger.debug("Aiml taxation response ", aimlTaxationResponse.getTaxonomyClassification().toString());
            	
            	// it will call AI/ML Service to get quality score. 
            	AimlQualityScoreRequest aimlQualityScoreRequest = aimlMapper.mapBatchRequestToAimlQualityScoreRequest(experianFileRequest);
            	AimlQualityScoreResponse aimlQualityScoreResponse = externalService.processFileToAimlToGetQualityScore(aimlQualityScoreRequest);
            	
            	// it will convert file request , quality score and taxation to get file final response.
            	AimlFileFinalResponse aimlFileFinalResponse = aimlMapper.mapQualityAndTaxationToGetFinalResponse(experianFileRequest, aimlTaxationResponse, aimlQualityScoreResponse);
            	System.out.println("Aiml final response "+aimlFileFinalResponse.getResponse().size());
            	// Map quality score response and taxation response to create single object.
            	// it will call neo4j service to get suggestions.
            	TaxationBasedSuggestionRequest taxationBasedSuggestionRequest = neo4jMapper.getTaxationBasedSuggestionFromAimlResponse(aimlFileFinalResponse);
            	System.out.println("taxationBasedSuggestionRequest : "+taxationBasedSuggestionRequest.toString());
            	SuggestionResponse suggestionResponse = externalService.processFileToNeo4jToGetSuggestions(taxationBasedSuggestionRequest);
            	logger.debug("Neo4j file suggestion ", suggestionResponse.getSuggestions().toString());
            	System.out.println("Neo4j file suggestion "+suggestionResponse.getSuggestions().toString());
            	// it will create final response
            	Map<AimlFileResponse, RequirementSuggestions> map = helper.fetchMapBasedOnRequirementId(aimlFileFinalResponse, suggestionResponse);
            	return helper.createFinalUploadResponseList(map);
            	
            } else {
            	logger.error("Could not find resource while reading file "+fileName);
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
        	logger.error("Caught error while reading file "+ex);
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }    	
    }
    
    /**
     * This method will read file data which is temporarily saved
     * and returns list of experian file object.
     * @param resource
     * @return
     */
    public ExperianFileRequest readFileData(Resource resource) {
    	ExperianFileRequest experianFile = new ExperianFileRequest();
    	try {
			FileInputStream excelFile  = new FileInputStream(resource.getFile());
			Workbook workbook = new XSSFWorkbook(excelFile);
			
			// Get Requirement List
            List<RequirementStatement> requirementList = new ArrayList<RequirementStatement>();
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int count =1;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                RequirementStatement requirement = new RequirementStatement();
                requirement.setId(count);
                requirement.setRequirementStatement(currentRow.getCell(0).getStringCellValue());
                logger.info("requirement number "+requirement.getId()+" requirement : "+requirement.getRequirementStatement());
                requirementList.add(requirement);
                count++;
            }
            experianFile.setRequirementList(requirementList);
            workbook.close();
		} catch (IOException ex) {
			logger.error("Caught error while reading file", ex);
			throw new FileStorageException("Caught error while reading file", ex);
		}
		return experianFile;
    }
    
    
}
