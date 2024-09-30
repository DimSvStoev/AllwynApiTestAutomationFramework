package com.depositsolutions.posintegration.objects;

import com.opencsv.bean.CsvBindByName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointOfSale {

	private String authenticationKey;
	
	private String identifier;

	@CsvBindByName(column = "bic")
	private String bic;

	@CsvBindByName(column = "iban")
	private String iban;

	@CsvBindByName(column = "name")
	private String name;

	@CsvBindByName(column = "solution info")
	private String solutionInfo;

	@Override
	public String toString() {
		String info = String.format("Identifier: %s; Auth key: %s; BIC: %s; IBAN: %s; Name: %s; Solution info: %s", this.identifier,
				this.authenticationKey, this.bic, this.iban, this.name, this.solutionInfo);

		return info;
	}
}
