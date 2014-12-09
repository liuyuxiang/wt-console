package com.wt.uum2.domain;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-12-21
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class EventParam implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3052267555526929647L;

	/**
	 * 
	 */
	private String uuid;
	
	/**
	 * 
	 */
	private Event event;
	
	/**
	 * 
	 */
	private String key;
	
	/**
	 * 初始值
	 */
	private String originalValue;
	
	/**
	 * 现在的值
	 */
	private String value;
	
	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String eventuuid;

	public String getEventuuid() {
		return eventuuid;
	}

	public void setEventuuid(String eventuuid) {
		this.eventuuid = eventuuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOriginalValue()
	{
		return originalValue;
	}

	public void setOriginalValue(String originalValue)
	{
		this.originalValue = originalValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return ssssssss
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
}
