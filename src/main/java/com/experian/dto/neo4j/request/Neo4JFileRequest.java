/**
 * 
 */
package com.experian.dto.neo4j.request;

/**
 * @author manchanda.a
 *
 */
public class Neo4JFileRequest {

	private Integer id;
	private double qualityScore;
	private String requirementStatement;
	private ShortCutDocument shortCutDocument;
	private String taxonomyLevel1;
	private String taxonomyLevel2;
	private String taxonomyLevel3;
	private String taxonomyLevel4;
	
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
	 * @return the shortCutDocument
	 */
	public ShortCutDocument getShortCutDocument() {
		return shortCutDocument;
	}
	/**
	 * @param shortCutDocument the shortCutDocument to set
	 */
	public void setShortCutDocument(ShortCutDocument shortCutDocument) {
		this.shortCutDocument = shortCutDocument;
	}
	/**
	 * @return the taxonomyLevel1
	 */
	public String getTaxonomyLevel1() {
		return taxonomyLevel1;
	}
	/**
	 * @param taxonomyLevel1 the taxonomyLevel1 to set
	 */
	public void setTaxonomyLevel1(String taxonomyLevel1) {
		this.taxonomyLevel1 = taxonomyLevel1;
	}
	/**
	 * @return the taxonomyLevel2
	 */
	public String getTaxonomyLevel2() {
		return taxonomyLevel2;
	}
	/**
	 * @param taxonomyLevel2 the taxonomyLevel2 to set
	 */
	public void setTaxonomyLevel2(String taxonomyLevel2) {
		this.taxonomyLevel2 = taxonomyLevel2;
	}
	/**
	 * @return the taxonomyLevel3
	 */
	public String getTaxonomyLevel3() {
		return taxonomyLevel3;
	}
	/**
	 * @param taxonomyLevel3 the taxonomyLevel3 to set
	 */
	public void setTaxonomyLevel3(String taxonomyLevel3) {
		this.taxonomyLevel3 = taxonomyLevel3;
	}
	/**
	 * @return the taxonomyLevel4
	 */
	public String getTaxonomyLevel4() {
		return taxonomyLevel4;
	}
	/**
	 * @param taxonomyLevel4 the taxonomyLevel4 to set
	 */
	public void setTaxonomyLevel4(String taxonomyLevel4) {
		this.taxonomyLevel4 = taxonomyLevel4;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Neo4JFileRequest [id=" + id + ", qualityScore=" + qualityScore + ", requirementStatement="
				+ requirementStatement + ", shortCutDocument=" + shortCutDocument + ", taxonomyLevel1=" + taxonomyLevel1
				+ ", taxonomyLevel2=" + taxonomyLevel2 + ", taxonomyLevel3=" + taxonomyLevel3 + ", taxonomyLevel4="
				+ taxonomyLevel4 + "]";
	}	
	
}
