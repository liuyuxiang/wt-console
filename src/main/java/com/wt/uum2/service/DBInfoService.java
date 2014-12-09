/*******************************************************************************
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-7-18 
 * 
 *******************************************************************************/
package com.wt.uum2.service;

import java.util.Map;

/**
 * <pre>
 * 业务名:数据库信息service接口
 * 功能说明: 取数据库信息
 * 编写日期:	2011-7-20
 * 作者:	李趁月
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DBInfoService
{
	/**
	 * 方法说明：将信息输出到启动日志
	 * 
	 */
	public void init();

	/**
	 * 方法说明：得到数据库信息
	 * 
	 * @return Map
	 */
	public Map<String, String> getDBinfo();

}
