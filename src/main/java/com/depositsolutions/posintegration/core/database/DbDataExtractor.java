package com.depositsolutions.posintegration.core.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sql2o.Connection;
import org.sql2o.Query;

public class DbDataExtractor {
	private DbManager dbManager;
	private Connection connection = null;

	public DbDataExtractor() {
		this.dbManager = new DbManager();
	}

	/**
	 * Fetches all data specified by the given SELECT query from DB
	 * 
	 * @param queryText
	 * @return All db records as list of key - value pairs where key is column name
	 *         and value is corresponding column value
	 */
	public List<Map<String, Object>> getAllData(String queryText) {
		openConnection();
		Query query = connection.createQuery(queryText);
		List<Map<String, Object>> data = query.executeAndFetchTable().asList();
		closeConnection();

		return data;
	}

	public <T> List<T> getAllRecords(String queryText, Class<T> returnedObject) {
		openConnection();
		Query query = connection.createQuery(queryText);
		List<T> object = query.executeAndFetch(returnedObject);
		closeConnection();

		return object;
	}
	
	public <T> T getSingleRecord(String queryText, Class<T> returnedObject) {
		return getAllRecords(queryText, returnedObject).get(0);
	}
	
	public Map<String, Object> getRecordByCriteria(String queryText, Map<String, Object> searchCriteria) {
		List<Map<String, Object>> allRecords = getAllData(queryText);
		Map<String, Object> record = new HashMap<String, Object>();

		for (Map<String, Object> singleRecord : allRecords) {
			if (singleRecord.entrySet().containsAll(searchCriteria.entrySet())) {
				record = new HashMap<String, Object>(singleRecord);
			}
		}

		return record;
	}

	private Connection openConnection() {
		if (connection == null) {
			connection = dbManager.sql2o.open();
		}

		return connection;
	}

	private void closeConnection() {
		if (connection != null) {
			connection.close();
		}
	}
}
