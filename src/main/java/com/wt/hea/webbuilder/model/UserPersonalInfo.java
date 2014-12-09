package com.wt.hea.webbuilder.model;

import java.io.Serializable;

/**
 * 用户个性化信息bean
 * 
 * @author xiaoqi
 */
@SuppressWarnings("serial")
public class UserPersonalInfo implements Serializable {

	/**
	 * 流水id主键
	 */
	private String id;

	/**
	 * 页面流水id
	 */
	private String pageId;

	/**
	 * portlet流水id
	 */
	private String portletId;

	/**
	 * portlet类型
	 */
	private String type;

	/**
	 * 显示名称
	 */
	private String showName;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户流水id
	 */
	private String userNo;

	/**
	 * 功能id
	 */
	private String funcId;
	
	/**
	 * 功能名称
	 */
	private String funcName;

	/**
	 * 数据请求地址
	 */
	private String funcAction;
	
	/**
	 * 点标题时的url
	 */
	private String titleUrl ;

	/**
	 * portlet状态
	 */
	private String portletSatus;

	/**
	 * 列序号标识所在列
	 */
	private Integer sortNo;

	/**
	 * 行序号
	 */
	private Integer rowNo;

	/**
	 * portlet所在列中显示的顺序
	 */
	private Integer dispSn;
	
	/**
	 * 可编辑时的编辑页面地址
	 */
	private String editAction;

	/**
	 * 所属页面
	 */
	private UserPage userPage ;
	/**
	 * 页面portlet
	 */
	private PortletProperty portletProperty ;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
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

	public String getPortletSatus() {
		return portletSatus;
	}

	public void setPortletSatus(String portletSatus) {
		this.portletSatus = portletSatus;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public UserPage getUserPage() {
		return userPage;
	}

	public void setUserPage(UserPage userPage) {
		this.userPage = userPage;
	}

	public PortletProperty getPortletProperty() {
		return portletProperty;
	}

	public void setPortletProperty(PortletProperty portletProperty) {
		this.portletProperty = portletProperty;
	}

	public Integer getDispSn() {
		return dispSn;
	}

	public void setDispSn(Integer dispSn) {
		this.dispSn = dispSn;
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
