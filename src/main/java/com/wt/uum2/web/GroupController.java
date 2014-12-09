package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UserListService;

/**
 * <pre>
 * 业务名: 组的action
 * 功能说明: 操作组的各种action,(dt)
 * 编写日期:	2011-4-1
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class GroupController extends GroupBaseController
{

	/**
	 * 
	 */
	private UserListService userListService;

	public void setUserListService(UserListService userListService)
	{
		this.userListService = userListService;
	}

	/**
	 * 方法说明：职务
	 * 
	 */
	@RequestMapping("/group/publicgtree")
	public void groupListHandle(ModelMap model)
	{
		Group groupTreeRoot = uumService.getRootGroup();
		groupChildListHandle(model, groupTreeRoot.getUuid());
	}
	
	@RequestMapping("/group/simpletree/publicgajax")
	public void groupChildListHandle(ModelMap model,@RequestParam(value = "uuid",required=false) String uuid)
	{
		Group parent = uumService.getGroupByUuid(uuid);
		List<Group> groupChildren = uumService.getGroupsByParentGroup(parent);
		model.addAttribute("groupList", groupChildren);
	}
	
	/**
	 * 方法说明：group树的ajax展示
	 * 
	 * @param parentUUID
	 *            父级组的主键
	 * @param model
	 *            map
	 */
	@RequestMapping("/group/simpletree/ajax")
	public void groupSimpletreeAjaxHandler(@RequestParam("id") String parentUUID, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		Group groupTreeRoot = uumService.getGroupByUuid(parentUUID);
		List<Group> groupChildren = null;
		if (InitParameters.isCqGroupAuthor()) {
			groupChildren = uumService
					.getChildGroupByParentGroupAndUserCQ(groupTreeRoot, loginUser);
		} else {
			groupChildren = uumService.getChildGroupByParentGroupAndUser(groupTreeRoot, loginUser);
		}
		model.addAttribute("groupChildren", groupChildren);
	}

	/**
	 * 方法说明：addgroupformHandler
	 * 
	 * @param id
	 *            id
	 * @param name
	 *            name
	 * @param orderNum
	 *            orderNum
	 * @param code
	 *            code
	 * @param appgrouptype
	 *            appgrouptype
	 * @param jsgroupuuid
	 *            jsgroupuuid
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/group/addgroupform")
	public String addgroupformHandler(@RequestParam("id") String id,
			@RequestParam("name") String name, @RequestParam("orderNum") String orderNum,
			@RequestParam("code") String code,
			@RequestParam(value = "appgrouptype", required = false) String appgrouptype,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			final HttpServletRequest request, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Group pgroupChildren = uumService.getGroupByUuid(id);
		Group newgroup = new Group();
		newgroup.setName(name);
		newgroup.setCode(code);
		newgroup.setStatus(1);
		newgroup.setOrder(Long.valueOf(uumService.getAllGroups().size() + 1));
		newgroup.setHasChildren(false);
		if (pgroupChildren.getCode().equals(InitParameters.getRootApplicationGroup())) {
			newgroup.setGroupType("1");
			addAttributeTypeHandler(name, code);
		} else {
			newgroup.setGroupType("0");
		}
		Set<Group> pgroups = new HashSet<Group>();
		pgroups.add(pgroupChildren);
		newgroup.setParents(pgroups);
		if (appgrouptype != null && newgroup.getGroupType().equals("1")) {
			newgroup.setAppGroupType("1");
		} else {
			newgroup.setAppGroupType("0");
		}
		if (!pgroupChildren.isHasChildren()) {
			pgroupChildren.setHasChildren(true);
			uumService.updateGroup(pgroupChildren);
		}
		uumService.createGroup(newgroup);
		if (InitParameters.getIDSTurnOn()) {
			if (appgrouptype != null && !newgroup.getGroupType().equals("1")) {
				createGroupSyncToIDS(newgroup);
			}
		}

		if (StringUtils.isNotBlank(jsgroupuuid)) {
			String[] jsgroupuuids = jsgroupuuid.split(",");
			List<Group> grouplist = new ArrayList<Group>();
			if (jsgroupuuids.length > 0) {
				for (int i = 0; i < jsgroupuuids.length; i++) {
					grouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
				}
				uumService.addGroupManagerGroups(newgroup, grouplist);
			}
		}

		// 处理扩展属性
		List<AttributeType> types = uumService.getAttributeTypeAllByResource(newgroup, null);
		for (AttributeType attributeType : types) {
			Attribute attribute = new Attribute(newgroup, attributeType);
			attribute
					.setValue(StringUtils.defaultString(request.getParameter(attributeType.getId())));
			uumService.saveAttribute(attribute);
		}

		Event event = eventFactory.createEventCreateGroup(newgroup.getUuid());
		eventListenerHandler.handle(event);
		model.addAttribute("groupChildren", newgroup);
		UserPageResult<Group> upr = uumService.getGroupManagedUnderGroup(1, 15, pgroupChildren);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("grouplist", upr.getList());
		model.addAttribute("uumService", uumService);
		return "/group/groupcontent";
	}

	/**
	 * 方法说明：authorizationHandler
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/group/authorization")
	public String authorizationHandler(@RequestParam("id") String parentUUID,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Group groupChildren = uumService.getGroupByUuid(parentUUID);
		if (type == null) {
			type = "user";
		}
		model.addAttribute("type", type);
		model.addAttribute("groupChildren", groupChildren);
		return "/group/authorization";
	}

	/**
	 * 方法说明：getSubGroupHandler
	 * 
	 * @param pauuid
	 *            pauuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/getSubGroup")
	public String getSubGroupHandler(
			@RequestParam(value = "pauuid", required = false) String pauuid, ModelMap model)
	{
		Group group = uumService.getGroupByUuid(pauuid);
		Group rootGroup = uumService.getRootGroup();
		Group currGroup = null;
		while (group != null && group.getParents() != null) {
			currGroup = group;
			group = group.getParents().iterator().next();
			if (currGroup != null && group.getUuid().equalsIgnoreCase(rootGroup.getUuid())) {
				model.addAttribute("Finally", currGroup.getUuid());
				break;
			}
		}
		return "/user/confirmationuser";
	}

	/**
	 * 方法说明：groupSimpletreePublicAjaxHandler
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param groupuuids
	 *            groupuuids
	 * @param checkedPa
	 *            checkedPa
	 * @param flag
	 *            flag
	 * @param type
	 *            type
	 * @param adminGroups
	 *            adminGroups
	 * @param mygroupuuid
	 *            mygroupuuid
	 * @param model
	 *            model
	 */
	@RequestMapping("/group/simpletree/publicajax")
	public void groupSimpletreePublicAjaxHandler(@RequestParam("id") String parentUUID,
			@RequestParam(value = "groupuuids", required = false) String groupuuids,
			@RequestParam(value = "checkedPa", required = false) String checkedPa,
			@RequestParam(value = "flag", required = false) String flag,
			@RequestParam("type") String type,
			@RequestParam(value = "adminGroups", required = false) String adminGroups,
			@RequestParam("mygroupuuid") String mygroupuuid, ModelMap model)
	{
		Group parent = uumService.getGroupByUuid(parentUUID);
		User loginUser = uumService.getLoginUser();
		if (checkedPa != null && checkedPa.equalsIgnoreCase("true")) {
			model.addAttribute("autoChecked", "true");
		} else {
			model.addAttribute("autoChecked", "false");
		}
		List<Group> groupChildren = new ArrayList<Group>();
		if (flag.equals("false")) {
			if (InitParameters.isCqGroupAuthor()) {
				groupChildren = uumService.getChildGroupByParentGroupAndUserCQ(parent, loginUser);
			} else {
				groupChildren = uumService.getChildGroupByParentGroupAndUser(parent, loginUser);
			}
		} else {
			groupChildren = uumService.getGroupsByParentGroup(parent);
		}
		model.addAttribute("type", type);
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("groupuuids", groupuuids);
		model.addAttribute("mygroupuuid", mygroupuuid);
		if (adminGroups != null && !adminGroups.equals("")) {
			model.addAttribute("adminGroups", adminGroups);
		}
		model.addAttribute("flag", flag);
		model.addAttribute("isCqGroup", String.valueOf(InitParameters.isCqGroupAuthor()));
		model.addAttribute("isSuper", uumService.isUserinSuperGroup(loginUser));

	}

	/**
	 * 方法说明：groupusercontentHandler
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
	@RequestMapping("/group/usercontent")
	public String groupusercontentHandler(@RequestParam("id") String parentUUID,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Group groupChildren = uumService.getGroupByUuid(parentUUID);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<User> userlist = uumService.getUserManagedUnderGroup(page, pagesize,
				groupChildren);
		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/group/usercontent";
	}

	/**
	 * 方法说明：groupuseradminHandler
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
	@RequestMapping("/group/useradmin")
	public String groupuseradminHandler(@RequestParam("id") String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Group group = uumService.getGroupByUuid(uuid);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<User> userlist = uumService.getUserUnderGroup(page, pagesize, group);

		boolean isAdmin = uumService.isUserinSuperGroup(loginUser);

		if (!isAdmin && InitParameters.isCqGroupAuthor()) {
			if (!group.getCode().equalsIgnoreCase(InitParameters.getSuperGroupCode())) {
				isAdmin = uumService.getUserGroups(loginUser).contains(
						uumService.getGroupByCode(InitParameters.modifyUserGroupCode()));
			}
		}

		if (!isAdmin) {
			isAdmin = uumService.isGroupManager(loginUser, group);
		}

		model.addAttribute("isAdmin", isAdmin);

		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("groupChildren", group);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isSearch", InitParameters.getSeachUserByLogin());
		if (StringUtils.equalsIgnoreCase(InitParameters.getMacroUserList(), "true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/group/useradmin";
	}

	/**
	 * 方法说明：groupdeptcontentHandler
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
	@RequestMapping("/group/deptcontent")
	public String groupdeptcontentHandler(@RequestParam("id") String parentUUID,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Group groupChildren = uumService.getGroupByUuid(parentUUID);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<Department> deptlist = uumService.getDepartmentManagedUnderGroup(page,
				pagesize, groupChildren);
		model.addAttribute("javapage", deptlist.getPager());
		model.addAttribute("deptlist", deptlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("uumService", uumService);
		return "/group/deptcontent";
	}

	/**
	 * 方法说明：groupgroupcontentHandler
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
	@RequestMapping("/group/groupcontent")
	public String groupgroupcontentHandler(@RequestParam("id") String parentUUID,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Group groupChildren = uumService.getGroupByUuid(parentUUID);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<Group> grouplist = uumService.getGroupManagedUnderGroup(page, pagesize,
				groupChildren);
		model.addAttribute("javapage", grouplist.getPager());
		model.addAttribute("grouplist", grouplist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("uumService", uumService);
		return "/group/groupcontent";
	}

	/**
	 * 方法说明：publicgrouptreeHandler
	 * 
	 * @param menuId
	 *            menuId
	 * @param flag
	 *            flag
	 * @param isGroup
	 *            isGroup
	 * @param checkedPa
	 *            checkedPa
	 * @param type
	 *            type
	 * @param mygroupuuid
	 *            mygroupuuid
	 * @param groupuuids
	 *            groupuuids
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/publicgrouptree")
	public String publicgrouptreeHandler(
			@RequestParam(value = "menuId", required = false) Integer menuId,
			@RequestParam(value = "flag", required = false) String flag,
			@RequestParam(value = "isGroup", required = false) String isGroup,
			@RequestParam(value = "checkedPa", required = false) String checkedPa,
			@RequestParam("type") String type, @RequestParam("mygroupuuid") String mygroupuuid,
			@RequestParam(value = "groupuuids", required = false) String groupuuids, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (checkedPa != null && checkedPa.equalsIgnoreCase("true")) {
			model.addAttribute("autoChecked", "true");
		} else {
			model.addAttribute("autoChecked", "false");
		}
		Group groupTreeRoot = uumService.getRootGroup();
		List<Group> groupChildren = new ArrayList<Group>();
		if (isGroup == null) {
			isGroup = "";
		}
		if (flag == null) {
			flag = "";
		}
		if (flag.equals("true")) {
			groupChildren = uumService.getGroupsByParentGroup(groupTreeRoot);
		} else {
			if (InitParameters.isCqGroupAuthor()) {
				groupChildren = uumService.getChildGroupByParentGroupAndUserCQ(groupTreeRoot, user);
			} else {
				groupChildren = uumService.getChildGroupByParentGroupAndUser(groupTreeRoot, user);
			}
			List<Group> adminGroups = uumService.getUserAdminGroups(user);
			List<String> adminGroupUUIDs = new ArrayList<String>();
			for (Group group : adminGroups) {
				adminGroupUUIDs.add(group.getUuid());
			}
			model.addAttribute("adminGroups", StringUtils.join(adminGroupUUIDs.iterator(), ","));
		}
		if (!isGroup.equals("true")) {
			Group group = uumService.getGroupByCode(InitParameters.getRootApplicationGroup());
			groupChildren.remove(group);
		}
		model.addAttribute("groupTreeRoot", groupTreeRoot);
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("groupuuids", groupuuids);
		model.addAttribute("type", type);
		model.addAttribute("mygroupuuid", mygroupuuid);
		model.addAttribute("flag", flag);
		model.addAttribute("isCqGroup", String.valueOf(InitParameters.isCqGroupAuthor()));
		model.addAttribute("isSuper", uumService.isUserinSuperGroup(user));
		return "/publicgrouptree";
	}

	/**
	 * 方法说明：cmsgrouptreeHandler
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/cmsGroup/cmsgrouptree")
	public String cmsgrouptreeHandler(@RequestParam("groupUuid") String groupUuid, ModelMap model)
	{
		Group groupTreeRoot = uumService.getGroupByUuid(groupUuid);
		model.addAttribute("groupTreeRoot", groupTreeRoot);
		model.addAttribute("groupChildren", uumService.getGroupsByParentGroup(groupTreeRoot));
		model.addAttribute("type", "checkbox");
		return "/cmsGroup/publictree";
	}

	/**
	 * 方法说明：publicgroupdataHandler
	 * 
	 * @param groupuuids
	 *            groupuuids
	 * @param model
	 *            model
	 */
	@RequestMapping("/publicgroupdata")
	public void publicgroupdataHandler(@RequestParam("groupuuids") String groupuuids, ModelMap model)
	{
		String[] groupuuid = groupuuids.split(",");
		Group groupChildren = null;
		List<Group> groupChildrens = new ArrayList<Group>();
		for (int i = 0; i < groupuuid.length; i++) {
			groupChildren = uumService.getGroupByUuid(groupuuid[i]);
			groupChildrens.add(groupChildren);
		}
		model.addAttribute("groupChildrens", groupChildrens);
	}

	/**
	 * 方法说明：movegroupuserHandler
	 * 
	 * @param userids
	 *            userids
	 * @param groupid
	 *            groupid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/movegroupuser")
	public String movegroupuserHandler(@RequestParam("userids") String userids,
			@RequestParam("id") String groupid, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		String[] userid = userids.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		User user = null;
		List<User> listuser = new ArrayList<User>();
		for (int i = 0; i < userid.length; i++) {
			user = uumService.getUserByUuid(userid[i]);
			uumService.deleteUserGroup(user, groupChildren);
			listuser.add(user);
			Event event = eventFactory.createUserEventRemoveGroup(user.getUuid(),
					groupChildren.getUuid());
			eventListenerHandler.handle(event);
		}
		groupuseradminHandler(groupid, null, null, model);
		return "/group/useradmin";
	}

	/**
	 * 方法说明：groupmovedeptHandler
	 * 
	 * @param depts
	 *            depts
	 * @param groupid
	 *            groupid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/groupmovedept")
	public String groupmovedeptHandler(@RequestParam("depts") String depts,
			@RequestParam("id") String groupid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		String[] deptid = depts.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		Department dept = null;
		for (int i = 0; i < deptid.length; i++) {
			dept = uumService.getDepartmentByUUID(deptid[i]);
			uumService.removeDepartmentManagedUnderGroup(dept, groupChildren);
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<Department> deptlist = uumService.getDepartmentManagedUnderGroup(page,
				pagesize, groupChildren);
		model.addAttribute("javapage", deptlist.getPager());
		model.addAttribute("deptlist", deptlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		return "/group/deptcontent";
	}

	/**
	 * 方法说明：movegrouptogroupHandler
	 * 
	 * @param groups
	 *            groups
	 * @param groupid
	 *            groupid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/movegrouptogroup")
	public String movegrouptogroupHandler(@RequestParam("groups") String groups,
			@RequestParam("id") String groupid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		String[] groupids = groups.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		Group newgroup = null;
		for (int i = 0; i < groupids.length; i++) {
			newgroup = uumService.getGroupByUuid(groupids[i]);
			uumService.removeGroupManagedUnderGroup(newgroup, groupChildren);
			if (groupChildren.getAdminGroups() != null) {
				List<String> oldAdminGroups = new ArrayList<String>();
				List<String> newAdminGroups = new ArrayList<String>();
				Iterator<Group> iterator = groupChildren.getAdminGroups().iterator();
				while (iterator.hasNext()) {
					oldAdminGroups.add(iterator.next().getName());
				}
				Set<Group> newGroups = groupChildren.getAdminGroups();
				newGroups.remove(groupChildren);
				Iterator<Group> iterator1 = newGroups.iterator();
				while (iterator1.hasNext()) {
					newAdminGroups.add(iterator1.next().getName());
				}
			}
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult<Group> grouplist = uumService.getGroupManagedUnderGroup(page, pagesize,
				groupChildren);
		model.addAttribute("javapage", grouplist.getPager());
		model.addAttribute("grouplist", grouplist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("uumService", uumService);
		return "/group/groupcontent";
	}

	/**
	 * 方法说明：delgrouptogroupHandler
	 * 
	 * @param groups
	 *            groups
	 * @param groupid
	 *            groupid
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
	@RequestMapping("/delgrouptogroup")
	public String delgrouptogroupHandler(@RequestParam("groups") String groups,
			@RequestParam("id") String groupid,
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
		String[] groupids = groups.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		Group newgroup = null;
		for (int i = 0; i < groupids.length; i++) {
			newgroup = uumService.getGroupByUuid(groupids[i]);
			uumService.removeGroupManagedUnderGroup(newgroup, groupChildren);
			if (groupChildren.getAdminGroups() != null) {
				List<String> oldAdminGroups = new ArrayList<String>();
				List<String> newAdminGroups = new ArrayList<String>();
				Iterator<Group> iterator = groupChildren.getAdminGroups().iterator();
				while (iterator.hasNext()) {
					oldAdminGroups.add(iterator.next().getName());
				}
				Set<Group> newGroups = groupChildren.getAdminGroups();
				newGroups.remove(groupChildren);
				Iterator<Group> iterator1 = newGroups.iterator();
				while (iterator1.hasNext()) {
					newAdminGroups.add(iterator1.next().getName());
				}
			}
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		UserPageResult<Group> grouplist = uumService.getGroups(page, pagesize, condition);
		model.addAttribute("javapage", grouplist.getPager());
		model.addAttribute("grouplist", grouplist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/group/groupgroupsearch";
	}

	/**
	 * 方法说明：searchgroupmovedeptHandler
	 * 
	 * @param depts
	 *            depts
	 * @param groupid
	 *            groupid
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
	@RequestMapping("/searchgroupmovedept")
	public String searchgroupmovedeptHandler(@RequestParam("depts") String depts,
			@RequestParam("id") String groupid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		String[] deptid = depts.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		for (int i = 0; i < deptid.length; i++) {
			Department dept = uumService.getDepartmentByUUID(deptid[i]);
			uumService.removeDepartmentManagedUnderGroup(dept, groupChildren);
		}
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
		UserPageResult<Department> deptlist = uumService.getDepartments(page, pagesize, condition);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("javapage", deptlist.getPager());
		model.addAttribute("deptlist", deptlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("uumService", uumService);
		return "/group/groupdeptsearch";
	}

	/**
	 * 方法说明：moveadmingroupuserHandler
	 * 
	 * @param userids
	 *            userids
	 * @param groupid
	 *            groupid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/moveadmingroupuser")
	public String moveadmingroupuserHandler(@RequestParam("userids") String userids,
			@RequestParam("id") String groupid, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		String[] userid = userids.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		for (int i = 0; i < userid.length; i++) {
			User user = uumService.getUserByUuid(userid[i]);
			// //////日志
			uumService.deleteUserManagedUnderGroup(groupChildren, user);
		}
		UserPageResult<User> userlist = uumService.getUserUnderGroup(1, 15, groupChildren);
		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/group/usercontent";
	}

	/**
	 * 方法说明：adduserstoadmingroupHandler
	 * 
	 * @param userids
	 *            userids
	 * @param groupid
	 *            groupid
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
	@RequestMapping("/adduserstoadmingroup")
	public String adduserstoadmingroupHandler(@RequestParam("userids") String userids,
			@RequestParam("id") String groupid,
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
		Group groupChildren = uumService.getGroupByUuid(groupid);
		User user = null;
		for (int i = 0; i < userid.length; i++) {
			user = uumService.getUserByUuid(userid[i]);
			uumService.addUserManagerUnderGroup(groupChildren, user);
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		UserPageResult<User> userlist = uumService.searchUsersManagedByUserGroups(page, pagesize,
				condition, loginUser);
		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/group/groupusersearch";
	}

	/**
	 * 方法说明：adddeptstoadmingroupHandler
	 * 
	 * @param depts
	 *            depts
	 * @param groupid
	 *            groupid
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
	@RequestMapping("/adddeptstoadmingroup")
	public String adddeptstoadmingroupHandler(@RequestParam("depts") String depts,
			@RequestParam("id") String groupid,
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
		String[] deptid = depts.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		Department dept = null;
		for (int i = 0; i < deptid.length; i++) {
			dept = uumService.getDepartmentByUUID(deptid[i]);
			uumService.addDepartmentManagedUnderGroup(dept, groupChildren);
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		UserPageResult<Department> deptlist = uumService.getDepartments(page, pagesize, condition);
		model.addAttribute("javapage", deptlist.getPager());
		model.addAttribute("deptlist", deptlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("uumService", uumService);
		return "/group/groupdeptsearch";
	}

	/**
	 * 方法说明：addgrouptoadmingroupHandler
	 * 
	 * @param groups
	 *            groups
	 * @param groupid
	 *            groupid
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
	@RequestMapping("/addgrouptoadmingroup")
	public String addgrouptoadmingroupHandler(@RequestParam("groups") String groups,
			@RequestParam("id") String groupid,
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
		String[] groupids = groups.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		for (int i = 0; i < groupids.length; i++) {
			Group group = uumService.getGroupByUuid(groupids[i]);
			uumService.addGroupManagedUnderGroup(group, groupChildren);
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		UserPageResult<Group> grouplist = uumService.getGroups(page, pagesize, condition);
		model.addAttribute("javapage", grouplist.getPager());
		model.addAttribute("grouplist", grouplist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/group/groupgroupsearch";
	}

	/**
	 * 方法说明：deluserstoadmingroupHandler
	 * 
	 * @param userids
	 *            userids
	 * @param groupid
	 *            groupid
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
	@RequestMapping("/deluserstoadmingroup")
	public String deluserstoadmingroupHandler(@RequestParam("userids") String userids,
			@RequestParam("id") String groupid,
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
		Group groupChildren = uumService.getGroupByUuid(groupid);
		User user = null;
		for (int i = 0; i < userid.length; i++) {
			user = uumService.getUserByUuid(userid[i]);
			uumService.deleteUserManagedUnderGroup(groupChildren, user);
		}
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		UserPageResult<User> userlist = uumService.searchUsersManagedByUserGroups(page, pagesize,
				condition, loginUser);
		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/group/groupusersearch";
	}

	/**
	 * 方法说明：delgroupuserHandler
	 * 
	 * @param userids
	 *            userids
	 * @param groupid
	 *            groupid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/delgroupuser")
	public String delgroupuserHandler(@RequestParam("userids") String userids,
			@RequestParam("id") String groupid, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		String[] userid = userids.split(",");
		Group groupChildren = uumService.getGroupByUuid(groupid);
		User user = null;
		for (int i = 0; i < userid.length; i++) {
			user = uumService.getUserByUuid(userid[i]);
			uumService.deleteUser(user);
		}
		UserPageResult<User> userlist = uumService.getUserUnderGroup(1, 15, groupChildren);
		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/group/useradmin";
	}

	/**
	 * 方法说明：confirmationgroupHandler
	 * 
	 * @param code
	 *            code
	 * @param name
	 *            name
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param model
	 *            model
	 */
	@RequestMapping("/group/confirmationgroup")
	public void confirmationgroupHandler(@RequestParam("code") String code,
			@RequestParam("name") String name, @RequestParam("id") String id,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		Group group = uumService.getGroupByUuid(id);
		boolean codeflag = false;
		boolean nameflag = false;
		String flag = "";
		if (type != null && type.equals("add")) {
			if (group.getCode() != null && group.getCode().equals(code)) {
				codeflag = true;
			} else {
				codeflag = uumService.existGroupCode(code);
			}
			if (group.getName() != null && group.getName().equals(name)) {
				nameflag = true;
			} else {
				nameflag = uumService.existGroupName(group, name);
			}
		} else {
			Group parentgroup = uumService.getGroupByUuid(group.getParents().iterator().next()
					.getUuid());
			if (group.getName().equals(name)) {
				nameflag = false;
			} else if (parentgroup.getName() != null && parentgroup.getName().equals(name)) {
				nameflag = true;
			} else {
				nameflag = uumService.existGroupName(parentgroup, name);
			}
		}
		if (codeflag && nameflag) {
			flag = "both";
		} else if (!codeflag && nameflag) {
			flag = "name";
		} else if (codeflag && !nameflag) {
			flag = "code";
		} else {
			flag = "false";
		}
		model.addAttribute("Finally", String.valueOf(flag));
	}

	/**
	 * 方法说明：existgroupUnderGroupHandler
	 * 
	 * @param id
	 *            id
	 * @param model
	 *            model
	 */
	@RequestMapping("/group/existgroupUnderGroup")
	public void existgroupUnderGroupHandler(@RequestParam("id") String id, ModelMap model)
	{
		Group group = uumService.getGroupByUuid(id);
		boolean flag = uumService.exitUserUnderGroupAndSubGroups(group);
		model.addAttribute("Finally", String.valueOf(flag));
	}

	/**
	 * 方法说明：groupsearchHandler
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
	@RequestMapping("/group/groupsearch")
	public String groupsearchHandler(@RequestParam("id") String id,
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
		Group group = uumService.getGroupByUuid(id);
		Condition condition = new Condition();
		boolean isUser = true;
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		} else if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		} else if (searchvalue.equals("deptid")) {
			condition.setUserId(searchcontent);
			isUser = false;
		} else if (searchvalue.equals("deptname")) {
			condition.setUserName(searchcontent);
			isUser = false;
		}
		UserPageResult upr = new UserPageResult();
		boolean isSuper = uumService.isUserinSuperGroup(loginUser);

		model.addAttribute("isSuper", isSuper);

		boolean isAdmin = isSuper;

		if (!isAdmin && InitParameters.isCqGroupAuthor()) {
			if (!group.getCode().equalsIgnoreCase(InitParameters.getSuperGroupCode())) {
				isAdmin = uumService.getUserGroups(loginUser).contains(
						uumService.getGroupByCode(InitParameters.modifyUserGroupCode()));
			}
		}

		if (!isAdmin && "sd".equalsIgnoreCase(ProjectResolver.getId())) {
			isAdmin = StringUtils.equals(uumService.getUserPrimaryDepartment(loginUser).getOrgCode(),
					InitParameters.getSgsDeptCode());
		}

		if (!isAdmin) {
			isAdmin = uumService.isGroupManager(loginUser, group);
		}
		if (isUser) {
			if (isAdmin) {
				upr = uumService.searchUsersByCondition(page, pagesize, condition);
			} else {
				if ("true".equals(InitParameters.getSeachUserByLogin())) {
					String orgCode = uumService.getUserPrimaryDepartment(loginUser).getOrgCode();
					upr = uumService.searchUsersByConditionAndOrgCode(page, pagesize, condition,
							orgCode);
				}
			}
		} else {
			DepartmentCondition dc = new DepartmentCondition();
			if (searchvalue.equals("deptid")) {
				dc.setId(searchcontent);
				upr = uumService.searchUsersByDepartmentCondition(page, pagesize, dc, loginUser);
			} else if (searchvalue.equals("deptname")) {
				dc.setName(searchcontent);
				upr = uumService.searchUsersByDepartmentCondition(page, pagesize, dc, loginUser);
			}
		}


		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("groupChildren", group);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		model.addAttribute("isSearch", InitParameters.getSeachUserByLogin());
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(upr.getList(), upr.getPager().getDataStart(), model);
			model.addAttribute("macro", "true");
		}
		return "/group/groupsearch";
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

	/**
	 * 方法说明：groupdeptsearchHandler
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
	@RequestMapping("/group/groupdeptsearch")
	public String groupdeptsearchHandler(@RequestParam("id") String id,
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
		Group groupChildren = uumService.getGroupByUuid(id);
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}

		UserPageResult<Department> result = uumService.searchDepartmentManagedByUserGroups(page,
				pagesize, condition, loginUser);

		model.addAttribute("javapage", result.getPager());
		model.addAttribute("deptlist", result.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/group/groupdeptsearch";
	}

	/**
	 * 方法说明：groupgroupsearchHandler
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
	@RequestMapping("/group/groupgroupsearch")
	public String groupgroupsearchHandler(@RequestParam("id") String id,
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
		Group groupChildren = uumService.getGroupByUuid(id);
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		UserPageResult<Group> groups = uumService.searchGroupManagedByUserGroups(page, pagesize,
				condition, loginUser);
		model.addAttribute("javapage", groups.getPager());
		model.addAttribute("grouplist", groups.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		return "/group/groupgroupsearch";
	}

	/**
	 * 方法说明：groupusersearchHandler
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
	@RequestMapping("/group/groupusersearch")
	public String groupusersearchHandler(@RequestParam("id") String id,
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
		Group groupChildren = uumService.getGroupByUuid(id);
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		UserPageResult<User> userlist = uumService.searchUsersManagedByUserGroups(page, pagesize,
				condition, loginUser);
		model.addAttribute("javapage", userlist.getPager());
		model.addAttribute("userlist", userlist.getList());
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("uumService", uumService);
		if (InitParameters.getMacroUserList() != null
				&& InitParameters.getMacroUserList().equalsIgnoreCase("true")) {
			userListService.putAll2ModelMap(userlist.getList(), userlist.getPager().getDataStart(),
					model);
			model.addAttribute("macro", "true");
		}
		return "/group/groupusersearch";
	}

	/**
	 * 方法说明：deluserstogroupHandler
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
	@RequestMapping("/group/deluserstogroup")
	public String deluserstogroupHandler(@RequestParam("id") String id,
			@RequestParam("users") String users,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User xssouser = uumService.getLoginUser();
		if (xssouser == null) {
			return NOTLOGIN;
		}
		Group groupChildren = uumService.getGroupByUuid(id);
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		String[] userid = users.split(",");
		for (int i = 0; i < userid.length; i++) {
			User usr = uumService.getUserByUuid(userid[i]);
			uumService.deleteUserGroup(usr, groupChildren);
			Event event = eventFactory.createUserEventRemoveGroup(usr.getUuid(),
					groupChildren.getUuid());
			eventListenerHandler.handle(event);
		}
		return groupsearchHandler(id, page, pagesize, searchvalue, searchcontent, model);
	}

	/**
	 * 方法说明：adduserstogroupHandler
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
	@RequestMapping("/group/adduserstogroup")
	public String adduserstogroupHandler(@RequestParam("id") String id,
			@RequestParam("users") String users,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User xssouser = uumService.getLoginUser();
		if (xssouser == null) {
			return NOTLOGIN;
		}
		Group groupChildren = uumService.getGroupByUuid(id);
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		String[] userid = users.split(",");
		for (int i = 0; i < userid.length; i++) {
			User usr = uumService.getUserByUuid(userid[i]);
			uumService.addUserGroup(usr, groupChildren);
			Event event = eventFactory.createUserEventAddGroup(usr.getUuid(),
					groupChildren.getUuid());
			eventListenerHandler.handle(event);
		}
		return groupsearchHandler(id, page, pagesize, searchvalue, searchcontent, model);
	}

	/**
	 * 方法说明：isapplicationgroupHandler
	 * 
	 * @param id
	 *            id
	 * @param model
	 *            model
	 */
	@RequestMapping("/group/isapplicationgroup")
	public void isapplicationgroupHandler(@RequestParam("id") String id, ModelMap model)
	{
		Group group = uumService.getGroupByUuid(id);
		boolean flag = uumService.isApplicationGroup(group);
		model.addAttribute("Finally", String.valueOf(flag));
	}

}
