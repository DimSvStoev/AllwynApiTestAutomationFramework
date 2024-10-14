package com.depositsolutions.posintegration.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestConfiguration {
	private String protocol;
	private String host;
	private String basePath;

	public String getBaseUrl() {
		return protocol + "://" + host + basePath;
	}
}
