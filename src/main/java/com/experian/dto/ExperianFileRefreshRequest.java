/**
 * 
 */
package com.experian.dto;

/**
 * @author manchanda.a
 *
 */
public class ExperianFileRefreshRequest {
	private String taxonomy_Level_1;
	private String taxonomy_Level_2;
	private String requirementStatement;
	
	/**
	 * @return the taxonomy_Level_1
	 */
	public String getTaxonomy_Level_1() {
		return taxonomy_Level_1;
	}
	
	/**
	 * @param taxonomy_Level_1 the taxonomy_Level_1 to set
	 */
	public void setTaxonomy_Level_1(String taxonomy_Level_1) {
		this.taxonomy_Level_1 = taxonomy_Level_1;
	}
	
	/**
	 * @return the taxonomy_Level_2
	 */
	public String getTaxonomy_Level_2() {
		return taxonomy_Level_2;
	}
	
	/**
	 * @param taxonomy_Level_2 the taxonomy_Level_2 to set
	 */
	public void setTaxonomy_Level_2(String taxonomy_Level_2) {
		this.taxonomy_Level_2 = taxonomy_Level_2;
	}
	
	/**
	 * @return the requirementStatement
	 */
	public String getRequirementStatement() {
		return requirementStatement;
	}
	
	/**
	 * @param requirementStatement the requirementStatement to set
	 */
	public void setRequirementStatement(String requirementStatement) {
		this.requirementStatement = requirementStatement;
	}
	
	
}
