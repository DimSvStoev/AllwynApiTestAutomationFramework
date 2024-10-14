package com.depositsolutions.posintegration.common;

import com.depositsolutions.posintegration.configuration.Configuration;
import com.depositsolutions.posintegration.configuration.ConfigurationManager;
import com.depositsolutions.posintegration.configuration.RestConfiguration;
import com.depositsolutions.posintegration.core.RestRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;


public class BaseRestApi extends ConfigurationManager {
	protected RestRequest restRequest;
	protected Configuration configuration;
	protected RestConfiguration restConfig;
	protected RequestSpecification requestSpecification;

	public void init() {
		configuration = loadConfigData();
		restConfig = configuration.getRest();
		requestSpecification = createRequestSpecification(restConfig.getBaseUrl());
		restRequest = new RestRequest(requestSpecification);
	}

	private RequestSpecification createRequestSpecification(String baseUrl) {
		return RestAssured.given()
				.baseUri(baseUrl)
				.log().all();
	}
}
