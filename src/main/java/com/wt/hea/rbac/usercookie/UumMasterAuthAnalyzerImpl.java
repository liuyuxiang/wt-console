package com.wt.hea.rbac.usercookie;

import javax.servlet.http.HttpServletRequest;

import com.hirisun.components.security.lcta.MasterAuthAnalyzer;
import com.wt.hea.common.util.WebUtil;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:获取session登录用户
 * 功能说明: 
 * 编写日期:	2011-4-27
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UumMasterAuthAnalyzerImpl implements MasterAuthAnalyzer
{

	/**
	 * 获取用户ID
	 * 
	 * @param request
	 *            request
	 * @return 用户ID
	 */
	public String analyze(HttpServletRequest request)
	{
		User user = WebUtil.getSessionUser(request);
		if (user != null)
			return user.getId();
		return null;
	}

}
