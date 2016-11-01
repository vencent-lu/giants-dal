/**
 * 
 */
package com.giants.dal.sharding.orm.ibatis;

import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月8日
 */
public class ShardingSqlMapExecutorDelegate extends SqlMapExecutorDelegate {

	/**
	 * 
	 */
	public ShardingSqlMapExecutorDelegate() {
		super();
		this.sqlExecutor = new ShardingSqlExecutor();
	}

}
