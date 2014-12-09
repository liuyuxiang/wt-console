package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 业务名:
 * 功能说明: 部门日志
 * 编写日期:	2013-1-9
 * 作者:	Alex
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ResourceLog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6724415281033446831L;

	/**
	 * 主键
	 */
	private String uuid;
	/**
	 * 变更标识
	 */
	private String logid;

	/**
	 * 变更字段
	 */
	private String fieldName;

	/**
	 * 变更前值
	 */
	private String beforeValue;

	/**
	 * 变更后值
	 */
	private String afterValue;

	/**
	 * 编辑人
	 */
	private String editPerson;

	/**
	 * 编辑时间
	 */
	private Date editDate;

	/**
	 * 备注
	 */
	private String remark;

	private String operatorIpAdderss;

	private String resourceuuid;

	/**
	 * @return ssssssss
	 */
	public String getOperatorIpAdderss()
	{
		return operatorIpAdderss;
	}

	/**
	 * @param operatorIpAdderss
	 *            the operatorIpAdderss to set
	 */
	public void setOperatorIpAdderss(String operatorIpAdderss)
	{
		this.operatorIpAdderss = operatorIpAdderss;
	}

	public String getResourceuuid()
	{
		return resourceuuid;
	}

	public void setResourceuuid(String resourceuuid)
	{
		this.resourceuuid = resourceuuid;
	}

	/**
	 * 
	 */
	public ResourceLog()
	{
		super();
		setEditDate(new Date());
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getLogid()
	{
		return logid;
	}

	public void setLogid(String logid)
	{
		this.logid = logid;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getBeforeValue()
	{
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue)
	{
		this.beforeValue = beforeValue;
	}

	public String getAfterValue()
	{
		return afterValue;
	}

	public void setAfterValue(String afterValue)
	{
		this.afterValue = afterValue;
	}

	public String getEditPerson()
	{
		return editPerson;
	}

	public void setEditPerson(String editPerson)
	{
		this.editPerson = editPerson;
	}

	public Date getEditDate()
	{
		return editDate;
	}

	public void setEditDate(Date editDate)
	{
		this.editDate = editDate;
	}

}
