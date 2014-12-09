package com.wt.hea.common.service.impl;

import com.wt.hea.common.dao.BehaveLogDao;
import com.wt.hea.common.dao.SystemCodeDao;

/**
 * Hea应用框架通用服务基类 ,所有数据访问对象将注入到的此业务基类
 * 
 * @author 袁明敏
 * 
 */
public class BaseService
{
	/**
	 * 日志操作数据库访问对象
	 */
	protected BehaveLogDao behaveLogDao;

	/**
	 * 应用系统常量封装BEAN
	 */
	protected SystemCodeDao systemCodeDao;

	/**
	 * 注入behaveLogDao数据访问对象
	 * 
	 * @param behaveLogDao
	 *            注入behaveLogDaoImpl数据访问对象
	 */
	public void setBehaveLogDao(BehaveLogDao behaveLogDao)
	{
		this.behaveLogDao = behaveLogDao;
	}

	/**
	 * 
	 * 方法说明：应用系统常量封装BEAN 注入systemCodeDao数据访问对象
	 * 
	 * @param systemCodeDao
	 *            应用系统常量封装BEAN
	 */
	public void setSystemCodeDao(SystemCodeDao systemCodeDao)
	{
		this.systemCodeDao = systemCodeDao;
	}

}
