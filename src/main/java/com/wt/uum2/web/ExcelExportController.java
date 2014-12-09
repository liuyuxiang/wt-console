package com.wt.uum2.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentTempLog;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserTempLog;
import com.wt.uum2.service.ExcelExportService;
import com.wt.uum2.service.UUMDataService;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-22
 * 作者:	LiChenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class ExcelExportController extends BaseController
{
	/**
	 * 
	 */
	private UUMService uumService;
	/**
	 * 
	 */
	private ExcelExportService excelExportService;
	/**
	 * 
	 */
	private UUMDataService uumDataService;

	public void setUumDataService(UUMDataService uumDataService)
	{
		this.uumDataService = uumDataService;
	}

	public void setExcelExportService(ExcelExportService excelExportService)
	{
		this.excelExportService = excelExportService;
	}

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/**
	 * 方法说明：exportUserAccountsHandler
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/excel/exportUser")
	public String exportUserAccountsHandler(@RequestParam(value = "deptUuid") String deptUuid,
			final HttpServletRequest request, final HttpServletResponse response, ModelMap model)
	{
		Department deptChildren = uumService.getDepartmentByUUID(deptUuid);
		UserPageResult<User> upr = uumService.getUserMembersUnderDepartment(-1, -1, deptChildren);
		List<User> userList = upr.getList();
		List<List<String>> xlsUsersList = new ArrayList<List<String>>();

		if (CollectionUtils.isNotEmpty(userList)) {

			// 设置表头
			List<String> headers = new ArrayList<String>();
			headers.add("部门名称");
			headers.add("部门ERP编码");
			headers.add("员工姓名");
			headers.addAll(excelExportService.getAttributeTypeByColumns());
			xlsUsersList.add(headers);

			for (User user : userList) {
				List<String> xlsUser = new ArrayList<String>();
				Department primaryDepartment = uumService.getUserPrimaryDepartment(user);
				xlsUser.add(primaryDepartment.getName());
				xlsUser.add(primaryDepartment.getCode());
				xlsUser.add(user.getName());
				xlsUser.addAll(excelExportService.transformXslUsers(user));
				xlsUsersList.add(xlsUser);
			}
			String tempFileName = excelExportService.exportExcel(xlsUsersList, request, response);
			model.addAttribute("fileName", tempFileName);
		}
		return "excel/export";
	}

	/**
	 * 方法说明：exportERPAbnormalDeptsHandler
	 * 
	 * @param type
	 *            type
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/excel/erpAbnormalDepts")
	public String exportERPAbnormalDeptsHandler(
			@RequestParam(value = "type", defaultValue = "abnormal", required = false) String type,
			final HttpServletRequest request, final HttpServletResponse response, ModelMap model)
	{
		List<DepartmentTempLog> erpAbnormalDeptsList = uumDataService
				.getAllDepartmentTempListByStatus(type);
		List<List<String>> xlsAbnormalDeptsList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(erpAbnormalDeptsList)) {
			// 设置表头
			List<String> headers = new ArrayList<String>();
			headers.add("序号");
			headers.add("ERP编码");
			headers.add("部门编码");
			headers.add("部门名称");
			headers.add("操作状态");
			headers.add("数据变更时间");
			headers.add("描述");
			xlsAbnormalDeptsList.add(headers);

			for (int i = 0; i < erpAbnormalDeptsList.size(); i++) {
				DepartmentTempLog deptTempLog = erpAbnormalDeptsList.get(i);
				List<String> column = new ArrayList<String>();
				column.add(String.valueOf(i + 1));
				column.add(deptTempLog.getDeptId());
				column.add(deptTempLog.getDeptCode());
				column.add(deptTempLog.getDeptName());
				column.add(switchTypetoString(deptTempLog.getModifyType()));
				column.add(deptTempLog.getLastUpdateTime().toString());
				column.add(deptTempLog.getRemark());
				xlsAbnormalDeptsList.add(column);
			}
			String tempFileName = excelExportService.exportExcel(xlsAbnormalDeptsList, request,
					response);
			model.addAttribute("fileName", tempFileName);
		}
		return "excel/export";

	}

	/**
	 * 方法说明：exportERPAbnormalUsersHandler
	 * 
	 * @param type
	 *            type
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/excel/erpAbnormalUsers")
	public String exportERPAbnormalUsersHandler(
			@RequestParam(value = "type", defaultValue = "abnormal", required = false) String type,
			final HttpServletRequest request, final HttpServletResponse response, ModelMap model)
	{
		List<UserTempLog> erpAbnormalUsersList = uumDataService.getAllUserTempListByStatus(type);
		List<List<String>> xlsAbnormalUsersList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(erpAbnormalUsersList)) {
			// 设置表头
			List<String> headers = new ArrayList<String>();
			headers.add("序号");
			headers.add("用户编码");
			headers.add("用户姓名");
			headers.add("部门名称");
			headers.add("操作状态");
			headers.add("数据变更时间");
			headers.add("描述");
			xlsAbnormalUsersList.add(headers);

			for (int i = 0; i < erpAbnormalUsersList.size(); i++) {
				UserTempLog userTempLog = erpAbnormalUsersList.get(i);
				List<String> column = new ArrayList<String>();
				column.add(String.valueOf(i + 1));
				column.add(userTempLog.getUserCode());
				column.add(userTempLog.getUserName());
				column.add(userTempLog.getDeptName());
				column.add(switchTypetoString(userTempLog.getModifyType()));// getModifyStatus()
				column.add(userTempLog.getLastUpdateTime().toString());
				column.add(userTempLog.getRemark());
				xlsAbnormalUsersList.add(column);
			}
			String tempFileName = excelExportService.exportExcel(xlsAbnormalUsersList, request,
					response);
			model.addAttribute("fileName", tempFileName);
		}
		return "excel/export";

	}

	/**
	 * 方法说明：switchTypetoString
	 * 
	 * @param type
	 *            type
	 * @return String
	 */
	public String switchTypetoString(String type)
	{
		if (type != null && (type.equalsIgnoreCase("c") || type.equalsIgnoreCase("insert"))) {
			return "新增";
		} else if (type != null && (type.equalsIgnoreCase("u") || type.equalsIgnoreCase("update"))) {
			return "更新";
		} else if (type != null && (type.equalsIgnoreCase("d") || type.equalsIgnoreCase("delete"))) {
			return "删除";
		} else {
			return "状态异常";
		}
	}
}
