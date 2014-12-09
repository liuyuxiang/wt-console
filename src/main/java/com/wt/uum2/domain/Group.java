package com.wt.uum2.domain;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	nautilus
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "group")
public class Group extends Resource
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -250699029657217406L;

	/**
	 * 组名
	 */
	@XmlElement
	private String name;

	/**
	 * 是否有子组
	 */
	private boolean hasChildren;

	/**
	 * 父组
	 */
	private Set<Group> parents;

	/**
	 * 组编号
	 */
	@XmlElement
	private String code;

	/**
	 * 组类型0--普通组，1--应用组
	 */
	private String groupType;

	/**
	 * 应用组是否自动加载标识0或者空为不自动加载，1表示该应用系统同步到目录默认为TRUE，并且帐号为门户帐号
	 */
	private String appGroupType;

	public String getAppGroupType()
	{
		return appGroupType;
	}

	public void setAppGroupType(String appGroupType)
	{
		this.appGroupType = appGroupType;
	}

	public String getGroupType()
	{
		return groupType;
	}

	public void setGroupType(String groupType)
	{
		this.groupType = groupType;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public boolean isHasChildren()
	{
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren)
	{
		this.hasChildren = hasChildren;
	}

	/**
	 * 
	 */
	public Group()
	{
		super();
		setType(1);
	}

	public String getName()
	{
		return name;
	}

	public Set<Group> getParents()
	{
		return parents;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setParents(Set<Group> parents)
	{
		this.parents = parents;
	}
}
