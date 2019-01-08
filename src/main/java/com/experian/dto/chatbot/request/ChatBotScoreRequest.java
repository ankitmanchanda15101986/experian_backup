/**
 * 
 */
package com.experian.dto.chatbot.request;

import java.util.List;

import com.experian.dto.neo4j.response.WordCategory;

/**
 * @author Manchanda's
 *
 */
public class ChatBotScoreRequest {
	private String requirement;
	private List<WordCategory> wordCategory;
	
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
	
	
	
	
}
