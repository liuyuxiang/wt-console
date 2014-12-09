package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	nautilus
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Attribute implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -450409134878389702L;

	/**
	 * 所属资源
	 */
	private Resource ownerResource;

	/**
	 * 属性类型
	 */
	private AttributeType type;

	/**
	 * 属性类型
	 */
	private String typeUUID;

	/**
	 * 所属资源
	 */
	private String ownerResourceUUID;

	/**
	 * 主键
	 */
	private String uuid;

	/**
	 * 属性值
	 */
	private String value;

	/**
	 * 
	 */
	public Attribute()
	{
		super();
	}

	/**
	 * @param owner
	 *            owner
	 * @param type
	 *            type
	 */
	public Attribute(Resource owner, AttributeType type)
	{
		this();
		setType(type);
		setOwnerResource(owner);
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
	 * 属性值集合
	 */
	private Set<AttributeValue> attValues;

	public String getOwnerResourceUUID()
	{
		return ownerResourceUUID;
	}

	public void setOwnerResourceUUID(String ownerResourceUUID)
	{
		this.ownerResourceUUID = ownerResourceUUID;
	}

	public String getTypeUUID()
	{
		return typeUUID;
	}

	public void setTypeUUID(String typeUUID)
	{
		this.typeUUID = typeUUID;
	}

	public Set<AttributeValue> getAttValues()
	{
		return attValues;
	}

	public void setAttValues(Set<AttributeValue> attValues)
	{
		this.attValues = attValues;
	}

	public Resource getOwnerResource()
	{
		return ownerResource;
	}

	public AttributeType getType()
	{
		return type;
	}

	public String getUuid()
	{
		return uuid;
	}

	/**
	 * 方法说明：setOwnerResource
	 * 
	 * @param ownerResource
	 *            ownerResource
	 */
	public void setOwnerResource(Resource ownerResource)
	{
		this.ownerResource = ownerResource;
		if (ownerResource != null) {
			this.ownerResourceUUID = ownerResource.getUuid();
		}

	}

	/**
	 * 方法说明：setType
	 * 
	 * @param type
	 *            type
	 */
	public void setType(AttributeType type)
	{
		this.type = type;
		if (type != null) {
			this.typeUUID = type.getUuid();
		}
	}

	protected void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

}
