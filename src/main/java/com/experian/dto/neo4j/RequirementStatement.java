/**
 * 
 */
package com.experian.dto.neo4j;

/**
 * @author manchanda.a
 *
 */
public class RequirementStatement {

	private int ID;
	private String requirementStatement;
	
	

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
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
