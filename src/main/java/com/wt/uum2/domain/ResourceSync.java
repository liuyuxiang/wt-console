package com.wt.uum2.domain;

/**
 * 同步配置表
 */
public class ResourceSync
{

	private static final long serialVersionUID = -250699029657217406L;

	/**
	 * 属性类型
	 */
	private String propType;

	/**
	 * 用户属性名
	 */
	private String propName;

	/**
	 * 属性同步时的处理方法
	 */
	private String transFunc;

	/**
	 * 资源类型
	 */
	private Integer type;

	/**
	 * 主键
	 */
	private String uuid;

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getPropType()
	{
		return propType;
	}

	public void setPropType(String propType)
	{
		this.propType = propType;
	}

	public String getPropName()
	{
		return propName;
	}

	public void setPropName(String propName)
	{
		this.propName = propName;
	}

	public String getTransFunc()
	{
		return transFunc;
	}

	public void setTransFunc(String transFunc)
	{
		this.transFunc = transFunc;
	}
}