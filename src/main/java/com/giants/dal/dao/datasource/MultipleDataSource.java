/**
 * 
 */
package com.giants.dal.dao.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author vencent.lu
 *
 * Create Date:2014年3月3日
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceHolder.getDataSourceKey();
	}

}
