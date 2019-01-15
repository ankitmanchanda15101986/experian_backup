/**
 * 
 */
package com.experian.dto.neo4j.response.taxation;

import java.util.List;

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
