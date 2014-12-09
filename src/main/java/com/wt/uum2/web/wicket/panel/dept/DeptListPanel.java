package com.wt.uum2.web.wicket.panel.dept;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxFallbackDefaultDataTable;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;

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
public class DeptListPanel extends BaseUUMPanel
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
	 * @param department
	 *            department
	 */
	public DeptListPanel(String id, Department department)
	{
		super(id);
//		boolean visible = false;
//		if (isModifyGroup(loginUser)) {
//			visible = true;
//		}
		DeptListToolbarPanel deptListToolbarPanel = new DeptListToolbarPanel(
				"deptListToolbarPanel", department, getUUMService().isDepartmentManager(loginUser, department));

		add(deptListToolbarPanel);


		add(getDataTable(department));

	}
	
	/**
	 * 方法说明：
	 * 
	 * @param department
	 *            department
	 * @return UUMAjaxFallbackDefaultDataTable
	 */
	protected UUMAjaxFallbackDefaultDataTable getDataTable(final Department department)
	{
		UserPageResult upr = getUUMService().getSubDepartments(1, 15, department);

		datatable = new UUMAjaxFallbackDefaultDataTable("deptListDataTable", createColumns(),
				upr.getList(), upr.getPager(), new GotoPageCallback() {

					public void gotoPage(AjaxRequestTarget target, Pager pager)
					{
						datastart = pager.getDataStart();
						replaceWithDataTable(
								target,
								getUUMService().getSubDepartments(pager.getCurrentPage(),
										pager.getPageSize(), department), this);
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
		columns.add(new PropertyColumn<String>(Model.of("部门层级编码"), "deptCode"));
		columns.add(new PropertyColumn<String>(Model.of("部门名称"), "name"));
		columns.add(new PropertyColumn<String>(Model.of("部门ERP编码"), "code"));
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
	public void replaceWithDataTable(AjaxRequestTarget target,
			UserPageResult<Department> newResult, final GotoPageCallback callback)
	{

		UUMAjaxFallbackDefaultDataTable newUserListDataTable = new UUMAjaxFallbackDefaultDataTable(
				datatable.getId(), createColumns(), newResult.getList(), newResult.getPager(),
				callback);
		datatable.replaceWith(newUserListDataTable);
		datatable = newUserListDataTable;
		target.add(newUserListDataTable);
	}
}
