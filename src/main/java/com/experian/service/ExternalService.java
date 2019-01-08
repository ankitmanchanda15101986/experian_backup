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
import com.experian.dto.neo4j.RequirementStatement;
import com.experian.dto.neo4j.RequirementSuggestions;
import com.experian.dto.neo4j.Suggestions;
import com.experian.dto.neo4j.request.FinalNeo4JRequest;
import com.experian.dto.neo4j.request.TaxationBasedSuggestionRequest;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.TaxationResponse;
import com.experian.dto.neo4j.response.WordCategoryResponse;
import com.experian.mapper.ExperianAIMLMapper;
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

	@Value("${service.aiml.taxation.uri}")
	private String aimlGetTaxationUri;
	
	@Value("${service.aiml.quality.score.uri}")
	private String aimlGetQualityScoreUri;

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
	 * return it will get taxation level 1,2,3,4.
	 * 
	 * @return
	 */
	public AimlTaxationResponse processFileToAimlToGetTaxation(AimlTaxationRequest request) {
		System.out.println("aimlGetTaxationUri : "+aimlGetTaxationUri);
		ResponseEntity<AimlTaxationResponse> response = template.postForEntity(aimlGetTaxationUri, request,
				AimlTaxationResponse.class);
		System.out.println("response.getBody() "+response.getBody());
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

	/**
	 * This method will call neo 4j service and pass requirement statement in
	 * return it will get suggestions and match % from neo4j which internally
	 * uses elastic search. suggestion
	 * 
	 * @param taxationBasedSuggestionRequest
	 * @return
	 */
	public SuggestionResponse processFileToNeo4jToGetSuggestions(
			TaxationBasedSuggestionRequest taxationBasedSuggestionRequest) {
		ResponseEntity<SuggestionResponse> response = template.postForEntity(neo4jSuggestionUri,
				taxationBasedSuggestionRequest, SuggestionResponse.class);
		System.out.println(" response for suggestion "+response.getBody());
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
			requirementStatement.setId(1);
			requirementStatement.setRequirementStatement(searchInput);
			requirementStatementList.add(requirementStatement);

			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlTaxationRequest.setWordCategory(wordCategoryResponse);
			aimlQualityScoreRequest.setRequirementStatements(requirementStatementList);
			experianFileRequest.setRequirementList(requirementStatementList);

			// Call AIML to process request to get taxation.
			AimlTaxationResponse aimlTaxationResponse = processFileToAimlToGetTaxation(aimlTaxationRequest);

			AimlQualityScoreResponse aimlQualityScoreRespnse = processFileToAimlToGetQualityScore(
					aimlQualityScoreRequest);

			AimlFileFinalResponse aimlFileFinalResponse = aimlMapper.mapQualityAndTaxationToGetFinalResponse(
					experianFileRequest, aimlTaxationResponse, aimlQualityScoreRespnse);

			// Call to get suggestion.
			TaxationBasedSuggestionRequest taxationBasedSuggestionRequest = neo4jMapper
					.getTaxationBasedSuggestionFromAimlResponse(aimlFileFinalResponse);
			SuggestionResponse suggestionResponse = processFileToNeo4jToGetSuggestions(taxationBasedSuggestionRequest);
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
		ResponseEntity<WordCategoryResponse> response = template.getForEntity(wordCategoryUri,
				WordCategoryResponse.class);
		return response.getBody();
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
			requirementStatement.setId(1);
			requirementStatement.setRequirementStatement(requirement);
			requirementStatementList.add(requirementStatement);

			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlTaxationRequest.setWordCategory(wordCategoryResponse);
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
				TaxationBasedSuggestionRequest taxationBasedSuggestionRequest = neo4jMapper
						.getTaxationBasedSuggestionFromAimlResponse(aimlFileFinalResponse);
				SuggestionResponse suggestionResponse = processFileToNeo4jToGetSuggestions(
						taxationBasedSuggestionRequest);
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
	public FileUploadResponse addMatchedRequirement(List<Suggestions> suggestionList) {
		// Get Word count.
		List<RequirementSuggestions> requirementSuggestionsList = new ArrayList<>();
		WordCategoryResponse wordCategoryResponse = getWordCategoryFromNeo4j();
		if (wordCategoryResponse != null) {
			AimlQualityScoreRequest aimlQualityScoreRequest = new AimlQualityScoreRequest();
			AimlTaxationRequest aimlTaxationRequest = new AimlTaxationRequest();
			ExperianFileRequest experianFileRequest = new ExperianFileRequest();

			List<RequirementStatement> requirementStatementList = new ArrayList<RequirementStatement>();
			int count = 1;
			for (Suggestions suggestions : suggestionList) {
				RequirementStatement requirementStatement = new RequirementStatement();
				requirementStatement.setId(count);
				requirementStatement.setRequirementStatement(suggestions.getSuggestion());
				count++;
				requirementStatementList.add(requirementStatement);
			}
			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlTaxationRequest.setWordCategory(wordCategoryResponse);
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

			SuggestionResponse suggestionResponse = new SuggestionResponse();
			suggestionResponse.setSuggestions(requirementSuggestionsList);
			Map<AimlFileResponse, RequirementSuggestions> map = helper.fetchMapBasedOnRequirementId(aimlFileFinalResponse,
					suggestionResponse);
			FileUploadResponseList responseList = helper.createFinalUploadResponseList(map);
			if (!responseList.getResponse().isEmpty()) {
				return responseList.getResponse().get(0);
			}
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
			requirementStatement.setId(1);
			requirementStatement.setRequirementStatement(requirement);
			requirementStatementList.add(requirementStatement);

			aimlTaxationRequest.setRequirementStatements(requirementStatementList);
			aimlTaxationRequest.setWordCategory(wordCategoryResponse);
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
	 * 
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
			ResponseEntity<ChatBotScoreResponse> response = template.postForEntity(chatbotCalculateScoreUri, request,
					ChatBotScoreResponse.class);
			return response.getBody();
		}

		return null;
	}
}
