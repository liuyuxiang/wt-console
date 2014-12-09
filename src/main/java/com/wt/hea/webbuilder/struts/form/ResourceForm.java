package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;


import com.wt.hea.webbuilder.model.ResourceSite;
/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 资源传输form
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
public class ResourceForm extends ActionForm {
	/**
	 * 资源实体bean
	 */
	private ResourceSite resource = new ResourceSite();

	public ResourceSite getResource() {
		return resource;
	}

	public void setResource(ResourceSite resource) {
		this.resource = resource;
	}
	
}
