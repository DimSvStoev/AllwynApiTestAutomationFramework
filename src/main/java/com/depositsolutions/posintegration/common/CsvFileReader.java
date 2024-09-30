package com.depositsolutions.posintegration.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class CsvFileReader {
	public <T> T extractFirstRowWithSkippedColumnNames(String filePath, Class<T> clazz) {
		List<T> allData = null;
		try {
			Reader reader = new FileReader(FileUtil.getFileByName(filePath));
			CsvToBeanBuilder<T> builder = new CsvToBeanBuilder<T>(reader);
			builder.withType(clazz)
			.withSeparator(',')
			.withSkipLines(0);
			
			allData = builder.build().parse();
		} catch (FileNotFoundException e) {
			LoggerUtil.logErrorMessage("Check file path!", e);
		}

		return allData.get(0);
	}
	
	public <T> T extractFirstRow(String filePath, Class<T> clazz) {
		List<T> allData = null;
		try {
			Reader reader = new FileReader(FileUtil.getFileByName(filePath));
			CsvToBeanBuilder<T> builder = new CsvToBeanBuilder<T>(reader)
			.withType(clazz)
			.withSeparator(',');
			
			allData = builder.build().parse();
		} catch (FileNotFoundException e) {
			LoggerUtil.logErrorMessage("Check file path!", e);
		}

		return allData.get(0);
	}
}
