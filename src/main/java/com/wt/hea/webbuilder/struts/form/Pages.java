package com.wt.hea.webbuilder.struts.form;

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
public class Pages {

	/**
	 * 
	 */
	private String id;

	/**
	 * 
	 */
	private String text;

	/**
	 * 
	 */
	private String themeCode;

	/**
	 * 
	 */
	private String themeContent;

	/**
	 * 
	 */
	private String layoutCode;

	/**
	 * 
	 */
	private List<PageColumns> columns;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<PageColumns> getColumns() {
		return columns;
	}

	public void setColumns(List<PageColumns> columns) {
		this.columns = columns;
	}

	public String getThemeCode() {
		return themeCode;
	}

	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}

	public String getLayoutCode() {
		return layoutCode;
	}

	public void setLayoutCode(String layoutCode) {
		this.layoutCode = layoutCode;
	}

	public String getThemeContent() {
		return themeContent;
	}

	public void setThemeContent(String themeContent) {
		this.themeContent = themeContent;
	}

}
