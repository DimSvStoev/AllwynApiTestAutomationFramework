package com.depositsolutions.posintegration.enums.api;

import lombok.Getter;

@Getter
public enum PointOfSaleResource {
	ORDERS_BATCH_PATH("/pos/{posIdentifier}/batches/{batchId}/orders"),
	
	PAYIN_TRANSACTION_PATH("/pos/{posIdentifier}/batches/{batchId}/transactions/payin"),
	
	PAYOUT_TRANSACTION_PATH("/pos/{posIdentifier}/batches/{batchId}/transactions/payout");

	private String path;

	private PointOfSaleResource(String path) {
		this.path = path;
	}
}
