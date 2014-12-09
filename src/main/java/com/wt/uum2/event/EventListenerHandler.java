package com.wt.uum2.event;

import java.util.List;

import com.wt.uum2.domain.Event;
import com.wt.uum2.service.EventService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class EventListenerHandler
{

	/**
	 * 
	 */
	private EventService eventService;

	/**
	 * 方法说明：createHandlerThread
	 * 
	 * @return EventListenerHandlerThread
	 */
	protected EventListenerHandlerThread createHandlerThread()
	{
		return null;
	}

	public void setEventService(EventService eventService)
	{
		this.eventService = eventService;
	}

	/**
	 * 方法说明：handle
	 * 
	 * @param event
	 *            event
	 */
	public void handle(Event event)
	{
		eventService.createEvent(event);
	}

	/**
	 * 方法说明：handle
	 * 
	 * @param events
	 *            events
	 */
	public void handle(List<Event> events)
	{
		for (Event event : events) {
			handle(event);
		}
	}

}
