package com.wt.hea.webbuilder.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.webbuilder.model.SiteManage;
import com.wt.hea.webbuilder.model.TemplateLayout;
import com.wt.hea.webbuilder.model.ThemeDefinition;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 个性化信息初始化
 * 编写日期:	2011-3-29
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：yinhaoqi
 *    修改内容：此类暂停使用
 * </pre>
 */
public class PersonalInitAction extends DispatchAction
{

	/**
	 * 
	 * 方法说明： 初始化个性化信息
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
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{

		List<TemplateLayout> tlList = this.templateLayoutService.findAll();
		List<SiteManage> smList = this.siteManageService.findAll();
		List<ThemeDefinition> themeList = this.themeDefinitionService.findAll();
		request.getSession().setAttribute("themeList", themeList);
		request.getSession().setAttribute("tlList", tlList);
		request.getSession().setAttribute("smList", smList);
		return mapping.findForward("initOver");
	}

}
