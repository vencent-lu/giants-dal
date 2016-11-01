/**
 * 
 */
package com.giants.dal.sharding.config.element;

import java.io.Serializable;

import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlIdKey;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月11日
 */
@XmlEntity
public class Table implements Serializable {

	private static final long serialVersionUID = -3830652616167294231L;
	
	@XmlAttribute
	@XmlIdKey
	private String name;
	
	@XmlAttribute
	private String strategyName;
	
	@XmlAttribute
	private String shardingRefFormat;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the strategyName
	 */
	public String getStrategyName() {
		return strategyName;
	}

	/**
	 * @param strategyName the strategyName to set
	 */
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}	

	/**
	 * @return the shardingRefFormat
	 */
	public String getShardingRefFormat() {
		return shardingRefFormat;
	}

	/**
	 * @param shardingRefFormat the shardingRefFormat to set
	 */
	public void setShardingRefFormat(String shardingRefFormat) {
		this.shardingRefFormat = shardingRefFormat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
