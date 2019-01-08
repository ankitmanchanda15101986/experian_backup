package com.experian.dto.neo4j.response.taxation;

import java.util.List;

public class TaxationLevel2 {
	
	private Integer id;
	private String level;
	private List<TaxationLevel3> taxonomyLevel3POJOs;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the taxonomyLevel3POJOs
	 */
	public List<TaxationLevel3> getTaxonomyLevel3POJOs() {
		return taxonomyLevel3POJOs;
	}
	/**
	 * @param taxonomyLevel3POJOs the taxonomyLevel3POJOs to set
	 */
	public void setTaxonomyLevel3POJOs(List<TaxationLevel3> taxonomyLevel3POJOs) {
		this.taxonomyLevel3POJOs = taxonomyLevel3POJOs;
	}
	
	
}
