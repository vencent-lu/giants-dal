/**
 * 
 */
package com.giants.dal.dao.ibatis;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giants.dal.common.DalConstants;
import com.giants.dal.orm.ibatis.SqlMapClientCallback;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.engine.execution.BatchException;

/**
 * @author vencent.lu
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractIbatisDao<T> extends SqlMapClientDaoSupport
		implements IbatisDao<T> {
	
	private long sqlExecuteThreshold = 200;
	protected final Logger   logger = LoggerFactory.getLogger(AbstractIbatisDao.class);
	
	private final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	
	private String buildStatementName(String sqlName) {
		return new StringBuffer(this.entityClass.getSimpleName())
				.append('.').append(sqlName).toString();
	}
	
	/**
	 * @return the entityClass
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}



	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#insert(java.lang.Object)
	 */
	@Override
	public void insert(T entity) {
		String statementName = this.buildStatementName(DalConstants.IBATIS_STATEMENT_INSERT);
		long timeScale=System.currentTimeMillis();
		super.getSqlMapClientTemplate().insert(statementName, entity);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
							.format("[insert(statementName={0},entity={1})] Insert entity into database in {2}ms",
									statementName, entity, executionTime));
		} else {
			this.logger.info(MessageFormat
							.format("[insert(statementName={0},entity={1})] Insert entity into database in {2}ms",
									statementName, entity, executionTime));
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) {
		String statementName = this.buildStatementName(DalConstants.IBATIS_STATEMENT_UPDATE);
		long timeScale=System.currentTimeMillis();
		super.getSqlMapClientTemplate().update(statementName, entity);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
							.format("[update(statementName={0},entity={1})] Update entity into database in {2}ms",
									statementName, entity, executionTime));
		} else {
			this.logger.info(MessageFormat
							.format("[update(statementName={0},entity={1})] Update entity into database in {2}ms",
									statementName, entity, executionTime));
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#insertAll(java.util.List)
	 */
	@Override
	public void insertAll(final List<T> entityList) {
		final String statementName = this.buildStatementName(DalConstants.IBATIS_STATEMENT_INSERT);
		long timeScale=System.currentTimeMillis();
		List result = super.getSqlMapClientTemplate().execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (T entity : entityList) {
					executor.insert(statementName, entity);
				}
				try {
					return executor.executeBatchDetailed();
				} catch (BatchException e) {
					logger.error("Batch insert failed!", e);
					throw new SQLException("Batch insert failed!",e);
				}
			}
		});
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[insertBatch(statementName={0},entityList={1})] InsertBatch entityList into database in {2}ms",
							statementName, entityList, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[insertBatch(statementName={0},entityList={1})] InsertBatch entityList into database in {2}ms",
							statementName, entityList, executionTime));
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		String statementName = this
				.buildStatementName(DalConstants.IBATIS_STATEMENT_DELETE);
		long timeScale = System.currentTimeMillis();
		super.getSqlMapClientTemplate().delete(statementName, entity);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[delete(statementName={0},entity={1})] Delete entity from database in {2}ms",
							statementName, entity, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[delete(statementName={0},entity={1})] Delete entity from database in {2}ms",
							statementName, entity, executionTime));
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#deleteAll(java.util.List)
	 */
	@Override
	public void deleteAll(final List<T> entityList) {
		final String statementName = this
				.buildStatementName(DalConstants.IBATIS_STATEMENT_DELETE);
		long timeScale = System.currentTimeMillis();
		super.getSqlMapClientTemplate().execute(
				new SqlMapClientCallback<Object>() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (T entity : entityList) {
							executor.delete(statementName, entity);
						}
						try {
							return executor.executeBatchDetailed();
						} catch (BatchException e) {
							logger.error("Batch delete failed!", e);
							throw new SQLException("Batch delete failed!", e);
						}
					}
				});
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[deleteBatch(statementName={0},entityList={1})] DeleteBatch entityList from database in {2}ms",
							statementName, entityList, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[deleteBatch(statementName={0},entityList={1})] DeleteBatch entityList from database in {2}ms",
							statementName, entityList, executionTime));
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#get(java.io.Serializable)
	 */
	@Override
	public T get(Serializable id) {
		String statementName = this
				.buildStatementName(DalConstants.IBATIS_STATEMENT_GETBYID);
		long timeScale = System.currentTimeMillis();
		T o = (T) super.getSqlMapClientTemplate().queryForObject(statementName,id);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[getById(statementName={0},id={1})] Get entity by id from database in {2}ms",
							statementName, id, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[getById(statementName={0},id={1})] Get entity by id from database in {2}ms",
							statementName, id, executionTime));
		}
		return o;
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#load(java.io.Serializable)
	 */
	@Override
	public T load(Serializable id) {
		return this.get(id);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#loadAll()
	 */
	@Override
	public List<T> loadAll() {
		String statementName = this
				.buildStatementName(DalConstants.IBATIS_STATEMENT_GETALL);
		long timeScale = System.currentTimeMillis();
		List<T> results = super.getSqlMapClientTemplate().queryForList(statementName);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[getAll(statementName={0})] Get all entities from database in {1}ms",
							statementName, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[getAll(statementName={0})] Get all entities from database in {1}ms",
							statementName, executionTime));
		}
		return results;
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#getCurrentDatetime()
	 */
	@Override
	public Date getCurrentDatetime() {
		return (Date) super.getSqlMapClientTemplate().queryForObject(
				DalConstants.IBATIS_STATEMENT_CURRENT_TIME);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#findOneEntityByExample(java.lang.Object)
	 */
	@Override
	public T findOneEntityByExample(T exampleEntity) {
		String statementName = this
				.buildStatementName(DalConstants.IBATIS_STATEMENT_EXAMPLE);
		long timeScale = System.currentTimeMillis();
		T o = (T) super.getSqlMapClientTemplate().queryForObject(statementName,exampleEntity);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[findByExample(statementName={0},example={1})] Find by example from database in {2}ms",
							statementName, exampleEntity, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[findByExample(statementName={0},example={1})] Find by example from database in {2}ms",
							statementName, exampleEntity, executionTime));
		}
		return o;
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#findByExample(java.lang.Object)
	 */
	@Override
	public List<T> findByExample(T exampleEntity) {
		String statementName = this
				.buildStatementName(DalConstants.IBATIS_STATEMENT_EXAMPLE);
		long timeScale = System.currentTimeMillis();
		List<T> results = super.getSqlMapClientTemplate().queryForList(statementName, exampleEntity);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[findByExample(statementName={0},example={1})] Find by example from database in {2}ms",
							statementName, exampleEntity, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[findByExample(statementName={0},example={1})] Find by example from database in {2}ms",
							statementName, exampleEntity, executionTime));
		}
		return results;
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.GinatsDao#searchForEntityList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<T> searchForEntityList(String statementName,
			Object parameterBean) {
		String fullStatementName = this.buildStatementName(statementName);
		long timeScale = System.currentTimeMillis();
		List<T> results = super.getSqlMapClientTemplate()
				.queryForList(fullStatementName, parameterBean);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[searchForList(statementName={0},parameter={1})] Search for list from database in {2}ms",
							fullStatementName, parameterBean, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[searchForList(statementName={0},parameter={1})] Search for list from database in {2}ms",
							fullStatementName, parameterBean, executionTime));
		}
		return results;
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.ibatis.IbatisDao#searchForBeanList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<Object> searchForBeanList(String statementName,
			Object parameterBean) {
		String fullStatementName = this.buildStatementName(statementName);
		long timeScale = System.currentTimeMillis();
		List<Object> results = super.getSqlMapClientTemplate()
				.queryForList(fullStatementName, parameterBean);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[searchForList(statementName={0},parameter={1})] Search for list from database in {2}ms",
							fullStatementName, parameterBean, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[searchForList(statementName={0},parameter={1})] Search for list from database in {2}ms",
							fullStatementName, parameterBean, executionTime));
		}
		return results;
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#searchForCount(java.lang.String, java.lang.Object)
	 */
	@Override
	public int searchForCount(String statementName,
			Object parameterBean) {
		String fullStatementName = this.buildStatementName(statementName);
		long timeScale = System.currentTimeMillis();
		Integer count = (Integer) super.getSqlMapClientTemplate()
				.queryForObject(fullStatementName, parameterBean);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[searchForCount(statementName={0},parameter={1})] Search For Count from database in {2}ms",
							fullStatementName, parameterBean, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[searchForCount(statementName={0},parameter={1})] Search For Count from database in {2}ms",
							fullStatementName, parameterBean, executionTime));
		}
		return count;
	}
	
	protected List findBySqlName(String sqlName) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		List results = super.getSqlMapClientTemplate().queryForList(	statementName);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[findBySqlName(statementName={0})] Find by sql name from database in {1}ms",
							statementName, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[findBySqlName(statementName={0})] Find by sql name from database in {1}ms",
							statementName, executionTime));
		}
		return results;
	}
	
	protected List findBySqlName(String sqlName, Object parameterObject) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		List results = super.getSqlMapClientTemplate().queryForList(
				statementName, parameterObject);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[findBySqlName(statementName={0},parameter={1})] Find by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[findBySqlName(statementName={0},parameter={1})] Find by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		}
		return results;
	}
	
	protected Object findSingleBySqlName(String sqlName) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		Object object = super.getSqlMapClientTemplate().queryForObject(statementName);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[findSingleBySqlName(statementName={0})] Find single by sql name from database in {1}ms",
							statementName, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[findSingleBySqlName(statementName={0})] Find single by sql name from database in {1}ms",
							statementName, executionTime));
		}
		return object;
	}
	
	protected Object findSingleBySqlName(String sqlName, Object parameterObject) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		Object object = super.getSqlMapClientTemplate().queryForObject(
				statementName, parameterObject);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[findSingleBySqlName(statementName={0},parameter={1})] Find single by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[findSingleBySqlName(statementName={0},parameter={1})] Find single by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		}
		return object;
	}
	
	protected void insertBySqlName(String sqlName) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		super.getSqlMapClientTemplate().insert(statementName);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[insertBySqlName(statementName={0})] Insert by sql name from database in {1}ms",
							statementName, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[insertBySqlName(statementName={0})] Insert by sql name from database in {1}ms",
							statementName, executionTime));
		}
	}
	
	protected void insertBySqlName(String sqlName, Object parameterObject) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		super.getSqlMapClientTemplate().insert(statementName, parameterObject);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[insertBySqlName(statementName={0},parameter={1})] Insert by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[insertBySqlName(statementName={0},parameter={1})] Insert by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		}
	}
	
	protected int updateBySqlName(String sqlName) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		int num = super.getSqlMapClientTemplate().update(statementName);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[updateBySqlName(statementName={0})] Update by sql name from database in {1}ms",
							statementName, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[updateBySqlName(statementName={0})] Update by sql name from database in {1}ms",
							statementName, executionTime));
		}
		return num;
	}
	
	protected int updateBySqlName(String sqlName, Object parameterObject) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		int num = super.getSqlMapClientTemplate().update(statementName, parameterObject);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[updateBySqlName(statementName={0},parameter={1})] Update by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[updateBySqlName(statementName={0},parameter={1})] Update by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		}
		return num;
	}
	
	protected int deleteBySqlName(String sqlName) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		int num = super.getSqlMapClientTemplate().delete(statementName);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[deleteBySqlName(statementName={0})] Delete by sql name from database in {1}ms",
							statementName, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[deleteBySqlName(statementName={0})] Delete by sql name from database in {1}ms",
							statementName, executionTime));
		}
		return num;
	}
	
	protected int deleteBySqlName(String sqlName, Object parameterObject) {
		String statementName = this.buildStatementName(sqlName);
		long timeScale = System.currentTimeMillis();
		int num = super.getSqlMapClientTemplate().delete(statementName, parameterObject);
		long executionTime = System.currentTimeMillis() - timeScale;
		if (executionTime > this.sqlExecuteThreshold) {
			this.logger.warn(MessageFormat
					.format("[deleteBySqlName(statementName={0},parameter={1})] Delete by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		} else {
			this.logger.info(MessageFormat
					.format("[deleteBySqlName(statementName={0},parameter={1})] Delete by sql name from database in {2}ms",
							statementName, parameterObject, executionTime));
		}
		return num;
	}

	public void setSqlExecuteThreshold(long sqlExecuteThreshold) {
		this.sqlExecuteThreshold = sqlExecuteThreshold;
	}

}
