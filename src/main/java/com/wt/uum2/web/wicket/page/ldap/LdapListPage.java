/*******************************************************************************
 * Copyright (c) 2012 by Hirisun Corporation all right reserved.
 * 2012-6-6 
 * 
 *******************************************************************************/
package com.wt.uum2.web.wicket.page.ldap;

import nak.nsf.pager.Pager;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;

import com.hirisun.components.web.wicket.modal.LazyLoadModalWindow;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.SyncED;
import com.wt.uum2.web.wicket.page.UUMBasePage;
import com.wt.uum2.web.wicket.panel.ConfirmationAnswer;
import com.wt.uum2.web.wicket.panel.YesNoPanel;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;
import com.wt.uum2.web.wicket.panel.ldap.LdapListDataTable;

/**
 * <pre>
 * 业  务  名:    
 * 功能说明:     
 * 编写日期: 2012-6-6
 * 作者: 朱宇航 - Hirisun
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class LdapListPage extends UUMBasePage
{

	/**
     * 
     */
	private static final long serialVersionUID = 8209247872997194568L;

	private Form<Void> form;
	/**
	 * userPageResult
	 */
	private UserPageResult ldapPageResult;
	/**
	 * 
	 */
	private LdapListDataTable ldapListDataTable;

	/**
	 * 
	 */
	private int pagesize;

	/**
	 * 
	 */

	public LdapListPage()
	{
		pagesize = 15;
		initForm();
	}

	/**
	 * 方法说明：初始化表单
	 * 
	 */
	public void initForm()
	{

		final LazyLoadModalWindow deleteModal = new LazyLoadModalWindow("deleteModal") {

			@Override
			public Component lazyCreateContent()
			{
				return null;
			}
		};
		deleteModal.setCookieName("deleteModal");
		deleteModal.setMinimalWidth(200);
		deleteModal.setMinimalHeight(100);
		deleteModal.setInitialWidth(200);
		deleteModal.setInitialHeight(100);
		deleteModal.setResizable(false);
		add(deleteModal);

		form = new Form<Void>("form");
		add(form);
		AjaxButton addButton = new AjaxButton("addLdap") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				setResponsePage(LdapEditPage.class);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{

			}
			
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
					tag.put("class", "button");
			}

		};

		form.add(addButton);
		AjaxButton removeButton = new AjaxButton("removeLdap") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				final ConfirmationAnswer ANSWER = new ConfirmationAnswer(false);
				deleteModal.setContent(new YesNoPanel(deleteModal.getContentId(), "您确实要删除所选项吗?",
						deleteModal, ANSWER));
				deleteModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
					public void onClose(AjaxRequestTarget target)
					{
						if (ANSWER.isAnswer()) {
							for (SyncED syncED : ldapListDataTable.getAddList()) {
								getUUMService().deleteSyncED(syncED);
							}
							ldapPageResult = getUUMService().getSyncED(
									ldapListDataTable.getPager().getCurrentPage(),
									ldapListDataTable.getPager().getPageSize());
							replaceWithDataTable(target, ldapPageResult,
									ldapListDataTable.getCallback());
						}
					}
				});
				deleteModal.show(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{

			}
			
			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("class", "button");
			}

		};
		form.add(removeButton);

		ldapPageResult = getUUMService().getSyncED(1, pagesize);

		ldapListDataTable = new LdapListDataTable("ldapListDataTable", ldapPageResult,
				new GotoPageCallback() {
					public void gotoPage(AjaxRequestTarget target, Pager pager)
					{
						ldapPageResult = getUUMService().getSyncED(pager.getCurrentPage(),
								pager.getPageSize());
						replaceWithDataTable(target, ldapPageResult,
								ldapListDataTable.getCallback());

					}
				});

		form.add(ldapListDataTable.setOutputMarkupId(true));

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
	public void replaceWithDataTable(AjaxRequestTarget target, UserPageResult<SyncED> newResult,
			final GotoPageCallback callback)
	{
		LdapListDataTable newLdapListDataTable = new LdapListDataTable(ldapListDataTable.getId(),
				newResult, callback);
		ldapListDataTable.replaceWith(newLdapListDataTable);
		ldapListDataTable = newLdapListDataTable;
		target.add(newLdapListDataTable);
	}

}
