package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.TemplatePage;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 模板页面form
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
public class TemplatePageForm extends ActionForm {
	/**
	 * 模板页面bean
	 */
	private TemplatePage templatePage ;

	public TemplatePage getTemplatePage() {
		return templatePage;
	}

	public void setTemplatePage(TemplatePage templatePage) {
		this.templatePage = templatePage;
	}
}
