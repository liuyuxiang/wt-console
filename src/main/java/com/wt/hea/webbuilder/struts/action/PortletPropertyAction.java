package com.wt.hea.webbuilder.struts.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.model.Condition;
import com.wt.hea.common.model.Page;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.PortletProperty;
import com.wt.hea.webbuilder.struts.form.PortletPropertyForm;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-21
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PortletPropertyAction extends DispatchAction
{

	/**
	 * 获取日志文件实例
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

	/**
	 * 
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
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		PrintWriter out = response.getWriter();
		String portletId = request.getParameter("portletId");
		if (portletId != null) {
			try {
				PortletProperty pp = this.portletPropertyService.findById(portletId);
				this.portletPropertyService.delete(pp);
				out.print("1");
			} catch (Exception e) {
				e.printStackTrace();
				out.print(e.getMessage());
			}

		}
		out.flush();
		out.close();
		String logMessage = "用户" + user.getName() + "删除了portletId为的" + portletId + " portlet";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return null;
	}

	/**
	 * 
	 * 方法说明： 查找所有的portlet
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
	public ActionForward findAllPortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String temp = request.getParameter("curPage");
		String state = request.getParameter("state");
		String type = request.getParameter("type");

		int curPage = 0;
		if (temp != null && !temp.equals("")) {
			curPage = Integer.parseInt(temp);
		}
		Page<PortletProperty> p = new Page<PortletProperty>(5, curPage);

		Map<String, Object> params = new HashMap<String, Object>();

		if (type != null && !type.equals("null"))
			params.put("portletType", type);
		p.addConditions(Condition.getInstance().allEq(params));
		p = this.portletPropertyService.loadPage(p);

		request.setAttribute("state", state);
		request.setAttribute("data", p);
		String logMessage = "用户" + user.getName() + "浏览所有portlet信息";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("allPortlets");
	}

	/**
	 * 取出所有portlet并标识用户已关联的portlet
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
		List<PortletProperty> portletPropertyList = this.portletPropertyService.findAll();

		User user = WebUtil.getSessionUser(request);
		String userId = user.getId();
		List<PortletProperty> ppfList = new ArrayList<PortletProperty>();
		boolean isAdded = false;
		for (PortletProperty pp : portletPropertyList) {
			isAdded = this.userPersonalInfoService.isAdded(userId, pp.getPortletId(),
					request.getParameter("pageId"));

			if (isAdded) {
				pp.setIsAdd("1");
				ppfList.add(pp);
			} else {
				pp.setIsAdd("0");
				ppfList.add(pp);
			}

		}
		request.setAttribute("pageId", request.getParameter("pageId"));
		request.setAttribute("portletList", ppfList);
		String logMessage = "用户" + user.getName() + "查看了页面所有可用的portlet（标识已添加的portlet）";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("portletList");
	}

	/**
	 * 
	 * 方法说明： 根据id查找portlet
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
	public ActionForward findById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String portletId = request.getParameter("portletId");
		String watchType = request.getParameter("watchType");
		User user = WebUtil.getSessionUser(request);
		PortletProperty port = null;
		if (portletId != null) {
			port = this.portletPropertyService.findById(portletId);
		}
		String logMessage = "用户" + user.getName() + "查看了portletId为的" + portletId + " portlet";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		if (port != null) {
			if (watchType.equalsIgnoreCase("object")) {
				request.setAttribute("portlet", port);
			} else if (watchType.equalsIgnoreCase("json")) {
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				JSONObject jo = JSONObject.fromObject(port);
				out.print(jo.toString());
				out.flush();
				out.close();
				return null;
			}
		}

		return null;
	}

	/**
	 * 
	 * 方法说明： 持久化一个portlet
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
		User user = WebUtil.getSessionUser(request);
		PrintWriter out = response.getWriter();
		try {

			PortletPropertyForm f = (PortletPropertyForm) form;
			PortletProperty p = f.getPortletProperty();

			// String portletType = request.getParameter("portletProperty.portletType");
			this.portletPropertyService.save(this.decode(p));

			out.print("1");
			out.flush();
			out.close();
			String logMessage = "用户" + user.getName() + "新增portlet" + p.getPortletName();

			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 方法说明： 更新portlet信息
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
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		PrintWriter out = response.getWriter();
		try {

			PortletPropertyForm f = (PortletPropertyForm) form;
			PortletProperty p = f.getPortletProperty();
			this.portletPropertyService.update(this.decode(p));
			out.print("1");
			String logMessage = "用户" + user.getName() + "更新了portletName为的" + p.getPortletName()
					+ " portlet";

			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
		}
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 
	 * 方法说明： 删除portlet信息
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
	public ActionForward deleteById(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		this.portletPropertyService.deleteById(id);

		return null;
	}

	/**
	 * modifyby:xiaoqi 修改保存的类型为01...
	 * 
	 * @param p
	 *            需要进行转码的portlet对象
	 * @return 转码后的portlet对象
	 */
	private PortletProperty decode(PortletProperty p)
	{

		try {
			p.setPortletName(java.net.URLDecoder.decode(p.getPortletName(), "utf-8"));
			p.setPortletDesc(java.net.URLDecoder.decode(p.getPortletDesc(), "utf-8"));
			if (p.getPortletType().substring(0, 2).equalsIgnoreCase("01")) {
				p.setFuncAction(java.net.URLDecoder.decode(p.getFuncAction(), "utf-8"));
				p.setEditAction(java.net.URLDecoder.decode(p.getEditAction(), "utf-8"));
				p.setTitleUrl(java.net.URLDecoder.decode(p.getTitleUrl(), "utf-8"));
			} else if (p.getPortletType().equalsIgnoreCase("02")) {
				p.setHtmlCode(java.net.URLDecoder.decode(p.getHtmlCode(), "utf-8"));
			} else if (p.getPortletType().equalsIgnoreCase("03")) {
				p.setFuncAction(java.net.URLDecoder.decode(p.getFuncAction(), "utf-8"));
				p.setEditAction(java.net.URLDecoder.decode(p.getEditAction(), "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

}
