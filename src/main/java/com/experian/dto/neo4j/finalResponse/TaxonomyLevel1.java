/**
 * 
 */
package com.experian.dto.neo4j.finalResponse;

import java.util.Set;

/**
 * @author manchanda.a
 *
 */
public class TaxonomyLevel1 {

	private Long id;
	private String functionalType;
	private String label;
	private Set<TaxonomyLevel2> taxonomyLevel2;
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
	 * @return the functionalType
	 */
	public String getFunctionalType() {
		return functionalType;
	}
	/**
	 * @param functionalType the functionalType to set
	 */
	public void setFunctionalType(String functionalType) {
		this.functionalType = functionalType;
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
	 * @return the taxonomyLevel2
	 */
	public Set<TaxonomyLevel2> getTaxonomyLevel2() {
		return taxonomyLevel2;
	}
	/**
	 * @param taxonomyLevel2 the taxonomyLevel2 to set
	 */
	public void setTaxonomyLevel2(Set<TaxonomyLevel2> taxonomyLevel2) {
		this.taxonomyLevel2 = taxonomyLevel2;
	}	
}
