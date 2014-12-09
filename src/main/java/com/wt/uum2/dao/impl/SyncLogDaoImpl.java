package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.SyncLogDao;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.SyncLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SyncLogDaoImpl extends BaseDaoSupport<SyncLog> implements SyncLogDao
{

	public List<SyncLog> getSyncLogList()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.SyncLogDao#getSyncLog(int, int)
	 */
	/**
	 * 方法说明：getSyncLog
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult<SyncLog> getSyncLog(int page, int pagesize)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.SyncLogDao#getSyncLogByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getSyncLogByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return SyncLog
	 */
	public SyncLog getSyncLogByUuid(String uuid)
	{
		if (uuid == null) {
			return null;
		}
		return (SyncLog) getSession().createCriteria(SyncLog.class)
				.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.SyncLogDao#getSyncLogByEvent(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getSyncLogByEvent
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	public List<SyncLog> getSyncLogByEvent(Event event)
	{
		if (event == null) {
			return new ArrayList<SyncLog>();
		}
		return getSession().createCriteria(SyncLog.class)
				.add(Restrictions.eq("eventuuid", event.getUuid())).list();
	}

}
