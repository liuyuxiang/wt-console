/**
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-3-28
 *
 */
package com.wt.hea.common.infrastructure.logger;

import javax.servlet.ServletRequest;

import com.wt.hea.common.model.BehaveLog;

/**
 * 
 * <pre>
 * 业务名:   日志接口
 * 功能说明: 系统运行日志定义和行为日志定义
 * 编写日期:	2011-3-28
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-28
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("rawtypes")
public interface Logger
{
	/**
	 * 系统运行日志 方法说明： 记录调式信息
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 */
	public void debug(Object obj);

	/**
	 * 系统运行日志 方法说明： 记录调式信息
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param clazz
	 *            日志启用的目标类
	 */

	void debug(Object obj, Class clazz);

	/**
	 * 系统运行日志 方法说明：记录错误日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param clazz
	 *            日志启用的目标类
	 */
	void error(Object obj, Class clazz);

	/**
	 * 系统运行日志 方法说明：记录系统毁灭性的日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param clazz
	 *            日志启用的目标类
	 */
	void fatal(Object obj, Class clazz);

	/**
	 * 系统运行日志 方法说明：记录系统平行运行状态日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param clazz
	 *            日志启用的目标类
	 */
	void info(Object obj, Class clazz);

	/**
	 * 系统运行日志 方法说明：记录系统警告日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param clazz
	 *            日志启用的目标类
	 */
	void warn(Object obj, Class clazz);

	/**
	 * 系统运行日志 方法说明：记录系统踪日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param clazz
	 *            日志启用的目标类
	 */
	void trace(Object obj, Class clazz);

	/**
	 * 系统运行日志 方法说明：记录系统跟踪日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param t
	 *            异常信息对象
	 * @param clazz
	 *            日志启用的目标类
	 */
	void trace(Object obj, Throwable t, Class clazz);

	/**
	 * 系统运行日志 方法说明：记录错误日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 */
	void error(Object obj);

	/**
	 * 系统运行日志 方法说明：记录系统毁灭性的日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 */
	void fatal(Object obj);

	/**
	 * 系统运行日志 方法说明：记录系统平行运行状态日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 */
	void info(Object obj);

	/**
	 * 系统运行日志 方法说明：记录系统警告日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 */
	void warn(Object obj);

	/**
	 * 系统运行日志 方法说明：记录系统踪日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 */
	void trace(Object obj);

	/**
	 * 系统运行日志 方法说明：记录系统跟踪日志
	 * 
	 * @param obj
	 *            字符串或其它日志值
	 * @param t
	 *            异常信息对象
	 */
	void trace(Object obj, Throwable t);

	/**
	 * 系统行为日志 方法说明：保存系统行为日志
	 * 
	 * @param acesslog
	 *            行为日志字段值
	 * @return 返回保存是否成功标识
	 */
	Boolean saveLog(BehaveLog acesslog);

	/**
	 * 
	 * 方法说明：保存系统行为日志
	 * 
	 * @param logmessage
	 *            行为信息描述
	 * @param req
	 *            客户端请求对象
	 * @return 返回保存是否成功标识
	 */
	Boolean saveLog(String logmessage, ServletRequest req);
}
