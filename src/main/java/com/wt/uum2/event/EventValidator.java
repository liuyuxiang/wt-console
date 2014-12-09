package com.wt.uum2.event;

import java.util.List;

import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;
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
public class EventValidator
{

	/**
	 * 
	 */
	private EventService eventService;

	public void setEventService(EventService eventService)
	{
		this.eventService = eventService;
	}

	/**
	 * 方法说明：validate
	 * 
	 * @param event
	 *            event
	 */
	public void validate(Event event)
	{
		if (event == null) {
			throw new IllegalArgumentException("the event is null!");
		}

		if (event.getType() == null) {
			throw new IllegalArgumentException("the type is null!");
		}

		if (event.getDate() == null) {
			throw new IllegalArgumentException("the date is null!");
		}

		List<EventParam> eventParams = eventService.getEventParams(event);

		if (eventParams == null) {
			throw new IllegalArgumentException("the params is null!");
		}

		if (eventParams.isEmpty()) {
			throw new IllegalArgumentException("the event's params is empty!");
		}
		switch (event.getEventType()) {
		case REMOVE_USER_GROUP:
			// validateParams(new ArrayList<EventParam>(event.getParams()),"groupUUID",false);
			break;

		case ADD_USER_GROUP:
			validateParams(eventParams, "groupUUID", false);
			break;

		case REMOVE_GROUP_GROUP:
			// validateParams(new ArrayList<EventParam>(event.getParams()),"parentUUID",false);
			break;

		case ADD_GROUP_GROUP:
			validateParams(eventParams, "parentUUID", false);
			break;

		}

	}

	/**
	 * 方法说明：validateParams
	 * 
	 * @param params
	 *            params
	 * @param key
	 *            key
	 * @param isSingle
	 *            isSingle
	 */
	private void validateParams(List<EventParam> params, String key, boolean isSingle)
	{
		Integer valueCount = 0;

		for (EventParam param : params) {

			if (param.getKey() == null) {
				throw new IllegalArgumentException("key is null!");
			}

			if (param.getKey().equals(key)) {
				valueCount++;
				String value = param.getValue();
				if (value == null) {
					throw new IllegalArgumentException("KEY[" + key + "]'s value is null!");
				}
			}
		}

		if (valueCount == 0) {
			throw new IllegalArgumentException("KEY[" + key + "] is not found!");
		}

		if (valueCount > 1 && isSingle) {
			throw new IllegalArgumentException("KEY[" + key + "] value is not single! size="
					+ valueCount);
		}

	}
}
