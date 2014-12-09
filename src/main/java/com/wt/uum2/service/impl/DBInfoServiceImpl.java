/*******************************************************************************
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-7-18 
 * 
 *******************************************************************************/
package com.wt.uum2.service.impl;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.dao.DBInfoDao;
import com.wt.uum2.service.DBInfoService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 得到数据库信息列表
 * 编写日期:	2011-7-18
 * 作者:	李趁月
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class DBInfoServiceImpl implements DBInfoService
{
	/**
	 * 定义dbInfoDao对象
	 */
	private DBInfoDao dbInfoDao;
	/**
	 * 定义logger对象
	 */
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DBInfoService#init()
	 */
	/**
	 * 方法说明：init
	 * 
	 */
	public void init()
	{
		logger.info(this.getDBinfo());
		if (this.getDBinfo().get("roles").toLowerCase().contains("dba")) {
			logger.warn("user " + this.getDBinfo().get("user")
					+ " have DBA role privileges,please revoke this role!");
		}

	}

	public DBInfoDao getDbInfoDao()
	{
		return dbInfoDao;
	}

	public void setDbInfoDao(DBInfoDao dbInfoDao)
	{
		this.dbInfoDao = dbInfoDao;
	}

	/* 
	 * 得到数据库信息列表
	 */

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DBInfoService#getDBinfo()
	 */
	/**
	 * 方法说明：getDBinfo
	 * 
	 * @return Map
	 */
	public Map<String, String> getDBinfo()
	{
		Map<String, String> map = new Hashtable<String, String>();
		map.put("serverHost", isNull(dbInfoDao.getServerHost()));
		map.put("serverIp", isNull(dbInfoDao.getServerIp()));
		map.put("clientIp", isNull(dbInfoDao.getClientIp()));
		map.put("instanceName", isNull(dbInfoDao.getInstanceName()));
		map.put("user", isNull(dbInfoDao.getUser()));
		map.put("tablespace", isNull(dbInfoDao.getTablespace()));
		map.put("roles", StringUtils.join(dbInfoDao.getUserRoleList(), ","));
		return map;
	}

	/**
	 * 方法说明：判断变量是否为空
	 * 
	 * @param str
	 *            str
	 * @return String
	 */
	public String isNull(String str)
	{
		return (str == null || str.equals("")) ? "未知" : str;
	}

}
