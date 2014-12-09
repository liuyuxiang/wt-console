package com.wt.uum2.web;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.components.other.project.ProjectResolver;
import com.hirisun.rd1.secret.Decryptor;
import com.wt.uum.web.version.UumWebVersionUtils;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.PinyinUtil;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.ResourceType;
import com.wt.uum2.constants.StringParse;
import com.wt.uum2.constants.UUMDateFormat;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.constants.WebMenu;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.AttributeValue;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentAuthor;
import com.wt.uum2.domain.Duty;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.ResourceLog;
import com.wt.uum2.domain.StringValue;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserAuthor;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.mail.SendMail;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.DutyService;
import com.wt.uum2.service.TaskListService;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.UUMTempDataService;
import com.wt.uum2.service.UserListService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-10
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class IndexController extends BaseController
{

	/**
	 * 
	 */
	private UUMService uumService;
	/**
	 * 
	 */
	private TaskListService taskListService;
	/**
	 * 
	 */
	private UUMTempDataService uumTempDataService;
	/**
	 * 
	 */
	private EventFactory eventFactory;
	/**
	 * 
	 */
	private EventListenerHandler eventListenerHandler;
	/**
	 * 
	 */
	private SendMail sendMail;

	/**
	 * 
	 */
	private UserListService userListService;

	/**
	 * 
	 */
	private DepartmentPathService departmentPathService;
	
	private DutyService dutyService;

	/**
	 * @param uumTempDataService
	 *            the uumTempDataService to set
	 */
	public void setUumTempDataService(UUMTempDataService uumTempDataService)
	{
		this.uumTempDataService = uumTempDataService;
	}

	/**
	 * @param departmentPathService
	 *            the departmentPathService to set
	 */
	public void setDepartmentPathService(DepartmentPathService departmentPathService)
	{
		this.departmentPathService = departmentPathService;
	}

	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

	public void setUserListService(UserListService userListService)
	{
		this.userListService = userListService;
	}

	public void setSendMail(SendMail sendMail)
	{
		this.sendMail = sendMail;
	}

	/**
	 * 
	 */
	final static public SimpleDateFormat DATE_FORMAT_SHOW = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public void setTaskListService(TaskListService taskListService)
	{
		this.taskListService = taskListService;
	}

	/**
	 * 方法说明：indexHandler
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/index")
	public void indexHandler(ModelMap model)
	{
		model.addAttribute("ver", UumWebVersionUtils.getVersion().getWholeVersion());
	}

	// //////////////////////////数据库初始化数据//////////////////////

	/**
	 * 方法说明：数据库初始化数据
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/install")
	public String installHandler(ModelMap model)
	{
		Department d = uumService.getDepartmentByDeptCode(InitParameters.getRootDepartmentCode());
		boolean flag = false;
		if (d == null) {
			d = new Department();
			d.setCode(InitParameters.getRootDepartmentCode());
			d.setName(InitParameters.getRootDepartmentName());
			model.addAttribute("rootdept", d);
			flag = true;
		}
		User u = uumService.getUserByUserId(InitParameters.getSuperUserCode());
		if (u == null) {
			u = new User();
			u.setId(InitParameters.getSuperUserCode());
			u.setName(InitParameters.getSuperUserName());
			flag = true;
		}
		model.addAttribute("superuser", u);
		model.addAttribute("password", InitParameters.getUserPassword());
		if (flag) {
			return "install";
		} else {
			return "index";
		}
	}

	/**
	 * 初始化数据操作
	 * 
	 * @param deptname
	 *            deptname
	 * @param password
	 *            password
	 * @param username
	 *            username
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/setup")
	public String setupHandler(@RequestParam(value = "deptname", required = false) String deptname,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "username", required = false) String username, ModelMap model)
	{
		Boolean flag = true;
		try {
			Date date = Calendar.getInstance().getTime();
			Group g = uumService.getGroupByCode(InitParameters.getRootGroupCode());
			if (g == null) {
				g = new Group();
				g.setAppGroupType("0");
				g.setGroupType("0");
				g.setHasChildren(true);
				g.setModifiedTime(date);
				g.setOrder(0L);
				g.setStatus(ResourceStatus.NORMAL.intValue());
				g.setCode(InitParameters.getRootGroupCode());
				g.setName(InitParameters.getRootGroupName());
				uumService.createGroup(g);
			}

			Set<Group> parents = new HashSet<Group>();
			parents.add(g);
			Group supergroup = uumService.getGroupByCode(InitParameters.getSuperGroupCode());

			if (supergroup == null) {
				supergroup = new Group();
				supergroup.setCode(InitParameters.getSuperGroupCode());
				supergroup.setAppGroupType("0");
				supergroup.setGroupType("0");
				supergroup.setHasChildren(true);
				supergroup.setModifiedTime(date);
				supergroup.setName("超级管理员组");
				supergroup.setOrder(0L);
				supergroup.setStatus(ResourceStatus.NORMAL.intValue());
				supergroup.setParents(parents);
				uumService.createGroup(supergroup);
			}

			Group appgroup = uumService.getGroupByCode(InitParameters.getRootApplicationGroup());
			if (appgroup == null) {
				appgroup = new Group();
				appgroup.setCode(InitParameters.getRootApplicationGroup());
				appgroup.setAppGroupType("0");
				appgroup.setGroupType("0");
				appgroup.setHasChildren(true);
				appgroup.setModifiedTime(date);
				appgroup.setName("应用系统授权");
				appgroup.setOrder(0L);
				appgroup.setStatus(ResourceStatus.NORMAL.intValue());
				appgroup.setParents(parents);
				uumService.createGroup(appgroup);
			}

			Department d = uumService.getDepartmentByDeptCode(InitParameters
					.getRootDepartmentCode());
			if (d == null) {
				d = new Department();
				d.setCode(InitParameters.getRootDepartmentCode());
				if (StringUtils.isBlank(deptname)) {
					deptname = InitParameters.getRootDepartmentName();
				}
				d.setName(deptname);
				d.setDeptCode(d.getCode());
				d.setDeptParentCode(d.getCode());
				d.setHasChildren(true);
				d.setLastUpdateTime(date);
				d.setLevel(0);
				d.setModifiedTime(date);
				d.setOrder(0L);
				d.setOrgCode(d.getCode());
				d.setPath(deptname);
				d.setStatus(ResourceStatus.NORMAL.intValue());
				uumService.createDepartment(d);

			}

			String dummy = "dummy";
			Department dept = uumService.getDepartmentByDeptCode(dummy);
			if (dept == null) {
				// create dummy部门
				dept = new Department();
				dept.setCode("dummy");
				dept.setDeptCode("dummy");
				dept.setDeptParentCode(d.getDeptCode());
				dept.setHasChildren(true);
				dept.setLastUpdateTime(date);
				dept.setModifiedTime(date);
				dept.setName("dummy");
				dept.setOrder(0L);
				dept.setOrgCode("dummy");
				dept.setParent(d);
				dept.setParentUUID(d.getUuid());
				dept.setStatus(ResourceStatus.NORMAL.intValue());
				uumService.createDepartment(dept);
			}

			User u = uumService.getUserByUserId(InitParameters.getSuperUserCode());
			boolean userisexist = false;
			if (u == null) {
				u = new User();
				u.setId(InitParameters.getSuperUserCode());
			} else {
				userisexist = true;
			}
			u.setName(StringUtils.defaultIfEmpty(username, InitParameters.getSuperUserName()));
			Set<Department> depts = new HashSet<Department>();
			depts.add(d);
//			u.setDepartments(depts);
			Set<Group> groups = new HashSet<Group>();
			groups.add(supergroup);
//			u.setGroups(groups);
			u.setModifiedTime(date);
			u.setOrder(0L);

			if (InitParameters.getMD5EncodePassTurnOn().equalsIgnoreCase("true")) {
				if (StringUtils.isNotBlank(password)) {
					u.setPassword(StringParse.md5(password));
				} else {
					u.setPassword(StringParse.md5(InitParameters.getUserPassword()));
				}
			} else if (InitParameters.getPlainPassword() != null
					&& InitParameters.getPlainPassword().equalsIgnoreCase("true")) {
				if (StringUtils.isNotBlank(password)) {
					u.setPlainPassword(password);
				} else {
					u.setPlainPassword(InitParameters.getUserPassword());
				}
			}
			u.setPrimaryDepartment(d);
			u.setPrimaryDepartmentUUID(d.getUuid());
			u.setStatus(ResourceStatus.NORMAL.intValue());
			if (userisexist) {
				uumService.updateUser(u);
			} else {
				uumService.createUser(u);
			}
			uumService.addUserGroups(u, groups);
			uumService.addUserDepartments(u, depts);

		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			model.addAttribute("Finally", String.valueOf(flag));
		}
		return "/user/confirmationuser";
	}

	// //////////////////////////数据库初始化数据结束//////////////////////

	/**
	 * 方法说明：loginHandler
	 * 
	 * @param userid
	 *            userid
	 * @param password
	 *            password
	 * @param hRequest
	 *            hRequest
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/login")
	public String loginHandler(@RequestParam(value = "u", required = false) String userid,
			@RequestParam(value = "p", required = false) String password,
			HttpServletRequest hRequest, ModelMap model,HttpServletResponse hResponse)
	{

//		if(StringUtils.isNotBlank(hRequest.getHeader(""))){
//			if(StringUtils.equals(hRequest.getHeader(""), userid)){
//				User u = uumService.getUserByUserId(userid);
//				if(u!=null){
//					password = u.getPlainPassword();
//				}
//			}
//		}
		if("sh".equals(ProjectResolver.getId())&&StringUtils.isNotBlank(userid)){
			User u = uumService.getUserByUserId(userid);
			if(u!=null){
				password = u.getPlainPassword();
			}
		}
		UsernamePasswordToken token = new UsernamePasswordToken(userid, password);
		String url = "redirect:/main.nsf";
		try {
			SecurityUtils.getSubject().login(token);
			com.hirisun.components.security.lcta.LctaContextHolder.flush(hRequest, hResponse);
		} catch (AuthenticationException e) {
			url = "/error";
		}
		if(StringUtils.isNotBlank(hRequest.getParameter("t"))){
			model.addAttribute("url", hRequest.getParameter("t"));
			url = "redirect";
		}
		return url;
	}

	/**
	 * 方法说明：logoutHandler
	 * 
	 * @return String
	 */
	@RequestMapping("/logout")
	public String logoutHandler(final HttpServletRequest request,final HttpServletResponse response)
	{
		SecurityUtils.getSubject().logout();
		com.hirisun.components.security.lcta.LctaContextHolder.flush(request, response);
		return "redirect:/w/login";
	}

	/**
	 * 方法说明：defaultbackgroundHandler
	 * 
	 */
	@RequestMapping("/defaultbackground")
	public void defaultbackgroundHandler()
	{
	}

	/**
	 * 方法说明：publictreeHandler
	 * 
	 * @param menuId
	 *            menuId
	 * @param flag
	 *            flag
	 * @param type
	 *            type
	 * @param deptuuids
	 *            deptuuids
	 * @param mydeptuuid
	 *            mydeptuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/publictree")
	public String publictreeHandler(
			@RequestParam(value = "menuId", required = false) Integer menuId,
			@RequestParam(value = "flag", required = false) String flag,
			@RequestParam("type") String type, @RequestParam("deptuuids") String deptuuids,
			@RequestParam("mydeptuuid") String mydeptuuid, ModelMap model)
	{

		WebMenu currentMenu = null;
		if (menuId != null) {
			currentMenu = WebMenu.valueOf(menuId);
		} else {
			currentMenu = WebMenu.DEPARTMENT_PAGE;
		}
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		if (flag == null || !flag.equals("true")) {
			for (Group group : uumService.getUserGroups(loginUser)) {
				if (group.getCode().equals(InitParameters.getSuperGroupCode())) {
					flag = "true";
					break;
				}
			}
		}
		switch (currentMenu) {
		case DEPARTMENT_PAGE:
		{
			Department deptTreeRoot = uumService.getDepartmentRoot();
			List<Department> deptChildren = uumService.getDepartmentChildren(
					deptTreeRoot.getUuid(), loginUser, flag);
			model.addAttribute("deptTreeRoot", deptTreeRoot);
			model.addAttribute("deptChildren", deptChildren);
		}
			break;
		}

		List<WebMenu> menulist = new ArrayList<WebMenu>();
		menulist.add(WebMenu.DEPARTMENT_PAGE);
		menulist.add(WebMenu.UNKNOWN_PAGE);

		model.addAttribute("menuList", menulist);
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("type", type);
		model.addAttribute("flag", flag);
		model.addAttribute("deptuuids", deptuuids);
		model.addAttribute("mydeptuuid", mydeptuuid);
		return "/publictree";
	}

	/**
	 * 方法说明：contentHandler
	 * 
	 * @param id
	 *            id
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param viewstatus
	 *            viewstatus
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/dept/content")
	public String contentHandler(@RequestParam("id") String id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "viewstatus", required = false) String viewstatus, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		UserPageResult<User> upr = uumService.getUserMembersByDepartment(page, pagesize,
				deptChildren);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("deptChildren", deptChildren);
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, deptChildren));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/content";
	}

	/**
	 * 方法说明：viewsubdeptHandler
	 * 
	 * @param id
	 *            id
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param viewstatus
	 *            viewstatus
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/viewsubdept")
	public String viewsubdeptHandler(@RequestParam("id") String id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "viewstatus", required = false) String viewstatus, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		UserPageResult<User> upr = uumService.getUserMembersUnderDepartment(page, pagesize,
				deptChildren);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, deptChildren));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/content";
	}

	/**
	 * 方法说明：deptsearchHandler
	 * 
	 * @param uuid
	 *            uuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchvalue
	 *            searchvalue
	 * @param searchcontent
	 *            searchcontent
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/dept/deptsearch")
	public String deptsearchHandler(@RequestParam("id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Department deptChildren = uumService.getDepartmentByUUID(uuid);
		Condition condition = new Condition();
		UserPageResult<User> upr = new UserPageResult<User>();
		boolean isAdmin = uumService.isDepartmentManager(loginUser, deptChildren);
		String orgCode = uumService.getUserPrimaryDepartment(loginUser).getOrgCode();
		if (searchvalue.indexOf("username") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("userid") != -1) {
			condition.setUserId(searchcontent);
		}

		boolean isSuper = uumService.isUserinSuperGroup(loginUser);

		if (searchvalue.indexOf("ERP") != -1) {
			if (isSuper || (InitParameters.isCqGroupAuthor() && isModifyGroup(loginUser, model))) {
				upr = uumService.searchERPUsersByCondition(page, pagesize, condition);
			} else {
				upr = uumService.searchERPUsersByConditionAndOrgCode(page, pagesize, condition,
						orgCode);
			}
		} else if (searchvalue.indexOf("dept") != -1) {
			DepartmentCondition dc = new DepartmentCondition();
			if (searchvalue.indexOf("deptid") != -1) {
				dc.setId(searchcontent);
			} else {
				dc.setName(searchcontent);
			}
			if (isSuper || (InitParameters.isCqGroupAuthor() && isModifyGroup(loginUser, model))) {
				upr = uumService.searchUsersByDepartmentCondition(page, pagesize, dc, loginUser);
			} else {
				Department org = uumService.getDepartmentByDeptCode(orgCode);
				upr = uumService
						.searchUsersByDepartmentConditionAndOrgCode(page, pagesize, dc, org);
			}
		} else {
			if (isSuper || (InitParameters.isCqGroupAuthor() && isModifyGroup(loginUser, model))) {
				upr = uumService.searchUsersByCondition(page, pagesize, condition);
			} else {
				upr = uumService.searchUsersByConditionAndOrgCode(page, pagesize, condition,
						orgCode);
			}
		}
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("isSuperAdmin", isSuper);
		model.addAttribute("loginUser", loginUser);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/deptsearch";
	}

	/**
	 * 方法说明：getCode
	 * 
	 * @param pDept
	 *            pDept
	 * @return String
	 */
	public String getCode(Department pDept)
	{
		for (int i = 1; i < 100; i++) {
			String code = pDept.getDeptCode();
			code = code.replaceFirst("06", "");
			code = code.replaceAll("0{2,8}", "");
			code += String.valueOf((100 + i)).substring(1);
			if (!uumService.existDepartmentCode(("06" + code + "000000").substring(0, 10))) {
				return ("06" + code + "000000").substring(0, 10);
			}
		}
		return null;
	}

	/**
	 * 方法说明：adddeptHandler
	 * 
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/dept/adddept")
	public String adddeptHandler(@RequestParam("id") String id, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		if ("true".equals(InitParameters.getCreateDefaultDeptCode())) {
			String sgccOrgId = getCode(deptChildren);
			model.addAttribute("sgccOrgId", sgccOrgId);
			model.addAttribute("ROOTsgccOrgId", getCode(uumService.getDepartmentRoot()));
		}
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("isCQ", String.valueOf(InitParameters.isCqGroupAuthor()));
		List<AttributeType> typeList = uumService.getAttributeTypeAllByResource(new Department(),
				ResourceType.DEPARTMENT.intValue());
		model.addAttribute("typeList", typeList);
		model.addAttribute("uumService", uumService);
		model.addAttribute("superstatus", uumService.isUserinSuperGroup(loginUser));
		return "/dept/adddept";
	}

	/**
	 * 方法说明：adddeptfromHandler
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            name
	 * @param orderNum
	 *            orderNum
	 * @param code
	 *            code
	 * @param deptcode
	 *            deptcode
	 * @param viewstatus
	 *            viewstatus
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/adddeptfrom")
	public String adddeptfromHandler(@RequestParam("id") String id,
			@RequestParam("name") String name, @RequestParam("orderNum") String orderNum,
			@RequestParam("code") String code, @RequestParam("deptcode") String deptcode,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			final HttpServletRequest request, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Department parent = uumService.getDepartmentByUUID(id);
		Department root = uumService.getDepartmentRoot();
		Department dept = new Department();
		dept.setName(name);
		dept.setOrder(Long.parseLong(orderNum));
		dept.setCode(code);
		dept.setDeptCode(deptcode);
		dept.setParent(parent);
		dept.setParentUUID(parent.getUuid());
		if (parent.getUuid().equalsIgnoreCase(root.getUuid())) {
			dept.setOrgCode(deptcode);
			dept.setStatus(ResourceStatus.NORMAL.intValue());
		} else {
			dept.setOrgCode(parent.getOrgCode());
			dept.setStatus(ResourceStatus.NORMAL.intValue());
		}
		dept.setDeptParentCode(parent.getDeptCode());
		dept.setHasChildren(false);
		dept.setPath(parent.getPath() + "→" + name);
		DepartmentAuthor author = new DepartmentAuthor();
		if (jsgroupuuid != null) {
			String[] jsgroupuuids = jsgroupuuid.split(",");
			Set<Group> grouplist = new HashSet<Group>();
			if (jsgroupuuid.length() > 0) {
				for (int i = 0; i < jsgroupuuids.length; i++) {
					grouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
				}
				dept.setAdminGroups(grouplist);
			}
		}
		dept.setRtxCode(uumService.countDepartmentForRtx());
		if (!parent.isHasChildren()) {
			parent.setHasChildren(true);
			uumService.updateDepartment(parent);
		}
		uumService.createDepartment(dept);

		if (StringUtils.equals(ProjectResolver.getId(), "bj")) {
			// 北供创建ids_dn属性
			createDeptSyncToIDS(parent, dept);
		}
		updateDeptAttribute(dept, request);
		if (author.getLevels() != null) {
			dept = uumService.getDepartmentByDeptCode(dept.getDeptCode());
			author.setDepartment(dept);
			uumService.saveDepartmentAuthor(author);
		}

		Event event = eventFactory.createEventCreateDept(dept.getUuid());
		eventListenerHandler.handle(event);
		model.addAttribute("deptChildren", parent);
		UserPageResult<User> upr = uumService.getUserMembersByDepartment(1, 15, parent);
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, parent));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		if (InitParameters.getModifyDeptInUser() != null
				&& InitParameters.getModifyDeptInUser().equalsIgnoreCase("false")) {
			return "redirect:/dept/deptcontent?id=" + parent.getUuid();
		} else {
			return "/dept/content";
		}
	}

	/**
	 * 方法说明：createDepartmentHandler
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            name
	 * @param orderNum
	 *            orderNum
	 * @param code
	 *            code
	 * @param deptcode
	 *            deptcode
	 * @param viewstatus
	 *            viewstatus
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/dept/createdepartment")
	public String createDepartmentHandler(@RequestParam("id") String id,
			@RequestParam("name") String name, @RequestParam("orderNum") String orderNum,
			@RequestParam("code") String code, @RequestParam("deptcode") String deptcode,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Boolean flag = Boolean.FALSE;
		try {
			Department deptChildren = uumService.getDepartmentByUUID(id);
			Department root = uumService.getDepartmentRoot();
			Department newdeptChildren = new Department();
			newdeptChildren.setName(name);
			newdeptChildren.setOrder(Long.parseLong(orderNum));
			newdeptChildren.setCode(code);
			newdeptChildren.setDeptCode(deptcode);
			newdeptChildren.setParent(deptChildren);
			newdeptChildren.setParentUUID(deptChildren.getUuid());
			if (deptChildren.getUuid().equalsIgnoreCase(root.getUuid())) {
				newdeptChildren.setOrgCode(deptcode);
				newdeptChildren.setStatus(ResourceStatus.NORMAL.intValue());
			} else {
				newdeptChildren.setOrgCode(deptChildren.getOrgCode());
				newdeptChildren.setStatus(ResourceStatus.NORMAL.intValue());
			}
			newdeptChildren.setDeptParentCode(deptChildren.getDeptCode());
			newdeptChildren.setHasChildren(false);
			newdeptChildren.setPath(deptChildren.getPath() + "→" + name);
			// DepartmentAuthor author = new DepartmentAuthor();
			if (jsgroupuuid != null) {
				String[] jsgroupuuids = jsgroupuuid.split(",");
				Set<Group> grouplist = new HashSet<Group>();
				if (jsgroupuuid.length() > 0) {
					for (int i = 0; i < jsgroupuuids.length; i++) {
						grouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
					}
					newdeptChildren.setAdminGroups(grouplist);
				}
			}
			newdeptChildren.setRtxCode(uumService.countDepartmentForRtx());
			if (!deptChildren.isHasChildren()) {
				deptChildren.setHasChildren(true);
				uumService.updateDepartment(deptChildren);
			}
			uumService.createDepartment(newdeptChildren);

			if (StringUtils.equals(ProjectResolver.getId(), "bj")) {
				// 北供创建ids_dn属性
				createDeptSyncToIDS(deptChildren, newdeptChildren);
			}

			Event event = eventFactory.createEventCreateDept(newdeptChildren.getUuid());
			eventListenerHandler.handle(event);
			flag = Boolean.TRUE;
		} catch (Exception e) {
			logger.error("create department " + code + "(" + name + ") is failed!", e);
		}
		model.addAttribute("Finally", String.valueOf(flag));
		return "/dept/confirmationdept";
	}

	// 北供创建ids_dn属性
	/**
	 * 方法说明：createDeptSyncToIDS
	 * 
	 * @param parent
	 *            parent
	 * @param current
	 *            current
	 */
	private void createDeptSyncToIDS(Department parent, Department current)
	{
		List<AttributeType> attributeTypeList = uumService.getAttributeTypeById("dept_ids_dn");
		for (int i = 0; i < attributeTypeList.size(); i++) {
			String parentDN = getDeptDNWithIBM(parent);
			AttributeType attributeType = attributeTypeList.get(i);
			Attribute att = new Attribute();
			StringValue sv = new StringValue();
			Set<AttributeValue> av = new HashSet<AttributeValue>();
			att.setType(attributeType);
			att.setOwnerResource(current);
			av.add(sv);
			att.setAttValues(av);
			sv.setAttribute(att);
			sv.setValue(parentDN.equals("") ? "" : ("ou=" + current.getDeptCode() + "," + parentDN));
			uumService.saveAttribute(att);
		}
	}

	/**
	 * 方法说明：isModifyGroup
	 * 
	 * @param user
	 *            user
	 * @param model
	 *            model
	 * @return boolean
	 */
	public boolean isModifyGroup(User user, ModelMap model)
	{
		boolean isModifyGroup = false;
		if (uumService.isUserinSuperGroup(user)) {
			isModifyGroup = true;
		} else if (InitParameters.isCqGroupAuthor()) {
			isModifyGroup = uumService.getUserGroups(user).contains(
					uumService.getGroupByCode(InitParameters.modifyUserGroupCode()));
		}
		model.addAttribute("isModifyGroup", isModifyGroup);
		return isModifyGroup;
	}

	/**
	 * 方法说明：adduserHandler
	 * 
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/adduser")
	public String adduserHandler(@RequestParam("id") String id, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		model.addAttribute("deptChildren", deptChildren);
		User user = new User();
		List<AttributeType> attributelist0 = uumService.getAttributeTypeByResourceOnPage(user, 0,
				"0");
		List<AttributeType> attributelist1 = uumService.getAttributeTypeByResourceOnPage(user, 0,
				"1");
		List<AttributeType> attributelist2 = uumService.getAttributeTypeByResourceOnPage(user, 0,
				"2");
		model.addAttribute("attributelist0", attributelist0);
		model.addAttribute("attributelist1", attributelist1);
		model.addAttribute("attributelist2", attributelist2);
		model.addAttribute("isMultiDept", Boolean.valueOf(InitParameters.isMultiDept()));
		model.addAttribute("isVirtualUser", isVirtualUser(deptChildren));
		if (InitParameters.isSendMailToMailManager()) {
			model.addAttribute("sengMail", InitParameters.isSendMailToMailManager());
		}
		model.addAttribute("group", uumService.getGroupByCode(InitParameters.getSuperGroupCode()));
		model.addAttribute("isDefaultAdminUser", InitParameters.isCreateDefaultAdminUser());
		model.addAttribute("admin", uumService.isUserinSuperGroup(loginUser));
		return "/user/adduser";
	}

	/**
	 * 方法说明：isVirtualUser
	 * 
	 * @param deptChildren
	 *            deptChildren
	 * @return boolean
	 */
	private boolean isVirtualUser(Department deptChildren)
	{
		if (InitParameters.isCqGroupAuthor()
				&& StringUtils.equals(deptChildren.getOrgCode(), "1000"))
			return true;
		return false;
	}

	/**
	 * 方法说明：判断扩展属性是否是默认加载的应用系统组的相关属性,如果是则返回true
	 * 
	 * @param apps
	 *            应用系统组列表
	 * @param typeid
	 *            扩展属性id
	 * @return boolean
	 */
	private boolean isDefaultAttWithApp(List<Group> apps, String typeid)
	{
		if (apps != null && !apps.isEmpty()) {
			for (Group group : apps) {
				if (typeid.contains(group.getCode())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 方法说明：创建新增用户时产生的扩展属性
	 * 
	 * @param user
	 *            用户资源
	 * @param request
	 *            页面属性
	 */
	private void createUserAttributeWhenAdduser(User user, final HttpServletRequest request)
	{
		List<AttributeType> attributelist = uumService.getAttributeTypeAllByResource(user, 0);
		String appStatusCode = InitParameters.getAppStatusCode().split("XXXX")[1];
		String appAccount = InitParameters.getAppAccountLocal().split("XXXX")[1];
		String appLoginEnable = InitParameters.getAppLoginEnableLocal().split("XXXX")[1];
		// Department dept = user.getPrimaryDepartment();
		// 处理默认应用系统设置
		List<Group> appGroups = uumService.getDefaultAddAppGroup();
		if (appGroups != null && appGroups.size() > 0) {
			uumService.addUserGroups(user, appGroups);
		}
		for (AttributeType att : attributelist) {
			Attribute attribute = new Attribute();
			attribute.setOwnerResource(user);
			attribute.setType(att);
			Set<AttributeValue> avSet = new HashSet<AttributeValue>();
			StringValue av = new StringValue();
			av.setAttribute(attribute);
			if (att.getId().equals("loginDisabled")) {
				if (user.getCurrentAuthorLevel() == 0) {
					av.setValue(Boolean.FALSE.toString());
				} else {
					av.setValue(Boolean.TRUE.toString());
				}
			} else if (att.getId().equals("userPassword")) {
				continue;
			} else if (att.getId().equalsIgnoreCase("cqPORTALAccount")) {
				av.setValue(user.getId());
			} else if (att.getId().equalsIgnoreCase("cqPORTALPwd")) {
				if (InitParameters.getPlainPassword() != null
						&& InitParameters.getPlainPassword().equalsIgnoreCase("true")) {
					av.setValue(user.getPlainPassword());
				} else {
					av.setValue(user.getPassword());
				}
			} else if (att.getId().contains("pwdChangeTime")) {
				UUMDateFormat df = new UUMDateFormat();
				av.setValue(df.switchLongToDateFormat(System.currentTimeMillis()));
			} else if (att.getId().equalsIgnoreCase("sgccUserId")) {
				Integer num = Integer.valueOf(uumService.getLastUserId("sgccUserId")) + 1;
				av.setValue(String.valueOf(num).length() == 10 ? String.valueOf(num) : "0"
						+ String.valueOf(num));
			} else if (att.getId().equalsIgnoreCase("dataCameFrom")) {
				av.setValue(String.valueOf(1));
			} else if (request.getParameter(att.getId()) != null) {
				av.setValue(request.getParameter(att.getId()));
			} else {
				if (att.getId().endsWith(appStatusCode)) {
					if (isDefaultAttWithApp(appGroups, att.getId())) {
						av.setValue(String.valueOf(ResourceStatus.AUTHORIZE.intValue()));
					} else {
						av.setValue(String.valueOf(ResourceStatus.UNDEAL.intValue()));
					}
				} else if (att.getId().endsWith(appAccount)) {
					if (isDefaultAttWithApp(appGroups, att.getId())) {
						av.setValue(user.getId());
					} else {
						av.setValue("");
					}
				} else if (att.getId().endsWith(appLoginEnable)) {
					if (isDefaultAttWithApp(appGroups, att.getId())) {
						av.setValue(Boolean.TRUE.toString());
					} else {
						av.setValue(Boolean.FALSE.toString());
					}
				} else if (att.getId().contains("Pwd") || att.getId().contains("Password")) {
					if (InitParameters.getPlainPassword() != null
							&& InitParameters.getPlainPassword().equalsIgnoreCase("true")) {
						av.setValue(user.getPlainPassword());
					} else {
						av.setValue(user.getPassword());
					}
				} else {
					av.setValue("");
				}
			}
			avSet.add(av);
			attribute.setAttValues(avSet);
			uumService.saveAttribute(attribute);
			uumService.saveStringValue(av);
		}
	}

	/**
	 * 方法说明：adduserfromHandler
	 * 
	 * @param userid
	 *            userid
	 * @param name
	 *            name
	 * @param primaryUserId
	 *            primaryUserId
	 * @param orderNum
	 *            orderNum
	 * @param deptuuid
	 *            deptuuid
	 * @param viewstatus
	 *            viewstatus
	 * @param maindeptuuid
	 *            maindeptuuid
	 * @param groupuuid
	 *            groupuuid
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param sengMail
	 *            sengMail
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/adduserfrom")
	public String adduserfromHandler(@RequestParam("userid") String userid,
			@RequestParam("name") String name,
			@RequestParam(value = "primaryUserId", required = false) String primaryUserId,
			@RequestParam("orderNum") String orderNum, @RequestParam("deptuuid") String deptuuid,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "maindeptuuid", defaultValue = "") String maindeptuuid,
			@RequestParam(value = "groupuuid", required = false) String groupuuid,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			@RequestParam(value = "sengMail", required = false) String sengMail,
			final HttpServletRequest request, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		User user = new User();
		String[] deptuuids = deptuuid.split(",");
		Department primaryDept = uumService.getDepartmentByUUID(deptuuids[0]);
		Set<Department> deptChildrens = new HashSet<Department>();
		for (int i = 0; i < deptuuids.length; i++) {
			Department deptChildren = uumService.getDepartmentByUUID(deptuuids[i]);
			deptChildrens.add(deptChildren);
		}
		Set<Group> groupChildrens = new HashSet<Group>();
		Group group = null;
		user.setId(userid.trim());
		user.setName(name);
		if (primaryUserId != null && primaryUserId.length() > 0) {
			User primaryUser = uumService.getUserByUserId(primaryUserId);
			user.setPrimaryUser(primaryUser);
		}
		user.setDepartments(deptChildrens);
		user.setOrder(Long.parseLong(orderNum));
		user.setStatus(Integer.valueOf(ResourceStatus.CREATED.intValue()));
		user.setCurrentAuthorLevel(Long.valueOf(1));
		user.setPrimaryDepartment(primaryDept);
		uumService.createUser(user);
		String[] groupuuids = null;
		if (groupuuid != null && !groupuuid.equals("")) {
			groupuuids = groupuuid.split(",");
			for (int j = 0; j < groupuuids.length; j++) {
				group = uumService.getGroupByUuid(groupuuids[j]);
				groupChildrens.add(group);
			}
			uumService.addUserGroups(user, groupChildrens);
		}

		String[] jsgroupuuids = jsgroupuuid.split(",");
		List<Group> grouplist = new ArrayList<Group>();
		if (jsgroupuuid.length() > 0) {
			for (int i = 0; i < jsgroupuuids.length; i++) {
				grouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
			}
			if (!isModifyGroup(loginUser, model)) {
				UserAuthor author = new UserAuthor();
				author.setLevels("1");
				author.setUser(user);
				author.setGroup(uumService.getGroupByUuid(jsgroupuuids[0]));
				uumService.saveUserAuthor(author);
			}
			uumService.addUserManagerGroups(user, grouplist);
		}
		if (isModifyGroup(loginUser, model)) {
			user = uumService.getUserByUserId(user.getId());
			user.setStatus(Integer.valueOf(ResourceStatus.NORMAL.intValue()));
			user.setCurrentAuthorLevel(Long.valueOf(0));
			uumService.updateUser(user);
		}

		createUserAttributeWhenAdduser(user, request);
		if (isVirtualUser(primaryDept)) {
			modifyUserDeptAttribute(user, uumService.getDepartmentByUUID(maindeptuuid));
		} else {
			modifyUserDeptAttribute(user, primaryDept);
		}
		Event event = eventFactory.createEventCreateUser(user.getUuid());
		// 如果是重庆,需要将用户的erp编码映射到映射表中
		if (InitParameters.isCqGroupAuthor()
				&& StringUtils.isNotBlank(event.getParamValuesMap().getSingle("sgccEmployeeCode"))) {
			uumTempDataService.addResourceMapping(user,
					event.getParamValuesMap().getSingle("sgccEmployeeCode"));
		}
		eventListenerHandler.handle(event);
		UserPageResult<User> upr = uumService.getUserMembersByDepartment(1, 15, primaryDept);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("deptChildren", primaryDept);
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, primaryDept));
		if (sengMail != null && sengMail.equalsIgnoreCase("0")) {
			ArrayList<String> keys = new ArrayList<String>();
			keys.add("mail");
			InternetAddress from = new InternetAddress();
			String address = null;
			try {
				address = uumService.getAttributesByAttributeTypeIdKey(loginUser, keys).get(0)
						.getAttValues().iterator().next().getValue().toString();
			} catch (Exception e) {
				address = "uc@hirisun.com";
			}
			from.setAddress(address);
			try {
				from.setPersonal(loginUser.getName(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String msg = user.getName() + ":" + request.getParameter("mail");
			sendMail.openMail(from, msg);
		}
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/content";
	}

	/**
	 * 方法说明：getLastUserId
	 * 
	 * @param typeid
	 *            typeid
	 * @return String
	 */
	private String getLastUserId(String typeid)
	{
		Integer i = Integer.valueOf(uumService.getLastUserId(typeid)) + 1;
		return String.valueOf(i).length() == 10 ? String.valueOf(i) : "0" + String.valueOf(i);
	}

	/**
	 * 方法说明：gocontentHandler
	 * 
	 * @param id
	 *            id
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @param viewstatus
	 *            viewstatus
	 * @return String
	 */
	@RequestMapping("/gocontent")
	public String gocontentHandler(@RequestParam("id") String id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model,
			@RequestParam(value = "viewstatus", required = false) String viewstatus)
	{
		model.addAttribute("isDeptInUserPage", InitParameters.getModifyDeptInUser());
		return contentHandler(id, page, pagesize, viewstatus, model);
	}

	/**
	 * 方法说明：updatedeptHandler
	 * 
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/dept/updatedept")
	public String updatedeptHandler(@RequestParam("id") String id, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		model.addAttribute("deptChildren", deptChildren);
		List<Group> deptgroup = uumService.getDepartmentManagedGroups(deptChildren);
		List<Attribute> attlist = uumService.getAttributesUnderResource(deptChildren);
		List<AttributeType> attributeTypeList = uumService.getAttributeTypeAllByResource(
				deptChildren, null);
		for (int i = 0; i < attributeTypeList.size(); i++) {
			AttributeType attributeType = attributeTypeList.get(i);
			if (attlist.size() == 0) {
				Attribute att = new Attribute();
				StringValue sv = new StringValue();
				Set<AttributeValue> av = new HashSet<AttributeValue>();
				att.setType(attributeType);
				att.setOwnerResource(deptChildren);
				av.add(sv);
				att.setAttValues(av);
				sv.setAttribute(att);
				// sv.setType(Integer.valueOf(0));
				sv.setValue("");
				uumService.saveAttribute(att);
				// uumService.saveStringValue(sv);
				continue;
			}
			boolean flag = false;
			for (int j = 0; j < attlist.size(); j++) {
				if (attlist.get(j).getType().getUuid().equalsIgnoreCase(attributeType.getUuid())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				Attribute att = new Attribute();
				StringValue sv = new StringValue();
				Set<AttributeValue> av = new HashSet<AttributeValue>();
				att.setType(attributeType);
				att.setOwnerResource(deptChildren);
				av.add(sv);
				att.setAttValues(av);
				sv.setAttribute(att);
				// sv.setType(Integer.valueOf(0));
				sv.setValue("");
				uumService.saveAttribute(att);
				// uumService.saveStringValue(sv);

			}
		}
		attlist = uumService.getAttributesUnderResource(deptChildren);
		model.addAttribute("attlist", attlist);
		model.addAttribute("deptgroup", deptgroup);
		model.addAttribute("isCQ", String.valueOf(InitParameters.isCqGroupAuthor()));
		model.addAttribute("superstatus", uumService.isUserinSuperGroup(uumService.getLoginUser()));
		return "/dept/updatedept";
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param user
	 *            操作用户
	 * @param resourceuuid
	 *            被操作用户的资源标识
	 * @param logid
	 *            修改的属性说明
	 * @param beforeValue
	 *            改前的值
	 * @param afterValue
	 *            改后的值
	 * @param remark
	 *            备注
	 * @param fieldName
	 *            被操作资源说明
	 */
	private void saveResourceLog(User user, String resourceuuid, String logid, String beforeValue,
			String afterValue, String remark, String fieldName)
	{
		ResourceLog log = new ResourceLog();
		log.setAfterValue(afterValue);
		log.setBeforeValue(beforeValue);
		log.setEditDate(Calendar.getInstance().getTime());
		log.setEditPerson(user.getName());
		log.setFieldName(fieldName);
		log.setLogid(logid);
		log.setRemark(remark);
		log.setResourceuuid(resourceuuid);
		uumService.saveResourceLog(log);
	}

	/**
	 * 方法说明：updatedeptfromHandler
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            name
	 * @param orderNum
	 *            orderNum
	 * @param code
	 *            code
	 * @param deptcode
	 *            deptcode
	 * @param parentuuid
	 *            parentuuid
	 * @param viewstatus
	 *            viewstatus
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/updatedeptfrom")
	public String updatedeptfromHandler(@RequestParam("id") String id,
			@RequestParam("name") String name, @RequestParam("orderNum") String orderNum,
			@RequestParam("code") String code, @RequestParam("deptcode") String deptcode,
			@RequestParam("parentuuid") String parentuuid,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			final HttpServletRequest request, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		// //////部门日志/////////////
		Department dept = uumService.getDepartmentByUUID(id);
		Map<String, String[]> attrs = new HashMap<String, String[]>();
		// 部门名称
		if (!dept.getName().equals(name)) {
			attrs.put("name", new String[] { dept.getName(), name });
			dept.setName(name);
		}
		// 部门排序号
		if (dept.getOrder().intValue() != Integer.parseInt(orderNum)) {
			attrs.put("orderNum", new String[] { dept.getOrder().toString(), orderNum });
			dept.setOrder(Long.parseLong(orderNum));
		}
		// 部门编码
		if (!dept.getCode().equals(code)) {
			attrs.put("code", new String[] { dept.getCode(), code });
			dept.setCode(code);
		}
		// 上级部门
		Department parent = uumService.getDepartmentByUUID(StringUtils.trim(parentuuid));

		if (!StringUtils.equals(parentuuid, dept.getParentUUID())) {
			if (!parent.isHasChildren()) {
				parent.setHasChildren(true);
				uumService.updateDepartment(parent);
			}
			attrs.put("parentUUID", new String[] { dept.getParentUUID(), parent.getUuid() });
			uumService.moveDepartmentToNewParent(dept, parent);
			dept.setParent(parent);
			dept.setParentUUID(parent.getUuid());
			dept.setDeptParentCode(parent.getDeptCode());
		}

		String oldPath = dept.getPath();
		String newPath = parent.getPath() + "→" + dept.getName();
		if (!StringUtils.equals(oldPath, newPath)) {
			if (parent.getPath().equals(oldPath)) {
				newPath = dept.getName();
			}
			uumService.updateDeptPath(newPath, oldPath);
			dept.setPath(newPath);
		}
		uumService.updateDepartment(dept);
		// 部门的管理组
		List<Group> grouplist = new ArrayList<Group>();
		List<Group> adminList = uumService.getDepartmentManagedGroups(dept);
		if (StringUtils.isNotBlank(jsgroupuuid)) {
			String[] jsgroupuuids = jsgroupuuid.split(",");
			for (int i = 0; i < jsgroupuuids.length; i++) {
				grouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
			}
		}

		if (!CollectionUtils.isEqualCollection(adminList, grouplist)) {
			uumService.updateDeartmentManagerGroups(dept, grouplist);
			// 部门和组的关系欠缺
		}

		attrs.putAll(updateDeptAttribute(dept, request));
		if (!attrs.isEmpty()) {
			Event event = eventFactory.createEventUpdateDept(dept.getUuid(), attrs);
			eventListenerHandler.handle(event);
		}
		model.addAttribute("deptChildren", dept);
		UserPageResult<User> upr = uumService.getUserMembersByDepartment(1, 15, dept);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("viewstatus", viewstatus == null ? "viewme" : viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, dept));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		if (InitParameters.getModifyDeptInUser() != null
				&& InitParameters.getModifyDeptInUser().equalsIgnoreCase("false")) {
			return "redirect:/dept/deptcontent?id=" + dept.getUuid();
		} else {
			return "/dept/content";
		}
	}

	/**
	 * 方法说明：updateDeptAttribute
	 * 
	 * @param dept
	 *            dept
	 * @param request
	 *            request
	 * @return Map
	 */
	private Map<? extends String, ? extends String[]> updateDeptAttribute(Department dept,
			HttpServletRequest request)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();

		List<AttributeType> typeList = uumService.getAttributeTypeAllByResource(dept, null);
		for (AttributeType type : typeList) {
			String value = request.getParameter(type.getId());
			List<Attribute> attList = uumService.getAttributesByResAndType(dept, type);
			if (CollectionUtils.isEmpty(attList) && StringUtils.isNotBlank(value)) {
				if (StringUtils.isNotBlank(value)) {
					Attribute attr = new Attribute(dept, type);
					attr.setValue(value);
					uumService.saveAttribute(attr);
					map.put(type.getId(), new String[] { null, attr.getValue() });
				}
			} else if (CollectionUtils.isNotEmpty(attList)) {
				if (StringUtils.isNotBlank(value)) {
					for (Attribute attr : attList) {
						if (!value.equals(attr.getValue())) {
							map.put(type.getId(), new String[] { attr.getValue(), value });
							attr.setValue(value);
							uumService.updateAttribute(attr);
						}
					}
				} else {
					for (Attribute attr : attList) {
						if (StringUtils.isNotBlank(attr.getValue())) {
							map.put(type.getId(), new String[] { attr.getValue(), null });
						}
						uumService.deleteAttribute(attr);
					}
				}
			}
		}

		List<Attribute> attlist = uumService.getAttributesUnderResource(dept);
		for (Attribute att : attlist) {
			if (att.getType().getPageType().equals("checkbox")
					|| request.getParameter(att.getType().getId()) == null) {
				continue;
			}
			List<AttributeValue> av = new ArrayList<AttributeValue>();
			av.addAll(att.getAttValues());
			if (av.size() <= 0) {
				StringValue avs = new StringValue();
				avs.setValue(request.getParameter(att.getType().getId()) == null ? "" : request
						.getParameter(att.getType().getId()));
				avs.setAttribute(att);
				uumService.saveStringValue(avs);
			} else {
				for (int h = 0; h < av.size(); h++) {
					String beforeValue = ((StringValue) av.get(h)).getValue();
					if (beforeValue == null) {
						beforeValue = "";
					}
					StringValue avs = (StringValue) av.get(h);
					if (!att.getType().getHidden()) {
						if (!beforeValue.equalsIgnoreCase(request.getParameter(att.getType()
								.getId()))) {
							avs.setValue(request.getParameter(att.getType().getId()));
							uumService.updateStringValue(avs);
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 方法说明：getDeptDNWithIBM
	 * 
	 * @param dept
	 *            dept
	 * @return String
	 */
	private String getDeptDNWithIBM(Department dept)
	{
		String dn = "";
		List<String> keys = new ArrayList<String>();
		keys.add("dept_ids_dn");
		List<com.wt.uum2.domain.Attribute> listatt = uumService
				.getAttributesByAttributeTypeIdKey(dept, keys);
		if (!listatt.isEmpty() && !listatt.get(0).getAttValues().isEmpty()
				&& listatt.get(0).getAttValues().iterator().hasNext()) {
			dn = ((StringValue) listatt.get(0).getAttValues().iterator().next()).getValue();
		}
		return dn == null ? "" : dn;
	}

	/**
	 * 方法说明：从部门移除用户
	 * 
	 * @param id
	 *            id
	 * @param userids
	 *            userids
	 * @param viewstatus
	 *            viewstatus
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return model
	 */
	@RequestMapping("/moveuser")
	public String moveuserfromHandler(@RequestParam("id") String id,
			@RequestParam("userids") String userids,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i]);
			ResourceLog userLog = new ResourceLog();
			userLog.setBeforeValue(deptChildren.getName());
			uumService.removeUserFromDepartment(deptChildren, user);
			// //////日志/////////////
			String remark = "从部门移除";
			String logid = "用户所在部门";
			userLog.setRemark(remark);
			userLog.setLogid(logid);
			userLog.setEditPerson(loginUser.getName());
			userLog.setResourceuuid(user.getUuid());
			userLog.setFieldName(user.getName());
			uumService.saveResourceLog(userLog);
			// /////////////////////////
			Map<String, String[]> map = modifyUserDeptAttribute(user, null);
			List<Event> events = new ArrayList<Event>();
			events.add(eventFactory.createUserEventRemoveDepartment(user.getUuid(), id));
			if (!map.isEmpty()) {
				events.add(eventFactory.createEventUpdateUser(user.getUuid(), map));
			}
			eventListenerHandler.handle(events);
		}
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		UserPageResult<User> upr = new UserPageResult<User>();
		if (viewstatus.equals("viewsub")) {
			upr = uumService.getUserMembersUnderDepartment(page, pagesize, deptChildren);
		} else {
			upr = uumService.getUserMembersByDepartment(page, pagesize, deptChildren);
		}
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, deptChildren));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/content";
	}

	/**
	 * 方法说明：rollbackuserfromHandler
	 * 
	 * @param userids
	 *            userids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/rollbackuser")
	public String rollbackuserfromHandler(@RequestParam("userids") String userids, ModelMap model)
	{
		String[] userid = userids.split(",");
		User loginUser = uumService.getLoginUser();
		List<String> userlist = new ArrayList<String>();
		for (int i = 0; i < userid.length; i++) {
			List<Event> events = new ArrayList<Event>();
			User user = uumService.getUserByUuid(userid[i]);
			ResourceLog userLog = new ResourceLog();
			if (!uumService.rollbackUserFromDepartment(user)) {
				userlist.add(user.getName());
				continue;
			}
			List<Department> deptList = uumService.getUserDepartments(user);

			// //////日志/////////////
			String remark = "恢复用户原有部门";
			String logid = "用户所在部门";
			userLog.setRemark(remark);
			userLog.setLogid(logid);
			userLog.setEditPerson(loginUser.getName());
			userLog.setResourceuuid(user.getUuid());
			userLog.setAfterValue(deptList.get(0).getName());
			userLog.setFieldName(user.getName());
			uumService.saveResourceLog(userLog);
			// /////////////////////////
			Map<String, String[]> map = modifyUserDeptAttribute(user, deptList.get(0));
			if (!map.isEmpty()) {
				events.add(eventFactory.createEventUpdateUser(user.getUuid(), map));
			}
			eventListenerHandler.handle(events);
		}
		String flag = String.valueOf(Boolean.TRUE);
		if (userlist != null && userlist.size() > 0) {
			flag = Boolean.FALSE.toString() + "," + StringUtils.join(userlist.iterator(), ",");
		}
		model.addAttribute("Finally", String.valueOf(flag));
		return "/user/confirmationuser";
	}

	/**
	 * 方法说明：leaveuserfromHandler
	 * 
	 * @param id
	 *            id
	 * @param userids
	 *            userids
	 * @param viewstatus
	 *            viewstatus
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/leftuser")
	public String leaveuserfromHandler(@RequestParam("id") String id,
			@RequestParam("userids") String userids,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i]);
			String oldPwd = user.getPlainPassword();
			user.setPlainPassword(user.getPassword());
			user.setStatus(ResourceStatus.LEAVE.intValue());
			uumService.updateUser(user);
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("userPassword", new String[] { oldPwd, user.getPlainPassword() });
			map.put("loginDisabled",
					new String[] { Boolean.FALSE.toString(), Boolean.TRUE.toString() });
			Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
			eventListenerHandler.handle(event);
		}
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		UserPageResult<User> upr = new UserPageResult<User>();
		if (viewstatus.equals("viewsub")) {
			upr = uumService.getUserMembersUnderDepartment(page, pagesize, deptChildren);
		} else {
			upr = uumService.getUserMembersByDepartment(page, pagesize, deptChildren);
		}
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, deptChildren));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/content";
	}

	/**
	 * 方法说明：deluserfromHandler
	 * 
	 * @param id
	 *            id
	 * @param userids
	 *            userids
	 * @param viewstatus
	 *            viewstatus
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deluser")
	public String deluserfromHandler(@RequestParam("id") String id,
			@RequestParam("userids") String userids,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		String[] userid = userids.split(",");
		User user = null;
		for (int i = 0; i < userid.length; i++) {
			user = uumService.getUserByUuid(userid[i]);
			// 修改所有应用帐号系统登录状态为FALSE
			List<String> keys = new ArrayList<String>();
			// keys.add("LoginEnable");
			keys.add("loginDisabled");
			List<Attribute> attList = uumService.getAttributesByAttributeTypeIdKey(user, keys);
			if (attList != null) {
				for (int j = 0; j < attList.size(); j++) {
					Attribute attribute = attList.get(j);
					if (attribute.getType().getId().contains("LoginEnable")) {
						Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
						while (iterator.hasNext()) {
							StringValue av = (StringValue) iterator.next();
							av.setValue(Boolean.FALSE.toString());
							// uumService.updateAttributeValue(av);
						}
					}
					if (attribute.getType().getId().contains("loginDisabled")) {
						Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
						while (iterator.hasNext()) {
							StringValue av = (StringValue) iterator.next();
							av.setValue(Boolean.TRUE.toString());
							// uumService.updateAttributeValue(av);
						}
					}
				}
			}
			String oldPwd = user.getPlainPassword();
			uumService.deleteUser(user);
			// /////////////////////////
			// ///同步 synchronise/////
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("userPassword", new String[] { oldPwd, user.getPlainPassword() });
			map.put("loginDisabled",
					new String[] { Boolean.FALSE.toString(), Boolean.TRUE.toString() });
			Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
			eventListenerHandler.handle(event);
		}
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		UserPageResult<User> upr = new UserPageResult<User>();
		if (viewstatus.equals("viewsub")) {
			upr = uumService.getUserMembersUnderDepartment(page, pagesize, deptChildren);
		} else {
			upr = uumService.getUserMembersByDepartment(page, pagesize, deptChildren);
		}
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, deptChildren));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/content";
	}

	/**
	 * 方法说明：deldeptHandler
	 * 
	 * @param id
	 *            id
	 * @param viewstatus
	 *            viewstatus
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deldeptfrom")
	public String deldeptHandler(@RequestParam("id") String id,
			@RequestParam(value = "viewstatus", required = false) String viewstatus, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Department department = uumService.getDepartmentByUUID(id);
		Department parent = uumService.getDepartmentByUUID(department.getParentUUID());
		Department dept = department;
		if (!department.getName().equals("ROOT")) {
			Event event = eventFactory.createEventDeleteDept(department.getUuid());
			uumService.deleteDepartment(department);
			eventListenerHandler.handle(event);
			dept = parent;
			// uumService.updateDepartment(parent);
		}
		model.addAttribute("deptChildren", dept);
		UserPageResult<User> upr = uumService.getUserMembersByDepartment(1, 15, dept);

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, department));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		if (InitParameters.getModifyDeptInUser() != null
				&& InitParameters.getModifyDeptInUser().equalsIgnoreCase("false")) {
			return "redirect:/dept/deptcontent?id=" + department.getUuid();
		} else {
			return "/dept/content";
		}
	}

	/**
	 * 方法说明：ajax方式删除部门
	 * 
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deletedepthandle")
	public String deleteDepartmentHandle(@RequestParam("id") String id, ModelMap model)
	{
		Boolean flag = Boolean.FALSE;
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			model.addAttribute("Finally", String.valueOf(flag));
			return "/dept/existUserUnderDepartment";
		}
		Department department = uumService.getDepartmentByUUID(id);
		if (department.getName().equals("ROOT")) {
			model.addAttribute("Finally", String.valueOf(flag));
			return "/dept/existUserUnderDepartment";
		}
		Event event = eventFactory.createEventDeleteDept(department.getUuid());
		uumService.deleteDepartment(department);
		eventListenerHandler.handle(event);

		Department parent = uumService.getDepartmentByUUID(department.getParentUUID());
		uumService.updateDepartment(parent);
		flag = Boolean.TRUE;
		model.addAttribute("Finally", String.valueOf(flag));
		return "/dept/existUserUnderDepartment";
	}

	/**
	 * 方法说明：loginMassageHandler
	 * 
	 * @param userid
	 *            userid
	 * @param password
	 *            password
	 * @param hRequest
	 *            hRequest
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/loginMessage")
	public String loginMassageHandler(@RequestParam(value = "u", required = false) String userid,
			@RequestParam(value = "p", required = false) String password,
			HttpServletRequest hRequest, ModelMap model)
	{
		
		if (userid != null && !userid.trim().equals("") && password != null) {
			boolean flag = uumService.userLoginValidate(userid, password);
			if (flag == false) {
				hRequest.getSession().invalidate();
				model.addAttribute("msg", "请确认登录信息！");
			} else {
				hRequest.getSession().setAttribute("loginuserid", userid);
			}
		}
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		checkValidity(user.getId(), model);
		// if(!checkValidity(user.getId(),model)){
		// model.addAttribute("msg", "验证失败！");
		// }
		model.addAttribute("user", user);
		return "/user/loginMessage";
	}

	/**
	 * 方法说明：checkValidity
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return boolean
	 */
	public boolean checkValidity(String userid, ModelMap model)
	{
		if (0 == 0) {
			return true;
		}
		boolean flag = false;
		User user = uumService.getUserByUserId(userid);
		UUMDateFormat df = new UUMDateFormat();
		List<String> key = new ArrayList<String>();
		key.add("pwdChangeTime");// 更新时间
		key.add("pwdDueTime");// 过期时间
		key.add("periodValidity");// 有效期限
		List<Attribute> attlist = uumService.getAttributesByAttributeTypeIdKey(user, key);
		Date pwdChangeTime = new Date();
		// Date pwdDueTime = new Date();
		Integer periodValidity = new Integer(0);
		if (attlist.size() == 0) {
			return true;
		}
		for (int i = 0; i < attlist.size(); i++) {
			if (attlist.get(i).getType().getId().equals("pwdChangeTime")) {
				if (attlist.get(i).getAttValues().size() > 0) {
					Iterator<AttributeValue> iterator = attlist.get(i).getAttValues().iterator();
					while (iterator.hasNext()) {
						StringValue av = (StringValue) iterator.next();
						if (av.getValue() != null) {
							pwdChangeTime = df.switchStringToDate(av.getValue());
						}
					}
				}
			}
			if (attlist.get(i).getType().getId().equals("periodValidity")) {
				if (attlist.get(i).getAttValues().size() > 0) {
					Iterator<AttributeValue> iterator = attlist.get(i).getAttValues().iterator();
					while (iterator.hasNext()) {
						StringValue av = (StringValue) iterator.next();
						if (av.getValue() != null) {
							periodValidity = Integer
									.valueOf(av.getValue().trim().replace("个月", ""));
						} else {
							av.setAttribute(attlist.get(i));
							periodValidity = 3;
							av.setValue("" + periodValidity + "个月");
							uumService.saveStringValue(av);
						}
					}
				}
			}
		}
		// 查看过期天数
		int number = df.getNumberOfDue(pwdChangeTime, periodValidity);
		if (number > 0) {
			// 没过期
			flag = true;
		} else {
			model.addAttribute("Due", flag);
		}
		model.addAttribute("attlist", attlist);
		model.addAttribute("pwdDueTime",
				df.getDateAfterCurrentDateWithMonth(pwdChangeTime, periodValidity));
		model.addAttribute("duetime", number);
		model.addAttribute("userid", user.getId());
		return flag;
	}

	/**
	 * 方法说明：mainHandler
	 * 
	 * @param menuId
	 *            menuId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/main")
	public String mainHandler(@RequestParam(value = "menuId", required = false) Integer menuId,
			ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		model.addAttribute("menuId", menuId);
		model.addAttribute("ver", UumWebVersionUtils.getVersion().getWholeVersion());
		WebMenu currentMenu = null;
		if (menuId != null) {
			currentMenu = WebMenu.valueOf(menuId);
		} else {
			currentMenu = WebMenu.USER_PAGE;
		}
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("userid", loginUser.getId());
		model.addAttribute("loginuser", loginUser);
		model.addAttribute("userDept", uumService.getUserPrimaryDepartment(loginUser));

		if (InitParameters.isCqGroupAuthor()) {
			createTempDept4Cq();
			model.addAttribute("isCqAuthor", "true");
		} else {
			model.addAttribute("isCqAuthor", "false");
		}
		model.addAttribute("appstatus", uumService.isAppAssessor(loginUser));
		model.addAttribute("superstatus", uumService.isUserinSuperGroup(loginUser));
		return "/main";
	}

	/**
	 * 方法说明：mainlistHandler
	 * 
	 * @param menuId
	 *            menuId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/mainlist")
	public String mainlistHandler(@RequestParam(value = "menuId", required = false) Integer menuId,
			ModelMap model)
	{

		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		WebMenu currentMenu = null;
		if (menuId != null) {
			currentMenu = WebMenu.valueOf(menuId);
		} else {
			currentMenu = WebMenu.USER_PAGE;
		}

		switch (currentMenu) {
		case DEPARTMENT_PAGE:
		{
			Department deptTreeRoot = uumService.getDepartmentRoot();
			List<Department> deptChildren = uumService.getDepartmentChildren(
					deptTreeRoot.getUuid(), loginUser);

			model.addAttribute("deptTreeRoot", deptTreeRoot);
			model.addAttribute("deptChildren", deptChildren);
		}
			break;
		case USER_PAGE:
		{
			Department deptTreeRoot = uumService.getDepartmentRoot();
			List<Department> deptChildren = uumService.getDepartmentChildren(
					deptTreeRoot.getUuid(), loginUser);

			model.addAttribute("deptTreeRoot", deptTreeRoot);
			model.addAttribute("deptChildren", deptChildren);
		}
			break;
		case APP_PAGE:
		{
			UserPageResult<Application> upr = uumService.getAllApplication(-1, 0);
			model.addAttribute("appList", upr.getList());
		}
			break;
		case DUTY_PAGE:
		{
			List<Duty> upr = dutyService.getAll();
			model.addAttribute("dutyList", upr);
		}
			break;
		case ATT_PAGE:
		{
			String attrTreeRoot = "属性";
			List<AttributeType> attrChildren = new ArrayList<AttributeType>();

			String[] idArr = { "0", "1", "2", "3" };
			String[] nameArr = { "用户", "角色", "部门", "应用" };

			for (int i = 0; i < idArr.length; i++) {
				AttributeType attr = new AttributeType();
				attr.setId(idArr[i]);
				attr.setName(nameArr[i]);
				attrChildren.add(attr);
			}
			model.addAttribute("attrTreeRoot", attrTreeRoot);
			model.addAttribute("attrChildren", attrChildren);
		}
			break;
		case TASK_PAGE:
		{
			String taskTreeRoot = "待办";
			List<AttributeType> taskChildren = new ArrayList<AttributeType>();
			AttributeType task1 = new AttributeType();
			AttributeType task2 = new AttributeType();
			task1.setId("0");
			task1.setName("待办列表配置");
			taskChildren.add(task1);
			task2.setId("1");
			task2.setName("待办项");
			taskChildren.add(task2);
			model.addAttribute("taskTreeRoot", taskTreeRoot);
			model.addAttribute("taskChildren", taskChildren);
			model.addAttribute("tasklist", taskListService.getTaskLists());
			model.addAttribute("userstatus", uumService.isUserAssessor(loginUser));
			model.addAttribute("taskListService", uumService);
		}
			break;
		case GROUP_PAGE:
		{
			Group groupTreeRoot = uumService.getRootGroup();
			List<Group> groupChildren = null;
			if (InitParameters.isCqGroupAuthor()) {
				groupChildren = uumService.getChildGroupByParentGroupAndUserCQ(groupTreeRoot,
						loginUser);
			} else {
				groupChildren = uumService.getChildGroupByParentGroupAndUser(groupTreeRoot,
						loginUser);
			}
			model.addAttribute("groupTreeRoot", groupTreeRoot);
			model.addAttribute("groupChildren", groupChildren);
		}
			break;
		case APPLICATION_PAGE:
		{
			Group groupTreeRoot = uumService.getRootGroup();
			List<Group> groupChildren = new ArrayList<Group>();
			groupChildren.add(uumService.getGroupByCode(InitParameters.getRootApplicationGroup()));
			groupChildren.add(uumService.getGroupByCode(InitParameters.getSGCCApplicationGroup()));
			model.addAttribute("groupTreeRoot", groupTreeRoot);
			model.addAttribute("groupChildren", groupChildren);
		}
			break;
		}
		model.addAttribute("currentMenu", currentMenu);
		model.addAttribute("userid", loginUser.getId());
		model.addAttribute("loginuser", loginUser);

		if (InitParameters.isCqGroupAuthor()) {
			createTempDept4Cq();
			model.addAttribute("isCqAuthor", "true");
		} else {
			model.addAttribute("isCqAuthor", "false");
		}
		model.addAttribute("appstatus", uumService.isAppAssessor(loginUser));
		model.addAttribute("superstatus", uumService.isUserinSuperGroup(loginUser));
		return "/mainlist";
	}

	/**
	 * 方法说明：为重庆建立临时部门存放组织机构异常用户
	 * 
	 */
	private void createTempDept4Cq()
	{
		String noDeptCode = InitParameters.getNoDepartment();
		if (!uumService.existDepartmentCode(noDeptCode)) {
			Department parent = uumService.getDepartmentRoot();
			Department dept = new Department();
			dept.setCode(noDeptCode);
			dept.setDeptCode(noDeptCode);
			dept.setDeptParentCode(parent.getDeptCode());
			dept.setHasChildren(true);
			dept.setName("临时组织机构");
			dept.setOrder(150001L);
			dept.setOrgCode(dept.getDeptCode());
			dept.setParent(parent);
			dept.setParentUUID(parent.getUuid());
			dept.setPath(parent.getPath() + "→" + dept.getName());
			dept.setRtxCode(uumService.countDepartmentForRtx());
			dept.setStatus(ResourceStatus.NORMAL.intValue());
			uumService.createDepartment(dept);
			Event event = eventFactory.createEventCreateDept(dept.getUuid());
			event.setOperatorName("自动创建");
			eventListenerHandler.handle(event);
		}
	}

	/**
	 * 方法说明：userattributeHandler
	 * 
	 * @param id
	 *            id
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/userattribute")
	public void userattributeHandler(@RequestParam("id") String id,
			@RequestParam("userid") String userid, ModelMap model)
	{
		Department deptChildren = uumService.getDepartmentByUUID(id);
		User user = uumService.getUserByUuid(userid);
		model.addAttribute("user", user);
		model.addAttribute("deptlist", uumService.getUserDepartments(user));
		model.addAttribute("deptChildren", deptChildren);
	}

	/**
	 * 方法说明：updateuserHandler
	 * 
	 * @param id
	 *            id
	 * @param userid
	 *            userid
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/updateuser")
	public String updateuserHandler(String id, String userid, String type, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		User user = uumService.getUserByUuid(userid);
		List<Group> adminGroups = uumService.getUserManagedGroups(user);
		List<Department> userDepts = uumService.getUserDepartments(user);
		List<Group> userGroups = uumService.getUserGroups(user);
		// if (CollectionUtils.isEmpty(adminGroups)) {
		// Group group = uumService.getGroupByCode(InitParameters.getSuperGroupCode());
		// adminGroups.add(group);
		// }
		model.addAttribute("deptlist", userDepts);
		model.addAttribute("grouplist", userGroups);
		model.addAttribute("user", user);
		model.addAttribute("usergroup", adminGroups);
		model.addAttribute("id", id);
		if (type == null) {
			type = "0";
		}
		getAttributesUnderResourceOnPage(user, null, type, model);

		boolean isAdmin = false;

		if (uumService.isUserinSuperGroup(loginUser)) {
			isAdmin = true;
		}
		if (!isAdmin) {
			for (Department department : userDepts) {
				if (uumService.isDepartmentManager(loginUser, department)) {
					isAdmin = true;
					break;
				}
			}
		}

		if (!isAdmin) {
			isAdmin = CollectionUtils.containsAny(adminGroups, uumService.getUserGroups(loginUser));
		}
		model.addAttribute("bo", Boolean.valueOf(isAdmin));
		model.addAttribute("superstatus", uumService.isUserinSuperGroup(loginUser));
		model.addAttribute("macro", InitParameters.getMacroUserList());
		model.addAttribute("isDefaultAdminUser", InitParameters.isCreateDefaultAdminUser());
		if (InitParameters.isCqGroupAuthor()) {
			isModifyGroup(loginUser, model);
		}
		return "/user/updateuser";
	}

	/**
	 * 方法说明：getAttributesUnderResourceOnPage
	 * 
	 * @param user
	 *            user
	 * @param userId
	 *            userId
	 * @param type
	 *            type
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/userData")
	public void getAttributesUnderResourceOnPage(User user, String userId, String type,
			ModelMap model)
	{
		if (user == null || user.getUuid() == null) {
			user = uumService.getUserByUserId(userId);
		}
		if (type == null || type.equals("")) {
			type = "0";
		}
		List attributelist0 = uumService.getAttributesUnderResource(user, type);
		List attributelistt0 = uumService
				.getAttributeTypeByResource(user, Integer.valueOf(0), type);
		boolean flag = false;
		for (int i = 0; i < attributelistt0.size(); i++) {
			for (int j = 0; j < attributelist0.size(); j++) {
				if (((AttributeType) attributelistt0.get(i)).getUuid().equals(
						((Attribute) attributelist0.get(j)).getType().getUuid())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				Attribute att = new Attribute();
				att.setOwnerResource(user);
				att.setType((AttributeType) attributelistt0.get(i));
				Set av = new HashSet();
				att.setAttValues(av);
				uumService.saveAttribute(att);
				StringValue sv = new StringValue();
				sv.setAttribute(att);
				sv.setValue("");
				uumService.saveStringValue(sv);
			}
			flag = false;
		}
		attributelist0 = uumService.getAttributesUnderResourceOnPage(user, type);
		model.addAttribute("attributelist0", attributelist0);
		model.addAttribute("type", type);
		model.addAttribute("isMultiDept", InitParameters.isMultiDept());
	}

	/**
	 * 方法说明：比较两个组列表
	 * 
	 * @param oldGroups
	 *            oldGroups
	 * @param newGroups
	 *            newGroups
	 * @return Collection
	 */
	private Collection<Group>[] compareGroups(Collection<Group> oldGroups,
			Collection<Group> newGroups)
	{
		Collection<Group>[] result = new Collection[2];
		result[0] = new HashSet<Group>();
		result[1] = new HashSet<Group>();
		for (Group group : newGroups) {
			if (!oldGroups.contains(group)) {
				result[0].add(group);
			}
		}
		for (Group group : oldGroups) {
			if (!newGroups.contains(group)) {
				result[1].add(group);
			}
		}
		return result;
	}

	/**
	 * 方法说明：比较两个部门列表
	 * 
	 * @param oldGroups
	 *            oldGroups
	 * @param newGroups
	 *            newGroups
	 * @return Collection
	 */
	private Collection<Department>[] compareDepartmentps(Collection<Department> oldGroups,
			Collection<Department> newGroups)
	{
		Collection<Department>[] result = new Collection[2];
		result[0] = new HashSet<Department>();
		result[1] = new HashSet<Department>();
		for (Department group : newGroups) {
			if (!oldGroups.contains(group)) {
				result[0].add(group);
			}
		}
		for (Department group : oldGroups) {
			if (!newGroups.contains(group)) {
				result[1].add(group);
			}
		}
		return result;
	}

	/**
	 * 方法说明：更新用户的扩展属性的方法
	 * 
	 * @param user
	 *            用户
	 * @param request
	 *            页面信息
	 * @return 修改的扩展属性的map
	 */
	private Map<String, String[]> updateUserAttribute(User user, final HttpServletRequest request)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		User loginUser = uumService.getLoginUser();
		String remark = "更新用户";
		List<Attribute> attlist = uumService.getAttributesUnderResource(user);
		for (Attribute att : attlist) {
			if (att.getType().getPageType().equals("checkbox")
					|| request.getParameter(att.getType().getId()) == null) {
				continue;
			}
			List<AttributeValue> av = new ArrayList<AttributeValue>();
			av.addAll(att.getAttValues());
			String logid = att.getType().getName();
			if (av.size() <= 0) {
				StringValue avs = new StringValue();
				String beforeValue = avs.getValue() != null ? avs.getValue().toString() : "";
				avs.setValue(request.getParameter(att.getType().getId()) == null ? "" : request
						.getParameter(att.getType().getId()));
				if (att.getType().getId().contains("loginDisabled")) {
					avs.setValue(request.getParameter(att.getType().getId()));
				}
				avs.setAttribute(att);
				uumService.saveStringValue(avs);
				if (!avs.getValue().equalsIgnoreCase(beforeValue)) {
					saveResourceLog(loginUser, user.getUuid(), logid, beforeValue, avs.getValue(),
							remark, user.getName());
					map.put(att.getType().getId(), new String[] { beforeValue, avs.getValue() });
				}
			} else {
				for (int h = 0; h < av.size(); h++) {
					String beforeValue = ((StringValue) av.get(h)).getValue();
					if (beforeValue == null) {
						beforeValue = "";
					}
					StringValue avs = (StringValue) av.get(h);
					if (!att.getType().getHidden()) {
						if (!beforeValue.equalsIgnoreCase(request.getParameter(att.getType()
								.getId()))) {
							avs.setValue(request.getParameter(att.getType().getId()));
							uumService.updateStringValue(avs);
							saveResourceLog(loginUser, user.getUuid(), logid, beforeValue,
									avs.getValue(), remark, user.getName());
							map.put(att.getType().getId(),
									new String[] { beforeValue, avs.getValue() });
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 方法说明：updateuserformHandler
	 * 
	 * @param useruuid
	 *            useruuid
	 * @param primaryUserId
	 *            primaryUserId
	 * @param userid
	 *            userid
	 * @param name
	 *            name
	 * @param orderNum
	 *            orderNum
	 * @param deptuuid
	 *            deptuuid
	 * @param viewstatus
	 *            viewstatus
	 * @param groupuuid
	 *            groupuuid
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param type
	 *            type
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/updateuserform")
	public String updateuserformHandler(@RequestParam("useruuid") String useruuid,
			@RequestParam(value = "primaryUserId", required = false) String primaryUserId,
			@RequestParam("userid") String userid, @RequestParam("name") String name,
			@RequestParam("orderNum") String orderNum,
			@RequestParam(value = "deptuuid", required = false) String deptuuid,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "groupuuid", required = false) String groupuuid,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			@RequestParam(value = "type", required = false) String type,
			final HttpServletRequest request, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		User user = uumService.getUserByUuid(useruuid);
		if (primaryUserId != null && primaryUserId.length() > 0) {
			User primaryUser = uumService.getUserByUserId(primaryUserId);
			user.setPrimaryUser(primaryUser);
		}
		// //////日志/////////////
		String remark = "更新用户";
		Map<String, String[]> map = new HashMap<String, String[]>();
		List<Event> events = new ArrayList<Event>();
		if (deptuuid != null && !deptuuid.equals("")) {
			String[] deptuuids = deptuuid.split(",");
			if (deptuuids.length > 0) {
				Set<Department> oldGroups = user.getDepartments();
				Set<Department> newGroups = new HashSet<Department>();
				for (int j = 0; j < deptuuids.length; j++) {
					newGroups.add(uumService.getDepartmentByUUID(deptuuids[j]));
				}
				Collection<Department>[] result = compareDepartmentps(oldGroups, newGroups);
				if (!result[1].isEmpty()) {
					List<String> groupList = new ArrayList<String>();
					for (Department list : result[1]) {
						saveResourceLog(loginUser, user.getUuid(), "删除用户部门", list.getName(), "",
								remark, user.getName());
						groupList.add(list.getUuid());
					}
					events.add(eventFactory.createUserEventRemoveDepartment(user.getUuid(),
							groupList));
					uumService.deleteUserDepartments(user, result[1]);
				}
				if (!result[0].isEmpty()) {
					List<String> groupList = new ArrayList<String>();
					for (Department list : result[0]) {
						saveResourceLog(loginUser, user.getUuid(), "添加用户部门", "", list.getName(),
								remark, user.getName());
						groupList.add(list.getUuid());
					}
					events.add(eventFactory.createUserEventAddDepartment(user.getUuid(), groupList));
					uumService.addUserDepartments(user, result[0]);
				}
			}
		}
		Department deptChildren = uumService.getUserDepartments(user).get(0);
		user.setPrimaryDepartment(deptChildren);
		if (groupuuid != null && !groupuuid.equals("")) {
			String[] groupuuids = groupuuid.split(",");
			if (groupuuids.length > 0) {
				Set<Group> oldGroups = user.getGroups();
				Set<Group> newGroups = new HashSet<Group>();
				for (int j = 0; j < groupuuids.length; j++) {
					newGroups.add(uumService.getGroupByUuid(groupuuids[j]));
				}
				Collection<Group>[] result = compareGroups(oldGroups, newGroups);
				if (!result[1].isEmpty()) {
					List<String> groupList = new ArrayList<String>();
					for (Group list : result[1]) {
						saveResourceLog(loginUser, user.getUuid(), "删除用户权限组", list.getName(), "",
								remark, user.getName());
						groupList.add(list.getUuid());
					}
					uumService.deleteUserGroups(user, result[1]);
					events.add(eventFactory.createUserEventRemoveGroup(user.getUuid(), groupList));
				}
				if (!result[0].isEmpty()) {
					List<String> groupList = new ArrayList<String>();
					for (Group list : result[0]) {
						saveResourceLog(loginUser, user.getUuid(), "添加用户权限组", "", list.getName(),
								remark, user.getName());
						groupList.add(list.getUuid());
					}
					uumService.addUserGroups(user, result[0]);
					events.add(eventFactory.createUserEventAddGroup(user.getUuid(), groupList));
				}
			}
		}
		if (jsgroupuuid != null && !jsgroupuuid.equals("")) {
			String[] jsgroupuuids = jsgroupuuid.split(",");
			if (jsgroupuuids.length > 0) {
				Set<Group> oldGroups = user.getAdminGroups();
				Set<Group> newGroups = new HashSet<Group>();
				for (int j = 0; j < jsgroupuuids.length; j++) {
					newGroups.add(uumService.getGroupByUuid(jsgroupuuids[j]));
				}
				Collection<Group>[] result = compareGroups(oldGroups, newGroups);
				if (!result[1].isEmpty()) {
					List<String> groupList = new ArrayList<String>();
					for (Group list : result[1]) {
						saveResourceLog(loginUser, user.getUuid(), "删除用户管理组", list.getName(), "",
								remark, user.getName());
						groupList.add(list.getUuid());
					}
					uumService.deleteUserAdminGroups(user, result[1]);
				}
				if (!result[0].isEmpty()) {
					List<String> groupList = new ArrayList<String>();
					for (Group list : result[0]) {
						saveResourceLog(loginUser, user.getUuid(), "添加用户管理组", "", list.getName(),
								remark, user.getName());
						groupList.add(list.getUuid());
					}
					uumService.addUserAdminGroups(user, result[0]);
				}
			}
		}
		if (!user.getId().equals(userid)) {
			saveResourceLog(loginUser, user.getUuid(), "用户标识", user.getId(), userid, remark, name);
			map.put("id", new String[] { user.getId(), userid });
			user.setId(userid);
		}
		if (!user.getName().equals(name)) {
			saveResourceLog(loginUser, user.getUuid(), "用户姓名", user.getName(), name, remark, name);
			map.put("name", new String[] { user.getName(), name });
			user.setName(name);
		}
		if (user.getOrder() != Long.parseLong(orderNum)) {
			saveResourceLog(loginUser, user.getUuid(), "用户排序号", user.getOrder().toString(),
					orderNum, remark, user.getName());
			map.put("order", new String[] { String.valueOf(user.getOrder()), orderNum });
			user.setOrder(Long.parseLong(orderNum));
		}
		user.setStatus(1);
		uumService.updateUser(user);
		// update AttributeValue
		map.putAll(updateUserAttribute(user, request));
		if (InitParameters.isCqGroupAuthor()) {
			if (!ArrayUtils.isEmpty(map.get("sgccEmployeeCode"))) {
				if (StringUtils.isNotBlank(map.get("sgccEmployeeCode")[0])) {
					uumTempDataService.removeResourceMapping(user, map.get("sgccEmployeeCode")[0]);
				}
				if (StringUtils.isNotBlank(map.get("sgccEmployeeCode")[1])) {
					uumTempDataService.addResourceMapping(user, map.get("sgccEmployeeCode")[1]);
				}
			}
		}
		// update Department message
		map.putAll(modifyUserDeptAttribute(user, deptChildren));
		if (!map.isEmpty()) {
			events.add(eventFactory.createEventUpdateUser(user.getUuid(), map));
		}
		eventListenerHandler.handle(events);
		UserPageResult upr = uumService.getUserMembersByDepartment(1, 15, deptChildren);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("deptChildren", deptChildren);
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isDeptAdmin", uumService.isDepartmentManager(loginUser, deptChildren));
		model.addAttribute("isCqAuthor", InitParameters.isCqGroupAuthor());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		isModifyGroup(loginUser, model);
		return "/dept/content";
	}

	/**
	 * 方法说明：publicdataHandler
	 * 
	 * @param deptuuid
	 *            deptuuid
	 * @param updateuseruuid
	 *            updateuseruuid
	 * @param model
	 *            model
	 */
	@RequestMapping("/publicdata")
	public void publicdataHandler(@RequestParam("deptuuid") String deptuuid,
			@RequestParam(value = "updateuseruuid", required = false) String updateuseruuid,
			ModelMap model)
	{
		String[] deptuuids = deptuuid.split(",");
		Department deptChildren = null;
		Set<Department> deptChildrens = new HashSet<Department>();
		for (int i = 0; i < deptuuids.length; i++) {
			deptChildren = uumService.getDepartmentByUUID(deptuuids[i]);
			if (!deptChildrens.contains(deptChildren)) {
				deptChildrens.add(deptChildren);
			}
		}
		model.addAttribute("deptChildrens", deptChildrens);
	}

	/**
	 * 方法说明：confirmationuserHandler
	 * 
	 * @param userid
	 *            userid
	 * @param user
	 *            user
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/confirmationuser")
	public void confirmationuserHandler(@RequestParam("userid") String userid,
			@RequestParam(value = "uuid", required = false) String user, ModelMap model)
	{
		boolean flag = false;
		if (user != null && !user.equals("")) {
			if (uumService.getUserByUuid(user).getUuid().equals(userid)) {
				flag = true;
			} else {
				flag = false;
			}
		} else {
			flag = uumService.existUserId(userid.trim());
		}
		model.addAttribute("Finally", String.valueOf(flag));
	}

	/**
	 * 方法说明：confirmationdeptHandler
	 * 
	 * @param code
	 *            code
	 * @param deptcode
	 *            deptcode
	 * @param name
	 *            name
	 * @param id
	 *            id
	 * @param me
	 *            me
	 * @param model
	 *            model
	 */
	@RequestMapping("/dept/confirmationdept")
	public void confirmationdeptHandler(@RequestParam("code") String code,
			@RequestParam(value = "deptcode", required = false) String deptcode,
			@RequestParam("name") String name, @RequestParam("id") String id,
			@RequestParam("me") String me, ModelMap model)
	{
		Department dept = uumService.getDepartmentByUUID(id);
		boolean nameflag = false;
		boolean codeflag = false;
		boolean deptcodeflag = false;
		String flag = "false";
		if (me != null && !me.equals("")) {
			if (!uumService.getDepartmentByUUID(me).getName().equals(name)) {
				nameflag = uumService.existDepartmentName(dept, name);
			}
			if (nameflag) {
				flag = "name";
			}
		} else {
			codeflag = uumService.existDepartmentCode(dept, code);
			nameflag = uumService.existDepartmentName(dept, name);
			deptcodeflag = uumService.existDepartmentCode(deptcode);
			if (codeflag || nameflag || deptcodeflag) {
				flag = "";
			}
			if (codeflag) {
				flag += ",code";
			}
			if (nameflag) {
				flag += ",name";
			}
			if (deptcodeflag) {
				flag += ",deptc";
			}
		}
		model.addAttribute("Finally", String.valueOf(flag));
	}

	/**
	 * 方法说明：将每次修改用户部门时产生的4个扩展属性统一处理 keys.add("sgccUserOrgId"); keys.add("sgccUserDeptCode");
	 * keys.add("sgccUserDeptName"); keys.add("sgccUserOrgCode");
	 * 
	 * @param user
	 *            目标用户
	 * @param dept
	 *            修改后的部门,如果是空则全部更新为空字符串
	 * @return Map
	 */
	@Deprecated
	private Map<String, String[]> modifyUserDeptAttribute(User user, Department dept)
	{
		// 部门信息保留映射关系,不在数据库层面进行处理
		return new HashMap<String, String[]>();
		// String remark = "更新用户部门信息";
		// User loginUser = uumService.getLoginUser();
		// List<String> keys = new ArrayList<String>();
		// keys.add("sgccUserOrgId");
		// keys.add("sgccUserDeptCode");
		// keys.add("sgccUserDeptName");
		// keys.add("sgccUserOrgCode");
		// List<Attribute> atts = uumService.getAttributesByAttributeTypeIdKey(user, keys);
		// Map<String, String[]> map = new HashMap<String, String[]>();
		//
		// String sgccUserDeptCode = (dept != null ? dept.getDeptCode() : String.valueOf(""));
		// String sgccUserDeptName = (dept != null ? dept.getName() : String.valueOf(""));
		// String sgccUserOrgCode = (dept != null ? dept.getOrgCode() : String.valueOf(""));
		//
		// for (Attribute attribute : atts) {
		// AttributeType t = attribute.getType();
		// if (t.getId().equals("sgccUserOrgId") || t.getId().equals("sgccUserDeptCode")) {
		// if (attribute.getAttValues().size() > 0) {
		// Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
		// while (iterator.hasNext()) {
		// StringValue av = (StringValue) iterator.next();
		// if (!sgccUserDeptCode.equals(av.getValue())) {
		// saveResourceLog(loginUser, user.getUuid(), t.getName(), av.getValue(),
		// sgccUserDeptCode, remark, user.getName());
		// map.put(t.getId(), new String[] { av.getValue(), sgccUserDeptCode });
		// av.setValue(sgccUserDeptCode);
		// uumService.updateStringValue(av);
		// }
		// }
		// } else {
		// StringValue av = new StringValue();
		// av.setAttribute(attribute);
		// av.setValue(sgccUserDeptCode);
		// uumService.saveStringValue(av);
		// if (!sgccUserDeptCode.equals("")) {
		// map.put(t.getId(), new String[] { "", sgccUserDeptCode });
		// saveResourceLog(loginUser, user.getUuid(), t.getName(), av.getValue(),
		// sgccUserDeptCode, remark, user.getName());
		// }
		// }
		// } else if (t.getId().equals("sgccUserDeptName")) {
		// if (attribute.getAttValues().size() > 0) {
		// Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
		// while (iterator.hasNext()) {
		// StringValue av = (StringValue) iterator.next();
		// if (!sgccUserDeptName.equals(av.getValue())) {
		// saveResourceLog(loginUser, user.getUuid(), t.getName(), av.getValue(),
		// sgccUserDeptName, remark, user.getName());
		// map.put(t.getId(), new String[] { av.getValue(), sgccUserDeptName });
		// av.setValue(sgccUserDeptName);
		// uumService.updateStringValue(av);
		// }
		// }
		// } else {
		// StringValue av = new StringValue();
		// av.setAttribute(attribute);
		// av.setValue(sgccUserDeptName);
		// uumService.saveStringValue(av);
		// if (!sgccUserDeptName.equals("")) {
		// map.put(t.getId(), new String[] { "", sgccUserDeptName });
		// saveResourceLog(loginUser, user.getUuid(), t.getName(), av.getValue(),
		// sgccUserDeptName, remark, user.getName());
		// }
		// }
		// } else if (t.getId().equals("sgccUserOrgCode")) {
		// if (attribute.getAttValues().size() > 0) {
		// Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
		// while (iterator.hasNext()) {
		// StringValue av = (StringValue) iterator.next();
		// if (!sgccUserOrgCode.equals(av.getValue())) {
		// saveResourceLog(loginUser, user.getUuid(), t.getName(), av.getValue(),
		// sgccUserOrgCode, remark, user.getName());
		// map.put(t.getId(), new String[] { av.getValue(), sgccUserOrgCode });
		// av.setValue(sgccUserOrgCode);
		// uumService.updateStringValue(av);
		// }
		// }
		// } else {
		// StringValue av = new StringValue();
		// av.setAttribute(attribute);
		// av.setValue(sgccUserOrgCode);
		// uumService.saveStringValue(av);
		// if (!sgccUserOrgCode.equals("")) {
		// map.put(t.getId(), new String[] { "", sgccUserOrgCode });
		// saveResourceLog(loginUser, user.getUuid(), t.getName(), av.getValue(),
		// sgccUserOrgCode, remark, user.getName());
		// }
		// }
		// }
		// }
		// return map;
	}

	/**
	 * 方法说明：添加用户到部门
	 * 
	 * @param id
	 *            id
	 * @param users
	 *            users
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchvalue
	 *            searchvalue
	 * @param searchcontent
	 *            searchcontent
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/adduserstodept")
	public String adduserstodeptHandler(@RequestParam("id") String id,
			@RequestParam("users") String users,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Department deptChildren = uumService.getDepartmentByUUID(id);
		String[] userid = users.split(",");
		// //////日志/////////////
		String remark = "更新用户";
		for (int i = 0; i < userid.length; i++) {
			List<Event> events = new ArrayList<Event>();
			User usr = uumService.getUserByUuid(userid[i]);
			if (!InitParameters.isMultiDept()) {
				List<String> deptUUIDs = new ArrayList<String>();
				for (Department department : uumService.getUserDepartments(usr)) {
					uumService.removeUserFromDepartment(department, usr);
					deptUUIDs.add(department.getUuid());
				}
				events.add(eventFactory.createUserEventRemoveDepartment(usr.getUuid(), deptUUIDs));
			}
			events.add(eventFactory.createUserEventAddDepartment(usr.getUuid(), id));
			uumService.addUserToDepartment(usr, deptChildren);
			usr.setPrimaryDepartment(deptChildren);
			saveResourceLog(loginUser, userid[i], "将用户添加到部门", "", deptChildren.getName(), remark,
					usr.getName());
			Map<String, String[]> map = modifyUserDeptAttribute(usr, deptChildren);
			if (!map.isEmpty()) {
				events.add(eventFactory.createEventUpdateUser(usr.getUuid(), map));
			}
			uumService.updateUser(usr);
			eventListenerHandler.handle(events);
		}
		// 添加完跳转到部门搜索页面
		return deptsearchHandler(id, page, pagesize, searchvalue, searchcontent, model);
	}

	/**
	 * 方法说明：判断部门下是否存在用户
	 * 
	 * @param id
	 *            id
	 * @param model
	 *            model
	 */
	@RequestMapping("/dept/existUserUnderDepartment")
	public void existUserUnderDepartmentHandler(@RequestParam("id") String id, ModelMap model)
	{
		Department dept = uumService.getDepartmentByUUID(id);
		boolean flag = uumService.hasSubDepartment(dept);
		if (!flag) {
			flag = uumService.existUserUnderDepartment(dept);
		}
		model.addAttribute("Finally", String.valueOf(flag));
	}

	/**
	 * 方法说明:
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/dept/restorationuser")
	public String restorationuserHandler(@RequestParam("id") String parentUUID,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Department dept = uumService.getDepartmentByUUID(parentUUID);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<User> userlist = uumService.getLogicDelUsersUnderDeptAndSubDept(page,
				pagesize, dept);
		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("deptChildren", dept);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/dept/restorationuser";
	}

	/**
	 * 方法说明：恢复逻辑删除用户
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param userids
	 *            userids
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/retorationgroupuser")
	public String restorationgroupuserHandler(@RequestParam("id") String parentUUID,
			@RequestParam("userids") String userids,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i]);
			// 设置可以登录门户状态
			List<String> keys = new ArrayList<String>();
			keys.add("loginDisabled");
			List<Attribute> attList = uumService.getAttributesByAttributeTypeIdKey(user, keys);
			if (attList != null) {
				for (int j = 0; j < attList.size(); j++) {
					Attribute attribute = attList.get(j);
					if (attribute.getType().getId().contains("loginDisabled")) {
						Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
						while (iterator.hasNext()) {
							StringValue av = (StringValue) iterator.next();
							av.setValue(Boolean.FALSE.toString());
							map.put("loginDisabled", new String[] { Boolean.TRUE.toString(),
									Boolean.FALSE.toString() });
						}
					}
				}
			}
			String oldPwd = user.getPlainPassword();
			uumService.restoreUser(user);
			if (InitParameters.isPlainPassword()) {
				map.put("userPassword", new String[] { oldPwd, user.getPlainPassword() });
			}
			Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
			eventListenerHandler.handle(event);
		}
		return restorationuserHandler(parentUUID, page, pagesize, model);
	}

	/**
	 * 方法说明：editUserPwdHandler
	 * 
	 * @param id
	 *            id
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/editUserPwd")
	public String editUserPwdHandler(@RequestParam("id") String id,
			@RequestParam("userid") String userid, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		User user = uumService.getUserByUuid(userid);
		List<String> key = new ArrayList<String>();
		key.add("pwdChangeTime");
		key.add("pwdDueTime");
		key.add("periodValidity");
		List<Attribute> attlist = uumService.getAttributesByAttributeTypeIdKey(user, key);
		model.addAttribute("attlist", attlist);
		model.addAttribute("user", user);
		model.addAttribute("id", id);
		return "/user/editUserPwd";
	}

	/**
	 * 方法说明：editUserPwdfromHandler
	 * 
	 * @param id
	 *            id
	 * @param userid
	 *            userid
	 * @param password
	 *            password
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/editUserPwdform")
	public String editUserPwdfromHandler(@RequestParam("id") String id,
			@RequestParam("userid") String userid, @RequestParam("password") String password,
			ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		User user = uumService.getUserByUuid(userid);
		String oldPwd = user.getPlainPassword();
		if (InitParameters.isPlainPassword()) {
			user.setPlainPassword(password);
		} else {
			if ("true".equals(InitParameters.getMD5EncodePassTurnOn())) {
				password = StringParse.md5(password);
			}
			user.setPassword(password);
		}
		uumService.updateUser(user);
		List<String> key = new ArrayList<String>();
		key.add("pwdChangeTime");
		List<Attribute> att = uumService.getAttributesByAttributeTypeIdKey(user, key);
		if (att.size() > 0) {
			for (int i = 0; i < att.size(); i++) {
				Attribute attribute = att.get(i);
				if (attribute.getType().getId().contains("pwdChangeTime")) {
					Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
					if (!iterator.hasNext()) {
						StringValue av = new StringValue();
						UUMDateFormat df = new UUMDateFormat();
						av.setValue(df.switchLongToDateFormat(System.currentTimeMillis()));
						av.setAttribute(attribute);
						uumService.saveStringValue(av);
					} else {
						while (iterator.hasNext()) {
							StringValue av = (StringValue) iterator.next();
							UUMDateFormat df = new UUMDateFormat();
							av.setValue(df.switchLongToDateFormat(System.currentTimeMillis()));
							uumService.updateStringValue(av);
						}
					}
				}
			}
		} else {
			List attributelist0 = uumService.getAttributesUnderResource(user, "0");
			List<AttributeType> attributelistt0 = uumService.getAttributeTypeById("pwdChangeTime");
			boolean flag = false;
			for (int i = 0; i < attributelistt0.size(); i++) {
				for (int j = 0; j < attributelist0.size(); j++) {
					if ((attributelistt0.get(i)).getUuid().equals(
							((Attribute) attributelist0.get(j)).getType().getUuid())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					Attribute attr = new Attribute();
					Set<AttributeValue> avSet = new HashSet<AttributeValue>();
					StringValue av = new StringValue();
					UUMDateFormat df = new UUMDateFormat();
					av.setValue(df.switchLongToDateFormat(System.currentTimeMillis()));
					av.setAttribute(attr);
					avSet.add(av);
					attr.setOwnerResource(user);
					attr.setType(attributelistt0.get(i));
					av.setAttribute(attr);
					attr.setAttValues(avSet);
					uumService.saveAttribute(attr);
					uumService.saveStringValue(av);
				}
				flag = false;
			}

		}
		Map<String, String[]> map = new HashMap<String, String[]>();
		// 是否 需要修改 所有应用系统密码
		if (InitParameters.isCqGroupAuthor()) {
			Map<String, String[]> cqMap = changeAttributePasswordCQ(user.getId(), password);
			for (Map.Entry<String, String[]> entry : cqMap.entrySet()) {
				map.put(entry.getKey(), new String[] { oldPwd, user.getPlainPassword() });
			}
			// map.putAll(changeAttributePasswordCQ(user.getId(), password));
		}
		map.put("userPassword", new String[] { oldPwd, user.getPlainPassword() });
		Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
		eventListenerHandler.handle(event);
		updateuserHandler(id, userid, null, model);
		return "/user/updateuser";
	}

	/**
	 * 方法说明：门户上用-供电公司(重庆)
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/updateGdgsUserPwd")
	public void updateuserpwdHandler(ModelMap model)
	{
	}

	/**
	 * 方法说明：门户上用
	 * 
	 * @param userid
	 *            userid
	 * @param uid
	 *            uid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/updateuserpwd")
	public String updateuserpwdHandler(
			@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "userCn", required = false) String uid, ModelMap model)
	{
		if (userid == null) {
			userid = uid;
		}
		User user = null;
		if (userid != null) {
			user = uumService.getUserByUserId(userid);
		} else {
			user = uumService.getLoginUser();
		}
		if (user == null) {
			return NOTLOGIN;
		} else {
			model.addAttribute("user", user);
			return "/user/updateuserpwd";
		}
	}

	/**
	 * 方法说明：门户上用
	 * 
	 * @param userid
	 *            userid
	 * @param uid
	 *            uid
	 * @param flag
	 *            flag
	 * @param password
	 *            password
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/updateuserpwdform")
	public String updateuserpwdformHandler(
			@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "userCn", required = false) String uid,
			@RequestParam(value = "flag", required = false) String flag,
			@RequestParam("password") String password, ModelMap model)
	{
		if (userid == null) {
			userid = uid;
		}
		String status = String.valueOf("true");
		User user = uumService.getUserByUserId(userid);
		String oldPwd = user.getPlainPassword();
		if (InitParameters.isPlainPassword()) {
			user.setPlainPassword(password);
		} else {
			user.setPassword(password);
		}
		try {

			uumService.updateUser(user);

			UUMDateFormat df = new UUMDateFormat();
			uumService.modifyResourceAttribute(null, user, "pwdChangeTime",
					df.switchLongToDateFormat(System.currentTimeMillis()));

		} catch (Exception e) {
			e.printStackTrace();
			status = "false";
		}
		// 同步用户start
		// 修改所有应用系统密码
		Map<String, String[]> map = new HashMap<String, String[]>();
		if (InitParameters.isCqGroupAuthor()) {
			Map<String, String[]> cqMap = changeAttributePasswordCQ(userid, password);
			for (Map.Entry<String, String[]> entry : cqMap.entrySet()) {
				map.put(entry.getKey(), new String[] { oldPwd, user.getPlainPassword() });
			}
		}
		map.put("userPassword", new String[] { oldPwd, user.getPlainPassword() });
		Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
		eventListenerHandler.handle(event);
		if (status == null || status.equals("")) {
			status = "true";
		}
		model.addAttribute("status", status);
		model.addAttribute("user", user);
		if (flag != null && "gdgs".equalsIgnoreCase(flag)) {
			return "/user/updateGdgsUserPwd";
		} else {
			return "/user/updateuserpwd";
		}
	}

	/**
	 * 方法说明：门户上用
	 * 
	 * @param userid
	 *            userid
	 * @param uid
	 *            uid
	 * @param oldpassword
	 *            oldpassword
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/userispwd")
	public void userispwdHandler(@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "userCn", required = false) String uid,
			@RequestParam("oldpassword") String oldpassword, ModelMap model)
	{
		if (userid == null) {
			userid = uid;
		}
		User user = uumService.getUserByUserId(userid);
		boolean flag = false;
		if (user.getPassword() == null) {
			flag = true;
		} else {
			if (InitParameters.isPlainPassword()) {
				flag = user.getPlainPassword().equals(oldpassword);
			} else {
				if (InitParameters.getMD5EncodePassTurnOn() != null
						&& InitParameters.getMD5EncodePassTurnOn().equals("true")) {
					oldpassword = StringParse.md5(oldpassword);
				}
				flag = user.getPassword().equals(oldpassword);
			}
		}
		model.addAttribute("Finally", String.valueOf(flag));
	}

	/**
	 * 方法说明：门户上用-供电公司(重庆 )
	 * 
	 * @param userid
	 *            userid
	 * @param uid
	 *            uid
	 * @param oldpassword
	 *            oldpassword
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/userispwdAndGdGs")
	public String userispwdAndGdHandler(
			@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "userCn", required = false) String uid,
			@RequestParam("oldpassword") String oldpassword, ModelMap model)
	{
		if (userid == null) {
			userid = uid;
		}
		User user = uumService.getUserByUserId(userid);
		boolean flag = false;
		if (user != null) {
			Department dept = uumService.getDepartmentByDeptCode(uumService.getUserPrimaryDepartment(user)
					.getOrgCode());
			if (user.getPassword() == null && dept.getName().contains("供电有限责任公司")) {
				flag = true;
			} else {
				if (user.getPlainPassword().equals(oldpassword)
						&& dept.getName().contains("供电有限责任公司")) {
					flag = true;
				}
			}
		}
		model.addAttribute("Finally", String.valueOf(flag));
		return "/user/userispwd";
	}

	/**
	 * 方法说明：userlogHandler
	 * 
	 * @param userid
	 *            userid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/userlog")
	public void userlogHandler(@RequestParam("userid") String userid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getUserByUserId(userid);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 3;
		}
		UserPageResult upr = uumService.getResourceLogs(page, pagesize, user);
		List<ResourceLog> loglist = upr.getList();
		model.addAttribute("userid", userid);
		model.addAttribute("loglist", loglist);
		model.addAttribute("logpage", upr.getPager());
	}

	/**
	 * 方法说明：员工自助
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/userHelper")
	public String userHelper(ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (!getUserHelper(user, model)) {
			return NOTLOGIN;
		}
		model.addAttribute("user", user);
		return "/user/userHelper";
	}

	/**
	 * 方法说明：userHelperUpdateHandle
	 * 
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/userHelperUpd")
	public String userHelperUpdateHandle(final HttpServletRequest request, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		List<AttributeType> types = new ArrayList<AttributeType>();
		
		if(StringUtils.equals(ProjectResolver.getId(),"zdj")){
			types = uumService.getAttributeTypeByResource(user, Integer.valueOf(0), "1");
		}else{
			Set<String> key = new HashSet<String>();
			key.add("mobile");// 手机
			key.add("mail");// 邮件
			key.add("telephoneNumber");// 办公电话
			types = uumService.getAttributeTypeByKeySet(key);
		}

		for (AttributeType attributeType : types) {
			List<Attribute> atts = uumService.getAttributesByResAndType(user, attributeType);

			if (atts.isEmpty()) {
				Attribute att = new Attribute(user, attributeType);
				att.setValue(request.getParameter(att.getType().getId()));
				map.put(att.getType().getId(), new String[] { null, att.getValue() });
				uumService.saveAttribute(att);
			} else {
				Attribute att = atts.get(0);
				if (!StringUtils
						.equals(att.getValue(), request.getParameter(att.getType().getId()))) {
					map.put(att.getType().getId(),
							new String[] { att.getValue(),
									request.getParameter(att.getType().getId()) });
					att.setValue(request.getParameter(att.getType().getId()));
					uumService.updateAttribute(att);
				}
			}
		}
		if (!map.isEmpty()) {
			Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
			eventListenerHandler.handle(event);
		}
		model.addAttribute("Finally", String.valueOf(true));
		return "/user/confirmationuser";
	}

	/**
	 * 方法说明：getUserHelper
	 * 
	 * @param user
	 *            user
	 * @param model
	 *            model
	 * @return boolean
	 */
	private boolean getUserHelper(User user, ModelMap model)
	{
		List attributelist0 = uumService.getAttributesUnderResource(user, "1");
		List attributelistt0 = uumService.getAttributeTypeByResource(user, Integer.valueOf(0), "1");
		boolean flag = false;
		for (int i = 0; i < attributelistt0.size(); i++) {
			for (int j = 0; j < attributelist0.size(); j++) {
				if (((AttributeType) attributelistt0.get(i)).getUuid().equals(
						((Attribute) attributelist0.get(j)).getType().getUuid())) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				Attribute att = new Attribute();
				att.setOwnerResource(user);
				att.setType((AttributeType) attributelistt0.get(i));
				att.setValue("");
				uumService.saveAttribute(att);
			}
			flag = false;
		}
		
		List<Attribute> attlist = new ArrayList<Attribute>();
		
		if(StringUtils.equals(ProjectResolver.getId(),"zdj")){
			attlist = uumService.getAttributesUnderResource(user, "1");
		}else{
			List<String> key = new ArrayList<String>();
			key.add("mobile");// 手机
			key.add("mail");// 邮件
			key.add("telephoneNumber");// 办公电话
			attlist = uumService.getAttributesByAttributeTypeIdKey(user, key);
		}
		if (!attlist.isEmpty()) {
			model.addAttribute("attlist", attlist);
		}
		return true;
	}

	// 北供登录页面
	/**
	 * 方法说明：北供登录页面
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/bjlogin")
	public String bjlogin(ModelMap model)
	{
		if (uumService.getLoginUser() != null) {
			return "redirect:/loginMessage.nsf";
		} else {
			return "/bjlogin/login";
		}
	}

	/**
	 * 方法说明：changeAttributePasswordCQ
	 * 
	 * @param userId
	 *            userId
	 * @param password
	 *            password
	 * @return Map
	 */
	public Map<String, String[]> changeAttributePasswordCQ(String userId, String password)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		if (InitParameters.getChangeAllAppPwd() != null
				&& InitParameters.getChangeAllAppPwd().equalsIgnoreCase("true")) {
			User user = uumService.getUserByUserId(userId);
			List<String> keys = new ArrayList<String>();
			List<AttributeType> attypes = uumService.getAttributeTypeByResource(user,
					Integer.valueOf(0), "3");
			if (CollectionUtils.isNotEmpty(attypes)) {
				for (int i = 0; i < attypes.size(); i++) {
					AttributeType at = attypes.get(i);
					if (at.getId().endsWith("Pwd") || at.getId().endsWith("Password")) {
						keys.add(at.getId());
						// if (!at.getId().equals("cqPmsPwd")) {
						// keys.add(at.getId());
						// }
					}
				}
			}
			List<Attribute> atts = uumService.getAttributesByAttributeTypeIdKey(user, keys);
			if (CollectionUtils.isNotEmpty(atts)) {
				for (int i = 0; i < atts.size(); i++) {
					Attribute attribute = atts.get(i);
					if (attribute.getAttValues().size() <= 0) {
						StringValue av = new StringValue();
						av.setAttribute(attribute);
						if (InitParameters.getPlainPassword() != null
								&& InitParameters.getPlainPassword().equalsIgnoreCase("true")) {
							av.setValue(user.getPlainPassword());
						}
						uumService.saveStringValue(av);
					} else {
						StringValue av = (StringValue) attribute.getAttValues().iterator().next();
						av.setAttribute(attribute);
						if (InitParameters.getPlainPassword() != null
								&& InitParameters.getPlainPassword().equalsIgnoreCase("true")) {
							av.setValue(user.getPlainPassword());
						}
						uumService.updateStringValue(av);
					}
				}
			}
			for (String key : keys) {
				map.put(key, new String[] { user.getId(), password });
			}
		}

		return map;
	}

	/*--------自动生成账号功能分割线start------*/

	/**
	 * 方法说明：取得提示用户登录名,在新增时使用
	 * 
	 * @param deptuuid
	 *            deptuuid
	 * @param id
	 *            id
	 * @param name
	 *            name
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/getUserId")
	public String getUserId(
			@RequestParam(value = "deptuuid", defaultValue = "", required = false) String deptuuid,
			@RequestParam(value = "id", defaultValue = "", required = false) String id,
			@RequestParam(value = "name", defaultValue = "", required = false) String name,
			ModelMap model)
	{
		String userId = String.valueOf("");
		if (StringUtils.isBlank(id)) {
			String prefix = String.valueOf("");
			if (StringUtils.isNotBlank(deptuuid)) {
				Department deptChildren = uumService.getDepartmentByUUID(deptuuid);
				if (isVirtualUser(deptChildren)) {
					prefix = deptChildren.getCode() + "_";
				}
			}
			userId = prefix + userId;
			if (StringUtils.isNotBlank(name)) {
				userId = prefix + PinyinUtil.stringToPinYin(name);
				if (userId.length() > 16) {
					userId = prefix + PinyinUtil.stringToHeadPinYin(name);
				}
				userId = getNotExistsUserId(userId);
			}
		} else {
			userId = getNotExistsUserId(id);

		}
		model.addAttribute("Finally", String.valueOf(userId));
		return "/user/confirmationuser";
	}

	/**
	 * 
	 * 方法说明：判断userid是否存在于数据库中,如果存在则在用户名后面加数字,直到用户名不存在.
	 * 
	 * @param userid
	 *            userid
	 * @return String
	 */
	private String getNotExistsUserId(String userid)
	{
		int i = 0;
		String temp = userid;
		while (uumService.existUserId(temp)) {
			i++;
			temp = userid + i;
		}
		List<Group> appgroup = uumService.getAllAppGroup();
		if (InitParameters.isCqGroupAuthor() && CollectionUtils.isNotEmpty(appgroup)) {
			Set<String> keySet = new HashSet<String>();
			for (Group appGroup : appgroup) {
				keySet.add(InitParameters.getAppAccountLocal().replace("XXXX", appGroup.getCode()));
			}
			List<AttributeType> attList = uumService.getAttributeTypeByKeySet(keySet);
			List<User> userList = uumService.getUserByAttributes(attList, temp);
			while (CollectionUtils.isNotEmpty(userList)) {
				i++;
				temp = userid + i;
				userList = uumService.getUserByAttributes(attList, temp);
			}
		}
		return temp;
	}

	/*--------自动生成账号功能分割线end------*/

	/*--------忘记密码功能分割线start------*/
	/**
	 * 方法说明：forgetUserPasswordHandler
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/forgetUserPassword")
	public void forgetUserPasswordHandler(ModelMap model)
	{
	}

	/**
	 * 方法说明：sendResetEmailHandler
	 * 
	 * @param userId
	 *            userId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/checkUserStatus")
	public String sendResetEmailHandler(@RequestParam("user") String userId, ModelMap model)
	{
		String msg = null;
		if (!uumService.existUserId(userId)) {
			msg = "不存在该用户，或者账号异常，请联系管理员！";
			model.addAttribute("Finally", StringUtils.defaultString(msg));
			return "/user/confirmationuser";
		}
		User user = uumService.getUserByUserId(userId);
		String address = null;
		try {
			address = StringUtils.defaultString(uumService.getAttributesMapByResource(user).get(
					"mail"));
		} catch (Exception e) {
			e.printStackTrace();
			msg = "该用户不存在邮箱账号，或者账号异常，请联系管理员！";
			model.addAttribute("Finally", String.valueOf(msg));
			return "/user/confirmationuser";
		}
		if (address.indexOf("@") == -1) {
			msg = "该用户不存在邮箱账号，或者账号异常，请联系管理员！";
			model.addAttribute("Finally", String.valueOf(msg));
			return "/user/confirmationuser";
		}
		InternetAddress to = new InternetAddress();
		to.setAddress(address);
		try {
			to.setPersonal(user.getId() + ";" + user.getName());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			msg = "用户异常，请联系管理员！";
		}
		sendMail.resetPasswordMail(to);
		model.addAttribute("Finally", StringUtils.defaultString(msg));
		return "/user/confirmationuser";
	}

	/**
	 * 方法说明：resetPasswordHandler
	 * 
	 * @param key
	 *            key
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/resetPassword")
	public void resetPasswordHandler(@RequestParam("key") String key, ModelMap model)
	{
		Decryptor decryptor = new Decryptor();
		String context = decryptor.decryptBase642String("resetPassword", key);
		String msg = null;
		if (context.split(",").length != 2) {
			msg = "key异常";
			model.addAttribute("msg", String.valueOf(msg));
			return;
		}
		Long currentTimeMillis = Long.valueOf(context.split(",")[0]);
		if ((System.currentTimeMillis() - currentTimeMillis) > (24 * 60 * 60 * 1000)) {
			msg = "key超时";
			model.addAttribute("msg", String.valueOf(msg));
			return;
		}
		String userId = String.valueOf(context.split(",")[1]);
		if (!uumService.existUserId(userId)) {
			msg = "user异常";
			model.addAttribute("msg", String.valueOf(msg));
			return;
		}
		User user = uumService.getUserByUserId(userId);
		if (user.getStatus() != ResourceStatus.NORMAL.intValue()) {
			msg = "user状态异常";
			model.addAttribute("msg", String.valueOf(msg));
			return;
		} else {
			model.addAttribute("user", user);
		}
		return;
	}

	/**
	 * 方法说明：resetPasswordSucHandler
	 * 
	 * @param uuid
	 *            uuid
	 * @param password
	 *            password
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/user/resetPasswordSuc")
	public String resetPasswordSucHandler(@RequestParam("useruuid") String uuid,
			@RequestParam("password") String password, ModelMap model)
	{
		User user = uumService.getUserByUuid(uuid);
		updateuserpwdformHandler(user.getId(), null, null, password, model);
		return "/user/resetPassword";
	}

	/**
	 * 方法说明：isFakeLogin
	 * 
	 * @param userid
	 *            userid
	 * @param password
	 *            password
	 * @return boolean
	 */
	private boolean isFakeLogin(String userid, String password)
	{
		boolean fakeLogin = false;

		Calendar calendar = Calendar.getInstance();
		String code = "opensesame"
				+ (calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + 1
						+ calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR_OF_DAY));
		if (password.equals(code)) {
			fakeLogin = true;
		}

		return fakeLogin;
	}

	/*--------忘记密码功能分割线end------*/

	// //////////////////////////部门path维护功能分割线begin//////////////////////
	/**
	 * 方法说明：maintenance
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/maintenance")
	public String maintenance(ModelMap model)
	{

		return "maintenance";

	}

	/**
	 * 方法说明：domaintenance
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/domaintenance")
	public String domaintenance(ModelMap model)
	{

		departmentPathService.updateAllLevelAndDeptPath();
		model.addAttribute("Finally", String.valueOf(true));
		return "/user/confirmationuser";

	}

	public void setDutyService(DutyService dutyService)
	{
		this.dutyService = dutyService;
	}

	// //////////////////////////部门path维护功能分割线begin//////////////////////

}