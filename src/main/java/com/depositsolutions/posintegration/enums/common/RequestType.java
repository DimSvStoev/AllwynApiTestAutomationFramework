package com.depositsolutions.posintegration.enums.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RequestType {
	PAY_IN("PayIn"), 
	
	PAY_OUT("PayOut");

	private String type;
}
