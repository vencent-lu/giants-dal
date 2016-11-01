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
public class Sharding implements Serializable {
	
	private static final long serialVersionUID = -8825121451342648969L;

	@XmlAttribute
	@XmlIdKey
	private String suffixRange;
	
	@XmlAttribute
	private String dataSource;

	/**
	 * @return the suffixRange
	 */
	public String getSuffixRange() {
		return suffixRange;
	}

	/**
	 * @param suffixRange the suffixRange to set
	 */
	public void setSuffixRange(String suffixRange) {
		this.suffixRange = suffixRange;
	}

	/**
	 * @return the dataSource
	 */
	public String getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((suffixRange == null) ? 0 : suffixRange.hashCode());
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
		Sharding other = (Sharding) obj;
		if (suffixRange == null) {
			if (other.suffixRange != null)
				return false;
		} else if (!suffixRange.equals(other.suffixRange))
			return false;
		return true;
	}

}
