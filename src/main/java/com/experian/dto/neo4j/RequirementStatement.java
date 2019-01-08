/**
 * 
 */
package com.experian.dto.neo4j;

/**
 * @author manchanda.a
 *
 */
public class RequirementStatement {

	private int id;
	private String requirement;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the requirement
	 */
	public String getRequirement() {
		return requirement;
	}
	
	/**
	 * @param requirement the requirement to set
	 */
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	
	
		
}
