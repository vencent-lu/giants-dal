/**
 * 
 */
package com.giants.dal.sharding.hashalgorithm;

import com.giants.common.codec.EncryptionDevice;
import com.giants.dal.sharding.HashAlgorithm;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月14日
 */
public class Md5TopTwoHashAlgorithm implements HashAlgorithm {

	private static final long serialVersionUID = 4212181858940627045L;

	/* (non-Javadoc)
	 * @see com.giants.dal.sharding.HashAlgorithm#calculateHashValue(java.lang.String)
	 */
	@Override
	public String calculateHashValue(String shardingRefValue) {
		try {
			return EncryptionDevice.md5ByHexCode(shardingRefValue).substring(0, 2);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
