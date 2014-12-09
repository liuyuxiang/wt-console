package com.wt.uum2.event;

import java.util.Collection;
import java.util.List;

import com.wt.uum2.domain.Event;
import com.wt.uum2.listener.EventListener;
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
public class EventListenerHandlerThread extends Thread
{

	/**
	 * 
	 */
	private EventValidator eventValidator;
	/**
	 * 
	 */
	private EventService eventService;
	/**
	 * 
	 */
	List<EventListener> eventListeners;
	/**
	 * 
	 */
	private Event event;
	/**
	 * 
	 */
	private Collection<Event> events;

	public void setEventValidator(EventValidator eventValidator)
	{
		this.eventValidator = eventValidator;
	}

	public void setEventService(EventService eventService)
	{
		this.eventService = eventService;
	}

	public void setEventListeners(List<EventListener> eventListeners)
	{
		this.eventListeners = eventListeners;
	}

	public void setEvent(Event event)
	{
		this.event = event;
	}

	public void setEvents(Collection<Event> events)
	{
		this.events = events;
	}

	/**
	 * 
	 */
	public EventListenerHandlerThread()
	{

	}

	/**
	 * @param eventValidator
	 *            eventValidator
	 * @param eventListeners
	 *            eventListeners
	 * @param event
	 *            event
	 * @param eventService
	 *            eventService
	 */
	public EventListenerHandlerThread(EventValidator eventValidator,
			List<EventListener> eventListeners, Event event, EventService eventService)
	{
		this(eventValidator, eventListeners, event);
		this.eventService = eventService;
	}

	/**
	 * @param eventValidator
	 *            eventValidator
	 * @param eventListeners
	 *            eventListeners
	 * @param events
	 *            events
	 * @param eventService
	 *            eventService
	 */
	public EventListenerHandlerThread(EventValidator eventValidator,
			List<EventListener> eventListeners, Collection<Event> events, EventService eventService)
	{
		this(eventValidator, eventListeners, events);
		this.eventService = eventService;
	}

	/**
	 * @param eventValidator
	 *            eventValidator
	 * @param eventListeners
	 *            eventListeners
	 * @param event
	 *            event
	 */
	public EventListenerHandlerThread(EventValidator eventValidator,
			List<EventListener> eventListeners, Event event)
	{
		super();
		this.eventValidator = eventValidator;
		this.eventListeners = eventListeners;
		this.event = event;
	}

	/**
	 * @param eventValidator
	 *            eventValidator
	 * @param eventListeners
	 *            eventListeners
	 * @param events
	 *            events
	 */
	public EventListenerHandlerThread(EventValidator eventValidator,
			List<EventListener> eventListeners, Collection<Event> events)
	{
		super();
		this.eventValidator = eventValidator;
		this.eventListeners = eventListeners;
		this.events = events;
	}

	@Override
	public void run()
	{
		if (event != null) {
			try {
				eventValidator.validate(event);
				if (eventListeners != null) {
					for (EventListener listener : eventListeners) {
						listener.fire(event);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (events != null) {

			try {
				for (Event e : events) {
					eventValidator.validate(e);
				}
				if (eventListeners != null) {
					for (EventListener listener : eventListeners) {
						listener.fire(events);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
