/**
 * 
 */
package com.giants.dal.dao.ibatis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.giants.dal.common.DalConstants;
import com.giants.dal.sharding.DalShardingRouting;

/**
 * @author vencent.lu
 *
 * Create Date:2014年3月3日
 */
public class AbstractMultipleDataSourceIbatisDao<T> extends
		AbstractIbatisDao<T> {
	
	private DalShardingRouting dataSourceTableSelector;
	
	private void  dataSourcePrepare(T entity) {
		/*DataSourceHolder.setDataSourceKey(this.dataSourceTableSelector
				.selectDataSourceKey(entity.getClass(), (Serializable) entity));
		if (entity instanceof SplitTableDomain) {
			((SplitTableDomain) entity)
					.setTableSuffix(this.dataSourceTableSelector
							.buildTableSuffix(entity.getClass(),
									(Serializable) entity));
		}*/
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.ibatis.AbstractIbatisDao#insert(java.lang.Object)
	 */
	@Override
	public void insert(T entity) {
		this.dataSourcePrepare(entity);
		super.insert(entity);
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.ibatis.AbstractIbatisDao#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) {
		this.dataSourcePrepare(entity);
		super.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.ibatis.AbstractIbatisDao#insertAll(java.util.List)
	 */
	@Override
	public void insertAll(List<T> entityList) {
		for (T entity : entityList) {
			this.insert(entity);
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.ibatis.AbstractIbatisDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		this.dataSourcePrepare(entity);
		super.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.ibatis.AbstractIbatisDao#deleteAll(java.util.List)
	 */
	@Override
	public void deleteAll(List<T> entityList) {
		for (T entity : entityList) {
			this.delete(entity);
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.ibatis.AbstractIbatisDao#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable id) {
		/*DataSourceHolder.setDataSourceKey(this.dataSourceTableSelector.selectDataSourceKey(this.getEntityClass(), id));
		String tableSuffix = this.dataSourceTableSelector.buildTableSuffix(this.getEntityClass(), id);*/
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("id", id);
		//parameterMap.put("tableSuffix", tableSuffix);
		return (T)this.findSingleBySqlName(DalConstants.IBATIS_STATEMENT_GETBYID, parameterMap);
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.ibatis.AbstractIbatisDao#load(java.io.Serializable)
	 */
	@Override
	public T load(Serializable id) {
		return this.get(id);
	}

	/**
	 * @param dataSourceTableSelector the dataSourceTableSelector to set
	 */
	public void setDataSourceTableSelector(
			DalShardingRouting dataSourceTableSelector) {
		this.dataSourceTableSelector = dataSourceTableSelector;
	}

}
