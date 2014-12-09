package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Alex
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserTempLog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6724415281033446931L;

	private String uuid;

	private String userId;
	private String userName;
	private String idCard;
	private Long userOrder;
	private String userCode;
	private String userNameUsed;
	private String gender;
	private Date birthDate;
	private String birthPlace;
	private String livePlace;
	private Date workDate;
	private String workPlace;
	private String jobStatus;
	private String jobPosition;
	private String techName;
	private String nationality;
	private String marriged;
	private String homePhone;
	private String mobiePhone;
	private String fax;
	private String email;
	private String officePhone;
	private String deptCode;
	private String deptName;
	private String userStatus;
	private String modifyType;
	private Date lastUpdateTime;
	private String dutyCode;
	private String dutyName;
	private String office;
	private Date joinElectricDate;
	private String supportName;
	private String deptOrgCode;
	private String password;
	private String recordState;
	private String reducingCode;
	private String retireDate;
	private Long modifyStatus;
	private String remark;

	/**
	 * 数据来源
	 */
	private String datafrom;

	public String getRecordState()
	{
		return recordState;
	}

	public void setRecordState(String recordState)
	{
		this.recordState = recordState;
	}

	public String getReducingCode()
	{
		return reducingCode;
	}

	public void setReducingCode(String reducingCode)
	{
		this.reducingCode = reducingCode;
	}

	public String getRetireDate()
	{
		return retireDate;
	}

	public void setRetireDate(String retireDate)
	{
		this.retireDate = retireDate;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Long getModifyStatus()
	{
		return modifyStatus;
	}

	public void setModifyStatus(Long modifyStatus)
	{
		this.modifyStatus = modifyStatus;
	}

	/**
	 * 
	 */
	public UserTempLog()
	{
		super();
	}

	public String getUuid()
	{
		return uuid;
	}

	protected void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getIdCard()
	{
		return idCard;
	}

	public void setIdCard(String idCard)
	{
		this.idCard = idCard;
	}

	public Long getUserOrder()
	{
		return userOrder;
	}

	public void setUserOrder(Long userOrder)
	{
		this.userOrder = userOrder;
	}

	public String getUserCode()
	{
		return userCode;
	}

	public void setUserCode(String userCode)
	{
		this.userCode = userCode;
	}

	public String getUserNameUsed()
	{
		return userNameUsed;
	}

	public void setUserNameUsed(String userNameUsed)
	{
		this.userNameUsed = userNameUsed;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public String getBirthPlace()
	{
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	public String getLivePlace()
	{
		return livePlace;
	}

	public void setLivePlace(String livePlace)
	{
		this.livePlace = livePlace;
	}

	public Date getWorkDate()
	{
		return workDate;
	}

	public void setWorkDate(Date workDate)
	{
		this.workDate = workDate;
	}

	public String getWorkPlace()
	{
		return workPlace;
	}

	public void setWorkPlace(String workPlace)
	{
		this.workPlace = workPlace;
	}

	public String getJobStatus()
	{
		return jobStatus;
	}

	public void setJobStatus(String jobStatus)
	{
		this.jobStatus = jobStatus;
	}

	public String getJobPosition()
	{
		return jobPosition;
	}

	public void setJobPosition(String jobPosition)
	{
		this.jobPosition = jobPosition;
	}

	public String getTechName()
	{
		return techName;
	}

	public void setTechName(String techName)
	{
		this.techName = techName;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	public String getMarriged()
	{
		return marriged;
	}

	public void setMarriged(String marriged)
	{
		this.marriged = marriged;
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getMobiePhone()
	{
		return mobiePhone;
	}

	public void setMobiePhone(String mobiePhone)
	{
		this.mobiePhone = mobiePhone;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getOfficePhone()
	{
		return officePhone;
	}

	public void setOfficePhone(String officePhone)
	{
		this.officePhone = officePhone;
	}

	public String getDeptCode()
	{
		return deptCode;
	}

	public void setDeptCode(String deptCode)
	{
		this.deptCode = deptCode;
	}

	public String getDeptName()
	{
		return deptName;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public String getUserStatus()
	{
		return userStatus;
	}

	public void setUserStatus(String userStatus)
	{
		this.userStatus = userStatus;
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

	public String getDutyCode()
	{
		return dutyCode;
	}

	public void setDutyCode(String dutyCode)
	{
		this.dutyCode = dutyCode;
	}

	public String getDutyName()
	{
		return dutyName;
	}

	public void setDutyName(String dutyName)
	{
		this.dutyName = dutyName;
	}

	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}

	public Date getJoinElectricDate()
	{
		return joinElectricDate;
	}

	public void setJoinElectricDate(Date joinElectricDate)
	{
		this.joinElectricDate = joinElectricDate;
	}

	public String getSupportName()
	{
		return supportName;
	}

	public void setSupportName(String supportName)
	{
		this.supportName = supportName;
	}

	public String getDeptOrgCode()
	{
		return deptOrgCode;
	}

	public void setDeptOrgCode(String deptOrgCode)
	{
		this.deptOrgCode = deptOrgCode;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setDatafrom(String datafrom)
	{
		this.datafrom = datafrom;
	}

	public String getDatafrom()
	{
		return datafrom;
	}

	public boolean isDelete()
	{
		return "delete".equals(getModifyType()) || "D".equals(getModifyType());
	}

}
