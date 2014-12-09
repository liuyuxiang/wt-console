package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:资源排序
 * 功能说明: 
 * 编写日期:	2012-10-25
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class ResourcesSortController extends BaseController
{

	/**
	 * 
	 */
	private UUMService uumService;

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/************************* 排序更新华丽丽的分割线 **************/

	/**
	 * 方法说明：showOrderHandle
	 * 
	 * @param model
	 *            model
	 * @param uuid
	 *            uuid
	 * @param type
	 *            type
	 * @return String
	 */
	@RequestMapping("/order/showorder")
	public String showOrderHandle(ModelMap model,
			@RequestParam(value = "id", required = false) String uuid,
			@RequestParam(value = "type", required = false, defaultValue = "department") String type)
	{
		Department dept = null;
		if (StringUtils.isNotBlank(uuid)) {
			dept = uumService.getDepartmentByUUID(uuid);
		} else {
			dept = uumService.getDepartmentRoot();
		}
		List<User> userList = uumService.getUsersUnderDepartment(dept);
		model.addAttribute("userList", userList);
		List<Department> deptList = uumService.getDepartmentChildren(dept.getUuid());
		model.addAttribute("deptList", deptList);
		model.addAttribute("type", type);
		return "/order/order";
	}

	/**
	 * 方法说明：orderMainHandle
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/order/main")
	public void orderMainHandle(ModelMap model)
	{
		Department deptTreeRoot = uumService.getDepartmentRoot();
		// List<Department> deptChildren = uumService.getDepartmentChildren(
		// deptTreeRoot.getUuid());

		List<Department> deptChildren = new ArrayList<Department>();
		deptChildren.add(deptTreeRoot);

		model.addAttribute("deptTreeRoot", deptTreeRoot);
		model.addAttribute("deptChildren", deptChildren);
	}

	/**
	 * 方法说明：修改用户、部门的排序
	 * 
	 * @param deptuuid
	 *            deptuuid
	 * @param useruuid
	 *            useruuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/order/modifyorder")
	public String modifyOrderHandle(
			@RequestParam(value = "deptuuid", required = false) String[] deptuuid,
			@RequestParam(value = "useruuid", required = false) String[] useruuid, ModelMap model)
	{
		if (!ArrayUtils.isEmpty(deptuuid)) {
			for (int i = 0; i < deptuuid.length; i++) {
				Department dept = uumService.getDepartmentByUUID(deptuuid[i]);
				if (dept != null && dept.getOrder().equals(Long.valueOf(i + 1))) {
					dept.setOrder(Long.valueOf(i + 1));
					uumService.updateDepartment(dept);
				}
			}
		}
		if (!ArrayUtils.isEmpty(useruuid)) {
			for (int i = 0; i < useruuid.length; i++) {
				User user = uumService.getUserByUuid(useruuid[i]);
				if (user != null && user.getOrder().equals(Long.valueOf(i + 1))) {
					user.setOrder(Long.valueOf(i + 1));
					uumService.updateUser(user);
				}
			}
		}
		model.addAttribute("Finally", String.valueOf(true));
		return "/user/confirmationuser";
	}

	/**
	 * 方法说明：通过上移下移设置用户的排序
	 * 
	 * @param model
	 *            model
	 * @param thisuuid
	 *            要移动的用户
	 * @param otheruuid
	 *            与之交换的用户
	 * @param type
	 *            type
	 * @return String
	 */
	@RequestMapping("/order/modifyuserorder")
	public String modifyUserOrderHandle(ModelMap model,
			@RequestParam(value = "thisuuid", required = false) String thisuuid,
			@RequestParam(value = "otheruuid", required = false) String otheruuid,
			@RequestParam(value = "type", required = false, defaultValue = "user") String type)
	{

		User user = uumService.getUserByUuid(thisuuid);
		Department primaryDepartment = uumService.getUserPrimaryDepartment(user);
		List<User> userList = uumService.getUsersUnderDepartment(primaryDepartment);
		long i = 0;
		for (User user1 : userList) {
			user1.setOrder(i);
			uumService.updateUser(user1);
			i++;
		}
		User thisUser = uumService.getUserByUuid(thisuuid);
		User otherUser = uumService.getUserByUuid(otheruuid);
		Long temp = thisUser.getOrder();
		thisUser.setOrder(otherUser.getOrder());
		otherUser.setOrder(temp);
		uumService.updateUser(thisUser);
		uumService.updateUser(otherUser);
		userList = uumService.getUsersUnderDepartment(primaryDepartment);
		model.addAttribute("userList", userList);
		model.addAttribute("type", type);
		model.addAttribute("uuid", thisuuid);
		return "/order/order";
	}

	/**
	 * 方法说明：modifyDeptOrderHandle
	 * 
	 * @param model
	 *            通过上移下移设置部门的排序
	 * @param thisuuid
	 *            thisuuid
	 * @param otheruuid
	 *            otheruuid
	 * @param type
	 *            type
	 * @return String
	 */
	@RequestMapping("/order/modifydeptorder")
	public String modifyDeptOrderHandle(ModelMap model,
			@RequestParam(value = "thisuuid") String thisuuid,
			@RequestParam(value = "otheruuid", required = false) String otheruuid,
			@RequestParam(value = "type", required = false, defaultValue = "department") String type)
	{
		Department parentDept = uumService.getDepartmentByUUID(thisuuid).getParent();
		List<Department> deptList = uumService.getDepartmentChildren(parentDept.getUuid());
		long i = 0;
		for (Department dept : deptList) {
			dept.setOrder(i);
			uumService.updateDepartment(dept);
			i++;
		}
		Department thisDept = uumService.getDepartmentByUUID(thisuuid);
		Department otherDept = uumService.getDepartmentByUUID(otheruuid);
		long temp = thisDept.getOrder();
		thisDept.setOrder(otherDept.getOrder());
		otherDept.setOrder(temp);
		uumService.updateDepartment(thisDept);
		uumService.updateDepartment(otherDept);

		deptList = uumService.getDepartmentChildren(parentDept.getUuid());
		model.addAttribute("deptList", deptList);
		model.addAttribute("type", type);
		model.addAttribute("uuid", thisuuid);
		return "/order/order";
	}

	/**
	 * 方法说明：设置用户、部门排序置顶置底
	 * 
	 * @param thisuuid
	 *            thisuuid
	 * @param type
	 *            type
	 * @param isTop
	 *            isTop
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/order/modifyordertop")
	public String modifyOrderTopHandle(
			@RequestParam(value = "thisuuid", required = false) String thisuuid,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "isTop") String isTop, ModelMap model)
	{
		List<Department> deptList = null;
		List<User> userList = null;
		if (!StringUtils.isBlank(type) && type.equals("department")) {
			String parentDeptUuid = uumService.getDepartmentByUUID(thisuuid).getParent().getUuid();
			deptList = uumService.getDepartmentChildren(parentDeptUuid);
			for (int i = 0; i < deptList.size(); i++) {
				Department dept = deptList.get(i);
				if (dept != null && isTop != null && isTop.equals("top")) {
					if (dept.getUuid().equals(thisuuid)) {
						dept.setOrder(Long.valueOf(0));
					} else {
						dept.setOrder(Long.valueOf(i + 1));
					}
				} else if (dept != null && isTop != null && isTop.equals("bottom")) {
					if (dept.getUuid().equals(thisuuid)) {
						dept.setOrder(Long.valueOf(deptList.size()));
					} else {
						dept.setOrder(Long.valueOf(i));
					}
				}
				uumService.updateDepartment(dept);
			}
			deptList = uumService.getDepartmentChildren(parentDeptUuid);
		} else if (!StringUtils.isBlank(type) && type.equals("user")) {
			User user1 = uumService.getUserByUuid(thisuuid);
			Department primaryDepartment = uumService.getUserPrimaryDepartment(user1);
			userList = uumService.getUsersUnderDepartment(primaryDepartment);
			for (int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				if (user != null && isTop != null && isTop.equals("top")) {
					if (user.getUuid().equals(thisuuid)) {
						user.setOrder(Long.valueOf(0));
					} else {
						user.setOrder(Long.valueOf(i + 1));
					}
				} else if (user != null && isTop != null && isTop.equals("bottom")) {
					if (user.getUuid().equals(thisuuid)) {
						user.setOrder(Long.valueOf(userList.size()));
					} else {
						user.setOrder(Long.valueOf(i));
					}
				}
				uumService.updateUser(user);
			}
			userList = uumService.getUsersUnderDepartment(primaryDepartment);
		}
		model.addAttribute("deptList", deptList);
		model.addAttribute("userList", userList);
		model.addAttribute("type", type);
		model.addAttribute("uuid", thisuuid);
		return "/order/order";
	}
	/************************* 排序更新华丽丽的分割线 **************/

}