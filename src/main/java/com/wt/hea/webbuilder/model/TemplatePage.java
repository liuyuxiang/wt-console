package com.wt.hea.webbuilder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 模板页面
 * @author xiaoqi
 *
 */
public class TemplatePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 页面标识
	 */
	private String pageId ;
	
	/**
	 * 内容类型，1、portlet；2、url
	 */
	private String contentType;
	/**
	 * 
	 */
	private String siteId ;
	/**
	 * 
	 */
	private String siteNo ;
	/**
	 * 
	 */
	private String siteName ;
	
	/**
	 * 页面编号
	 */
	private String pageNo ;
	/**
	 * 
	 */
	private String pageName ;
	/**
	 * 
	 */
	private String tmplId ;
	
	/**
	 * 页面类型
	 */
	private String type ;
	
	/**
	 * 页面标题
	 */
	private String pageTitle ;
	
	/**
	 * 布局编号
	 */
	private String layoutCode ;
	
	/**
	 * 主题编号
	 */
	private String themeCode ;
	/**
	 * 
	 */
	private String pageStatus ;
	
	/**
	 * 是否默认
	 */
	private Integer ifDefault ;
	
	/**
	 * 显示顺序
	 */
	private Integer dispSn ;
	/**
	 * 
	 */
	private String pageAddr ;
	/**
	 * 
	 */
	private Date pubTime ;
	/**
	 * 
	 */
	private String userNo ;
	/**
	 * 
	 */
	private String pubAddr ;
	/**
	 * 判断用户是否添加，该属性为辅助性不持久化
	 * @return
	 */
	private String isAdd ;
	/**
	 * 
	 */
	private List<TemplatePortletInfo> templatePortletInfoList;
	/**
	 * 
	 */
	private SiteManage siteManage ;
	/**
	 * 
	 */
	private TemplateLayout templateLayout ;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getIfDefault() {
		return ifDefault;
	}

	public void setIfDefault(Integer ifDefault) {
		this.ifDefault = ifDefault;
	}

	public void setDispSn(Integer dispSn) {
		this.dispSn = dispSn;
	}

	public Integer getDispSn() {
		return dispSn;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

	public List<TemplatePortletInfo> getTemplatePortletInfoList() {
		return templatePortletInfoList;
	}

	public void setTemplatePortletInfoList(
			List<TemplatePortletInfo> templatePortletInfoList) {
		this.templatePortletInfoList = templatePortletInfoList;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSiteNo() {
		return siteNo;
	}

	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getTmplId() {
		return tmplId;
	}

	public void setTmplId(String tmplId) {
		this.tmplId = tmplId;
	}

	public String getPageAddr() {
		return pageAddr;
	}

	public void setPageAddr(String pageAddr) {
		this.pageAddr = pageAddr;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPubAddr() {
		return pubAddr;
	}

	public void setPubAddr(String pubAddr) {
		this.pubAddr = pubAddr;
	}

	public String getPageStatus() {
		return pageStatus;
	}

	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}

	public SiteManage getSiteManage() {
		return siteManage;
	}

	public void setSiteManage(SiteManage siteManage) {
		this.siteManage = siteManage;
	}

	public TemplateLayout getTemplateLayout() {
		return templateLayout;
	}

	public void setTemplateLayout(TemplateLayout templateLayout) {
		this.templateLayout = templateLayout;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
