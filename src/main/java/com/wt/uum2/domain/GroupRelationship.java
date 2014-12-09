package com.wt.uum2.domain;

import java.io.Serializable;

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
public class GroupRelationship implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6570917911087352763L;
	private Group child;
	private Group parent;
	private String childUUID;
	private String parentUUID;

	public Group getChild()
	{
		return child;
	}

	public void setChild(Group child)
	{
		this.child = child;
	}

	public Group getParent()
	{
		return parent;
	}

	public void setParent(Group parent)
	{
		this.parent = parent;
	}

	public String getChildUUID()
	{
		return childUUID;
	}

	public void setChildUUID(String childUUID)
	{
		this.childUUID = childUUID;
	}

	public String getParentUUID()
	{
		return parentUUID;
	}

	public void setParentUUID(String parentUUID)
	{
		this.parentUUID = parentUUID;
	}

}
