/**
 * 
 */
package com.experian.dto.neo4j.response;

import java.util.List;

import com.experian.dto.neo4j.response.taxation.Taxation;

/**
 * @author manchanda.a
 *
 */
public class TaxationResponse {

	private List<Taxation> response;

	/**
	 * @return the response
	 */
	public List<Taxation> getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(List<Taxation> response) {
		this.response = response;
	}
	
	
}
