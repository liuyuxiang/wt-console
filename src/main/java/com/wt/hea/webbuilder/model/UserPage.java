package com.wt.hea.webbuilder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 个性化页面bean
 * @author xiaoqi
 *
 */
@SuppressWarnings("serial")
public class UserPage implements Serializable {

	/**
	 * 页面id
	 */
	private String pageId;
	/**
	 * 页面编号
	 */
	private String pageNo ;
	/**
	 * 页面标题
	 */
	private String pageTitle;
	/**
	 * 页面类型
	 */
	private String pageType;
	/**
	 * 显示顺序
	 */
	private Integer dispSn;
	/**
	 * 布局代码
	 */
	private String layoutCode;
	/**
	 * 主题编号
	 */
	private String themeCode;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * userno
	 */
	private String userNo;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 是否默认
	 */
	private Integer ifDefault;
	/**
	 * 用户页面信息列表
	 */	
	private List<UserPersonalInfo> userPersonalInfo = new ArrayList<UserPersonalInfo>(0);
	
	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getLayoutCode() {
		return layoutCode;
	}

	public void setLayoutCode(String layoutCode) {
		this.layoutCode = layoutCode;
	}

	public String getThemeCode() {
		return themeCode;
	}

	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public List<UserPersonalInfo> getUserPersonalInfo() {
		return userPersonalInfo;
	}

	public void setUserPersonalInfo(List<UserPersonalInfo> userPersonalInfo) {
		this.userPersonalInfo = userPersonalInfo;
	}

	public Integer getDispSn() {
		return dispSn;
	}

	public void setDispSn(Integer dispSn) {
		this.dispSn = dispSn;
	}

	public Integer getIfDefault() {
		return ifDefault;
	}

	public void setIfDefault(Integer ifDefault) {
		this.ifDefault = ifDefault;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}



}
