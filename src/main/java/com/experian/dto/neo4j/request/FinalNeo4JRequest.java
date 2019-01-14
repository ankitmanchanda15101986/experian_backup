package com.experian.dto.neo4j.request;

import java.util.List;

public class FinalNeo4JRequest {

	private Neo4jDocumentRequest documentRequest;
	private List<Neo4JFileRequest> fileRequest;
	
	
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
	 * @return the fileRequest
	 */
	public List<Neo4JFileRequest> getFileRequest() {
		return fileRequest;
	}

	/**
	 * @param fileRequest the fileRequest to set
	 */
	public void setFileRequest(List<Neo4JFileRequest> fileRequest) {
		this.fileRequest = fileRequest;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FinalNeo4JRequest [fileRequest=" + fileRequest + "]";
	}

	
	
}
