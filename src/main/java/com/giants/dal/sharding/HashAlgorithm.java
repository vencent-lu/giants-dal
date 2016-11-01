/**
 * 
 */
package com.giants.dal.sharding;

import java.io.Serializable;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月14日
 */
public interface HashAlgorithm extends Serializable {
	
	String calculateHashValue(String shardingRefValue);

}
