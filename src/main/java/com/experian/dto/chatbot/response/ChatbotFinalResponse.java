/**
 * 
 */
package com.experian.dto.chatbot.response;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class ChatbotFinalResponse {

	private Integer qualityScore;
	private List<ChatBotScore> wordCount;
	/**
	 * @return the qualityScore
	 */
	public Integer getQualityScore() {
		return qualityScore;
	}
	/**
	 * @param qualityScore the qualityScore to set
	 */
	public void setQualityScore(Integer qualityScore) {
		this.qualityScore = qualityScore;
	}
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
