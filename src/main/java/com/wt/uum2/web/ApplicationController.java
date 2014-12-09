package com.wt.uum2.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.AuthenticationProfile;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.UUMAppService;
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
public class ApplicationController extends BaseController
{

	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * 
	 */
	private UUMAppService uumAppService;

	/**
	 * 
	 */
	private EventFactory eventFactory;
	/**
	 * 
	 */
	private EventListenerHandler eventListenerHandler;

	/**
	 * @param uumAppService
	 *            the uumAppService to set
	 */
	public void setUumAppService(UUMAppService uumAppService)
	{
		this.uumAppService = uumAppService;
	}

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

	/**
	 * 方法说明：应用系统用户列表
	 * 
	 * @param type
	 *            type
	 * @param uuid
	 *            uuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/application/content")
	public String applicationContext(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "id") String uuid, ModelMap model)
	{
		if (uuid.equals("create")) {
			List<AttributeType> list = uumService.getAttributeTypeByResourceOnPage(
					new Application(), 3, "0");
			model.addAttribute("attributelist", list);
			return "/application/createApp";
		}
		model.addAttribute("application", uumService.getApplicationByUuid(uuid));
		model.addAttribute("type", StringUtils.defaultIfEmpty(type, "authorized"));

		return "/application/content";
	}

	/**
	 * 方法说明：创建新应用系统
	 * 
	 * @param model
	 *            model
	 * @param appName
	 *            appName
	 * @param appId
	 *            appId
	 * @param appRemark
	 *            appRemark
	 * @param order
	 *            order
	 * @param errorKeyword
	 *            errorKeyword
	 * @param actionUrlServerSide
	 *            actionUrlServerSide
	 * @param submitMethod
	 *            submitMethod
	 * @param charset
	 *            charset
	 * @param actionUrl
	 *            actionUrl
	 * @param inputNamePassword
	 *            inputNamePassword
	 * @param inputNameUserid
	 *            inputNameUserid
	 * @param appAccountKey
	 *            appAccountKey
	 * @param appLoginEnableKey
	 *            appLoginEnableKey
	 * @param appPasswordKey
	 *            appPasswordKey
	 * @param appStatusKey
	 *            appStatusKey
	 * @param admingroupuuid
	 *            admingroupuuid
	 * @return String
	 */
	@RequestMapping("/application/createApp")
	public String createApplicationHandle(
			ModelMap model,
			@RequestParam(value = "name") String appName,
			@RequestParam(value = "code") String appId,
			@RequestParam(value = "remark", required = false) String appRemark,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "errorKeyword", required = false) String errorKeyword,
			@RequestParam(value = "actionUrlServerSide", required = false) String actionUrlServerSide,
			@RequestParam(value = "submitMethod", required = false) String submitMethod,
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "actionUrl", required = false) String actionUrl,
			@RequestParam(value = "inputNamePassword", required = false) String inputNamePassword,
			@RequestParam(value = "inputNameUserid", required = false) String inputNameUserid,
			@RequestParam(value = "appAccountKey", required = false) String appAccountKey,
			@RequestParam(value = "appLoginEnableKey", required = false) String appLoginEnableKey,
			@RequestParam(value = "appPasswordKey", required = false) String appPasswordKey,
			@RequestParam(value = "appStatusKey", required = false) String appStatusKey,
			@RequestParam(value = "admingroupuuid", required = false) String admingroupuuid)
	{
		Application ap = new Application();
		AuthenticationProfile apo = new AuthenticationProfile();
		Set<Group> groupSet = new HashSet<Group>();
		if (admingroupuuid != null) {
			for (String groupid : admingroupuuid.split(",")) {
				Group group = uumService.getGroupByUuid(groupid);
				if (group != null) {
					groupSet.add(group);
				}
			}
			if (CollectionUtils.isNotEmpty(groupSet)) {
				ap.setAdminGroups(groupSet);
			}
		}
		ap.setCode(appId);
		ap.setName(appName);
		ap.setRemark(appRemark);
		ap.setOrder(Long.valueOf(1));
		ap.setStatus(Integer.valueOf(1));
		ap.setModifiedTime(Calendar.getInstance().getTime());
		uumService.createApplication(ap);
		ap = uumService.getApplicationByAppId(appId);
		apo.setApplication(ap);
		apo.setApplicationUUID(ap.getUuid());
		apo.setActionUrl(actionUrl);
		apo.setInputNamePassword(inputNamePassword);
		apo.setInputNameUserid(inputNameUserid);
		apo.setProfileName(ap.getCode() + "_pf");
		apo.setCharset(charset);
		apo.setErrorKeyword(errorKeyword);
		apo.setSubmitMethod(submitMethod);
		apo.setActionUrlServerSide(actionUrlServerSide);
		uumService.createAuthenticationProfile(apo);
		return "redirect:/application/content.nsf?id=" + ap.getUuid();
	}

	/**
	 * 方法说明：跳转到维护页面
	 * 
	 * @param model
	 *            model
	 * @param uuid
	 *            主键
	 */
	@RequestMapping("/application/updateApp")
	public void updateApplicationHandle(ModelMap model, @RequestParam(value = "uuid") String uuid)
	{
		Application ap = uumService.getApplicationByUuid(uuid);
		AuthenticationProfile apo = uumAppService.getAuthenticationProfile(ap);
		// boolean flag = false;
		List<AttributeType> attributeTypeList = uumService.getAttributeTypeAllByResource(ap, null);

		for (AttributeType attributeType : attributeTypeList) {
			List<Attribute> atts = uumService.getAttributesByResAndType(ap, attributeType);
			if (CollectionUtils.isEmpty(atts)) {
				Attribute att = new Attribute(ap, attributeType);
				uumService.saveAttribute(att);
			}
		}
		model.addAttribute("application", ap);
		model.addAttribute("appProfile", apo);
		model.addAttribute("attributelist", uumService.getAttributesUnderResource(ap));
	}

	/**
	 * 方法说明：更新应用系统
	 * 
	 * @param model
	 *            model
	 * @param uuid
	 *            uuid
	 * @param appName
	 *            appName
	 * @param appRemark
	 *            appRemark
	 * @param order
	 *            order
	 * @param errorKeyword
	 *            errorKeyword
	 * @param actionUrlServerSide
	 *            actionUrlServerSide
	 * @param submitMethod
	 *            submitMethod
	 * @param charset
	 *            charset
	 * @param actionUrl
	 *            actionUrl
	 * @param inputNamePassword
	 *            inputNamePassword
	 * @param inputNameUserid
	 *            inputNameUserid
	 * @param appAccountKey
	 *            appAccountKey
	 * @param appLoginEnableKey
	 *            appLoginEnableKey
	 * @param appPasswordKey
	 *            appPasswordKey
	 * @param appStatusKey
	 *            appStatusKey
	 * @param admingroupuuid
	 *            admingroupuuid
	 * @param request
	 *            request
	 * @return String
	 */
	@RequestMapping("/application/updateAppSuc")
	public String updateApplicationHandle(
			ModelMap model,
			@RequestParam(value = "uuid") String uuid,
			@RequestParam(value = "name") String appName,
			@RequestParam(value = "remark", required = false) String appRemark,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "errorKeyword", required = false) String errorKeyword,
			@RequestParam(value = "actionUrlServerSide", required = false) String actionUrlServerSide,
			@RequestParam(value = "submitMethod", required = false) String submitMethod,
			@RequestParam(value = "charset", required = false) String charset,
			@RequestParam(value = "actionUrl", required = false) String actionUrl,
			@RequestParam(value = "inputNamePassword", required = false) String inputNamePassword,
			@RequestParam(value = "inputNameUserid", required = false) String inputNameUserid,
			@RequestParam(value = "appAccountKey", required = false) String appAccountKey,
			@RequestParam(value = "appLoginEnableKey", required = false) String appLoginEnableKey,
			@RequestParam(value = "appPasswordKey", required = false) String appPasswordKey,
			@RequestParam(value = "appStatusKey", required = false) String appStatusKey,
			@RequestParam(value = "admingroupuuid", required = false) String admingroupuuid,
			final HttpServletRequest request)
	{
		Application ap = uumService.getApplicationByUuid(uuid);
		AuthenticationProfile apo = uumAppService.getAuthenticationProfile(ap);
		if (apo == null) {
			apo = new AuthenticationProfile();
		}
		Set<Group> groupSet = new HashSet<Group>();
		if (admingroupuuid != null) {
			for (String groupid : admingroupuuid.split(",")) {
				Group group = uumService.getGroupByUuid(groupid);
				if (group != null) {
					groupSet.add(group);
				}
			}
			if (CollectionUtils.isNotEmpty(groupSet)) {
				ap.setAdminGroups(groupSet);
			}
		}
		ap.setName(appName);
		ap.setRemark(appRemark);
		ap.setOrder(Long.valueOf(1));
		ap.setStatus(Integer.valueOf(1));
		ap.setModifiedTime(Calendar.getInstance().getTime());
		uumService.updateApplication(ap);
		apo.setApplication(ap);
		apo.setApplicationUUID(ap.getUuid());
		apo.setActionUrl(actionUrl);
		apo.setActionUrlServerSide(actionUrlServerSide);
		apo.setCharset(charset);
		apo.setErrorKeyword(errorKeyword);
		apo.setSubmitMethod(submitMethod);
		apo.setInputNamePassword(inputNamePassword);
		apo.setInputNameUserid(inputNameUserid);
		apo.setProfileName(ap.getCode() + "_pf");
		uumAppService.updateAuthenticationProfile(apo);

		List<AttributeType> attributeTypeList = uumService.getAttributeTypeAllByResource(ap, null);
		for (AttributeType attributeType : attributeTypeList) {
			modifyAttribute(ap, attributeType, request.getParameter(attributeType.getId()));
		}
		return "redirect:/application/content.nsf?id=" + ap.getUuid();
	}

	/**
	 * 方法说明：modifyAttribute
	 * 
	 * @param res
	 *            res
	 * @param type
	 *            type
	 * @param value
	 *            value
	 * @return Map
	 */
	private Map<String, String[]> modifyAttribute(Resource res, AttributeType type, String value)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		List<Attribute> atts = uumService.getAttributesByResAndType(res, type);
		if (CollectionUtils.isEmpty(atts) && StringUtils.isNotBlank(value)) {
			Attribute att = new Attribute(res, type);
			att.setValue(value);
			map.put(type.getId(), new String[] { null, value });
			uumService.saveAttribute(att);
		} else if (CollectionUtils.isNotEmpty(atts) && StringUtils.isBlank(value)) {
			for (Attribute att : atts) {
				if (!StringUtils.equals(att.getValue(), value)) {
					map.put(type.getId(), new String[] { att.getValue(), null });
				}
				uumService.deleteAttribute(att);
			}
		} else {
			for (Attribute att : atts) {
				if (!StringUtils.equals(att.getValue(), value)) {
					map.put(type.getId(), new String[] { att.getValue(), value });
					att.setValue(value);
					uumService.updateAttribute(att);
				}
			}
		}
		return map;
	}

	/**
	 * 方法说明：取得所有应用系统下用户列表(所有在应用系统下的用户)
	 * 
	 * @param uuid
	 *            uuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/application/authorizedList")
	public String applicationUserAdminList(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{

		Application app = uumService.getApplicationByUuid(uuid);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<Object> upr = new UserPageResult<Object>();
		if (app != null) {
			upr = uumService.getUsersUnderApplication(page, pagesize, app);
		}
		model.addAttribute("application", app);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("applist", upr.getList());
		return "/application/authorizedList";
	}

	
	
	/**
	 * 方法说明：取得所有应用系统下未授权用户列表(所有不在应用系统下的用户)
	 * 
	 * @param uuid
	 *            uuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/application/unauthorizedList")
	public String applicationAuthorizationList(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{

		Application app = uumService.getApplicationByUuid(uuid);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<User> upr = new UserPageResult<User>();
		if (app != null) {
			upr = uumService.getUsersNotUnderApplication(page, pagesize, app);
		}
		model.addAttribute("application", app);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("applist", upr.getList());
		return "/application/unauthorizedList";
	}

	/**
	 * 方法说明：给用户授权某应用系统
	 * 
	 * @param uuid
	 *            uuid
	 * @param useruuids
	 *            useruuids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/application/adduserstoapp")
	public String addUserToApplicationHandle(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "userids", required = false) String useruuids, ModelMap model)
	{

		Application app = uumService.getApplicationByUuid(uuid);
		String[] useruuid = useruuids.split(",");
		if (!ArrayUtils.isEmpty(useruuid)) {
			for (String string : useruuid) {
				User u = uumService.getUserByUuid(string);
				uumAppService.addUserToApplication(u, app, null, u.getId(),
						InitParameters.getUserPassword(), null);
			}
		}
		return "/confirmation";
	}

	/**
	 * 方法说明：搜索
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
	 */
	@RequestMapping("/application/authorizedSearch")
	public void authorizedSearch(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		Application app = uumService.getApplicationByUuid(uuid);
		UserPageResult<UserApplication> upr = new UserPageResult<UserApplication>();
		if (app != null) {
			upr = uumService.searchUserUnderApplication(page, pagesize, condition, app);
		}
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("application", app);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
	}

	/**
	 * 方法说明：未授权用户搜索
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
	 */
	@RequestMapping("/application/unauthorizedSearch")
	public void unauthorizedSearch(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "searchvalue", required = false) String searchvalue,
			@RequestParam(value = "searchcontent", required = false) String searchcontent,
			ModelMap model)
	{
		searchcontent = StringUtils.trimToEmpty(searchcontent);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		if (searchvalue.equals("deptid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("deptname")) {
			condition.setUserName(searchcontent);
		}
		Application app = uumService.getApplicationByUuid(uuid);
		UserPageResult<User> upr = new UserPageResult<User>();
		if (app != null) {
			upr = uumService.searchUserNotUnderApplication(page, pagesize, condition, app);
		}
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("application", app);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
	}

	/**
	 * 方法说明：删除应用
	 * 
	 * @param uuid
	 *            uuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/application/delete")
	public String deleteApplicationHandler(@RequestParam(value = "id") String uuid, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Application app = uumService.getApplicationByUuid(uuid);
		uumAppService.deleteApplication(app);
		String flag = String.valueOf(Boolean.TRUE);
		model.addAttribute("Finally", String.valueOf(flag));
		return "/confirmation";
	}

	/**
	 * 
	 * 方法说明：判断应用系统下是否存在资源
	 * 
	 * @param uuid
	 *            uuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/application/existResourceUnderApp")
	public String existResourceUnderApplication(@RequestParam(value = "id") String uuid,
			ModelMap model)
	{
		Application app = uumService.getApplicationByUuid(uuid);
		List<UserApplication> userList = uumAppService.getUserApplicationByApplication(app);
		String flag = String.valueOf(Boolean.TRUE);
		if (CollectionUtils.isEmpty(userList)) {
			flag = String.valueOf(Boolean.FALSE);
		}
		model.addAttribute("Finally", String.valueOf(flag));
		return "/confirmation";

	}

	/**
	 * 方法说明：从应用系统移除用户
	 * 
	 * @param uuid
	 *            uuid
	 * @param userids
	 *            userids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/applicaiton/moveuser")
	public String deleteUserApplicationHanlder(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "userids", defaultValue = "") String userids, ModelMap model)
	{
		Application app = uumService.getApplicationByUuid(uuid);
		String[] users = userids.split(",");
		if (!ArrayUtils.isEmpty(users)) {
			for (String string : users) {
				User u = uumService.getUserByUuid(string);
				UserApplication ua = uumAppService.getUserApplication(app, u);
				if (ua != null) {
					uumAppService.deleteUserApplication(ua);
				}
			}
		}
		String flag = String.valueOf(Boolean.TRUE);
		model.addAttribute("Finally", String.valueOf(flag));
		return "/confirmation";
	}

	@RequestMapping("/application/modifyAppUser")
	public String modifyAppUser(@RequestParam(value = "uid") String uid,
			@RequestParam(value = "aid") String aid, ModelMap model)
	{
		Application app = uumService.getApplicationByUuid(aid);
		User u = uumService.getUserByUuid(uid);
		UserApplication ua = uumAppService.getUserApplication(app, u);
		model.addAttribute("ua", ua);
		return "/application/modifyAppUser";
	}

	@RequestMapping("/application/modifyAppUserSuc")
	public String modifyAppUserAction(@RequestParam(value = "uuid") String uuid,
			@RequestParam(value = "useruuid") String uid,
			@RequestParam(value = "appuuid") String aid,
			@RequestParam(value = "loginable") String loginable,
			@RequestParam(value = "mappenduserid") String mappenduserid,
			@RequestParam(value = "mappendpassword") String mappendpassword, ModelMap model)
	{
		Application app = uumService.getApplicationByUuid(aid);
		User u = uumService.getUserByUuid(uid);
		uumAppService.saveOrUpdateUserApplication(app, u, mappenduserid, mappendpassword,
				"on".equals(loginable), null);
		model.addAttribute("ua", uumAppService.getUserApplication(app, u));
		return "/application/modifyAppUser";
	}
	
	
	@RequestMapping("/application/userapplicationList")
	public String userapplicationList(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{

		Application app = uumService.getApplicationByUuid(uuid);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<Object> upr = new UserPageResult<Object>();
		if (app != null) {
			upr = uumService.getUsersUnderApplication(page, pagesize, app);
		}
		model.addAttribute("application", app);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("applist", upr.getList());
		return "/application/userapplicationList";
	}
	
	
	@RequestMapping("/application/userapplicationSearch")
	public void userapplicationSearch(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		Application app = uumService.getApplicationByUuid(uuid);
		UserPageResult<UserApplication> upr = new UserPageResult<UserApplication>();
		if (app != null) {
			upr = uumService.searchUserUnderApplication(page, pagesize, condition, app);
		}
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("application", app);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
	}
}