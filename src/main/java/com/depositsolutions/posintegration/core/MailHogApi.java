package com.depositsolutions.posintegration.core;

import com.depositsolutions.posintegration.configuration.RestConfiguration;

public class MailHogApi extends BaseRestApi {
	private RestConfiguration mailConfig = loadConfigData().getMail();
	
	public RestRequest initConnection() {
		return initConnection(mailConfig.getUsername(), mailConfig.getPassword(), mailConfig.getBaseUrl());
	}
}
