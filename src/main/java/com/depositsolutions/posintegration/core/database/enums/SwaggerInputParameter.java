package com.depositsolutions.posintegration.core.database.enums;

import lombok.Getter;

@Getter
public enum SwaggerInputParameter {
	BATCH_ID("batchId"),
	
	CUSTOMER_REFERENCE("customerReference"),
	
	DATE("date"),
	
	INPUT_FILE("inputFile"),
	
	POS_IDENTIFIER("posIdentifier");
	
	private String name;
	
	private SwaggerInputParameter(String name) {
		this.name = name;
	}
}
