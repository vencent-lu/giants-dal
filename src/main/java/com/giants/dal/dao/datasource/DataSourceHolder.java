package com.giants.dal.dao.datasource;

public class DataSourceHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static void setDataSourceKey(String dataSourcekey){
		contextHolder.set(dataSourcekey);
	}
	
	public static String getDataSourceKey(){
		return contextHolder.get();
	}
	
	public static void removeCustType(){
		contextHolder.remove();
	}
}
