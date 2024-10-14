package com.depositsolutions.posintegration.core;

import java.util.List;
import java.util.Map;

import com.depositsolutions.posintegration.common.FileUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestRequest {
	private RequestSpecification requestSpecification;

	public RestRequest(RequestSpecification requestSpecification) {
		this.requestSpecification = requestSpecification;
	}

	public <T> Response sendPostRequestWithBody(T bodyObject, String resourcePath) {
		requestSpecification
				.body(bodyObject)  // Добавяне на тяло на заявката
				.contentType(ContentType.JSON);  // Увери се, че типът на съдържанието е JSON

		return post(resourcePath);  // Извикване на метода за изпращане на POST заявка
	}
	
	public Response get(String resourcePath) {
		return requestSpecification.log().all().get(resourcePath);
	}

	public Response post(String resourcePath) {
		return requestSpecification.log().all().post(resourcePath);
	}

	public <T> Response sendPutRequestWithBody(T bodyObject, String resourcePath) {
		requestSpecification
				.body(bodyObject)
				.contentType(ContentType.JSON);

		return requestSpecification.log().all().put(resourcePath);
	}
	public <T> Response delete(String resourcePath) {
		return requestSpecification.log().all().delete(resourcePath);
	}
}
