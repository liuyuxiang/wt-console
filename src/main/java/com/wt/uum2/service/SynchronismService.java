package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.SyncLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface SynchronismService
{

	/**
	 * 
	 * 方法说明：读取同步日志
	 * 
	 * @param uuid
	 *            uuid
	 * @return SyncLog
	 */
	public SyncLog getSyncLog(String uuid);

	/**
	 * 
	 * 方法说明：创建同步日志
	 * 
	 * @param syncLog
	 *            syncLog
	 * @return int
	 */
	public int createSyncLog(SyncLog syncLog);

	/**
	 * 
	 * 方法说明：根据事件对象取得同步日志列表
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	public List<SyncLog> getSyncLogByEvent(Event event);

}
