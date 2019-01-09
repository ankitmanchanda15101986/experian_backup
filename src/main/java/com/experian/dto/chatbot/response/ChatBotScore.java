/**
 * 
 */
package com.experian.dto.chatbot.response;

/**
 * @author Manchanda's
 *
 */
public class ChatBotScore {

	private String Category;
	private String Word;
	private Integer Count;
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return Category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		Category = category;
	}
	/**
	 * @return the word
	 */
	public String getWord() {
		return Word;
	}
	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		Word = word;
	}
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return Count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		Count = count;
	}	
}
