package com.wt.sso.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.ISecuritySettings;
import org.apache.wicket.util.crypt.ClassCryptFactory;
import org.apache.wicket.util.crypt.SunJceCrypt;

public class SSOExtApplication extends WebApplication
{

	static {
		System.setProperty("java.awt.headless", "true");
	}

	@Override
	protected void init()
	{
		getSecuritySettings().setCryptFactory(
				new ClassCryptFactory(SunJceCrypt.class, ISecuritySettings.DEFAULT_ENCRYPTION_KEY));

		getDebugSettings().setDevelopmentUtilitiesEnabled(true);
		mountPage("/login", LoginPage.class);
		mountPage("/ssoflow", SSOLoginPage.class);
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return SSOLoginPage.class;
	}

}
