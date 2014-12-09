package com.wt.hea.common.dao.support;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * 建立动态数据源类，这个类继承AbstractRoutingDataSource
 * 
 * @author 袁明敏 
 * @since 1.0
 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
 * */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * 方法说明：覆盖父类方法
	 * @param 查找上下文数据源标识键
	 * @return 返回键名称
	 */
	protected Object determineCurrentLookupKey() {
		 return ContextHolder.getType();
	}
	
}
