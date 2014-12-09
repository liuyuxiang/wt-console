package com.wt.uum.shiro.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hirisun.components.security.shiro.filter.sso.HeaderSsoFilter;
import com.hirisun.rd1.secret.Decryptor;

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
public class HeaderSsoFilterBJ extends HeaderSsoFilter
{

	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLog(HeaderSsoFilterBJ.class);

	/**
	 * cookieName
	 */
	private String cookieName;
	/**
	 * xaes2Decryptor
	 */
	private Decryptor xaes2Decryptor;

	/**
	 * 方法说明：
	 *
	 * @param cookieName cookieName
	 */
	public void setCookieName(String cookieName)
	{
		this.cookieName = cookieName;
	}

	/**
	 * 方法说明：
	 *
	 * @param xaes2Decryptor xaes2Decryptor
	 */
	public void setXaes2Decryptor(Decryptor xaes2Decryptor)
	{
		this.xaes2Decryptor = xaes2Decryptor;
	}

	@Override
	protected String getUserId(HttpServletRequest req)
	{

		String headerValue = super.getUserId(req);

		if (StringUtils.isNotBlank(headerValue)) {

			if (headerValue.indexOf(",") > 0) {
				String[] userids = headerValue.split(",");
				for (String uid : userids) {
					if (uid.startsWith("HQ_")) {
						return uid;
					}
				}

				LOGGER.debug("Can not get userId start with 'HQ_' from HTTP header ["
						+ getHeaderName() + "]");

				return null;
			} else {
				return headerValue;
			}
		} else if (StringUtils.isNotBlank(req.getParameter("user"))) {
			String tokenValue = req.getParameter("user");
			String tokenValueReadability = null;
			String userid = null;
			long time = 0L;
			if (tokenValue != null) {
				tokenValueReadability = this.xaes2Decryptor.decryptBase642String(this.cookieName,
						tokenValue);
				if (tokenValueReadability != null) {
					String[] tokens = tokenValueReadability.split(",");
					if (tokens.length == 2) {
						userid = tokens[0];
						time = Long.valueOf(tokens[1]).longValue();
						if (System.currentTimeMillis() - time < 600000L) {
							return userid;
						}
					}
				}
			}
			return null;
		} else {
			LOGGER.debug("Can not get value from HTTP header [" + getHeaderName() + "]");
			return null;
		}

	}

}
