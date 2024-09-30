package com.depositsolutions.posintegration.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class DateUtil {
	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public enum DateFormat {
		YYYY_MM_DD("yyyy-MM-dd");

		private String format;
	}
	
	public static String formatToString(LocalDateTime date, DateFormat format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format.getFormat());
		String formattedDate = date.format(formatter);
		
		return formattedDate;
	}
}
