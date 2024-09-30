package com.depositsolutions.posintegration.core;

import com.depositsolutions.posintegration.configuration.ConfigurationManager;
import com.depositsolutions.posintegration.configuration.RestConfiguration;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

public abstract class BaseRestApi extends ConfigurationManager {
	private RestConfiguration restConfig = loadConfigData().getRest();
	
	public RestRequest initConnection() {
		return initConnection(restConfig.getUsername(), restConfig.getPassword(), restConfig.getBaseUrl());
	}
	
	public RestRequest initConnection(Header header) {
		return initConnection(restConfig.getUsername(), restConfig.getPassword(), restConfig.getBaseUrl(), header);
	}
	
	public RestRequest initConnection(String username, String password, String baseUrl) {
		 RequestSpecification requestSpecification = RestAssured.given()
				.auth().basic(username, password)
				.baseUri(baseUrl).log().all();
		 
		 return new RestRequest(requestSpecification);
	}
	
	public RestRequest initConnection(String username, String password, String baseUrl, Header header) {
		 RequestSpecification requestSpecification = RestAssured.given()
				.auth().basic(username, password)
				.baseUri(baseUrl)
				.header(header)
				.log().all();
		 
		 return new RestRequest(requestSpecification);
	}
}
