/**
 * 
 */
package com.experian.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.stereotype.Service;
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
import com.experian.dto.neo4j.request.latest.Neo4jSuggestionResponse;
import com.experian.dto.neo4j.request.latest.SuggestionBasedOnMultipleRequirementRequest;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.WordCategoryResponse;
import com.experian.exception.FileStorageException;
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
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	/**
	 * This method will read file from path and then save data into database.
	 * 
	 * @param fileName
	 */
	public FileUploadResponseList readFile(MultipartFile file) {
		// it will read data from file and convert it to list of object.
		ExperianFileRequest experianFileRequest = readFileData(file);

		// it will call neo4j service to get master data.
		WordCategoryResponse wordCategoryResponse = externalService.getWordCategoryFromNeo4j();
		logger.debug("Word category response ", wordCategoryResponse.getWordCategory().toString());
		// it will call AI/ML Service to get taxation .
		AimlTaxationRequest aimlTaxationRequest = aimlMapper.mapBatchRequestToAIMLTaxationRequest(experianFileRequest);
		AimlTaxationResponse aimlTaxationResponse = externalService.processFileToAimlToGetTaxation(aimlTaxationRequest);
		logger.debug("Aiml taxation response ", aimlTaxationResponse.getTaxonomyClassification().size());

		// it will call AI/ML Service to get quality score.
		AimlQualityScoreRequest aimlQualityScoreRequest = aimlMapper
				.mapBatchRequestToAimlQualityScoreRequest(wordCategoryResponse, experianFileRequest);
		AimlQualityScoreResponse aimlQualityScoreResponse = externalService
				.processFileToAimlToGetQualityScore(aimlQualityScoreRequest);

		// it will convert file request , quality score and taxation to
		// get file final response.
		AimlFileFinalResponse aimlFileFinalResponse = aimlMapper.mapQualityAndTaxationToGetFinalResponse(
				experianFileRequest, aimlTaxationResponse, aimlQualityScoreResponse);
		logger.debug("Aiml final response ", aimlFileFinalResponse.getResponse().size());

		// Calling Neo4j Service
		SuggestionBasedOnMultipleRequirementRequest suggestionRequest = neo4jMapper
				.convertRequirementSuggestionToSuggestionRequest(aimlFileFinalResponse);
		List<Neo4jSuggestionResponse> neo4jSuggestionResponse = externalService
				.processFileToNeo4jToGetSuggestion(suggestionRequest);

		SuggestionResponse suggestionResponse = neo4jMapper.convertSuggestionBasedResponse(neo4jSuggestionResponse);
		logger.debug("Neo4j file suggestion ", suggestionResponse.getSuggestions().toString());

		// it will create final response
		Map<AimlFileResponse, RequirementSuggestions> map = helper.fetchMapBasedOnRequirementId(aimlFileFinalResponse,
				suggestionResponse);
		return helper.createFinalUploadResponseList(map);
	}

	/**
	 * This method will read file data and returns
	 * list of experian file object.
	 * 
	 * @param resource
	 * @return
	 */
	public ExperianFileRequest readFileData(MultipartFile file) {
		ExperianFileRequest experianFile = new ExperianFileRequest();
		try {
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
			// Get Requirement List
			List<RequirementStatement> requirementList = new ArrayList<RequirementStatement>();
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int count = 1;
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				if (count > 1) {
					RequirementStatement requirement = new RequirementStatement();
					Integer id = (int) currentRow.getCell(0).getNumericCellValue();
					requirement.setID(id);
					requirement.setRequirementStatement(currentRow.getCell(1).getStringCellValue());
					logger.debug("requirement number " + requirement.getID() + " requirement : "
							+ requirement.getRequirementStatement());
					requirementList.add(requirement);
				}
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
