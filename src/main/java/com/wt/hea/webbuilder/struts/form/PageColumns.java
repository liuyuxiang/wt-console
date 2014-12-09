package com.wt.hea.webbuilder.struts.form;

//
import java.util.List;

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
public class PageColumns {

	/**
	 * 
	 */
	private String width;

	/**
	 * 
	 */
	private List<PagePortlets> portlets;

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public List<PagePortlets> getPortlets() {
		return portlets;
	}

	public void setPortlets(List<PagePortlets> portlets) {
		this.portlets = portlets;
	}

}
