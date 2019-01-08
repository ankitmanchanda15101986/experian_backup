package com.experian.dto.neo4j.response.taxation;

import java.util.List;

public class TaxationLevel3 {
	
	private String taxation_level_3;
	private List<TaxationLevel4> level4;
	/**
	 * @return the taxation_level_3
	 */
	public String getTaxation_level_3() {
		return taxation_level_3;
	}
	/**
	 * @param taxation_level_3 the taxation_level_3 to set
	 */
	public void setTaxation_level_3(String taxation_level_3) {
		this.taxation_level_3 = taxation_level_3;
	}
	/**
	 * @return the level4
	 */
	public List<TaxationLevel4> getLevel4() {
		return level4;
	}
	/**
	 * @param level4 the level4 to set
	 */
	public void setLevel4(List<TaxationLevel4> level4) {
		this.level4 = level4;
	}
	
	
}
