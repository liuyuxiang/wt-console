package com.wt.uum2.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-22
 * 作者:	Li chenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface ExcelExportService
{

	/**
	 * 方法说明：设置表头
	 * 
	 * @return List
	 */
	public List<String> getAttributeTypeByColumns();

	/**
	 * 方法说明：导出到excel
	 * 
	 * @param xlsUsersList
	 *            xlsUsersList
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return String
	 */
	public String exportExcel(List<List<String>> xlsUsersList, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 方法说明：取用户的相关资源
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<String> transformXslUsers(User user);

	/**
	 * 方法说明：取当日期时间为文件名
	 * 
	 * @return String
	 */
	public String getFileName();

}