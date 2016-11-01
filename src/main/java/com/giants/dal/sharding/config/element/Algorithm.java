/**
 * 
 */
package com.giants.dal.sharding.config.element;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import com.giants.dal.sharding.HashAlgorithm;
import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlIdKey;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月11日
 */
@XmlEntity
public class Algorithm implements Serializable {

	private static final long serialVersionUID = 8346878262121446115L;
	
	@XmlAttribute
	@XmlIdKey
	private String name;
	
	@XmlAttribute
	private Class<?> algorithmClass;
	
	private HashAlgorithm hashAlgorithm;
	
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
	 * @return the algorithmClass
	 */
	public Class<?> getAlgorithmClass() {
		return algorithmClass;
	}

	/**
	 * @param algorithmClass the algorithmClass to set
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void setAlgorithmClass(Class<?> algorithmClass)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		this.algorithmClass = algorithmClass;
		this.hashAlgorithm = (HashAlgorithm) this.algorithmClass
				.getConstructor().newInstance();
	}

	/**
	 * @return the hashAlgorithm
	 */
	public HashAlgorithm getHashAlgorithm() {
		return hashAlgorithm;
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
		Algorithm other = (Algorithm) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
