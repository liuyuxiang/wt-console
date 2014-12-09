package com.wt.uum.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.hirisun.components.security.lcta.LctaContextHolder;
import com.hirisun.components.security.shiro.filter.sso.LctaSsoFilter;

public class LCTASSOFilterZDJ extends LctaSsoFilter {

	/**
	 * 匿名用户名
	 */
	private static final String ANONYMOUS_USER_NAME = "anonymous";
	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLog(LCTASSOFilterZDJ.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		String lctaUserid = LctaContextHolder.getContext().getUserid();
		Subject shiroSubject = SecurityUtils.getSubject();
		Subject subject = null;
		if (StringUtils.isNotBlank(request.getParameter("method"))) {
			String method = request.getParameter("method");
			if (StringUtils.equals(method, "logout")) {
				SecurityUtils.getSubject().logout();
				subject = createAnonymousSubject(request, response);
			} else if (StringUtils.equals(method, "login")) {
				if (StringUtils.isNotBlank(request.getParameter("u"))
						&& StringUtils.isNotBlank(request.getParameter("p"))) {
					String username = request.getParameter("u");
					String password = request.getParameter("p");

					if (SecurityUtils.getSubject().getPrincipal() == null
							|| !StringUtils.equals(SecurityUtils.getSubject()
									.getPrincipal().toString(), username)) {
						UsernamePasswordToken token = new UsernamePasswordToken(
								username, password);
						try {
							SecurityUtils.getSubject().login(token);
							lctaUserid = username;
						} catch (AuthenticationException e) {
							LOGGER.error("Error authenticating. LoginServlet");
						}
					}
				}
			}

		}
		if (!isAnonymous(shiroSubject)) {
			// 如果SHIRO现在有人登录
			if (!lctaUserid.equals(shiroSubject.getPrincipal().toString())) {
				// 如果两个用户不相等生成单点Subject(LCTA)
				LOGGER.debug("lctaUserid != shiroSubject =>true");
				//lctaUserid = shiroSubject.getPrincipal().toString();
				subject = createSubject(lctaUserid, true, "LCTA_REALM",
						request, response);
			}
		} else if (ANONYMOUS_USER_NAME.equals(lctaUserid)) { // 如果LCTA是匿名登录
			LOGGER.debug("lctaUserid-anonymous=true");
			subject = createAnonymousSubject(request, response);
		} else {
			LOGGER.debug("lctaUserid != shiroSubject =>true");
		}
		if (subject != null) {
			LOGGER.debug("sso======subject" + subject.getPrincipal());
			saveSubject(subject);
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}

}
