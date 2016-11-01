/**
 * 
 */
package com.giants.dal.sharding;

import java.io.Serializable;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月15日
 */
public class RoutingResult implements Serializable {

	private static final long serialVersionUID = 2385449402056308794L;
	
	private String dataSource;
	private String tableSuffix;
	
	/**
	 * @return the dataSource
	 */
	public String getDataSource() {
		return dataSource;
	}
	
	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * @return the tableSuffix
	 */
	public String getTableSuffix() {
		return tableSuffix;
	}
	
	/**
	 * @param tableSuffix the tableSuffix to set
	 */
	public void setTableSuffix(String tableSuffix) {
		this.tableSuffix = tableSuffix;
	}

}
