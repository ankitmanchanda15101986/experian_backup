/**
 * 
 */
package com.experian.dto.neo4j.response.wordCategory;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class WordCategoryResponse {

	private List<WordCategory> wordCategory;

	
	
	/**
	 * @return the wordCategory
	 */
	public List<WordCategory> getWordCategory() {
		return wordCategory;
	}



	/**
	 * @param wordCategory the wordCategory to set
	 */
	public void setWordCategory(List<WordCategory> wordCategory) {
		this.wordCategory = wordCategory;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WordCategoryResponse [response=" + wordCategory + ", getResponse()=" + getWordCategory() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
