/**
 * 
 */
package com.giants.dal.sharding;

/**
 * @author vencent.lu
 *
 * Create Date:2014年3月3日
 */
public interface DalShardingRouting {
	
	RoutingResult calculateRouting(String tableName, Object parameterBean);

}
