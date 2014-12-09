package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.UserPage;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 用户页面form
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
public class UserPageForm extends ActionForm {

	/**
	 * 用户页面bean
	 */
	private UserPage userpages = new UserPage();

	public UserPage getUserpages() {
		return userpages;
	}

	public void setUserpages(UserPage userpages) {
		this.userpages = userpages;
	}
	
}
