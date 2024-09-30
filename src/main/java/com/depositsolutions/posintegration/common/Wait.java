package com.depositsolutions.posintegration.common;

import static org.awaitility.Awaitility.with;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Wait {
	private static final long DELAY_IN_SECONDS = 1L;
	
	private static final long POLL_INTERVAL_IN_SECONDS = 2L;
	
	public static void until(Callable<Boolean> conditionToBeMet) {
		until(DELAY_IN_SECONDS, POLL_INTERVAL_IN_SECONDS, conditionToBeMet);
	}
	
	public static void until(long delay, long pollInterval, Callable<Boolean> conditionToBeMet) {
		with().pollDelay(delay, TimeUnit.SECONDS).and().pollInterval(pollInterval, TimeUnit.SECONDS).await().until(conditionToBeMet);
	}
}
