package com.wt.uum2.web.wicket.panel;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;

import com.wt.uum.service.SyncOnOffService;
import com.wt.uum2.bean.Setting;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.ExcelExportService;
import com.wt.uum2.service.GroupService;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.UUMTempDataService;
import com.wt.uum2.service.UserListService;
import com.wt.uum2.web.wicket.BaseUUMApplication;
import com.wt.uum2.web.wicket.panel.user.UserAttributePanel;

/**
 * <pre>
 * 业务名:UUM公共的PANEL
 * 功能说明: 
 * 编写日期:	2011-9-27
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class BaseUUMPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4482521300888784060L;

	/**
	 * @param id id
	 */
	public BaseUUMPanel(String id) {
		super(id);
	}


	protected UUMService getUUMService() {
		return ((BaseUUMApplication) getApplication()).getUUMService();
	}
	
	protected DepartmentPathService getDepartmentPathService() {
		return ((BaseUUMApplication) getApplication()).getDepartmentPathService();
	}
	
	protected EventFactory getEventFactory()
	{
		return ((BaseUUMApplication) getApplication()).getEventFactory();
	}

	protected EventListenerHandler getEventListenerHandler()
	{
		return ((BaseUUMApplication) getApplication()).getEventListenerHandler();
	}
	
	protected UserListService getUserListService()
	{
		return ((BaseUUMApplication) getApplication()).getUserListService();
	}
	
	protected Setting getSetting(){
		return ((BaseUUMApplication)getApplication()).getSetting();
	}

	protected ExcelExportService getExcelExportService()
	{
		return ((BaseUUMApplication) getApplication()).getExcelExportService();
	}

	protected UUMTempDataService getUumTempDataService()
	{
		return ((BaseUUMApplication) getApplication()).getUumTempDataService();
	}

	protected SyncOnOffService getSyncOnOffService()
	{
		return ((BaseUUMApplication) getApplication()).getSyncOnOffService();
	}

	protected GroupService getGroupService()
	{
		return ((BaseUUMApplication) getApplication()).getGroupService();
	}

	/**
	 * 方法说明：是否创建用户
	 * 
	 * @param user
	 *            用户
	 * @return boolean
	 */
	protected boolean isNew(User user)
	{
		if (user == null) {
			return true;
		}
		return StringUtils.isBlank(user.getUuid());
	}

	/**
	 * 方法说明：isModifyGroup
	 * 
	 * @param user
	 *            用户
	 * @return boolean
	 */
	protected boolean isModifyGroup(User user) {
		boolean isModifyGroup = false;
		if (getUUMService().isUserinSuperGroup(user)) {
			return true;
		}
		if (InitParameters.isCqGroupAuthor()) {
				isModifyGroup = getUUMService().getUserGroups(user).contains(
						getUUMService().getGroupByCode(
								InitParameters.modifyUserGroupCode()));
		}
		return isModifyGroup;
	}

	/**
	 * 方法说明：是否是超级管理员或部门管理员
	 * 
	 * @param loginUser
	 *            登录用户
	 * @param user
	 *            操作用户
	 * @return boolean
	 */
	public boolean isAdmin(final User loginUser, final User user)
	{
		boolean isAdmin = false;
		isAdmin = isModifyGroup(loginUser);
		if (isAdmin) {
			return true;
		}
		List<Group> adminGroups = getUUMService().getUserManagedGroups(user);
		List<Department> userDepts = getUUMService().getUserDepartments(user);
		for (Department department : userDepts) {
			if (getUUMService().isDepartmentManager(loginUser, department)) {
				return true;
			}
		}

		return CollectionUtils.containsAny(adminGroups, getUUMService()
				.getUserGroups(loginUser));
	}

	/**
	 * 方法说明：权限
	 * 
	 * @param component
	 *            组件
	 * @param user
	 *            用户
	 */
	public void revisabilityUserPasswordHandle(Component component,User user){
			if(!isAdmin(loginUser,user) && component!=null){
				component.setEnabled(false);
			}
	}

	/**
	 * 方法说明：是否关闭当前窗口
	 * 
	 * @param msg
	 *            msg
	 * @param target
	 *            target
	 */
	public void confirmCloseHandle(String msg, AjaxRequestTarget target)
	{
		msg = StringEscapeUtils.escapeJavaScript(msg + "\n    是否关闭窗口？");
		if (target != null) {
			target.appendJavaScript(" if (confirm('" + msg + "')){window.close();}");
		}
	}

	/**
	 * 方法说明：保存资源的扩展属性
	 * 
	 * @param panels
	 *            扩展属性面板
	 * @param resource
	 *            resource
	 */
	protected final void saveAttributes(List<UserAttributePanel> panels, Resource resource)
	{
		for (UserAttributePanel p : panels) {
			Attribute attribute = p.getAttribute();
			if (!attribute.getType().getHidden() && p.isChanged()) {
				if (p.isNew()) {
					attribute.setOwnerResource(resource);
					getUUMService().saveAttribute(attribute);
				} else if (p.isDelete()) {
					getUUMService().deleteAttribute(attribute);
				} else {
					getUUMService().updateAttribute(attribute);
				}
			}
		}
	}

	/**
	 * 登录用户
	 */
	protected User loginUser = getUUMService().getLoginUser();
	

}
