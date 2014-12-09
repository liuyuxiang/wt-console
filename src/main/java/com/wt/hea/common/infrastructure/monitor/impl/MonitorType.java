package com.wt.hea.common.infrastructure.monitor.impl;

/**
 * 
 * <pre>
 * 业务名: “监控"枚举
 * 功能说明: 用于监控接口跟据什么类型来执行相应的命令
 * 编写日期:	2011-4-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-4-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public enum MonitorType {
	/**
	 * cmd命令标识
	 */
	CMD,
	/**
	 * sql命令标识，sql语句
	 */
	 SQL,
	 /**
	  * url地址
	  */
	 URL
}
