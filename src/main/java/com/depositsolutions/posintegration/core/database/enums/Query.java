package com.depositsolutions.posintegration.core.database.enums;

public enum Query {

	// table t_pos
	SELECT_POS_COLUMN_BY_IDENTIFIER("select %s from public.t_pos where identifier = '%s' and is_active = true"),
	
	SELECT_POS_BY_IDENTIFIER("select identifier, name, solution_info, bic, iban, authentication_key from public.t_pos where identifier = '%s' and is_active = true"),
	
	SELECT_FIRST_ACTIVE_RECORD_FROM_TABLE("select * from public.%s where is_active = true limit 1"),
	
	SELECT_ALL_RECORDS_FROM_TABLE("select * from public.%s");
	
	private String query;
	
	private Query(String query) {
		this.query = query;
	}
	
	public String getQuery(Object...parameters) {
		return String.format(query, parameters);
	}
}
