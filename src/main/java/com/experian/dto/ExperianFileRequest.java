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

	private String documentHeading;
	private String documentPurpose;
	private List<RequirementStatement> requirementList;
	/**
	 * @return the documentHeading
	 */
	public String getDocumentHeading() {
		return documentHeading;
	}
	/**
	 * @param documentHeading the documentHeading to set
	 */
	public void setDocumentHeading(String documentHeading) {
		this.documentHeading = documentHeading;
	}
	/**
	 * @return the documentPurpose
	 */
	public String getDocumentPurpose() {
		return documentPurpose;
	}
	/**
	 * @param documentPurpose the documentPurpose to set
	 */
	public void setDocumentPurpose(String documentPurpose) {
		this.documentPurpose = documentPurpose;
	}
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
