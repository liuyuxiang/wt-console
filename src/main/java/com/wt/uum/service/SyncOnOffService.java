package com.wt.uum.service;

import com.wt.uum2.bean.Setting;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-10
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface SyncOnOffService
{

	/**
	 * 方法说明：
	 * 
	 * @param setting
	 *            setting
	 */
	public abstract void init(Setting setting);

	/**
	 * 方法说明：
	 * 
	 * @return boolean
	 */
	public abstract boolean isSyncOn();

	/**
	 * 方法说明：
	 * 
	 */
	public abstract void syncOff();

	/**
	 * 方法说明：syncOn
	 *
	 */
	public abstract void syncOn();

}