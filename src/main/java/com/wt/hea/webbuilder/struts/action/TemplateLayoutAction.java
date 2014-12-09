package com.wt.hea.webbuilder.struts.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hirisun.components.data.validate.Validator;
import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.model.Condition;
import com.wt.hea.common.model.Page;
import com.wt.hea.common.util.FileUtil;
import com.wt.hea.common.util.UploadUtil;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.common.util.ZipUtils;
import com.wt.hea.rbac.model.GroupExtends;
import com.wt.hea.webbuilder.model.PersonalSystemParameter;
import com.wt.hea.webbuilder.model.PlaceHolderGroup;
import com.wt.hea.webbuilder.model.TemplateLayout;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;
import com.wt.hea.webbuilder.struts.form.TemplateLayoutForm;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 模板布局定义控制
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class TemplateLayoutAction extends DispatchAction
{
	/**
	 * 获取日志文件实例
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

	/**
	 * 取出角色组合所有模板 以供配置模板中占位符的权限
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
	public ActionForward findAllByUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		List<TemplateLayout> tlList = this.templateLayoutService.findAll();
		User user = WebUtil.getSessionUser(request);
		List<Group> groups = null;
		if (user != null && this.rbacService.isAdmin(user)) {
			groups = rbacService.getAllGroups();
		} else {
			groups = this.rbacService.getUserGroups(user);
		}
		// Group group = this.rbacService.getGroupByUuid(this.rootGroup);
		// groups.remove(group);
		// groups.add(0, group);
		request.setAttribute("tmplList", tlList);
		request.setAttribute("groups", groups);
		String userName = "";
		if (user != null) {
			userName = user.getName();
		}
		String logMessage = "用户" + userName + "查看角色组合所有模板 以供配置模板中占位符的权限";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("placeHolderControl");
	}

	/**
	 * 查询出模板并设置权限
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

		User user = WebUtil.getSessionUser(request);
		String tmplId = request.getParameter("tmplId");
		String groupId = request.getParameter("groupId");
		TemplateLayout tl = this.templateLayoutService.findById(tmplId);
		String tempAddr = "/" + this.contextPath + tl.getTmplStyleAddr();

		String templatePath = this.rootPath + this.contextPath + tl.getTmplAddr();
		String template = FileUtil.fileRead(templatePath);

		List<PlaceHolderGroup> phgList = this.placeHolderGroupService.findByGroupAndTmpl(groupId,
				tmplId);
		for (PlaceHolderGroup phg : phgList) {// 取出占位符并给其加上可编辑的状态isEdit="1"
			String edit = "prefix=\"\\" + phg.getPlaceHolderId() + "\"";
			template = template.replaceAll(edit,
					edit + " isEdit=\"1\"" + " placeHolderId=\"" + phg.getPgId() + "\"");
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// 把模板和布局样式一并返回到页面
		String temp = template + "__" + tempAddr;
		out.print(temp);
		String logMessage = "用户" + user.getName() + "查询出模板并设置权限";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return null;
	}

	/**
	 * 修改权限
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
	public ActionForward editPlaceHolder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String noEdit = request.getParameter("noEdit");
		String editing = request.getParameter("editing");
		String tmplId = request.getParameter("tmplId");
		String groupId = request.getParameter("groupId");
		if (noEdit != null && !"".equals(noEdit)) {
			String[] noEdits = noEdit.split(",");
			for (String no : noEdits) {// 删除权限控制
				this.placeHolderGroupService.deleteById(no);
			}
		}
		if (editing != null && !"".equals(editing)) {
			String[] editings = editing.split(",");
			for (String e : editings) {
				PlaceHolderGroup phg = new PlaceHolderGroup();
				phg.setGroupId(groupId);
				phg.setPlaceHolderId(e);
				phg.setTmplId(tmplId);
				this.placeHolderGroupService.save(phg);
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print("1");
		}
		String logMessage = "用户" + user.getName() + "修改模板权限";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return null;
	}

	/**
	 * 查找所有的模板
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

		List<PersonalSystemParameter> pspList = this.personalSystemParameterService
				.findByType(new String[] { "tmplType" });
		List<TemplateLayout> tmplList = this.templateLayoutService.findAll();
		request.setAttribute("pspList", pspList);
		for (TemplateLayout tl : tmplList) {
			tl.setTmplPicAddr("/" + this.contextPath + tl.getTmplPicAddr());
		}
		request.setAttribute("tmplList", tmplList);
		return mapping.findForward("tmplList");
	}

	/**
	 * 分页显示模板
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
	public ActionForward loadPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String tmplCode = request.getParameter("tmplCode");
		String currPage = request.getParameter("currPage");
		String perPageRecode = request.getParameter("perPage");
		Page<TemplateLayout> templates = null;
		if (tmplCode == null) {
			tmplCode = "";
		}
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
		templates = new Page<TemplateLayout>(Integer.valueOf(perPageRecode),
				Integer.valueOf(currPage));
		if (tmplCode != "") {
			Condition condition = Condition.getInstance();
			condition.eq("tmplCode", tmplCode);
			templates.addConditions(condition);
		}
		templates = this.templateLayoutService.loadPage(templates);
		request.setAttribute("tmplList", templates);
		request.setAttribute("tmplCode", tmplCode);
		List<TemplateLayout> tl = (List<TemplateLayout>) templates.getData();
		for (TemplateLayout t : tl) {
			t.setTmplPicAddr("/" + this.contextPath + t.getTmplPicAddr());
		}
		String logMessage = "用户" + user.getName() + "浏览所有模板";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("tmplList");

	}

	/**
	 * 根据类型查找模板
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
	public ActionForward findbyType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String tmplCode = request.getParameter("tmplCode");
		List<TemplateLayout> tmplList = null;
		if (tmplCode != null && !"".equals(tmplCode)) {
			if (tmplCode.equals("all")) {
				tmplList = this.templateLayoutService.findAll();
			} else {
				tmplList = this.templateLayoutService.findByProperty("tmplCode", tmplCode);
			}
		}
		List<PersonalSystemParameter> pspList = this.personalSystemParameterService
				.findByType(new String[] { "tmplType" });
		request.setAttribute("tmplCode", tmplCode);
		request.setAttribute("pspList", pspList);
		request.setAttribute("tmplList", tmplList);
		return mapping.findForward("tmpllist");
	}

	/**
	 * 编辑模板
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
	public ActionForward editTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		TemplateLayoutForm tlf = (TemplateLayoutForm) form;
		TemplateLayout template = tlf.getTemplateLayout();
		if (!template.getTmplCode().equals("02")) {
			template.setTmplAddr(template.getTmplAddr().replaceAll("\\\\", "/"));
			template.setTmplPicAddr(template.getTmplPicAddr().replaceAll("\\\\", "/"));
			template.setTmplStyleAddr(template.getTmplStyleAddr().replaceAll("\\\\", "/"));
		}
		String sourcePath = this.rootPath + this.contextPath;
		File tempFile = new File(sourcePath);
		if (tempFile != null && !tempFile.exists()) {
			boolean flag = tempFile.mkdirs();
			if (flag) {

			}
		}
		if (tlf.getFile() != null) {
			try {
				String filePath = sourcePath + File.separator + tlf.getFile().getFileName();
				File file = new File(filePath);
				UploadUtil.upload(tlf.getFile().getFileData(), filePath);
				ZipUtils.decompress(filePath, sourcePath);
				boolean flag = file.delete();
				if (flag) {
					log.info("delete files successfully");
				}
			} catch (Exception e) {

				request.setAttribute("templateLayout", template);
				request.setAttribute("message", "资源文件格式错误，只能上传zip格式的资源文件");
				return mapping.findForward("editTemplate");
			}
		}
		template.setModifyDate(new Date());
		this.templateLayoutService.update(template);
		String logMessage = "用户" + user.getName() + "修改模板" + template.getTmplName();

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return loadPage(mapping, form, request, response);
	}

	/**
	 * 
	 * 方法说明： 编辑模板提供数据
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
	public ActionForward toEditTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String tmplId = request.getParameter("tmplId");
		TemplateLayout template = null;
		if (tmplId != null && !"".equals(tmplId)) {
			template = this.templateLayoutService.findById(tmplId);
		}
		request.setAttribute("templateLayout", template);
		return mapping.findForward("editTemplate");
	}

	/**
	 * 删除模板及模板文件 删除依赖于此模板的页面 删除该模板的占位符的权限数据
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
	public ActionForward deleteTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String tmplId = request.getParameter("tmplId");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		User user = WebUtil.getSessionUser(request);
		if (tmplId != null && !"".equals(tmplId)) {
			List<TemplatePage> tps = this.templatePageService.findByProperty("tmplId", tmplId);
			for (TemplatePage tp : tps) {
				List<TemplatePortletInfo> tpiList = tp.getTemplatePortletInfoList();
				for (TemplatePortletInfo tpi : tpiList) {
					this.templatePortletInfoService.delete(tpi);
				}
				this.templatePageService.delete(tp);
			}
			List<PlaceHolderGroup> phgList = this.placeHolderGroupService.findByProperty("tmplId",
					tmplId);
			for (PlaceHolderGroup phg : phgList) {
				this.placeHolderGroupService.delete(phg);
			}
			this.templateLayoutService.deleteById(tmplId);
			out.print(1);
		} else {
			out.print("删除失败");
		}
		String logMessage = "用户" + user.getName() + "删除 id为" + tmplId + "的模板";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return null;
	}

	/**
	 * 添加新的模板
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
	public ActionForward addTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		TemplateLayoutForm tlf = (TemplateLayoutForm) form;
		TemplateLayout template = tlf.getTemplateLayout();
		if (!template.getTmplCode().equals("02")) {
			template.setTmplAddr(template.getTmplAddr().replaceAll("\\\\", "/"));
			template.setTmplPicAddr(template.getTmplPicAddr().replaceAll("\\\\", "/"));
			template.setTmplStyleAddr(template.getTmplStyleAddr().replaceAll("\\\\", "/"));
		}
		String sourcePath = this.rootPath + this.contextPath;
		File tempFile = new File(sourcePath);
		if (tempFile != null && !tempFile.exists()) {
			boolean flag = tempFile.mkdirs();
			if (flag) {
				log.info("create files successfully");
			}
		}
		if (tlf.getFile() != null) {// 没有提交文件时则只更新模板属性
			try {
				String filePath = sourcePath + File.separator + tlf.getFile().getFileName();
				File file = new File(filePath);
				UploadUtil.upload(tlf.getFile().getFileData(), filePath);
				ZipUtils.decompress(filePath, sourcePath);
				boolean flag = file.delete();
				if (flag) {
					log.info("delete files successfully");
				}
			} catch (Exception e) {

				request.setAttribute("templateLayout", template);
				request.setAttribute("message", "上传的资源文件格式错误，请选择zip格式的文件");
				return mapping.findForward("addTemplate");
			}
		}
		template.setCreateDate(new Date());
		template.setModifyDate(new Date());
		this.templateLayoutService.save(template);
		String logMessage = "用户" + user.getName() + "添加新的模板";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return loadPage(mapping, form, request, response);
	}

	/**
	 * 查找模板时否存在
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
	public ActionForward templateExists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String tmplId = request.getParameter("tmplId");
		TemplateLayout template = this.templateLayoutService.findById(tmplId);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (template != null) {
			String tmplAddr = this.rootPath + this.contextPath + template.getTmplAddr();
			File file = null;
			if (tmplAddr != null && tmplAddr.trim().length() > 0) {
				file = new File(tmplAddr);
				if (!file.exists()) {
					out.print("0");
					log.info("模板文件不存在" + tmplAddr + "!");
				} else {
					out.print("1");
				}
			} else {
				out.print("0");
				log.info("模板文件不存在" + tmplAddr + "!");
			}
		}
		return null;
	}

	// private String hasChild(Group group){
	// List<Group> groups = this.rbacService.getGroupsByParentGroup(group.getUuid());
	// if(groups != null && !groups.isEmpty() ){
	// return "1";
	// }
	// return "0";
	// }

	/**
	 * 
	 * 方法说明： 初始化组的第一级xml数据
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
	public ActionForward initTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);// 登录用户
		List<Group> managerGroupList = null;// 用户可管理的组
		boolean isAdmin = rbacService.isAdmin(user);// isAdmin
		if (isAdmin) {
			managerGroupList = rbacService.getAllGroups();
		} else {
			managerGroupList = rbacService
					.getManagerGroups(WebUtil.getSessionLoginGroupId(request));
		}
		List<Group> childGroupList = null;// 子级组
		Group rootGroup = null;// 根级组
		if (isAdmin) {
			rootGroup = rbacService.getRootGroup();
			childGroupList = rbacService.getGroupsByParentGroup(rootGroup.getUuid());

			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<tree id=\"0\">");
			boolean hasChild = rbacService.hasChildGroup(rootGroup.getUuid());
			xml.append("<item  text=\"" + rootGroup.getName() + "\" id=\"" + rootGroup.getUuid()
					+ "\" child=\"" + (hasChild ? 1 : 0) + "\" >");
			xml.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + rootGroup.getUuid()
					+ "]]></userdata>");
			for (Group g : childGroupList) {
				hasChild = rbacService.hasChildGroup(g.getUuid());
				xml.append("<item  text=\"" + g.getName() + "\" id=\"" + g.getUuid()
						+ "\" child=\"" + (hasChild ? 1 : 0) + "\">");
				xml.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + g.getUuid() + "]]>"
						+ "</userdata>");
				xml.append("</item>");
			}
			xml.append("</item></tree>");
			response.setContentType("text/xml;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(xml.toString());
			out.flush();
			out.close();
		} else {
			rootGroup = new Group();
			rootGroup.setUuid("__Y__");
			rootGroup.setName("根节点");
			List<GroupExtends> geList = new ArrayList<GroupExtends>();
			for (int i = 0; i < managerGroupList.size(); i++) {
				Group group_i = managerGroupList.get(i);
				List<Group> parentGroup = rbacService.getParentGroup(group_i.getUuid());
				GroupExtends ge = new GroupExtends();
				ge.group = group_i;
				ge.parentId = rootGroup.getUuid();
				for (int p = 0; p < parentGroup.size(); p++) {
					Group group_p = parentGroup.get(p);
					for (int k = 0; k < managerGroupList.size(); k++) {
						Group group_k = managerGroupList.get(k);
						if (group_p.getUuid().equals(group_k.getUuid())) {
							ge.parentId = group_p.getUuid();
							break;
						}
					}
				}
				geList.add(ge);
			}

			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<tree id=\"0\">");
			xml.append("<item  text=\"" + rootGroup.getName() + "\" id=\"" + rootGroup.getUuid()
					+ "\" child=\"0\" >");
			for (int i = 0; i < geList.size(); i++) {
				GroupExtends ge = geList.get(i);
				if (ge.parentId.equals(rootGroup.getUuid())) {
					StringBuilder childXML = new StringBuilder();
					childXML.append("<item  text=\"" + ge.group.getName() + "\" id=\""
							+ ge.group.getUuid() + "\" child=\"hasChild\" >");
					childXML.append("<userdata name=\"groupUuid\">" + "<![CDATA["
							+ ge.group.getUuid() + "]]>" + "</userdata>");
					String cXML = appendGroupXML(ge.group.getUuid(), geList);
					if (cXML.length() > 10) {
						xml.append(childXML.toString().replace("hasChild", "1"));
					} else {
						xml.append(childXML.toString().replace("hasChild", "0"));
					}
					xml.append(cXML);
					xml.append("</item>");
				}
			}

			xml.append("</item></tree>");
			response.setContentType("text/xml;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(xml.toString());
			out.flush();
			out.close();
		}
		String logMessage = "用户" + user.getName() + "浏览权限组";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return null;
	}

	/**
	 * 
	 * 方法说明： ajax加载子组的xml
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
	public ActionForward loadTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String groupId = request.getParameter("id");
		User user = WebUtil.getSessionUser(request);
		List<Group> childGroupList = null;// 子级组

		boolean isAdmin = rbacService.isAdmin(user);

		if (isAdmin)
			childGroupList = rbacService.getGroupsByParentGroup(groupId);
		else {
			request.setAttribute("xml", "<tree id=\"0\"></tree>");
			return mapping.findForward("tree_xmlData");
		}
		boolean hasChild = rbacService.hasChildGroup(groupId);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + groupId + "\">");
		for (Group n : childGroupList) {
			hasChild = rbacService.hasChildGroup(n.getUuid());
			xml.append("<item  text=\"" + n.getName() + "\" id=\"" + n.getUuid() + "\" child=\""
					+ (hasChild ? 1 : 0) + "\" >");
			xml.append("<userdata name=\"groupUuid\">");
			xml.append("<![CDATA[" + n.getUuid() + "]]>");
			xml.append("</userdata>");

			xml.append("</item>");
		}
		xml.append("</tree>");
		response.setContentType("text/xml;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(xml.toString());
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 方法说明：追加组树XML片段
	 * 
	 * @param pid
	 *            pid
	 * @param geList
	 *            geList
	 * @return String
	 */
	private String appendGroupXML(String pid, List<GroupExtends> geList)
	{
		StringBuilder sb = new StringBuilder();
		for (GroupExtends ge : geList) {
			if (ge.parentId.equals(pid)) {
				sb.append("<item  text=\"" + ge.group.getName() + "\" id=\"" + ge.group.getUuid()
						+ "\" child=\"hasChild\" ");
				sb.append(">");
				sb.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + ge.group.getUuid()
						+ "]]>" + "</userdata>");
				String childXML = appendGroupXML(ge.group.getUuid(), geList);
				String xml = null;
				if (childXML.length() > 10) {
					xml = sb.toString().replace("hasChild", "1");
				} else {
					xml = sb.toString().replace("hasChild", "0");
				}
				sb = new StringBuilder(xml);
				sb.append("</item>");
			}
		}
		return sb.toString();
	}
}
