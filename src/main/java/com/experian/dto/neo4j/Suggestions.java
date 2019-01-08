/**
 * 
 */
package com.experian.dto.neo4j;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author manchanda.a
 *
 */
public class Suggestions {

	private int matchPercentage;
	private String suggestion;
	private String taxation;
	
	
	/**
	 * @return the matchPercentage
	 */
	public int getMatchPercentage() {
		return matchPercentage;
	}
	/**
	 * @param matchPercentage the matchPercentage to set
	 */
	public void setMatchPercentage(int matchPercentage) {
		this.matchPercentage = matchPercentage;
	}
	/**
	 * @return the suggestion
	 */
	public String getSuggestion() {
		return suggestion;
	}
	/**
	 * @param suggestion the suggestion to set
	 */
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	/**
	 * @return the taxation
	 */
	public String getTaxation() {
		return taxation;
	}
	/**
	 * @param taxation the taxation to set
	 */
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}	
	
}
