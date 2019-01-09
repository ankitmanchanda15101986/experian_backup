/**
 * 
 */
package com.experian.dto.chatbot.response;

/**
 * @author Manchanda's
 *
 */
public class ChatBotScore {

	private String category;
	private String word;
	private Integer cnt;
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}
	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * @return the cnt
	 */
	public Integer getCnt() {
		return cnt;
	}
	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	
	
}
