package com.experian.dto.neo4j.request;

import java.util.List;

public class WordCountRequest {
	
	private List<WordCount> wordCount;

	/**
	 * @return the wordCount
	 */
	public List<WordCount> getWordCount() {
		return wordCount;
	}

	/**
	 * @param wordCount the wordCount to set
	 */
	public void setWordCount(List<WordCount> wordCount) {
		this.wordCount = wordCount;
	}
	
	
}
