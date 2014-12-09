package com.wt.hea.webbuilder.struts.form;

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
public class Prefix {

	/**
	 * 
	 */
	private String prefix = null;
	/**
	 * 
	 */
	private String dataurl = null;
	/**
	 * 标识展位符类型内容题还是横幅、导航、版权
	 */
	private String type = null;
	/**
	 * 
	 */
	private String portletId = null;
	/**
	 * 
	 */
	private String tpId = null;
	/**
	 * 
	 */
	private String htmlCode = null;
	/**
	 * 标识portlet类型cms、webportlet
	 */
	private String portletType = null;

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDataurl() {
		return dataurl;
	}

	public void setDataurl(String dataurl) {
		this.dataurl = dataurl;
	}

	public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	public String getPortletType() {
		return portletType;
	}

	public void setPortletType(String portletType) {
		this.portletType = portletType;
	}

}
