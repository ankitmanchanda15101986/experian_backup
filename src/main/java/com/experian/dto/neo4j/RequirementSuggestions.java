/**
 * 
 */
package com.experian.dto.neo4j;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class RequirementSuggestions {

	private RequirementStatement requirements;
	private List<Suggestions> suggestionResponse;
	
	/**
	 * @return the requirements
	 */
	public RequirementStatement getRequirements() {
		return requirements;
	}
	/**
	 * @param requirements the requirements to set
	 */
	public void setRequirements(RequirementStatement requirements) {
		this.requirements = requirements;
	}
	
	
	/**
	 * @return the suggestionResponse
	 */
	public List<Suggestions> getSuggestionResponse() {
		return suggestionResponse;
	}
	/**
	 * @param suggestionResponse the suggestionResponse to set
	 */
	public void setSuggestionResponse(List<Suggestions> suggestionResponse) {
		this.suggestionResponse = suggestionResponse;
	}	
}
