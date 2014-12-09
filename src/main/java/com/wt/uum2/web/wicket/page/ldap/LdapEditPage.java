/*******************************************************************************
 * Copyright (c) 2012 by Hirisun Corporation all right reserved.
 * 2012-6-7 
 * 
 *******************************************************************************/
package com.wt.uum2.web.wicket.page.ldap;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.wt.uum2.domain.SyncED;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.web.wicket.form.ErrorClassAppender;
import com.wt.uum2.web.wicket.page.UUMBasePage;

/**
 * <pre>
 * 业  务  名:    
 * 功能说明:     
 * 编写日期:    2012-6-7
 * 作        者:    朱宇航 - Hirisun
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class LdapEditPage extends UUMBasePage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3077399910994757506L;

	@SpringBean
	private UUMService uumService;

	/**
	 * 
	 */
	public LdapEditPage()
	{
		this(new SyncED());
	}

	/**
	 * @param syncED syncED
	 */
	public LdapEditPage(final SyncED syncED)
	{
		Form<SyncED> form = new Form<SyncED>("form");
		add(form);

		String[] props = new String[] { "ldapFactory", "ldapUserName", "ldapUserPassWord",
				"ldapUrl", "baseDN", "userDN", "groupDN", "deptDN", "userKey", "groupKey",
				"deptKey", "userObjectClass", "deptObjectClass", "groupObjectClass" };

		for (String str : props) {
			addNotNullTextField(syncED, form, str);
		}

		form.add(new TextField<String>("dbDeptRoot",
				new PropertyModel<String>(syncED, "dbDeptRoot")));

		form.add(new SimpleFeedbackPanel("feedback").setOutputMarkupId(true));
		
		form.add(new Button("submit") {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2356889725722259825L;

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("class", "button");
			}

			@Override
			public void onSubmit()
			{
				String msg = "创建成功！";
				if (StringUtils.isBlank(syncED.getUuid())) {
					uumService.saveSyncED(syncED);
				} else {
					uumService.updateSyncED(syncED);
					msg = "更新成功！";
				}
				info(msg);
			}

		});
		form.add(new Link<String>("reset") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1209456697632413644L;

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("class", "button");
			}

			@Override
			public void onClick()
			{
				setResponsePage(new LdapEditPage(syncED));
			}

		});
		form.add(new Link<String>("goback") {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1474000221366220708L;

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("class", "button");
			}

			@Override
			public void onClick()
			{
				setResponsePage(LdapListPage.class);
			}
		});

	}

	
	/**
	 * 方法说明：addNotNullTextField
	 *
	 * @param syncED syncED
	 * @param form form
	 * @param elementName elementName
	 */
	private void addNotNullTextField(final SyncED syncED, Form<SyncED> form, String elementName)
	{
		
		TextField<String> textField = new RequiredTextField<String>(elementName, new PropertyModel<String>(
				syncED, elementName));

		textField.add(new ErrorClassAppender());

		form.add(textField);

		SimpleFeedbackPanel textFeedback = new SimpleFeedbackPanel(elementName + "Feedback",
				new ComponentFeedbackMessageFilter(textField));
		form.add(textFeedback.setOutputMarkupId(true));
	}
}