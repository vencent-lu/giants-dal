/**
 * 
 */
package com.giants.dal.sharding;

import org.apache.commons.lang.StringUtils;

import com.giants.dal.sharding.config.DalShardingRoutingConfig;
import com.giants.dal.sharding.config.element.Table;
import com.giants.xmlmapping.XmlDataModule;
import com.giants.xmlmapping.XmlMappingData;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 * Create Date:2014年7月14日
 */
public class DefaultDalShardingRoutingImpl implements DalShardingRouting {
	
	private DalShardingRoutingConfig dalShardingRoutingConfig;
	
	

	/**
	 * @throws XmlMapException 
	 * @throws XMLParseException 
	 * @throws XmlDataException 
	 * 
	 */
	public DefaultDalShardingRoutingImpl(String configFile) throws XmlMapException, XmlDataException, XMLParseException {
		if (StringUtils.isNotEmpty(configFile)) {
			XmlMappingData xmlMappingData = new XmlMappingData(
					DalShardingRoutingConfig.class);
			xmlMappingData.loadXmls(configFile);
			XmlDataModule<DalShardingRoutingConfig> xmlDataModule = xmlMappingData
					.getDataModule(DalShardingRoutingConfig.class);
			this.dalShardingRoutingConfig = xmlDataModule.get();
		}
	}



	/* (non-Javadoc)
	 * @see com.giants.dal.sharding.DalShardingRouting#calculateRouting(java.lang.String, java.lang.Object)
	 */
	@Override
	public RoutingResult calculateRouting(String tableName, Object parameterBean) {
		RoutingResult routingResult = new RoutingResult();
		Table table = this.dalShardingRoutingConfig.getTable(tableName);
		if (table != null) {
			String shardingRef = table.getShardingRefFormat();
			//routingResult.setTableSuffix(this.dalShardingRoutingConfig.getAlgorithm(table.get));
		}
		return null;
	}

}
