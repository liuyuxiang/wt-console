package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.UserPersonalInfo;
/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 用户个性化信息portlet form
 * 编写日期:	2011-3-29
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class UserPersonalInfoForm extends ActionForm {
	/**
	 * 用户个性化portlet信息bean
	 */
	private UserPersonalInfo userPersonalInfo = new UserPersonalInfo() ;

	public UserPersonalInfo getUserPersonalInfo() {
		return userPersonalInfo;
	}

	public void setUserPersonalInfo(UserPersonalInfo userPersonalInfo) {
		this.userPersonalInfo = userPersonalInfo;
	}
	
}
