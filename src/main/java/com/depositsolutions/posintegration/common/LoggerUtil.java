package com.depositsolutions.posintegration.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

	public final static Logger LOGGER = LogManager.getLogger();
	
	public static void logInfoMessage(String message) {
		LOGGER.info(message);
	}
	
	public static void logErrorMessage(Throwable error) {
		LOGGER.error(error);
	}
	
	public static void logErrorMessage(String message, Throwable error) {
		LOGGER.error(message, error);
	}	
}
