package com.depositsolutions.posintegration.enums.api;

import lombok.Getter;

@Getter
public enum AdminResource {
	ADMIN_POS_IDENTIFIER("/admin/pos/{identifier}"),
	
	ADMIN_POS_IDENTIFIER_CUSTOMER_BALANCES("/admin/pos/{identifier}/customer-balances"),
	
	TRANSACTIONS_VALIDATION_PATH("/admin/transactions/validate");
	
	private String path;
	
	private AdminResource(String path) {
		this.path = path;
	}
}
