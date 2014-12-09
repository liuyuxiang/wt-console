package com.wt.uum2.domain;

import java.io.Serializable;

/**
 * @author Alex
 * 
 *         候选项
 */
public class CandidateItem implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9114972771888008632L;

	/**
	 * 主键
	 */
	private String uuid;

	/**
	 * 是否是默认候选项
	 */
	private Boolean isDefault;

	/**
	 * 候选项值
	 */
	private String value;

	/**
	 * 候选项值的说明
	 */
	private String caption;

	/**
	 * 排序号
	 */
	private Long sortNumber;

	/**
	 * 扩展属性
	 */
	private AttributeType attributeType;

	public AttributeType getAttributeType()
	{
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType)
	{
		this.attributeType = attributeType;
	}

	public Boolean getIsDefault()
	{
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getCaption()
	{
		return caption;
	}

	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	public Long getSortNumber()
	{
		return sortNumber;
	}

	public void setSortNumber(Long sortNumber)
	{
		this.sortNumber = sortNumber;
	}

	public static long getSerialVersionUID()
	{
		return serialVersionUID;
	}

	public String getUuid()
	{
		return uuid;
	}

	protected void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

}
