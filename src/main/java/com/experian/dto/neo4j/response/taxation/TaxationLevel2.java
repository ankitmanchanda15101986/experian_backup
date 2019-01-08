package com.experian.dto.neo4j.response.taxation;

import java.util.List;

public class TaxationLevel2 {
	
	private String taxation_level_2;
	private List<TaxationLevel3> level3;
	/**
	 * @return the taxation_level_2
	 */
	public String getTaxation_level_2() {
		return taxation_level_2;
	}
	/**
	 * @param taxation_level_2 the taxation_level_2 to set
	 */
	public void setTaxation_level_2(String taxation_level_2) {
		this.taxation_level_2 = taxation_level_2;
	}
	/**
	 * @return the level3
	 */
	public List<TaxationLevel3> getLevel3() {
		return level3;
	}
	/**
	 * @param level3 the level3 to set
	 */
	public void setLevel3(List<TaxationLevel3> level3) {
		this.level3 = level3;
	}
	
	

}
