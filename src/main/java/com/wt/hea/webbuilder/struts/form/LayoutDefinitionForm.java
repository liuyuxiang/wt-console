package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.LayoutDefinition;

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
public class LayoutDefinitionForm extends ActionForm {
	
	/**
	 * 布局管理bean
	 */
	private LayoutDefinition layoutDefinition = new LayoutDefinition();

	public LayoutDefinition getLayoutDefinition() {
		return layoutDefinition;
	}

	public void setLayoutDefinition(LayoutDefinition layoutDefinition) {
		this.layoutDefinition = layoutDefinition;
	} 
	
	
}
