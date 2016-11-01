/**
 * 
 */
package com.giants.dal.sharding.orm.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.common.xml.Nodelet;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapConfigParser;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月9日
 */
public class ShardingSqlMapConfigParser extends SqlMapConfigParser {
	
	protected final Logger   logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 */
	public ShardingSqlMapConfigParser() {
		super();		
		Class<?> parserclass = this.parser.getClass();
		Class<?> superclass = (Class<?>)this.getClass().getGenericSuperclass();
		try {
			Field letMapField = parserclass.getDeclaredField("letMap");
			letMapField.setAccessible(true);
			letMapField.set(this.parser, new HashMap<String, Nodelet>());
			
			Field stateField = superclass.getDeclaredField("state");
			stateField.setAccessible(true);
			stateField.set(this, new ShardingXmlParserState());
			
			Method addSqlMapConfigNodeletsMethod = superclass.getDeclaredMethod("addSqlMapConfigNodelets");
			Method addGlobalPropNodeletsMethod = superclass.getDeclaredMethod("addGlobalPropNodelets");
			Method addSettingsNodeletsMethod = superclass.getDeclaredMethod("addSettingsNodelets");
			Method addTypeAliasNodeletsMethod = superclass.getDeclaredMethod("addTypeAliasNodelets");
			Method addTypeHandlerNodeletsMethod = superclass.getDeclaredMethod("addTypeHandlerNodelets");
			Method addTransactionManagerNodeletsMethod = superclass.getDeclaredMethod("addTransactionManagerNodelets");
			Method addResultObjectFactoryNodeletsMethod = superclass.getDeclaredMethod("addResultObjectFactoryNodelets");
			
			addSqlMapConfigNodeletsMethod.setAccessible(true);
			addGlobalPropNodeletsMethod.setAccessible(true);
			addSettingsNodeletsMethod.setAccessible(true);
			addTypeAliasNodeletsMethod.setAccessible(true);
			addTypeHandlerNodeletsMethod.setAccessible(true);
			addTransactionManagerNodeletsMethod.setAccessible(true);
			addResultObjectFactoryNodeletsMethod.setAccessible(true);
			
			addSqlMapConfigNodeletsMethod.invoke(this);
			addGlobalPropNodeletsMethod.invoke(this);
			addSettingsNodeletsMethod.invoke(this);
			addTypeAliasNodeletsMethod.invoke(this);
			addTypeHandlerNodeletsMethod.invoke(this);
			addTransactionManagerNodeletsMethod.invoke(this);
			this.addSqlMapNodelets();
			addResultObjectFactoryNodeletsMethod.invoke(this);			
		} catch (Exception e) {
			this.logger.error("ShardingSqlMapConfigParser initialization failed!", e);
		}
	}

}
