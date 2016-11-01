/**
 * 
 */
package com.giants.dal.dao.hibernate;

import java.util.List;

import com.giants.dal.dao.GiantsDao;

/**
 * @author vencent.lu
 *
 * Create Date:2014年2月20日
 */
public interface HibernateDao<T> extends GiantsDao<T> {
	
	void flush();

	void clear();

	void evict(T entiy);
	
	T merge(T entity);

	T insertOrUpdate(T entity);
	
	List<T> insertOrUpdateAll(List<T> entityList);

}
