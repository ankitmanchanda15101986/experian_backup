/**
 * 
 */
package com.experian.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ICell;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
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
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.wordCategory.WordCategoryResponse;
import com.experian.dto.neo4j.suggestion.request.Neo4jSuggestionResponse;
import com.experian.dto.neo4j.suggestion.request.SuggestionBasedOnMultipleRequirementRequest;
import com.experian.exception.FileStorageException;
import com.experian.mapper.ExperianAIMLMapper;
import com.experian.mapper.ExperianNeo4JMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author manchanda.a
 *
 */
@Service
public class FileReaderService {
	public static final Logger logger = LoggerFactory.getLogger(FileReaderService.class);

	@Autowired
	private ExternalService externalService;

	@Autowired
	private ExperianAIMLMapper aimlMapper;

	@Autowired
	private ServiceHelper helper;

	@Autowired
	private ExperianNeo4JMapper neo4jMapper;


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
	 * @deprecated Use {@link com.experian.mapper.ExperianAIMLMapper#readExcelFile(MultipartFile)} instead
	 */
	public ExperianFileRequest readFileData(MultipartFile file) {
		ExperianFileRequest experianFile = null;
		if(file.getOriginalFilename().endsWith("xlsx")) {
			experianFile = readExcelFile(file);
		} else if(file.getOriginalFilename().endsWith("docx")) {
			experianFile = readWordFile(file);
		} 
		return experianFile;
	}
	
	
	/**
	 * This method will read excel file data and returns
	 * list of experian file object.
	 * 
	 * @param resource
	 * @param file TODO
	 * @return
	 */
	private ExperianFileRequest readExcelFile(MultipartFile file) {
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
					FileReaderService.logger.debug("requirement number " + requirement.getID() + " requirement : "
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
	
	/**
	 * This method will read word file data and returns
	 * list of experian file object.
	 * 
	 * @param resource
	 * @param file TODO
	 * @return
	 */
	private ExperianFileRequest readWordFile(MultipartFile file) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ExperianFileRequest experianFile = new ExperianFileRequest();
		List<RequirementStatement> requirementStatements = new ArrayList<>();
		try {
			XWPFDocument document = new XWPFDocument(file.getInputStream());
			List<XWPFTable> fileData = document.getTables();
			for (XWPFTable xwpfTable : fileData) {
				int rowCount = 0;
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					int count = 0;
					RequirementStatement requirementStatement = new RequirementStatement();
					if(rowCount > 0) {
						for (XWPFTableCell cell : xwpfTableRow.getTableCells()) {
							if(count==0) {
								requirementStatement.setID(Integer.parseInt(cell.getText()));
							} else {
								requirementStatement.setRequirementStatement(cell.getText());
							}
							count++;
						}
						requirementStatements.add(requirementStatement);
					}
					rowCount++;
				}
			}
			experianFile.setRequirementList(requirementStatements);
			document.close();
			
		} catch (IOException ex) {
			logger.error("Caught error while reading file", ex);
			throw new FileStorageException("Caught error while reading file", ex);
		}
		return experianFile;
	}

}
