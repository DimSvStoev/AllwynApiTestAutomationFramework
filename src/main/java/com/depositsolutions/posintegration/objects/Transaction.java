package com.depositsolutions.posintegration.objects;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Transaction {
	private BigDecimal amount;
	private String currency;
	private String productIdentifier;
	private String transferDate;

	@Override
	public String toString() {
		String info = String.format("Amount: %s; Currency: %s; Product Id: %s; Transfer Date: %s;", this.amount,
				this.currency, this.productIdentifier, this.transferDate);

		return info;
	}
}
