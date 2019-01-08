/**
 * 
 */
package com.experian.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.experian.dto.FileUploadResponse;
import com.experian.dto.FileUploadResponseList;
import com.experian.dto.aiml.response.AimlFileFinalResponse;
import com.experian.dto.aiml.response.AimlFileResponse;
import com.experian.dto.neo4j.RequirementSuggestions;
import com.experian.dto.neo4j.Suggestions;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.mapper.ExperianAIMLMapper;

/**
 * @author manchanda.a
 *
 */
@Component
public class ServiceHelper {
	
	@Autowired
	private ExperianAIMLMapper aimlMapper;
	
	/**
     * This method will create map of aiml file response and requirement suggestion based on id of requirement.
     * @param aimlResponseList
     * @param suggestionResponseList
     * @return
     */
    public Map<AimlFileResponse, RequirementSuggestions> fetchMapBasedOnRequirementId(AimlFileFinalResponse aimlResponseList,
    		SuggestionResponse suggestionResponseList) {
    	Map<AimlFileResponse, RequirementSuggestions> map = new HashMap<>();
    	Map<Integer, AimlFileResponse> aimlResponse = aimlMapper.getAimlResponseMapper(aimlResponseList);
    	Map<Integer, RequirementSuggestions> suggestionsResponse = aimlMapper.getRequirmementSuggestionsMapper(suggestionResponseList);
    	for (Integer id : aimlResponse.keySet()) {
    		if(suggestionsResponse != null && suggestionsResponse.containsKey(id)) {
    			map.put(aimlResponse.get(id), suggestionsResponse.get(id));
    		} else {
    			RequirementSuggestions requirementSuggestions = new RequirementSuggestions();
    			List<Suggestions> list = new ArrayList<Suggestions>();
    			requirementSuggestions.setSuggestionResponse(list);
    			map.put(aimlResponse.get(id), requirementSuggestions);
    		}
		}
    	return map;
    }
    
    /**
     * This method will create final upload response based on map of aiml file response and requirement suggestions.
     * @param map
     * @return
     */
    public FileUploadResponseList createFinalUploadResponseList(Map<AimlFileResponse, RequirementSuggestions> map) {
    	FileUploadResponseList finalResponse = new FileUploadResponseList();
    	List<FileUploadResponse> responseList = new ArrayList<>();
    	for (Entry<AimlFileResponse, RequirementSuggestions> path : map.entrySet()) {
    		FileUploadResponse response = aimlMapper.mapRequestToFileUploadResponse(path.getKey(), path.getValue());
    		responseList.add(response);
    	}
    	finalResponse.setResponse(responseList);
		return finalResponse;
    }

}
