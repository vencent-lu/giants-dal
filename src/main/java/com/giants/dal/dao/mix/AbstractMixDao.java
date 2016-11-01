/**
 * 
 */
package com.giants.dal.dao.mix;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.giants.common.lang.JavaUtil;
import com.giants.dal.dao.hibernate.HibernateDao;
import com.giants.dal.dao.ibatis.IbatisDao;

/**
 * @author vencent.lu
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractMixDao<T> extends MixDaoSupport implements
		HibernateDao<T>, IbatisDao<T> {
	
	protected final Logger   logger = LoggerFactory.getLogger(AbstractMixDao.class);
			
	private final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
		
	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#flush()
	 */
	@Override
	public void flush() {
		super.getHibernateTemplate().flush();
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#clear()
	 */
	@Override
	public void clear() {
		super.getSession().clear();
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#evict(java.lang.Object)
	 */
	@Override
	public void evict(T entiy) {
		super.getHibernateTemplate().evict(entiy);
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#insert(java.lang.Object)
	 */
	@Override
	public void insert(T entity) {
		logger.debug(new StringBuffer(128)
				.append("inserting ")
				.append(JavaUtil.getClassNameWithoutPackage(entity.getClass()
						.getName())).append(": ").append(entity).toString());
		super.getHibernateTemplate().save(entity);
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) {
		logger.debug(new StringBuffer(128)
				.append("updating ")
				.append(JavaUtil.getClassNameWithoutPackage(entity.getClass()
						.getName())).append(": ").append(entity).toString());
		super.getHibernateTemplate().update(entity);
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#merge(java.lang.Object)
	 */
	@Override
	public T merge(T entity) {
		logger.debug(new StringBuffer(128)
				.append("updating ")
				.append(JavaUtil.getClassNameWithoutPackage(entity.getClass()
						.getName())).append(": ").append(entity).toString());
		super.getHibernateTemplate().merge(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#insertOrUpdate(java.lang.Object)
	 */
	@Override
	public T insertOrUpdate(T entity) {
		logger.debug(new StringBuffer(128)
				.append("insert or update ")
				.append(JavaUtil.getClassNameWithoutPackage(entity.getClass()
						.getName())).append(": ").append(entity).toString());
		super.getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#insertAll(java.util.List)
	 */
	@Override
	public void insertAll(List<T> entityList) {
		logger.debug("inserting " + entityList.size()
				+ " records into FillsDto ...");
		for(T entity : entityList){
			super.getHibernateTemplate().save(entity);
		}
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#insertOrUpdateAll(java.util.List)
	 */
	@Override
	public List<T> insertOrUpdateAll(List<T> entityList) {
		logger.debug("insert or update " + entityList.size()
				+ " records into FillsDto ...");
		for (T entity : entityList) {
			super.getHibernateTemplate().saveOrUpdate(entity);
		}
		/*super.getHibernateTemplate().saveOrUpdateAll(entityList);*/
		return entityList;
	}

	/* (non-Javadoc)
	 * @see com.milanoo.integration.dao.MilanooDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		logger.debug(new StringBuffer(128)
				.append("deleting ")
				.append(JavaUtil.getClassNameWithoutPackage(entity.getClass()
						.getName())).append(": ").append(entity).toString());
		super.getHibernateTemplate().delete(entity);
	}

	@Override
	public void deleteAll(List<T> entityList) {
		logger.debug("deleting " + entityList.size()
				+ " records from FillsDto ...");
		super.getHibernateTemplate().deleteAll(entityList);
	}
	
	@Override
	public Date getCurrentDatetime() {
		return (Date)super.getSqlMapClientTemplate().queryForObject("getCurrentDatetime");
	}

	public T findOneEntityByExample(T exampleEntity){
		List result = super.getHibernateTemplate().findByExample(exampleEntity);
		if(result.isEmpty()){
			return null;
		}else{
			return (T)result.get(0);
		}
	}
	
	public List<T> findByExample(T exampleEntity){
		return super.getHibernateTemplate().findByExample(exampleEntity);
	}
	
	protected List<T> findByHqlQueryName(String queryName){
		return (List<T>) super.getHibernateTemplate().findByNamedQuery(queryName);
	}

	protected List<T> findByHqlQueryName(String queryName, String paramName,
			Object value) {
		return (List<T>) super.getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramName, value);
	}

	protected List<T> findByHqlQueryName(String queryName, String[] paramNames,
			Object[] values) {
		return (List<T>) super.getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, paramNames, values);
	}
	
	protected Object executeByHqlQueryName(String queryName) {
		return this.executeByHqlQueryName(queryName, (String[]) null,
				(Object[]) null);
	}

	protected Object executeByHqlQueryName(String queryName, String paramName,
			Object value) {
		return this.executeByHqlQueryName(queryName,
				new String[] { paramName }, new Object[] { value });
	}

	protected Object executeByHqlQueryName(final String queryName,
			final String[] paramNames, final Object[] values) {
		return super.getHibernateTemplate().execute(
				new HibernateCallback<Object>() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query queryObject = session.getNamedQuery(queryName);
						if (values != null) {
							for (int i = 0; i < values.length; i++) {
								queryObject.setParameter(paramNames[i],
										values[i]);
							}
						}
						return queryObject.uniqueResult();
					}

				});
	}

	public T get(Serializable id) {
		return super.getHibernateTemplate().get(this.entityClass, id);
	}

	public T load(Serializable id) {
		return super.getHibernateTemplate().load(this.entityClass, id);
	}

	public List<T> loadAll() {
		return super.getHibernateTemplate().loadAll(this.entityClass);
	}
	
	protected List<Object> findBySqlQueryName(String statementName){
		return super.getSqlMapClientTemplate().queryForList(statementName);
	}
	
	protected List<Object> findBySqlQueryName(String statementName,Object parameterBean){
		return super.getSqlMapClientTemplate().queryForList(statementName, parameterBean);
	}
	
	protected Object queryBySqlReturnObject(String statementName,Object parameterBean){
		return super.getSqlMapClientTemplate().queryForObject(statementName, parameterBean);
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.GinatsDao#searchForEntityList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<T> searchForEntityList(String statementName,
			Object parameterBean) {
		return super.getSqlMapClientTemplate().queryForList(statementName, parameterBean);
	}

	@Override
	public List<Object> searchForBeanList(String statementName, Object parameterBean) {
		return super.getSqlMapClientTemplate().queryForList(statementName, parameterBean);
	}

	@Override
	public int searchForCount(String statementName, Object parameterBean) {
		return (Integer)super.getSqlMapClientTemplate().queryForObject(statementName, parameterBean);
	}

}
