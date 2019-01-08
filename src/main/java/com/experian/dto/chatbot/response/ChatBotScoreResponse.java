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
	
	private List<ChatBotScore> response;

	/**
	 * @return the response
	 */
	public List<ChatBotScore> getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(List<ChatBotScore> response) {
		this.response = response;
	}
	
	
}
