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
public class PagesContainer {

	/**
	 * 是否允许拖拽
	 */
	private String isDrag;

	/**
	 * 是否允许编辑
	 */
	private String isEdit;

	/**
	 * 是否允许关闭
	 */
	private String isClose;

	/**
	 * 是否允许刷新
	 */
	private String isRefresh;

	/**
	 * 是否允许切换主题
	 */
	private String isSwitchTheme;

	/**
	 * 是否允许切换布局
	 */
	private String isSwitchLayout;

	/**
	 * 是否有标签
	 */
	private String isTabPanel;

	/**
	 * 是否有菜单
	 */
	private String isMenu;

	/**
	 * 
	 */
	private String isPersonal;

	/**
	 * 
	 */
	private List<Pages> pages;

	public List<Pages> getPages() {
		return pages;
	}

	public void setPages(List<Pages> pages) {
		this.pages = pages;
	}

	public String getIsDrag() {
		return isDrag;
	}

	public void setIsDrag(String isDrag) {
		this.isDrag = isDrag;
	}

	public String getIsEdit() {
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

	public String getIsRefresh() {
		return isRefresh;
	}

	public void setIsRefresh(String isRefresh) {
		this.isRefresh = isRefresh;
	}

	public String getIsSwitchTheme() {
		return isSwitchTheme;
	}

	public void setIsSwitchTheme(String isSwitchTheme) {
		this.isSwitchTheme = isSwitchTheme;
	}

	public String getIsSwitchLayout() {
		return isSwitchLayout;
	}

	public void setIsSwitchLayout(String isSwitchLayout) {
		this.isSwitchLayout = isSwitchLayout;
	}

	public String getIsTabPanel() {
		return isTabPanel;
	}

	public void setIsTabPanel(String isTabPanel) {
		this.isTabPanel = isTabPanel;
	}

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}

	public String getIsPersonal() {
		return isPersonal;
	}

	public void setIsPersonal(String isPersonal) {
		this.isPersonal = isPersonal;
	}

}
