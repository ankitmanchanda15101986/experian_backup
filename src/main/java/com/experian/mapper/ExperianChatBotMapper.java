/**
 * 
 */
package com.experian.mapper;

import org.springframework.stereotype.Component;

import com.experian.dto.aiml.response.AimlQualityScoreResponse;
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
	 * @param aimlQualityScoreResponse
	 * @param chatBotScoreResponse
	 * @return
	 */
	public ChatbotFinalResponse createChatBotResponseToIncludeQualityResponse(AimlQualityScoreResponse aimlQualityScoreResponse,
			ChatBotScoreResponse chatBotScoreResponse) {
		ChatbotFinalResponse response = new ChatbotFinalResponse();
		System.out.println("aimlQualityScoreResponse : "+aimlQualityScoreResponse.getQualityScore().size());
		System.out.println("chatBotScoreResponse : "+chatBotScoreResponse.getWordCount().size());
		if(aimlQualityScoreResponse != null && chatBotScoreResponse != null) {
			response.setQualityScore(aimlQualityScoreResponse.getQualityScore().get(0).getQualityScore());
			response.setWordCount(chatBotScoreResponse.getWordCount());
		}
		return response;
	}

}
