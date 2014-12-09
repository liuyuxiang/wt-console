package com.wt.hea.common.model;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 系统常量bean
 * 编写日期:	2011-5-26
 * 作者:	yuanmingmin
 * 
 * 历史记录
 * 1、修改日期： 
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class SystemCode implements java.io.Serializable
{

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String regCode;
	/**
	 * 
	 */
	private String regType;
	/**
	 * 
	 */
	private String regName;
	/**
	 * 
	 */
	private String regDesc;
	/**
	 * 
	 */
	private String regProp;
	/**
	 * 
	 */
	private Integer regOrder;
	/**
	 * 
	 */
	private String regLabel;
	/**
	 * 
	 */
	private String regValue;
	/**
	 * 
	 */
	private String parentId;
	/**
	 * 
	 */
	private Integer indexLevel;
	/**
	 * 
	 */
	private Integer haschild;
	/**
	 * 
	 */
	private String levelCode;

	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 
	 */
	private SystemCode parentSystemCode;

	public String getRegCode()
	{
		return regCode;
	}

	public void setRegCode(String regCode)
	{
		this.regCode = regCode;
	}

	public String getRegType()
	{
		return regType;
	}

	public void setRegType(String regType)
	{
		this.regType = regType;
	}

	public String getRegName()
	{
		return regName;
	}

	public void setRegName(String regName)
	{
		this.regName = regName;
	}

	public String getRegDesc()
	{
		return regDesc;
	}

	public void setRegDesc(String regDesc)
	{
		this.regDesc = regDesc;
	}

	public String getRegProp()
	{
		return regProp;
	}

	public void setRegProp(String regProp)
	{
		this.regProp = regProp;
	}

	public Integer getRegOrder()
	{
		return regOrder;
	}

	public void setRegOrder(Integer regOrder)
	{
		this.regOrder = regOrder;
	}

	public String getRegValue()
	{
		return regValue;
	}

	public void setRegValue(String regValue)
	{
		this.regValue = regValue;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public Integer getIndexLevel()
	{
		return indexLevel;
	}

	public void setIndexLevel(Integer indexLevel)
	{
		this.indexLevel = indexLevel;
	}

	public Integer getHaschild()
	{
		return haschild;
	}

	public void setHaschild(Integer haschild)
	{
		this.haschild = haschild;
	}

	public SystemCode getParentSystemCode()
	{
		return parentSystemCode;
	}

	public void setParentSystemCode(SystemCode parentSystemCode)
	{
		this.parentSystemCode = parentSystemCode;
	}

	public String getLevelCode()
	{
		return levelCode;
	}

	public void setLevelCode(String levelCode)
	{
		this.levelCode = levelCode;
	}

	public String getRegLabel()
	{
		return regLabel;
	}

	public void setRegLabel(String regLabel)
	{
		this.regLabel = regLabel;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

}