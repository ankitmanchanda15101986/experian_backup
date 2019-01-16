package com.experian.dto.neo4j.request;

import java.util.Set;

public class WordCount {

	private Long id;
	private String categoryName;
	private int totalCount;
	private Set<Details> details;
	
	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the details
	 */
	public Set<Details> getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(Set<Details> details) {
		this.details = details;
	}
	
}
