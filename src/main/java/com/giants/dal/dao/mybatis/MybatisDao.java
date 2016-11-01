/**
 * 
 */
package com.giants.dal.dao.mybatis;

import java.util.List;

import com.giants.common.tools.PageCondition;
import com.giants.dal.dao.GiantsDao;

/**
 * @author vencent.lu
 *
 */
public interface MybatisDao<T> extends GiantsDao<T> {
	
	List<T> search(PageCondition pageCondition);

	int searchCount(PageCondition pageCondition);
	
	List<Object> searchForBeanList(Object parameterBean);

}
