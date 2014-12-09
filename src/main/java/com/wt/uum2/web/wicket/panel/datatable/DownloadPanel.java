package com.wt.uum2.web.wicket.panel.datatable;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-16
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DownloadPanel extends BaseUUMPanel
{

	/**
	 * @param id
	 *            id
	 * @param dept
	 *            dept
	 */
	public DownloadPanel(String id, Department dept)
	{
		super(id);
		UserPageResult<User> upr = getUUMService().getUserMembersUnderDepartment(-1, -1, dept);
		List<User> userList = upr.getList();
		List<List<String>> xlsUsersList = new ArrayList<List<String>>();

		if (CollectionUtils.isNotEmpty(userList)) {

			// 设置表头
			List<String> headers = new ArrayList<String>();
			headers.add("部门名称");
			headers.add("部门ERP编码");
			headers.add("员工姓名");
			headers.addAll(getExcelExportService().getAttributeTypeByColumns());
			xlsUsersList.add(headers);

			for (User user : userList) {
				List<String> xlsUser = new ArrayList<String>();
				Department primaryDepartment = getUUMService().getUserPrimaryDepartment(loginUser);
				xlsUser.add(primaryDepartment.getName());
				xlsUser.add(primaryDepartment.getCode());
				xlsUser.add(user.getName());
				xlsUser.addAll(getExcelExportService().transformXslUsers(user));
				xlsUsersList.add(xlsUser);
			}
			final String TEMPFILENAME = getExcelExportService().exportExcel(xlsUsersList,
					(HttpServletRequest) getRequest().getContainerRequest(),
					(HttpServletResponse) getResponse().getContainerResponse());
			Link link = new Link("downloada") {

				@Override
				public void onClick()
				{
				}

				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					tag.put("href", getRequest().getContextPath() + TEMPFILENAME);
				}

			};
			add(link);
			Label label = new Label("label", "点击下载");
			link.add(label);
		} else {
			Link link = new Link("downloada") {

				@Override
				public void onClick()
				{
				}

			};
			add(link.setEnabled(false));
			Label label = new Label("label", "当前部门及其子部门下都没有用户，不能导出！");
			link.add(label);
		}
	}


}
