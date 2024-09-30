package com.depositsolutions.posintegration.objects;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	@CsvBindByPosition(position = 0)
	private String customerReference;

	@CsvBindByPosition(position = 1)
	private String productBankBic;
	
	@CsvBindByPosition(position = 2)
	private String productId;

	@CsvBindByPosition(position = 3)
	private String amount;
	
	@CsvBindByPosition(position = 4)
	private String currency;
	
	@Override
	public String toString() {
		String info = String.format("Customer Reference: %s; Product Bank BIC: %s; Product Id: %s; Amount: %s; Currency: %s",
				this.customerReference, this.productBankBic, this.productId, this.amount, this.currency);

		return info;
	}
}
