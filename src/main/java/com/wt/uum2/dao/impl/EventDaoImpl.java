package com.wt.uum2.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.EventDao;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-25
 * 作者:	Liuyuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class EventDaoImpl extends BaseDaoSupport<Event> implements EventDao
{

	public List<Event> getEventList()
	{
		return getSession().createCriteria(Event.class).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventDao#getEvent(java.lang.String)
	 */
	/**
	 * 方法说明：getEvent
	 * 
	 * @param uuid
	 *            uuid
	 * @return Event
	 */
	public Event getEvent(String uuid)
	{
		return (Event) getSession().createCriteria(Event.class).add(Restrictions.eq("uuid", uuid))
				.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventDao#getEventList(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getEventList
	 * 
	 * @param resourceuuid
	 *            resourceuuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getEventList(String resourceuuid, Integer page, Integer pagesize)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		Criteria l = getSession().createCriteria(Event.class).setProjection(
				Projections.countDistinct("uuid"));

		if (StringUtils.isNotBlank(resourceuuid)) {
			l.add(Restrictions.eq("resourceuuid", resourceuuid));
		}

		Long size = (Long) l.uniqueResult();

		pager.setQuantityOfData(size.intValue());

		pager.compute();

		Criteria c = getSession().createCriteria(Event.class);

		if (StringUtils.isNotBlank(resourceuuid)) {
			c.add(Restrictions.eq("resourceuuid", resourceuuid));
		}

		c.addOrder(Order.desc("date"));
		c.addOrder(Order.desc("sequence"));
		c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		c.setFirstResult(pager.getDataStart());
		c.setMaxResults(pager.getPageSize());

		result.setList(c.list());

		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventDao#getEventParam(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getEventParam
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	public List<EventParam> getEventParam(Event event)
	{
		return getSession().createCriteria(EventParam.class).add(Restrictions.eq("event", event))
				.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventDao#updateEvent(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：updateEvent
	 * 
	 * @param event
	 *            event
	 */
	public void updateEvent(Event event)
	{

		getSession().update(getSession().merge(event));

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventDao#getEventSequence()
	 */
	/**
	 * 方法说明：getEventSequence
	 * 
	 * @return long
	 */
	public long getEventSequence()
	{
		if(isMysqlDB()){
			BigInteger b = (BigInteger) getSession().createSQLQuery(
					"select ifnull(e.seq,count(1))+1 from U2_Event as e").uniqueResult();
	
			return b.longValue();
		}else{
			BigDecimal b = (BigDecimal) getSession().createSQLQuery(
					"select U2_EVENT_SEQUENCE.Nextval from dual").uniqueResult();
	
			return b.longValue();
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.EventDao#getEventList(int, int, java.lang.Integer, java.lang.Boolean)
	 */
	/**
	 * 方法说明：getEventList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param status
	 *            status
	 * @param order
	 *            order
	 * @return UserPageResult
	 */
	public UserPageResult<Event> getEventList(int page, int pagesize, Integer status, Boolean order)
	{
		UserPageResult<Event> result = new UserPageResult<Event>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);

		Criteria l = getSession().createCriteria(Event.class).setProjection(
				Projections.countDistinct("uuid"));

		if (status != null) {
			l.add(Restrictions.eq("status", status));
		}

		Long size = (Long) l.uniqueResult();

		pager.setQuantityOfData(size.intValue());

		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(size.intValue());
		}

		pager.compute();

		Criteria c = getSession().createCriteria(Event.class);

		if (status != null) {
			c.add(Restrictions.eq("status", status));
		}

		if (order != null && order) {
			c.addOrder(Order.asc("date"));
			c.addOrder(Order.asc("sequence"));
		} else {
			c.addOrder(Order.desc("date"));
			c.addOrder(Order.desc("sequence"));
		}
		c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		c.setFirstResult(pager.getDataStart());
		c.setMaxResults(pager.getPageSize());

		result.setList(c.list());

		result.setPager(pager);
		return result;
	}

}
