package com.wt.hea.webbuilder.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.LayoutDefinition;
import com.hirisun.hea.api.domain.User;

/**
 * 个性化布局对象控制层<br>
 * 用于管理布局信息，添加；删除；查询；修改布局信息
 * 
 * @author xiaoqi
 * 
 */
public class LayoutDefinitionAction extends DispatchAction
{

	/**
	 * 
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

	/**
	 * 添加一个新的布局信息
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		return null;
	}

	/**
	 * 以List形式返回所有布局信息
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward findAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		List<LayoutDefinition> layoutDef = this.layoutDefinitionService.findAll();
		request.getSession().setAttribute("layoutDefList", layoutDef);
		String logMessage = "用户" + user.getName() + "浏览布局信息";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("themeDefinition");
	}
}
