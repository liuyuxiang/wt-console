/*******************************************************************************
 * Copyright (c) 2012 by Hirisun Corporation all right reserved.
 * 2012-6-6 
 * 
 *******************************************************************************/
package com.wt.uum2.web.wicket.panel.ldap;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.hirisun.components.web.wicket.table.AjaxLinkPropertyColumn;
import com.hirisun.components.web.wicket.table.CheckBoxPropertyColumn;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.SyncED;
import com.wt.uum2.web.wicket.page.ldap.LdapEditPage;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxFallbackDefaultDataTable;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;

/**
 * <pre>
 * 业  务  名:    
 * 功能说明:     
 * 编写日期:    2012-6-6
 * 作        者:    朱宇航 - Hirisun
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class LdapListDataTable extends BaseUUMPanel
{

	private UUMAjaxFallbackDefaultDataTable datatable;
	private GotoPageCallback callback;
	private Pager pager;
	private List<SyncED> addList = new ArrayList<SyncED>();

	public GotoPageCallback getCallback()
	{
		return callback;
	}

	public Pager getPager()
	{
		return pager;
	}

	/**
	 * @param id
	 *            id
	 */
	public LdapListDataTable(String id)
	{
		super(id);
	}

	public List<SyncED> getAddList()
	{
		return addList;
	}

	/**
	 * @param id
	 *            id
	 * @param ldapPageResult
	 *            ldapPageResult
	 * @param callback
	 *            callback
	 */
	public LdapListDataTable(String id, UserPageResult<SyncED> ldapPageResult,
			GotoPageCallback callback)
	{
		super(id);
		this.callback = callback;
		this.pager = ldapPageResult.getPager();

		createDataTable(addList, ldapPageResult, callback);
	}

	/**
	 * 方法说明：createDataTable
	 * 
	 * @param addList
	 *            addList
	 * @param ldapPageResult
	 *            ldapPageResult
	 * @param callback
	 *            callback
	 */
	public void createDataTable(List<SyncED> addList, final UserPageResult<SyncED> ldapPageResult,
			GotoPageCallback callback)
	{

		List<IColumn<SyncED>> columns = new ArrayList<IColumn<SyncED>>();
		columns.add(new AbstractColumn<SyncED>(Model.of("序号")) {
			public void populateItem(Item<ICellPopulator<SyncED>> cellItem, String componentId,
					IModel<SyncED> rowModel)
			{
				int num = (ldapPageResult.getPager().getCurrentPage() - 1)
						* ldapPageResult.getPager().getPageSize()
						+ ldapPageResult.getList().indexOf(rowModel.getObject()) + 1;
				cellItem.add(new Label(componentId, String.valueOf(num)));

			}

			@Override
			public String getCssClass()
			{
				return "";
			}

		});

		columns.add(new AjaxLinkPropertyColumn<SyncED>(Model.of("同步地址"), "ldapUrl") {

			@Override
			protected void onClick(IModel<SyncED> clicked, AjaxRequestTarget target)
			{
				setResponsePage(new LdapEditPage(clicked.getObject()));
			}
		});

		columns.add(new CheckBoxPropertyColumn(addList) {

		});
		datatable = new UUMAjaxFallbackDefaultDataTable("ldaplisttable", columns,
				ldapPageResult.getList(), ldapPageResult.getPager(), callback);
		add(datatable);
	}
}
