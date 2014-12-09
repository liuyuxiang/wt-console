package com.wt.uum2.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.EventDao;
import com.wt.uum2.dao.EventParamDao;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;
import com.wt.uum2.event.MulitHashMap;
import com.wt.uum2.service.EventService;

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
public class EventServiceImpl implements EventService
{

	/**
	 * 
	 */
	private EventDao eventDao;

	/**
	 * 
	 */
	private EventParamDao eventParamDao;

	public void setEventDao(EventDao eventDao)
	{
		this.eventDao = eventDao;
	}

	public void setEventParamDao(EventParamDao eventParamDao)
	{
		this.eventParamDao = eventParamDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.EventService#getEventByEventUUID(java.lang.String)
	 */
	/**
	 * 方法说明：getEventByEventUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return Event
	 */
	@Transactional(readOnly = true)
	public Event getEventByEventUUID(String uuid)
	{
		return eventDao.read(uuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.EventService#createEvent(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：createEvent
	 * 
	 * @param event
	 *            event
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createEvent(Event event)
	{
		if (CollectionUtils.isNotEmpty(event.getParams())){
			for (EventParam eventParam : event.getParams()) {
				if (eventParam.getEvent() == null)
					eventParam.setEvent(event);
			}
		}
		event.setSequence(eventDao.getEventSequence());
		eventDao.save(event);
		return 0;
	}

	/**
	 * 方法说明：createEventParam
	 * 
	 * @param params
	 *            params
	 */
	@Transactional(readOnly = false)
	public void createEventParam(Set<EventParam> params)
	{
		for (EventParam eventParam : params) {
			eventParamDao.save(eventParam);
		}

	}

	/**
	 * 方法说明：createEventParam
	 * 
	 * @param params
	 *            params
	 */
	@Transactional(readOnly = false)
	public void createEventParam(Collection<EventParam> params)
	{
		for (EventParam eventParam : params) {
			eventParamDao.save(eventParam);
		}

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.EventService#closeEvent(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：closeEvent
	 * 
	 * @param event
	 *            event
	 */
	@Transactional(readOnly = false)
	public void closeEvent(Event event)
	{
		int status = (event.getStatus() == Event.PENDING) ? Event.SUCCESSFUL : event.getStatus();
		event.setStatus(status);
		eventDao.updateEvent(event);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.EventService#getUntreatedEventListBySize(int)
	 */
	/**
	 * 方法说明：getUntreatedEventListBySize
	 * 
	 * @param i
	 *            i
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Event> getUntreatedEventListBySize(int i)
	{
		UserPageResult<Event> ru = eventDao.getEventList(1, i, 0, true);
		return ru.getList();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.EventService#getEventParams(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getEventParams
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<EventParam> getEventParams(Event event)
	{
		return eventParamDao.getEventParams(event);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.EventService#getParamOriginalValuesMap(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getParamOriginalValuesMap
	 * 
	 * @param event
	 *            event
	 * @return MulitHashMap
	 */
	@Transactional(readOnly = true)
	public MulitHashMap getParamOriginalValuesMap(Event event)
	{
		List<EventParam> params = getEventParams(event);
		MulitHashMap map = new MulitHashMap(params.size());
		for (EventParam param : params) {
			List<String> valueList = map.get(param.getKey());
			if (valueList == null) {
				valueList = new ArrayList<String>();
				valueList.add(param.getOriginalValue());
				map.put(param.getKey(), valueList);
			} else {
				valueList.add(param.getOriginalValue());
			}
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.EventService#getParamValuesMap(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getParamValuesMap
	 * 
	 * @param event
	 *            event
	 * @return MulitHashMap
	 */
	@Transactional(readOnly = true)
	public MulitHashMap getParamValuesMap(Event event)
	{
		List<EventParam> params = getEventParams(event);
		MulitHashMap map = new MulitHashMap(params.size());
		for (EventParam param : params) {
			List<String> valueList = map.get(param.getKey());
			if (valueList == null) {
				valueList = new ArrayList<String>();
				valueList.add(param.getValue());
				map.put(param.getKey(), valueList);
			} else {
				valueList.add(param.getValue());
			}
		}
		return map;
	}
}
