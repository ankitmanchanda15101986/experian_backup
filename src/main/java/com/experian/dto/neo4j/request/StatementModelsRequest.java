/**
 * 
 */
package com.experian.dto.neo4j.request;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class StatementModelsRequest {

	private List<Neo4JFileRequest> statementModels;

	/**
	 * @return the statementModels
	 */
	public List<Neo4JFileRequest> getStatementModels() {
		return statementModels;
	}

	/**
	 * @param statementModels the statementModels to set
	 */
	public void setStatementModels(List<Neo4JFileRequest> statementModels) {
		this.statementModels = statementModels;
	}
	
}
