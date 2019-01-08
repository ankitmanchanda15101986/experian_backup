/**
 * 
 */
package com.experian.dto.neo4j.request;

import com.experian.dto.neo4j.request.WordCountRequest;

/**
 * @author manchanda.a
 *
 */
public class Neo4JFileRequest {

	private double qualityScore;
	private String taxonomy_Level_1;
	private String taxonomy_Level_2;
	private String taxonomy_Level_3;
	private String taxonomy_Level_4;
	private String requirementStatement;
	private WordCountRequest wordCount;
	
	public double getQualityScore() {
		return qualityScore;
	}
	public void setQualityScore(double qualityScore) {
		this.qualityScore = qualityScore;
	}				
	public String getTaxonomy_Level_1() {
		return taxonomy_Level_1;
	}
	public void setTaxonomy_Level_1(String taxonomy_Level_1) {
		this.taxonomy_Level_1 = taxonomy_Level_1;
	}
	public String getTaxonomy_Level_2() {
		return taxonomy_Level_2;
	}
	public void setTaxonomy_Level_2(String taxonomy_Level_2) {
		this.taxonomy_Level_2 = taxonomy_Level_2;
	}
	public String getTaxonomy_Level_3() {
		return taxonomy_Level_3;
	}
	public void setTaxonomy_Level_3(String taxonomy_Level_3) {
		this.taxonomy_Level_3 = taxonomy_Level_3;
	}
	public String getTaxonomy_Level_4() {
		return taxonomy_Level_4;
	}
	public void setTaxonomy_Level_4(String taxonomy_Level_4) {
		this.taxonomy_Level_4 = taxonomy_Level_4;
	}
	public String getRequirementStatement() {
		return requirementStatement;
	}
	public void setRequirementStatement(String requirementStatement) {
		this.requirementStatement = requirementStatement;
	}
	
	/**
	 * @return the wordCount
	 */
	public WordCountRequest getWordCount() {
		return wordCount;
	}
	/**
	 * @param wordCount the wordCount to set
	 */
	public void setWordCount(WordCountRequest wordCount) {
		this.wordCount = wordCount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(qualityScore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((requirementStatement == null) ? 0 : requirementStatement.hashCode());
		result = prime * result + ((taxonomy_Level_1 == null) ? 0 : taxonomy_Level_1.hashCode());
		result = prime * result + ((taxonomy_Level_2 == null) ? 0 : taxonomy_Level_2.hashCode());
		result = prime * result + ((taxonomy_Level_3 == null) ? 0 : taxonomy_Level_3.hashCode());
		result = prime * result + ((taxonomy_Level_4 == null) ? 0 : taxonomy_Level_4.hashCode());
		result = prime * result + ((wordCount == null) ? 0 : wordCount.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Neo4JFileRequest other = (Neo4JFileRequest) obj;
		if (Double.doubleToLongBits(qualityScore) != Double.doubleToLongBits(other.qualityScore))
			return false;
		if (requirementStatement == null) {
			if (other.requirementStatement != null)
				return false;
		} else if (!requirementStatement.equals(other.requirementStatement))
			return false;
		if (taxonomy_Level_1 == null) {
			if (other.taxonomy_Level_1 != null)
				return false;
		} else if (!taxonomy_Level_1.equals(other.taxonomy_Level_1))
			return false;
		if (taxonomy_Level_2 == null) {
			if (other.taxonomy_Level_2 != null)
				return false;
		} else if (!taxonomy_Level_2.equals(other.taxonomy_Level_2))
			return false;
		if (taxonomy_Level_3 == null) {
			if (other.taxonomy_Level_3 != null)
				return false;
		} else if (!taxonomy_Level_3.equals(other.taxonomy_Level_3))
			return false;
		if (taxonomy_Level_4 == null) {
			if (other.taxonomy_Level_4 != null)
				return false;
		} else if (!taxonomy_Level_4.equals(other.taxonomy_Level_4))
			return false;
		if (wordCount == null) {
			if (other.wordCount != null)
				return false;
		} else if (!wordCount.equals(other.wordCount))
			return false;
		return true;
	}
	
	
}
