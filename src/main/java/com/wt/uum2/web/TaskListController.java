package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nak.nsf.pager.Pager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UUMDateFormat;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.AttributeValue;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.ResourceLog;
import com.wt.uum2.domain.StringValue;
import com.wt.uum2.domain.SyncED;
import com.wt.uum2.domain.TaskList;
import com.wt.uum2.domain.User;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.TaskListService;
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
public class TaskListController extends BaseController
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
	private EventFactory eventFactory;
	/**
	 * 
	 */
	private EventListenerHandler eventListenerHandler;

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

	public void setTaskListService(TaskListService taskListService)
	{
		this.taskListService = taskListService;
	}

	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

	/**
	 * 用户审核列表
	 * 
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/userInitList")
	public String userInitListHandler(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getUserByUserId(userId);
		if (user == null)
			user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		} else {

			if (page == null) {
				page = 1;
			}
			if (pagesize == null) {
				pagesize = 15;
			}
			UserPageResult result = uumService
					.getCreatedUsersManagedUnderUser(page, pagesize, user);
			model.addAttribute("javapage", result.getPager());
			model.addAttribute("userlist", result.getList());
			model.addAttribute("user", user);
			model.addAttribute("uumService", uumService);
			if (InitParameters.getMacroUserList() != null
					&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
				userListService.putAll2ModelMap(result.getList(), result.getPager().getDataStart(),
						model);
				model.addAttribute("macro", "true");
			}

		}
		return "/tasklist/userInitList";
	}

	/**
	 * 退回
	 * 
	 * @param userId
	 *            userId
	 * @param userids
	 *            userids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/rollback")
	public String rollbackHandler(@RequestParam(value = "userId", required = false) String userId,
			@RequestParam("userids") String userids, ModelMap model)
	{
		User user = uumService.getUserByUserId(userId);
		if (user == null)
			user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		} else {
			String[] userid = userids.split(",");
			User unuser = null;
			for (int i = 0; i < userid.length; i++) {
				unuser = uumService.getUserByUuid(userid[i]);
				Event event = eventFactory.createEventDeleteUser(unuser.getUuid());
				uumService.rollbackUser(unuser);
				if (unuser.getStatus() == ResourceStatus.ROLLBACK.intValue()) {
					eventListenerHandler.handle(event);
				}
			}
			UserPageResult result = uumService.getCreatedUsersManagedUnderUser(1, 15, user);
			model.addAttribute("javapage", result.getPager());
			model.addAttribute("userlist", result.getList());
			model.addAttribute("user", user);
			model.addAttribute("uumService", uumService);
			if (InitParameters.getMacroUserList() != null
					&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
				userListService.putAll2ModelMap(result.getList(), result.getPager().getDataStart(),
						model);
				model.addAttribute("macro", "true");
			}

		}
		return "/tasklist/userInitList";
	}

	/**
	 * 方法说明：
	 * 
	 * @param userId
	 *            userId
	 * @param userids
	 *            userids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/pass")
	public String passHandler(@RequestParam(value = "userId", required = false) String userId,
			@RequestParam("userids") String userids, ModelMap model)
	{
		User user = uumService.getUserByUserId(userId);
		if (user == null) {
			user = uumService.getLoginUser();
		}
		if (user == null) {
			return NOTLOGIN;
		}
		String[] userid = userids.split(",");
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < userid.length; i++) {
			Map<String, String[]> map = new HashMap<String, String[]>();
			List<Event> events = new ArrayList<Event>();
			User unuser = uumService.getUserByUuid(userid[i]);
			uumService.authorUser(unuser);
			if (unuser.getCurrentAuthorLevel() < 1) {
				// 审核通过其实只是改变的logindisabled的属性值,所以此处将原有的属性代码都删除,仅更新两个属性
				List<Group> appGroups = uumService.getDefaultAddAppGroup();
				List<String> keys = new ArrayList<String>();
				for (int k = 0; k < appGroups.size(); k++) {
					String key = appGroups.get(k).getCode() + "LoginEnable";
					keys.add(key);
				}
				String appStatusCode = InitParameters.getAppStatusCode().split("XXXX")[1];
				keys.add(appStatusCode);
				keys.add("loginDisabled");
				List<Attribute> attList = uumService
						.getAttributesByAttributeTypeIdKey(unuser, keys);
				for (int j = 0; j < attList.size(); j++) {
					Attribute attribute = attList.get(j);
					for (int k = 0; k < appGroups.size(); k++) {
						String key = appGroups.get(k).getCode() + "LoginEnable";
						if (attribute.getType().getId().contains(key)) {
							StringValue av = new StringValue();
							if (attribute.getAttValues().size() <= 0) {
								av.setAttribute(attribute);
								av.setValue("true");
								uumService.saveStringValue(av);
							} else {
								av = (StringValue) attribute.getAttValues().iterator().next();
								av.setAttribute(attribute);
								av.setValue("true");
								uumService.updateStringValue(av);
							}
							map.put(attribute.getType().getId(),
									new String[] { Boolean.FALSE.toString(), av.getValue() });
						}
					}
					if (attribute.getType().getId().contains("loginDisabled")) {
						StringValue av = new StringValue();
						if (attribute.getAttValues().size() <= 0) {
							av.setAttribute(attribute);
							av.setValue("false");
							uumService.saveStringValue(av);
						} else {
							av = (StringValue) attribute.getAttValues().iterator().next();
							av.setAttribute(attribute);
							if (av.getValue() != null && av.getValue().equalsIgnoreCase("true")) {
								av.setValue("false");
								uumService.updateStringValue(av);
							}
						}
						map.put(attribute.getType().getId(), new String[] {
								Boolean.TRUE.toString(), av.getValue() });
					}
					if (attribute.getType().getId().contains("pwdChangeTime")) {
						StringValue av = new StringValue();
						if (attribute.getAttValues().size() <= 0) {
							av.setAttribute(attribute);
							UUMDateFormat df = new UUMDateFormat();
							av.setValue(df.switchLongToDateFormat(System.currentTimeMillis()));
							uumService.saveStringValue(av);
						} else {
							av = (StringValue) attribute.getAttValues().iterator().next();
							av.setAttribute(attribute);
							UUMDateFormat df = new UUMDateFormat();
							av.setValue(df.switchLongToDateFormat(System.currentTimeMillis()));
							uumService.updateStringValue(av);
						}
						map.put(attribute.getType().getId(), new String[] { "", av.getValue() });
					}
				}
				list.add(unuser);
				List<Group> grouplist = uumService.getUserGroups(unuser);
				if (!map.isEmpty()) {
					events.add(eventFactory.createEventUpdateUser(unuser.getUuid(), map));
				}
				if (grouplist != null && !grouplist.isEmpty()) {
					List<String> groupUUIDs = new ArrayList<String>();
					for (Group group : grouplist) {
						groupUUIDs.add(group.getUuid());
					}
					events.add(eventFactory.createUserEventAddGroup(unuser.getUuid(), groupUUIDs));
				}
				if (!events.isEmpty()) {
					eventListenerHandler.handle(events);
				}
			}
		}
		UserPageResult result = uumService.getCreatedUsersManagedUnderUser(1, 15, user);
		model.addAttribute("javapage", result.getPager());
		model.addAttribute("userlist", result.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(result.getList(), result.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/tasklist/userInitList";
	}

	/**
	 * 方法说明：用户物理删除
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/delMain")
	public String deleteMainHandler(@RequestParam("userId") String userid, ModelMap model)
	{
		User user = uumService.getUserByUserId(userid);
		if (user == null)
			user = uumService.getLoginUser();
		if (user == null)
			return NOTLOGIN;
		model.addAttribute("userId", user.getId());
		model.addAttribute("loginuser", user);
		model.addAttribute("status", uumService.isAppAssessor(user));
		return "/tasklist/delMain";
	}

	/**
	 * 方法说明：deleteUserListHandler
	 * 
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/deluserList")
	public String deleteUserListHandler(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getUserByUserId(userId);
		if (user == null)
			user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		} else {
			if (page == null) {
				page = 1;
			}
			if (pagesize == null) {
				pagesize = 15;
			}
			UserPageResult upr = uumService.getLogicDeleteUsers(page, pagesize);
			model.addAttribute("javapage", upr.getPager());
			model.addAttribute("userlist", upr.getList());
			model.addAttribute("user", user);
			model.addAttribute("uumService", uumService);
			if (InitParameters.getMacroUserList() != null
					&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
				userListService
						.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
				model.addAttribute("macro", "true");
			}
		}
		return "/tasklist/deluserList";
	}

	/**
	 * 方法说明：rollbackDeleteUserListHandler
	 * 
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
	@RequestMapping("/tasklist/rollbackdeluser")
	public String rollbackDeleteUserListHandler(@RequestParam("userids") String userids,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i]);
			Map<String, String[]> map = new HashMap<String, String[]>();
			// 设置可以登录门户状态
			// List<Group> appGroups = uumService.getDefaultAddAppGroup();
			List<String> keys = new ArrayList<String>();
			// for(int k=0;k<appGroups.size();k++){
			// String key = appGroups.get(k).getCode()+"LoginEnable";
			// keys.add(key);
			// }
			keys.add("loginDisabled");
			List<Attribute> attList = uumService.getAttributesByAttributeTypeIdKey(user, keys);
			if (attList != null) {
				for (int j = 0; j < attList.size(); j++) {
					Attribute attribute = attList.get(j);
					if (attribute.getType().getId().contains("loginDisabled")) {
						attribute.setValue(Boolean.FALSE.toString());
						map.put(attribute.getType().getId(), new String[] {
								Boolean.TRUE.toString(), attribute.getValue() });
						uumService.updateAttribute(attribute);
						// Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
						// while (iterator.hasNext()) {
						// StringValue av = (StringValue) iterator.next();
						// av.setValue(Boolean.FALSE.toString());
						// map.put(attribute.getType().getId(),
						// new String[] { Boolean.TRUE.toString(), av.getValue() });
						// uumService.updateStringValue(av);
						// }
					}
				}
			}
			uumService.restoreUser(user);
			if (InitParameters.isPlainPassword()) {
				map.put("userPassword", new String[] { null, user.getPlainPassword() });
			} else {
				map.put("userPassword", new String[] { null, user.getPassword() });
			}
			Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
			eventListenerHandler.handle(event);
		}

		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = uumService.getLogicDeleteUsers(page, pagesize);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", loginUser);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		return "/tasklist/deluserList";
	}

	/**
	 * 方法说明：deleteUserHandler
	 * 
	 * @param userId
	 *            userId
	 * @param userids
	 *            userids
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/deleteUser")
	public String deleteUserHandler(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam("userids") String userids,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		User user = uumService.getUserByUserId(userId);
		if (user == null)
			user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		String[] useridlist = userids.split(",");
		if (useridlist.length > 0) {
			for (int i = 0; i < useridlist.length; i++) {
				User u = uumService.getUserByUuid(useridlist[i].trim());
				Event event = eventFactory.createEventDeleteUser(u.getUuid());
				uumService.deleteUserWL(u);
				eventListenerHandler.handle(event);
			}
		}

		String action = null;
		UserPageResult upr = null;

		if (StringUtils.equals(type, "left")) {
			action = "/tasklist/leftUserList";
			upr = uumService.getLeftUsers(1, 15);
		} else if (StringUtils.equals(type, "retired")) {
			action = "/tasklist/retiredUserList";
			;
			upr = uumService.getRetiredUsers(1, 15);
		} else {
			upr = uumService.getLogicDeleteUsers(1, 15);
			action = "/tasklist/deluserList";
		}

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}

		return action;
	}

	/**
	 * 方法说明：deleteUserSearchHandler
	 * 
	 * @param id
	 *            id
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
	@RequestMapping("/tasklist/deleteusersearch")
	public String deleteUserSearchHandler(@RequestParam("id") String id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		User user = uumService.getUserByUserId(id);
		Condition condition = new Condition();
		if (searchvalue.indexOf("username") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("userid") != -1) {
			condition.setUserId(searchcontent);
		}

		UserPageResult upr = uumService.searchLogicDeleteUsers(page, pagesize, condition);

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		return "/tasklist/delsearch";
	}

	// 离职人员列表///////////////////////////

	/**
	 * 方法说明：离职人员列表
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/leftUserMain")
	public String leaveMainHandler(@RequestParam("userId") String userid, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		model.addAttribute("userId", user.getId());
		model.addAttribute("loginuser", user);
		model.addAttribute("status", uumService.isAppAssessor(user));
		return "/tasklist/leftUserMain";
	}

	/**
	 * 方法说明：leftUserListHandler
	 * 
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/leftUserList")
	public String leftUserListHandler(
			@RequestParam(value = "userId", required = false) String userId,
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
		UserPageResult upr = uumService.getLeftUsers(page, pagesize);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/tasklist/leftUserList";
	}

	/**
	 * 方法说明：leaveUserSearchHandler
	 * 
	 * @param id
	 *            id
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
	@RequestMapping("/tasklist/leftUserSearch")
	public String leaveUserSearchHandler(@RequestParam("id") String id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		User user = uumService.getUserByUserId(id);
		Condition condition = new Condition();
		if (searchvalue.indexOf("username") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("userid") != -1) {
			condition.setUserId(searchcontent);
		}

		UserPageResult upr = uumService.searchLeftUsers(page, pagesize, condition);

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/tasklist/leftUserSearch";
	}

	/**
	 * 方法说明：leftUserRollbackHandler
	 * 
	 * @param userids
	 *            userids
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
	@RequestMapping("/tasklist/leftUserRollback")
	public String leftUserRollbackHandler(@RequestParam("userids") String userids,
			@RequestParam(value = "type", required = false) String type,
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
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i]);
			if (InitParameters.isPlainPassword()) {
				user.setPlainPassword(InitParameters.getUserPassword());
			} else {
				user.setPassword(InitParameters.getUserPassword());
			}
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("userPassword", new String[] { null, user.getPlainPassword() });

			List<AttributeType> attrs = uumService.getAttributeTypeById("loginDisabled");
			if (CollectionUtils.isNotEmpty(attrs) && attrs.get(0) != null) {
				List<Attribute> as = uumService.getAttributesByResAndType(user, attrs.get(0));
				if (CollectionUtils.isNotEmpty(as)) {
					for (Attribute attribute : as) {
						Set<AttributeValue> av = attribute.getAttValues();
						if (CollectionUtils.isNotEmpty(av)) {
							for (AttributeValue attributeValue : av) {
								StringValue s = (StringValue) attributeValue;
								s.setValue(Boolean.FALSE.toString());
								map.put(attribute.getType().getId(),
										new String[] { Boolean.TRUE.toString(), s.getValue() });
								uumService.updateStringValue(s);
							}
						}
					}
				}
			}
			user.setStatus(ResourceStatus.NORMAL.intValue());
			uumService.updateUser(user);
			if(!StringUtils.equals(ProjectResolver.getId(), "hlx")){
				Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
				eventListenerHandler.handle(event);
			}
		}
		if (type != null) {
			return leaveUserSearchHandler(loginUser.getId(), page, pagesize, searchvalue,
					searchcontent, model);
		} else {
			return leftUserListHandler(loginUser.getId(), page, pagesize, model);
		}
	}

	// 离职人员列表///////////////////////////

	// 退休人员列表///////////////////////////

	/**
	 * 方法说明：退休人员列表
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/retiredUserMain")
	public String retiredUserMainHandler(@RequestParam("userId") String userid, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		model.addAttribute("userId", user.getId());
		model.addAttribute("loginuser", user);
		model.addAttribute("status", uumService.isAppAssessor(user));
		return "/tasklist/retiredUserMain";
	}

	/**
	 * 方法说明：retiredUserListHandler
	 * 
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/retiredUserList")
	public String retiredUserListHandler(
			@RequestParam(value = "userId", required = false) String userId,
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
		UserPageResult upr = uumService.getRetiredUsers(page, pagesize);
		// UserPageResult upr = uumService.getVoidUsers(page, pagesize);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/tasklist/retiredUserList";
	}

	/**
	 * 方法说明：retiredUserSearchHandler
	 * 
	 * @param id
	 *            id
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
	@RequestMapping("/tasklist/retiredUserSearch")
	public String retiredUserSearchHandler(@RequestParam("id") String id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		User user = uumService.getUserByUserId(id);
		Condition condition = new Condition();
		if (searchvalue.indexOf("username") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.indexOf("userid") != -1) {
			condition.setUserId(searchcontent);
		}

		UserPageResult upr = uumService.searchRetiredUsers(page, pagesize, condition);

		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/tasklist/retiredUserSearch";
	}

	/**
	 * 方法说明：retiredUserRollbackHandler
	 * 
	 * @param userids
	 *            userids
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
	@RequestMapping("/tasklist/retiredUserRollback")
	public String retiredUserRollbackHandler(@RequestParam("userids") String userids,
			@RequestParam(value = "type", required = false) String type,
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
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i]);
			if (InitParameters.isPlainPassword()) {
				user.setPlainPassword(InitParameters.getUserPassword());
			} else {
				user.setPassword(InitParameters.getUserPassword());
			}
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("userPassword", new String[] { null, user.getPlainPassword() });

			List<AttributeType> attrs = uumService.getAttributeTypeById("loginDisabled");
			if (CollectionUtils.isNotEmpty(attrs) && attrs.get(0) != null) {
				List<Attribute> as = uumService.getAttributesByResAndType(user, attrs.get(0));
				if (CollectionUtils.isNotEmpty(as)) {
					for (Attribute attribute : as) {
						Set<AttributeValue> av = attribute.getAttValues();
						if (CollectionUtils.isNotEmpty(av)) {
							for (AttributeValue attributeValue : av) {
								StringValue s = (StringValue) attributeValue;
								s.setValue(Boolean.FALSE.toString());
								map.put(attribute.getType().getId(),
										new String[] { Boolean.TRUE.toString(), s.getValue() });
								uumService.updateStringValue(s);
							}
						}
					}
				}
			}
			user.setStatus(ResourceStatus.NORMAL.intValue());
			uumService.updateUser(user);
			Event event = eventFactory.createEventUpdateUser(user.getUuid(), map);
			eventListenerHandler.handle(event);
		}
		if (type != null) {
			return retiredUserSearchHandler(loginUser.getId(), page, pagesize, searchvalue,
					searchcontent, model);
		} else {
			return retiredUserListHandler(loginUser.getId(), page, pagesize, model);
		}
	}

	// 离职人员列表///////////////////////////

	/**
	 * 方法说明：用户审核主入口
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/userMain")
	public String userMainHandler(@RequestParam("userId") String userid, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		model.addAttribute("userId", userid);
		model.addAttribute("loginuser", loginUser);
		model.addAttribute("status", uumService.isUserAssessor(loginUser));
		return "/tasklist/userMain";
	}

	/**
	 * 方法说明：部门审核主入口
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/deptMain")
	public String deptMainHandler(@RequestParam("userId") String userid, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		model.addAttribute("userId", userid);
		model.addAttribute("loginuser", uumService.getLoginUser());
		model.addAttribute("status", uumService.isUserAssessor(uumService.getLoginUser()));
		return "/tasklist/deptMain";
	}

	/**
	 * 方法说明：部门审核列表
	 * 
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/deptInitList")
	public String deptInitListHandler(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getUserByUserId(userId);
		if (user == null) {
			return NOTLOGIN;
		} else {

			if (page == null) {
				page = 1;
			}
			if (pagesize == null) {
				pagesize = 15;
			}
			UserPageResult<Department> upr = uumService.getCreatedDepartmentsManagedUnderUser(page,
					pagesize, user);
			List<Department> deptlist = upr.getList();
			Pager javapage = upr.getPager();
			model.addAttribute("javapage", javapage);
			model.addAttribute("deptlist", deptlist);
			model.addAttribute("user", user);
			model.addAttribute("uumService", uumService);

		}
		return "/tasklist/deptInitList";
	}

	/**
	 * 方法说明：passdeptHandler
	 * 
	 * @param userId
	 *            userId
	 * @param deptids
	 *            deptids
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/passdept")
	public String passdeptHandler(@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "deptids", required = false) String deptids,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		String[] deptcode = deptids.split(",");
		List<Department> listDepartment = new ArrayList<Department>();
		for (int i = 0; i < deptcode.length; i++) {
			String dcode = deptcode[i];
			Department department = uumService.getDepartmentByDeptCode(dcode);
			uumService.authorDepartment(department);
			listDepartment.add(department);
			Event event = eventFactory.createEventCreateDept(department.getUuid());
			eventListenerHandler.handle(event);

		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<Department> upr = uumService.getCreatedDepartmentsManagedUnderUser(page,
				pagesize, user);
		// .getList();
		// Pager javapage = uumService.getCreatedDepartmentsManagedUnderUser(page,
		// pagesize, user).getPager();
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("deptlist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/tasklist/deptInitList";
	}

	/**
	 * 方法说明：rollbackdeptHandler
	 * 
	 * @param userId
	 *            userId
	 * @param deptids
	 *            deptids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/rollbackdept")
	public String rollbackdeptHandler(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam("deptids") String deptids, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		String[] deptcode = deptids.split(",");
		for (int i = 0; i < deptcode.length; i++) {
			String dcode = deptcode[i];
			Department department = uumService.getDepartmentByDeptCode(dcode);
			uumService.rollbackDepartment(department);
		}
		List<Department> deptlist = uumService.getCreatedDepartmentsManagedUnderUser(1, 15, user)
				.getList();
		Pager javapage = uumService.getCreatedDepartmentsManagedUnderUser(1, 15, user).getPager();
		model.addAttribute("javapage", javapage);
		model.addAttribute("deptlist", deptlist);
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/tasklist/deptInitList";
	}

	/**
	 * 方法说明：部门永久删除列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/deptDelLog")
	public String deptDelLogHandler(@RequestParam(value = "page", required = false) Integer page,
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
		List<ResourceLog> deptLoglist = uumService.getDelResourceLogs(page, pagesize, 2).getList();
		Pager javapage = uumService.getDelResourceLogs(page, pagesize, 2).getPager();
		model.addAttribute("logpage", javapage);
		model.addAttribute("loglist", deptLoglist);
		return "/tasklist/deptDelLog";
	}

	/**
	 * 角色永久删除列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/groupDelLog")
	public String groupDelLogHandler(@RequestParam(value = "page", required = false) Integer page,
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
		List<ResourceLog> groupLoglist = uumService.getDelResourceLogs(page, pagesize, 1).getList();
		Pager javapage = uumService.getDelResourceLogs(page, pagesize, 1).getPager();
		model.addAttribute("logpage", javapage);
		model.addAttribute("loglist", groupLoglist);

		return "/tasklist/groupDelLog";
	}

	/**
	 * 用户永久删除列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/tasklist/userDelLog")
	public String userDelLogHandler(@RequestParam(value = "page", required = false) Integer page,
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

		List<ResourceLog> userLoglist = uumService.getDelResourceLogs(page, pagesize, 0).getList();
		Pager javapage = uumService.getDelResourceLogs(page, pagesize, 0).getPager();
		model.addAttribute("logpage", javapage);
		model.addAttribute("loglist", userLoglist);
		return "/tasklist/userDelLog";
	}

	/**
	 * 方法说明：处理同步源(开始)
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/sync/synMain")
	public String synMainHandler(@RequestParam("userId") String userid, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		model.addAttribute("userId", userid);
		model.addAttribute("loginuser", uumService.getLoginUser());
		return "/sync/synMain";
	}

	/**
	 * 方法说明：synListHandler
	 * 
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/sync/synList")
	public String synListHandler(@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		} else {
			if (page == null) {
				page = 1;
			}
			if (pagesize == null) {
				pagesize = 15;
			}
			UserPageResult upr = uumService.getSyncED(page, pagesize);
			model.addAttribute("javapage", upr.getPager());
			model.addAttribute("synclist", upr.getList());
			model.addAttribute("user", user);
			model.addAttribute("uumService", uumService);
		}
		return "/sync/synList";
	}

	/**
	 * 方法说明：synDetailHandler
	 * 
	 * @param syncUuid
	 *            syncUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/sync/synDetail")
	public String synDetailHandler(
			@RequestParam(value = "syncUuid", required = false) String syncUuid, ModelMap model)
	{
		SyncED syncED = uumService.getSyncEDByUuid(syncUuid);
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		} else {
			model.addAttribute("syncED", syncED);
			model.addAttribute("uumService", uumService);
		}
		return "/sync/synDetail";
	}

	/**
	 * 方法说明：updateSyncHandler
	 * 
	 * @param ldapFactory
	 *            ldapFactory
	 * @param ldapUserName
	 *            ldapUserName
	 * @param ldapUserPassWord
	 *            ldapUserPassWord
	 * @param ldapUrl
	 *            ldapUrl
	 * @param baseDN
	 *            baseDN
	 * @param userDN
	 *            userDN
	 * @param groupDN
	 *            groupDN
	 * @param deptDN
	 *            deptDN
	 * @param userKey
	 *            userKey
	 * @param groupKey
	 *            groupKey
	 * @param deptKey
	 *            deptKey
	 * @param userObjectClass
	 *            userObjectClass
	 * @param deptObjectClass
	 *            deptObjectClass
	 * @param groupObjectClass
	 *            groupObjectClass
	 * @param dBdeptRoot
	 *            dBdeptRoot
	 * @param syncUuid
	 *            syncUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/sync/updateSync")
	public String updateSyncHandler(
			@RequestParam(value = "ldapFactory", required = false) String ldapFactory,
			@RequestParam(value = "ldapUserName", required = false) String ldapUserName,
			@RequestParam(value = "ldapUserPassWord", required = false) String ldapUserPassWord,
			@RequestParam(value = "ldapUrl", required = false) String ldapUrl,
			@RequestParam(value = "baseDN", required = false) String baseDN,
			@RequestParam(value = "userDN", required = false) String userDN,
			@RequestParam(value = "groupDN", required = false) String groupDN,
			@RequestParam(value = "deptDN", required = false) String deptDN,
			@RequestParam(value = "userKey", required = false) String userKey,
			@RequestParam(value = "groupKey", required = false) String groupKey,
			@RequestParam(value = "deptKey", required = false) String deptKey,
			@RequestParam(value = "userObjectClass", required = false) String userObjectClass,
			@RequestParam(value = "deptObjectClass", required = false) String deptObjectClass,
			@RequestParam(value = "groupObjectClass", required = false) String groupObjectClass,
			@RequestParam(value = "DBdeptRoot", required = false) String dBdeptRoot,
			@RequestParam(value = "syncUuid", required = false) String syncUuid, ModelMap model)
	{
		SyncED syncED = uumService.getSyncEDByUuid(syncUuid);
		syncED.setBaseDN(baseDN);
		syncED.setDbDeptRoot(dBdeptRoot);
		syncED.setDeptDN(deptDN);
		syncED.setDeptKey(deptKey);
		syncED.setDeptObjectClass(deptObjectClass);
		syncED.setGroupDN(groupDN);
		syncED.setGroupKey(groupKey);
		syncED.setGroupObjectClass(groupObjectClass);
		syncED.setLdapFactory(ldapFactory);
		syncED.setLdapUrl(ldapUrl);
		syncED.setLdapUserName(ldapUserName);
		syncED.setLdapUserPassWord(ldapUserPassWord);
		syncED.setUserDN(userDN);
		syncED.setUserKey(userKey);
		syncED.setUserObjectClass(userObjectClass);

		uumService.updateSyncED(syncED);

		User user = uumService.getLoginUser();
		UserPageResult upr = uumService.getSyncED(1, 15);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("synclist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/sync/synList";
	}

	/**
	 * 方法说明：synAddHandler
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/sync/synAdd")
	public void synAddHandler(ModelMap model)
	{
	}

	/**
	 * 方法说明：addSyncfromHandler
	 * 
	 * @param ldapFactory
	 *            ldapFactory
	 * @param ldapUserName
	 *            ldapUserName
	 * @param ldapUserPassWord
	 *            ldapUserPassWord
	 * @param ldapUrl
	 *            ldapUrl
	 * @param baseDN
	 *            baseDN
	 * @param userDN
	 *            userDN
	 * @param groupDN
	 *            groupDN
	 * @param deptDN
	 *            deptDN
	 * @param userKey
	 *            userKey
	 * @param groupKey
	 *            groupKey
	 * @param deptKey
	 *            deptKey
	 * @param userObjectClass
	 *            userObjectClass
	 * @param deptObjectClass
	 *            deptObjectClass
	 * @param groupObjectClass
	 *            groupObjectClass
	 * @param dBdeptRoot
	 *            dBdeptRoot
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/sync/addSyncfrom")
	public String addSyncfromHandler(
			@RequestParam(value = "ldapFactory", required = false) String ldapFactory,
			@RequestParam(value = "ldapUserName", required = false) String ldapUserName,
			@RequestParam(value = "ldapUserPassWord", required = false) String ldapUserPassWord,
			@RequestParam(value = "ldapUrl", required = false) String ldapUrl,
			@RequestParam(value = "baseDN", required = false) String baseDN,
			@RequestParam(value = "userDN", required = false) String userDN,
			@RequestParam(value = "groupDN", required = false) String groupDN,
			@RequestParam(value = "deptDN", required = false) String deptDN,
			@RequestParam(value = "userKey", required = false) String userKey,
			@RequestParam(value = "groupKey", required = false) String groupKey,
			@RequestParam(value = "deptKey", required = false) String deptKey,
			@RequestParam(value = "userObjectClass", required = false) String userObjectClass,
			@RequestParam(value = "deptObjectClass", required = false) String deptObjectClass,
			@RequestParam(value = "groupObjectClass", required = false) String groupObjectClass,
			@RequestParam(value = "DBdeptRoot", required = false) String dBdeptRoot, ModelMap model)
	{
		SyncED syncED = new SyncED();
		syncED.setBaseDN(baseDN);
		syncED.setDbDeptRoot(dBdeptRoot);
		syncED.setDeptDN(deptDN);
		syncED.setDeptKey(deptKey);
		syncED.setDeptObjectClass(deptObjectClass);
		syncED.setGroupDN(groupDN);
		syncED.setGroupKey(groupKey);
		syncED.setGroupObjectClass(groupObjectClass);
		syncED.setLdapFactory(ldapFactory);
		syncED.setLdapUrl(ldapUrl);
		syncED.setLdapUserName(ldapUserName);
		syncED.setLdapUserPassWord(ldapUserPassWord);
		syncED.setUserDN(userDN);
		syncED.setUserKey(userKey);
		syncED.setUserObjectClass(userObjectClass);
		uumService.saveSyncED(syncED);

		User user = uumService.getLoginUser();

		UserPageResult upr = uumService.getSyncED(1, 15);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("synclist", upr.getList());
		model.addAttribute("user", user);
		return "/sync/synList";
	}

	/**
	 * 方法说明：deleteSyncHandler
	 * 
	 * @param syncUuids
	 *            syncUuids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/sync/deleteSync")
	public String deleteSyncHandler(@RequestParam("syncUuids") String syncUuids, ModelMap model)
	{

		String[] syncUuidlist = syncUuids.split(",");
		List<SyncED> listSync = new ArrayList<SyncED>();
		if (syncUuidlist.length > 0) {
			for (int i = 0; i < syncUuidlist.length; i++) {
				listSync.add(uumService.getSyncEDByUuid(syncUuidlist[i].trim()));
			}
			for (int i = 0; i < listSync.size(); i++) {
				SyncED syncED = listSync.get(i);
				uumService.deleteSyncED(syncED);
			}
		}
		User user = uumService.getLoginUser();
		UserPageResult upr = uumService.getSyncED(1, 15);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("synclist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/sync/synList";
	}

	/**
	 * 处理同步源(结束)
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 */

	/**
	 * 处理待办候选项(开始)
	 * 
	 * @param userid
	 *            userid
	 * @param model
	 *            model
	 */
	@RequestMapping("/taskCandidate/taskCandidateMain")
	public void taskCandidateMainHandler(@RequestParam("userId") String userid, ModelMap model)
	{
		model.addAttribute("userId", userid);
		model.addAttribute("loginuser", uumService.getLoginUser());
	}

	/**
	 * 方法说明：taskCandidateListHandler
	 * 
	 * @param userId
	 *            userId
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/taskCandidate/taskCandidateList")
	public String taskCandidateListHandler(
			@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		} else {
			if (page == null) {
				page = 1;
			}
			if (pagesize == null) {
				pagesize = 15;
			}
			UserPageResult upr = taskListService.getTaskList(page, pagesize);
			model.addAttribute("javapage", upr.getPager());
			model.addAttribute("tasklist", upr.getList());
			model.addAttribute("user", user);
		}
		return "/taskCandidate/taskCandidateList";
	}

	/**
	 * 方法说明：taskCandidateDetailHandler
	 * 
	 * @param taskCandidateUuid
	 *            taskCandidateUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/taskCandidate/taskCandidateDetail")
	public String taskCandidateDetailHandler(
			@RequestParam(value = "taskCandidateUuid", required = false) String taskCandidateUuid,
			ModelMap model)
	{
		TaskList taskList = taskListService.getTaskListByUuid(taskCandidateUuid);
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		} else {
			model.addAttribute("taskList", taskList);
		}
		return "/taskCandidate/taskCandidateDetail";
	}

	/**
	 * 方法说明：updateTaskCandidateHandler
	 * 
	 * @param id
	 *            id
	 * @param linkName
	 *            linkName
	 * @param linkUrl
	 *            linkUrl
	 * @param linkOrder
	 *            linkOrder
	 * @param taskCandidateUuid
	 *            taskCandidateUuid
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/taskCandidate/updateTaskCandidate")
	public String updateTaskCandidateHandler(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "linkName", required = false) String linkName,
			@RequestParam(value = "linkUrl", required = false) String linkUrl,
			@RequestParam(value = "linkOrder", required = false) String linkOrder,
			@RequestParam(value = "taskCandidateUuid", required = false) String taskCandidateUuid,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		TaskList taskList = taskListService.getTaskListByUuid(taskCandidateUuid);
		taskList.setId(id);
		taskList.setLinkName(linkName);
		taskList.setLinkUrl(linkUrl);
		taskList.setLinkOrder(Integer.parseInt(linkOrder));
		Set<Group> adminGroups = new HashSet<Group>();
		if (jsgroupuuid != null) {
			String[] jsgroupuuids = jsgroupuuid.split(",");
			List<Group> grouplist = new ArrayList<Group>();
			if (jsgroupuuid.length() > 0) {
				for (int i = 0; i < jsgroupuuids.length; i++) {
					grouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
				}
			}
			if (grouplist != null && grouplist.size() > 0) {
				adminGroups.addAll(grouplist);
			}
		}
		taskList.setAdminGroups(adminGroups);

		taskListService.updateTaskList(taskList);

		UserPageResult upr = taskListService.getTaskList(1, 15);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("tasklist", upr.getList());
		model.addAttribute("user", user);
		return "/taskCandidate/taskCandidateList";
	}

	/**
	 * 方法说明：taskCandidateAddHandler
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/taskCandidate/taskCandidateAdd")
	public void taskCandidateAddHandler(ModelMap model)
	{
	}

	/**
	 * 方法说明：addTaskCandidatefromHandler
	 * 
	 * @param id
	 *            id
	 * @param linkName
	 *            linkName
	 * @param linkUrl
	 *            linkUrl
	 * @param linkOrder
	 *            linkOrder
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/taskCandidate/addTaskCandidatefrom")
	public String addTaskCandidatefromHandler(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "linkName", required = false) String linkName,
			@RequestParam(value = "linkUrl", required = false) String linkUrl,
			@RequestParam(value = "linkOrder", required = false) String linkOrder,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		TaskList taskList = new TaskList();
		taskList.setId(id);
		taskList.setLinkName(linkName);
		try {
			taskList.setLinkOrder(Integer.parseInt(linkOrder));
		} catch (NumberFormatException e) {
			logger.warn(e);
			taskList.setLinkOrder(0);
		}
		taskList.setLinkUrl(linkUrl);
		Set<Group> adminGroups = new HashSet<Group>();

		if (jsgroupuuid != null) {
			String[] jsgroupuuids = jsgroupuuid.split(",");
			List<Group> grouplist = new ArrayList<Group>();
			if (jsgroupuuid.length() > 0) {
				for (int i = 0; i < jsgroupuuids.length; i++) {
					grouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
				}
			}
			if (grouplist != null && grouplist.size() > 0) {
				adminGroups.addAll(grouplist);
			}
		}
		taskList.setAdminGroups(adminGroups);

		taskListService.saveTaskList(taskList);

		UserPageResult upr = taskListService.getTaskList(1, 15);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("tasklist", upr.getList());
		model.addAttribute("user", user);
		return "/taskCandidate/taskCandidateList";
	}

	/**
	 * 方法说明：deleteTaskCandidateHandler
	 * 
	 * @param taskCandidateUuids
	 *            taskCandidateUuids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/taskCandidate/deleteTaskCandidate")
	public String deleteTaskCandidateHandler(
			@RequestParam("taskCandidateUuids") String taskCandidateUuids, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}

		String[] taskCandidateUuidlist = taskCandidateUuids.split(",");
		List<TaskList> listTaskList = new ArrayList<TaskList>();
		if (taskCandidateUuidlist.length > 0) {
			for (int i = 0; i < taskCandidateUuidlist.length; i++) {
				listTaskList
						.add(taskListService.getTaskListByUuid(taskCandidateUuidlist[i].trim()));
			}
			for (int i = 0; i < listTaskList.size(); i++) {
				TaskList taskList = listTaskList.get(i);
				taskListService.deleteTaskList(taskList);
			}
		}
		UserPageResult upr = taskListService.getTaskList(1, 15);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("tasklist", upr.getList());
		model.addAttribute("user", user);
		return "/taskCandidate/taskCandidateList";
	}

	/**
	 * 处理待办候选项(结束)
	 * 
	 * @param userid
	 * @param model
	 */

}