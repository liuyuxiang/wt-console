package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentTemp;
import com.wt.uum2.domain.DepartmentTempLog;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.ResourceLog;
import com.wt.uum2.domain.ResourceTempDetails;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserTemp;
import com.wt.uum2.domain.UserTempLog;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.quartz.SyncListener;
import com.wt.uum2.service.UUMDataService;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.UUMTempDataService;
import com.wt.uum2.userlist.DeptTempType;
import com.wt.uum2.userlist.UserTempType;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-12-10
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class SyncController extends BaseController
{

	/**
	 * 
	 */
	private UUMService uumService;
	/**
	 * 
	 */
	private UUMTempDataService uumTempDataService;
	/**
	 * 
	 */
	private UUMDataService uumDataService;
	/**
	 * 
	 */
	private SyncListener synJobDetail;
	/**
	 * 
	 */
	private EventFactory eventFactory;
	/**
	 * 
	 */
	private EventListenerHandler eventListenerHandler;

	/**
	 * @param synJobDetail
	 *            the synJobDetail to set
	 */
	public void setSynJobDetail(SyncListener synJobDetail)
	{
		this.synJobDetail = synJobDetail;
	}

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/**
	 * @param uumTempDataService
	 *            the uumTempDataService to set
	 */
	public void setUumTempDataService(UUMTempDataService uumTempDataService)
	{
		this.uumTempDataService = uumTempDataService;
	}

	/**
	 * @param uumDataService
	 *            the uumDataService to set
	 */
	public void setUumDataService(UUMDataService uumDataService)
	{
		this.uumDataService = uumDataService;
	}

	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

	/******************** ERP用户页面开始 ***********/

	/**
	 * 方法说明：ERP用户列表入口
	 * 
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/erpUserMain")
	public String erpUserMainHandler(
			@RequestParam(value = "type", defaultValue = "noMapping", required = false) String type,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		model.addAttribute("loginuser", user);
		model.addAttribute("type", type);
		return "/userTemp/erpUserMain";
	}

	/**
	 * 方法说明：erp无关联门户账号的列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/noMappingUserList")
	public String noMappingUserListHandler(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = uumTempDataService.getUserTempList(page, pagesize,
				UserTempType.noMapping);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/userTemp/noMappingUserList";
	}

	/**
	 * 
	 * 方法说明：erp关联门户账号的列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/mappingUserList")
	public String mappingUserListHandler(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = uumTempDataService.getUserTempList(page, pagesize,
				UserTempType.mapping);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/userTemp/mappingUserList";
	}

	/**
	 * 
	 * 方法说明：erp用户搜索列表
	 * 
	 * @param type
	 *            type
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
	@RequestMapping("/userTemp/erpusersearch")
	public String erpusersearchHandler(
			@RequestParam(value = "type", defaultValue = "erpUser", required = false) String type,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition condition = new Condition();
		if (searchvalue.indexOf("username") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("userid") != -1) {
			condition.setUserId(searchcontent);
		}

		if (StringUtils.isBlank(type)) {
			type = UserTempType.erpUser.toString();
		}
		UserPageResult upr = uumTempDataService.searchUserTempList(page, pagesize, condition,
				UserTempType.valueOf(type));

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		model.addAttribute("type", type);
		return "/userTemp/erpusersearch";
	}

	/**
	 * 
	 * 方法说明：erp用户详细列表
	 * 
	 * @param userUuid
	 *            userUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/erpUserDetail")
	public String erpUserDetailHandler(
			@RequestParam(value = "userUuid", required = false) String userUuid, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		UserTemp user = uumTempDataService.getUserTempByUuid(userUuid);
		List<User> us = uumService.searchUsersByERPCode(user.getUserCode());
		Department dept = uumService.getDepartmentByDeptCode(user.getDeptCode());
		model.addAttribute("us", us);
		model.addAttribute("user", user);
		model.addAttribute("dept", dept);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("isSuper", isModifyGroup(loginUser, model));
		return "/userTemp/erpUserDetail";
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
		if (InitParameters.isCqGroupAuthor()) {
			if (uumService.isUserinSuperGroup(user)) {
				isModifyGroup = true;
			} else {
				isModifyGroup = uumService.getUserGroups(user).contains(
						uumService.getGroupByCode(InitParameters.modifyUserGroupCode()));
			}
			model.addAttribute("isModifyGroup", isModifyGroup);
		}
		return isModifyGroup;
	}

	/******************** ERP用户页面结束 ***********/

	/******************** ERP部门页面开始 ***********/

	/**
	 * 
	 * 方法说明：ERP部门入口
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/erpDepartmentMain")
	public String erpDepartmentMainHandler(ModelMap model)
	{
		User loginuser = uumService.getLoginUser();
		if (loginuser == null) {
			return NOTLOGIN;
		}
		model.addAttribute("loginuser", loginuser);
		return "/deptTemp/erpDepartmentMain";
	}

	/**
	 * 
	 * 方法说明：erp部门列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/erpDepartmentList")
	public String erpDepartmentListHandler(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}

		UserPageResult upr = uumTempDataService.getDepartmentTempList(page, pagesize,
				DeptTempType.noMapping);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("deptlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/deptTemp/erpDepartmentList";
	}

	/**
	 * 
	 * 方法说明：搜索部门
	 * 
	 * @param type
	 *            type
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
	@RequestMapping("/deptTemp/erpDepartmentSearch")
	public String erpDepartmentsearchHandler(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User loginuser = uumService.getLoginUser();
		if (loginuser == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition condition = new Condition();
		if (searchvalue.indexOf("deptname") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("deptcode") != -1) {
			condition.setUserId(searchcontent);
		}

		UserPageResult upr = uumTempDataService.searchDepartmentTempList(page, pagesize, condition,
				DeptTempType.noMapping);

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("deptlist", upr.getList());
		model.addAttribute("user", loginuser);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/deptTemp/erpDepartmentSearch";
	}

	/**
	 * 
	 * 方法说明：部门列表
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/erpDepartmentDetail")
	public String erpDepartmentDetailHandler(
			@RequestParam(value = "deptUuid", required = false) String deptUuid, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		DepartmentTemp deptTemp = uumTempDataService.getDepartmentTempByUuid(deptUuid);
		Department d = uumService.getDepartmentByDeptCode(deptTemp.getDeptCode());
		Department p = uumService.getDepartmentByDeptCode(deptTemp.getParentDeptCode());
		Department o = uumService.getDepartmentByDeptCode(deptTemp.getDeptOrgCode());
		model.addAttribute("department", deptTemp);
		model.addAttribute("dept", d);
		model.addAttribute("parent", p);
		model.addAttribute("org", o);
		return "/deptTemp/erpDepartmentDetail";
	}

	/**
	 * 方法说明：addDepartmentHandler
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/addDepartment")
	public String addDepartmentHandler(
			@RequestParam(value = "deptUuid", required = false) String deptUuid, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		DepartmentTemp deptTemp = uumTempDataService.getDepartmentTempByUuid(deptUuid);
		Department d = uumService.getDepartmentByDeptCode(deptTemp.getDeptCode());
		if (d == null) {
			Department p = uumService.getDepartmentByDeptCode(deptTemp.getParentDeptCode());

			Department dept = new Department();
			dept.setAdminGroups(p.getAdminGroups());
			dept.setCode(deptTemp.getUuid());
			dept.setCurrentAuthorLevel(0l);
			dept.setDeptCode(deptTemp.getDeptCode());
			dept.setDeptParentCode(deptTemp.getParentDeptCode());
			dept.setHasChildren(true);
			dept.setLastUpdateTime(Calendar.getInstance().getTime());
			dept.setModifiedTime(Calendar.getInstance().getTime());
			dept.setName(deptTemp.getDeptName());
			dept.setOrder(uumService.countDepartment() + 1l);
			dept.setOrgCode(deptTemp.getDeptOrgCode());
			dept.setParent(p);
			dept.setParentUUID(p.getParentUUID());
			dept.setPath(p.getPath() + "→" + dept.getName());
			dept.setRtxCode(uumService.countDepartmentForRtx());
			dept.setStatus(ResourceStatus.NORMAL.intValue());
			uumService.createDepartment(dept);

			dept = uumService.getDepartmentByDeptCode(dept.getDeptCode());
			ResourceLog deptLog = new ResourceLog();
			deptLog.setEditPerson(loginUser.getName());
			deptLog.setResourceuuid(dept.getUuid());
			deptLog.setFieldName(dept.getName());
			deptLog.setRemark("新增部门");
			uumService.saveResourceLog(deptLog);

			Event event = eventFactory.createEventCreateDept(dept.getUuid());
			eventListenerHandler.handle(event);
		}

		return "redirect:/deptTemp/erpDepartmentList.nsf";
	}

	/******************** ERP部门页面结束 ***********/

	/******************** ERP用户变更页面开始 ***********/

	/**
	 * 方法说明：userLogMainHandler
	 * 
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/userTempMain")
	public String userLogMainHandler(
			@RequestParam(value = "type", defaultValue = "abnormal", required = false) String type,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		model.addAttribute("loginuser", user);
		model.addAttribute("type", type);
		return "/userTemp/userTempMain";
	}

	/**
	 * 方法说明：userMainHandler
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/userTempList")
	public String userMainHandler(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "type", defaultValue = "abnormal", required = false) String type,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = uumDataService.getUserTempList(page, pagesize, type);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("type", type);
		return "/userTemp/userTempList";
	}

	/**
	 * 方法说明：userTempSearchHandler
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            v
	 * @param type
	 *            type
	 * @param searchvalue
	 *            searchvalue
	 * @param searchcontent
	 *            searchcontent
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/userTempSearch")
	public String userTempSearchHandler(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition condition = new Condition();
		if (searchvalue.indexOf("username") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("userid") != -1) {
			condition.setUserId(searchcontent);
		}

		UserPageResult upr = uumDataService.searchUserTempList(page, pagesize, condition, type);

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("type", type);
		return "/userTemp/userTempSearch";
	}

	/**
	 * 方法说明：userTempDetailHandler
	 * 
	 * @param type
	 *            type
	 * @param userUuid
	 *            userUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/userTempDetail")
	public String userTempDetailHandler(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "userUuid", required = false) String userUuid, ModelMap model)
	{
		UserTempLog user = uumDataService.getUserTempByUuid(userUuid);
		List<ResourceTempDetails> details = uumDataService.getResourceTempDetails(user);
		model.addAttribute("user", user);
		model.addAttribute("details", details);
		model.addAttribute("type", type);
		return "/userTemp/userTempDetail";
	}

	/**
	 * 方法说明：changeUserTempDetailHandler
	 * 
	 * @param type
	 *            type
	 * @param userUuid
	 *            userUuid
	 * @param changeType
	 *            changeType
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/changeUserTempDetail")
	public String changeUserTempDetailHandler(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "userUuid", required = false) String userUuid,
			@RequestParam(value = "changeType", required = false) String changeType, ModelMap model)
	{
		UserTempLog newUser = uumDataService.getUserTempByUuid(userUuid);
		if (newUser.getModifyStatus() != 2L) {
			if (changeType != null && changeType.equals("ignore")) {
				newUser.setModifyStatus(3L);
			} else if (StringUtils.equals(newUser.getModifyType(), "update")) {
				synJobDetail.updateUserHandle(newUser);
			} else if (StringUtils.equals(newUser.getModifyType(), "delete")) {
				synJobDetail.deleteUserHandle(newUser);
			}
		}
		return "redirect:/userTemp/userTempList.nsf?type=" + type;
	}

	/**
	 * 方法说明：userTempListAllPassHandler
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/userTemp/userTempAllPass")
	public String userTempListAllPassHandler(ModelMap model)
	{
		List<UserTempLog> listUser = uumDataService.getAllUserTempListByStatus(1L);
		synJobDetail.UserSyncFrom(listUser);
		return "redirect:/userTemp/userTempList.nsf";
	}

	/******************** ERP用户变更页面结束 ***********/

	/******************** ERP部门变更页面开始 ***********/

	/**
	 * 方法说明：departmentLogMainHandler
	 * 
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/departmentTempMain")
	public String departmentLogMainHandler(
			@RequestParam(value = "type", defaultValue = "abnormal", required = false) String type,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		model.addAttribute("loginuser", user);
		model.addAttribute("type", type);
		return "/deptTemp/departmentTempMain";
	}

	/**
	 * 方法说明：处理ERP新增部门(开始)
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/departmentTempList")
	public String departmentMainHandler(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "type", defaultValue = "abnormal", required = false) String type,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = uumDataService.getDepartmentTempLogList(page, pagesize, type);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("deptlist", upr.getList());
		model.addAttribute("type", type);
		return "/deptTemp/departmentTempList";
	}

	/**
	 * 方法说明：departmentTempSearchHandler
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @param searchvalue
	 *            searchvalue
	 * @param searchcontent
	 *            searchcontent
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/departmentTempSearch")
	public String departmentTempSearchHandler(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition condition = new Condition();
		if (searchvalue.indexOf("departmentname") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("departmentid") != -1) {
			condition.setUserId(searchcontent);
		}

		UserPageResult upr = uumDataService.searchDepartmentTempList(page, pagesize, condition,
				type);

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("deptlist", upr.getList());
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("type", type);
		return "/deptTemp/departmentTempSearch";
	}

	/**
	 * 方法说明：departmentTempDetailHandler
	 * 
	 * @param type
	 *            type
	 * @param deptUuid
	 *            deptUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/departmentTempDetail")
	public String departmentTempDetailHandler(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "deptUuid", required = false) String deptUuid, ModelMap model)
	{
		DepartmentTempLog department = uumDataService.getDepartmentTempByUuid(deptUuid);
		model.addAttribute("department", department);
		model.addAttribute("uumService", uumService);
		model.addAttribute("type", type);
		return "/deptTemp/departmentTempDetail";
	}

	/**
	 * 方法说明：changeDepartmentTempDetailHandler
	 * 
	 * @param type
	 *            type
	 * @param deptUuid
	 *            deptUuid
	 * @param changeType
	 *            changeType
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/changeDepartmentTempDetail")
	public String changeDepartmentTempDetailHandler(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "deptUuid", required = false) String deptUuid,
			@RequestParam(value = "changeType", required = false) String changeType, ModelMap model)
	{
		List<DepartmentTempLog> listDept = new ArrayList<DepartmentTempLog>();
		DepartmentTempLog newDept = uumDataService.getDepartmentTempByUuid(deptUuid);
		if (newDept.getModifyStatus() != 2L) {
			listDept.add(newDept);
			if (changeType != null && changeType.equals("ignore")) {
				newDept.setModifyStatus(3L);
				uumDataService.updateDeptTemp(newDept);
			} else {
				synJobDetail.DepartmentSyncFrom(listDept);
			}
		}
		return "redirect:/deptTemp/departmentTempList.nsf?type=" + type;
	}

	/**
	 * 方法说明：departmentTempListAllPassHandler
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/deptTemp/departmentTempAllPass")
	public String departmentTempListAllPassHandler(ModelMap model)
	{
		Long l = 1L;
		List<DepartmentTempLog> listDept = uumDataService.getAllDepartmentTempListByStatus(l);

		synJobDetail.DepartmentSyncFrom(listDept);
		return "redirect:/deptTemp/departmentTempList.nsf";
	}

	/******************** ERP用户变更页面结束 ***********/

}