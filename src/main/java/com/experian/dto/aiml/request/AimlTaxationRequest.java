/**
 * 
 */
package com.experian.dto.aiml.request;

import java.util.List;

import com.experian.dto.neo4j.RequirementStatement;
import com.experian.dto.neo4j.response.WordCategoryResponse;

/**
 * @author manchanda.a
 *
 */
public class AimlTaxationRequest {

	private List<RequirementStatement> requirementStatements;
	
	/**
	 * @return the requirementStatements
	 */
	public List<RequirementStatement> getRequirementStatements() {
		return requirementStatements;
	}
	/**
	 * @param requirementStatements the requirementStatements to set
	 */
	public void setRequirementStatements(List<RequirementStatement> requirementStatements) {
		this.requirementStatements = requirementStatements;
	}

	
	
	
}
