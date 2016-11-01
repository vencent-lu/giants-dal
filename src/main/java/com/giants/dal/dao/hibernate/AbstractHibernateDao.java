/**
 * 
 */
package com.giants.dal.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.giants.common.beanutils.BeanUtils;
import com.giants.common.lang.JavaUtil;

/**
 * @author vencent.lu
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractHibernateDao<T> extends HibernateDaoSupport
		implements HibernateDao<T> {

	protected final Logger   logger = LoggerFactory.getLogger(AbstractHibernateDao.class);
	
	private final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];

	/* (non-Javadoc)
	 * @see com.giants.dao.hibernate.HibernateDao#flush()
	 */
	@Override
	public void flush() {
		super.getHibernateTemplate().flush();		
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.hibernate.HibernateDao#clear()
	 */
	@Override
	public void clear() {
		super.getSession().clear();
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.hibernate.HibernateDao#evict(java.lang.Object)
	 */
	@Override
	public void evict(T entiy) {
		super.getHibernateTemplate().evict(entiy);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#insert(java.lang.Object)
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
	 * @see com.giants.dao.GinatsDao#update(java.lang.Object)
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
	 * @see com.giants.dao.hibernate.HibernateDao#merge(java.lang.Object)
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
	 * @see com.giants.dao.hibernate.HibernateDao#insertOrUpdate(java.lang.Object)
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
	 * @see com.giants.dao.GinatsDao#insertAll(java.util.List)
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
	 * @see com.giants.dao.hibernate.HibernateDao#insertOrUpdateAll(java.util.List)
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
	 * @see com.giants.dao.GinatsDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		logger.debug(new StringBuffer(128)
				.append("deleting ")
				.append(JavaUtil.getClassNameWithoutPackage(entity.getClass()
						.getName())).append(": ").append(entity).toString());
		super.getHibernateTemplate().delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#deleteAll(java.util.List)
	 */
	@Override
	public void deleteAll(List<T> entityList) {
		logger.debug("deleting " + entityList.size()
				+ " records from FillsDto ...");
		super.getHibernateTemplate().deleteAll(entityList);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#get(java.io.Serializable)
	 */
	@Override
	public T get(Serializable id) {
		return super.getHibernateTemplate().get(this.entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#load(java.io.Serializable)
	 */
	@Override
	public T load(Serializable id) {
		return super.getHibernateTemplate().load(this.entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#loadAll()
	 */
	@Override
	public List<T> loadAll() {
		return super.getHibernateTemplate().loadAll(this.entityClass);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#getCurrentDatetime()
	 */
	@Override
	public Date getCurrentDatetime() {
		return (Date) super.getSession().createSQLQuery("SELECT CURRENT_TIMESTAMP()").uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#findOneEntityByExample(java.lang.Object)
	 */
	@Override
	public T findOneEntityByExample(T exampleEntity) {
		List result = super.getHibernateTemplate().findByExample(exampleEntity);
		if(result.isEmpty()){
			return null;
		}else{
			return (T)result.get(0);
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#findByExample(java.lang.Object)
	 */
	@Override
	public List<T> findByExample(T exampleEntity) {
		return super.getHibernateTemplate().findByExample(exampleEntity);
	}

	/* (non-Javadoc)
	 * @see com.giants.dal.dao.GinatsDao#searchForEntityList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<T> searchForEntityList(final String statementName,
			final Object parameterBean) {
		return super.getHibernateTemplate().execute(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(statementName);
				if (parameterBean instanceof Map) {
					query.setProperties((Map)parameterBean);
				} else {
					query.setProperties(parameterBean);
				}
				try {
					query.setFirstResult((Integer)BeanUtils.getPropertyValue(parameterBean, "offset"));
					query.setMaxResults((Integer)BeanUtils.getPropertyValue(parameterBean, "size"));
				} catch (Exception e) {
					logger.warn("get offset and size", e);
				}
				return query.list();
			}

		});
		//return super.getHibernateTemplate().findByNamedQueryAndValueBean(statementName, parameterBean);
	}

	/* (non-Javadoc)
	 * @see com.giants.dao.GinatsDao#searchForCount(java.lang.String, java.lang.Object)
	 */
	@Override
	public int searchForCount(final String statementName,
			final Object parameterBean) {
		return (Integer)this.executeByHqlQueryName(statementName, parameterBean);
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
	
	protected List<T> findByHqlQueryName(String queryName, Object paramBean) {
		return (List<T>) super.getHibernateTemplate().findByNamedQueryAndValueBean(queryName, paramBean);
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
	
	protected Object executeByHqlQueryName(final String queryName, final Object paramBean) {
		return super.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query queryObject = session.getNamedQuery(queryName);
				queryObject.setProperties(paramBean);
				return queryObject.uniqueResult();
			}
		});
	}
	
	protected List<T> findByHibernateCallback(HibernateCallback<List<T>> hibernateCallback) {
		return super.getHibernateTemplate().execute(hibernateCallback);
	}
	
	protected Object executeHibernateCallback(HibernateCallback<Object> hibernateCallback) {
		return super.getHibernateTemplate().execute(hibernateCallback);
	}

}
