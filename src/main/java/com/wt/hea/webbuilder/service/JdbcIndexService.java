package com.wt.hea.webbuilder.service;

import java.util.Map;


/**
 * <pre>
 * 业务名:
 * 功能说明: 指标jdbc实现
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface JdbcIndexService {
	
	/**
	 * 
	 * @param table 数据库表名
	 * @param map 字段名与字段值映射
	 * @return 插入成功返回true
	 */
	public Boolean insert(String table,Map<String,Object> map);
}
