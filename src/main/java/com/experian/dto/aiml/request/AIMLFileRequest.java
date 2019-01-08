/**
 * 
 */
package com.experian.dto.aiml.request;

import java.util.List;

import com.experian.dto.neo4j.RequirementStatement;
import com.experian.dto.neo4j.response.WordCategoryResponse;

/**
 * @author manchanda.a
 *
 */
public class AIMLFileRequest {

	private WordCategoryResponse wordCategory;
	private List<RequirementStatement> requirementStatement;
	
	/**
	 * @return the wordCategory
	 */
	public WordCategoryResponse getWordCategory() {
		return wordCategory;
	}
	/**
	 * @param wordCategory the wordCategory to set
	 */
	public void setWordCategory(WordCategoryResponse wordCategory) {
		this.wordCategory = wordCategory;
	}
	/**
	 * @return the requirementStatement
	 */
	public List<RequirementStatement> getRequirementStatement() {
		return requirementStatement;
	}
	/**
	 * @param requirementStatement the requirementStatement to set
	 */
	public void setRequirementStatement(List<RequirementStatement> requirementStatement) {
		this.requirementStatement = requirementStatement;
	}
	
	
	
}
