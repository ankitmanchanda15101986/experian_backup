/**
 * 
 */
package com.experian.dto.neo4j.request.latest;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class SuggestionBasedOnMultipleRequirementRequest {

	private List<SuggestionBasedOnMultipleRequirement> multipleSearch;

	/**
	 * @return the multipleSearch
	 */
	public List<SuggestionBasedOnMultipleRequirement> getMultipleSearch() {
		return multipleSearch;
	}

	/**
	 * @param multipleSearch the multipleSearch to set
	 */
	public void setMultipleSearch(List<SuggestionBasedOnMultipleRequirement> multipleSearch) {
		this.multipleSearch = multipleSearch;
	}	
}
