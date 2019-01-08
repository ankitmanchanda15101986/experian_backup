/**
 * 
 */
package com.experian.dto;

import java.util.List;

import com.experian.dto.neo4j.Suggestions;

/**
 * @author manchanda.a
 *
 */
public class ExperianMatchedRequirementsRequest {

	public List<Suggestions> matchedRequirements;
	
	/**
	 * @return the matchedRequirements
	 */
	public List<Suggestions> getMatchedRequirements() {
		return matchedRequirements;
	}
	/**
	 * @param matchedRequirements the matchedRequirements to set
	 */
	public void setMatchedRequirements(List<Suggestions> matchedRequirements) {
		this.matchedRequirements = matchedRequirements;
	}
	
	
}
