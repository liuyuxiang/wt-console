package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
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
public interface SyncLogDao extends BaseDao<SyncLog>
{

	/**
	 * 
	 * 方法说明：所有同步日志
	 * 
	 * @return list<SyncLog>
	 */
	public List<SyncLog> getSyncLogList();

	/**
	 * 
	 * 方法说明：所有同步日志
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult<SyncLog>
	 */
	public UserPageResult<SyncLog> getSyncLog(int page, int pagesize);

	/**
	 * 
	 * 方法说明：同步日志
	 * 
	 * @param uuid
	 *            日志主键
	 * @return SyncLog
	 */
	public SyncLog getSyncLogByUuid(String uuid);

	/**
	 * 
	 * 方法说明：同步日志列表
	 * 
	 * @param event
	 *            同步事件
	 * @return List
	 */
	public List<SyncLog> getSyncLogByEvent(Event event);

}
