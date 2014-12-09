package com.wt.hea.common.infrastructure.monitor;

import com.wt.hea.common.infrastructure.monitor.impl.MonitorType;

/**
 * 
 * <pre>
 * 业务名: 获取系统环境信息，提供一些监控信息
 * 功能说明: 
 * 编写日期:	2011-3-29
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-29
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public interface Monitor {
	/**
	 * 
	 * 方法说明：调用操作系统命令返回服务器监控信息
	 *
	 * @param cmds 系统可识别的命令
	 * @return 返回系统信息
	 */
	String excuteCmd (String ... cmds);
	
	/**
	 * 
	 * 方法说明：获取系统的安装环境，如：java环境变量,ant环境变量等
	 *
	 * @return 返回系统环境信息
	 */
	String getEnvInfo();
	
	/**
	 * 
	 * 方法说明：监控数据库,返回监控信息
	 *
	 * @param args cmd命令参数或sql语句
	 * @param type 监控类型枚举
	 * @return 服务器返回的监控信息
	 */
	String monitDbServer(String[] args,MonitorType type);
	 
	/**
	 * 
	 * 方法说明：监控Web服务器,返回监控信息
	 *
	 * @param args cmd命令参数或sql语句
	 * @param type 监控类型枚举
	 * @return  服务器返回的监控信息
	 */
	String monitWebServer(String [] args,MonitorType type);
}
