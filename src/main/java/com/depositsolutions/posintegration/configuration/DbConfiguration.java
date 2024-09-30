package com.depositsolutions.posintegration.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DbConfiguration {
	private String url;
	private String username;
	private String password;
	private String className;
}
