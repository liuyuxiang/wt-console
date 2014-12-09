package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.CandidateItem;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.ResourceLog;
import com.wt.uum2.domain.ResourceSync;
import com.wt.uum2.domain.User;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.UUMService;

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
public class GroupBaseController extends BaseController
{
	/**
	 * 
	 */
	protected UUMService uumService;
	/**
	 * 
	 */
	protected EventFactory eventFactory;
	/**
	 * 
	 */
	protected EventListenerHandler eventListenerHandler;

	/**
	 * @param uumService
	 *            uumService
	 */
	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
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
	 * 比较两个组列表
	 * 
	 * @param oldGroups
	 *            oldGroups
	 * @param newGroups
	 *            newGroups
	 * @return [addgroups,delgroups]
	 */
	protected Collection<Group>[] compareGroups(Collection<Group> oldGroups,
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
	 * 方法说明：saveResourceLog
	 * 
	 * @param editUser
	 *            editUser
	 * @param beforeValue
	 *            beforeValue
	 * @param afterValue
	 *            afterValue
	 * @param logId
	 *            logId
	 * @param remark
	 *            remark
	 * @param resourceUUID
	 *            resourceUUID
	 * @param fieldName
	 *            fieldName
	 */
	protected void saveResourceLog(String editUser, String beforeValue, String afterValue,
			String logId, String remark, String resourceUUID, String fieldName)
	{
		ResourceLog groupLog = new ResourceLog();
		groupLog.setEditDate(new java.util.Date());
		groupLog.setEditPerson(editUser);
		if (beforeValue != null) {
			groupLog.setBeforeValue(beforeValue);
		}
		if (afterValue != null) {
			groupLog.setAfterValue(afterValue);
		}
		groupLog.setLogid(logId);
		groupLog.setRemark(remark);
		groupLog.setResourceuuid(resourceUUID);
		groupLog.setFieldName(fieldName);
		uumService.saveResourceLog(groupLog);
	}

	/**
	 * 方法说明：添加扩展属性 addAttributeTypeHandler()
	 * 
	 * @param name
	 *            name
	 * @param code
	 *            code
	 */
	protected void addAttributeTypeHandler(String name, String code)
	{
		String newname = "";
		String newcode = "";
		Set<Group> groups = new HashSet<Group>();
		Set<Group> adminGroups = new HashSet<Group>();
		boolean hidden = true;
		boolean multivalued = false;
		int order = 0;
		String wpsadmins = InitParameters.getSuperGroupCode();
		Group group = uumService.getGroupByCode(wpsadmins);
		groups.add(group);
		adminGroups.add(group);
		int resourceType = 0;
		String catagory = "3";
		String pageType = "text";
		String[] attrlist = { "Account", "Pwd", "LoginEnable", "Status" };
		String[] attrnamelist = { "账号", "密码", "是否允许登陆", "状态" };
		for (String attr : attrlist) {
			AttributeType attribute = new AttributeType();
			boolean sync = false;
			if (attr.equals(attrlist[0])) {
				newcode = InitParameters.getAppAccountLocal().replace("XXXX", code);
				newname = name + attrnamelist[0];
				hidden = false;
			} else if (attr.equals(attrlist[1])) {
				newcode = InitParameters.getAppPwdLocal().replace("XXXX", code);
				newname = name + attrnamelist[1];
				hidden = true;
			} else if (attr.equals(attrlist[2])) {
				newcode = InitParameters.getAppLoginEnableLocal().replace("XXXX", code);
				newname = name + attrnamelist[2];
				hidden = false;
				sync = true;
			} else if (attr.equals(attrlist[3])) {
				newcode = InitParameters.getAppStatusCode().replace("XXXX", code);
				newname = name + attrnamelist[3];
				hidden = true;
			}
			order = uumService.getAttributeTypeAllByResource(new User(), resourceType).size() + 1;
			if (uumService.existAttributeTypeId(newcode)) {
				attribute = uumService.getAttributeTypeById(newcode).get(0);
			}
			attribute.setAdminGroups(adminGroups);
			attribute.setCatagory(catagory);
			attribute.setGroups(groups);
			attribute.setHidden(hidden);
			attribute.setId(newcode);
			attribute.setMultivalued(multivalued);
			attribute.setName(newname);
			attribute.setOrder(Long.valueOf(order));
			attribute.setPageType(pageType);
			attribute.setResourceType(resourceType);
			if (uumService.existAttributeTypeId(newcode)) {
				uumService.updateAttributeType(attribute);
			} else {
				uumService.saveAttributeType(attribute);
			}
			if (sync) {
				ResourceSync ap = new ResourceSync();
				if (uumService.existResourceSyncPropName(newcode)) {
					ap = uumService.getResourceSyncByPorpName(newcode).get(0);
				}
				ap.setPropType("c");
				ap.setPropName(newcode);
				ap.setType(resourceType);
				if (!uumService.existResourceSyncPropName(newcode))
					uumService.saveResourceSync(ap);
				else {
					uumService.updateResourceSync(ap);
				}
			}
		}
	}

	/**
	 * 方法说明：删除扩展属性 deleteAttributeTypeHandler()
	 * 
	 * @param code
	 *            code
	 */
	protected void deleteAttributeTypeHandler(String code)
	{
		String newcode = "";
		String[] attrlist = { "Account", "Pwd", "LoginEnable", "Status" };
		for (String attr : attrlist) {
			if (attr.equals(attrlist[0])) {
				newcode = InitParameters.getAppAccountLocal().replace("XXXX", code);
			} else if (attr.equals(attrlist[1])) {
				newcode = InitParameters.getAppPwdLocal().replace("XXXX", code);
			} else if (attr.equals(attrlist[2])) {
				newcode = InitParameters.getAppLoginEnableLocal().replace("XXXX", code);
			} else if (attr.equals(attrlist[3])) {
				newcode = InitParameters.getAppStatusCode().replace("XXXX", code);
			}
			if (uumService.existAttributeTypeId(newcode)) {
				AttributeType app = uumService.getAttributeTypeById(newcode).get(0);
				List<CandidateItem> aaa = uumService.getCandidateItemsByAttributeType(app);
				List<Attribute> att = uumService.getAttributesByAttributeType(app);
				List<ResourceSync> sync = uumService.getResourceSyncByPorpName(app.getId());
				for (int i = 0; i < aaa.size(); i++) {
					uumService.deleteCandidateItem(aaa.get(i));
				}
				for (int i = 0; i < att.size(); i++) {
					uumService.deleteAttribute(att.get(i));
				}
				for (int i = 0; i < sync.size(); i++) {
					uumService.deleteResourceSync(sync.get(i));
				}
				uumService.deleteAttributeType(app);
			}
		}
	}

	/**
	 * 方法说明：createGroupSyncToIDS
	 * 
	 * @param g
	 *            g
	 */
	protected void createGroupSyncToIDS(Group g)
	{
		Map<String, String> attributeMap = new HashMap<String, String>();
		attributeMap.put("group_ids_dn", "cn=" + g.getCode() + ",cn=groups,o=bepc");
		if (attributeMap.keySet().isEmpty())
			return;
		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
			List<AttributeType> attrList = uumService.getAttributeTypeById(entry.getKey());
			for (AttributeType attributeType : attrList) {
				Attribute attribute = new Attribute(g, attributeType);
				attribute.setValue(entry.getValue());
				uumService.saveAttribute(attribute);
				/*
				Attribute attribute = new Attribute();
				attribute.setOwnerResource(g);
				attribute.setType(attributeType);
				Set<AttributeValue> avSet = new HashSet<AttributeValue>();
				StringValue av = new StringValue();
				av.setAttribute(attribute);
				av.setValue(entry.getValue());
				avSet.add(av);
				attribute.setAttValues(avSet);
				uumService.saveAttribute(attribute);*/
			}
		}
	}

	/**
	 * 方法说明：addgroupHandler
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/group/addgroup")
	public String addgroupHandler(@RequestParam("id") String parentUUID, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		String appgroup = null;
		Group groupChildren = uumService.getGroupByUuid(parentUUID);
		if (groupChildren.getCode().equals(InitParameters.getRootApplicationGroup())) {
			appgroup = "1";
		} else {
			appgroup = "0";
		}
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("uumService", uumService);
		model.addAttribute("appgroup", appgroup);
		model.addAttribute("attributelist",
				uumService.getAttributeTypeAllByResource(new Group(), null));
		if (InitParameters.isCqGroupAuthor()) {
			model.addAttribute("cqAuthor", InitParameters.isCqGroupAuthor());
		}
		model.addAttribute("superstatus", uumService.isUserinSuperGroup(uumService.getLoginUser()));
		return "/group/addgroup";
	}

	/**
	 * 方法说明：updategroupHandler
	 * 
	 * @param uuid
	 *            uuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/group/updategroup")
	public String updategroupHandler(@RequestParam("id") String uuid, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		String appgroup = null;
		Group group = uumService.getGroupByUuid(uuid);
		List<Group> parents = uumService.getParentsGroup(group);
		if (parents.get(0).getCode().equals(InitParameters.getRootApplicationGroup())) {
			appgroup = "1";
		} else {
			appgroup = "0";
		}

		List<AttributeType> types = uumService.getAttributeTypeAllByResource(group, null);
		for (AttributeType type : types) {
			List<Attribute> attributes = uumService.getAttributesByResAndType(group, type);
			if (CollectionUtils.isEmpty(attributes)) {
				Attribute attribute = new Attribute(group, type);
				uumService.saveAttribute(attribute);
			}
		}

		List<Attribute> attlist = uumService.getAttributesUnderResource(group);

		model.addAttribute("attlist", attlist);
		model.addAttribute("groupChildren", group);
		model.addAttribute("uumService", uumService);
		List<Group> groupgroup = uumService.getGroupManagedGroups(group);
		model.addAttribute("groupgroup", groupgroup);
		model.addAttribute("appgroup", appgroup);
		model.addAttribute("superstatus", uumService.isUserinSuperGroup(uumService.getLoginUser()));
		return "/group/updategroup";
	}

	/**
	 * 方法说明：updategroupfromHandler
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
	 * @param pid
	 *            pid
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
	@RequestMapping("/group/updategroupfrom")
	public String updategroupfromHandler(@RequestParam("id") String id,
			@RequestParam("name") String name, @RequestParam("orderNum") String orderNum,
			@RequestParam("code") String code,
			@RequestParam(value = "appgrouptype", required = false) String appgrouptype,
			@RequestParam("pid") String pid,
			@RequestParam(value = "viewstatus", required = false) String viewstatus,
			@RequestParam(value = "jsgroupuuid", required = false) String jsgroupuuid,
			final HttpServletRequest request, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		String[] newpid = pid.split(",");
		List<Group> grouplistn = new ArrayList<Group>();
		for (int i = 0; i < newpid.length; i++) {
			grouplistn.add(uumService.getGroupByUuid(newpid[i]));
		}
		Group groupChildren = uumService.getGroupByUuid(id);
		// //////角色日志/////////////
		String remark = "修改角色";
		String resourceUUID = groupChildren.getUuid();
		String fieldname = groupChildren.getName();
		Map<String, String[]> map = new HashMap<String, String[]>();
		List<Event> events = new ArrayList<Event>();
		if (!groupChildren.getName().equals(name)) {
			saveResourceLog(loginUser.getName(), groupChildren.getName(), name, "组中文名", remark,
					resourceUUID, fieldname);
			map.put("name", new String[] { groupChildren.getName(), name });
			groupChildren.setName(name);
		}
		if (!groupChildren.getCode().equals(code)) {
			saveResourceLog(loginUser.getName(), groupChildren.getCode(), code, "组编码", remark,
					resourceUUID, fieldname);
			map.put("code", new String[] { groupChildren.getCode(), code });
			groupChildren.setCode(code);
		}
		if (groupChildren.getOrder().intValue() != Integer.parseInt(orderNum)) {
			saveResourceLog(loginUser.getName(), groupChildren.getOrder().toString(), orderNum,
					"组排序号", remark, resourceUUID, fieldname);
			map.put("order", new String[] { groupChildren.getOrder().toString(), orderNum });
			groupChildren.setOrder(Long.parseLong(orderNum));
		}
		List<Group> jsgrouplist = new ArrayList<Group>();
		if (jsgroupuuid != null && !jsgroupuuid.equals("")) {
			String[] jsgroupuuids = jsgroupuuid.split(",");
			if (jsgroupuuids.length > 0) {
				for (int i = 0; i < jsgroupuuids.length; i++) {
					jsgrouplist.add(uumService.getGroupByUuid(jsgroupuuids[i]));
				}
				if (groupChildren.getAdminGroups() != null) {
					if (!(groupChildren.getAdminGroups().containsAll(jsgrouplist) && jsgrouplist
							.containsAll(groupChildren.getAdminGroups()))) {
						List<String> oldAdminGroups = new ArrayList<String>();
						List<String> newAdminGroups = new ArrayList<String>();
						Iterator<Group> iterator = groupChildren.getAdminGroups().iterator();
						while (iterator.hasNext()) {
							oldAdminGroups.add(iterator.next().getName());
						}
						for (int k = 0; k < jsgrouplist.size(); k++) {
							newAdminGroups.add(jsgrouplist.get(k).getName());
						}
						saveResourceLog(loginUser.getName(), StringUtils.join(oldAdminGroups, ","),
								StringUtils.join(newAdminGroups, ","), "组的管理组", remark,
								resourceUUID, fieldname);
					}
				}
			}
		}
		uumService.updateGroupManagerGroups(groupChildren, jsgrouplist);

		if (groupChildren.getParents() != null && grouplistn != null && !grouplistn.isEmpty()) {
			Set<Group> oldGroups = groupChildren.getParents();
			List<Group> newGroups = grouplistn;

			Collection<Group>[] result = compareGroups(oldGroups, newGroups);
			if (!result[1].isEmpty()) {
				List<String> groupList = new ArrayList<String>();
				for (Group list : result[1]) {
					saveResourceLog(loginUser.getName(), list.getName(), null, "删除角色的上级组", remark,
							resourceUUID, fieldname);
					groupList.add(list.getUuid());
				}
				uumService.deleteGroupParentGroups(groupChildren, result[1]);
				events.add(eventFactory.createGroupEventRemoveGroup(groupChildren.getUuid(),
						groupList));
			}
			if (!result[0].isEmpty()) {
				List<String> groupList = new ArrayList<String>();
				for (Group list : result[0]) {
					saveResourceLog(loginUser.getName(), null, list.getName(), "添加角色的上级组", remark,
							resourceUUID, fieldname);
					groupList.add(list.getUuid());
					if (!list.isHasChildren()) {
						list.setHasChildren(true);
						uumService.updateGroup(list);
					}
				}
				uumService.addGroupParentGroups(groupChildren, result[0]);
				events.add(eventFactory.createGroupEventAddGroup(groupChildren.getUuid(), groupList));
			}
		}
		if (appgrouptype != null) {
			groupChildren.setAppGroupType("1");
		} else {
			groupChildren.setAppGroupType("0");
		}
		List<Attribute> attlist = uumService.getAttributesUnderResource(groupChildren, "0");
		for (int j = 0; j < attlist.size(); j++) {
			Attribute att = attlist.get(j);
			if (att.getType().getPageType().equals("checkbox")
					|| request.getParameter(att.getType().getId()) == null) {
				continue;
			}
			String after = StringUtils.defaultString(request.getParameter(att.getType().getId()));
			if (!after.equals(att.getValue())) {
				map.put(att.getType().getId(), new String[] { att.getValue(), after });
				att.setValue(after);
				uumService.updateAttribute(att);
			}
			/*List<AttributeValue> av = new ArrayList<AttributeValue>();
			av.addAll(att.getAttValues());
			StringValue avs = new StringValue();
			String before = String.valueOf("");
			if (av.size() <= 0) {
				avs.setAttribute(att);
				avs.setValue(request.getParameter(att.getType().getId()) == null ? "" : request.getParameter(att.getType().getId()));
				map.put(att.getType().getId(), new String[] { null, avs.getValue() });
				uumService.saveStringValue(avs);
			} else {
				for (int h = 0; h < av.size(); h++) {
					avs = (StringValue) av.get(h);
					String after = request.getParameter(att.getType().getId()) == null ? "" : request.getParameter(att.getType().getId());
					if (!avs.getValue().equalsIgnoreCase(after)) {
						map.put(att.getType().getId(), new String[] { avs.getValue(), after });
						avs.setValue(after);
						uumService.updateStringValue(avs);
					}
				}
			}*/
		}
		if (!map.isEmpty()) {
			events.add(eventFactory.createEventUpdateGroup(groupChildren.getUuid(), map));
		}
		if (!events.isEmpty()) {
			eventListenerHandler.handle(events);
		}
		model.addAttribute("groupChildren", groupChildren);
		model.addAttribute("type", "group");
		UserPageResult<Group> grouplist = uumService
				.getGroupManagedUnderGroup(1, 15, groupChildren);
		model.addAttribute("javapage", grouplist.getPager());
		model.addAttribute("grouplist", grouplist.getList());
		model.addAttribute("uumService", uumService);
		return "/group/groupcontent";
	}

	/**
	 * 方法说明：delgroupHandler
	 * 
	 * @param groupuuid
	 *            groupuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/group/delgroup")
	public String delgroupHandler(@RequestParam("id") String groupuuid, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Group groupChildren = uumService.getGroupByUuid(groupuuid);
		Group parent = groupChildren;
		Event event = eventFactory.createEventDeleteGroup(groupChildren.getUuid());
		List<Group> parents = uumService.getParentsGroup(groupChildren);
		if (CollectionUtils.isNotEmpty(parents)) {
			parent = parents.get(0);
		} else {
			parent = uumService.getRootGroup();
		}
		if (groupChildren.getGroupType().equals("1")) {
			deleteAttributeTypeHandler(groupChildren.getCode());
		}

		// //////角色日志/////////////
		// saveResourceLog(loginUser.getName(), "角色code:" + groupChildren.getCode(), null, "删除角色",
		// "删除角色", groupChildren.getUuid(), groupChildren.getName());
		// /////////////////////////
		groupChildren = uumService.getGroupByUuid(groupuuid);
		uumService.deleteGroup(groupChildren);
		eventListenerHandler.handle(event);
		model.addAttribute("type", "group");
		return groupcontentHandler(parent.getUuid(), "group", model);

	}

	/**
	 * 方法说明：groupcontentHandler
	 * 
	 * @param uuid
	 *            uuid
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/group/content")
	public String groupcontentHandler(@RequestParam("id") String uuid,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		Group group = uumService.getGroupByUuid(uuid);
		if (type == null) {
			type = "useradmin";
		}
		boolean flag = uumService.isUserinSuperGroup(loginUser);

		model.addAttribute("isSuper", flag);

		boolean isAdmin = flag;

		if (!isAdmin && InitParameters.isCqGroupAuthor()) {
			boolean isModifyGroup = uumService.getUserGroups(loginUser).contains(
					uumService.getGroupByCode(InitParameters.modifyUserGroupCode()));
			if (!group.getCode().equalsIgnoreCase(InitParameters.getSuperGroupCode())) {
				isAdmin = isModifyGroup;
			}
			model.addAttribute("isModifyGroup", isModifyGroup);
		}

		if (!isAdmin) {
			isAdmin = uumService.isGroupManager(loginUser, group);
		}

		model.addAttribute("isAdmin", isAdmin);

		model.addAttribute("type", type);
		model.addAttribute("groupChildren", group);
		model.addAttribute("uumService", uumService);
		return "/group/content";
	}

	/**
	 * 方法说明：createGroupHandler
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
	@RequestMapping("/group/creategroup")
	public String createGroupHandler(@RequestParam("id") String id,
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
		Boolean flag = Boolean.FALSE;
		try {
			Group pgroupChildren = uumService.getGroupByUuid(id);
			Group newgroup = new Group();
			newgroup.setName(name);
			newgroup.setCode(code);
			newgroup.setStatus(1);
			try {
				newgroup.setOrder(Long.parseLong(orderNum));
			} catch (NumberFormatException e) {
				newgroup.setOrder(Long.valueOf(uumService.getAllGroups().size() + 1));
			}
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

			if (jsgroupuuid != null && !jsgroupuuid.equals("")) {
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
				attribute.setValue(StringUtils.defaultString(request.getParameter(attributeType
						.getId())));
				uumService.saveAttribute(attribute);
			}

			Event event = eventFactory.createEventCreateGroup(newgroup.getUuid());
			eventListenerHandler.handle(event);
			flag = Boolean.TRUE;
		} catch (Exception e) {
			logger.error("create group " + code + "(" + name + ") is failed!", e);
		}
		model.addAttribute("Finally", String.valueOf(flag));
		return "/group/confirmationgroup";
	}

}
