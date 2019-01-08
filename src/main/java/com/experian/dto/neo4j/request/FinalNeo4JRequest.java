package com.experian.dto.neo4j.request;

import java.util.List;

import com.experian.dto.neo4j.request.MetaDataRequest;

public class FinalNeo4JRequest {

	private String documentPurpose;
	private String requirementElaboration;
	private MetaDataRequest metaData;
	private List<Neo4JFileRequest> fileRequest;
	
	public String getDocumentPurpose() {
		return documentPurpose;
	}
	
	public void setDocumentPurpose(String documentPurpose) {
		this.documentPurpose = documentPurpose;
	}
	
	public String getRequirementElaboration() {
		return requirementElaboration;
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentPurpose == null) ? 0 : documentPurpose.hashCode());
		result = prime * result + ((fileRequest == null) ? 0 : fileRequest.hashCode());
		result = prime * result + ((metaData == null) ? 0 : metaData.hashCode());
		result = prime * result + ((requirementElaboration == null) ? 0 : requirementElaboration.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FinalNeo4JRequest other = (FinalNeo4JRequest) obj;
		if (documentPurpose == null) {
			if (other.documentPurpose != null)
				return false;
		} else if (!documentPurpose.equals(other.documentPurpose))
			return false;
		if (fileRequest == null) {
			if (other.fileRequest != null)
				return false;
		} else if (!fileRequest.equals(other.fileRequest))
			return false;
		if (metaData == null) {
			if (other.metaData != null)
				return false;
		} else if (!metaData.equals(other.metaData))
			return false;
		if (requirementElaboration == null) {
			if (other.requirementElaboration != null)
				return false;
		} else if (!requirementElaboration.equals(other.requirementElaboration))
			return false;
		return true;
	}	
	
	
}
