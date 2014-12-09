package com.wt.hea.rbac.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hirisun.components.data.DateUtils;
import com.wt.hea.common.model.BehaveLog;
import com.wt.hea.rbac.model.Index;
import com.hirisun.hea.api.domain.User;

/**
 * WEB 层，日志对象控制器
 * 
 * @author 袁明敏
 * @see com.wt.hea.common.action.DispatchAction
 * @see com.wt.hea.common.action.BaseDispatchAction
 * @see com.wt.hea.rbac.service.AccesslogService
 * 
 */
public class AccessslogAction extends BaseAction
{
	/**
	 * 用户点击指标的日志,ajax方式
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward addLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String useruuid = request.getParameter("useruuid"); // ajax请求传进的用户主键入参
		User user = this.rbacService.getUserByUuid(useruuid);

		String indexuuid = request.getParameter("indexuuid"); // ajax请求传进的指标主键入参
		Index index = this.indexService.findById(indexuuid);

		String accessIp = request.getRemoteAddr();
		String accesstime = DateUtils.getCurrDate("yyyy-MM-dd HH:mm:ss");

		String accesstype = "0";

		BehaveLog accessLog = new BehaveLog();
		accessLog.setAccesstype(accesstype);
		accessLog.setIndexname(index.getIndexname());
		accessLog.setIndexuuid(index.getIndexuuid());
		accessLog.setUsername(user.getName());
		accessLog.setUseruuid(user.getUuid());
		accessLog.setAccessip(accessIp);
		accessLog.setAccesstime(accesstime);

		// this.accesslogService.save(accessLog);

		return null;
	}
}
