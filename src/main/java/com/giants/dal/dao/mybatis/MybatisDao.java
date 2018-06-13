/**
 * 
 */
package com.giants.dal.dao.mybatis;

import java.util.List;

import com.giants.common.tools.PageQueryCondition;
import com.giants.dal.dao.GiantsDao;

/**
 * @author vencent.lu
 *
 */
public interface MybatisDao<T> extends GiantsDao<T> {
	
	List<T> search(PageQueryCondition<?> pageCondition);

	int searchCount(PageQueryCondition<?> pageCondition);
	
	List<Object> searchForBeanList(Object parameterBean);

}
