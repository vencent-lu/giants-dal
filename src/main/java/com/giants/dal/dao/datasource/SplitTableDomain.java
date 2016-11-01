/**
 * 
 */
package com.giants.dal.dao.datasource;

import java.io.Serializable;

/**
 * @author vencent.lu
 *
 * Create Date:2014年3月3日
 */
public abstract class SplitTableDomain implements Serializable {

	private static final long serialVersionUID = -5573418748087811226L;
	
	private String tableSuffix;

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
