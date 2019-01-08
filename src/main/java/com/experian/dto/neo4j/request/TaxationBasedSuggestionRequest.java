/**
 * 
 */
package com.experian.dto.neo4j.request;

import java.util.List;

import com.experian.dto.neo4j.TaxationBasedSuggestion;

/**
 * @author manchanda.a
 *
 */
public class TaxationBasedSuggestionRequest {

	private List<TaxationBasedSuggestion> request;

	/**
	 * @return the request
	 */
	public List<TaxationBasedSuggestion> getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(List<TaxationBasedSuggestion> request) {
		this.request = request;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaxationBasedSuggestionRequest [request=" + request + "]";
	}
	
	
}
