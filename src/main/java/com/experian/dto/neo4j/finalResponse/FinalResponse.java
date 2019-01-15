/**
 * 
 */
package com.experian.dto.neo4j.finalResponse;

import com.experian.dto.neo4j.request.Neo4jDocumentRequest;
import com.experian.dto.neo4j.request.WordCount;

/**
 * @author manchanda.a
 *
 */
public class FinalResponse {

	private Neo4jDocumentRequest document;
	private String requirementElaboration;
	private double qualityScore;
	private TaxonomyLevel1 taxonomyLevel1;
	private String level1;
	private TaxonomyLevel2 taxonomyLevel2;
	private String level2;
	private TaxonomyLevel3 taxonomyLevel3;
	private String level3;
	private String taxonomyLevel4;
	private String level4;
	private String requirementStatement;
	private WordCount wordCategoryCount;
	/**
	 * @return the document
	 */
	public Neo4jDocumentRequest getDocument() {
		return document;
	}
	/**
	 * @param document the document to set
	 */
	public void setDocument(Neo4jDocumentRequest document) {
		this.document = document;
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
	 * @return the taxonomyLevel1
	 */
	public TaxonomyLevel1 getTaxonomyLevel1() {
		return taxonomyLevel1;
	}
	/**
	 * @param taxonomyLevel1 the taxonomyLevel1 to set
	 */
	public void setTaxonomyLevel1(TaxonomyLevel1 taxonomyLevel1) {
		this.taxonomyLevel1 = taxonomyLevel1;
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
	 * @return the taxonomyLevel2
	 */
	public TaxonomyLevel2 getTaxonomyLevel2() {
		return taxonomyLevel2;
	}
	/**
	 * @param taxonomyLevel2 the taxonomyLevel2 to set
	 */
	public void setTaxonomyLevel2(TaxonomyLevel2 taxonomyLevel2) {
		this.taxonomyLevel2 = taxonomyLevel2;
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
	/**
	 * @return the taxonomyLevel3
	 */
	public TaxonomyLevel3 getTaxonomyLevel3() {
		return taxonomyLevel3;
	}
	/**
	 * @param taxonomyLevel3 the taxonomyLevel3 to set
	 */
	public void setTaxonomyLevel3(TaxonomyLevel3 taxonomyLevel3) {
		this.taxonomyLevel3 = taxonomyLevel3;
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
	/**
	 * @return the level4
	 */
	public String getLevel4() {
		return level4;
	}
	/**
	 * @param level4 the level4 to set
	 */
	public void setLevel4(String level4) {
		this.level4 = level4;
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
	 * @return the wordCategoryCount
	 */
	public WordCount getWordCategoryCount() {
		return wordCategoryCount;
	}
	/**
	 * @param wordCategoryCount the wordCategoryCount to set
	 */
	public void setWordCategoryCount(WordCount wordCategoryCount) {
		this.wordCategoryCount = wordCategoryCount;
	}
	
	
	
	
	
}
