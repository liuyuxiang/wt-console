package com.wt.hea.webbuilder.service.impl;

import java.util.Map;

import com.wt.hea.webbuilder.service.JdbcIndexService;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-20
 * 作者:	Mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class JdbcIndexServiceImpl extends JdbcBaseService implements JdbcIndexService
{

	/**
	 * @param table
	 *            table
	 * @param map
	 *            map
	 * @return Boolean
	 */
	public Boolean insert(String table, Map<String, Object> map)
	{
		return this.jdbcIndexDao.insert(table, map);
	}
}
