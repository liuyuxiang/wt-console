package com.wt.hea.rbac.struts.action;

import javax.servlet.http.HttpServletRequest;

import com.hirisun.components.data.DateUtils;
import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.model.BehaveLog;
import com.wt.hea.common.util.WebUtil;
import com.hirisun.hea.api.domain.User;

/**
 * 所有RBAC接口类注入此基类
 * 
 * @author 袁明敏
 * 
 */
public abstract class BaseAction extends DispatchAction
{
	/**
	 * 保存访问日志
	 * 
	 * @param request
	 *            请求
	 * @param logger
	 *            发生日志对象
	 * @param remark
	 *            日志信息
	 */
	protected void saveUserAccessLog(HttpServletRequest request, Logger logger, String remark)
	{
		BehaveLog acclog = new BehaveLog();
		acclog.setAccessip(WebUtil.getIpAddr(request));
		acclog.setAccesstime(DateUtils.getCurrDate(null));
		acclog.setAccesstype("1");
		acclog.setIndexname(null);
		User user = WebUtil.getSessionUser(request);
		acclog.setUsername(user.getName());
		acclog.setUseruuid(user.getUuid());
		acclog.setRemark(remark);
		logger.saveLog(acclog);
	}

	protected String xmlEnCode(String indexname)
	{
		return indexname.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll("'", "&apos;")
				.replaceAll("\"", "&quot;").replaceAll(">", "&gt;");
	}
}
