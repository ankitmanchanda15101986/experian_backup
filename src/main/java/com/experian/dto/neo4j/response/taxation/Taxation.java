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

	private String taxation_level_1;
	private List<TaxationLevel2> level2;
	
	/**
	 * @return the taxation_level_1
	 */
	public String getTaxation_level_1() {
		return taxation_level_1;
	}
	
	/**
	 * @param taxation_level_1 the taxation_level_1 to set
	 */
	public void setTaxation_level_1(String taxation_level_1) {
		this.taxation_level_1 = taxation_level_1;
	}

	/**
	 * @return the level2
	 */
	public List<TaxationLevel2> getLevel2() {
		return level2;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(List<TaxationLevel2> level2) {
		this.level2 = level2;
	}

	
	
}
