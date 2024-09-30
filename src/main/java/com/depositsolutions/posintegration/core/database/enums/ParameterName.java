package com.depositsolutions.posintegration.core.database.enums;

import lombok.Getter;

@Getter
public enum ParameterName {
	//table t_pos
	AUTHENTICATION_KEY("authentication_key", "authenticationKey"),
	
	IDENTIFIER("identifier", "identifier"),
	
	NAME("name", "name"),
	
	SOLUTION_INFO("solution_info", "solutionInfo");
	
	private String columnName;
	private String fieldName;
	
	private ParameterName(String columnName,String fieldName) {
		this.columnName = columnName;
		this.fieldName = fieldName;
	}
}
