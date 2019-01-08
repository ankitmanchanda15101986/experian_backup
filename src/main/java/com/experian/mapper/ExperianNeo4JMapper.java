package com.experian.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.experian.dto.aiml.response.AimlFileFinalResponse;
import com.experian.dto.aiml.response.AimlFileResponse;
import com.experian.dto.neo4j.TaxationBasedSuggestion;
import com.experian.dto.neo4j.request.TaxationBasedSuggestionRequest;

@Component
public class ExperianNeo4JMapper {
	
	
	/**
	 * This method will get list of taxation based suggestion from aiml file response.
	 * @param aimlResponse
	 * @return
	 */
	public TaxationBasedSuggestionRequest getTaxationBasedSuggestionFromAimlResponse(AimlFileFinalResponse aimlResponse) {
		TaxationBasedSuggestionRequest taxationBasedSuggestionRequest = new TaxationBasedSuggestionRequest();
		List<TaxationBasedSuggestion> taxationBasedSuggestionlist = new ArrayList<>();
		System.out.println(" aimlResponse.getResponse().isEmpty() "+aimlResponse.getResponse().isEmpty());
		if(!aimlResponse.getResponse().isEmpty()) {
			for (AimlFileResponse response : aimlResponse.getResponse()) {
				System.out.println("AimlFileResponse  "+response.toString());
				TaxationBasedSuggestion taxationBasedSuggestion = new TaxationBasedSuggestion();
				taxationBasedSuggestion.setId(response.getRequirementStatement().getId());
				taxationBasedSuggestion.setRequirementStatement(response.getRequirementStatement().getRequirementStatement());
				taxationBasedSuggestion.setTaxation(response.getTaxonomy_Level_1());
				taxationBasedSuggestionlist.add(taxationBasedSuggestion);
			}
		}
		taxationBasedSuggestionRequest.setRequest(taxationBasedSuggestionlist);
		return taxationBasedSuggestionRequest;
	}

}
