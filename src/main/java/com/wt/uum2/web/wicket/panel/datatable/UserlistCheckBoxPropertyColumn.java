package com.wt.uum2.web.wicket.panel.datatable;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import com.hirisun.components.web.wicket.table.CheckBoxPropertyColumn;

/**
 * <pre>
 * 业务名:复选框列
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
 * @param <User>
 *            用户
 */
public class UserlistCheckBoxPropertyColumn<User> extends CheckBoxPropertyColumn<User>
{
	/**
	 * 
	 */
	private WebMarkupContainer visibleselecteduser;

	/**
	 * @param userlistgroup
	 *            userlistgroup
	 * @param visibleselecteduser
	 *            userlistgroup
	 */
	public UserlistCheckBoxPropertyColumn(List<User> userlistgroup,WebMarkupContainer visibleselecteduser) {
		
		super(userlistgroup);
		this.visibleselecteduser=visibleselecteduser;
	}

	@Override
	public void populateItem(Item<ICellPopulator<User>> cellItem,
			String componentId, IModel<User> rowModel) {
		super.populateItem(cellItem, componentId, rowModel);
	}
	@Override
	protected void onUpdateCheckBoxItem(AjaxRequestTarget target,
			AjaxCheckBox checkbox, IModel<User> rowModel) {
		super.onUpdateCheckBoxItem(target, checkbox, rowModel);
		if(CollectionUtils.isNotEmpty(getList())){
			visibleselecteduser.get(0).setDefaultModelObject(getList().size());
			target.add(visibleselecteduser.setVisible(true));
		}else{
			target.add(visibleselecteduser.setVisible(false));
		}
	}

	@Override
	protected void onUpdateHearder(AjaxRequestTarget target) {
		if(CollectionUtils.isNotEmpty(getList())){
			visibleselecteduser.get(0).setDefaultModelObject(getList().size());
			target.add(visibleselecteduser.setVisible(true));
		}else{
			target.add(visibleselecteduser.setVisible(false));
		}
	}


	
}
