/**
 * 
 */
package com.experian.dto.neo4j;

/**
 * @author manchanda.a
 *
 */
public class TaxationBasedSuggestion {

	private Integer id;
	private String requirementStatement;
	private String taxation;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	/**
	 * @return the taxation
	 */
	public String getTaxation() {
		return taxation;
	}
	/**
	 * @param taxation the taxation to set
	 */
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	
	
	
}
