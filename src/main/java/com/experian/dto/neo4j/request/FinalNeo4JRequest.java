package com.experian.dto.neo4j.request;

import java.util.List;

public class FinalNeo4JRequest {

	private Neo4jDocumentRequest documentRequest;
	private List<Neo4JFileRequest> statementModels;
	
	
	/**
	 * @return the documentRequest
	 */
	public Neo4jDocumentRequest getDocumentRequest() {
		return documentRequest;
	}

	/**
	 * @param documentRequest the documentRequest to set
	 */
	public void setDocumentRequest(Neo4jDocumentRequest documentRequest) {
		this.documentRequest = documentRequest;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FinalNeo4JRequest [documentRequest=" + documentRequest + ", statementModels=" + statementModels + "]";
	}
	
}
