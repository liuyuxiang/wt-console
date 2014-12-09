package com.wt.hea.webbuilder.dao.impl;

import java.util.Map;

import com.wt.hea.common.dao.impl.JdbcDao;
import com.wt.hea.webbuilder.dao.JdbcIndexDao;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 操作index的jdbcDao
 * 编写日期:	2011-3-25
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class JdbcIndexDaoImpl extends JdbcDao implements JdbcIndexDao
{

	/**
	 * 
	 * @param tableName
	 *            数据库表名
	 * @param map
	 *            字段名与字段值映射
	 * @return 插入成功返回true
	 */
	public Boolean insert(String tableName, Map<String, Object> map)
	{
		return super.insert(tableName, map);
	}

}
