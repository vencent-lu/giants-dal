/**
 * 
 */
package com.giants.dal.dao.ibatis;

import java.util.List;

import com.giants.dal.dao.GiantsDao;

/**
 * @author vencent.lu
 *
 * Create Date:2014年2月20日
 */
public interface IbatisDao<T> extends GiantsDao<T> {
	
	List<Object> searchForBeanList(String statementName, Object parameterBean);

}
