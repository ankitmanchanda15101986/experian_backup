/**
 * 
 */
package com.experian.dto.neo4j.finalResponse;

import java.util.Set;

/**
 * @author manchanda.a
 *
 */
public class TaxonomyLevel3 {

	private Long id;
	private String label;
	private Set<TaxonomyLevel4> taxonomyLevel4;
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
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the taxonomyLevel4
	 */
	public Set<TaxonomyLevel4> getTaxonomyLevel4() {
		return taxonomyLevel4;
	}
	/**
	 * @param taxonomyLevel4 the taxonomyLevel4 to set
	 */
	public void setTaxonomyLevel4(Set<TaxonomyLevel4> taxonomyLevel4) {
		this.taxonomyLevel4 = taxonomyLevel4;
	}
	
	
}
