/**
 * 
 */
package com.experian.dto;

import java.util.List;

import com.experian.dto.neo4j.RequirementStatement;

/**
 * @author manchanda.a
 *
 */
public class ExperianFileRequest {

	private List<RequirementStatement> requirementList;
	
	/**
	 * @return the requirementList
	 */
	public List<RequirementStatement> getRequirementList() {
		return requirementList;
	}
	/**
	 * @param requirementList the requirementList to set
	 */
	public void setRequirementList(List<RequirementStatement> requirementList) {
		this.requirementList = requirementList;
	}
	
	
	
	
}
