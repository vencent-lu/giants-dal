/**
 * 
 */
package com.giants.dal.sharding.orm.ibatis;

import java.sql.Connection;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月8日
 */
public class ShardingSqlExecutor extends SqlExecutor {

	/* (non-Javadoc)
	 * @see com.ibatis.sqlmap.engine.execution.SqlExecutor#executeUpdate(com.ibatis.sqlmap.engine.scope.StatementScope, java.sql.Connection, java.lang.String, java.lang.Object[])
	 */
	@Override
	public int executeUpdate(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters) throws SQLException {
		System.out.println("########sql:"+sql);
		return super.executeUpdate(statementScope, conn, sql, parameters);
	}

	/* (non-Javadoc)
	 * @see com.ibatis.sqlmap.engine.execution.SqlExecutor#executeQuery(com.ibatis.sqlmap.engine.scope.StatementScope, java.sql.Connection, java.lang.String, java.lang.Object[], int, int, com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback)
	 */
	@Override
	public void executeQuery(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback) throws SQLException {
		System.out.println("########sql:"+sql);
		super.executeQuery(statementScope, conn, sql, parameters, skipResults,
				maxResults, callback);
	}

}
