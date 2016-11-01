/**
 * 
 */
package com.giants.dal.sharding.orm.ibatis;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.engine.builder.xml.XmlParserState;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月9日
 */
public class ShardingXmlParserState extends XmlParserState {
	
	protected final Logger   logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 
	 */
	public ShardingXmlParserState() {
		super();
		Class<?> superclass = (Class<?>)this.getClass().getGenericSuperclass();
		try {
			Field configField = superclass.getDeclaredField("config");
			configField.setAccessible(true);
			configField.set(this, new ShardingSqlMapConfiguration());
		} catch (Exception e) {
			this.logger.error("ShardingXmlParserState initialization failed!", e);
		}
	}

}
