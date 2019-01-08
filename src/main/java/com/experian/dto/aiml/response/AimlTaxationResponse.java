/**
 * 
 */
package com.experian.dto.aiml.response;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class AimlTaxationResponse {
	
	private List<AimlTaxation> taxonomyClassification;

	/**
	 * @return the taxonomyClassification
	 */
	public List<AimlTaxation> getTaxonomyClassification() {
		return taxonomyClassification;
	}

	/**
	 * @param taxonomyClassification the taxonomyClassification to set
	 */
	public void setTaxonomyClassification(List<AimlTaxation> taxonomyClassification) {
		this.taxonomyClassification = taxonomyClassification;
	}

	
	

}
