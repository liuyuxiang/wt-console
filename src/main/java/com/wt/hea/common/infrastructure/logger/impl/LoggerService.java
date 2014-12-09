/**
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-3-28
 *
 */
package com.wt.hea.common.infrastructure.logger.impl;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.data.DateUtils;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.model.BehaveLog;
import com.wt.hea.common.service.impl.BaseService;

/**
 * 
 * <pre>
 * 业务名:   日志接口实现类
 * 功能说明: 系统运行日志定义和行为日志定义实现类
 * 编写日期:	2011-3-28
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-28
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
@Transactional(readOnly = true)
@SuppressWarnings("rawtypes")
public class LoggerService extends BaseService implements Logger
{
	/**
	 * 从xml文件获取的系统属性
	 */
	@SuppressWarnings("unused")
	private static Properties SYSTEM_PROPERTIES = null;
	static {
		try {
			SYSTEM_PROPERTIES = PropertiesLoaderUtils
					.loadAllProperties("com/hirisun/hea/common/config/log4j.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 目标日志类
	 */
	private static Class CLAZZ = null;

	/**
	 * 构造函数
	 */
	private LoggerService()
	{
	}

	/**
	 * 单例对象
	 */
	private static Logger logservice = null;

	/**
	 * 
	 * 方法说明：获取单例日志对象
	 * 
	 * @return 单例日志对象
	 */
	public static Logger getInstance()
	{
		if (logservice == null) {
			logservice = new LoggerService();
		}
		return logservice;
	}

	/**
	 * 
	 * 方法说明：获取单例日志对象
	 * 
	 * @param clazz
	 *            日志对象寄宿的目标类
	 * @return 单例日志对象
	 */
	public static Logger getInstance(Class clazz)
	{
		if (logservice == null) {
			logservice = new LoggerService();
		}
		CLAZZ = clazz;
		return logservice;
	}

	/**
	 * log4j实现
	 */
	private Log log = null;

	/**
	 * 系统运行日志 方法说明： 记录调式信息
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 */
	public void debug(Object obj)
	{
		log = LogFactory.getLog(CLAZZ);

		if (log.isDebugEnabled()) {
			log.debug(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明： 记录调式信息
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param targetClazz
	 *            日志启用的目标类
	 */
	public void debug(Object obj, Class targetClazz)
	{

		log = LogFactory.getLog(targetClazz);
		if (log.isDebugEnabled()) {
			log.debug(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录错误日志
	 * 
	 * @param obj
	 *            错误信息的日志值
	 * @param targetClazz
	 *            日志启用的目标类
	 */
	public void error(Object obj, Class targetClazz)
	{
		log = LogFactory.getLog(targetClazz);
		if (log.isErrorEnabled()) {
			log.error(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录错误日志
	 * 
	 * @param obj
	 *            错误信息的日志值
	 */
	public void error(Object obj)
	{
		log = LogFactory.getLog(CLAZZ);
		if (log.isErrorEnabled()) {
			log.error(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统毁灭性的日志
	 * 
	 * @param obj
	 *            毁灭性日志值
	 * @param targetClazz
	 *            日志启用的目标类
	 */
	public void fatal(Object obj, Class targetClazz)
	{
		log = LogFactory.getLog(targetClazz);
		if (log.isFatalEnabled()) {
			log.fatal(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统毁灭性的日志
	 * 
	 * @param obj
	 *            毁灭性日志值
	 */
	public void fatal(Object obj)
	{
		log = LogFactory.getLog(CLAZZ);
		if (log.isFatalEnabled()) {
			log.fatal(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统平行运行状态日志
	 * 
	 * @param obj
	 *            平行运行状态的日志对象
	 * @param targetClazz
	 *            日志启用的目标类
	 */
	public void info(Object obj, Class targetClazz)
	{
		log = LogFactory.getLog(targetClazz);
		if (log.isInfoEnabled()) {
			log.info(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统平行运行状态日志
	 * 
	 * @param obj
	 *            平行运行状态的日志对象
	 */
	public void info(Object obj)
	{
		log = LogFactory.getLog(CLAZZ);
		if (log.isInfoEnabled()) {
			log.info(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统踪日志
	 * 
	 * @param obj
	 *            跟踪状态的日志对象
	 * @param targetClazz
	 *            日志启用的目标类
	 */
	public void trace(Object obj, Class targetClazz)
	{
		log = LogFactory.getLog(targetClazz);
		if (log.isTraceEnabled()) {
			log.trace(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统踪日志
	 * 
	 * @param obj
	 *            跟踪状态的日志对象
	 */
	public void trace(Object obj)
	{
		log = LogFactory.getLog(CLAZZ);
		if (log.isTraceEnabled()) {
			log.trace(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统踪日志
	 * 
	 * @param obj
	 *            跟踪状态的日志对象
	 * @param t
	 *            异常对象
	 * @param targetClazz
	 *            日志启用的目标类
	 */
	public void trace(Object obj, Throwable t, Class targetClazz)
	{
		log = LogFactory.getLog(targetClazz);
		if (log.isTraceEnabled()) {
			log.trace(obj, t);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统踪日志
	 * 
	 * @param obj
	 *            跟踪状态的日志对象
	 * @param t
	 *            异常对象
	 */
	public void trace(Object obj, Throwable t)
	{
		log = LogFactory.getLog(CLAZZ);
		if (log.isTraceEnabled()) {
			log.trace(obj, t);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统警告日志
	 * 
	 * @param obj
	 *            警告日志对象
	 * @param targetClazz
	 *            日志启用的目标类
	 */
	public void warn(Object obj, Class targetClazz)
	{
		log = LogFactory.getLog(targetClazz);
		if (log.isTraceEnabled()) {
			log.trace(obj);
		}
	}

	/**
	 * 系统运行日志 方法说明：记录系统警告日志
	 * 
	 * @param obj
	 *            警告日志对象
	 */
	public void warn(Object obj)
	{
		log = LogFactory.getLog(CLAZZ);
		if (log.isTraceEnabled()) {
			log.trace(obj);
		}
	}

	/**
	 * 系统行为日志 方法说明：保存系统行为日志
	 * 
	 * @param actlog
	 *            行为日志对象
	 * @return 返回日志添加成功状态
	 */
	@Transactional(readOnly = false)
	public Boolean saveLog(BehaveLog actlog)
	{
		/*
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			String driver = (String) SYSTEM_PROPERTIES.get("log4j.appender.jdbc2.dbclass");
			String username = (String) SYSTEM_PROPERTIES.get("log4j.appender.jdbc2.username");
			String password = (String) SYSTEM_PROPERTIES.get("log4j.appender.jdbc2.password");
			String url = (String) SYSTEM_PROPERTIES.get("log4j.appender.jdbc2.url");

			boolean isConfed = false;
			isConfed = StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
					|| StringUtils.isEmpty(url);
			if (isConfed) {
				String jndiName = (String) SYSTEM_PROPERTIES.get("log4j.appender.jdbc2.jndiName");
				con = DbHelper.lookupDataSource(jndiName).getConnection();

			} else {
				Class.forName(driver);
				con = DriverManager.getConnection(url, username, password);
			}

			String sql = "insert into hea_accesslog(LOGID,USER_UUID,USER_NAME,INDEX_UUID,INDEX_NAME,ACCESS_IP,ACCESS_TIME,REMARK,ACCESS_TYPE,APP_ID,APP_NAME) values(?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			java.util.Random random = new java.util.Random();
			double d = random.nextDouble();
			pstmt.setString(1, MD5.md5(System.currentTimeMillis() + "" + d));
			pstmt.setString(2, actlog.getUseruuid());
			pstmt.setString(3, actlog.getUsername());
			pstmt.setString(4, actlog.getIndexuuid());
			pstmt.setString(5, actlog.getIndexname());
			pstmt.setString(6, actlog.getAccessip());

			pstmt.setString(7, actlog.getAccesstime());
			pstmt.setString(8, actlog.getRemark());
			pstmt.setString(9, actlog.getAccesstype());
			pstmt.setString(10, actlog.getAppId());
			pstmt.setString(11, actlog.getAppName());

			int cnt = pstmt.executeUpdate();

			return cnt > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} finally {
			DbHelper.destroy(con, pstmt, null);
		}
		*/
		return true;
	}

	/**
	 * 系统行为日志 方法说明：保存系统行为日志
	 * 
	 * @param logmessage
	 *            行为日志对象
	 * @param req
	 *            请求对象
	 * @return 返回日志添加成功状态
	 */
	public Boolean saveLog(String logmessage, ServletRequest req)
	{
		BehaveLog log = new BehaveLog();
		log.setAccessip(req.getRemoteHost());
		log.setAccesstime(DateUtils.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		log.setRemark(logmessage);
		HttpServletRequest request = (HttpServletRequest) req;

		String appId = request.getSession().getServletContext().getInitParameter("appId");
		String appName = request.getSession().getServletContext().getInitParameter("appName");
		log.setAppId(appId);
		log.setAppName(appName);
		log.setAppName(appName);
		return saveLog(log);

	}

}
