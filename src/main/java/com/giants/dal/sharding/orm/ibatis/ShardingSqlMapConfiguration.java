/**
 * 
 */
package com.giants.dal.sharding.orm.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.engine.config.SqlMapConfiguration;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月8日
 */
public class ShardingSqlMapConfiguration extends SqlMapConfiguration {
	
	protected final Logger   logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 */
	public ShardingSqlMapConfiguration() {
		super();
		Class<?> superclass = (Class<?>)this.getClass().getGenericSuperclass();
		try {
			Field delegateField = superclass.getDeclaredField("delegate");
			Field typeHandlerFactoryField = superclass.getDeclaredField("typeHandlerFactory");
			Field clientField = superclass.getDeclaredField("client");
			
			delegateField.setAccessible(true);
			typeHandlerFactoryField.setAccessible(true);
			clientField.setAccessible(true);
			
			SqlMapExecutorDelegate delegate = new ShardingSqlMapExecutorDelegate();
			delegateField.set(this, delegate);
			typeHandlerFactoryField.set(this, delegate.getTypeHandlerFactory());
			clientField.set(this, new SqlMapClientImpl(delegate));
			
			Method method = superclass.getDeclaredMethod("registerDefaultTypeAliases");
			method.setAccessible(true);
			method.invoke(this);
		} catch (Exception e) {
			this.logger.error("ShardingSqlMapConfiguration initialization failed!", e);
		}
	}

}
