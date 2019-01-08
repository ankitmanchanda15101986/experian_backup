/**
 * 
 */
package com.experian.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.experian.dto.ExperianFileRefreshRequest;
import com.experian.dto.FileUploadResponse;
import com.experian.dto.FileUploadResponseList;
import com.experian.dto.aiml.request.AIMLFileRequest;
import com.experian.dto.aiml.response.AimlFileFinalResponse;
import com.experian.dto.aiml.response.AimlFileResponse;
import com.experian.dto.aiml.response.RefreshScoreResponse;
import com.experian.dto.chatbot.request.ChatBotScoreRequest;
import com.experian.dto.chatbot.response.ChatBotScoreResponse;
import com.experian.dto.neo4j.RequirementStatement;
import com.experian.dto.neo4j.RequirementSuggestions;
import com.experian.dto.neo4j.Suggestions;
import com.experian.dto.neo4j.request.FinalNeo4JRequest;
import com.experian.dto.neo4j.request.TaxationBasedSuggestionRequest;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.TaxationResponse;
import com.experian.dto.neo4j.response.WordCategoryResponse;
import com.experian.mapper.ExperianNeo4JMapper;

import org.springframework.stereotype.Service;

/**
 * @author manchanda.a
 *
 */
@Service
public class ExternalService {
	private static final Logger logger = LoggerFactory.getLogger(ExternalService.class);

	@Autowired
	private RestTemplate template;

	@Autowired
	private ServiceHelper helper;

	@Autowired
	private ExperianNeo4JMapper neo4jMapper;

	@Value("${service.aiml.processfile.uri}")
	private String aimlProcessFileUri;

	@Value("${service.neo4j.suggestion.uri}")
	private String neo4jSuggestionUri;

	@Value("${service.save.uri}")
	private String saveUri;

	@Value("${service.word.category.uri}")
	private String wordCategoryUri;

	@Value("${service.calculate.score.uri}")
	private String calculateScoreUri;

	@Value("${service.taxation.uri}")
	private String taxationUri;

	@Value("${service.chatbot.calculate.score.uri}")
	private String chatbotCalculateScoreUri;

	/**
	 * This method will call AI/ML service and pass requirement statement , in
	 * return it will get level 1,2,3,4 and quality score of that statement.
	 * 
	 * @return
	 */
	public AimlFileFinalResponse processFileToAiml(AIMLFileRequest request) {
		ResponseEntity<AimlFileFinalResponse> response = template.postForEntity(aimlProcessFileUri, request,
				AimlFileFinalResponse.class);
		return response.getBody();

	}

	/**
	 * This method will call neo 4j service and pass requirement statement in return
	 * it will get suggestions and match % from neo4j which internally uses elastic
	 * search. suggestion
	 * 
	 * @param taxationBasedSuggestionRequest
	 * @return
	 */
	public SuggestionResponse processFileToNeo4jToGetSuggestions(
			TaxationBasedSuggestionRequest taxationBasedSuggestionRequest) {
		ResponseEntity<SuggestionResponse> response = template.postForEntity(neo4jSuggestionUri,
				taxationBasedSuggestionRequest, SuggestionResponse.class);
		return response.getBody();
	}

	/**
	 * This method will call neo 4j service and pass requirement statement in return
	 * it will get suggestions and match % from neo4j which internally uses elastic
	 * search.
	 * 
	 * @param taxationBasedSuggestionRequest
	 * @return
	 */
	public SuggestionResponse searchRequirementToGetSuggestions(String searchInput) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AIMLFileRequest aIMLFileRequest = new AIMLFileRequest();
			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			RequirementStatement requirementStatement = new RequirementStatement();
			requirementStatement.setId(1);
			requirementStatement.setRequirement(searchInput);
			requirementStatementList.add(requirementStatement);

			aIMLFileRequest.setRequirementStatement(requirementStatementList);
			aIMLFileRequest.setWordCategory(wordCategoryResponse);

			// Call AIML to process request
			AimlFileFinalResponse aimlResponse = processFileToAiml(aIMLFileRequest);
			if (aimlResponse != null && !aimlResponse.getResponse().isEmpty()) {
				// Call to get suggestion.
				TaxationBasedSuggestionRequest taxationBasedSuggestionRequest = neo4jMapper
						.getTaxationBasedSuggestionFromAimlResponse(aimlResponse);
				SuggestionResponse suggestionResponse = processFileToNeo4jToGetSuggestions(
						taxationBasedSuggestionRequest);
				return suggestionResponse;
			}
		}
		return null;
	}

	/**
	 * This method will call neo4j with final request that will be saved in neo4j
	 * database.
	 * 
	 * @param request
	 * @return
	 */
	public boolean processFinalResponse(FinalNeo4JRequest request) {
		ResponseEntity<Boolean> response = template.postForEntity(saveUri, request, Boolean.class);
		return response.getBody();
	}

	/**
	 * This method will call neo4j service to get list of word category and process
	 * response.
	 * 
	 * @return
	 */
	public WordCategoryResponse getWordCategoryFromNeo4j() {
		ResponseEntity<WordCategoryResponse> response = template.getForEntity(wordCategoryUri,
				WordCategoryResponse.class);
		return response.getBody();
	}

	/**
	 * This method will call ai/ml and pass taxation level 1,2,3,4 and requirement
	 * statement, in return it will get score.
	 * 
	 * @param request
	 * @return
	 */
	public RefreshScoreResponse calculateScore(ExperianFileRefreshRequest request) {
		ResponseEntity<RefreshScoreResponse> response = template.postForEntity(calculateScoreUri, request,
				RefreshScoreResponse.class);
		return response.getBody();
	}

	/**
	 * This method will take string as an request parameter and then call ai/ml to
	 * get taxation levels and quality score, and then neo4j to get suggestions
	 * based on that it will pass combine it and prepare response.
	 * 
	 * @param requirement
	 * @return
	 */
	public FileUploadResponse addNewRequirement(String requirement) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AIMLFileRequest aIMLFileRequest = new AIMLFileRequest();
			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			RequirementStatement requirementStatement = new RequirementStatement();
			requirementStatement.setId(1);
			requirementStatement.setRequirement(requirement);
			requirementStatementList.add(requirementStatement);

			aIMLFileRequest.setRequirementStatement(requirementStatementList);
			aIMLFileRequest.setWordCategory(wordCategoryResponse);

			// Call AIML to process request
			AimlFileFinalResponse aimlResponse = processFileToAiml(aIMLFileRequest);
			if (aimlResponse != null) {
				// Call to get suggestion.
				TaxationBasedSuggestionRequest taxationBasedSuggestionRequest = neo4jMapper
						.getTaxationBasedSuggestionFromAimlResponse(aimlResponse);
				SuggestionResponse suggestionResponse = processFileToNeo4jToGetSuggestions(
						taxationBasedSuggestionRequest);
				if (suggestionResponse != null) {
					Map<AimlFileResponse, RequirementSuggestions> map = helper
							.fetchMapBasedOnRequirementId(aimlResponse, suggestionResponse);
					FileUploadResponseList responseList = helper.createFinalUploadResponseList(map);
					if (!responseList.getResponse().isEmpty()) {
						return responseList.getResponse().get(0);
					}
				}
			}
		}
		return null;
	}

	/**
	 * This method will return response for matched case requirements. So Neo4j wont
	 * be called again we have got any matching for that requirement.
	 * 
	 * @param suggestionList
	 * @return
	 */
	public FileUploadResponse addMatchedRequirement(List<Suggestions> suggestionList) {
		// Get Word count.
		List<RequirementSuggestions> requirementSuggestionsList = new ArrayList<>();
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AIMLFileRequest aIMLFileRequest = new AIMLFileRequest();
			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			int count = 1;
			for (Suggestions suggestions : suggestionList) {
				RequirementStatement requirementStatement = new RequirementStatement();
				requirementStatement.setId(count);
				requirementStatement.setRequirement(suggestions.getSuggestion());
				count++;
				requirementStatementList.add(requirementStatement);

				// Creating requirement suggestions object.
				RequirementSuggestions requirementSuggestions = new RequirementSuggestions();
				requirementSuggestions.setRequirements(requirementStatement);
				List<Suggestions> suggest = new ArrayList<>();
				suggest.add(suggestions);
				requirementSuggestions.setSuggestionResponse(suggest);
			}
			aIMLFileRequest.setRequirementStatement(requirementStatementList);
			aIMLFileRequest.setWordCategory(wordCategoryResponse);

			// Call AIML to process request
			AimlFileFinalResponse aimlResponse = processFileToAiml(aIMLFileRequest);
			if (aimlResponse != null) {
				SuggestionResponse suggestionResponse = new SuggestionResponse();
				suggestionResponse.setSuggestions(requirementSuggestionsList);
				Map<AimlFileResponse, RequirementSuggestions> map = helper.fetchMapBasedOnRequirementId(aimlResponse,
						suggestionResponse);
				FileUploadResponseList responseList = helper.createFinalUploadResponseList(map);
				if (!responseList.getResponse().isEmpty()) {
					return responseList.getResponse().get(0);
				}
			}
		}
		return null;
	}

	/**
	 * This method will return response for no match case requirements. So Neo4j
	 * wont be called again as we already have got requirements from matched set.
	 * 
	 * @param requirement
	 * @return
	 */
	public FileUploadResponse addNoMatchedRequirement(String requirement) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AIMLFileRequest aIMLFileRequest = new AIMLFileRequest();
			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			RequirementStatement requirementStatement = new RequirementStatement();
			requirementStatement.setId(1);
			requirementStatement.setRequirement(requirement);
			requirementStatementList.add(requirementStatement);
			aIMLFileRequest.setRequirementStatement(requirementStatementList);
			aIMLFileRequest.setWordCategory(wordCategoryResponse);

			// Call AIML to process request
			AimlFileFinalResponse aimlResponse = processFileToAiml(aIMLFileRequest);
			if (aimlResponse != null) {
				SuggestionResponse suggestionResponse = new SuggestionResponse();
				suggestionResponse.setSuggestions(new ArrayList<>());
				Map<AimlFileResponse, RequirementSuggestions> map = helper.fetchMapBasedOnRequirementId(aimlResponse,
						suggestionResponse);
				FileUploadResponseList responseList = helper.createFinalUploadResponseList(map);
				if (!responseList.getResponse().isEmpty()) {
					return responseList.getResponse().get(0);
				}
			}
		}
		return null;

	}

	/**
	 * This method will call neo4j service to get taxation levels and process
	 * response.
	 * 
	 * @return
	 */
	public TaxationResponse getTaxation() {
		ResponseEntity<TaxationResponse> response = template.getForEntity(taxationUri, TaxationResponse.class);
		return response.getBody();
	}

	/**
	 * This method will calculate chatbot score.
	 * @param requirement
	 * @return
	 */
	public ChatBotScoreResponse calculateChatBotScore(String requirement) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			ChatBotScoreRequest request = new ChatBotScoreRequest();
			request.setRequirement(requirement);
			request.setWordCategory(wordCategoryResponse.getWordCategory());
			ResponseEntity<ChatBotScoreResponse> response = template.postForEntity(
					chatbotCalculateScoreUri,request, ChatBotScoreResponse.class);
			return response.getBody();
		}

		return null;
	}
}
