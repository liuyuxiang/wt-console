package com.wt.uum2.web.wicket.panel.datatable;

import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-11-25
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 * 
 * @param <T>
 *            类型
 */
public class IsExistCurrentDeptColumn<T> extends PropertyColumn<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Department dept;

	/**
	 * @param displayModel
	 *            displayModel
	 * @param propertyExpression
	 *            displayModel
	 * @param dept
	 *            dept
	 */
	public IsExistCurrentDeptColumn(IModel<String> displayModel, String propertyExpression,Department dept)
	{
		super(displayModel, propertyExpression);
		this.dept=dept;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel)
	{
		Map<String,Object> map=(Map<String,Object>)rowModel.getObject();
		User u=(User)map.get("userObject");
		item.add(new Label(componentId, u.getPrimaryDepartmentUUID().equals(dept.getUuid()) ? "已加入" : "否"));
	}
	
}
