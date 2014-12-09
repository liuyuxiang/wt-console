package com.wt.uum2.domain;

import java.io.Serializable;

/**
 * @author nautilus
 * 
 *         属性值
 */
public abstract class AttributeValue implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6914481681413275503L;

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeValue other = (AttributeValue) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	/**
	 * 所属属性
	 */
	private Attribute attribute;

	/**
	 * 主键
	 */
	private String uuid;

	/**
	 * 类型
	 */
	private Integer type;

	public Attribute getAttribute()
	{
		return attribute;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setAttribute(Attribute attribute)
	{
		this.attribute = attribute;
	}

	@SuppressWarnings("unused")
	private void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	/**
	 * 方法说明：getValue
	 * 
	 * @return Object
	 */
	public abstract Object getValue();

}
