/**
 * 
 */
package com.experian.dto.neo4j.response;

import java.util.List;

import com.experian.dto.neo4j.RequirementSuggestions;

/**
 * @author manchanda.a
 *
 */
public class SuggestionResponse {
	
	private List<RequirementSuggestions> suggestions;

	/**
	 * @return the suggestions
	 */
	public List<RequirementSuggestions> getSuggestions() {
		return suggestions;
	}

	/**
	 * @param suggestions the suggestions to set
	 */
	public void setSuggestions(List<RequirementSuggestions> suggestions) {
		this.suggestions = suggestions;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SuggestionResponse [suggestions=" + suggestions + ", getSuggestions()=" + getSuggestions()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
}
