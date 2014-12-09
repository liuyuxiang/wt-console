package com.wt.uum2.web.wicket.panel.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-9
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SystemTreePanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 */
	public SystemTreePanel(String id)
	{
		super(id);
		final SystemItem mainItem = new SystemItem("待办列表配置",
				"/taskCandidate/taskCandidateMain.nsf?userId=" + loginUser.getId());
		WebMarkupContainer systemConfigurationLink = new AjaxLink<Void>("systemConfigurationLink") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				SystemTreePanel.this.onClick(mainItem, target);
			}
		};
		add(systemConfigurationLink);
		systemConfigurationLink.add(new Label("systemConfigurationTitle", mainItem.getTitle()));

		List<SystemItem> itemsList = new ArrayList<SystemItem>();

		itemsList.add(new SystemItem("扩展属性配置", "/mainlist.nsf?menuId=3"));

		// itemsList.add(new SystemItem("LDAP同步源配置", "/sync/synMain.nsf?userId=" + loginUser.getId()));
		// itemsList.add(new SystemItem("LDAP同步源配置", LdapListPage.class,SystemItemType.page));
		// itemsList.add(new SystemItem("LDAP同步源配置", "/w/ldaplist"));

		// itemsList.add(new SystemItem("同步开关设置", SyncOnOffPanel.class));

		itemsList.add(new SystemItem("部门路径信息维护", "/maintenance.nsf"));
		itemsList.add(new SystemItem("资源排序", "/order/main.nsf"));

		itemsList.add(new SystemItem("审计", "/audit/auditMain.nsf"));

		itemsList.add(new SystemItem("用户审核", "/tasklist/userMain.nsf?userId=" + loginUser.getId()));
		itemsList.add(new SystemItem("部门审核", "/tasklist/deptMain.nsf?userId=" + loginUser.getId()));
		itemsList
				.add(new SystemItem("用户删除列表", "/tasklist/delMain.nsf?userId=" + loginUser.getId()));
		itemsList.add(new SystemItem("用户离职列表", "/tasklist/leftUserMain.nsf?userId="
				+ loginUser.getId()));
		itemsList.add(new SystemItem("服务器信息", AppStatusPanel.class));

		itemsList.add(new SystemItem("数据库信息", DBStatusPanel.class));

		ListView<SystemItem> items = new ListView<SystemItem>("items", itemsList) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<SystemItem> item)
			{
				final WebMarkupContainer titleLink = newLink("link", item.getModelObject());

				titleLink.add(newTitle("title", item.getModelObject()));
				item.add(titleLink);

			}

		};
		add(items);
		if (!getUUMService().isUserinSuperGroup(loginUser)) {
			systemConfigurationLink.setVisible(false);
			items.setVisible(false);
		}
	}

	/**
	 * 方法说明：newLink
	 * 
	 * @param linkId
	 *            linkId
	 * @param item
	 *            item
	 * @return WebMarkupContainer
	 */
	protected WebMarkupContainer newLink(final String linkId, final SystemItem item)
	{
		return new AjaxLink<Void>(linkId) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				SystemTreePanel.this.onClick(item, target);
			}

		};
	}

	/**
	 * 方法说明：newTitle
	 * 
	 * @param titleId
	 *            titleId
	 * @param item
	 *            item
	 * @return Component
	 */
	protected Component newTitle(final String titleId, SystemItem item)
	{
		return new Label(titleId, item.getTitle());
	}

	/**
	 * 方法说明：onClick
	 * 
	 * @param item
	 *            item
	 * @param target
	 *            target
	 */
	public void onClick(SystemItem item, AjaxRequestTarget target)
	{

	}
}
