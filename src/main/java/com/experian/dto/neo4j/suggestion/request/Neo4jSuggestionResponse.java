/**
 * 
 */
package com.experian.dto.neo4j.suggestion.request;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class Neo4jSuggestionResponse {

	private List<Neo4jSuggestionDataList> result;
	private String id;
	
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the result
	 */
	public List<Neo4jSuggestionDataList> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<Neo4jSuggestionDataList> result) {
		this.result = result;
	}
	
	
}
