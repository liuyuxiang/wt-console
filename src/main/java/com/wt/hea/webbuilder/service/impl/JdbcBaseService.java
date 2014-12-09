package com.wt.hea.webbuilder.service.impl;

import com.wt.hea.webbuilder.dao.JdbcIndexDao;

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
public class JdbcBaseService {

	/**
	 * 
	 */
	protected JdbcIndexDao jdbcIndexDao;

	public void setJdbcIndexDao(JdbcIndexDao jdbcIndexDaoImpl) {
		this.jdbcIndexDao = jdbcIndexDaoImpl;
	}

}
