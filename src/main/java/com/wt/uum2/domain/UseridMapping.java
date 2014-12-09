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
public class UseridMapping implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -37468712330588355L;

	private String uuid;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	private Application application;
	private User user;
	private String appUserid;
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAppUserid() {
		return appUserid;
	}
	public void setAppUserid(String appUserid) {
		this.appUserid = appUserid;
	}
	
	
}
