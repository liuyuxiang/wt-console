package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.CandidateItem;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.ResourceSync;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 属性类
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
@SessionAttributes("userid")
public class AttrController extends BaseController
{

	/**
	 * 
	 */
	private UUMService uumService;

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/**
	 * 方法说明：listHandler
	 * 
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/attr/list")
	public String listHandler(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		// Integer treeid = Integer.valueOf(id);
		// if (treeid < 0) {
		// treeid = 0;
		// }
		if (StringUtils.isBlank(type)) {
			type = "attribute";
		}
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		return "/attr/list";
	}

	/**
	 * 方法说明：添加属性
	 * 
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/attr/attradd")
	public String addAttr(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		return "/attr/attradd";
	}

	/**
	 * 方法说明：添加属性
	 * 
	 * @param model
	 *            model
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param name
	 *            name
	 * @param hidden
	 *            hidden
	 * @param multivalued
	 *            multivalued
	 * @param order
	 *            order
	 * @param sync
	 *            sync
	 * @param resourceType
	 *            resourceType
	 * @param groups
	 *            groups
	 * @param adminGroups
	 *            adminGroups
	 * @param catagory
	 *            catagory
	 * @param attrid
	 *            attrid
	 * @param length
	 *            length
	 * @param rule
	 *            rule
	 * @param typetext
	 *            typetext
	 * @param typecaption
	 *            typecaption
	 * @param pageType
	 *            pageType
	 * @return String
	 */
	@RequestMapping("/attr/attraddsuc")
	public String addsucAttrHandler(ModelMap model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "hidden", required = false) String hidden,
			@RequestParam(value = "multivalued", required = false) String multivalued,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "sync", required = false) String sync,
			@RequestParam(value = "resourceType", required = false) String resourceType,
			@RequestParam(value = "groupsuuid", required = false) String groups,
			@RequestParam(value = "adminGroupsuuid", required = false) String adminGroups,
			@RequestParam(value = "catagory", required = false) String catagory,
			@RequestParam(value = "attrid", required = false) String attrid,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "rule", required = false) String rule,
			@RequestParam(value = "typetext", required = false) String[] typetext,
			@RequestParam(value = "typecaption", required = false) String[] typecaption,
			@RequestParam(value = "pageType", required = false) String pageType)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		AttributeType ap = new AttributeType();

		ap.setName(name);
		ap.setHidden(hidden.equals("hidden"));
		ap.setMultivalued(multivalued.equals("multiple"));
		ap.setId(attrid);
		ap.setOrder(StringUtils.isBlank(order) ? 0l : Long.valueOf(order));
		ap.setPageType(pageType);
		ap.setResourceType(StringUtils.isBlank(resourceType) ? 0 : Integer.valueOf(resourceType));
		ap.setCatagory(catagory);
		ap.setLength(length == null ? 100 : Long.valueOf(length));
		ap.setRule(rule);

		assemblyAttrTypeGroup(ap, adminGroups, groups);

		uumService.saveAttributeType(ap);

		modifyCandidateItem(ap, null, typetext, typecaption);

		modifyAttribteTypeSyncFlag(ap, sync);

		attrlistHandler(id, type, null, null, model);
		return "attr/attrlist";
	}

	/**
	 * 
	 * 方法说明：变更扩展属性备选项
	 * 
	 * @param at
	 *            扩展属性
	 * @param uuids
	 *            页面备选项主键
	 * @param values
	 *            页面备选项内容
	 * @param captions
	 *            页面备选项提示文字
	 */
	private void modifyCandidateItem(AttributeType at, String[] uuids, String[] values,
			String[] captions)
	{

		List<CandidateItem> list = uumService.getCandidateItemsByAttributeType(at);
		if (values == null) {
			// 如果页面没有备选项,则将扩展属性的所有备选项进行删除
			for (CandidateItem candidateItem : list) {
				uumService.deleteCandidateItem(candidateItem);
			}
			return;
		}

		if (uuids != null && uuids.length > 0) {
			// 有列表
			for (CandidateItem candidateItem : list) {
				if (Arrays.asList(uuids).contains(candidateItem.getUuid())) {
					// 列表中存在,update
					int sort = Arrays.binarySearch(uuids, candidateItem.getUuid());
					if (sort > -1) {
						candidateItem.setValue(values[sort]);
						candidateItem.setCaption(captions[sort]);
						candidateItem.setSortNumber((long) sort);
						uumService.updateCandidateItem(candidateItem);
					}
				} else {
					// 列表中不存在,delete
					uumService.deleteCandidateItem(candidateItem);
				}
			}

			for (int i = 0; i < uuids.length; i++) {
				if (StringUtils.isBlank(uuids[i])) {
					CandidateItem candidate = new CandidateItem();
					candidate.setValue(values[i]);
					candidate.setCaption(captions[i]);
					candidate.setAttributeType(at);
					candidate.setSortNumber((long) i);
					uumService.saveCandidateItem(candidate);
				}
			}
		} else {
			for (int i = 0; i < values.length; i++) {
				CandidateItem candidate = new CandidateItem();
				candidate.setValue(values[i]);
				candidate.setCaption(captions[i]);
				candidate.setAttributeType(at);
				candidate.setSortNumber((long) i);
				uumService.saveCandidateItem(candidate);
			}
		}
	}

	/**
	 * 
	 * 方法说明：组装扩展属性的组信息
	 * 
	 * @param at
	 *            扩展属性
	 * @param adminGroups
	 *            管理组
	 * @param groups
	 *            权限组
	 */
	private void assemblyAttrTypeGroup(AttributeType at, String adminGroups, String groups)
	{
		Set<Group> groupSet = new HashSet<Group>();
		Set<Group> admingroupSet = new HashSet<Group>();

		if (StringUtils.isNotBlank(adminGroups)) {
			for (String groupid : adminGroups.split(",")) {
				Group group = uumService.getGroupByUuid(groupid);
				if (group != null) {
					groupSet.add(group);
				}
			}
			if (CollectionUtils.isNotEmpty(groupSet)) {
				at.setAdminGroups(groupSet);
			}
		}
		if (StringUtils.isNotBlank(groups)) {
			for (String groupid : groups.split(",")) {
				Group group = uumService.getGroupByUuid(groupid);
				if (group != null) {
					admingroupSet.add(group);
				}
			}
			if (CollectionUtils.isNotEmpty(admingroupSet)) {
				at.setGroups(admingroupSet);
			}
		}

	}

	/**
	 * 
	 * 方法说明：同步标识变更
	 * 
	 * @param ap
	 *            扩展属性
	 * @param flag
	 *            不为空时同步,否则不同步
	 */
	private void modifyAttribteTypeSyncFlag(AttributeType ap, String flag)
	{
		List<ResourceSync> synclist = uumService.getResourceSyncByPorpName(ap.getId());
		if (StringUtils.isNotBlank(flag)) {
			if (CollectionUtils.isEmpty(synclist)) {
				ResourceSync rs = new ResourceSync();
				rs.setPropType("c");
				rs.setPropName(ap.getId());
				rs.setType(ap.getResourceType());
				uumService.saveResourceSync(rs);
			}
		} else {
			for (ResourceSync resourceSync : synclist) {
				uumService.deleteResourceSync(resourceSync);
			}
		}
	}

	/**
	 * 方法说明：查看编辑属性
	 * 
	 * @param model
	 *            model
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param attruuid
	 *            attruuid
	 * @return String
	 */
	@RequestMapping("/attr/attredit")
	public String editAttr(ModelMap model, @RequestParam(value = "id") String id,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "attruuid") String attruuid)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		AttributeType app = uumService.getAttributeTypeByUuid(attruuid.trim());
		List<String> adminGroupName = new ArrayList<String>();
		List<String> adminGroupUuid = new ArrayList<String>();
		List<String> groupName = new ArrayList<String>();
		List<String> groupUuid = new ArrayList<String>();
		Set<Group> adminGroupSet = app.getAdminGroups();
		Set<Group> groupSet = app.getGroups();
		for (Group group : adminGroupSet) {
			adminGroupName.add(group.getName());
			adminGroupUuid.add(group.getUuid());
		}
		for (Group group : groupSet) {
			groupName.add(group.getName());
			groupUuid.add(group.getUuid());
		}
		String admingroupname = StringUtils.join(adminGroupName.iterator(), ",");
		String admingroupuuid = StringUtils.join(adminGroupUuid.iterator(), ",");
		String groupname = StringUtils.join(groupName.iterator(), ",");
		String groupuuid = StringUtils.join(groupUuid.iterator(), ",");
		model.addAttribute("app", app);
		model.addAttribute("cuappname", app.getName());
		model.addAttribute("jsAdminGroupName", admingroupname);
		model.addAttribute("jsAdminGroupUuid", admingroupuuid);
		model.addAttribute("jsgroupAreName", groupname);
		model.addAttribute("jsgroupuuid", groupuuid);
		model.addAttribute("sync", uumService.existResourceSyncPropName(app.getId()));
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		return "/attr/attredit";
	}

	/**
	 * 方法说明：编辑属性
	 * 
	 * @param model
	 *            model
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param attruuid
	 *            attruuid
	 * @param name
	 *            name
	 * @param hidden
	 *            hidden
	 * @param multivalued
	 *            multivalued
	 * @param order
	 *            order
	 * @param sync
	 *            sync
	 * @param resourceType
	 *            resourceType
	 * @param groups
	 *            groups
	 * @param adminGroups
	 *            adminGroups
	 * @param catagory
	 *            catagory
	 * @param typetext
	 *            typetext
	 * @param typecaption
	 *            typecaption
	 * @param length
	 *            length
	 * @param rule
	 *            rule
	 * @param typeuuid
	 *            typeuuid
	 * @param pageType
	 *            pageType
	 * @return String
	 */
	@RequestMapping("/attr/attreditsuc")
	public String editsucAttrHandler(ModelMap model, @RequestParam(value = "id") String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "attruuid") String attruuid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "hidden", required = false) String hidden,
			@RequestParam(value = "multivalued", required = false) String multivalued,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "sync", required = false) String sync,
			@RequestParam(value = "resourceType", required = false) String resourceType,
			@RequestParam(value = "groupsuuid", required = false) String groups,
			@RequestParam(value = "adminGroupsuuid", required = false) String adminGroups,
			@RequestParam(value = "catagory", required = true) String catagory,
			@RequestParam(value = "typetext", required = false) String[] typetext,
			@RequestParam(value = "typecaption", required = false) String[] typecaption,
			@RequestParam(value = "length", required = false) Integer length,
			@RequestParam(value = "rule", required = false) String rule,
			@RequestParam(value = "typeuuid", required = false) String[] typeuuid,
			@RequestParam(value = "pageType", required = false) String pageType)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		AttributeType ap = uumService.getAttributeTypeByUuid(attruuid.trim());
		ap.setName(name);
		ap.setHidden(hidden.equals("hidden"));
		ap.setMultivalued(multivalued.equals("multiple"));
		ap.setOrder(StringUtils.isBlank(order) ? 0l : Long.valueOf(order));
		ap.setPageType(pageType);
		ap.setResourceType(StringUtils.isBlank(resourceType) ? 0 : Integer.valueOf(resourceType));
		ap.setCatagory(catagory);
		ap.setLength(length == null ? 100 : Long.valueOf(length));
		ap.setRule(rule);

		assemblyAttrTypeGroup(ap, adminGroups, groups);

		uumService.updateAttributeType(ap);

		modifyCandidateItem(ap, typeuuid, typetext, typecaption);

		modifyAttribteTypeSyncFlag(ap, sync);

		attrlistHandler(id, type, null, null, model);
		return "attr/attrlist";
	}

	/**
	 * 方法说明：删除属性
	 * 
	 * @param model
	 *            model
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param attruuid
	 *            attruuid
	 * @return String
	 */
	@RequestMapping("/attr/attrdel")
	public String deleteAttrHandler(ModelMap model, @RequestParam(value = "id") String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "attruuid") String attruuid)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		AttributeType app = uumService.getAttributeTypeByUuid(attruuid.trim());
		List<CandidateItem> aaa = uumService.getCandidateItemsByAttributeType(app);
		List<Attribute> attr = uumService.getAttributesByAttributeType(app);
		List<ResourceSync> sync = uumService.getResourceSyncByPorpName(app.getId());
		for (int i = 0; i < aaa.size(); i++) {
			uumService.deleteCandidateItem(aaa.get(i));
		}
		for (int i = 0; i < attr.size(); i++) {
			uumService.deleteAttribute(attr.get(i));
		}
		for (int i = 0; i < sync.size(); i++) {
			uumService.deleteResourceSync(sync.get(i));
		}
		uumService.deleteAttributeType(app);
		attrlistHandler(id, type, null, null, model);
		return "attr/attrlist";
	}

	/**
	 * 方法说明：属性列表
	 * 
	 * @param id
	 *            id
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
	@RequestMapping("/attr/attrlist")
	public String attrlistHandler(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "pagesize", required = false, defaultValue = "0") Integer pagesize,
			ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}

		Integer treeid = Integer.valueOf(id);
		if (treeid < 0) {
			treeid = 0;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = uumService.getAttributeTypesByResource(page, pagesize, treeid);
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("uumService", uumService);
		model.addAttribute("attrlist", upr.getList());
		return "/attr/attrlist";
	}

	/**
	 * 方法说明：同步列表
	 * 
	 * @param id
	 *            id
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
	@RequestMapping("/attr/synclist")
	public String synclistHandler(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}

		Integer treeid = Integer.valueOf(id);
		if (treeid > 2 || treeid < 0) {
			treeid = 2;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = uumService.getResourceSyncByResource(page, pagesize, treeid);
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("attrlist", upr.getList());
		return "/attr/synclist";
	}

	/**
	 * 方法说明：添加同步
	 * 
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/attr/syncadd")
	public String syncaddHandler(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		return "/attr/syncadd";
	}

	/**
	 * 方法说明：添加同步提交
	 * 
	 * @param model
	 *            model
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param name
	 *            name
	 * @param content
	 *            content
	 * @param functions
	 *            functions
	 * @return String
	 */
	@RequestMapping("/attr/syncaddsuc")
	public String addsucSyncHandler(ModelMap model,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "functions", required = false) String functions)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		ResourceSync ap = new ResourceSync();
		ap.setPropType(name);
		if (content == null) {
			ap.setPropName("");
		} else {
			ap.setPropName(content);
		}
		// ap.setTransFunc(transFunc);
		ap.setType(Integer.valueOf(id));
		uumService.saveResourceSync(ap);
		synclistHandler(id, type, null, null, model);
		return "attr/synclist";
	}

	/**
	 * 方法说明：查看同步属性
	 * 
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param syncuuid
	 *            syncuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/attr/syncedit")
	public String editSync(@RequestParam(value = "id") String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "syncuuid") String syncuuid, ModelMap model)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		ResourceSync app = uumService.getResourceSyncByUuid(syncuuid.trim());
		model.addAttribute("app", app);
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		return "/attr/syncedit";
	}

	/**
	 * 方法说明：编辑属性
	 * 
	 * @param model
	 *            model
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param syncuuid
	 *            syncuuid
	 * @param name
	 *            name
	 * @param content
	 *            content
	 * @param functions
	 *            functions
	 * @return String
	 */
	@RequestMapping("/attr/synceditsuc")
	public String editsucSyncHandler(ModelMap model, @RequestParam(value = "id") String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "syncuuid") String syncuuid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "functions", required = false) String functions)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		ResourceSync ap = uumService.getResourceSyncByUuid(syncuuid.trim());
		ap.setPropType(name);
		if (content == null) {
			ap.setPropName("");
		} else {
			ap.setPropName(content);
		}
		uumService.updateResourceSync(ap);
		synclistHandler(id, type, null, null, model);
		return "attr/synclist";
	}

	/**
	 * 方法说明：删除属性
	 * 
	 * @param model
	 *            model
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param syncuuid
	 *            syncuuid
	 * @return String
	 */
	@RequestMapping("/attr/syncdel")
	public String deleteSyncHandler(ModelMap model, @RequestParam(value = "id") String id,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "syncuuid") String syncuuid)
	{
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		ResourceSync app = uumService.getResourceSyncByUuid(syncuuid.trim());
		uumService.deleteResourceSync(app);
		synclistHandler(id, type, null, null, model);
		return "attr/synclist";
	}

	/**
	 * 方法说明：搜索指定组内所有属性
	 * 
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @param searchType
	 *            searchType
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/attr/search")
	public String attrsearchHandler(@RequestParam(value = "id") String id,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam(value = "searchcontent") String searchcontent,
			@RequestParam(value = "searchType") String searchType, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		if (uumService.getLoginUser() == null) {
			return NOTLOGIN;
		}
		Integer treeid = Integer.valueOf(id);
		if (treeid > 2 || treeid < 0) {
			treeid = 2;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		Condition cond = new Condition();
		if (searchType != null && searchType.equals("attrname")) {
			cond.setUserName(searchcontent);
		}
		if (searchType != null && searchType.equals("attrid")) {
			cond.setUserId(searchcontent);
		}

		UserPageResult upr = uumService
				.searchAttributeTypesByResource(page, pagesize, treeid, cond);
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("attrlist", upr.getList());
		model.addAttribute("searchcontent", searchcontent);
		model.addAttribute("searchType", searchType);
		model.addAttribute("uumService", uumService);
		return "/attr/attrlistsearch";
	}

	/**
	 * 方法说明：confirmationattrHandler
	 * 
	 * @param code
	 *            code
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/attr/confirmationattr")
	public String confirmationattrHandler(@RequestParam("code") String code, ModelMap model)
	{
		boolean codeflag = false;
		codeflag = uumService.existAttributeTypeId(code);
		model.addAttribute("Finally", String.valueOf(codeflag));
		return "/attr/confirmationattr";
	}

	/*
	 * 
	 */
	/**
	 * 方法说明：confirmationsyncHandler
	 * 
	 * @param code
	 *            code
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/attr/confirmationsync")
	public String confirmationsyncHandler(@RequestParam("code") String code, ModelMap model)
	{
		boolean codeflag = false;
		codeflag = uumService.existResourceSyncPropName(code);
		model.addAttribute("Finally", String.valueOf(codeflag));
		return "/attr/confirmationattr";
	}

}
