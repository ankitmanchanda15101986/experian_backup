/**
 * 
 */
package com.experian.dto.chatbot.response;

import java.util.List;


/**
 * @author Manchanda's
 *
 */
public class ChatBotScoreResponse {
	
	private List<ChatBotScore> wordCount;

	/**
	 * @return the wordCount
	 */
	public List<ChatBotScore> getWordCount() {
		return wordCount;
	}

	/**
	 * @param wordCount the wordCount to set
	 */
	public void setWordCount(List<ChatBotScore> wordCount) {
		this.wordCount = wordCount;
	}

	
}
