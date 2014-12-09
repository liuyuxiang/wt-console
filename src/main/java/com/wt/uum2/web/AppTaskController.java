package com.wt.uum2.web;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wt.uum2.bean.UserListPageBean;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.StringParse;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeValue;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.StringValue;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.UserListService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
@SessionAttributes(types = { UserListPageBean.class })
public class AppTaskController extends BaseController
{
	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * 
	 */
	public UserListService userListService;

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public void setUserListService(UserListService userListService)
	{
		this.userListService = userListService;
	}

	/**
	 * 方法说明：将应用系统的主键放入bean,添加session
	 * 
	 * @param model
	 *            model
	 * @param groupUuid
	 *            groupUuid
	 */
	public void userListPage(ModelMap model, String groupUuid)
	{
		UserListPageBean ulpb = new UserListPageBean();
		if (groupUuid != null) {
			ulpb.setGroupUuid(groupUuid);
		}
		model.put("userListPage", ulpb);
	}

	/**
	 * 方法说明：应用系统授权代办列表
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param ulpb
	 *            ulpb
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/userList")
	public String userListHandler(@RequestParam(value = "id", required = false) String groupUuid,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@ModelAttribute("userListPage") UserListPageBean ulpb, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		List<Group> groups = uumService.getAppGroupsManagedUnderUser(user);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}

		Group group = null;
		if (groupUuid != null || (ulpb != null && ulpb.getGroupUuid() != null)) {
			if (groupUuid == null) {
				groupUuid = ulpb.getGroupUuid();
			}
			userListPage(model, groupUuid);
			group = uumService.getGroupByUuid(groupUuid);
		} else if (CollectionUtils.isNotEmpty(groups)) {
			group = groups.get(0);
		}

		if (group == null) {
			return NOTLOGIN;
		}

		UserPageResult<Object[]> result = uumService.getNormalUsersUnderApplication(page, pagesize,
				group, user);
		model.addAttribute("group", group);
		model.addAttribute("javapage", result.getPager());
		model.addAttribute("userlist", result.getList());
		model.addAttribute("groups", groups);
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/tasklist/userList";
	}

	/**
	 * 方法说明：应用系统过滤列表
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param ulpb
	 *            ulpb
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/userfilterList")
	public String userfilterListHandler(
			@RequestParam(value = "id", required = false) String groupUuid,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@ModelAttribute("userListPage") UserListPageBean ulpb, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		} else {
			List<Group> groups = uumService.getAppGroupsManagedUnderUser(user);
			if (page == null) {
				page = 1;
			}
			if (pagesize == null) {
				pagesize = 15;
			}

			if (groupUuid != null || (ulpb != null && ulpb.getGroupUuid() != null)) {
				if (groupUuid == null) {
					groupUuid = ulpb.getGroupUuid();
				}
				userListPage(model, groupUuid);
				Group group = uumService.getGroupByUuid(groupUuid);
				UserPageResult result = uumService.getFilterUsersUnderApplication(page, pagesize,
						group, user);
				model.addAttribute("group", group);
				model.addAttribute("javapage", result.getPager());
				model.addAttribute("userlist", result.getList());
			} else if (groups != null && groups.size() > 0) {
				Group group = groups.get(0);
				UserPageResult result = uumService.getFilterUsersUnderApplication(page, pagesize,
						group, user);
				model.addAttribute("group", group);
				model.addAttribute("javapage", result.getPager());
				model.addAttribute("userlist", result.getList());
			} else {
				return NOTLOGIN;
			}
			model.addAttribute("groups", groups);
			model.addAttribute("user", user);
			model.addAttribute("uumService", uumService);
		}
		return "/tasklist/userfilterList";
	}

	/**
	 * 方法说明：搜索应用系统过滤列表
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userId
	 *            userId
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
	@RequestMapping("/userfiltersearch")
	public String userfiltersearchHandler(
			@RequestParam(value = "id", required = true) String groupUuid,
			@RequestParam(value = "userId", required = false) String userId,
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
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		List<Group> groups = uumService.getAppGroupsManagedUnderUser(user);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		if (groupUuid != null) {
			Group group = uumService.getGroupByUuid(groupUuid);
			UserPageResult result = uumService.searchFilterUsersUnderApplication(page, pagesize,
					group, user, condition);
			model.addAttribute("group", group);
			model.addAttribute("javapage", result.getPager());
			model.addAttribute("userlist", result.getList());
		} else if (groups != null && groups.size() > 0) {
			Group group = groups.get(0);
			UserPageResult result = uumService.searchFilterUsersUnderApplication(page, pagesize,
					group, user, condition);
			model.addAttribute("group", group);
			model.addAttribute("javapage", result.getPager());
			model.addAttribute("userlist", result.getList());
		} else {
			return NOTLOGIN;
		}
		model.addAttribute("groups", groups);
		model.addAttribute("user", user);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/tasklist/userfiltersearchList";
	}

	/**
	 * 方法说明：给某个过滤人员预授权
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userids
	 *            userids
	 * @param userId
	 *            userId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/authorfilteruser")
	public String authorfilteruserHandler(
			@RequestParam(value = "id", required = true) String groupUuid,
			@RequestParam("userids") String userids,
			@RequestParam(value = "userId", required = false) String userId, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		Group group = uumService.getGroupByUuid(groupUuid);
		String[] userid = userids.split(",");
		User unuser = null;
		for (int i = 0; i < userid.length; i++) {
			unuser = uumService.getUserByUuid(userid[i]);
			uumService.authorUserUnderApplication(unuser, group);
			List<Attribute> attList = uumService.getAttributesUnderResource(unuser, "3");
			for (int j = 0; j < attList.size(); j++) {
				Attribute attribute = attList.get(j);
				if ((attribute.getType().getId().contains("Pwd")
						|| attribute.getType().getId().contains("CqepRzgkPassword") || attribute
						.getType().getId().contains("Account"))
						&& attribute.getType().getId().contains(group.getCode())) {
					if (attribute.getAttValues().size() <= 0) {
						StringValue av = new StringValue();
						av.setAttribute(attribute);
						if (InitParameters.getEncodePassTurnOn().equalsIgnoreCase("false")) {
							if (InitParameters.getPlainPassword() != null
									&& InitParameters.getPlainPassword().equalsIgnoreCase("false")) {
								av.setValue(unuser.getPassword());
							} else {
								av.setValue(unuser.getPlainPassword());
							}
						} else {
							if (InitParameters.getPlainPassword() != null
									&& InitParameters.getPlainPassword().equalsIgnoreCase("false")) {
								av.setValue(StringParse.decryptData(unuser.getPassword()));
							} else {
								av.setValue(StringParse.decryptData(unuser.getPlainPassword()));
							}
						}
						uumService.saveStringValue(av);
					} else {
						Set<AttributeValue> attrvalues = attribute.getAttValues();
						StringValue av = (StringValue) attrvalues.iterator().next();
						av.setAttribute(attribute);
						String appAccount = InitParameters.getAppAccountLocal().split("XXXX")[1];
						if (attribute.getType().getId().contains(appAccount)) {
							av.setValue(unuser.getId());
						}
						uumService.updateStringValue(av);
					}
				}
			}
		}
		return userfilterListHandler(groupUuid, null, null, null, null, model);
	}

	/**
	 * 方法说明：把应用系统中的某人过滤
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userids
	 *            userids
	 * @param userId
	 *            userId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/filteruser")
	public String filteruserHandler(@RequestParam(value = "id", required = true) String groupUuid,
			@RequestParam("userids") String userids,
			@RequestParam(value = "userId", required = false) String userId, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		Group group = uumService.getGroupByUuid(groupUuid);
		String[] userid = userids.split(",");
		User unuser = null;
		for (int i = 0; i < userid.length; i++) {
			unuser = uumService.getUserByUuid(userid[i]);
			uumService.filterUserUnderApplication(unuser, group);
		}
		return userListHandler(groupUuid, null, null, null, null, model);
	}

	/**
	 * 全过滤应用系统中的用户
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userId
	 *            userId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/allfilteruser")
	public String allfilteruserHandler(
			@RequestParam(value = "id", required = true) String groupUuid,
			@RequestParam(value = "userId", required = false) String userId, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null)
			return NOTLOGIN;
		Group group = uumService.getGroupByUuid(groupUuid);
		List<Object[]> userlist = uumService
				.getNormalUsersUnderApplication(1, 9999999, group, user).getList();
		for (int i = 0; i < userlist.size(); i++) {
			User unuser = (User) userlist.get(i)[0];
			uumService.filterUserUnderApplication(unuser, group);
		}
		return userListHandler(groupUuid, null, null, null, null, model);
	}

	/**
	 * 搜索应用代办列表
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userId
	 *            userId
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
	@RequestMapping("/usersearch")
	public String usersearchHandler(@RequestParam(value = "id", required = true) String groupUuid,
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User user = uumService.getUserByUserId(userId);
		if (user == null)
			user = uumService.getLoginUser();
		if (user == null)
			return NOTLOGIN;
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		List<Group> groups = uumService.getAppGroupsManagedUnderUser(user);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		if (groupUuid != null) {
			Group group = uumService.getGroupByUuid(groupUuid);
			UserPageResult result = uumService.searchNormalUsersUnderApplication(page, pagesize,
					group, user, condition);
			model.addAttribute("group", group);
			model.addAttribute("javapage", result.getPager());
			model.addAttribute("userlist", result.getList());
		} else {
			if (groups.size() > 0) {
				Group group = groups.get(0);
				UserPageResult result = uumService.searchNormalUsersUnderApplication(page,
						pagesize, group, user, condition);
				model.addAttribute("group", group);
				model.addAttribute("javapage", result.getPager());
				model.addAttribute("userlist", result.getList());
			} else {
				return NOTLOGIN;
			}
		}
		model.addAttribute("groups", groups);
		model.addAttribute("user", user);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);

		return "/tasklist/usersearchList";
	}

	/**
	 * 方法说明：给某个人员预授权
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userids
	 *            userids
	 * @param userId
	 *            userId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/authoruser")
	public String authoruserHandler(@RequestParam(value = "id", required = true) String groupUuid,
			@RequestParam("userids") String userids,
			@RequestParam(value = "userId", required = false) String userId, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		Group group = uumService.getGroupByUuid(groupUuid);
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			User unuser = uumService.getUserByUuid(userid[i]);
			uumService.authorUserUnderApplication(unuser, group);

		}
		return userListHandler(groupUuid, null, null, null, null, model);
	}

	/**
	 * 方法说明：全授权所有用户
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param userId
	 *            userId
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/allauthoruser")
	public String allauthoruserHandler(
			@RequestParam(value = "id", required = true) String groupUuid,
			@RequestParam(value = "userId", required = false) String userId, ModelMap model)
	{
		User user = uumService.getUserByUserId(userId);
		if (user == null)
			user = uumService.getLoginUser();
		if (user == null)
			return NOTLOGIN;
		Group group = uumService.getGroupByUuid(groupUuid);
		List<Object[]> userlist = uumService
				.getNormalUsersUnderApplication(1, 9999999, group, user).getList();
		User unuser = null;
		for (int i = 0; i < userlist.size(); i++) {
			unuser = (User) userlist.get(i)[0];
			uumService.authorUserUnderApplication(unuser, group);
			List<Attribute> attList = uumService.getAttributesUnderResource(unuser, "3");
			for (int j = 0; j < attList.size(); j++) {
				Attribute attribute = attList.get(j);
				if ((attribute.getType().getId().contains("Pwd") || attribute.getType().getId()
						.contains("CqepRzgkPassword"))
						&& attribute.getType().getId().contains(group.getCode())) {
					if (attribute.getAttValues().size() <= 0) {
						StringValue av = new StringValue();
						av.setAttribute(attribute);
						if (InitParameters.getEncodePassTurnOn().equalsIgnoreCase("false")) {
							if (InitParameters.getPlainPassword() != null
									&& InitParameters.getPlainPassword().equalsIgnoreCase("false")) {
								av.setValue(unuser.getPassword());
							} else {
								av.setValue(unuser.getPlainPassword());
							}
						} else {
							if (InitParameters.getPlainPassword() != null
									&& InitParameters.getPlainPassword().equalsIgnoreCase("false")) {
								av.setValue(StringParse.decryptData(unuser.getPassword()));
							} else {
								av.setValue(StringParse.decryptData(unuser.getPlainPassword()));
							}
						}
						uumService.saveStringValue(av);
					}
				}
			}
		}
		return userListHandler(groupUuid, null, null, null, null, model);
	}

	/**
	 * 方法说明：应用系统主入口
	 * 
	 * @param userid
	 *            userid
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/appMain")
	public String appMainHandler(@RequestParam("userId") String userid,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		if (uumService.getLoginUser() == null)
			return NOTLOGIN;
		if (type == null) {
			type = "userList";
			userListPage(model, null);
		}
		model.addAttribute("type", type);
		model.addAttribute("userId", userid);
		model.addAttribute("loginuser", uumService.getLoginUser());
		model.addAttribute("status", uumService.isAppAssessor(uumService.getLoginUser()));
		return "/tasklist/appMain";
	}

}