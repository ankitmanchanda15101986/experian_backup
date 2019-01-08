/**
 * 
 */
package com.experian.dto.aiml.response;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class AimlQualityScoreResponse {
	
	private List<AimlQualityScore> qualityScore;

	/**
	 * @return the qualityScore
	 */
	public List<AimlQualityScore> getQualityScore() {
		return qualityScore;
	}

	/**
	 * @param qualityScore the qualityScore to set
	 */
	public void setQualityScore(List<AimlQualityScore> qualityScore) {
		this.qualityScore = qualityScore;
	}


	
	

}
