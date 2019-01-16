/**
 * 
 */
package com.experian.dto.neo4j.finalResponse;

import java.util.Set;

import com.experian.dto.neo4j.request.Neo4jDocumentRequest;
import com.experian.dto.neo4j.request.WordCount;

/**
 * @author manchanda.a
 *
 */
public class FinalResponse {

	private Neo4jDocumentRequest document;
	private String requirementElaboration;
	private Double qualityScore;
	private TaxonomyLevel1 taxonomyLevel1;
	private String level1;
	private TaxonomyLevel2 taxonomyLevel2;
	private String level2;
	private TaxonomyLevel3 taxonomyLevel3;
	private String level3;
	private TaxonomyLevel4 taxonomyLevel4;
	private String level4;
	private String requirementStatement;
	private Set<WordCount> wordCategoryCount;
	
	public Neo4jDocumentRequest getDocument() {
		return document;
	}
	public void setDocument(Neo4jDocumentRequest document) {
		this.document = document;
	}
	public String getRequirementElaboration() {
		return requirementElaboration;
	}
	public void setRequirementElaboration(String requirementElaboration) {
		this.requirementElaboration = requirementElaboration;
	}
	public Double getQualityScore() {
		return qualityScore;
	}
	public void setQualityScore(Double qualityScore) {
		this.qualityScore = qualityScore;
	}
	public TaxonomyLevel1 getTaxonomyLevel1() {
		return taxonomyLevel1;
	}
	public void setTaxonomyLevel1(TaxonomyLevel1 taxonomyLevel1) {
		this.taxonomyLevel1 = taxonomyLevel1;
	}
	public String getLevel1() {
		return level1;
	}
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
	public TaxonomyLevel2 getTaxonomyLevel2() {
		return taxonomyLevel2;
	}
	public void setTaxonomyLevel2(TaxonomyLevel2 taxonomyLevel2) {
		this.taxonomyLevel2 = taxonomyLevel2;
	}
	public String getLevel2() {
		return level2;
	}
	public void setLevel2(String level2) {
		this.level2 = level2;
	}
	public TaxonomyLevel3 getTaxonomyLevel3() {
		return taxonomyLevel3;
	}
	public void setTaxonomyLevel3(TaxonomyLevel3 taxonomyLevel3) {
		this.taxonomyLevel3 = taxonomyLevel3;
	}
	public String getLevel3() {
		return level3;
	}
	public void setLevel3(String level3) {
		this.level3 = level3;
	}
	public TaxonomyLevel4 getTaxonomyLevel4() {
		return taxonomyLevel4;
	}
	public void setTaxonomyLevel4(TaxonomyLevel4 taxonomyLevel4) {
		this.taxonomyLevel4 = taxonomyLevel4;
	}
	public String getLevel4() {
		return level4;
	}
	public void setLevel4(String level4) {
		this.level4 = level4;
	}
	public String getRequirementStatement() {
		return requirementStatement;
	}
	public void setRequirementStatement(String requirementStatement) {
		this.requirementStatement = requirementStatement;
	}
	public Set<WordCount> getWordCategoryCount() {
		return wordCategoryCount;
	}
	public void setWordCategoryCount(Set<WordCount> wordCategoryCount) {
		this.wordCategoryCount = wordCategoryCount;
	}	
}
