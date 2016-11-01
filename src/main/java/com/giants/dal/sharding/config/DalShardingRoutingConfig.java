/**
 * 
 */
package com.giants.dal.sharding.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.giants.dal.sharding.config.element.Algorithm;
import com.giants.dal.sharding.config.element.Strategy;
import com.giants.dal.sharding.config.element.Table;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlManyElement;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月11日
 */
@XmlEntity
public class DalShardingRoutingConfig implements Serializable {

	private static final long serialVersionUID = -7294284024428105643L;
	
	@XmlManyElement
	private List<Algorithm> algorithms;
	
	private Map<String, Algorithm> algorithmMap;
		
	@XmlManyElement
	private List<Strategy> strategies;
	
	private Map<String, Strategy> strategyMap;
	
	@XmlManyElement
	private List<Table> tables;
	
	private Map<String, Table> tableMap;
	
	public Algorithm getAlgorithm(String name) {
		if (this.algorithmMap == null) {
			return null;
		}
		return this.algorithmMap.get(name);
	}
	
	public Strategy getStrategy(String name) {
		if (this.strategyMap == null) {
			return null;
		}
		return this.strategyMap.get(name);
	}
	
	public Table getTable(String name) {
		if (this.tableMap == null) {
			return null;
		}
		return this.tableMap.get(name);
	}

	/**
	 * @return the hashAlgorithms
	 */
	public List<Algorithm> getAlgorithms() {
		return algorithms;
	}

	/**
	 * @param hashAlgorithms the hashAlgorithms to set
	 */
	public void setHashAlgorithms(List<Algorithm> algorithms) {
		this.algorithms = algorithms;
		if (CollectionUtils.isNotEmpty(this.algorithms)) {
			this.algorithmMap = new HashMap<String, Algorithm>();
			for (Algorithm algorithm : this.algorithms) {
				this.algorithmMap.put(algorithm.getName(), algorithm);
			}
		}
	}

	/**
	 * @return the strategies
	 */
	public List<Strategy> getStrategies() {
		return strategies;
	}

	/**
	 * @param strategies the strategies to set
	 */
	public void setStrategies(List<Strategy> strategies) {
		this.strategies = strategies;
		if (CollectionUtils.isNotEmpty(this.strategies)) {
			this.strategyMap = new HashMap<String, Strategy>();
			for (Strategy strategy : this.strategies) {
				this.strategyMap.put(strategy.getName(), strategy);
			}
		}
	}

	/**
	 * @return the tables
	 */
	public List<Table> getTables() {
		return tables;
	}

	/**
	 * @param tables the tables to set
	 */
	public void setTables(List<Table> tables) {
		this.tables = tables;
		if (CollectionUtils.isNotEmpty(this.tables)) {
			this.tableMap = new HashMap<String, Table>();
			for (Table table : this.tables) {
				this.tableMap.put(table.getName(), table);
			}
		}
	}
	
	

}
