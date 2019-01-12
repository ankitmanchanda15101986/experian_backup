/**
 * 
 */
package com.experian.dto.neo4j.request.latest;

/**
 * @author manchanda.a
 *
 */
public class Neo4jSuggestionData {

	private double searchScore;
	private double qualityScore;
	private String requirementElaboration;
	private Integer id;
	private String requirementStatement;
	private String level1;
	private String level3;
	private String level2;
	/**
	 * @return the searchScore
	 */
	public double getSearchScore() {
		return searchScore;
	}
	/**
	 * @param searchScore the searchScore to set
	 */
	public void setSearchScore(double searchScore) {
		this.searchScore = searchScore;
	}
	/**
	 * @return the qualityScore
	 */
	public double getQualityScore() {
		return qualityScore;
	}
	/**
	 * @param qualityScore the qualityScore to set
	 */
	public void setQualityScore(double qualityScore) {
		this.qualityScore = qualityScore;
	}
	/**
	 * @return the requirementElaboration
	 */
	public String getRequirementElaboration() {
		return requirementElaboration;
	}
	/**
	 * @param requirementElaboration the requirementElaboration to set
	 */
	public void setRequirementElaboration(String requirementElaboration) {
		this.requirementElaboration = requirementElaboration;
	}
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
	 * @return the level1
	 */
	public String getLevel1() {
		return level1;
	}
	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
	/**
	 * @return the level3
	 */
	public String getLevel3() {
		return level3;
	}
	/**
	 * @param level3 the level3 to set
	 */
	public void setLevel3(String level3) {
		this.level3 = level3;
	}
	/**
	 * @return the level2
	 */
	public String getLevel2() {
		return level2;
	}
	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}
	
	
}
