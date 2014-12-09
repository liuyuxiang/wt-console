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
public class ResourceAdminGroup implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1917312692338641099L;
	private Resource resource;
	private Group group;
	private String resourceUUID;
	private String groupUUID;

	public String getResourceUUID()
	{
		return resourceUUID;
	}

	public void setResourceUUID(String resourceUUID)
	{
		this.resourceUUID = resourceUUID;
	}

	public Resource getResource()
	{
		return resource;
	}

	public void setResource(Resource resource)
	{
		this.resource = resource;
	}

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(Group group)
	{
		this.group = group;
	}

	public String getGroupUUID()
	{
		return groupUUID;
	}

	public void setGroupUUID(String groupUUID)
	{
		this.groupUUID = groupUUID;
	}
}
