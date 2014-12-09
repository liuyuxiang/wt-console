package com.wt.uum.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.hirisun.components.security.shiro.filter.sso.HeaderSsoFilter;

/**
 * <pre>
 * 业务名: 单点登录
 * 功能说明: 北京（地市）单点登录
 * 编写日期:	2011-12-7
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class HeaderSsoFilterSD extends HeaderSsoFilter
{

	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLog(HeaderSsoFilterSD.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
			Object mappedValue)
	{

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String headerUserid = httpRequest.getHeader(getHeaderName());

		LOGGER.debug("headerUserid=" + headerUserid);

		if (StringUtils.isBlank(headerUserid)) {
			return super.isAccessAllowed(httpRequest, response, mappedValue);
		}

		Subject shiroSubject = SecurityUtils.getSubject();

		LOGGER.debug("shiroSubject-Principal=" + shiroSubject.getPrincipal());
		Subject subject = null;

		if (!isAnonymous(shiroSubject)) {// 如果SHIRO现在有人登录
			if (!headerUserid.equals(shiroSubject.getPrincipal().toString())) { // 如果两个用户不相等生成单点Subject(LCTA)
				LOGGER.debug("lctaUserid != shiroSubject =>true");
				shiroSubject.logout();
				subject = createSubject(headerUserid, true, "HEADER_REALM", request, response);
			}

		} else {
			LOGGER.debug("lctaUserid != shiroSubject =>true");
			subject = createSubject(headerUserid, true, "HEADER_REALM", request, response);
		}

		if (subject != null) {
			LOGGER.debug("sso======subject" + subject.getPrincipal());
			saveSubject(subject);
		}

		return true;

	}

}
