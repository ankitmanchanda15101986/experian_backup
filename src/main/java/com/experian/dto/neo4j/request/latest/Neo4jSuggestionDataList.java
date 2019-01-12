/**
 * 
 */
package com.experian.dto.neo4j.request.latest;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class Neo4jSuggestionDataList {

	private List<Neo4jSuggestionData> suggestion;
	private String statement;
	private String level1;
	private String level2;

	/**
	 * @return the suggestion
	 */
	public List<Neo4jSuggestionData> getSuggestion() {
		return suggestion;
	}

	/**
	 * @param suggestion the suggestion to set
	 */
	public void setSuggestion(List<Neo4jSuggestionData> suggestion) {
		this.suggestion = suggestion;
	}

	/**
	 * @return the statement
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * @param statement the statement to set
	 */
	public void setStatement(String statement) {
		this.statement = statement;
	}

	/**
	 * @return the level1
	 */
	public String getLevel1() {
		return level1;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	/**
	 * @return the level2
	 */
	public String getLevel2() {
		return level2;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}	
}
