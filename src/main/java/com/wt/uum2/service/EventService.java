package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;
import com.wt.uum2.event.MulitHashMap;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-10-9
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface EventService
{

	/**
	 * 方法说明：getEventByEventUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return Event
	 */
	public Event getEventByEventUUID(String uuid);

	/**
	 * 
	 * 方法说明：创建事件
	 * 
	 * @param event
	 *            event
	 * @return int
	 */
	public int createEvent(Event event);

	/**
	 * 
	 * 方法说明：关闭事件,主要是更新事件处理状态
	 * 
	 * @param event
	 *            event
	 */
	public void closeEvent(Event event);

	/**
	 * 方法说明：取未处理的event事件
	 * 
	 * @param i
	 *            如果为负数则取全列表
	 * @return List
	 */
	public List<Event> getUntreatedEventListBySize(int i);

	/**
	 * 方法说明：取event对应的eventparam列表
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	public List<EventParam> getEventParams(Event event);

	/**
	 * 方法说明：取event对应的修改之后的属性值列表
	 * 
	 * @param event
	 *            event
	 * @return MulitHashMap
	 */
	public MulitHashMap getParamValuesMap(Event event);

	/**
	 * 方法说明：取event对应的修改之后的属性值列表
	 * 
	 * @param event
	 *            event
	 * @return MulitHashMap
	 */
	public MulitHashMap getParamOriginalValuesMap(Event event);

}
