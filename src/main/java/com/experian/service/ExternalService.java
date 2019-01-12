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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.experian.dto.ExperianFileRequest;
import com.experian.dto.FileUploadResponse;
import com.experian.dto.FileUploadResponseList;
import com.experian.dto.aiml.request.AimlQualityScoreRequest;
import com.experian.dto.aiml.request.AimlTaxationRequest;
import com.experian.dto.aiml.response.AimlFileFinalResponse;
import com.experian.dto.aiml.response.AimlFileResponse;
import com.experian.dto.aiml.response.AimlQualityScoreResponse;
import com.experian.dto.aiml.response.AimlTaxationResponse;
import com.experian.dto.chatbot.request.ChatBotScoreRequest;
import com.experian.dto.chatbot.response.ChatBotScoreResponse;
import com.experian.dto.chatbot.response.ChatbotFinalResponse;
import com.experian.dto.neo4j.RequirementStatement;
import com.experian.dto.neo4j.RequirementSuggestions;
import com.experian.dto.neo4j.Suggestions;
import com.experian.dto.neo4j.request.FinalNeo4JRequest;
import com.experian.dto.neo4j.request.latest.Neo4jSuggestionDataList;
import com.experian.dto.neo4j.request.latest.Neo4jSuggestionResponse;
import com.experian.dto.neo4j.request.latest.SuggestionBasedOnMultipleRequirementRequest;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.WordCategory;
import com.experian.dto.neo4j.response.WordCategoryResponse;
import com.experian.dto.neo4j.response.taxation.Taxation;
import com.experian.mapper.ExperianAIMLMapper;
import com.experian.mapper.ExperianChatBotMapper;
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

	@Autowired
	private ExperianAIMLMapper aimlMapper;
	
	@Autowired
	private ExperianChatBotMapper chatbotMapper;

	@Value("${service.aiml.taxation.uri}")
	private String aimlGetTaxationUri;

	@Value("${service.aiml.quality.score.uri}")
	private String aimlGetQualityScoreUri;

	@Value("${service.save.uri}")
	private String saveUri;

	@Value("${service.word.category.uri}")
	private String wordCategoryUri;

	@Value("${service.taxationMap.uri}")
	private String taxationMapUri;

	@Value("${service.chatbot.calculate.score.uri}")
	private String chatbotCalculateScoreUri;
	
	@Value("${service.neo4j.suggestion.latest.uri}")
	private String neo4jSuggestionLatestUri;

	/**
	 * This method will call AI/ML service and pass requirement statement , in
	 * return it will get taxation level 1,2,3,4.
	 * 
	 * @return
	 */
	public AimlTaxationResponse processFileToAimlToGetTaxation(AimlTaxationRequest request) {
		ResponseEntity<AimlTaxationResponse> response = template.postForEntity(aimlGetTaxationUri, request,
				AimlTaxationResponse.class);
		return response.getBody();

	}

	/**
	 * This method will call AI/ML service and pass requirement statement , in
	 * return it will get quality score.
	 * 
	 * @return
	 */
	public AimlQualityScoreResponse processFileToAimlToGetQualityScore(AimlQualityScoreRequest request) {
		ResponseEntity<AimlQualityScoreResponse> response = template.postForEntity(aimlGetQualityScoreUri, request,
				AimlQualityScoreResponse.class);
		return response.getBody();

	}	
	
	public List<Neo4jSuggestionResponse> processFileToNeo4jToGetSuggestion(SuggestionBasedOnMultipleRequirementRequest request) {
		ResponseEntity<List<Neo4jSuggestionResponse>> response = template.exchange(neo4jSuggestionLatestUri,  HttpMethod.POST, new HttpEntity<SuggestionBasedOnMultipleRequirementRequest>(request), new ParameterizedTypeReference<List<Neo4jSuggestionResponse>>() {
				});
		return response.getBody();
	}

	/**
	 * This method will call neo 4j service and pass requirement statement in
	 * return it will get suggestions and match % from neo4j which internally
	 * uses elastic search.
	 * 
	 * @param taxationBasedSuggestionRequest
	 * @return
	 */
	public SuggestionResponse searchRequirementToGetSuggestions(String searchInput) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AimlQualityScoreRequest aimlQualityScoreRequest = new AimlQualityScoreRequest();
			AimlTaxationRequest aimlTaxationRequest = new AimlTaxationRequest();
			ExperianFileRequest experianFileRequest = new ExperianFileRequest();

			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			RequirementStatement requirementStatement = new RequirementStatement();
			requirementStatement.setID(1);
			requirementStatement.setRequirementStatement(searchInput);
			requirementStatementList.add(requirementStatement);

			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlQualityScoreRequest.setWordCategory(wordCategoryResponse.getWordCategory());
			aimlQualityScoreRequest.setRequirementStatements(requirementStatementList);
			experianFileRequest.setRequirementList(requirementStatementList);

			// Call AIML to process request to get taxation.
			AimlTaxationResponse aimlTaxationResponse = processFileToAimlToGetTaxation(aimlTaxationRequest);

			AimlQualityScoreResponse aimlQualityScoreRespnse = processFileToAimlToGetQualityScore(
					aimlQualityScoreRequest);

			AimlFileFinalResponse aimlFileFinalResponse = aimlMapper.mapQualityAndTaxationToGetFinalResponse(
					experianFileRequest, aimlTaxationResponse, aimlQualityScoreRespnse);

			// Calling Neo4j Service to get suggestion.
			SuggestionBasedOnMultipleRequirementRequest suggestionRequest = neo4jMapper.convertRequirementSuggestionToSuggestionRequest(aimlFileFinalResponse);
			List<Neo4jSuggestionResponse> neo4jSuggestionResponse = processFileToNeo4jToGetSuggestion(suggestionRequest);
	
			SuggestionResponse suggestionResponse = neo4jMapper.convertSuggestionBasedResponse(neo4jSuggestionResponse);
			logger.debug("Neo4j file suggestion ", suggestionResponse.getSuggestions().toString());
			return suggestionResponse;
		}
		return null;
	}

	/**
	 * This method will call neo4j with final request that will be saved in
	 * neo4j database.
	 * 
	 * @param request
	 * @return
	 */
	public boolean processFinalResponse(FinalNeo4JRequest request) {
		ResponseEntity<Boolean> response = template.postForEntity(saveUri, request, Boolean.class);
		return response.getBody();
	}

	/**
	 * This method will call neo4j service to get list of word category and
	 * process response.
	 * 
	 * @return
	 */
	public WordCategoryResponse getWordCategoryFromNeo4j() {
		ResponseEntity<List<WordCategory>> response = template.exchange(wordCategoryUri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<WordCategory>>() {
				});
		WordCategoryResponse wordCategoryResponse = new WordCategoryResponse();
		wordCategoryResponse.setWordCategory(response.getBody());
		return wordCategoryResponse;
	}

	/**
	 * This method will take string as an request parameter and then call ai/ml
	 * to get taxation levels and quality score, and then neo4j to get
	 * suggestions based on that it will pass combine it and prepare response.
	 * 
	 * @param requirement
	 * @return
	 */
	public FileUploadResponse addNewRequirement(String requirement) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AimlQualityScoreRequest aimlQualityScoreRequest = new AimlQualityScoreRequest();
			AimlTaxationRequest aimlTaxationRequest = new AimlTaxationRequest();
			ExperianFileRequest experianFileRequest = new ExperianFileRequest();

			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			RequirementStatement requirementStatement = new RequirementStatement();
			requirementStatement.setID(1);
			requirementStatement.setRequirementStatement(requirement);
			requirementStatementList.add(requirementStatement);

			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlQualityScoreRequest.setWordCategory(wordCategoryResponse.getWordCategory());
			aimlQualityScoreRequest.setRequirementStatements(requirementStatementList);
			experianFileRequest.setRequirementList(requirementStatementList);

			// Call AIML to process request to get taxation.
			AimlTaxationResponse aimlTaxationResponse = processFileToAimlToGetTaxation(aimlTaxationRequest);
			// Call AIML to process request to get quality score.
			AimlQualityScoreResponse aimlQualityScoreRespnse = processFileToAimlToGetQualityScore(
					aimlQualityScoreRequest);
			// convert quality score and taxation into final response
			AimlFileFinalResponse aimlFileFinalResponse = aimlMapper.mapQualityAndTaxationToGetFinalResponse(
					experianFileRequest, aimlTaxationResponse, aimlQualityScoreRespnse);

			if (aimlFileFinalResponse != null) {
				// Call to get suggestion.
				// Calling Neo4j Service to get suggestion.
				SuggestionBasedOnMultipleRequirementRequest suggestionRequest = neo4jMapper.convertRequirementSuggestionToSuggestionRequest(aimlFileFinalResponse);
				List<Neo4jSuggestionResponse> neo4jSuggestionResponse = processFileToNeo4jToGetSuggestion(suggestionRequest);
		
				SuggestionResponse suggestionResponse = neo4jMapper.convertSuggestionBasedResponse(neo4jSuggestionResponse);
				logger.debug("Neo4j file suggestion ", suggestionResponse.getSuggestions().toString());
				if (suggestionResponse != null) {
					Map<AimlFileResponse, RequirementSuggestions> map = helper
							.fetchMapBasedOnRequirementId(aimlFileFinalResponse, suggestionResponse);
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
	 * This method will return response for matched case requirements. So Neo4j
	 * wont be called again we have got any matching for that requirement.
	 * 
	 * @param suggestionList
	 * @return
	 */
	public FileUploadResponseList addMatchedRequirement(List<Suggestions> suggestionList) {
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AimlQualityScoreRequest aimlQualityScoreRequest = new AimlQualityScoreRequest();
			AimlTaxationRequest aimlTaxationRequest = new AimlTaxationRequest();
			ExperianFileRequest experianFileRequest = new ExperianFileRequest();

			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			int count = 1;
			for (Suggestions suggestions : suggestionList) {
				RequirementStatement requirementStatement = new RequirementStatement();
				requirementStatement.setID(count);
				requirementStatement.setRequirementStatement(suggestions.getSuggestion());
				count++;
				requirementStatementList.add(requirementStatement);
			}
			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlQualityScoreRequest.setWordCategory(wordCategoryResponse.getWordCategory());
			aimlQualityScoreRequest.setRequirementStatements(requirementStatementList);
			experianFileRequest.setRequirementList(requirementStatementList);

			// Call AIML to process request to get taxation.
			AimlTaxationResponse aimlTaxationResponse = processFileToAimlToGetTaxation(aimlTaxationRequest);
			// Call AIML to process request to get quality score.
			AimlQualityScoreResponse aimlQualityScoreRespnse = processFileToAimlToGetQualityScore(
					aimlQualityScoreRequest);
			// convert quality score and taxation into final response
			AimlFileFinalResponse aimlFileFinalResponse = aimlMapper.mapQualityAndTaxationToGetFinalResponse(
					experianFileRequest, aimlTaxationResponse, aimlQualityScoreRespnse);
			SuggestionResponse suggestionResponse = createSuggestionResponseFromSuggestionList(suggestionList);
			Map<AimlFileResponse, RequirementSuggestions> map = helper
					.fetchMapBasedOnRequirementId(aimlFileFinalResponse, suggestionResponse);
			FileUploadResponseList responseList = helper.createFinalUploadResponseList(map);
			return responseList;
		}
		return null;
	}

	/**
	 * This method will return response for no match case requirements. So Neo4j
	 * wont be called again as we already have got requirements from matched
	 * set.
	 * 
	 * @param requirement
	 * @return
	 */
	public FileUploadResponse addNoMatchedRequirement(String requirement) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AimlQualityScoreRequest aimlQualityScoreRequest = new AimlQualityScoreRequest();
			AimlTaxationRequest aimlTaxationRequest = new AimlTaxationRequest();
			ExperianFileRequest experianFileRequest = new ExperianFileRequest();

			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			RequirementStatement requirementStatement = new RequirementStatement();
			requirementStatement.setID(1);
			requirementStatement.setRequirementStatement(requirement);
			requirementStatementList.add(requirementStatement);

			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlQualityScoreRequest.setWordCategory(wordCategoryResponse.getWordCategory());
			aimlQualityScoreRequest.setRequirementStatements(requirementStatementList);
			experianFileRequest.setRequirementList(requirementStatementList);

			// Call AIML to process request to get taxation.
			AimlTaxationResponse aimlTaxationResponse = processFileToAimlToGetTaxation(aimlTaxationRequest);
			// Call AIML to process request to get quality score.
			AimlQualityScoreResponse aimlQualityScoreRespnse = processFileToAimlToGetQualityScore(
					aimlQualityScoreRequest);
			// convert quality score and taxation into final response
			AimlFileFinalResponse aimlFileFinalResponse = aimlMapper.mapQualityAndTaxationToGetFinalResponse(
					experianFileRequest, aimlTaxationResponse, aimlQualityScoreRespnse);
			if (aimlFileFinalResponse != null) {
				SuggestionResponse suggestionResponse = new SuggestionResponse();
				suggestionResponse.setSuggestions(new ArrayList<>());
				Map<AimlFileResponse, RequirementSuggestions> map = helper
						.fetchMapBasedOnRequirementId(aimlFileFinalResponse, suggestionResponse);
				FileUploadResponseList responseList = helper.createFinalUploadResponseList(map);
				if (responseList != null) {
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
	public List<Taxation> getTaxation() {
		ResponseEntity<List<Taxation>> response = template.exchange(taxationMapUri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Taxation>>() {
				});
		return response.getBody();
	}

	/**
	 * This method will calculate chatbot score.
	 * 
	 * @param requirement
	 * @return
	 */
	public ChatbotFinalResponse calculateChatBotScore(String requirement) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			ChatBotScoreRequest request = new ChatBotScoreRequest();
			request.setRequirement(requirement);
			request.setWordCategory(wordCategoryResponse.getWordCategory());
			ResponseEntity<ChatBotScoreResponse> chatBotScoreResponse = template.postForEntity(chatbotCalculateScoreUri, request,
					ChatBotScoreResponse.class);
			AimlQualityScoreResponse aimlQualityScoreResponse = refreshQualityScore(requirement);
			ChatbotFinalResponse chatbotFinalResponse = chatbotMapper.createChatBotResponseToIncludeQualityResponse(aimlQualityScoreResponse, chatBotScoreResponse.getBody());
			return chatbotFinalResponse;
		}

		return null;
	}

	/**
	 * This method will create suggestion response from list of suggestions.
	 * 
	 * @param suggestionList
	 * @return
	 */
	private SuggestionResponse createSuggestionResponseFromSuggestionList(List<Suggestions> suggestionList) {
		SuggestionResponse suggestionResponse = new SuggestionResponse();
		List<RequirementSuggestions> requirementSuggestionsList = new ArrayList<>();
		int count = 1;
		for (Suggestions suggestions : suggestionList) {
			List<Suggestions> suggestionsList = new ArrayList<>();
			RequirementSuggestions requirementSuggestions = new RequirementSuggestions();
			if (suggestions != null) {
				RequirementStatement requirementStatement = new RequirementStatement();
				requirementStatement.setID(count);
				requirementStatement.setRequirementStatement(suggestions.getSuggestion());
				requirementSuggestions.setRequirements(requirementStatement);
				count++;
			}
			suggestionsList.add(suggestions);
			requirementSuggestions.setSuggestionResponse(suggestionsList);
			requirementSuggestionsList.add(requirementSuggestions);
		}
		suggestionResponse.setSuggestions(requirementSuggestionsList);
		return suggestionResponse;
	}

	/**
	 * This method will calculate get quality score based on requirement.
	 * @param requirement
	 * @return
	 */
	public AimlQualityScoreResponse refreshQualityScore(String requirement) {
		// Get Word count.
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AimlQualityScoreRequest aimlQualityScoreRequest = new AimlQualityScoreRequest();
			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			RequirementStatement requirementStatement = new RequirementStatement();
			requirementStatement.setID(1);
			requirementStatement.setRequirementStatement(requirement);
			requirementStatementList.add(requirementStatement);

			aimlQualityScoreRequest.setWordCategory(wordCategoryResponse.getWordCategory());
			aimlQualityScoreRequest.setRequirementStatements(requirementStatementList);

			// Call AIML to process request to get quality score.
			AimlQualityScoreResponse aimlQualityScoreRespnse = processFileToAimlToGetQualityScore(
					aimlQualityScoreRequest);
			return aimlQualityScoreRespnse;
		}
		return null;
	}
}
