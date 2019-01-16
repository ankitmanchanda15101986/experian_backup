/**
 * 
 */
package com.experian.dto.neo4j.finalResponse;

import java.util.Set;

/**
 * @author manchanda.a
 *
 */
public class TaxonomyLevel2 {

	private Long id;
	private String label;
	private Set<TaxonomyLevel3> taxonomyLevel3;
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
	 * @return the taxonomyLevel3
	 */
	public Set<TaxonomyLevel3> getTaxonomyLevel3() {
		return taxonomyLevel3;
	}
	/**
	 * @param taxonomyLevel3 the taxonomyLevel3 to set
	 */
	public void setTaxonomyLevel3(Set<TaxonomyLevel3> taxonomyLevel3) {
		this.taxonomyLevel3 = taxonomyLevel3;
	}
}
