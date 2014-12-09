package com.wt.hea.rbac.struts.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hirisun.components.data.DateUtils;
import com.hirisun.components.data.validate.Validator;
import com.hirisun.components.security.lcta.LctaContextHolder;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.model.SystemCode;
import com.wt.hea.common.service.SystemCodeService;
import com.wt.hea.common.util.I18NResourceUtil;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.rbac.model.Index;

/**
 * WEB层，用户对象控制器
 * 
 * @author 袁明敏
 * @see com.wt.hea.common.action.DispatchAction
 * @see com.wt.hea.common.action.BaseDispatchAction
 * @see com.wt.hea.rbac.service.UserService
 * 
 */
public class UserAction extends BaseAction
{
	/**
	 * 获取日志实例
	 */
	private final Logger logger = LoggerService.getInstance(UserAction.class);

	/**
	 * 系统code
	 */
	private SystemCodeService systemCodeService;

	/**
	 * @param systemCodeService
	 *            系统code
	 */
	public void setSystemCodeService(SystemCodeService systemCodeService)
	{
		this.systemCodeService = systemCodeService;
	}

	/**
	 * 后台管理登陆
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward adminValidateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		User loginUser = rbacService.getUserByUserId(id);
		if (loginUser != null && loginUser.getUuid() != null
				&& rbacService.userValidate(id, password)) {
			boolean isAdmin = rbacService.isAdmin(loginUser);
			if (isAdmin) {
				log.info("super user isValidated");
				request.getSession().setAttribute("loginGroup", null);// 用户选择使用的登录组
			} else {
				List<Group> groupList = rbacService.getUserGroups(loginUser);
				if (CollectionUtils.isEmpty(groupList)) {
					request.setAttribute("message", "用户没有分配权限组!");
					return mapping.findForward("logoutAdmin");
				}
				String[] groupUuids = new String[groupList.size()];
				for (int i = 0; i < groupList.size(); i++) {
					groupUuids[i] = groupList.get(i).getUuid();
				}
				request.getSession().setAttribute("loginGroup", groupUuids);
				log.info("user isValidated");
			}
			request.getSession().setAttribute("user", loginUser); // 保存用户
			request.getSession().setAttribute("locale", request.getLocale()); // 保存用户本地化信息
			request.getSession().setAttribute("theme", "blue"); // 主题
			request.getSession().setAttribute("consoleIsAdmin", isAdmin);
			request.setAttribute("copyrightTime", DateUtils.getCurrDate("yyyy"));
			super.saveUserAccessLog(request, logger, "后台登录");
			return mapping.findForward("adminIndex");
		}
		request.setAttribute("message", "用户名或密码错误!");
		return mapping.findForward("logoutAdmin");
	}

	/***
	 * 前台展现登陆
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	@Deprecated
	public ActionForward validateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		// String password = request.getParameter("password");

		// boolean isValidated = rbacService.userValidate(id, password);
		boolean isValidated = true;
		if (isValidated) {
			User user = rbacService.getUserByUserId(id);
			request.getSession().setAttribute("user", user);

			request.getSession().setAttribute("locale", request.getLocale());
			request.getSession().getServletContext()
					.setAttribute("I18NResource", I18NResourceUtil.getInstance());

			// 查找关联下级指标
			List<Group> groupList = rbacService.getUserGroups(user);
			if (groupList == null || groupList.size() == 0) {
				request.setAttribute("message", "用户没有分配权限组!");
				return mapping.findForward("message");
			}
			List<String> groupIds = new ArrayList<String>();
			for (int i = 0; i < groupList.size(); i++) {
				groupIds.add(groupList.get(i).getUuid());
			}
			String[] groupIdsArray = new String[groupIds.size()];
			request.getSession().setAttribute("louverGroup", groupIds.toArray(groupIdsArray));// 用户选择使用的登录组
			// 根据ID
			String sysRootIndexUuid = getLouverRootIndexId();
			List<Index> indexList = indexService.findChildIndexByUser(user, 0, sysRootIndexUuid);
			request.setAttribute("indexList", indexList);
			super.saveUserAccessLog(request, logger, "前端登录");

			// if (1 == 1) { // 测试
			createMenus(request);
			SystemCode systemCode = new SystemCode();
			systemCode.setRegCode("hn4_sys_login_after");
			List<SystemCode> list = systemCodeService.findByExample(systemCode);
			if (list != null && list.size() == 1) {
				systemCode = list.get(0);
				request.setAttribute("systemCode", systemCode);
			}

			// String appId=request.getParameter("appId");
			// String theme=request.getParameter("theme");

			List<Group> groupList1 = rbacService.getUserGroups(user);
			if (user != null) {
				if (groupList == null || groupList.size() == 0) {
					request.setAttribute("message", "用户没有分配权限组!");
					return mapping.findForward("message");
				} else {
					String[] groupUuids = new String[groupList1.size()];
					for (int i = 0; i < groupList1.size(); i++) {
						groupUuids[i] = groupList1.get(i).getUuid();
					}
					request.getSession().setAttribute("loginGroup", groupUuids);
				}
			}

			// 设置主题
			String theme = request.getParameter("theme");
			request.getSession().setAttribute("theme", theme);

			String page = request.getParameter("forward");
			request.getRequestDispatcher(page).forward(request, response);

			// }

			return null;
		} else {
			request.setAttribute("message", "用户名或密码错误,请重新登陆！！");
			return mapping.findForward("message");
		}
	}

	/**
	 * 
	 * 方法说明：创建左侧滑动菜单
	 * 
	 * @param request
	 *            请求对象
	 */
	private void createMenus(HttpServletRequest request)
	{
		List<Group> list = this.rbacService.getUserGroups(WebUtil.getSessionUser(request));
		List<String> groupIds = new ArrayList<String>();
		for (Group i : list) {
			groupIds.add(i.getUuid());
		}

		List<Index> userResources = this.indexService.findIndexByGroupID(groupIds, 0); // 登陆用户所带的子标权限

		request.setAttribute("userResources", userResources); // 用户所有带权限的子标

		// 获取横向导航结点(第一层)
		Set<Integer> levels = new HashSet<Integer>();
		for (Index i : userResources) {
			levels.add(i.getIndexlevel());
		}
		List<Integer> level = new ArrayList<Integer>(levels);
		Collections.sort(level);
		Integer rootLevel = -1;
		if (level.isEmpty() == false) {
			rootLevel = level.iterator().next();
		}

		// 获取横向菜单
		List<Index> menu = new ArrayList<Index>();
		for (Index i : userResources) {
			if (i.getIndexlevel().compareTo(rootLevel) == 0) {

				menu.add(i);
			}
		}
		Collections.sort(menu);
		request.setAttribute("topMenus", menu);
	}

	/**
	 * 系统用户登出,会话消毁 可以传入target参数控制注销后跳转到指定页面（注意是转发）
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String targetUrl = request.getParameter("target");
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.invalidate();
		LctaContextHolder.flush(request, response);
		if (Validator.isNotEmpty(targetUrl)) {
			response.sendRedirect(targetUrl);
			return null;
		}
		return mapping.findForward("logoutAdmin");
	}

	/**
	 * 系统用户登出,前端会话消毁。
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward logoutLouver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.getSession().removeAttribute("louverGroup");
		return mapping.findForward("logoutLouver");
	}

	/**
	 * 获取系统设置前端根级指标ID
	 * 
	 * @return 根ID
	 */
	private String getLouverRootIndexId()
	{
		SystemCode obj = new SystemCode();
		obj.setRegCode("louver_root_index");
		obj.setRegName("louver_root_index");
		List<SystemCode> list = this.systemCodeService.findByExample(obj);
		if (list != null && list.size() == 1) {
			return list.get(0).getRegValue();
		}
		return null;
	}

	/**
	 * 获取用户所在组列表
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 *             Exception
	 */
	public ActionForward userGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		if (user != null) {
			List<Group> groupList = rbacService.getUserGroups(user);
			request.setAttribute("groupList", groupList);
		}
		return null;
	}

	/**
	 * 方法说明： 用于不同应用时是否还有session
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 *             Exception
	 */
	public ActionForward isSessionValidate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	public ActionForward continueSessionReq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String req = "true";
		User user = WebUtil.getSessionUser(request);
		if (null == user) {
			req = "false";
		}
		response.getWriter().write(req);
		return null;
	}

	/**
	 * url传参登陆
	 * 
	 */
	private final static String SSOID = "userid";
	/**
	 * 
	 */
	private final static String SSOPASSWORD = "userpwd";
	/**
	 * 
	 */
	private final static String PAINDEX = "paindex";

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 *             Exception
	 */
	public ActionForward ssoValidateUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.getSession().setAttribute(PAINDEX, request.getParameter(PAINDEX));

		String id = request.getParameter(SSOID);
		String password = request.getParameter(SSOPASSWORD); // 获取河南系统md5加密后的串
		boolean isValidated = false;

		User ssoUser = rbacService.getUserByUserId(id); // hea返回来的密码，已经是md5加密的
		String ssoPassword = "";
		if (ssoUser != null) {
			ssoPassword = ssoUser.getPassword();
		}

		if (StringUtils.isNotEmpty(ssoPassword) && StringUtils.isNotEmpty(password)) {
			isValidated = ssoPassword.trim().equals(password.trim());
		} else {
			isValidated = false;
		}
		if (isValidated) {
			// User user = rbacService.getUserByUserId(id);
			request.getSession().setAttribute("user", ssoUser);

			String orgCode = this.rbacService.getUserOrgCode(ssoUser);
			request.getSession().setAttribute("deptCode", orgCode);

			// 查找关联下级指标
			List<Group> groupList = rbacService.getUserGroups(ssoUser);

			if (groupList == null || groupList.size() == 0) {
				request.setAttribute("message", "用户没有分配权限组!");
				return mapping.findForward("message");
			}
			List<String> groupIds = new ArrayList<String>();
			for (int i = 0; i < groupList.size(); i++) {
				groupIds.add(groupList.get(i).getUuid());
			}
			String[] groupIdsArray = new String[groupIds.size()];
			request.getSession().setAttribute("louverGroup", groupIds.toArray(groupIdsArray));// 用户选择使用的登录组
			// 根据ID
			String sysRootIndexUuid = getLouverRootIndexId();
			List<Index> indexList = indexService.findChildIndexByUser(ssoUser, 0, sysRootIndexUuid);
			Collections.sort(indexList);

			request.setAttribute("indexList", indexList);

			request.getSession().setAttribute("theme", "green");
			createMenus(request);
			SystemCode systemCode = new SystemCode();
			systemCode.setRegCode("hn4_sys_login_after");
			List<SystemCode> list = systemCodeService.findByExample(systemCode);
			if (list != null && list.size() == 1) {
				systemCode = list.get(0);
				request.setAttribute("systemCode", systemCode);
			}

			if (ssoUser != null) {
				if (groupList == null || groupList.size() == 0) {
					request.setAttribute("message", "用户没有分配权限组!");
					return mapping.findForward("message");
				} else {
					String[] groupUuids = new String[groupList.size()];
					for (int i = 0; i < groupList.size(); i++) {
						groupUuids[i] = groupList.get(i).getUuid();
					}
					request.getSession().setAttribute("loginGroup", groupUuids);
				}
				request.setAttribute("multiGroup", true);
			}

			String forward = request.getParameter("forward");
			if (StringUtils.isNotEmpty(forward)) {
				request.getRequestDispatcher(forward).forward(request, response);
			} else {
				request.setAttribute("message", "没有找到页面!");
				return mapping.findForward("message");
			}
			return null;
		} else {
			request.setAttribute("message", "用户名或密码错误!");
			return mapping.findForward("logoutLouver");
		}
	}
}
