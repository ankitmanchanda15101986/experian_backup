/**
 * 
 */
package com.experian.dto.neo4j.request;

/**
 * @author manchanda.a
 *
 */
public class Neo4jDocumentRequest {

	private String documentPurpose;
	private MetaDataRequest metaData;
	private String requirementElaboration;
	/**
	 * @return the documentPurpose
	 */
	public String getDocumentPurpose() {
		return documentPurpose;
	}
	/**
	 * @param documentPurpose the documentPurpose to set
	 */
	public void setDocumentPurpose(String documentPurpose) {
		this.documentPurpose = documentPurpose;
	}
	/**
	 * @return the requirementElaboration
	 */
	public String getRequirementElaboration() {
		return requirementElaboration;
	}
	/**
	 * @param requirementElaboration the requirementElaboration to set
	 */
	public void setRequirementElaboration(String requirementElaboration) {
		this.requirementElaboration = requirementElaboration;
	}
	/**
	 * @return the metaData
	 */
	public MetaDataRequest getMetaData() {
		return metaData;
	}
	/**
	 * @param metaData the metaData to set
	 */
	public void setMetaData(MetaDataRequest metaData) {
		this.metaData = metaData;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Neo4jDocumentRequest [documentPurpose=" + documentPurpose + ", requirementElaboration="
				+ requirementElaboration + ", metaData=" + metaData + "]";
	}
	
	
}
