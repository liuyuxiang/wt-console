package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alex
 * 
 *         部门临时日志表
 */
public class DepartmentTempLog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6724415281033446931L;

	private String uuid;

	private String deptId;

	private String deptName;

	private Long deptOrder;

	private String deptCode;

	private String deptOrgCode;

	private String parentDeptCode;

	private String isvirtual;

	private String modifyType;

	private Long modifyStatus;

	private Date lastUpdateTime;

	private Date beginDate;

	private Date endDate;

	private String recordState;

	private String dummy;

	private String remark;

	/**
	 * 
	 */
	public DepartmentTempLog()
	{
		super();
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public Long getModifyStatus()
	{
		return modifyStatus;
	}

	public void setModifyStatus(Long modifyStatus)
	{
		this.modifyStatus = modifyStatus;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public Long getDeptOrder()
	{
		return deptOrder;
	}

	public void setDeptOrder(Long deptOrder)
	{
		this.deptOrder = deptOrder;
	}

	public String getDeptCode()
	{
		return deptCode;
	}

	public void setDeptCode(String deptCode)
	{
		this.deptCode = deptCode;
	}

	public String getDeptOrgCode()
	{
		return deptOrgCode;
	}

	public void setDeptOrgCode(String deptOrgCode)
	{
		this.deptOrgCode = deptOrgCode;
	}

	public String getParentDeptCode()
	{
		return parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode)
	{
		this.parentDeptCode = parentDeptCode;
	}

	public String getIsvirtual()
	{
		return isvirtual;
	}

	public void setIsvirtual(String isvirtual)
	{
		this.isvirtual = isvirtual;
	}

	public String getModifyType()
	{
		return modifyType;
	}

	public void setModifyType(String modifyType)
	{
		this.modifyType = modifyType;
	}

	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getBeginDate()
	{
		return beginDate;
	}

	public void setBeginDate(Date beginDate)
	{
		this.beginDate = beginDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getRecordState()
	{
		return recordState;
	}

	public void setRecordState(String recordState)
	{
		this.recordState = recordState;
	}

	public String getDummy()
	{
		return dummy;
	}

	public void setDummy(String dummy)
	{
		this.dummy = dummy;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public boolean isDelete()
	{
		return "delete".equals(getModifyType()) || "D".equals(getModifyType());
	}

}
