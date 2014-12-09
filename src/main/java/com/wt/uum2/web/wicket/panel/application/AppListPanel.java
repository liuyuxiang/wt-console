package com.wt.uum2.web.wicket.panel.application;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.UserApplication;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxFallbackDefaultDataTable;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;
import com.wt.uum2.web.wicket.panel.dept.NumPropertyColumn;

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
public class AppListPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private UUMAjaxFallbackDefaultDataTable datatable;
	/**
	 * 
	 */
	private int datastart = 0;

	/**
	 * @param id
	 *            id
	 * @param application
	 *            application
	 */
	public AppListPanel(String id, Application application)
	{
		super(id);
		boolean visible = false;
		if (isModifyGroup(loginUser)) {
			visible = true;
		}
		AppListToolbarPanel appListToolbarPanel = new AppListToolbarPanel("appListToolbarPanel",
				application, visible);

		add(appListToolbarPanel);

		add(getDataTable(application));

	}

	/**
	 * 方法说明：
	 * 
	 * @param application
	 *            application
	 * @return UUMAjaxFallbackDefaultDataTable
	 */
	protected UUMAjaxFallbackDefaultDataTable getDataTable(final Application application)
	{
		UserPageResult upr = getUUMService().getUsersUnderApplication(1, 15, application);

		datatable = new UUMAjaxFallbackDefaultDataTable("userListDataTable", createColumns(),
				upr.getList(), upr.getPager(), new GotoPageCallback() {

					public void gotoPage(AjaxRequestTarget target, Pager pager)
					{
						datastart = pager.getDataStart();
						replaceWithDataTable(target,
								getUUMService().getUsersUnderApplication(1, 15, application), this);
					}
				});
		return datatable;
	}

	/**
	 * 方法说明：
	 * 
	 * @return List
	 */
	protected List<PropertyColumn> createColumns()
	{
		List<PropertyColumn> columns = new ArrayList<PropertyColumn>();

		columns.add(new NumPropertyColumn(Model.of("序号"), null, datastart));
		
		columns.add(new PropertyColumn<UserApplication>(Model.of("用户ID"), "user.id"));
		columns.add(new PropertyColumn<UserApplication>(Model.of("姓名"), "user.name"));
		columns.add(new PropertyColumn<UserApplication>(Model.of("所在部门"), "user.primaryDepartment.path"));
		columns.add(new PropertyColumn<UserApplication>(Model.of("登录名"), "mappendUserid"));
		columns.add(new PropertyColumn<UserApplication>(Model.of("登录状态"), "loginable"));
		return columns;
	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param newResult
	 *            newResult
	 * @param callback
	 *            callback
	 */
	public void replaceWithDataTable(AjaxRequestTarget target, UserPageResult<Object> newResult,
			final GotoPageCallback callback)
	{

		UUMAjaxFallbackDefaultDataTable newUserListDataTable = new UUMAjaxFallbackDefaultDataTable(
				datatable.getId(), createColumns(), newResult.getList(), newResult.getPager(),
				callback);
		datatable.replaceWith(newUserListDataTable);
		datatable = newUserListDataTable;
		target.add(newUserListDataTable);
	}
}
