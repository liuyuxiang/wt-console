package com.wt.uum2.domain;

import java.io.Serializable;

/**
 * @author liuyx
 * 
 *         资源
 */
public class ResourceMapping implements Serializable
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceMapping other = (ResourceMapping) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6724415281033446931L;

	/**
	 * 资源主键
	 */
	private String uuid;

	private String mappingid;
	
	private String resourceuuid;
	
	private Resource resource;

	/**
	 * @return ssssssss
	 */
	public Resource getResource()
	{
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(Resource resource)
	{
		this.resource = resource;
	}

	/**
	 * @return ssssssss
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * @return ssssssss
	 */
	public String getMappingid()
	{
		return mappingid;
	}

	/**
	 * @param mappingid the mappingid to set
	 */
	public void setMappingid(String mappingid)
	{
		this.mappingid = mappingid;
	}

	/**
	 * @return ssssssss
	 */
	public String getResourceuuid()
	{
		return resourceuuid;
	}

	/**
	 * @param resourceuuid the resourceuuid to set
	 */
	public void setResourceuuid(String resourceuuid)
	{
		this.resourceuuid = resourceuuid;
	}
	
}
