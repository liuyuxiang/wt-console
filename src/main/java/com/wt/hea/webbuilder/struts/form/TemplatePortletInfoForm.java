package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.TemplatePortletInfo;
/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class TemplatePortletInfoForm extends ActionForm {
	/**
	 * 
	 */
	private TemplatePortletInfo templatePortletInfo ;

	public TemplatePortletInfo getTemplatePortletInfo() {
		return templatePortletInfo;
	}

	public void setTemplatePortletInfo(TemplatePortletInfo templatePortletInfo) {
		this.templatePortletInfo = templatePortletInfo;
	}
}
