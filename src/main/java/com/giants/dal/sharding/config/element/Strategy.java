/**
 * 
 */
package com.giants.dal.sharding.config.element;

import java.io.Serializable;
import java.util.List;

import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlIdKey;
import com.giants.xmlmapping.annotation.XmlManyElement;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月11日
 */
@XmlEntity
public class Strategy implements Serializable {

	private static final long serialVersionUID = 649969660816277545L;
	
	@XmlAttribute
	@XmlIdKey
	private String name;
	
	@XmlAttribute
	private String algorithmName;
	
	@XmlManyElement
	private List<Sharding> shardings;

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
	 * @return the algorithmName
	 */
	public String getAlgorithmName() {
		return algorithmName;
	}

	/**
	 * @param algorithmName the algorithmName to set
	 */
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	/**
	 * @return the shardings
	 */
	public List<Sharding> getShardings() {
		return shardings;
	}

	/**
	 * @param shardings the shardings to set
	 */
	public void setShardings(List<Sharding> shardings) {
		this.shardings = shardings;
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
		Strategy other = (Strategy) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
