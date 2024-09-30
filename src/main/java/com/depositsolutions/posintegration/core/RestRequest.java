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

	public Response uploadFile(String controlName, String file, Map<String, Object> pathParameters, String resourcePath) {
		requestSpecification
		.multiPart(controlName, FileUtil.getFileByName(file))
		.pathParams(pathParameters);

		return post(resourcePath);
	}

	public <T> Response sendPostRequest(Map<String, Object> pathParameters, T bodyObject, String resourcePath) {
		requestSpecification
		.pathParams(pathParameters)
		.body(bodyObject);

		return post(resourcePath);
	}

	public <T> Response sendPostRequest(Map<String, Object> pathParameters, List<T> bodyObjects, String resourcePath) {
		requestSpecification
		.pathParams(pathParameters)
		.contentType(ContentType.JSON)
		.body(bodyObjects);

		return post(resourcePath);
	}

	public Response sendPostRequestWithQueryParams(Map<String, Object> queryParameters, String resourcePath) {
		requestSpecification
		.queryParams(queryParameters);

		return post(resourcePath);
	}
	
	public Response get(String resourcePath) {
		return requestSpecification.log().all().get(resourcePath);
	}

	public Response post(String resourcePath) {
		return requestSpecification.log().all().post(resourcePath);
	}
}
