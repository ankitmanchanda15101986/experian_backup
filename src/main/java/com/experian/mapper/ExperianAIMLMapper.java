package com.experian.mapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.experian.dto.ExperianFileRequest;
import com.experian.dto.FileUploadResponse;
import com.experian.dto.aiml.request.AIMLFileRequest;
import com.experian.dto.aiml.response.AimlFileFinalResponse;
import com.experian.dto.aiml.response.AimlFileResponse;
import com.experian.dto.neo4j.RequirementSuggestions;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.WordCategoryResponse;

@Component
public class ExperianAIMLMapper {

	/**
	 * This method will convert master data and experian file request to aiml file request which will be sent
	 * as aiml request.
	 * @param masterData
	 * @param request
	 * @return
	 */
	public AIMLFileRequest mapBatchRequestToAIMLRequest(WordCategoryResponse wordCategory,
			ExperianFileRequest experianFileRequest) {
		AIMLFileRequest updateDto = new AIMLFileRequest();
		updateDto.setWordCategory(wordCategory);
		updateDto.setRequirementStatement(experianFileRequest.getRequirementList());
		return updateDto;		
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
