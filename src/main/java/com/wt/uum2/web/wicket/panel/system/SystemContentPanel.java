package com.wt.uum2.web.wicket.panel.system;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.index.ContentPanel;

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
public class SystemContentPanel extends ContentPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 */
	public SystemContentPanel(String id)
	{
		super(id);
	}

	@Override
	protected Panel getTreePanel()
	{
		return new SystemTreePanel("treePanel") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(SystemItem item, AjaxRequestTarget target)
			{
				WebMarkupContainer container = getMainContainer();

				BaseUUMPanel mainPanel = null;

				if (item.isLinkUrl()) {
					mainPanel = new SystemPanel(getMainPanel().getId(), item.getLinkUrl());
					container.addOrReplace(mainPanel.setOutputMarkupId(true));
				} else if (item.isPanelClass()) {
					if (item.getPanelClass() == SyncOnOffPanel.class) {
						mainPanel = new SyncOnOffPanel(getMainPanel().getId());
					} else if (item.getPanelClass() == AppStatusPanel.class) {
						mainPanel = new AppStatusPanel(getMainPanel().getId());
					} else if (item.getPanelClass() == DBStatusPanel.class) {
						mainPanel = new DBStatusPanel(getMainPanel().getId());
					}
					if (mainPanel != null) {
						container.addOrReplace(mainPanel.setOutputMarkupId(true));
					}
					// } else if(item.isPageClass()){
					// container.addOrReplace(new LdapListPage().setOutputMarkupId(true));
				}

				target.add(container);
			}
		};
	}

	/*@Override
	protected void init()
	{
		Form<Void> contentForm = new Form<Void>("contentForm");
		add(contentForm);
		final WebMarkupContainer MAINCONTAINER = new WebMarkupContainer("mainContainer");
		contentForm.add(MAINCONTAINER.setOutputMarkupId(true));
		MAINCONTAINER.add(new WelcomePanel("mainPanel"));

		SystemTreePanel treePanel = new SystemTreePanel("treePanel") {

			*//**
			 * 
			 */
	/*
	private static final long serialVersionUID = 1L;

	@Override
	public void onClick(SystemItem item, AjaxRequestTarget target)
	{
	if (item.isLinkUrl()) {

		SystemPanel mainPanel = new SystemPanel("mainPanel", item.getLinkUrl());
		MAINCONTAINER.addOrReplace(mainPanel.setOutputMarkupId(true));
		target.add(MAINCONTAINER);
	} else if (item.isPanelClass() && item.getPanelClass() == SyncOnOffPanel.class) {
		SyncOnOffPanel mainPanel = new SyncOnOffPanel("mainPanel");
		MAINCONTAINER.addOrReplace(mainPanel.setOutputMarkupId(true));
		target.add(MAINCONTAINER);
	}



	}
	

	};
	contentForm.add(treePanel.setOutputMarkupId(true));
	


	}*/
}
