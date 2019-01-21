/**
 * 
 */
package com.experian.dto.neo4j.brd;

import com.experian.dto.neo4j.finalResponse.FinalResponse;
import com.experian.dto.neo4j.request.Neo4jDocumentRequest;

/**
 * @author manchanda.a
 *
 */
public class RequirementBasedOnElaborationResponse {

	private Integer documentInfoEntityId;
	private Neo4jDocumentRequest documentInfoNode;
	private Integer requirementStatementId;
	private FinalResponse requirementStatementNode;
	/**
	 * @return the documentInfoEntityId
	 */
	public Integer getDocumentInfoEntityId() {
		return documentInfoEntityId;
	}
	/**
	 * @param documentInfoEntityId the documentInfoEntityId to set
	 */
	public void setDocumentInfoEntityId(Integer documentInfoEntityId) {
		this.documentInfoEntityId = documentInfoEntityId;
	}
	/**
	 * @return the documentInfoNode
	 */
	public Neo4jDocumentRequest getDocumentInfoNode() {
		return documentInfoNode;
	}
	/**
	 * @param documentInfoNode the documentInfoNode to set
	 */
	public void setDocumentInfoNode(Neo4jDocumentRequest documentInfoNode) {
		this.documentInfoNode = documentInfoNode;
	}
	/**
	 * @return the requirementStatementId
	 */
	public Integer getRequirementStatementId() {
		return requirementStatementId;
	}
	/**
	 * @param requirementStatementId the requirementStatementId to set
	 */
	public void setRequirementStatementId(Integer requirementStatementId) {
		this.requirementStatementId = requirementStatementId;
	}
	/**
	 * @return the requirementStatementNode
	 */
	public FinalResponse getRequirementStatementNode() {
		return requirementStatementNode;
	}
	/**
	 * @param requirementStatementNode the requirementStatementNode to set
	 */
	public void setRequirementStatementNode(FinalResponse requirementStatementNode) {
		this.requirementStatementNode = requirementStatementNode;
	}
	
	
}
