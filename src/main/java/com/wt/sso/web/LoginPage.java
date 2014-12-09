package com.wt.sso.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.hirisun.hea.api.HEA;
import com.hirisun.hea.api.HeaFactory;
import com.hirisun.hea.api.domain.Application;
import com.hirisun.hea.api.domain.AuthenticationProfile;
import com.hirisun.hea.api.domain.User;

public class LoginPage extends BasePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8670056463238969689L;

	private SSOUserInfo userinfo = new SSOUserInfo();

	private PasswordTextField passwordField;

	private TextField<String> usernameField;

	private static HEA uum = HeaFactory.hea();

	private String appLoginTitle;

	private AuthenticationProfile profile;

	private Application app;

	public LoginPage()
	{

		super();

		HttpServletRequest request = (HttpServletRequest) getRequest().getContainerRequest();

		final String uid = request.getParameter("uid");

		if (StringUtils.isBlank(uid)) {
			setResponsePage(new ErrorPage(getString("useridIsBlank")));
		}

		final String profileUUID = request.getParameter("pid");

		if (StringUtils.isBlank(profileUUID)) {
			setResponsePage(new ErrorPage(getString("profileuuidIsBlank")));
		}

		try {
			profile = uum.application().getProfile(profileUUID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (profile == null) {
			setResponsePage(new ErrorPage(getString("readProfileError")));
		} else {

			app = uum.application().get(profile.getApplicationUUID());
			appLoginTitle = app.getName() + getString("login");

			Form<?> form = new Form<Void>("form") {

				/**
			 * 
			 */
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit()
				{

					LoginValidator lvor = new LoginValidator();

					boolean checklogin = lvor.validate(profile, userinfo);

					// http client login

					if (!checklogin) {
						error(getString("loginError"));
					} else {
						User u = uum.user().getByID(uid);
						boolean success = false;

						try {
							success = uum.user().updateUserApplication(u.getUuid(),
									profile.getApplicationUUID(), userinfo.getUsername(),
									userinfo.getPassword());
						} catch (Exception e) {
							e.printStackTrace();
						}
						Application a = uum.application().get(profile.getApplicationUUID());
						Map<String, String> map = uum.application().getAttribute(a.getUuid(),
								new String[] { "useridKey", "passwordKey" });
						Map<String, String> map1 = new HashMap<String, String>();
						if (!String.valueOf(map.get("useridKey")).equals("null")) {
							map1.put(String.valueOf(map.get("useridKey")), userinfo.getUsername());
						}

						if (!String.valueOf(map.get("passwordKey")).equals("null")) {
							map1.put(String.valueOf(map.get("passwordKey")), userinfo.getPassword());
						}

						if (!map1.isEmpty()) {
							success = uum.user().updateUserAttributes(u.getUuid(), map1);
						}

						// save
						if (!success) {
							setResponsePage(new OkPage(getString("saveerror")));
						} else {
							if (StringUtils.isNotBlank(profile.getActionUrlServerSide())) {
								setResponsePage(new RedirectPage(lvor.getAppURL(profile, userinfo)));
							} else {
								setResponsePage(new OkPage(getString("saveok")));
							}
						}
					}
				}

			};

			add(form);

			form.add(new CloseButton("close"));

			form.add(new Label("sysNameLoginTitle", new PropertyModel<String>(appLoginTitle, null)));

			usernameField = new RequiredTextField<String>("username", new PropertyModel<String>(
					userinfo, "username"));
			usernameField.setLabel(Model.of(getString("username")));
			usernameField.add(new ErrorClassAppender());
			form.add(usernameField);

			form.add(new FeedbackPanel("usernameFeedback", new ComponentFeedbackMessageFilter(
					usernameField)));

			passwordField = new PasswordTextField("password", new PropertyModel<String>(userinfo,
					"password"));
			passwordField.setLabel(Model.of(getString("password")));
			passwordField.add(new ErrorClassAppender());
			form.add(passwordField);

			form.add(new FeedbackPanel("passwordFeedback", new ComponentFeedbackMessageFilter(
					passwordField)));

			form.add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(form)));
		}
	}
}
