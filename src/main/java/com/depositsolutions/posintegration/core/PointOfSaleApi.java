package com.depositsolutions.posintegration.core;

import io.restassured.http.Header;

public class PointOfSaleApi extends BaseRestApi{
	private static final String POS_HEADER = "X-DS-Tenant-Id";
	
	public RestRequest initConnection(String authenticationKey) {
		return initConnection(new Header(POS_HEADER, authenticationKey));
	}
}
