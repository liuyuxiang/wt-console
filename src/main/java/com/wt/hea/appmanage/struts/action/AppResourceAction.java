package com.wt.hea.appmanage.struts.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hirisun.components.data.validate.Validator;
import com.wt.hea.appmanage.struts.form.AppManageForm;
import com.wt.hea.common.model.Condition;
import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.ResourceSite;
import com.wt.hea.webbuilder.model.ThemeDefinition;

/**
 * 
 * <pre>
 * 业务名:action
 * 功能说明: 
 * 编写日期:	2011-9-20
 * 作者:	Mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class AppResourceAction extends BaseAction
{
	/**
	 * 
	 */
	private final static String LOGOTYPE = "05";

	/**
	 * 
	 */
	private final static String BANNERTYPE = "07";

	/**
	 * 方法说明：
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
	 *             Exception
	 */
	public ActionForward saveResource(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String forward = request.getParameter("forward");
		AppManageForm amf = (AppManageForm) form;
		ResourceSite resource = amf.getResource();
		resource = this.resourceService.update(resource);
		String message = "<script>"
				+ "window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId="
				+ resource.getAppId() + "';</script>";
		if (LOGOTYPE.equals(forward) || BANNERTYPE.equals(forward)) {
			message = "<script>window.location = 'heaAppResourceAction.hea?action=resourceList&appId="
					+ resource.getAppId() + "&resType=" + resource.getResType() + "';</script>";
		}
		request.setAttribute("message", message);
		return mapping.findForward("addlogo");
	}

	/**
	 * 方法说明：
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
	public ActionForward deleteResource(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		String appId = request.getParameter("appId");
		String forward = request.getParameter("forward");
		if (id != null && !"".equals(id)) {
			this.resourceService.deleteById(id);
		}
		String message = "<script>"
				+ "window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId=" + appId
				+ "'";
		if (BANNERTYPE.equals(forward) || LOGOTYPE.equals(forward)) {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(forward);
		}
		request.setAttribute("message", message);
		return mapping.findForward("addapp2page");
	}

	/**
	 * 方法说明：
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
	public ActionForward toUpdateResource(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		String appId = request.getParameter("appId");
		if (id != null && !"".equals(id)) {
			ResourceSite resource = this.resourceService.findById(id);
			request.setAttribute("resource", resource);
			request.setAttribute("forward", resource.getResType());
		}
		request.setAttribute("appId", appId);
		return mapping.findForward("addlogo");
	}

	/**
	 * 方法说明：
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
	 *             Exception
	 */
	public ActionForward saveTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String forward = request.getParameter("forward");
		AppManageForm amf = (AppManageForm) form;
		ThemeDefinition td = amf.getThemeDef();
		td = this.themeDefinitionService.update(td);
		String message = "<script>"
				+ "window.location = 'heaAppManageAction.hea?action=toUpdateApp2&appId="
				+ td.getAppId() + "';</script>";
		if ("themelist".equals(forward)) {
			message = "<script>window.location = 'heaAppResourceAction.hea?action=themeList&appId="
					+ td.getAppId() + "';</script>";
		}
		request.setAttribute("message", message);
		return mapping.findForward("addtheme");
	}

	/**
	 * 方法说明：
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
	 *             Exception
	 */
	public ActionForward toUpdateTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String forward = request.getParameter("forward");
		String id = request.getParameter("id");
		if (!"".equals(id)) {
			ThemeDefinition td = this.themeDefinitionService.findById(id);
			request.setAttribute("themeDef", td);
		}
		request.setAttribute("forward", forward);
		return mapping.findForward("addtheme");
	}

	/**
	 * 方法说明：
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
	 *             Exception
	 */
	public ActionForward deleteTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String forward = request.getParameter("forward");
		String id = request.getParameter("id");
		if (!"".equals(id)) {
			this.themeDefinitionService.deleteById(id);
		}
		if ("themelist".equals(forward)) {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print("themelist");
		}
		return null;
	}

	/**
	 * 方法说明：
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
	 *             Exception
	 */
	public ActionForward themeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String appId = request.getParameter("appId");
		String currPage = request.getParameter("currPage");
		String perPageRecode = request.getParameter("perPage");
		Page<ThemeDefinition> themeList = null;
		if (currPage != null && perPageRecode != null) {
			if ("".equals(currPage)) {
				currPage = "0";
			}
			if ("".equals(perPageRecode)) {
				perPageRecode = "10";
			}
		} else {
			currPage = "0";
			perPageRecode = "10";
		}
		themeList = new Page<ThemeDefinition>(Integer.valueOf(perPageRecode),
				Integer.valueOf(currPage));
		themeList.addConditions(Condition.getInstance().eq("appId", appId));
		themeList = this.themeDefinitionService.loadPage(themeList);
		request.setAttribute("themes", themeList);
		return mapping.findForward("themelist");
	}

	/**
	 * 方法说明：
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward ActionForward
	 * @throws Exception
	 *             Exception
	 */
	public ActionForward resourceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String appId = request.getParameter("appId");
		String resType = request.getParameter("resType");
		String currPage = request.getParameter("currPage");
		String perPageRecode = request.getParameter("perPage");

		if (currPage != null && perPageRecode != null) {
			if ("".equals(currPage) || !Validator.isNumeric(currPage)) {
				currPage = "0";
			}
			if ("".equals(perPageRecode) || !Validator.isNumeric(perPageRecode)) {
				perPageRecode = "10";
			}
		} else {
			currPage = "0";
			perPageRecode = "10";
		}
		Page<ResourceSite> resources = new Page<ResourceSite>(Integer.valueOf(perPageRecode),
				Integer.valueOf(currPage));
		resources.addConditions(Condition.getInstance().eq("resType", resType).eq("appId", appId));
		resources = this.resourceService.loadPage(resources);
		request.setAttribute("resources", resources);
		return mapping.findForward(resType);
	}
}
