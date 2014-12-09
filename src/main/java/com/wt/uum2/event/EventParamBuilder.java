package com.wt.uum2.event;

import java.util.ArrayList;
import java.util.List;

import com.wt.uum2.domain.EventParam;

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
public class EventParamBuilder
{

	/**
	 * 
	 */
	private List<EventParam> paramList = new ArrayList<EventParam>();

	/**
	 * 方法说明：clear
	 * 
	 * @return EventParamBuilder
	 */
	public EventParamBuilder clear()
	{
		paramList.clear();
		return this;
	}

	/**
	 * 方法说明：add
	 * 
	 * @param key
	 *            key
	 * @param originalValue
	 *            originalValue
	 * @param value
	 *            value
	 * @return EventParamBuilder
	 */
	public EventParamBuilder add(String key, String originalValue, String value)
	{
		EventParam eventParam = new EventParam();
		eventParam.setKey(key);
		eventParam.setOriginalValue(originalValue);
		eventParam.setValue(value);
		paramList.add(eventParam);
		return this;
	}

	public List<EventParam> getList()
	{
		return paramList;
	}
}
