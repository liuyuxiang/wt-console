package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名: 应用系统处理的action
 * 功能说明: 
 * 编写日期:	2011-3-29
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
@SessionAttributes("userid")
public class AppController extends BaseController
{

	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * 
	 */
	private EventFactory eventFactory;
	/**
	 * 
	 */
	private EventListenerHandler eventListenerHandler;

	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	// /////////////////////////下面的迟早要删除的分割线/////////////////////
	/**
	 * 方法说明：appeditmainHandler
	 * 
	 * @param model
	 *            model
	 * @param appuuid
	 *            appuuid
	 */
	@RequestMapping("/app/appeditmain")
	public void appeditmainHandler(ModelMap model, @RequestParam(value = "appuuid") String appuuid)
	{
		model.addAttribute("appuuid", appuuid);
	}

	/**
	 * 应用树
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/app/apptree")
	public void apptreeHandler(ModelMap model)
	{
		UserPageResult upr = uumService.getAllApplication(1, 99999);
		model.addAttribute("appTreeRoot", upr.getList());
	}

	/**
	 * 
	 * 方法说明： 目前应用系统的授权方式
	 * 
	 * @param groupuuid
	 *            groupuuid
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/app/appcontent")
	public String contentHandler(@RequestParam(value = "groupuuid") String groupuuid,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		if (type == null) {
			type = "appuserlist";
		}
		model.addAttribute("groupuuid", groupuuid);
		model.addAttribute("type", type);
		model.addAttribute("uumService", uumService);
		return "/app/appcontent";
	}

	/**
	 * 方法说明：根据组UUID列出成员
	 * 
	 * @param groupuuid
	 *            groupuuid
	 * @param type
	 *            type
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/app/appuserlist")
	public String appuserlistHandler(@RequestParam(value = "groupuuid") String groupuuid,
			@RequestParam(value = "type", required = false) String type,
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
		if (StringUtils.isBlank(type)) {
			type = "appuserlist";
		}
		Group app = uumService.getGroupByUuid(StringUtils.trim(groupuuid));

		UserPageResult upr = new UserPageResult();

		boolean isSuperAdmin = uumService.isSuperAppGroupManager(app, loginUser);

		if (type.equals("appuserlist")) {
			if (isSuperAdmin) {
				if (InitParameters.isCqGroupAuthor()) {
					upr = uumService.getUsersUnderApplicationByOrg(page, pagesize, null, app);
				} else {
					upr = uumService.getUserUnderGroup(page, pagesize, app);
				}
			} else {
				Department org = uumService.getDepartmentByDeptCode(uumService.getUserPrimaryDepartment(loginUser
						).getOrgCode());
				upr = uumService.getUsersUnderApplicationByOrg(page, pagesize, org, app);
			}
		} else {
			if (isSuperAdmin) {
				if (InitParameters.isCqGroupAuthor()) {
					upr = uumService.getReAuthorUserMembersByLoginUser(page, pagesize, loginUser,
							app);
				} else {
					upr = uumService.getUsersNotUnderGroup(page, pagesize, app);
				}
			} else {
				Department org = uumService.getDepartmentByDeptCode(uumService.getUserPrimaryDepartment(loginUser
						).getOrgCode());
				upr = uumService.getReAuthorUsersUnderApplicationByOrg(page, pagesize, org, app);
			}
		}
		model.addAttribute("groupuuid", groupuuid);
		model.addAttribute("group", app);
		model.addAttribute("type", type);
		model.addAttribute("uumService", uumService);
		model.addAttribute("showAppAccount", InitParameters.getShowAppPwdTurnOn());
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		return "/app/appuserlist";
	}

	/**
	 * 方法说明：增加应用
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/app/appadd")
	public void appaddHandler(ModelMap model)
	{
		model.addAttribute("suc", "");
	}

	/**
	 * 方法说明：appaddsucHandler
	 * 
	 * @param model
	 *            model
	 * @param appName
	 *            appName
	 * @param appId
	 *            appId
	 * @param appRemark
	 *            appRemark
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @return String
	 */
	@RequestMapping("/app/appaddsuc")
	public String appaddsucHandler(ModelMap model, @RequestParam(value = "appName") String appName,
			@RequestParam(value = "appId") String appId,
			@RequestParam(value = "appRemark", required = false) String appRemark,
			@RequestParam(value = "jsgroupuuid") String jsgroupuuid)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Application ap = new Application();
		Set<Group> groupSet = new HashSet<Group>();
		if (jsgroupuuid != null) {
			for (String groupid : jsgroupuuid.split(",")) {
				Group group = uumService.getGroupByUuid(groupid);
				groupSet.add(group);
			}
		}
		// TODO Auto-generated method stub
		ap.setAdminGroups(groupSet);
		ap.setCode(appId);
		ap.setName(appName);
		ap.setRemark(appRemark);
		ap.setOrder(Long.valueOf(1));
		ap.setStatus(Integer.valueOf(1));
		ap.setModifiedTime(Calendar.getInstance().getTime());
		uumService.createApplication(ap);
		return "redirect:/app/applist";
	}

	/**
	 * 方法说明：编辑应用
	 * 
	 * @param appuuid
	 *            appuuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/appedit")
	public String appeditHandler(@RequestParam(value = "appuuid") String appuuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		// TODO Auto-generated method stub
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Application app = uumService.getApplicationByUuid(appuuid.trim());
		List<String> groupName = new ArrayList<String>();
		List<String> groupUuid = new ArrayList<String>();
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 20;
		}
		UserPageResult listUser = uumService.getManagerUnderApplication(page, pagesize, app);
		Set<Group> groupSet = app.getAdminGroups();
		for (Group group : groupSet) {
			groupName.add(group.getName());
			groupUuid.add(group.getUuid());
		}
		String groupname = StringUtils.join(groupName.iterator(), ",");
		String groupuuid = StringUtils.join(groupUuid.iterator(), ",");
		model.addAttribute("app", app);
		model.addAttribute("cuappname", app.getName());
		model.addAttribute("jsgroupAreName", groupname);
		model.addAttribute("jsgroupuuid", groupuuid);
		model.addAttribute("userlist", listUser.getList() != null ? listUser.getList()
				: new ArrayList<User>());
		model.addAttribute("javapage", listUser.getPager() != null ? listUser.getPager() : null);
		model.addAttribute("suc", "");
		return "/app/appedit";
	}

	/**
	 * 方法说明：显示应用管理员
	 * 
	 * @param appuuid
	 *            appuuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/appshowadminuser")
	public String appshowadminuserHandler(@RequestParam(value = "appuuid") String appuuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		// TODO Auto-generated method stub
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Application app = uumService.getApplicationByUuid(appuuid.trim());
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 3;
		}
		UserPageResult listUser = uumService.getManagerUnderApplication(page, pagesize, app);
		model.addAttribute("app", app);
		model.addAttribute("cuappname", app.getName());
		model.addAttribute("userlist", listUser.getList() != null ? listUser.getList()
				: new ArrayList<User>());
		model.addAttribute("javapage", listUser.getPager() != null ? listUser.getPager() : null);
		model.addAttribute("suc", "");
		return "/app/appshowadminuser";
	}

	/**
	 * 方法说明：编辑应用成功
	 * 
	 * @param appuuid
	 *            appuuid
	 * @param appRemark
	 *            appRemark
	 * @param appUrl
	 *            appUrl
	 * @param appSingleLoginUrl
	 *            appSingleLoginUrl
	 * @param appUserName
	 *            appUserName
	 * @param appPassword
	 *            appPassword
	 * @param appDBUrl
	 *            appDBUrl
	 * @param appDBPort
	 *            appDBPort
	 * @param appDBName
	 *            appDBName
	 * @param appDBUserName
	 *            appDBUserName
	 * @param appDBPassword
	 *            appDBPassword
	 * @param appDBMidTableName
	 *            appDBMidTableName
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param jsgroupAreName
	 *            jsgroupAreName
	 * @param accessGroup
	 *            accessGroup
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/app/appeditsuc")
	public String appeditsucHandler(@RequestParam(value = "appuuid") String appuuid,
			@RequestParam(value = "appRemark") String appRemark,
			@RequestParam(value = "appUrl") String appUrl,
			@RequestParam(value = "appSingleLoginUrl") String appSingleLoginUrl,
			@RequestParam(value = "appUserName") String appUserName,
			@RequestParam(value = "appPassword") String appPassword,
			@RequestParam(value = "appDBUrl") String appDBUrl,
			@RequestParam(value = "appDBPort") String appDBPort,
			@RequestParam(value = "appDBName") String appDBName,
			@RequestParam(value = "appDBUserName") String appDBUserName,
			@RequestParam(value = "appDBPassword") String appDBPassword,
			@RequestParam(value = "appDBMidTableName") String appDBMidTableName,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			@RequestParam(value = "jsgroupAreName", required = false) String jsgroupAreName,
			@RequestParam(value = "accessGroup", required = false) String accessGroup,
			ModelMap model)
	{
		// TODO Auto-generated method stub
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Application app = uumService.getApplicationByUuid(appuuid.trim());
		Set<Group> groupSet = new HashSet<Group>();
		if (jsgroupuuid != null) {
			for (String groupid : jsgroupuuid.split(",")) {
				Group group = uumService.getGroupByUuid(groupid);
				groupSet.add(group);
			}
		}
		app.setAdminGroups(null);
		uumService.updateApplication(app);

		app.setAdminGroups(groupSet);
		app.setStatus(1);
		app.setRemark(appRemark);
		uumService.updateApplication(app);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 20;
		}
		UserPageResult<User> listUser = uumService.getManagerUnderApplication(page, pagesize, app);
		model.addAttribute("app", app);
		model.addAttribute("jsgroupAreName", jsgroupAreName);
		model.addAttribute("jsgroupuuid", jsgroupuuid);
		model.addAttribute("cuappname", app.getName());
		model.addAttribute("userlist", listUser.getList());
		model.addAttribute("javapage", listUser.getPager());
		model.addAttribute("suc", "编辑成功");
		return "/app/appedit";
	}

	/**
	 * 方法说明：搜索所有人
	 * 
	 * @param appuuid
	 *            appuuid
	 * @param searchcontent
	 *            searchcontent
	 * @param searchType
	 *            searchType
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/search/appsearchalluser")
	public String appsearchalluserHandler(@RequestParam(value = "appuuid") String appuuid,
			@RequestParam(value = "searchcontent") String searchcontent,
			@RequestParam(value = "searchType") String searchType,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		// TODO Auto-generated method stub
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}

		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 3;
		}
		Condition cond = new Condition();
		if (searchType != null && searchType.equals("username")) {
			cond.setUserName(searchcontent);
		}
		if (searchType != null && searchType.equals("userid")) {
			cond.setUserId(searchcontent);
		}
		UserPageResult pathUser = uumService.getManagerUnderApplication(page, 99999999,
				uumService.getApplicationByUuid(appuuid.trim()));
		UserPageResult listUser = uumService.searchUsersByCondition(page, pagesize, cond);
		List<User> pathUsers = pathUser.getList();
		List<User> listUsers = listUser.getList();
		pathUsers.retainAll(listUsers);
		listUsers.removeAll(pathUsers);
		model.addAttribute("appuuid", appuuid);
		model.addAttribute("pathlist", pathUsers);
		model.addAttribute("userlist", listUsers);
		model.addAttribute("javapage", listUser.getPager());
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("searchType", searchType);
		return "/app/search/appsearchalluser";
	}

	/**
	 * 方法说明：搜索指定组内所有人
	 * 
	 * @param groupuuid
	 *            groupuuid
	 * @param type
	 *            type
	 * @param searchcontent
	 *            searchcontent
	 * @param searchType
	 *            searchType
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/search/appsearchgroupuser")
	public String appsearchgroupuserHandler(@RequestParam(value = "groupuuid") String groupuuid,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "searchcontent") String searchcontent,
			@RequestParam(value = "searchType") String searchType,
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
		type = StringUtils.defaultIfEmpty(type, "appuserlist");
		Condition cond = buildSearchDetail(searchcontent, searchType);
		Group group = uumService.getGroupByUuid(groupuuid.trim());
		UserPageResult listUser;
		if (!uumService.isSuperAppGroupManager(group, loginUser)) {
			Department org = uumService.getDepartmentByDeptCode(uumService.getUserPrimaryDepartment(loginUser)
					.getOrgCode());
			listUser = uumService
					.searchUsersUnderApplicationByOrg(page, pagesize, cond, org, group);
		} else {
			listUser = uumService.searchUsersMembersByLoginUser(page, pagesize, cond, loginUser,
					group);
		}
		List<User> userList = listUser.getList();
		List<Object[]> mixUserList = new ArrayList<Object[]>();
		for (User u : userList) {
			Object[] item = new Object[4];
			item[0] = u;
			item[1] = ((Department) uumService.getUserOrgDepartment(u).get(0)).getName();
			item[2] = uumService.getUserPrimaryDepartment(u).getName();
			Set<Group> groups = u.getGroups();
			item[3] = groups.contains(group);
			mixUserList.add(item);
		}
		model.addAttribute("userlist", mixUserList);
		model.addAttribute("javapage", listUser.getPager());
		model.addAttribute("groupuuid", groupuuid);
		model.addAttribute("type", type);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("searchType", searchType);
		model.addAttribute("showAppAccount", InitParameters.getShowAppPwdTurnOn());
		return "/app/search/appsearchgroupuser";
	}

	/**
	 * 方法说明：构建搜索内容
	 * 
	 * @param searchcontent
	 * @param searchType
	 * @return
	 */
	private Condition buildSearchDetail(String searchcontent, String searchType)
	{
		searchcontent = searchcontent.trim();
		Condition cond = new Condition();
		if ("username".equals(searchType)) {
			cond.setUserName(searchcontent);
		}
		if ("userid".equals(searchType)) {
			cond.setUserId(searchcontent);
		}
		return cond;
	}

	/**
	 * 将用户提升为XX应用管理员
	 * 
	 * @param model
	 *            model
	 * @param userids
	 *            userids
	 * @param appuuid
	 *            appuuid
	 * @param searchType
	 *            searchType
	 * @param searchcontent
	 *            searchcontent
	 * @return String
	 */
	@RequestMapping("/app/appupdateuseradmin")
	public String appupdateuseradminHandler(ModelMap model,
			@RequestParam(value = "userids") String userids,
			@RequestParam(value = "appuuid") String appuuid,
			@RequestParam(value = "searchType") String searchType,
			@RequestParam(value = "searchcontent") String searchcontent)
	{
		searchcontent = searchcontent.trim();
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		String[] userid = userids.split(",");
		Application ap = uumService.getApplicationByUuid(appuuid);
		for (int i = 0; i < userid.length; i++) {
			uumService.setApplicationManager(ap, uumService.getUserByUuid(userid[i]));
		}
		Condition cond = new Condition();
		if (searchType != null && searchType.equals("username")) {
			cond.setUserName(searchcontent);
		}
		if (searchType != null && searchType.equals("userid")) {
			cond.setUserId(searchcontent);
		}
		UserPageResult<User> listUser = uumService.searchUsersByCondition(1, 3, cond);
		model.addAttribute("userlist", listUser.getList());
		model.addAttribute("javapage", listUser.getPager());
		model.addAttribute("appuuid", appuuid);
		model.addAttribute("searchType", searchType);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		model.addAttribute("showAppAccount", InitParameters.getShowAppPwdTurnOn());
		return "/app/search/appsearchalluser";
	}

	/**
	 * 方法说明：将用户从XX应用中移除(从组中)
	 * 
	 * @param model
	 *            model
	 * @param userid
	 *            userid
	 * @param groupuuid
	 *            groupuuid
	 * @param type
	 *            type
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return String
	 */
	@RequestMapping("/app/appdelgroupuser")
	public String appdelgroupuserHandler(ModelMap model,
			@RequestParam(value = "moveusers1") String[] userid,
			@RequestParam(value = "groupuuid") String groupuuid,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Group group = uumService.getGroupByUuid(groupuuid.trim());
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i].trim());
			uumService.removeApplicationMember(group, user);
			List<Event> events = new ArrayList<Event>();
			events.add(eventFactory.createUserEventRemoveGroup(user.getUuid(), group.getUuid()));
			Map<String, String[]> map = modifyAppLoginEnable(group, user, Boolean.FALSE);
			if (!map.isEmpty()) {
				events.add(eventFactory.createEventUpdateUser(user.getUuid(), map));
			}
			eventListenerHandler.handle(events);
		}
		appuserlistHandler(groupuuid, type, page, pagesize, model);
		return "/app/appuserlist";
	}

	/**
	 * 方法说明：将用户提升为XX应用成员(从组中)
	 * 
	 * @param userid
	 *            userid
	 * @param groupuuid
	 *            groupuuid
	 * @param type
	 *            type
	 * @param searchcontent
	 *            searchcontent
	 * @param searchType
	 *            searchType
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/app/appupdategroupuser")
	public String appupdategroupuserHandler(@RequestParam(value = "moveusers") String[] userid,
			@RequestParam(value = "groupuuid") String groupuuid,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "searchcontent", required = false) String searchcontent,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Group group = uumService.getGroupByUuid(groupuuid.trim());
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i].trim());
			uumService.setApplicationMember(group, user);
			List<Event> events = new ArrayList<Event>();
			events.add(eventFactory.createUserEventAddGroup(user.getUuid(), group.getUuid()));
			// 是否自动开启同步，在中煤项目中不要自动同步，需要开启开关才可以
			Boolean flag = Boolean.TRUE;
			if ("zm".equals(ProjectResolver.getId())) {
				flag = Boolean.FALSE;
			}
			Map<String, String[]> map = modifyAppLoginEnable(group, user, flag);
			if (!map.isEmpty()) {
				events.add(eventFactory.createEventUpdateUser(user.getUuid(), map));
			}
			eventListenerHandler.handle(events);
		}
		appuserlistHandler(groupuuid, type, page, pagesize, model);
		return "/app/appuserlist";
	}

	/**
	 * 方法说明：当用户添加或移除应用系统组时,自动更新对应应用系统的扩展属性
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param flag
	 *            flag
	 * @return Map
	 */
	private Map<String, String[]> modifyAppLoginEnable(Group group, User user, Boolean flag)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		List<AttributeType> ats = uumService.getAttributeTypeByResourceAndAppGroup(user, null,
				group);
		String appStatusCode = InitParameters.getAppStatusCode().split("XXXX")[1];
		String appAccount = InitParameters.getAppAccountLocal().split("XXXX")[1];
		String appLoginEnable = InitParameters.getAppLoginEnableLocal().split("XXXX")[1];

		Map<String, String> attrs = uumService.getAttributesMapByResourceAndTypes(user, ats);

		for (AttributeType at : ats) {
			String value = String.valueOf("");
			if (at.getId().endsWith(appStatusCode)) {
				continue;
				// value = String.valueOf(ResourceStatus.AUTHORIZE.intValue());
			} else if (at.getId().endsWith(appAccount)) {
				value = user.getId();
				if (InitParameters.isCqGroupAuthor()
						&& StringUtils.contains(group.getCode(), "YXYW")) {
					List<Attribute> atts = uumService.getAttributesByResAndType(user,
							"sgccEmployeeCode");
					if (CollectionUtils.isNotEmpty(atts)) {
						value = StringUtils.defaultIfEmpty(atts.get(0).getValue(), user.getId());
					}
				}
				map.put(at.getId(), new String[] { attrs.get(at.getId()), value });
			} else if (at.getId().endsWith(appLoginEnable)) {
				value = String.valueOf(flag);
				map.put(at.getId(), new String[] { attrs.get(at.getId()), value });
			} else if (at.getId().contains("Pwd") || at.getId().contains("Password")) {
				if (InitParameters.isPlainPassword()) {
					value = user.getPlainPassword();
				} else {
					value = user.getPassword();
				}
				map.put(at.getId(), new String[] { attrs.get(at.getId()), value });
			}
			uumService.modifyResourceAttribute(null, user, at, value);
		}
		return map;
	}

	/**
	 * 方法说明：应用列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/app/applist")
	public String applistHandler(@RequestParam(value = "page", required = false) Integer page,
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
			pagesize = 20;
		}
		UserPageResult<Application> upr = uumService.getAllApplication(page, pagesize);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("applist", upr.getList());
		return "/app/applist";
	}

	/**
	 * 方法说明：应用管理员主列表(上_应用管理员列表,下_用户信息)
	 * 
	 * @param groupuuid
	 *            groupuuid
	 * @param model
	 *            model
	 */
	@RequestMapping("/app/appuserlistmain")
	public void appuserlistmainHandler(@RequestParam(value = "groupuuid") String groupuuid,
			ModelMap model)
	{
		model.addAttribute("groupuuid", groupuuid);
	}

	/**
	 * 方法说明：编辑应用管理员
	 * 
	 * @param useruuid
	 *            useruuid
	 * @param groupuuid
	 *            groupuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/app/appedituser")
	public String appedituserHandler(@RequestParam(value = "useruuid") String useruuid,
			@RequestParam(value = "groupuuid") String groupuuid, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		User user = uumService.getUserByUuid(useruuid);
		Group group = uumService.getGroupByUuid(groupuuid);
		List<Attribute> listAtt = uumService.getAttributesUnderResourceAndGroup(user, group);
		List<AttributeType> attributelistt0 = uumService.getAttributeTypeByResourceAndAppGroup(
				user, user.getType(), group);
		boolean checkAttType = false;

		for (int i = 0; i < attributelistt0.size(); i++) {
			for (int j = 0; j < listAtt.size(); j++) {
				if (attributelistt0.get(i).getUuid().equals(listAtt.get(j).getType().getUuid())) {
					checkAttType = true;
				}
			}
			if (!checkAttType) {
				Attribute att = new Attribute();
				att.setOwnerResource(user);
				att.setType(attributelistt0.get(i));
				uumService.saveAttribute(att);
			}
			checkAttType = false;
		}
		listAtt = uumService.getAttributesUnderResourceAndGroup(user, group);
		String attr = group.getCode().trim().toUpperCase();
		String flag = "";
		String name = "";
		String pwd = "";
		String attrflaguuid = "";
		String attrnameuuid = "";
		String attrpwduuid = "";
		List<Attribute> listShowAtt = new ArrayList<Attribute>();
		for (int i = 0; i < listAtt.size(); i++) {
			String attrTypeId = listAtt.get(i).getType().getId().toUpperCase().toString().trim();

			if (attrTypeId.contains(attr + "LoginEnable".toUpperCase())) {
				flag = Boolean.valueOf(listAtt.get(i).getValue()).toString();
				attrflaguuid = listAtt.get(i).getUuid();
				continue;
			} else if (attrTypeId.contains(attr + "Account".toUpperCase())) {
				name = listAtt.get(i).getValue();
				attrnameuuid = listAtt.get(i).getUuid();
				continue;
			} else if (attrTypeId.contains(attr + "Pwd".toUpperCase())
					|| attrTypeId.contains(attr + "Password".toUpperCase())) {
				pwd = StringUtils.defaultIfEmpty(pwd, user.getPlainPassword());
				attrpwduuid = listAtt.get(i).getUuid();
				continue;
			} else if (attrTypeId.contains(attr + "EP".toUpperCase())) {
				listShowAtt.add(listAtt.get(i));
			} else if (attrTypeId.contains(attr + "R3".toUpperCase())) {
				listShowAtt.add(listAtt.get(i));
			} else if (attrTypeId.contains(attr + "BW".toUpperCase())) {
				listShowAtt.add(listAtt.get(i));
			}
		}
		model.addAttribute("showflag",
				"true".equalsIgnoreCase(InitParameters.getShowAppPwdTurnOn()));
		model.addAttribute("flag", flag);
		model.addAttribute("loginname", name);
		model.addAttribute("attrflaguuid", attrflaguuid);
		model.addAttribute("attrnameuuid", attrnameuuid);
		model.addAttribute("groupuuid", groupuuid);
		model.addAttribute("listAtt", listShowAtt);
		model.addAttribute("useruuid", useruuid);
		model.addAttribute("userid", user.getId());
		model.addAttribute("username", user.getName());
		model.addAttribute("attrpwduuid", attrpwduuid);
		model.addAttribute("pwd", pwd);
		return "/app/appedituser";
	}

	/**
	 * 方法说明：编辑用户应用系统登录信息
	 * 
	 * @param useruuid
	 *            useruuid
	 * @param groupuuid
	 *            groupuuid
	 * @param attuuids
	 *            attuuids
	 * @param attvalues
	 *            attvalues
	 * @param clogin
	 *            clogin
	 * @param loginusername
	 *            loginusername
	 * @param loginuserpwd
	 *            loginuserpwd
	 * @param hidloginuserpwd
	 *            hidloginuserpwd
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/app/appeditusersuc")
	public String appeditusersucHandler(@RequestParam(value = "useruuid") String useruuid,
			@RequestParam(value = "groupuuid", required = false) String groupuuid,
			@RequestParam(value = "attuuids", required = false) String attuuids,
			@RequestParam(value = "attvalues", required = false) String attvalues,
			@RequestParam(value = "clogin", required = false) String clogin,
			@RequestParam(value = "loginuser", required = false) String loginusername,
			@RequestParam(value = "loginpwd", required = false) String loginuserpwd,
			@RequestParam(value = "hidloginuserpwd", required = false) String hidloginuserpwd,
			final HttpServletRequest request, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		// //////日志
		User user = uumService.getUserByUuid(useruuid);
		Map<String, String[]> map = new HashMap<String, String[]>();
		Group app = uumService.getGroupByUuid(groupuuid);
		if (StringUtils.isNotBlank(hidloginuserpwd)) {
			Attribute ab = uumService.getAttributeByUuid(hidloginuserpwd.trim());

			if (!StringUtils.equals(ab.getValue(), loginuserpwd)) {
				map.put(ab.getType().getId(), new String[] { ab.getValue(), loginuserpwd });
				ab.setValue(loginuserpwd);
				uumService.updateAttribute(ab);
			}
			// }else{
			// List<AttributeType> list =
			// uumService.getAttributeTypeById(InitParameters.getAppPwdLocal().replace("XXXX",app.getCode()));
			// if(CollectionUtils.isNotEmpty(list)){
			// map.put(list.get(0).getId(),
			// new String[] { null, user.getPlainPassword() });
			// }
		}
		Boolean loginEnable = Boolean.valueOf("on".equals(clogin));
		if (StringUtils.isNotBlank(attuuids)) {
			Attribute ab = uumService.getAttributeByUuid(attuuids.trim());

			if (!StringUtils.equals(ab.getValue(), loginEnable.toString())) {
				map.put(ab.getType().getId(),
						new String[] { ab.getValue(), loginEnable.toString() });
				ab.setValue(loginEnable.toString());
				uumService.updateAttribute(ab);
			}
		}
		if (StringUtils.isNotBlank(attvalues)) {
			Attribute ab = uumService.getAttributeByUuid(attvalues.trim());

			if (!StringUtils.equals(ab.getValue(), loginusername)) {
				map.put(ab.getType().getId(), new String[] { ab.getValue(), loginusername });
				ab.setValue(loginusername);
				uumService.updateAttribute(ab);
			}
		}

		// uumService.updateUser(user);
		if (!map.isEmpty()) {
			Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
			eventListenerHandler.handle(event);
		}
		model.addAttribute("groupList", user.getGroups());
		model.addAttribute("groupuuid", groupuuid);
		model.addAttribute("useruuid", useruuid);
		model.addAttribute("userid", user.getId());
		model.addAttribute("username", user.getName());
		appuserlistHandler(groupuuid, null, null, null, model);
		return "/app/appuserlist";
	}

}