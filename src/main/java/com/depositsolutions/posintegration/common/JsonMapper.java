package com.depositsolutions.posintegration.common;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> T convertJsonToObject(String fileName, Class<T> clazz) {
		T retunedObject = null;
		try {
			retunedObject = mapper.readValue(FileUtil.getFileByName(fileName), clazz);
		} catch (JsonParseException e) {
			LoggerUtil.logErrorMessage(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LoggerUtil.logErrorMessage(e.getMessage(), e);
		} catch (IOException e) {
			LoggerUtil.logErrorMessage(e.getMessage(), e);
		}
		
		return retunedObject;
	}
}
