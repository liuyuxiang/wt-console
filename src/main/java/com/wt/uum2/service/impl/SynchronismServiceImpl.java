package com.wt.uum2.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.dao.EventDao;
import com.wt.uum2.dao.SyncLogDao;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.SyncLog;
import com.wt.uum2.service.SynchronismService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class SynchronismServiceImpl implements SynchronismService
{

	/**
	 * 
	 */
	private SyncLogDao syncLogDao;

	/**
	 * 
	 */
	private EventDao eventDao;

	/**
	 * @param eventDao
	 *            the eventDao to set
	 */
	public void setEventDao(EventDao eventDao)
	{
		this.eventDao = eventDao;
	}

	/**
	 * @param syncLogDao
	 *            the syncLogDao to set
	 */
	public void setSyncLogDao(SyncLogDao syncLogDao)
	{
		this.syncLogDao = syncLogDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.SynchronismService#getSyncLog(java.lang.String)
	 */
	/**
	 * 方法说明：getSyncLog
	 * 
	 * @param uuid
	 *            uuid
	 * @return SyncLog
	 */
	@Transactional(readOnly = true)
	public SyncLog getSyncLog(String uuid)
	{
		return syncLogDao.getSyncLogByUuid(uuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.SynchronismService#createSyncLog(com.wt.uum2.domain.SyncLog)
	 */
	/**
	 * 方法说明：createSyncLog
	 * 
	 * @param syncLog
	 *            syncLog
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createSyncLog(SyncLog syncLog)
	{
		Event event = eventDao.getEvent(syncLog.getEventuuid());
		if (event != null) {

			switch (syncLog.getStatus()) {
			case SyncLog.FAILED:
				event.setStatus(Event.FAILED);
				break;

			case SyncLog.WARING:
			{
				if (event.getStatus() != Event.WARING && event.getStatus() != Event.FAILED) {
					event.setStatus(Event.WARING);
				}
				break;
			}
			default:
			{
				if (event.getStatus() == Event.PENDING) {
					event.setStatus(Event.SUCCESSFUL);
				}
				break;
			}
			}

			eventDao.update(event);
		}
		syncLogDao.save(syncLog);
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.SynchronismService#getSyncLogByEvent(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getSyncLogByEvent
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<SyncLog> getSyncLogByEvent(Event event)
	{
		return syncLogDao.getSyncLogByEvent(event);
	}

}
