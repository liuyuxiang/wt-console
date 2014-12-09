package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 业务名:UserApplication实体类
 * 功能说明: 
 * 编写日期:	2011-3-23
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "userapplication")
public class UserApplication implements Serializable
{

	/**
	 * 
	 */
	public UserApplication()
	{
		super();
		setType("0");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7347310459920154407L;
	/**
	 * 
	 */
	@XmlElement(name = "uuid")
	private String uuid;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	@XmlElement(name = "id")
	private String mappendUserid;
	/**
	 * 
	 */
	@XmlElement(name = "password")
	private String mappendPassword;
	/**
	 * 
	 */
	@XmlElement(name = "useruuid")
	private String useruuid;
	/**
	 * 
	 */
	private User user;
	/**
	 * 
	 */
	private boolean loginable;
	/**
	 * 
	 */
	@XmlElement(name = "appuuid")
	private String applicationuuid;
	/**
	 * 
	 */
	private Application application;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Date lastmodifytime;

	public String getUuid()
	{
		if (StringUtils.isBlank(uuid)) {
			uuid = StringUtils.remove(UUID.randomUUID().toString(), "-");
		}
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getMappendUserid()
	{
		return mappendUserid;
	}

	public void setMappendUserid(String mappendUserid)
	{
		this.mappendUserid = mappendUserid;
	}

	public String getMappendPassword()
	{
		return mappendPassword;
	}

	public void setMappendPassword(String mappendPassword)
	{
		this.mappendPassword = mappendPassword;
	}

	public String getUseruuid()
	{
		return useruuid;
	}

	public void setUseruuid(String useruuid)
	{
		this.useruuid = useruuid;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
		if (user != null && StringUtils.isNotBlank(user.getUuid())) {
			setUseruuid(user.getUuid());
		}
	}

	public boolean isLoginable()
	{
		return loginable;
	}

	public void setLoginable(boolean loginable)
	{
		this.loginable = loginable;
	}

	public String getApplicationuuid()
	{
		return applicationuuid;
	}

	public void setApplicationuuid(String applicationuuid)
	{
		this.applicationuuid = applicationuuid;
	}

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
		if (application != null && StringUtils.isNotBlank(application.getUuid())) {
			setApplicationuuid(application.getUuid());
		}
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Date getLastmodifytime()
	{
		return lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime)
	{
		this.lastmodifytime = lastmodifytime;
	}

}
