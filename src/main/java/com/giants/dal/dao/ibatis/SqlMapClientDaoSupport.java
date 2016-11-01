/**
 * 
 */
package com.giants.dal.dao.ibatis;

import javax.sql.DataSource;

import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

import com.giants.dal.orm.ibatis.SqlMapClientTemplate;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author vencent.lu
 *
 */
public class SqlMapClientDaoSupport extends DaoSupport {

	private SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	private boolean externalTemplate = false;


	/**
	 * Set the JDBC DataSource to be used by this DAO.
	 * Not required: The SqlMapClient might carry a shared DataSource.
	 * @see #setSqlMapClient
	 */
	public final void setDataSource(DataSource dataSource) {
		if (!this.externalTemplate) {
	  	this.sqlMapClientTemplate.setDataSource(dataSource);
		}
	}

	/**
	 * Return the JDBC DataSource used by this DAO.
	 */
	DataSource getDataSource() {
		return this.sqlMapClientTemplate.getDataSource();
	}

	/**
	 * Set the iBATIS Database Layer SqlMapClient to work with.
	 * Either this or a "sqlMapClientTemplate" is required.
	 * @see #setSqlMapClientTemplate
	 */
	public final void setSqlMapClient(SqlMapClient sqlMapClient) {
		if (!this.externalTemplate) {
			this.sqlMapClientTemplate.setSqlMapClient(sqlMapClient);
		}
	}

	/**
	 * Return the iBATIS Database Layer SqlMapClient that this template works with.
	 */
	SqlMapClient getSqlMapClient() {
		return this.sqlMapClientTemplate.getSqlMapClient();
	}

	/**
	 * Set the SqlMapClientTemplate for this DAO explicitly,
	 * as an alternative to specifying a SqlMapClient.
	 * @see #setSqlMapClient
	 */
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		Assert.notNull(sqlMapClientTemplate, "SqlMapClientTemplate must not be null");
		this.sqlMapClientTemplate = sqlMapClientTemplate;
		this.externalTemplate = true;
	}

	/**
	 * Return the SqlMapClientTemplate for this DAO,
	 * pre-initialized with the SqlMapClient or set explicitly.
	 */
	SqlMapClientTemplate getSqlMapClientTemplate() {
	  return this.sqlMapClientTemplate;
	}

	@Override
	protected final void checkDaoConfig() {
		if (!this.externalTemplate) {
			this.sqlMapClientTemplate.afterPropertiesSet();
		}
	}

}
