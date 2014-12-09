package com.wt.uum2.web.wicket;

import org.apache.wicket.Page;

import com.wt.sso.web.SSOLoginPage;
import com.wt.uum2.web.wicket.common.tree.UserLinkTreePage;
import com.wt.uum2.web.wicket.page.DeptListPage;
import com.wt.uum2.web.wicket.page.DeptTagPage;
import com.wt.uum2.web.wicket.page.IndexPage;
import com.wt.uum2.web.wicket.page.LoginPage;
import com.wt.uum2.web.wicket.page.SystemListPage;
import com.wt.uum2.web.wicket.page.SystemTagPage;
import com.wt.uum2.web.wicket.page.UserListPage;
import com.wt.uum2.web.wicket.page.application.AppTagPage;
import com.wt.uum2.web.wicket.page.audit.AuditPage;
import com.wt.uum2.web.wicket.page.ldap.LdapListPage;
import com.wt.uum2.web.wicket.page.user.CreateUserPage;
import com.wt.uum2.web.wicket.page.user.UserContentPage;
import com.wt.uum2.web.wicket.page.user.UserInfoPage;
import com.wt.uum2.web.wicket.page.user.password.ForgetUserPasswordPage;
import com.wt.uum2.web.wicket.page.user.password.UpdateUserPasswordPage;

/**
 * <pre>
 * 业务名:创建用户的application
 * 功能说明: 
 * 编写日期:	2011-10-9
 * 作者:	Li chenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class MainUUMApplication extends BaseUUMApplication
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.web.wicket.BaseUUMApplication#getHomePage()
	 */
	@Override
	public Class<? extends Page> getHomePage()
	{
		return CreateUserPage.class;
	}

	@Override
	protected void init()
	{
		super.init();
		// 创建用户
		mountPage("/createuser", CreateUserPage.class);
		// 用户列表noheader
		mountPage("/userlist", UserListPage.class);
		// 登录页面
		mountPage("/login", LoginPage.class);
		// 登陆后首页
		mountPage("/index", IndexPage.class);
		// 忘记密码
		mountPage("/forgetPassword", ForgetUserPasswordPage.class);
		// 更新密码
		mountPage("/updatePassword", UpdateUserPasswordPage.class);
		// 审计页面
		mountPage("/audit", AuditPage.class);
		// 部门
		mountPage("/dept", DeptTagPage.class);
		// 部门列表noheader
		mountPage("/deptlist", DeptListPage.class);
		// 应用列表
		mountPage("/app", AppTagPage.class);
		// 用户内容页面
		mountPage("/usercontent", UserContentPage.class);
		// 系统配置
		mountPage("/system", SystemTagPage.class);
		// 系统配置
		mountPage("/syslist", SystemListPage.class);
		// 员工自助
		mountPage("/editUser", UserInfoPage.class);
		// ldap配置列表
		mountPage("/ldaplist", LdapListPage.class);

		mountPage("/usertree", UserLinkTreePage.class);
		// mountPage("/userdepttree", UserDeptTreePage.class);

		// sso-ext
		mountPage("/ssologin", LoginPage.class);
		mountPage("/ssoflow", SSOLoginPage.class);

	}

}
