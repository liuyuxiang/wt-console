package com.wt.hea.webbuilder.struts.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.webbuilder.model.PersonalSystemParameter;
import com.wt.hea.webbuilder.model.PortletProperty;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.wt.hea.webbuilder.struts.form.TemplatePageForm;
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
public class IndexAction extends DispatchAction
{

	/**
	 * 获取日志实例
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
	 * @return null
	 * @throws Exception
	 */
	public ActionForward initTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String type = request.getParameter("menuType");
		User user = WebUtil.getSessionUser(request);
		String rootId = null;

		// if(this.rbacService.isAdmin(user)){
		rootId = this.rootmenu;
		// }
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		response.setContentType("text/xml; charset=UTF-8");
		Index rootIndex = this.indexService.findById(rootId);
		Set<Index> set = rootIndex.getSubIndexes();
		List<Index> menu = new ArrayList<Index>();
		for (Index index : set) {
			if (index.getIndextype() == 3)
				menu.add(index);
		}

		if (type.equalsIgnoreCase("tree")) {
			sb.append("<tree id=\"0\">");
			sb.append("<item  text=\"" + rootIndex.getIndexname() + "\" id=\""
					+ rootIndex.getIndexuuid() + "\" child=\"" + rootIndex.getHasChild() + "\">");
			sb.append("<userdata name=\"url\">"
					+ "<![CDATA["
					+ (rootIndex.getIndexurl() != null ? ((rootIndex.getIndexurl().contains("?") == true ? rootIndex
							.getIndexurl() : rootIndex.getIndexurl() + "?")
							+ "&treeNodeId=" + rootIndex.getIndexuuid())
							: "") + "]]>" + "</userdata>");
			for (Index idx : menu) {
				sb.append("<item  text=\"" + idx.getIndexname() + "\" id=\"" + idx.getIndexuuid()
						+ "\" child=\"" + idx.getHasChild() + "\">");
				sb.append("<userdata name=\"url\">"
						+ "<![CDATA["
						+ (idx.getIndexurl() != null ? (idx.getIndexurl() + "&treeNodeId=" + idx
								.getIndexuuid()) : "") + "]]>" + "</userdata>");
				sb.append("</item>");
			}
			sb.append("</item>");
			sb.append("</tree>");

			request.setAttribute("xml", sb.toString().trim());
			response.setContentType("text/xml; charset=UTF-8");
			response.addHeader("Content-Type", "text/xml;charset=UTF-8");
			String logMessage = "用户" + user.getName() + "登录前初始化菜单树";

			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
			return mapping.findForward("tree_xmlData");
		} else if (type.equalsIgnoreCase("menu")) {
			// List<Index> list = new ArrayList<Index>();
			sb.append("<root>");
			for (Index index : menu) {
				sb.append("<menu name=\"" + index.getIndexname() + "\" level=\""
						+ index.getIndexlevel() + "\">");
				for (Index sub : index.getSubIndexes()) {
					sb.append("<menu name=\"" + sub.getIndexname() + "\" level=\""
							+ sub.getIndexlevel() + "\">");
					sb.append("<![CDATA[" + sub.getIndexurl() + "]]>");
					sb.append("</menu>");
				}
				sb.append("</menu>");
			}
			sb.append("</root>");

			PrintWriter out = response.getWriter();
			out.print(sb.toString());
			out.flush();
			out.close();

		}

		return null;
	}

	/**
	 * 
	 * 方法说明： ajax加载树结构
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
	public ActionForward loadNextNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String indexuuid = request.getParameter("id");
		Index node = this.indexService.findById(indexuuid);
		List<Index> menu = new ArrayList<Index>();
		for (Index index : node.getSubIndexes()) {

			if (index.getIndextype() == 3)
				menu.add(index);
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + node.getIndexuuid() + "\">");
		for (Index n : menu) {
			xml.append("<item  text=\"" + n.getIndexname() + "\" id=\"" + n.getIndexuuid()
					+ "\" child=\"" + n.getHasChild() + "\">");
			xml.append("<userdata name=\"url\">"
					+ "<![CDATA["
					+ (n.getIndexurl() != null ? ((n.getIndexurl().contains("?") == true ? n
							.getIndexurl() + "&" : n.getIndexurl() + "?")
							+ "treeNodeId=" + n.getIndexuuid()) : "") + "]]>" + "</userdata>");

			xml.append("</item>");
		}
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());

		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		String logMessage = "用户" + user.getName() + "访问" + node.getIndexname() + "下的节点";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明： 查找首页配置
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
	public ActionForward findIndexConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String type = request.getParameter("type");
		List<PersonalSystemParameter> psps = this.personalSystemParameterService.findByProperty(
				"codeType", "overall");
		String logMessage = "用户" + user.getName() + "浏览首页配置";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		if (type.equalsIgnoreCase("admin")) {// 用于页面配置
			request.setAttribute("overAllsysParams", psps);
			return mapping.findForward("sysparam");
		} else {// 用户页面定制
			printJSON(psps, response);
			return null;
		}

	}

	/**
	 * 用于页面上添加
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
	public ActionForward findAllPersonalPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String type = request.getParameter("type");
		User user = WebUtil.getSessionUser(request);

		List<TemplatePage> templatePageList = this.templatePageService.findAll();

		for (TemplatePage tp : templatePageList) {
			tp.setTemplatePortletInfoList(null);
			tp.setTemplateLayout(null);
			tp.setSiteManage(null);

			boolean flag = this.userPageService.isAddedTemplatePage(tp.getPageNo(), user.getId());

			if (flag) {
				tp.setIsAdd("0");
			} else {
				tp.setIsAdd("1");
			}
		}
		String logMessage = "用户" + user.getName() + "浏览首页";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		if (type != null && type.equalsIgnoreCase("json")) {
			printJSON(templatePageList, response);
			return null;
		} else {
			request.setAttribute("templatePageList", templatePageList);
			return mapping.findForward("viewTemplates");
		}
	}

	/**
	 * 后台页面管理
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
	public ActionForward pageManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<TemplatePage> templatePageList = this.templatePageService.findAll();
		request.setAttribute("data", templatePageList);
		return mapping.findForward("pageManager");
	}

	/**
	 * 后台添加页面
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
	public ActionForward newPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TemplatePageForm f = (TemplatePageForm) form;
		this.templatePageService.save(f.getTemplatePage());
		response.sendRedirect("heaIndexPageAction.hea?action=pageManager");
		return null;
	}

	/**
	 * 
	 * 方法说明： 为配置页面准备数据
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
	public ActionForward toConfigPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		TemplatePage page = this.templatePageService.findById(id);
		request.setAttribute("page", page);
		return mapping.findForward("configPage");
	}

	/**
	 * 给configPage准备portlet数据
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
	public ActionForward portletList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<PortletProperty> list = this.portletPropertyService.findAll();
		request.setAttribute("portlets", list);
		return mapping.findForward("portletList");
	}

	/**
	 * 
	 * 方法说明： 输出到页面转换后的json
	 * 
	 * @param data
	 *            要转换为json串的数据列表
	 * @param response
	 *            response
	 */
	@SuppressWarnings("rawtypes")
	private void printJSON(List data, HttpServletResponse response)
	{
		try {
			JSONArray jo = JSONArray.fromObject(data);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(jo.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*private void printJSON(Object data,HttpServletResponse response){
		try {
			JSONObject jo =JSONObject.fromObject(data);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(jo.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}