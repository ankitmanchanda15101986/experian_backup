/**
 * 
 */
package com.experian.mapper;

import org.springframework.stereotype.Component;

import com.experian.dto.aiml.response.AimlQualityScore;
import com.experian.dto.chatbot.response.ChatBotScoreResponse;
import com.experian.dto.chatbot.response.ChatbotFinalResponse;

/**
 * @author manchanda.a
 *
 */
@Component
public class ExperianChatBotMapper {
	
	/**
	 * This method will create chat bot response based on quality score and word count.
	 * @param aimlQualityScore
	 * @param chatBotScoreResponse
	 * @return
	 */
	public ChatbotFinalResponse createChatBotResponseToIncludeQualityResponse(AimlQualityScore aimlQualityScore,
			ChatBotScoreResponse chatBotScoreResponse) {
		ChatbotFinalResponse response = new ChatbotFinalResponse();
		if(aimlQualityScore != null && chatBotScoreResponse != null) {
			response.setQualityScore(aimlQualityScore.getQualityScore());
			response.setWordCount(chatBotScoreResponse.getWordCount());
		}
		return response;
	}

}
