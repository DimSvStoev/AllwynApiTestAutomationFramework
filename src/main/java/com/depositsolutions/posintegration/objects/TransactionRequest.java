package com.depositsolutions.posintegration.objects;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TransactionRequest {
	private String ordersBatchId;
	private String productIdentifier;
	private String productBankBic;
	private String requestType;
	private BigDecimal amount;
	private String currency;
	private String status;
	private String incidentId;

	@Override
	public String toString() {
		String info = String.format(
				"Orders batch id: %s; Product Id: %s; Product Bank BIC: %s; Request Type: %s; Amount: %s; Currency: %s; Status: %s; Incident Id: %s",
				this.ordersBatchId, this.productIdentifier, this.productBankBic, this.requestType, this.amount.toString(),
				this.currency, this.status, this.incidentId);

		return info;
	}
}
