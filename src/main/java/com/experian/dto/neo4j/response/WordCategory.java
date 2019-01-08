/**
 * 
 */
package com.experian.dto.neo4j.response;

import java.util.Arrays;

/**
 * @author manchanda.a
 *
 */
public class WordCategory {

	private int id;
	private String categoryName;
	private String[] categoryValues;
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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
	 * @return the categoryValues
	 */
	public String[] getCategoryValues() {
		return categoryValues;
	}
	/**
	 * @param categoryValues the categoryValues to set
	 */
	public void setCategoryValues(String[] categoryValues) {
		this.categoryValues = categoryValues;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WordCategory [id=" + id + ", categoryName=" + categoryName + ", categoryValues="
				+ Arrays.toString(categoryValues) + ", getId()=" + getId() + ", getCategoryName()=" + getCategoryName()
				+ ", getCategoryValues()=" + Arrays.toString(getCategoryValues()) + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
