/**
 * 
 */
package com.experian.dto.aiml.response;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class AimlFileFinalResponse {
	
	private List<AimlFileResponse> response;

	/**
	 * @return the response
	 */
	public List<AimlFileResponse> getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(List<AimlFileResponse> response) {
		this.response = response;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AimlFileFinalResponse [response=" + response + ", getResponse()=" + getResponse() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
