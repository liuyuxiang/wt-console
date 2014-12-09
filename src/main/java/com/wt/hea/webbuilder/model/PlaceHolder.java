package com.wt.hea.webbuilder.model;

import java.io.Serializable;

/**
 * 占位符实体类
 * 模板中有几个占位符
 * @author xiaoqi
 *
 */
@SuppressWarnings("serial")
public class PlaceHolder implements Serializable{

	/**
	 * 流水id
	 */
	private String tmplAttrId ;
	
	/**
	 * 模板布局id
	 */
	private String tmplId ;
	
	/**
	 * 占位符序号
	 */
	private String placeHolderId ;
	
	/**
	 * 占位符的名字
	 */
	private String placeHolderName ;
	
	/**
	 * 占位符类型
	 */
	private String placeHolderType ;
	/**
	 * 类型
	 */
	private String contentCode ;
	/**
	 * portletid
	 */
	private String portletId ;
	/**
	 * portlet名字
	 */
	private String portletName ;
	/**
	 * 功能地址
	 */
	private String funcAction ;
	/**
	 * 编辑地址
	 */
	private String editAction ;
	/**
	 * 标题url
	 */
	private String titleUrl ;
	
	public String getTmplAttrId() {
		return tmplAttrId;
	}

	public void setTmplAttrId(String tmplAttrId) {
		this.tmplAttrId = tmplAttrId;
	}

	public String getTmplId() {
		return tmplId;
	}

	public void setTmplId(String tmplId) {
		this.tmplId = tmplId;
	}

	public String getPlaceHolderId() {
		return placeHolderId;
	}

	public void setPlaceHolderId(String placeHolderId) {
		this.placeHolderId = placeHolderId;
	}

	public String getPlaceHolderName() {
		return placeHolderName;
	}

	public void setPlaceHolderName(String placeHolderName) {
		this.placeHolderName = placeHolderName;
	}

	public String getPlaceHolderType() {
		return placeHolderType;
	}

	public void setPlaceHolderType(String placeHolderType) {
		this.placeHolderType = placeHolderType;
	}

	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public String getPortletName() {
		return portletName;
	}

	public void setPortletName(String portletName) {
		this.portletName = portletName;
	}

	public String getFuncAction() {
		return funcAction;
	}

	public void setFuncAction(String funcAction) {
		this.funcAction = funcAction;
	}

	public String getEditAction() {
		return editAction;
	}

	public void setEditAction(String editAction) {
		this.editAction = editAction;
	}

	public String getTitleUrl() {
		return titleUrl;
	}

	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
	}
	
}
