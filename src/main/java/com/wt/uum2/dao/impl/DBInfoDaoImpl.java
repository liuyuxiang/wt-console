/*******************************************************************************
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-7-18 
 * 
 *******************************************************************************/
package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.wt.uum2.dao.DBInfoDao;

/**
 * <pre>
 * 业务名: 读取数据库信息的dao
 * 功能说明: 读取数据库信息的dao
 * 编写日期:	2011-7-18
 * 作者:李趁月
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DBInfoDaoImpl implements DBInfoDao
{

	/**
	 * 查询服务器主机名
	 */
	public static String SERVER_HOST = "select UTL_INADDR.get_host_name() from dual";
	/**
	 * 查询服务器 ip
	 */
	public static String SERVER_IP = "select utl_inaddr.GET_HOST_ADDRESS() from dual";
	/**
	 * 连接者ip
	 */
	public static String CLIENT_IP = "select sys_context('userenv','ip_address') from dual";
	/**
	 * 实例名
	 */
	public static String INSTANCE_NAME = "select instance_name from v$instance";
	/**
	 * 当前连接用户名
	 */
	public static String USER = "select user from dual";
	/**
	 * 表空间
	 */
	public static String TABLESPACE = "select default_tablespace from user_users";
	/**
	 * 用户角色
	 */
	public static String USER_ROLES = "select granted_role from user_role_privs urp where default_role='YES'";

	/**
	 * 定义simpleJdbcTemplate对象
	 */
	private SimpleJdbcTemplate simpleJdbcTemplate;
	/**
	 * 定义logger对象
	 */
	private final Logger logger = LogManager.getLogger(this.getClass().getName());

	/**
	 * 方法说明：得到spring的jdbc
	 * 
	 * @return SimpleJdbcTemplate
	 */
	protected SimpleJdbcTemplate getSimpleJdbcTemplate()
	{
		return simpleJdbcTemplate;
	}

	/**
	 * 方法说明：初始化数据源
	 * 
	 * @param dataSource
	 *            dataSource
	 */
	public void setDataSource(DataSource dataSource)
	{
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DBInfoDao#getServerHost()
	 */
	/**
	 * 方法说明：getServerHost
	 * 
	 * @return String
	 */
	public String getServerHost()
	{
		try {
			return simpleJdbcTemplate.queryForObject(SERVER_HOST, String.class);
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DBInfoDao#getServerIp()
	 */
	/**
	 * 方法说明：getServerIp
	 * 
	 * @return String
	 */
	public String getServerIp()
	{
		try {
			return simpleJdbcTemplate.queryForObject(SERVER_IP, String.class);
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DBInfoDao#getClientIp()
	 */
	/**
	 * 方法说明：getClientIp
	 * 
	 * @return String
	 */
	public String getClientIp()
	{
		try {
			return simpleJdbcTemplate.queryForObject(CLIENT_IP, String.class);
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DBInfoDao#getInstanceName()
	 */
	/**
	 * 方法说明：getInstanceName
	 * 
	 * @return String
	 */
	public String getInstanceName()
	{
		try {
			return simpleJdbcTemplate.queryForObject(INSTANCE_NAME, String.class);
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DBInfoDao#getUser()
	 */
	/**
	 * 方法说明：getUser
	 * 
	 * @return String
	 */
	public String getUser()
	{
		try {
			return simpleJdbcTemplate.queryForObject(USER, String.class);
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DBInfoDao#getTablespace()
	 */
	/**
	 * 方法说明：getTablespace
	 * 
	 * @return String
	 */
	public String getTablespace()
	{
		try {
			return simpleJdbcTemplate.queryForObject(TABLESPACE, String.class);
		} catch (Exception e) {
			logger.error(e, e);
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DBInfoDao#getUserRoleList()
	 */
	/**
	 * 方法说明：getUserRoleList
	 * 
	 * @return List
	 */
	public List<String> getUserRoleList()
	{
		List<String> returnList = new ArrayList<String>();
		try {
			List<Map<String, Object>> list = simpleJdbcTemplate.queryForList(USER_ROLES);
			for (Map<String, Object> map : list) {
				returnList.add(map.get("granted_role").toString());
			}
			return returnList;
		} catch (Exception e) {
			logger.error(e, e);
			return returnList;
		}
	}

	/*public static void main(String[] a)
	{
		DBInfoDaoImpl db = new DBInfoDaoImpl();
		//System.out.println(db.getClientIp());
	}*/
}
