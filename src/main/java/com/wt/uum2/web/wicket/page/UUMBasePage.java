package com.wt.uum2.web.wicket.page;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum.web.version.UumWebVersionUtils;
import com.wt.uum2.bean.Setting;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.mail.SendMail;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.TaskListService;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.UUMTempDataService;
import com.wt.uum2.service.UserListService;
import com.wt.uum2.web.wicket.BaseUUMApplication;

/**
 * <pre>
 * 业务名:UUMBasePage
 * 功能说明: UUMBasePage
 * 编写日期:	2011-10-13
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class UUMBasePage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5271648007218401111L;

	/**
	 * 登录用户
	 */
	protected User loginUser = getUUMService().getLoginUser();

	protected UUMService getUUMService()
	{
		return ((BaseUUMApplication) getApplication()).getUUMService();
	}

	protected DepartmentPathService getDepartmentPathService()
	{
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

	protected UUMTempDataService getUumTempDataService()
	{
		return ((BaseUUMApplication) getApplication()).getUumTempDataService();
	}

	protected Setting getSetting()
	{
		return ((BaseUUMApplication) getApplication()).getSetting();
	}

	protected SendMail getSendMail()
	{
		return ((BaseUUMApplication) getApplication()).getSendMail();
	}

	protected UserListService getUserListService()
	{
		return ((BaseUUMApplication) getApplication()).getUserListService();
	}

	protected TaskListService getTaskListService()
	{
		return ((BaseUUMApplication) getApplication()).getTaskListService();
	}

	/**
	 * 
	 */
	public UUMBasePage()
	{
		super();
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		add(new Label("pageTitle", "统一用户管理 (" + UumWebVersionUtils.getVersion().getWholeVersion()
				+ ")"));
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderJavaScriptReference("js/jquery/jquery-1.6.2.min.js");
		response.renderJavaScriptReference("js/fitiframe.js");
		response.renderCSSReference("style/hirisun/style.css");
		response.renderCSSReference("style/hirisun/wstyle.css");
		response.renderCSSReference("style/" + ProjectResolver.getId() + "/wstyle.css");
		response.renderCSSReference("style/hirisun/css.css");
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
	 * 方法说明：是否是超级管理员或是否是重庆组授权模式
	 * 
	 * @param user
	 *            用户
	 * @return boolean
	 */
	protected boolean isModifyGroup(User user)
	{
		boolean isModifyGroup = false;
		if (getUUMService().isUserinSuperGroup(user)) {
			return true;
		}
		if (InitParameters.isCqGroupAuthor()) {
			isModifyGroup = getUUMService().getUserGroups(user).contains(
					getUUMService().getGroupByCode(InitParameters.modifyUserGroupCode()));
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
	protected boolean isAdmin(final User loginUser, final User user)
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

		return CollectionUtils.containsAny(adminGroups, getUUMService().getUserGroups(loginUser));
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
	 * 方法说明：权限
	 * 
	 * @param component
	 *            组件
	 * @param loginUser
	 *            登录用户
	 * @param user
	 *            用户
	 */
	public void revisabilityUserType(Component component, User loginUser, User user)
	{

		if (!isNew(user)) {
			if ("isModifyGroup".equalsIgnoreCase(getSetting().getRevisabilityUserType())) {
				if (!isModifyGroup(loginUser) && component != null) {
					component.setEnabled(false);
				}
			} else {
				if (!isAdmin(loginUser, user) && component != null) {
					component.setEnabled(false);
				}
			}
		}
	}

}
