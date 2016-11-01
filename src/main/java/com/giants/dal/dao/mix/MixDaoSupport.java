package com.giants.dal.dao.mix;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.giants.dal.orm.ibatis.SqlMapClientTemplate;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author vencent.lu
 */
public abstract class MixDaoSupport extends DaoSupport {
	
	private HibernateTemplate hibernateTemplate;
	
	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	private boolean externalTemplate = false;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		if (this.hibernateTemplate == null || sessionFactory != this.hibernateTemplate.getSessionFactory()) {
			this.hibernateTemplate = createHibernateTemplate(sessionFactory);
		}
	}
	
	protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}
	
	SessionFactory getSessionFactory() {
		return (this.hibernateTemplate != null ? this.hibernateTemplate.getSessionFactory() : null);
	}
	
	HibernateTemplate getHibernateTemplate() {
		  return this.hibernateTemplate;
	}
	
	Session getSession()
    	throws DataAccessResourceFailureException, IllegalStateException {

		return getSession(this.hibernateTemplate.isAllowCreate());
	}
	
	Session getSession(boolean allowCreate)
			throws DataAccessResourceFailureException, IllegalStateException {

		return (!allowCreate ? SessionFactoryUtils.getSession(
				getSessionFactory(), false) : SessionFactoryUtils.getSession(
				getSessionFactory(),
				this.hibernateTemplate.getEntityInterceptor(),
				this.hibernateTemplate.getJdbcExceptionTranslator()));
	}
	
	protected final DataAccessException convertHibernateAccessException(HibernateException ex) {
		return this.hibernateTemplate.convertHibernateAccessException(ex);
	}
	
	protected final void releaseSession(Session session) {
		SessionFactoryUtils.releaseSession(session, getSessionFactory());
	}
	
	public final void setDataSource(DataSource dataSource) {
		if (!this.externalTemplate) {
	  	this.sqlMapClientTemplate.setDataSource(dataSource);
		}
	}
	
	DataSource getDataSource() {
		return this.sqlMapClientTemplate.getDataSource();
	}
	
	public final void setSqlMapClient(SqlMapClient sqlMapClient) {
		if (!this.externalTemplate) {
			this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
		}
	}
	
	SqlMapClient getSqlMapClient() {
		return this.sqlMapClientTemplate.getSqlMapClient();
	}
	
	SqlMapClientTemplate getSqlMapClientTemplate() {
		  return this.sqlMapClientTemplate;
	}

	/* (non-Javadoc)
	 * @see org.springframework.dao.support.DaoSupport#checkDaoConfig()
	 */
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		if (this.hibernateTemplate == null) {
			throw new IllegalArgumentException("'sessionFactory' or 'hibernateTemplate' is required");
		}
		if (!this.externalTemplate) {
			this.sqlMapClientTemplate.afterPropertiesSet();
		}
	}

}
