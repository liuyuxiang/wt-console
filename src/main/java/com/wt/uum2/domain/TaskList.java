package com.wt.uum2.domain;

import java.util.Set;

/**
 * @author Alex
 * 
 *         待办列表
 */
public class TaskList
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930475255743128792L;

	/**
	 * 关键字--我们UUM自己设定
	 */
	private String uuid;

	private String id;

	private String linkName;

	private String linkUrl;

	private Integer linkOrder;

	private Set<Group> adminGroups;

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLinkName()
	{
		return linkName;
	}

	public void setLinkName(String linkName)
	{
		this.linkName = linkName;
	}

	public String getLinkUrl()
	{
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl)
	{
		this.linkUrl = linkUrl;
	}

	public Integer getLinkOrder()
	{
		return linkOrder;
	}

	public void setLinkOrder(Integer linkOrder)
	{
		this.linkOrder = linkOrder;
	}

	public Set<Group> getAdminGroups()
	{
		return adminGroups;
	}

	public void setAdminGroups(Set<Group> adminGroups)
	{
		this.adminGroups = adminGroups;
	}

	public static long getSerialVersionUID()
	{
		return serialVersionUID;
	}

}
