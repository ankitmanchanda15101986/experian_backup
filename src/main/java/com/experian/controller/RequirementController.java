/**
 * 
 */
package com.experian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.experian.dto.ExperianMatchedRequirementsRequest;
import com.experian.dto.ExperianSearchRequest;
import com.experian.dto.FileUploadResponse;
import com.experian.dto.FileUploadResponseList;
import com.experian.dto.aiml.response.AimlQualityScore;
import com.experian.dto.chatbot.response.ChatbotFinalResponse;
import com.experian.dto.neo4j.finalResponse.SavedDataResponse;
import com.experian.dto.neo4j.request.FinalNeo4JRequest;
import com.experian.dto.neo4j.response.SuggestionResponse;
import com.experian.dto.neo4j.response.autocomplete.AutoCompleteResponse;
import com.experian.dto.neo4j.response.taxation.Taxation;
import com.experian.resolver.ExcelView;
import com.experian.service.ExternalService;
import com.experian.validator.Validator;

/**
 * @author manchanda.a
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders="*", allowCredentials="true",  origins = "*", maxAge = 3600)
public class RequirementController {

	@Autowired
	private ExternalService service;

	@Autowired
	private Validator validate;

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public List<SavedDataResponse> submitRequirementToNeo4j(@RequestBody FinalNeo4JRequest request) {
		return service.processFinalResponse(request);
	}

	/**
	 * This method will generate brd document,when user click on generate document.
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/generate/brd", method = RequestMethod.GET, produces = "application/xlsx")
	public ModelAndView generateBRDDocument(@RequestParam("input") String requirementElaboration) {
		System.out.println("requirementElaboration : "+requirementElaboration);
		List<SavedDataResponse> dataResponses = service.getResultToGenerateBrd(requirementElaboration);
		return new ModelAndView(new ExcelView(),"request", dataResponses);
	}

	/**
	 * This controller will be called when user click on search for resources.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public SuggestionResponse searchResource(@RequestBody ExperianSearchRequest request) {
		validate.validateSearchText(request.getRequirement());
		SuggestionResponse response = service.searchRequirementToGetSuggestions(request.getRequirement());
		return response;
	}

	/**
	 * This controller will be called when user update level 3 and 4 , it will
	 * call AI/ML service to get updated score.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public AimlQualityScore getScore(@RequestBody ExperianSearchRequest request) {
		return service.refreshQualityScore(request.getRequirement());
	}

	/**
	 * This controller will be called when there is search and match conditions
	 * occurs.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add/matching", method = RequestMethod.POST)
	public FileUploadResponseList addNewResourceMatched(@RequestBody ExperianMatchedRequirementsRequest request) {
		return service.addMatchedRequirement(request.getMatchedRequirements());
	}

	/**
	 * This controller will be called when no match found for requirement.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add/notMatching", method = RequestMethod.POST)
	public FileUploadResponse addNoMatchFoundForResource(@RequestBody ExperianSearchRequest request) {
		return service.addNoMatchedRequirement(request.getRequirement());
	}

	/**
	 * This controller will be called when new requirement is added.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add/new", method = RequestMethod.POST)
	public FileUploadResponse addNewResource(@RequestBody ExperianSearchRequest request) {
		return service.addNewRequirement(request.getRequirement());
	}

	/**
	 * This controller will be called to retrieve taxation mapping.
	 */
	@RequestMapping(value = "/getTaxation", method = RequestMethod.GET)
	public List<Taxation> getTaxation() {
		return service.getTaxation();
	}

	/**
	 * This controller will be called when chatbot send request to get quality
	 * score.
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/chatbot/score", method = RequestMethod.POST)
	public ChatbotFinalResponse getChatbotQualityScore(@RequestBody ExperianSearchRequest request) {
		return service.calculateChatBotScore(request.getRequirement());
	}
	
	/**
	 * This controller will be called when autocomplete request comes from frontend.
	 * @param statement
	 * @return
	 */
	@RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
	public List<AutoCompleteResponse> getAutoComplete(@RequestParam("input") String inputStatement) {
		return service.processAutoComplete(inputStatement);
	}
}
