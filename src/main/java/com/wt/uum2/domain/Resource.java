package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.collections.CollectionUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 资源
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
public abstract class Resource implements Serializable
{

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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Resource)) {
			return false;
		}
		Resource other = (Resource) obj;
		if (uuid == null) {
			if (other.getUuid() != null) {
				return false;
			}
		} else if (!uuid.equals(other.getUuid())) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6724415281033446931L;

	/**
	 * 创建时间
	 */
	private Date createdTime;

	/**
	 * 修改时间
	 */
	private Date modifiedTime;

	/**
	 * 资源状态
	 */
	private Integer status;

	/**
	 * 资源类型
	 */
	private Integer type;

	/**
	 * 资源主键
	 */
	@XmlElement
	private String uuid;
	
	/**
	 * 排序号
	 */
	private Long order;

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	/**
	 * 管理组
	 */
	private Set<Group> adminGroups;

	public Set<Group> getAdminGroups()
	{
		return adminGroups;
	}

	public void setAdminGroups(Set<Group> adminGroups)
	{
		this.adminGroups = adminGroups;
	}

	/**
	 * 方法说明：addAdminGroup
	 * 
	 * @param group
	 *            group
	 */
	public void addAdminGroup(Group group)
	{
		if (adminGroups == null) {
			adminGroups = new HashSet<Group>();
		}
		adminGroups.add(group);
	}

	/**
	 * 方法说明：removeAdminGroup
	 * 
	 * @param group
	 *            group
	 */
	public void removeAdminGroup(Group group)
	{
		if (CollectionUtils.isNotEmpty(adminGroups)) {
			adminGroups.remove(group);
		}
	}

	/**
	 * 
	 */
	public Resource()
	{
		super();
		createdTime = new Date();
		modifiedTime = new Date();
		order = 0L;
	}

	public Date getCreatedTime()
	{
		return createdTime;
	}

	public Date getModifiedTime()
	{
		return modifiedTime;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Integer getType()
	{
		return type;
	}

	public String getUuid()
	{
		return uuid;
	}

	@SuppressWarnings("unused")
	private void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public void setModifiedTime(Date modifiedTime)
	{
		this.modifiedTime = modifiedTime;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	protected void setType(Integer type)
	{
		this.type = type;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

}
