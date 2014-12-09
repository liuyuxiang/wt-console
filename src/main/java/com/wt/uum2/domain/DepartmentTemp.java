package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 业务名:
 * 功能说明:部门临时表 
 * 编写日期:	2013-1-8
 * 作者:	Alex
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DepartmentTemp implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4183767867238042588L;

	private String uuid;

	private String deptId;

	private String deptName;

	private Long deptOrder;

	private String deptCode;

	private String deptOrgCode;

	private String parentDeptCode;

	private String isvirtual;

	private String modifyType;

	private Date lastUpdateTime;

	private Date beginDate;

	private Date endDate;

	private String recordState;

	private String dummy;

	/**
	 * 
	 */
	public DepartmentTemp()
	{
		super();
	}

	/**
	 * @return ssssssss
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * @return ssssssss
	 */
	public String getDeptId()
	{
		return deptId;
	}

	/**
	 * @param deptId
	 *            the deptId to set
	 */
	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	/**
	 * @return ssssssss
	 */
	public String getDeptName()
	{
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	/**
	 * @return ssssssss
	 */
	public Long getDeptOrder()
	{
		return deptOrder;
	}

	/**
	 * @param deptOrder
	 *            the deptOrder to set
	 */
	public void setDeptOrder(Long deptOrder)
	{
		this.deptOrder = deptOrder;
	}

	/**
	 * @return ssssssss
	 */
	public String getDeptCode()
	{
		return deptCode;
	}

	/**
	 * @param deptCode
	 *            the deptCode to set
	 */
	public void setDeptCode(String deptCode)
	{
		this.deptCode = deptCode;
	}

	/**
	 * @return ssssssss
	 */
	public String getDeptOrgCode()
	{
		return deptOrgCode;
	}

	/**
	 * @param deptOrgCode
	 *            the deptOrgCode to set
	 */
	public void setDeptOrgCode(String deptOrgCode)
	{
		this.deptOrgCode = deptOrgCode;
	}

	/**
	 * @return ssssssss
	 */
	public String getParentDeptCode()
	{
		return parentDeptCode;
	}

	/**
	 * @param parentDeptCode
	 *            the parentDeptCode to set
	 */
	public void setParentDeptCode(String parentDeptCode)
	{
		this.parentDeptCode = parentDeptCode;
	}

	/**
	 * @return ssssssss
	 */
	public String getIsvirtual()
	{
		return isvirtual;
	}

	/**
	 * @param isvirtual
	 *            the isvirtual to set
	 */
	public void setIsvirtual(String isvirtual)
	{
		this.isvirtual = isvirtual;
	}

	/**
	 * @return ssssssss
	 */
	public String getModifyType()
	{
		return modifyType;
	}

	/**
	 * @param modifyType
	 *            the modifyType to set
	 */
	public void setModifyType(String modifyType)
	{
		this.modifyType = modifyType;
	}

	/**
	 * @return ssssssss
	 */
	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return ssssssss
	 */
	public Date getBeginDate()
	{
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(Date beginDate)
	{
		this.beginDate = beginDate;
	}

	/**
	 * @return ssssssss
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * @return ssssssss
	 */
	public String getRecordState()
	{
		return recordState;
	}

	/**
	 * @param recordState
	 *            the recordState to set
	 */
	public void setRecordState(String recordState)
	{
		this.recordState = recordState;
	}

	/**
	 * @return ssssssss
	 */
	public String getDummy()
	{
		return dummy;
	}

	/**
	 * @param dummy
	 *            the dummy to set
	 */
	public void setDummy(String dummy)
	{
		this.dummy = dummy;
	}

}
