package com.depositsolutions.posintegration.enums.api;

import lombok.Getter;

@Getter
public enum MailHogResource {

	MESSAGES("/messages");
	
	private String path;
	
	private MailHogResource(String path) {
		this.path = path;
	}
}
