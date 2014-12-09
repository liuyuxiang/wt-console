package com.wt.uum2.web.wicket.page.user.password;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.util.string.StringValue;

import com.wt.uum2.web.wicket.page.UUMBasePage;
import com.wt.uum2.web.wicket.panel.user.password.UpdateUserPasswordPanel;

/**
 * <pre>
 * 业务名:重置密码
 * 功能说明: 包括门户上忘记密码后的修改密码、门户登录后的修改密码、系统的修改密码
 * 编写日期:	2011-10-28
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UpdateUserPasswordPage extends UUMBasePage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3929828644041273450L;

	/**
	 * 构造函数
	 */
	public UpdateUserPasswordPage()
	{
		StringValue userid = getRequest().getRequestParameters().getParameterValue("userid");// 参数用户ID
		StringValue userCn = getRequest().getRequestParameters().getParameterValue("userCn");
		ModifyPasswordType type = ModifyPasswordType.valueOfString(getRequest()
				.getRequestParameters().getParameterValue("type").toString());// 参数区分来源
		StringValue key = getRequest().getRequestParameters().getParameterValue("key");
		final StringValue url = getRequest().getRequestParameters().getParameterValue("url");
		StringValue flag1 = getRequest().getRequestParameters().getParameterValue("flag");// 当类型是portal时重庆会用到的参数

		add(new UpdateUserPasswordPanel("updatepwd", userid, userCn, type, key, flag1.toString()) {

			@Override
			public void goTo(AjaxRequestTarget target)
			{
				if (!url.isEmpty()) {
					setResponsePage(new RedirectPage(url.toString()));
				}

			}
			
		});
	}

}
