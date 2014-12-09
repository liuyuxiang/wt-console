package com.wt.hea.webbuilder.model;

import java.io.Serializable;

/**
 * 模板portlet信息
 * @author xiaoqi
 *
 */
public class TemplatePortletInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String tpId;
	/**
	 * 
	 */
	private String pageId;
	/**
	 * 
	 */
	private String portletId;
	/**
	 * 
	 */
	private String placeHolderId ;
	/**
	 * 
	 */
	private String placeHolderName ;
	/**
	 * 
	 */
	private String placeHolderType ;
	/**
	 * 
	 */
	private String showName ;
	/**
	 * 
	 */
	private String funcId;
	/**
	 * 
	 */
	private String funcName;
	/**
	 * 
	 */
	private String funcAction;
	/**
	 * 
	 */
	private String editAction ;
	/**
	 * 
	 */
	private String titleUrl ;
	/**
	 * 
	 */
	private String portletStatus;
	/**
	 * 
	 */
	private Integer sortNo;
	/**
	 * 
	 */
	private Integer rowNo;
	/**
	 * 
	 */
	private Integer dispSn;
	/**
	 * 
	 */
	private String portletType ;
	/**
	 * 
	 */
	private String htmlCode ;
	/**
	 * 
	 */
	private TemplatePage templatePageInfo ;
	/**
	 * 
	 */
	private PortletProperty portletProperty ;

	public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncAction() {
		return funcAction;
	}

	public void setFuncAction(String funcAction) {
		this.funcAction = funcAction;
	}

	public String getPortletStatus() {
		return portletStatus;
	}

	public void setPortletStatus(String portletStatus) {
		this.portletStatus = portletStatus;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Integer getRowNo() {
		return rowNo;
	}

	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

	public Integer getDispSn() {
		return dispSn;
	}

	public void setDispSn(Integer dispSn) {
		this.dispSn = dispSn;
	}

	public PortletProperty getPortletProperty() {
		return portletProperty;
	}

	public void setPortletProperty(PortletProperty portletProperty) {
		this.portletProperty = portletProperty;
	}

	public TemplatePage getTemplatePageInfo() {
		return templatePageInfo;
	}

	public void setTemplatePageInfo(TemplatePage templatePageInfo) {
		this.templatePageInfo = templatePageInfo;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getTitleUrl() {
		return titleUrl;
	}

	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
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

	public String getEditAction() {
		return editAction;
	}

	public void setEditAction(String editAction) {
		this.editAction = editAction;
	}

	public String getPortletType() {
		return portletType;
	}

	public void setPortletType(String portletType) {
		this.portletType = portletType;
	}

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}
}
