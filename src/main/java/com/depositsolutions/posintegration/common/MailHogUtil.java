package com.depositsolutions.posintegration.common;

import java.util.concurrent.Callable;

import com.depositsolutions.posintegration.core.MailHogApi;
import com.depositsolutions.posintegration.core.RestRequest;
import com.depositsolutions.posintegration.enums.api.MailHogResource;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailHogUtil {
	private MailHogApi mailHogApi;
	
	public int getInitialMessageCount() {
		return getMessagesAsJson().getInt("count");
	}

	public Callable<Boolean> notificationIsReceived(int initialCount) {
		return new Callable<Boolean>() {
			public Boolean call() throws Exception {				
				int currentCount = getMessagesAsJson().getInt("count");

				// The condition that must be fulfilled
				return currentCount == initialCount + 1; 
			}
		};
	}
	
	public JsonPath getMessagesAsJson() {
		RestRequest mailRequest = mailHogApi.initConnection();
		Response mailResponse = mailRequest.get(MailHogResource.MESSAGES.getPath());
		String json = mailResponse.then().extract().response().asString();
		
		return new JsonPath(json);
	}
}
