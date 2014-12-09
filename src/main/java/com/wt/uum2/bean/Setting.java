package com.wt.uum2.bean;

import java.util.List;

import com.wt.uum.service.SyncOnOffService;
import com.wt.uum2.constants.MenuItemType;

/**
 * <pre>
 * 业务名:属性设置
 * 功能说明: 
 * 编写日期:	2011-8-11
 * 作者:	李趁月
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Setting
{


	/**
	 * 
	 */
	private SyncOnOffService syncOnOffService;

	/**
	 * 每页显示最多条数
	 */
	private int maxRecordPerPage;

	/**
	 * 修改用户权限类型
	 */
	private String revisabilityUserType;

	/**
	 * 用户密码最大长度
	 */
	private int userPasswordMaxLength;
	/**
	 * 用户密码最小长度
	 */
	private int userPasswordMinLength;

	/**
	 * 用户密码是否以字母为开头
	 */
	private boolean userPasswordIsBeginEN;

	/**
	 * 用户密码是否允许有字母
	 */
	private boolean userPasswordIsMustHaveEN;

	/**
	 * 用户密码是否允许有数字
	 */
	private boolean userPasswordIsMustHaveNUM;
	
	/**
	 * 用户密码是否允许特殊字符
	 */
	private boolean userPasswordIsMustHaveSpecialCharacters;

	/**
	 * 版权所有
	 */
	private String copyRight;

	/**
	 * 生产模式的同步开关
	 */
	private String syncOnOff;
	/**
	 * 自动补全邮箱后缀
	 */
	private String autoCompleMailSuffix;

	/**
	 * 显示不显示离职按钮
	 */
	private String visibleLeaveofficeButton;

	/**
	 * 普通菜单列表
	 */
	private List<MenuItemType> normalMenuList;
	/**
	 * 超级管理员菜单列表
	 */
	private List<MenuItemType> adminMenuList;

	public int getUserPasswordMaxLength()
	{
		return userPasswordMaxLength;
	}

	public void setUserPasswordMaxLength(int userPasswordMaxLength)
	{
		this.userPasswordMaxLength = userPasswordMaxLength;
	}

	public int getUserPasswordMinLength()
	{
		return userPasswordMinLength;
	}

	public void setUserPasswordMinLength(int userPasswordMinLength)
	{
		this.userPasswordMinLength = userPasswordMinLength;
	}

	public String getRevisabilityUserType()
	{
		return revisabilityUserType;
	}

	public void setRevisabilityUserType(String revisabilityUserType)
	{
		this.revisabilityUserType = revisabilityUserType;
	}

	public int getMaxRecordPerPage()
	{
		return maxRecordPerPage;
	}

	public void setMaxRecordPerPage(int maxRecordPerPage)
	{
		this.maxRecordPerPage = maxRecordPerPage;
	}

	public boolean getUserPasswordIsBeginEN()
	{
		return userPasswordIsBeginEN;
	}

	public void setUserPasswordIsBeginEN(boolean userPasswordIsBeginEN)
	{
		this.userPasswordIsBeginEN = userPasswordIsBeginEN;
	}

	public boolean getUserPasswordIsMustHaveEN()
	{
		return userPasswordIsMustHaveEN;
	}

	public void setUserPasswordIsMustHaveEN(boolean userPasswordIsMustHaveEN)
	{
		this.userPasswordIsMustHaveEN = userPasswordIsMustHaveEN;
	}

	public boolean getUserPasswordIsMustHaveNUM()
	{
		return userPasswordIsMustHaveNUM;
	}

	public void setUserPasswordIsMustHaveNUM(boolean userPasswordIsMustHaveNUM)
	{
		this.userPasswordIsMustHaveNUM = userPasswordIsMustHaveNUM;
	}

	public String getAutoCompleMailSuffix()
	{
		return autoCompleMailSuffix;
	}

	public void setAutoCompleMailSuffix(String autoCompleMailSuffix)
	{
		this.autoCompleMailSuffix = autoCompleMailSuffix;
	}

	public String getVisibleLeaveofficeButton() {
		return visibleLeaveofficeButton;
	}

	public void setVisibleLeaveofficeButton(String visibleLeaveofficeButton) {
		this.visibleLeaveofficeButton = visibleLeaveofficeButton;
	}

	public String getCopyRight()
	{
		return copyRight;
	}

	public void setCopyRight(String copyRight)
	{
		this.copyRight = copyRight;
	}


	public List<MenuItemType> getNormalMenuList()
	{
		return normalMenuList;
	}

	public void setNormalMenuList(List<MenuItemType> normalMenuList)
	{
		this.normalMenuList = normalMenuList;
	}

	public List<MenuItemType> getAdminMenuList()
	{
		return adminMenuList;
	}

	public void setAdminMenuList(List<MenuItemType> adminMenuList)
	{
		this.adminMenuList = adminMenuList;
	}
	
	public String getSyncOnOff()
	{
		return syncOnOff;
	}

	public void setSyncOnOff(String syncOnOff)
	{
		this.syncOnOff = syncOnOff;
	}

	public SyncOnOffService getSyncOnOffService()
	{
		return syncOnOffService;
	}

	public void setSyncOnOffService(SyncOnOffService syncOnOffService)
	{
		this.syncOnOffService = syncOnOffService;
	}

	/**
	 * 方法说明：同步器开关初始化
	 * 
	 */
	public void initSyncOnOff()
	{
		syncOnOffService.init(this);


	}

	public boolean isUserPasswordIsMustHaveSpecialCharacters()
	{
		return userPasswordIsMustHaveSpecialCharacters;
	}

	public void setUserPasswordIsMustHaveSpecialCharacters(
			boolean userPasswordIsMustHaveSpecialCharacters)
	{
		this.userPasswordIsMustHaveSpecialCharacters = userPasswordIsMustHaveSpecialCharacters;
	}

}
