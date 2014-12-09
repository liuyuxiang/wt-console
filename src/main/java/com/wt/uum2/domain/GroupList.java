package com.wt.uum2.domain;

import java.io.Serializable;

import com.wt.uum2.constants.ResourceType;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-24
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GroupList implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3610165780129187159L;
	/**
	 * 
	 */
	private int num;
	/**
	 * 
	 */
	private String ID;
	/**
	 * 
	 */
	private int type;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String department;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private String uuid;

	/**
	 * @param uuid
	 *            uuid
	 * @param num
	 *            num
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param name
	 *            name
	 * @param dept
	 *            dept
	 * @param description
	 *            description
	 */
	public GroupList(String uuid, int num, String id, int type, String name, String dept,
			String description)
	{
		this.uuid = uuid;
		this.num = num;
		this.ID = id;
		this.type = type;
		this.name = name;
		this.department = dept;
		this.description = description;
	}

	public int getNum()
	{
		return this.num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public String getID()
	{
		return this.ID;
	}

	public void setID(String iD)
	{
		this.ID = iD;
	}

	public String getType()
	{
		return ResourceType.getStringByType(type);
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDepartment()
	{
		return this.department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getUuid()
	{
		return this.uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * 方法说明：isGroup
	 * 
	 * @return boolean
	 */
	public final boolean isGroup()
	{
		if (ResourceType.valueOf(type) == ResourceType.GROUP) {
			return true;
		}
		return false;
	}

	/**
	 * 方法说明：isUser
	 * 
	 * @return boolean
	 */
	public final boolean isUser()
	{
		if (ResourceType.valueOf(type) == ResourceType.USER) {
			return true;
		}
		return false;
	}

}