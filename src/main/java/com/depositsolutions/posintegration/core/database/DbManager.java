package com.depositsolutions.posintegration.core.database;

import java.util.Map;

import org.sql2o.Sql2o;

import com.depositsolutions.posintegration.common.LoggerUtil;
import com.depositsolutions.posintegration.configuration.ConfigurationManager;
import com.depositsolutions.posintegration.configuration.DbConfiguration;
import com.depositsolutions.posintegration.core.database.enums.ParameterName;
import com.google.common.collect.ImmutableMap;

public class DbManager extends ConfigurationManager {
	protected Sql2o sql2o;
	private DbConfiguration dbConfig;

	/**
	 * Used to map DB column names with the corresponding property names of a specified object
	 */
	private static final Map<String, String> COLUMN_MAPPINGS = ImmutableMap.of(
			ParameterName.AUTHENTICATION_KEY.getColumnName(), ParameterName.AUTHENTICATION_KEY.getFieldName(),
			ParameterName.SOLUTION_INFO.getColumnName(), ParameterName.SOLUTION_INFO.getFieldName());

	public DbManager() {
		dbConfig = loadConfigData().getDb();
		loadDbDriver(dbConfig.getClassName());
		sql2o = new Sql2o(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
		sql2o.setDefaultColumnMappings(COLUMN_MAPPINGS);
	}
	
	private void loadDbDriver(String className) {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			LoggerUtil.logErrorMessage(e);
		}
	}
}
