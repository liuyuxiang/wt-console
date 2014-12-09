/*******************************************************************************
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-7-18 
 * 
 *******************************************************************************/
package com.wt.uum2.dao;

import java.util.List;

/**
 * <pre>
 * 业务名:
 * 功能说明: 得到数据库信息
 * 编写日期:	2011-7-18
 * 作者:	李趁月
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DBInfoDao
{
	/**
	 * 方法说明：getServerHost
	 * 
	 * @return String
	 */
	public String getServerHost();

	/**
	 * 方法说明：getServerIp
	 * 
	 * @return String
	 */
	/**
	 * 方法说明：getServerIp
	 * 
	 * @return String
	 */
	public String getServerIp();

	/**
	 * 方法说明：getClientIp
	 * 
	 * @return String
	 */
	public String getClientIp();

	/**
	 * 方法说明：getInstanceName
	 * 
	 * @return String
	 */
	public String getInstanceName();

	/**
	 * 方法说明：getUser
	 * 
	 * @return String
	 */
	public String getUser();

	/**
	 * 方法说明：getTablespace
	 * 
	 * @return String
	 */
	public String getTablespace();

	/**
	 * 方法说明：getUserRoleList
	 * 
	 * @return List
	 */
	public List<String> getUserRoleList();
}
