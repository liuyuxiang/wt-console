package com.wt.uum2.web.wicket.panel.dept;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

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
public class NumPropertyColumn extends PropertyColumn<Integer>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int datastart;

	/**
	 * @param displayModel
	 *            displayModel
	 * @param propertyExpression
	 *            propertyExpression
	 * @param datastart
	 *            datastart
	 */
	public NumPropertyColumn(IModel displayModel, String propertyExpression, int datastart)
	{
		super(displayModel, propertyExpression);
		this.datastart = datastart;
	}

	/**
	 * @param displayModel
	 *            displayModel
	 * @param sortProperty
	 *            sortProperty
	 * @param propertyExpression
	 *            propertyExpression
	 * @param datastart
	 *            datastart
	 */
	public NumPropertyColumn(IModel displayModel, String sortProperty, String propertyExpression,
			int datastart)
	{
		super(displayModel, sortProperty, propertyExpression);
		this.datastart = datastart;
	}

	@Override
	public void populateItem(Item item, String componentId, IModel rowModel)
	{
		item.add(new Label(componentId, Model.of(((Item) item.getParent().getParent()).getIndex()
				+ datastart + 1)));

	}

}
