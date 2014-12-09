package com.wt.uum2.domain;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ResourceTempDetails
{

	private String uuid;

	private Integer type;

	private String key;

	private String originalValue;

	private String value;

	private String name;

	private String eventuuid;

	private boolean status;

	/**
	 * @return ssssssss
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type)
	{
		this.type = type;
	}

	/**
	 * @return ssssssss
	 */
	public boolean isStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public String getEventuuid()
	{
		return eventuuid;
	}

	public void setEventuuid(String eventuuid)
	{
		this.eventuuid = eventuuid;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
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

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

}
