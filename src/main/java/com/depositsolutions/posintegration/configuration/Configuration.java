package com.depositsolutions.posintegration.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Configuration {
	private String environment;
	private RestConfiguration rest;
	private RestConfiguration mail;
	private DbConfiguration db;
}
