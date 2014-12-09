package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.PortletProperty;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-20
 * 作者:	Mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class PortletPropertyForm extends ActionForm {

	/**
	 * 
	 */
	private PortletProperty portletProperty = new PortletProperty();

	public PortletProperty getPortletProperty() {
		return portletProperty;
	}

	public void setPortletProperty(PortletProperty portletProperty) {
		this.portletProperty = portletProperty;
	}

}
