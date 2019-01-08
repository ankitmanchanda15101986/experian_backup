package com.experian.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.experian.dto.ExperianFileRequest;
import com.experian.dto.FileUploadResponse;
import com.experian.dto.aiml.request.AimlQualityScoreRequest;
import com.experian.dto.aiml.request.AimlTaxationRequest;
import com.experian.dto.aiml.response.AimlFileFinalResponse;
import com.experian.dto.aiml.response.AimlFileResponse;
import com.experian.dto.aiml.response.AimlQualityScore;
import com.experian.dto.aiml.response.AimlQualityScoreResponse;
import com.experian.dto.aiml.response.AimlTaxation;
import com.experian.dto.aiml.response.AimlTaxationResponse;
import com.experian.dto.neo4j.RequirementStatement;
import com.experian.dto.neo4j.RequirementSuggestions;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.WordCategoryResponse;

@Component
public class ExperianAIMLMapper {

	/**
	 * This method will convert master data and experian file request to aiml taxation request which will be sent
	 * as aiml request.
	 * @param masterData
	 * @param request
	 * @return
	 */
	public AimlTaxationRequest mapBatchRequestToAIMLTaxationRequest(WordCategoryResponse wordCategory,
			ExperianFileRequest experianFileRequest) {
		AimlTaxationRequest request = new AimlTaxationRequest();
		request.setWordCategory(wordCategory);
		request.setRequirementStatements(experianFileRequest.getRequirementList());
		return request;		
	}	
	
	/**
	 * This method will convert experian file request to aiml quality score request.
	 * @param experianFileRequest
	 * @return
	 */
	public AimlQualityScoreRequest mapBatchRequestToAimlQualityScoreRequest(ExperianFileRequest experianFileRequest) {
		AimlQualityScoreRequest request = new AimlQualityScoreRequest();
		request.setRequirementStatements(experianFileRequest.getRequirementList());
		return request; 
	}
	
	/**
	 * This method will map quality score response and taxation response to create final response.
	 * @param aimlTaxationResponse
	 * @param aimlQualityScoreResponse
	 * @return
	 */
	public AimlFileFinalResponse mapQualityAndTaxationToGetFinalResponse(ExperianFileRequest experianFileRequest, AimlTaxationResponse aimlTaxationResponse, AimlQualityScoreResponse aimlQualityScoreResponse) {
		AimlFileFinalResponse aimlFileFinalResponse = new AimlFileFinalResponse();
		List<AimlFileResponse> aimlFileResponseList = new ArrayList<>();
		Map<Integer, AimlTaxation> aimlTaxationMap =  new HashMap<>();
		Map<Integer, RequirementStatement> requirementList = new HashMap<>();
		
		Map<Integer, AimlQualityScore> aimlQualityScoreMap =  new HashMap<>();
		if(experianFileRequest !=  null && !experianFileRequest.getRequirementList().isEmpty()) {
			for (RequirementStatement requirementStatement : experianFileRequest.getRequirementList()) {
				requirementList.put(requirementStatement.getId(), requirementStatement);
			}
		}
		
		if(aimlTaxationResponse != null && !aimlTaxationResponse.getTaxonomyClassification().isEmpty()) {
			for (AimlTaxation aimlTaxation : aimlTaxationResponse.getTaxonomyClassification()) {
				aimlTaxationMap.put(aimlTaxation.getId(), aimlTaxation);
			}
		}
		
		if(aimlQualityScoreResponse != null && !aimlQualityScoreResponse.getQualityScore().isEmpty()) {
			for (AimlQualityScore aimlQualityScore : aimlQualityScoreResponse.getQualityScore()) {
				aimlQualityScoreMap.put(aimlQualityScore.getId(), aimlQualityScore);
			}
		}
		
		for (Integer value : aimlTaxationMap.keySet()) {
			AimlFileResponse aimlFileResponse = convertAimlTaxationAndQualityScoreToFinalResponse(requirementList.get(value), aimlQualityScoreMap.get(value), aimlTaxationMap.get(value));
			aimlFileResponseList.add(aimlFileResponse);
		}
		aimlFileFinalResponse.setResponse(aimlFileResponseList);
		return aimlFileFinalResponse;
	}
	
	/**
	 * This method will convert aiml taxation and quality score to aiml final response.
	 * @param requirementStatement 
	 * @param aimlQualityScore
	 * @param aimlTaxation
	 * @return
	 */
	private AimlFileResponse convertAimlTaxationAndQualityScoreToFinalResponse(RequirementStatement requirementStatement, AimlQualityScore aimlQualityScore, AimlTaxation aimlTaxation) {
		AimlFileResponse aimlFileResponse = new AimlFileResponse();
		aimlFileResponse.setQualityScore(aimlQualityScore.getQualityScore());
		aimlFileResponse.setTaxonomy_Level_1(aimlTaxation.getTaxonomy_Level_1());
		aimlFileResponse.setTaxonomy_Level_2(aimlTaxation.getTaxonomy_Level_2());
		aimlFileResponse.setTaxonomy_Level_3(aimlTaxation.getTaxonomy_Level_3());
		aimlFileResponse.setTaxonomy_Level_4(aimlTaxation.getTaxonomy_Level_4());
		aimlFileResponse.setRequirementStatement(requirementStatement);
		return aimlFileResponse;
		
	}
	
	
	/**
	 * This method will convert aiml response and requirement suggestion into file upload response.response.setTaxonomy_Level_1(aimlResponse.getTaxonomy_Level_1());
	 * @param aimlResponse
	 * @param suggestionResponse
	 * @return
	 */
	public FileUploadResponse  mapRequestToFileUploadResponse(AimlFileResponse aimlResponse,
			RequirementSuggestions suggestionResponse) {
		FileUploadResponse response = new FileUploadResponse();
		response.setQualityScore(aimlResponse.getQualityScore());
		response.setRequirementStatement(aimlResponse.getRequirementStatement());
		response.setTaxonomy_Level_1(aimlResponse.getTaxonomy_Level_1());
		response.setTaxonomy_Level_2(aimlResponse.getTaxonomy_Level_2());
		response.setTaxonomy_Level_3(aimlResponse.getTaxonomy_Level_3());
		response.setTaxonomy_Level_4(aimlResponse.getTaxonomy_Level_4());
		response.setSuggestions(suggestionResponse.getSuggestionResponse());
		return response;		
	}
	
	/**
	 * This method will provide id and response based map.
	 * @param aimlResponseList
	 * @return
	 */
	public Map<Integer, AimlFileResponse> getAimlResponseMapper(AimlFileFinalResponse aimlResponseList) {
		Map<Integer, AimlFileResponse> map = new HashMap<>();
		for (AimlFileResponse response : aimlResponseList.getResponse()) {
			map.put(response.getRequirementStatement().getId(), response);
		}
		return map;
		
	}
	
	/**
	 * This method will provide id and response based map.
	 * @param suggestionResponse
	 * @return
	 */
	public Map<Integer, RequirementSuggestions> getRequirmementSuggestionsMapper(SuggestionResponse suggestionResponse) {
		Map<Integer, RequirementSuggestions> map = new HashMap<>();
		if(suggestionResponse != null) {
			for (RequirementSuggestions response : suggestionResponse.getSuggestions()) {
				map.put(response.getRequirements().getId(), response);
			}
		}
		return map;
		
	}
}
