package com.experian.dto.neo4j.response.taxation;

import java.util.List;

public class TaxationLevel3 {
	
	private Integer id;
	private String level;
	private List<TaxationLevel4> taxonomyLevel4POJOs;
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
	 * @return the taxonomyLevel4POJOs
	 */
	public List<TaxationLevel4> getTaxonomyLevel4POJOs() {
		return taxonomyLevel4POJOs;
	}
	/**
	 * @param taxonomyLevel4POJOs the taxonomyLevel4POJOs to set
	 */
	public void setTaxonomyLevel4POJOs(List<TaxationLevel4> taxonomyLevel4POJOs) {
		this.taxonomyLevel4POJOs = taxonomyLevel4POJOs;
	}
	
	
}
