package com.depositsolutions.posintegration.common;

import static org.assertj.core.api.Assertions.*;

public class AssertionUtil {

	public void verifyIfEquals(String formattedMessage, Object actual, Object expected) {
		assertThat(actual)
		.as(formattedMessage)
		.isEqualTo(expected);
	}
	
	public void verifyIfContains(String formattedMessage, String actual, String expected) {
		assertThat(actual)
		.as(formattedMessage)
		.contains(expected);
	}
	
	public void verifyIfNotNull(String formattedMessage, Object actual) {
		assertThat(actual)
		.as(formattedMessage)
		.isNotNull();
	}
	
}
