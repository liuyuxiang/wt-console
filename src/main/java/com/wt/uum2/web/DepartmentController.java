package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

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
public class DepartmentController extends BaseController
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
	 * @param parentUUID
	 *            parentUUID
	 * @param model
	 *            model
	 */
	@RequestMapping("/dept/simpletree/ajax")
	public void deptSimpletreeAjaxHandler(@RequestParam("id") String parentUUID, ModelMap model)
	{

		List<Department> deptChildren = uumService.getDepartmentChildren(parentUUID,
				uumService.getLoginUser());
		model.addAttribute("deptChildren", deptChildren);
	}

	/**
	 * 方法说明：getSubDeptHandler
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
	@RequestMapping("/getSubDept")
	public String getSubDeptHandler(@RequestParam("id") String id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model,
			@RequestParam(value = "viewstatus", required = false) String viewstatus)
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
		UserPageResult upr = uumService.getSubDepartments(page, pagesize, deptChildren);
		model.addAttribute("deptlist", upr.getList());
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("javapage", upr.getPager());
		if (viewstatus == null) {
			viewstatus = "viewme";
		}
		model.addAttribute("viewstatus", viewstatus);
		model.addAttribute("uumService", uumService);
		if (InitParameters.isCqGroupAuthor()) {
			isModifyGroup(loginUser, model);
		}
		model.addAttribute("user", loginUser);
		return "/dept/deptcontent";
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
	 * 方法说明：deptSimpletreePublicAjaxHandler
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param deptuuids
	 *            deptuuids
	 * @param type
	 *            type
	 * @param flag
	 *            flag
	 * @param mydeptuuid
	 *            mydeptuuid
	 * @param model
	 *            model
	 */
	@RequestMapping("/dept/simpletree/publicajax")
	public void deptSimpletreePublicAjaxHandler(@RequestParam("id") String parentUUID,
			@RequestParam("deptuuids") String deptuuids, @RequestParam("type") String type,
			@RequestParam(value = "flag", required = false) String flag,
			@RequestParam("mydeptuuid") String mydeptuuid, ModelMap model)
	{
		List<Department> deptChildren = null;
		if (flag == null) {
			deptChildren = uumService.getDepartmentChildren(parentUUID, uumService.getLoginUser());
		} else {
			deptChildren = uumService.getDepartmentChildren(parentUUID);
		}
		model.addAttribute("type", type);
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("deptuuids", deptuuids);
		model.addAttribute("mydeptuuid", mydeptuuid);
		model.addAttribute("flag", flag);
	}

	/**
	 * 
	 * 方法说明：部门日志
	 * 
	 * @param deptuuid
	 *            deptuuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 */
	@RequestMapping("/dept/deptlog")
	@Deprecated
	public void deptlogHandler(@RequestParam("dept") String deptuuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		Department dept = uumService.getDepartmentByUUID(deptuuid);
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 3;
		}
		UserPageResult upr = uumService.getResourceLogs(page, pagesize, dept);
		model.addAttribute("deptid", deptuuid);
		model.addAttribute("loglist", upr.getList());
		model.addAttribute("logpage", upr.getPager());
	}

	/**
	 * 方法说明：用户树
	 * 
	 * @param menuId
	 *            menuId
	 * @param flag
	 *            flag
	 * @param type
	 *            type
	 * @param deptuuids
	 *            deptuuids
	 * @param changetype
	 *            changetype
	 * @param mydeptuuid
	 *            mydeptuuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/publicusertree")
	public String publicusertreeHandler(
			@RequestParam(value = "flag", defaultValue="true") String flag,
			@RequestParam(value = "type", defaultValue="checkbox") String type,
			@RequestParam(value = "deptuuids",defaultValue="") String deptuuids,
			@RequestParam(value = "groupuuids",defaultValue="") String groupuuids,
			@RequestParam(value = "changetype",defaultValue="all") String changetype,
			@RequestParam(value = "mydeptuuid",defaultValue="") String mydeptuuid,
			ModelMap model)
	{

		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		
		Department dept = null;
		
		List<Department> deptChildren = new ArrayList<Department>();
		
		if(StringUtils.equals("self", changetype)){
			dept = uumService.getDepartmentByUUID(mydeptuuid);
			if(dept==null){
				dept = uumService.getUserPrimaryDepartment(loginUser);
			}
		}else if(StringUtils.equals("select", changetype)&&StringUtils.isNotBlank(deptuuids)){
			dept = uumService.getDepartmentRoot();
			String[] depts = deptuuids.split(",");
			for (int i = 0; i < depts.length; i++) {
				Department d = uumService.getDepartmentByUUID(depts[i]);
				if(d!=null&&!deptChildren.contains(d)){
					deptChildren.add(d);
				}
			}
		}
		
		if(dept==null){
			dept = uumService.getDepartmentRoot();
		}
		
		if(CollectionUtils.isEmpty(deptChildren)){
			deptChildren = uumService.getDepartmentChildren(dept.getUuid());
		}
		
		model.addAttribute("deptTreeRoot", dept);
		model.addAttribute("deptChildren", deptChildren);
		List<User> members = uumService.getUsersUnderDepartment(dept);
		if(StringUtils.isNotBlank(groupuuids)){
			List<User> groupuser = new ArrayList<User>();
			for (String uuid : groupuuids.split(",")) {
				Group g = uumService.getGroupByUuid(uuid);
				if(g==null){
					continue;
				}
				groupuser.addAll(uumService.getUsersByGroup(g, 1, -1));
			}
			if(!groupuser.isEmpty()){
				members.retainAll(groupuser);
			}
			model.addAttribute("groupuuids",groupuuids);
		}
		model.addAttribute("members", members);

		model.addAttribute("type", type);
		model.addAttribute("flag", flag);
		model.addAttribute("changetype", changetype);
		return "/publicusertree";
	}

	
	
	/**
	 * 方法说明：udtree 
	 * add by lcy
	 *
	 * @param flag
	 * @param type
	 * @param deptuuids
	 * @param groupuuids
	 * @param changetype
	 * @param mydeptuuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/publicudtree")
	public String publicudtreeHandler(
			@RequestParam(value = "flag", defaultValue="true") String flag,
			@RequestParam(value = "type", defaultValue="checkbox") String type,
			@RequestParam(value = "deptuuids",defaultValue="") String deptuuids,
			@RequestParam(value = "changetype",defaultValue="all") String changetype,
			@RequestParam(value = "mydeptuuid",defaultValue="") String mydeptuuid,
			@RequestParam(value = "selected",defaultValue="") String selected,
			ModelMap model)
	{

		User loginUser = uumService.getLoginUser();
		if (loginUser == null) {
			return NOTLOGIN;
		}
		
		Department dept = null;
		
		List<Department> deptChildren = new ArrayList<Department>();
		
		if(StringUtils.equals("self", changetype)){
			dept = uumService.getDepartmentByUUID(mydeptuuid);
			if(dept==null){
				dept = uumService.getUserPrimaryDepartment(loginUser);
			}
		}else if(StringUtils.equals("select", changetype)&&StringUtils.isNotBlank(deptuuids)){
			dept = uumService.getDepartmentRoot();
			String[] depts = deptuuids.split(",");
			for (int i = 0; i < depts.length; i++) {
				Department d = uumService.getDepartmentByUUID(depts[i]);
				if(d!=null&&!deptChildren.contains(d)){
					deptChildren.add(d);
				}
			}
		}
		
		if(dept==null){
			dept = uumService.getDepartmentRoot();
		}
		
		if(CollectionUtils.isEmpty(deptChildren)){
			deptChildren = uumService.getDepartmentChildren(dept.getUuid());
		}
		
		//设置选中
		setudSelected(selected, model);
		model.addAttribute("deptTreeRoot", dept);
		model.addAttribute("deptChildren", deptChildren);
		List<User> members = uumService.getUsersUnderDepartment(dept);
		model.addAttribute("members", members);

		model.addAttribute("type", type);
		model.addAttribute("flag", flag);
		model.addAttribute("changetype", changetype);
		return "/publicudtree";
	}
	
	/**
	 * 方法说明：循环生成节点
	 * 
	 * @param deptUUID
	 *            parentUUID
	 * @param deptuuids
	 *            deptuuids
	 * @param type
	 *            type
	 * @param changetype
	 *            useruuids
	 * @param flag
	 *            flag
	 * @param mydeptuuid
	 *            mydeptuuid
	 * @param model
	 *            model
	 */
	@RequestMapping("/user/simpletree/publicajax")
	public void deptSimpletreePublicAjaxHandler(
			@RequestParam(value = "id", required = false) String deptUUID,
			@RequestParam(value = "flag", defaultValue="true") String flag,
			@RequestParam(value = "type",defaultValue="checkbox") String type,
			@RequestParam(value = "deptuuids",defaultValue="") String deptuuids,
			@RequestParam(value = "groupuuids", defaultValue="") String groupuuids,
			@RequestParam(value = "changetype",defaultValue="all") String changetype,
			@RequestParam(value = "mydeptuuid",defaultValue="") String mydeptuuid, ModelMap model)
	{
		List<Department> deptChildren = new ArrayList<Department>();
		
		if(StringUtils.equals("all", changetype)||StringUtils.equals("true", flag)){
			deptChildren = uumService.getDepartmentChildren(deptUUID);
		}
		List<User> members = uumService.getUsersUnderDepartment(uumService
				.getDepartmentByUUID(deptUUID));
		if(StringUtils.isNotBlank(groupuuids)){
			List<User> groupuser = new ArrayList<User>();
			for (String uuid : groupuuids.split(",")) {
				Group g = uumService.getGroupByUuid(uuid);
				if(g==null){
					continue;
				}
				groupuser.addAll(uumService.getUsersByGroup(g, 1, -1));
			}
			if(!groupuser.isEmpty()){
				members.retainAll(groupuser);
			}
			model.addAttribute("groupuuids",groupuuids);
		}
		model.addAttribute("type", StringUtils.defaultIfEmpty(type, "checkbox"));
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("members", members);
		model.addAttribute("flag", flag);
		model.addAttribute("changetype", changetype);
	}
	@RequestMapping("/ud/simpletree/publicajax")
	public void deptuserSimpletreePublicAjaxHandler(
			@RequestParam(value = "id", required = false) String deptUUID,
			@RequestParam(value = "flag", defaultValue="true") String flag,
			@RequestParam(value = "type",defaultValue="checkbox") String type,
			@RequestParam(value = "deptuuids",defaultValue="") String deptuuids,
			@RequestParam(value = "groupuuids", defaultValue="") String groupuuids,
			@RequestParam(value = "changetype",defaultValue="all") String changetype,
			@RequestParam(value = "selected",defaultValue="") String selected,
			@RequestParam(value = "mydeptuuid",defaultValue="") String mydeptuuid, ModelMap model)
	{
		List<Department> deptChildren = new ArrayList<Department>();
		
		if(StringUtils.equals("all", changetype)||StringUtils.equals("true", flag)){
			deptChildren = uumService.getDepartmentChildren(deptUUID);
		}
		List<User> members = uumService.getUsersUnderDepartment(uumService
				.getDepartmentByUUID(deptUUID));
		if(StringUtils.isNotBlank(groupuuids)){
			List<User> groupuser = new ArrayList<User>();
			for (String uuid : groupuuids.split(",")) {
				Group g = uumService.getGroupByUuid(uuid);
				if(g==null){
					continue;
				}
				groupuser.addAll(uumService.getUsersByGroup(g, 1, -1));
			}
			if(!groupuser.isEmpty()){
				members.retainAll(groupuser);
			}
			model.addAttribute("groupuuids",groupuuids);
		}
		
		setudSelected(selected, model);
		model.addAttribute("type", StringUtils.defaultIfEmpty(type, "checkbox"));
		model.addAttribute("deptChildren", deptChildren);
		model.addAttribute("members", members);
		model.addAttribute("flag", flag);
		model.addAttribute("changetype", changetype);
	}
	
	
	
	/**
	 * 方法说明：设置选中
	 *
	 * @param selecteds
	 * @param model
	 */
	private void setudSelected(String selected,
			ModelMap model){
		List<String> selecteduuids = new ArrayList<String>();
		StringBuilder selectedjson = new StringBuilder();
		
				String[] selectedDeptUser=StringUtils.split(selected, ',');
				for (int i = 0; i < selectedDeptUser.length; i++) {
					String[] data = StringUtils.split(selectedDeptUser[i], "|");
					if (data != null && data.length > 0) {
						if (StringUtils.equalsIgnoreCase("d", data[0])) {
							Department d= uumService.getDepartmentByDeptCode(data[1]);
							if(d!=null&&!selecteduuids.contains(d.getUuid())){
								selecteduuids.add(d.getUuid());
								JSONObject jo=new JSONObject();
								jo.put("uuid", d.getUuid());
								jo.put("id", d.getDeptCode());
								jo.put("name",d.getName());
								jo.put("type","d");
								selectedjson.append("<div id='").append(d.getUuid()).append("'>").append(jo.toString()).append("</div>");
								
							}
						} else if (StringUtils.equalsIgnoreCase("u", data[0])) {
							User u=uumService.getUserByUserId(data[1]);
							if(u!=null&&!selecteduuids.contains(u.getUuid())){
								selecteduuids.add(u.getUuid());
								JSONObject jo=new JSONObject();
								jo.put("uuid", u.getUuid());
								jo.put("id", u.getId());
								jo.put("name",u.getName());
								jo.put("type","u");
								selectedjson.append("<div id='").append(u.getUuid()).append("'>").append(jo.toString()).append("</div>");
							}
						}
					}
				}
				model.addAttribute("selectedjson", selectedjson);
				model.addAttribute("selecteduuids", selecteduuids);
				model.addAttribute("selected", selected);
	}
	/**
	 * 方法说明：返回选中用户数据
	 * 
	 * @param useruuid
	 *            useruuid
	 * @param model
	 *            model
	 */
	@RequestMapping("/publicuserdata")
	public void publicuserdataHandler(@RequestParam("useruuid") String useruuid, ModelMap model)
	{
		String[] useruuids = useruuid.split(",");
		User user = null;
		Set<User> users = new HashSet<User>();
		for (int i = 0; i < useruuids.length; i++) {
			user = uumService.getUserByUuid(useruuids[i]);
			if (user != null && !users.contains(user)) {
				users.add(user);
			}
		}
		model.addAttribute("users", users);
	}
}
