/**
 * 
 */
package com.giants.dal.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author vencent.lu
 *
 */
public interface GiantsDao<T> {

	void insert(T entity);

	void update(T entity);	

	void insertAll(List<T> entityList);	

	void delete(T entity);

	void deleteAll(List<T> entityList);

	T get(Serializable id);

	T load(Serializable id);

	List<T> loadAll();

	Date getCurrentDatetime();

	T findOneEntityByExample(T exampleEntity);

	List<T> findByExample(T exampleEntity);

	List<T> searchForEntityList(String statementName, Object parameterBean);

	int searchForCount(String statementName, Object parameterBean);

}
