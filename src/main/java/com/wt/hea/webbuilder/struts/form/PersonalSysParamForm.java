package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.PersonalSystemParameter;
/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 系统全局参数form
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
public class PersonalSysParamForm extends ActionForm {

	/**
	 * 系统全局参数bean
	 */
	private PersonalSystemParameter personalSystemParameter ;

	public PersonalSystemParameter getPersonalSystemParameter() {
		return personalSystemParameter;
	}

	public void setPersonalSystemParameter(
			PersonalSystemParameter personalSystemParameter) {
		this.personalSystemParameter = personalSystemParameter;
	}
}
