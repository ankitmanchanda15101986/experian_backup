/**
 * 
 */
package com.experian.dto.neo4j.response.taxation;

import java.util.List;

/**
 * @author manchanda.a
 *
 */
public class Taxation {

	private Integer id;
	private String level;
	private List<TaxationLevel2> taxonomyLevel2POJOs;
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
	 * @return the taxonomyLevel2POJOs
	 */
	public List<TaxationLevel2> getTaxonomyLevel2POJOs() {
		return taxonomyLevel2POJOs;
	}
	/**
	 * @param taxonomyLevel2POJOs the taxonomyLevel2POJOs to set
	 */
	public void setTaxonomyLevel2POJOs(List<TaxationLevel2> taxonomyLevel2POJOs) {
		this.taxonomyLevel2POJOs = taxonomyLevel2POJOs;
	}
	
	
}
