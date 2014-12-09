package com.wt.uum2.web.wicket.panel.datatable;

import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.OddEvenItem;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.wt.uum2.web.wicket.panel.UUMSortableListDataProvider;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;

/**
 * <pre>
 * 业务名:UUM的datatable
 * 功能说明: 
 * 编写日期:	2011-11-3
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
public class UUMAjaxFallbackDefaultDataTable<T> extends DataTable<T>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 * @param columns
	 *            columns
	 * @param dataProvider
	 *            dataProvider
	 * @param pager
	 *            pager
	 * @param callback
	 *            callback
	 */
	public UUMAjaxFallbackDefaultDataTable(final String id, final List<IColumn<T>> columns,
			final IDataProvider<T> dataProvider, Pager pager, GotoPageCallback callback)
	{
		super(id, columns, dataProvider, pager.getPageSize());
		setOutputMarkupId(true);
		setVersioned(false);
		addTopToolbar(new AjaxFallbackHeadersToolbar(this, (ISortStateLocator) dataProvider));
		addBottomToolbar(new NoRecordsToolbar(this, Model.of("没有记录！")));
		addBottomToolbar(new UUMAjaxNavigationToolbar(this, columns.size(), pager, callback));
		// addBottomToolbar(new AjaxNavigationToolbar(this));
	}

	/**
	 * @param id
	 *            id
	 * @param columns
	 *            columns
	 * @param list
	 *            list
	 * @param pager
	 *            pager
	 * @param callback
	 *            callback
	 */
	public UUMAjaxFallbackDefaultDataTable(final String id, final List<IColumn<T>> columns,
			final List list, Pager pager, GotoPageCallback callback)
	{
		super(id, columns, new UUMSortableListDataProvider(list), pager.getPageSize());
		setOutputMarkupId(true);
		setVersioned(false);
		addTopToolbar(new AjaxFallbackHeadersToolbar(this, new UUMSortableListDataProvider(list)));
		addBottomToolbar(new NoRecordsToolbar(this, Model.of("没有记录！")));
		addBottomToolbar(new UUMAjaxNavigationToolbar(this, columns.size(), pager, callback));
		// addBottomToolbar(new AjaxNavigationToolbar(this));
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable#newRowItem(java.lang.String, int, org.apache.wicket.model.IModel)
	 */
	@Override
	protected Item<T> newRowItem(final String id, final int index, final IModel<T> model)
	{
		return new OddEvenItem<T>(id, index, model);
	}
}
