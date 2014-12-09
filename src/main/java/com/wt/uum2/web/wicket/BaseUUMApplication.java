/*******************************************************************************
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-9-9 
 * 
 *******************************************************************************/ 
package com.wt.uum2.web.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.time.Duration;

import com.hirisun.components.other.runtime.RuntimeResolver;
import com.wt.uum.service.SyncOnOffService;
import com.wt.uum2.bean.Setting;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.mail.SendMail;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.ExcelExportService;
import com.wt.uum2.service.GroupService;
import com.wt.uum2.service.TaskListService;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.UUMTempDataService;
import com.wt.uum2.service.UserListService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-9
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class BaseUUMApplication extends WebApplication
{
	/**
	 * UUMService
	 */
	private UUMService uumService;

	/**
	 * DepartmentPathService
	 */
	private DepartmentPathService departmentPathService;

	/**
	 * EventFactory
	 */
	private EventFactory eventFactory;

	/**
	 * EventListenerHandler
	 */
	private EventListenerHandler eventListenerHandler;

	/**
	 * UUMTempDataService
	 */
	private UUMTempDataService uumTempDataService;

	
	
	/**
	 * 
	 */
	private UserListService userListService;

	/**
	 * 
	 */
	private TaskListService taskListService;

	/**
	 * 
	 */
	private Setting setting;

	/**
	 * 
	 */
	private SendMail sendMail;
	
	/**
	 * 
	 */
	private SyncOnOffService syncOnOffService;
	/**
	 * 
	 */
	private ExcelExportService excelExportService;

	private GroupService groupService;

	public ExcelExportService getExcelExportService()
	{
		return excelExportService;
	}

	public void setExcelExportService(ExcelExportService excelExportService)
	{
		this.excelExportService = excelExportService;
	}

	public TaskListService getTaskListService() {
		return taskListService;
	}

	public void setTaskListService(TaskListService taskListService) {
		this.taskListService = taskListService;
	}

	public SendMail getSendMail()
	{
		return sendMail;
	}

	public void setSendMail(SendMail sendMail)
	{
		this.sendMail = sendMail;
	}

	public Setting getSetting()
	{
		return setting;
	}

	public void setSetting(Setting setting)
	{
		this.setting = setting;
	}

	public UserListService getUserListService()
	{
		return userListService;
	}

	public void setUserListService(UserListService userListService)
	{
		this.userListService = userListService;
	}

	public UUMTempDataService getUumTempDataService()
	{
		return uumTempDataService;
	}

	public void setUumTempDataService(UUMTempDataService uumTempDataService)
	{
		this.uumTempDataService = uumTempDataService;
	}

	public EventListenerHandler getEventListenerHandler()
	{
		return eventListenerHandler;
	}

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

	public EventFactory getEventFactory()
	{
		return eventFactory;
	}

	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	/**
	 * @return the uumService
	 */
	public UUMService getUUMService()
	{
		return uumService;
	}

	/**
	 * @return the departmentPathService
	 */
	public DepartmentPathService getDepartmentPathService()
	{
		return departmentPathService;
	}

	/**
	 * @param uumService
	 *            the uumService to set
	 */
	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/**
	 * @param departmentPathService
	 *            the departmentPathService to set
	 */
	public void setDepartmentPathService(DepartmentPathService departmentPathService)
	{
		this.departmentPathService = departmentPathService;
	}

	public SyncOnOffService getSyncOnOffService()
	{
		return syncOnOffService;
	}

	public void setSyncOnOffService(SyncOnOffService syncOnOffService)
	{
		this.syncOnOffService = syncOnOffService;
	}

	public GroupService getGroupService()
	{
		return this.groupService;
	}

	public void setGroupService(GroupService groupService)
	{
		this.groupService = groupService;
	}

	static {
		System.setProperty("java.awt.headless", "true");
	}

	@Override
	protected void init()
	{
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getMarkupSettings().setDefaultBeforeDisabledLink("");
		getMarkupSettings().setDefaultAfterDisabledLink("");
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));

		getDebugSettings().setDevelopmentUtilitiesEnabled(false);

		if (RuntimeResolver.isDevMode()) {
			getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
		}
		// getApplicationSettings().setPageExpiredErrorPage(UUMExpiredErrorPage.class);// 设置过期提示页面
		// getApplicationSettings().setAccessDeniedPage(UUMAccessDeniedPage.class);
		// getApplicationSettings().setInternalErrorPage(UUMInternalErrorPage.class);// 设置错误页面
		//getExceptionSettings().setUnexpectedExceptionDisplay(
		//		IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);

	}

	@Override
	public RuntimeConfigurationType getConfigurationType()
	{
		RuntimeConfigurationType type = RuntimeConfigurationType.DEVELOPMENT;
		if (RuntimeResolver.isDepMode()) {
			type = RuntimeConfigurationType.DEPLOYMENT;
		}
		return type;
	}

	@Override
	public abstract Class<? extends Page> getHomePage();
}
