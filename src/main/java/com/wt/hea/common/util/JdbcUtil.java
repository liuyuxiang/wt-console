package com.wt.hea.common.util;

import com.wt.hea.common.dao.impl.JdbcDao;

/**
 * 为JDBC操作提供支持的工具类
 * 
 * @author 袁明敏
 * 
 */
public class JdbcUtil {
	/**
	 * 数据库访问对象
	 */
	private static JdbcDao jdbcDao;
	static {
		jdbcDao = (JdbcDao) SpringUtil.getBean("heaJdbcDao");
	}

	/**
	 * 批量执行insert ,update ,delet 语句
	 * 
	 * @param sqls 多条不带参数的sql语句
	 * @return 返回影响的行数
	 */
	public static int batchUpdate(String... sqls) {
		return jdbcDao.batchUpdate(sqls);
	}

	/**
	 * 查询单个结果,如：select count(*) from table where ...
	 * 
	 * @param sql
	 *            查询语句,如:select * from tab t where t.filed1=? and t.filed2 like
	 *            '%?%' ...
	 * @param paramValues 参数值数组
	 * @return 返回单一结果值
	 */
	public static int queryForInt(String sql, Object... paramValues) {
		return jdbcDao.queryForInt(sql, paramValues);
	}


}
