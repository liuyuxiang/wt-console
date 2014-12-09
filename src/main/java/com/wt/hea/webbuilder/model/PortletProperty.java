package com.wt.hea.webbuilder.model;

import java.io.Serializable;

/**
 * portlet属性信息bean
 * 
 * @author xiaoqi
 * 
 */
@SuppressWarnings("serial")
public class PortletProperty implements Serializable {

	/**
	 * portlet流水id
	 */
	private String portletId;

	/**
	 * portlet名字
	 */
	private String portletName;

	/**
	 * portlet类型
	 */
	private String portletType;

	/**
	 * 取数据的地址
	 */
	private String funcAction;
	
	/**
	 * 点标题时的URL
	 */
	private String titleUrl ;

	/**
	 * 可编辑时的编辑页面地址
	 */
	private String editAction;
	
	/**
	 * 是否可拖拽
	 *//*
	private String isDropDrag ;
	
	*//**
	 * 是否可编辑
	 *//*
	private String isEdit ;
	
	*//**
	 * 是否可关闭
	 *//*
	private String isClose ;*/
	

	/**
	 * 是否已添加
	 */
	private String isAdd ;
	
	/**
	 * portlet描述
	 */
	private String portletDesc;
	
	
	/**
	 * portlet宽
	 */
	private Integer width;
	/**
	 * portlet高
	 */
	private Integer height;
	
	/**
	 * portlet中的html片段
	 */
	private String htmlCode;
	
	
	
	public String getPortletDesc() {
		return portletDesc;
	}

	public void setPortletDesc(String portletDesc) {
		
		this.portletDesc = portletDesc;
	}

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}
	public String getPortletName() {
		return portletName;
	}

	public void setPortletName(String portletName) {
		this.portletName = portletName;
	}

	public String getPortletType() {
		return portletType;
	}

	public void setPortletType(String portletType) {
		this.portletType = portletType;
	}


	public String getFuncAction() {
		return funcAction;
	}

	public void setFuncAction(String funcAction) {
		this.funcAction = funcAction;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

	public String getPortletId() {
		return portletId;
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

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}



	
	/*	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsClose() {
		return isClose;
	}

	public void setIsClose(String isClose) {
		this.isClose = isClose;
	}

	public String getIsDropDrag() {
		return isDropDrag;
	}

	public void setIsDropDrag(String isDropDrag) {
		this.isDropDrag = isDropDrag;
	}
	 */
	
}
