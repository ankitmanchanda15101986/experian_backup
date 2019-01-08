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

	private List<Taxation> taxationResponse;

	/**
	 * @return the taxationResponse
	 */
	public List<Taxation> getTaxationResponse() {
		return taxationResponse;
	}

	/**
	 * @param taxationResponse the taxationResponse to set
	 */
	public void setTaxationResponse(List<Taxation> taxationResponse) {
		this.taxationResponse = taxationResponse;
	}	
}
