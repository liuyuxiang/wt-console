package com.wt.uum2.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import nak.nsf.dao.support.BaseDaoSupport;

import com.wt.uum2.dao.EventParamDao;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-10-9
 * 作者:Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class EventParamDaoImpl extends BaseDaoSupport<EventParam> implements EventParamDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventParamDao#save(java.util.List)
	 */
	/**
	 * 方法说明：save
	 * 
	 * @param params
	 *            params
	 */
	public void save(List<EventParam> params)
	{
		for (EventParam eventParam : params) {
			this.save(eventParam);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventParamDao#getEventParams(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getEventParams
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	public List<EventParam> getEventParams(Event event)
	{
		return getSession().createCriteria(EventParam.class).add(Restrictions.eq("event", event))
				.list();
	}

}
