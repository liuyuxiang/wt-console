package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.wt.uum2.event.EventType;
import com.wt.uum2.event.MulitHashMap;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-25
 * 作者:	Li Chenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Event implements Serializable
{

	/**
	 * 待处理
	 */
	public static final int PENDING = 0;

	/**
	 * 失败
	 */
	public static final int FAILED = 1;

	/**
	 * 成功
	 */
	public static final int SUCCESSFUL = 2;

	/**
	 * 处理中
	 */
	public static final int INPROCESS = 3;

	/**
	 * 警告
	 */
	public static final int WARING = 4;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3964656427637505559L;

	/**
	 * 事件类型
	 */
	private EventType eventType;

	/**
	 * 事件类型
	 */
	private String type;

	/**
	 * 事件主键
	 */
	private String uuid;

	/**
	 * 事件发生时间
	 */
	private Date date;

	/**
	 * 事件参数
	 */
	private Set<EventParam> params;

	/**
	 * 资源主键
	 */
	private String resourceuuid;

	/**
	 * 资源标识
	 */
	private String resourceId;

	/**
	 * 资源类型
	 */
	private String resourceType;

	/**
	 * 资源名
	 */
	private String resourceName;

	/**
	 * 操作者主键
	 */
	private String operUUID;

	/**
	 * 操作者id
	 */
	private String operator;

	/**
	 * 操作者名称
	 */
	private String operatorName;

	/**
	 * 操作者部门信息
	 */
	private String operatorDept;

	/**
	 * 操作者操作IP
	 */
	private String operatorIpAdderss;

	/**
	 * 依赖事件
	 */
	private Event dependEvent;

	/**
	 * 处理状态,0为处理中,1为异常,2为正常结束,3warning
	 */
	private int status;

	/**
	 * 有序序列
	 */
	private Long sequence;

	/**
	 * 
	 */
	public Event()
	{
		super();
		setDate(new Date());
		setStatus(Event.PENDING);
	}

	/**
	 * @return dependEvent
	 */
	public Event getDependEvent()
	{
		return dependEvent;
	}

	/**
	 * @return ssssssss
	 */
	public String getResourceName()
	{
		return resourceName;
	}

	/**
	 * @param resourceName
	 *            the resourceName to set
	 */
	public void setResourceName(String resourceName)
	{
		this.resourceName = resourceName;
	}

	/**
	 * @param dependEvent
	 *            the dependEvent to set
	 */
	public void setDependEvent(Event dependEvent)
	{
		this.dependEvent = dependEvent;
	}

	/**
	 * @return ssssssss
	 */
	public String getOperUUID()
	{
		return operUUID;
	}

	/**
	 * @param operUUID
	 *            the operUUID to set
	 */
	public void setOperUUID(String operUUID)
	{
		this.operUUID = operUUID;
	}

	public String getResourceuuid()
	{
		return resourceuuid;
	}

	public void setResourceuuid(String resourceuuid)
	{
		this.resourceuuid = resourceuuid;
	}

	/**
	 * @return ssssssss
	 */
	public String getResourceId()
	{
		return resourceId;
	}

	/**
	 * @param resourceId
	 *            the resourceId to set
	 */
	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}

	/**
	 * @return ssssssss
	 */
	public String getResourceType()
	{
		return resourceType;
	}

	/**
	 * @param resourceType
	 *            the resourceType to set
	 */
	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}

	/**
	 * 
	 * 方法说明：取得event修改后的值
	 * 
	 * @return 多值map
	 */
	public MulitHashMap getParamValuesMap()
	{
		if (CollectionUtils.isEmpty(params)) {
			return new MulitHashMap(0);
		}
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

	/**
	 * 
	 * 方法说明：取得event修改前的值
	 * 
	 * @return 多值map
	 */
	public MulitHashMap getParamOriginalValuesMap()
	{
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

	public Set<EventParam> getParams()
	{
		return params;
	}

	public void setParams(Set<EventParam> params)
	{
		this.params = params;
	}

	/**
	 * 方法说明：setParams
	 * 
	 * @param params
	 *            params
	 */
	public void setParams(List<EventParam> params)
	{
		this.params = new HashSet<EventParam>();
		this.params.addAll(params);
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public Date getDate()
	{
		return date;
	}

	protected void setDate(Date date)
	{
		this.date = date;
	}

	public EventType getEventType()
	{
		return eventType;
	}

	/**
	 * 方法说明：setEventType
	 * 
	 * @param eventType
	 *            eventType
	 */
	public void setEventType(EventType eventType)
	{
		this.eventType = eventType;
		this.type = eventType.toString();
	}

	public String getType()
	{
		return type;
	}

	/**
	 * 方法说明：setType
	 * 
	 * @param type
	 *            type
	 */
	public void setType(String type)
	{
		this.eventType = Enum.valueOf(EventType.class, type);
		this.type = type;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}

	public String getOperatorDept()
	{
		return operatorDept;
	}

	public void setOperatorDept(String operatorDept)
	{
		this.operatorDept = operatorDept;
	}

	/**
	 * @return ssssssss
	 */
	public String getOperatorIpAdderss()
	{
		return operatorIpAdderss;
	}

	/**
	 * @param operatorIpAdderss
	 *            the operatorIpAdderss to set
	 */
	public void setOperatorIpAdderss(String operatorIpAdderss)
	{
		this.operatorIpAdderss = operatorIpAdderss;
	}

	/**
	 * @return ssssssss
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

	public Long getSequence()
	{
		return sequence;
	}

	public void setSequence(Long sequence)
	{
		this.sequence = sequence;
	}

}
