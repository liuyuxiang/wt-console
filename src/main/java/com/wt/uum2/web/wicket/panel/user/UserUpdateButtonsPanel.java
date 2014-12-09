/*******************************************************************************
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-9-6 
 * 
 *******************************************************************************/
package com.wt.uum2.web.wicket.panel.user;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.InlineFrame;
import org.apache.wicket.markup.html.pages.RedirectPage;

import com.hirisun.components.web.wicket.link.AjaxWaitingLink;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.page.user.password.ModifyPasswordType;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.user.password.UpdateUserPasswordPanel;

/**
 * <pre>
 * 业务名:修改用户时按钮面板
 * 功能说明: 
 * 编写日期:	2011-9-6
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class UserUpdateButtonsPanel extends BaseUUMPanel
{
	/**
	 * 上下文
	 */
	private final String contextPath = getRequest().getContextPath();

	/**
	 * 
	 */
	private InlineFrame inlineFrame;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3334910627940623588L;

	/**
	 * @param id
	 *            id
	 * @param user
	 *            用户
	 */
	public UserUpdateButtonsPanel(String id, User user)
	{
		super(id);
		initComponents(user);
	}

	/**
	 * 方法说明：初始化
	 * 
	 * @param user
	 *            user
	 */
	protected void initComponents(final User user)
	{
		RedirectPage page = new RedirectPage(contextPath + "/audit/resourceLog.nsf?uuid="
				+ user.getUuid());
		inlineFrame = new InlineFrame("logs", page);
		inlineFrame.setOutputMarkupId(true);
		inlineFrame.setOutputMarkupPlaceholderTag(true);
		add(inlineFrame.setVisible(false));
		AjaxWaitingLink resetPage = new AjaxWaitingLink("resetPage") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				reset(target);
			}
		};
		add(resetPage);
		AjaxWaitingLink resetPassword = new AjaxWaitingLink("resetPassword") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				final WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");
				mainContainer.addOrReplace(new UpdateUserPasswordPanel("mainPanel", user,
						ModifyPasswordType.system) {

					@Override
					public void goTo(AjaxRequestTarget target)
					{
						mainContainer.addOrReplace(new CreateUserPanel("mainPanel", user, null));
						target.add(mainContainer);
					}

				});
				target.add(mainContainer);

			}
		};
		add(resetPassword);
		revisabilityUserPasswordHandle(resetPassword, user);

		add(new AjaxWaitingLink("openResourceLog") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (!inlineFrame.isVisible()) {
					target.add(inlineFrame.setVisible(true));
				}
				target.add(setEnabled(true));
			}
		}).setOutputMarkupId(true);
		add(new AjaxWaitingLink("closeResourceLog") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (inlineFrame.isVisible()) {
					target.add(inlineFrame.setVisible(false));
				}
				target.add(setEnabled(true));
			}
		}).setOutputMarkupId(true);

	}

	/**
	 * 方法说明：reset
	 * 
	 * @param target
	 *            target
	 */
	public abstract void reset(AjaxRequestTarget target);
}
