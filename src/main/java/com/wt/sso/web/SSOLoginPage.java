package com.wt.sso.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.request.RequestHandlerStack.ReplaceHandlerException;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.handler.RenderPageRequestHandler.RedirectPolicy;

import com.hirisun.hea.api.HEA;
import com.hirisun.hea.api.HeaFactory;
import com.hirisun.hea.api.domain.Application;
import com.hirisun.hea.api.domain.AuthenticationProfile;
import com.hirisun.hea.api.domain.User;
import com.hirisun.hea.api.domain.UserApplication;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-11-26
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SSOLoginPage extends BasePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4315370836697279538L;

	private final SSOUserInfo userinfo = new SSOUserInfo();

	private static HEA hea = HeaFactory.hea();

	private AuthenticationProfile profile;

	public SSOLoginPage()
	{

		super();

		HttpServletRequest request = (HttpServletRequest) getRequest().getContainerRequest();

		final String uid = request.getParameter("uid");

		if (StringUtils.isBlank(uid)) {
			setResponsePage(new ErrorPage(getString("useridIsBlank")));
			return;
		}

		final String profileUUID = request.getParameter("pid");

		if (StringUtils.isBlank(profileUUID)) {
			setResponsePage(new ErrorPage(getString("profileuuidIsBlank")));
			return;
		}

		try {
			profile = hea.application().getProfile(profileUUID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (profile == null) {
			throw new ReplaceHandlerException(new RenderPageRequestHandler(new PageProvider(
					new ErrorPage(getString("readProfileError"))), RedirectPolicy.NEVER_REDIRECT),
					true);
		}

		User user = hea.user().getByID(uid);
		if (user == null) {
			setResponsePage(new ErrorPage(getString("readUserError")));
			return;
		}

		Application app = hea.application().get(profile.getApplicationUUID());

		UserApplication ua = hea.application().getMappingUser(app.getUuid(), user.getUuid());

		if (ua == null) {
			setResponsePage(new ErrorPage(getString("readUserError")));
			return;
		}

		userinfo.setUsername(ua.getId());
		userinfo.setPassword(ua.getPassword());

		LoginValidator lvor = new LoginValidator();

		if (lvor.validate(profile, userinfo)) {
			setResponsePage(new RedirectPage(lvor.getAppURL(profile, userinfo)));
		} else {
			setResponsePage(new LoginPage());
		}

	}

}
