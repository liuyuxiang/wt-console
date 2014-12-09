package com.wt.hea.webbuilder.struts.action;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
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
import com.wt.hea.common.util.FileUtil;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.model.LayoutDefinition;
import com.wt.hea.webbuilder.model.PersonalSystemParameter;
import com.wt.hea.webbuilder.model.PlaceHolderGroup;
import com.wt.hea.webbuilder.model.SiteManage;
import com.wt.hea.webbuilder.model.TemplateLayout;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;
import com.wt.hea.webbuilder.model.ThemeDefinition;
import com.wt.hea.webbuilder.struts.form.Prefix;
import com.wt.hea.webbuilder.struts.form.PrefixContainer;
import com.wt.hea.webbuilder.util.FileIOUtil;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:页面实例Action
 * 功能说明: 
 * 编写日期:	2011-3-29
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class TemplatePageAction extends DispatchAction
{

	/**
	 * 
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

	/**
	 * 查询出所有模板页面并把用户已添加的标识出来 用作自动布局使用
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

		List<TemplatePage> templatePageList = this.templatePageService.findAll();

		for (TemplatePage tp : templatePageList) {

			boolean flag = this.userPageService.isAddedTemplatePage(tp.getPageNo(), user.getId());

			if (flag) {
				tp.setIsAdd("0");
			} else {
				tp.setIsAdd("1");
			}
		}
		String logMessage = "用户" + user.getName() + "查找可用页面";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		request.setAttribute("templatePageList", templatePageList);
		return mapping.findForward("viewTemplates");
	}

	/**
	 * 根据页面类型查找模板页面
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
	public ActionForward findAllTmplPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 页面类型 01 门户首页 02 用户个性化首页
		String pageType = request.getParameter("pageType");
		String site = request.getParameter("site");
		// List<TemplatePage> tps = this.templatePageService.findByProperty("type", pageType);
		String temp = request.getParameter("curPage");
		int curPage = 0;
		if (temp != null && !temp.equals("")) {
			curPage = Integer.parseInt(temp);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", pageType);
		if (site != null)
			params.put("siteId", site);
		Page<TemplatePage> page = new Page<TemplatePage>(10, curPage);
		page.addConditions(Condition.getInstance().allEq(params));
		page = this.templatePageService.loadPage(page);
		request.setAttribute("data", page);

		List<SiteManage> smList = this.siteManageService.findAll();
		request.setAttribute("smList", smList);

		return mapping.findForward("allTemplatePage");
	}

	/**
	 * 查询出模板、站点、主题以供创建页面
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
	public ActionForward toFirstStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 用户所操作的目录的ID
		String siteId = request.getParameter("siteId");
		SiteManage siteManage = new SiteManage();
		if (siteId != null) {
			siteManage = this.siteManageService.findById(siteId);
		}
		List<TemplateLayout> tlList = this.templateLayoutService.findAll();
		List<ThemeDefinition> themeList = this.themeDefinitionService.findAll();
		List<LayoutDefinition> layoutDefs = this.layoutDefinitionService.findAll();
		List<PersonalSystemParameter> pspList = this.personalSystemParameterService
				.findByType(new String[] { "tmplType" });
		for (TemplateLayout tl : tlList) {
			tl.setTmplPicAddr("/" + this.contextPath + tl.getTmplPicAddr());
		}
		for (ThemeDefinition td : themeList) {
			td.setThemePicPath("/" + this.contextPath + td.getThemePicPath());
		}
		request.setAttribute("themeList", themeList);
		request.setAttribute("tlList", tlList);
		request.setAttribute("siteId", siteId);
		request.setAttribute("siteName", siteManage.getParentSite().getSiteName());
		request.setAttribute("layoutDefs", layoutDefs);
		request.setAttribute("pspList", pspList);
		return mapping.findForward("newPage");
	}

	/**
	 * 保存页面信息，不进行持久化 暂停使用
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
	public ActionForward saveNoStatic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String tmplId = request.getParameter("tmplId");
		String siteId = request.getParameter("siteId");
		String pageName = request.getParameter("pageId");
		String themeCode = request.getParameter("themeCode");
		if (themeCode == null) {
			themeCode = "theme1";
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		User user = WebUtil.getSessionUser(request);
		try {
			this.templatePageService.saveTemplate(pageName, tmplId, siteId, themeCode, user);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 添加管理员编辑portlet的限制
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
	public ActionForward toSecondStep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String tmplId = request.getParameter("tmplId");
		String siteId = request.getParameter("siteId");
		String pageName = request.getParameter("pageName");
		String themeCode = request.getParameter("themeCode");
		String type = request.getParameter("pageType");
		pageName = URLDecoder.decode(pageName, "utf-8");
		if (themeCode == null) {
			themeCode = "theme1";
		}
		TemplateLayout tl = this.templateLayoutService.findById(tmplId);
		String tempAddr = tl.getTmplAddr();
		User user = WebUtil.getSessionUser(request);
		// user = rbacService.getUserByUuid(user.getUuid());
		String u = this.rootPath + this.contextPath + tl.getTmplAddr();
		// String u = request.getRealPath(tl.getTmplAddr());
		String layout = FileUtil.fileRead(u);
		// String layout = FileIOUtil.readFile(u);
		// 加入logo、banner、版权、权限控制等站点的资源
		layout = this.addPower(layout, user, tmplId);
		layout = this.addResource(layout, siteId);
		ThemeDefinition themeDef = this.themeDefinitionService.findById(themeCode);
		String theme = themeDef.getThemeContent();
		tempAddr = "/" + this.contextPath + tl.getTmplStyleAddr();
		request.setAttribute("tmplId", tmplId);
		request.setAttribute("siteId", siteId);
		request.setAttribute("pageName", pageName);
		request.setAttribute("themeCode", themeCode);
		request.setAttribute("theme", theme);
		// 模板布局样式
		request.setAttribute("layoutAddr", tempAddr);
		// 主题皮肤样式
		request.setAttribute("styleAddr", "/" + this.contextPath + themeDef.getThemePath());
		request.setAttribute("pageType", type);
		request.getSession().setAttribute("templateLayout", layout);
		return mapping.findForward("loadTemplate");
	}

	/**
	 * 修改页面portlet的方法
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
	public ActionForward modifyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String pageId = request.getParameter("pageId");
		String pageName = request.getParameter("pageName");
		String themeCode = request.getParameter("themeCode");
		String tmplId = request.getParameter("tmplId");
		String pageType = request.getParameter("pageType");
		pageName = URLDecoder.decode(pageName, "utf-8");
		TemplatePage page = this.templatePageService.findById(pageId);
		TemplateLayout templateLayout = this.templateLayoutService.findById(tmplId);
		page.setPageName(pageName);
		page.setThemeCode(themeCode);
		page.setType(pageType);
		page.setTemplateLayout(templateLayout);
		this.templatePageService.update(page);
		TemplateLayout tl = page.getTemplateLayout();
		String tempAddr = tl.getTmplAddr();
		User user = WebUtil.getSessionUser(request);
		// String u = request.getRealPath(tl.getTmplAddr());
		String u = this.rootPath + this.contextPath + tl.getTmplAddr();
		String layout = FileUtil.fileRead(u);
		// String layout = FileIOUtil.readFile(u);

		// 加入logo、banner、版权等站点的资源
		layout = this.addPower(layout, user, tmplId);
		layout = this.addResource(layout, page.getSiteId());
		List<TemplatePortletInfo> list = page.getTemplatePortletInfoList();
		PrefixContainer pc = new PrefixContainer();
		List<Prefix> pres = new ArrayList<Prefix>();
		for (TemplatePortletInfo tpi : list) {
			Prefix p = new Prefix();
			p.setTpId(tpi.getTpId());
			p.setDataurl(tpi.getFuncAction());
			p.setPortletId(tpi.getPortletProperty().getPortletId());
			p.setPrefix(tpi.getPlaceHolderId());
			p.setType(tpi.getPlaceHolderType());
			p.setHtmlCode(tpi.getHtmlCode());
			p.setPortletType(tpi.getPortletType());
			pres.add(p);
		}
		pc.setPrefixes(pres);
		JSONObject jo = JSONObject.fromObject(pc);
		ThemeDefinition themeDef = this.themeDefinitionService.findById(page.getThemeCode());
		String theme = themeDef.getThemeContent();
		tempAddr = "/" + this.contextPath + tl.getTmplStyleAddr();
		request.setAttribute("layoutAddr", tempAddr);
		request.setAttribute("styleAddr", "/" + this.contextPath + themeDef.getThemePath());
		request.setAttribute("theme", theme);
		request.getSession().setAttribute("templateLayout", layout);
		request.setAttribute("json", jo.toString());
		request.setAttribute("pageId", pageId);
		return mapping.findForward("modifyPage");
	}

	/**
	 * 发布已经保存的站点
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
	@SuppressWarnings("deprecation")
	public ActionForward publish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String pageId = request.getParameter("pageId");

		StringBuffer html = new StringBuffer();

		TemplatePage tp = this.templatePageService.findById(pageId);
		TemplateLayout tl = this.templateLayoutService.findById(tp.getTmplId());
		String themeCode = tp.getThemeCode();
		ThemeDefinition themeDef = this.themeDefinitionService.findById(themeCode);
		String htmlPath = request.getRealPath("heaconsole/web/webbuild/template/html.tmpl");
		String tempHtml = FileUtil.fileRead(htmlPath);
		// String tempHtml = FileIOUtil.readFileByReadStream(htmlPath);
		String content = (String) request.getSession().getAttribute("templateLayout");
		List<TemplatePortletInfo> tpiList = tp.getTemplatePortletInfoList();
		// 添加取数据的URL
		content = content.replaceAll(" isEdit=\"1\"", "");
		for (TemplatePortletInfo tpi : tpiList) {
			if ("02".equals(tpi.getPortletProperty().getPortletType())) {
				content = content.replaceAll("prefix=\"\\*" + tpi.getPlaceHolderId() + "\">", ">"
						+ tpi.getPortletProperty().getHtmlCode());
			} else {
				if ("0102".equals(tpi.getPortletProperty().getPortletType())) {
					content = content
							.replaceAll(
									"prefix=\"\\*" + tpi.getPlaceHolderId() + "\">",
									">"
											+ "<iframe src=\""
											+ tpi.getFuncAction()
											+ "\" height=\"100%\" width=\"100%\" scolling=\"no\" frameborder=\"0px\" ></iframe>");
				} else {
					content = content.replaceAll("\\*" + tpi.getPlaceHolderId() + "\"",
							tpi.getPlaceHolderId() + "\" dataUrl='" + tpi.getFuncAction() + "'");
				}
			}
		}
		// 加入logo、banner、版权等站点的资源

		content = this.addResource(content, tp.getSiteId());
		// tempHtml = tempHtml.replaceAll("\\$style_\\$", request.getContextPath() + "/"
		// +this.getStyleAddr(tl.getTmplAddr()) + themeDef.getThemeName()+"/css/template.css");
		tempHtml = tempHtml.replaceAll("\\$style_\\$",
				"/" + this.contextPath + tl.getTmplStyleAddr());
		tempHtml = tempHtml.replaceAll("\\$themestyle",
				"/" + this.contextPath + themeDef.getThemePath());// 主题样式
		tempHtml = tempHtml.replaceAll("\\$script_\\$", request.getContextPath());
		tempHtml = tempHtml.replaceAll("\\$title_\\$", tp.getSiteName());
		tempHtml = tempHtml.replaceAll("\\$content_\\$", content);
		tempHtml = tempHtml.replaceAll("\\$putcmsstyle_\\$", themeDef.getThemeContent());
		tempHtml = tempHtml.replaceAll("_encode", FileUtil.getEncoding());
		String filepath = tp.getPubAddr();
		tp.setPubTime(new Date());
		html.append(tempHtml);
		File file = new File(filepath);
		PrintWriter out = response.getWriter();
		try {
			// FileUtil.fileWrite(filepath, html.toString());
			// FileUtil.fileWrite(filepath, tempHtml);
			FileIOUtil.writeFile(html, file);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
		}
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 删除一个页面<br>
	 * out success:1,faild:0
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

		String pageId = request.getParameter("pageId");
		TemplatePage tp = this.templatePageService.findById(pageId);
		List<TemplatePortletInfo> tpiList = tp.getTemplatePortletInfoList();
		String filePath = tp.getPubAddr();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		try {
			for (TemplatePortletInfo tpi : tpiList) {
				this.templatePortletInfoService.delete(tpi);
			}
			tp.setTemplatePortletInfoList(null);
			this.templatePageService.delete(tp);
			FileUtil.removeFile(filePath);
			out.print("1");
		} catch (Exception e) {
			out.print("0");
			e.printStackTrace();
		}
		out.flush();
		out.close();
		request.setAttribute("isRefresh", "1");
		return null;
	}

	/**
	 * 修改第一步
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
	public ActionForward modifyPageFirst(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String pageId = request.getParameter("pageId");

		TemplatePage tp = this.templatePageService.findById(pageId);
		String pageName = tp.getPageName();
		String pageType = tp.getType();
		String tmplId = tp.getTmplId();
		String theme = tp.getThemeCode();
		String layout = tp.getLayoutCode();

		String siteName = tp.getSiteManage().getParentSite().getSiteName();
		List<TemplateLayout> tmplList = this.templateLayoutService.findAll();
		List<ThemeDefinition> themeDefs = this.themeDefinitionService.findAll();
		List<LayoutDefinition> layoutList = this.layoutDefinitionService.findAll();
		for (TemplateLayout tl : tmplList) {
			tl.setTmplPicAddr("/" + this.contextPath + tl.getTmplPicAddr());
		}
		for (ThemeDefinition td : themeDefs) {
			td.setThemePicPath("/" + this.contextPath + td.getThemePicPath());
		}
		List<PersonalSystemParameter> pspList = this.personalSystemParameterService
				.findByType(new String[] { "tmplType" });
		request.setAttribute("pageName", pageName);
		request.setAttribute("pageType", pageType);
		request.setAttribute("tmplId", tmplId);
		request.setAttribute("themeCode", theme);
		request.setAttribute("layoutCode", layout);
		request.setAttribute("siteName", siteName);
		request.setAttribute("tmplList", tmplList);
		request.setAttribute("themeDefs", themeDefs);
		request.setAttribute("layoutList", layoutList);
		request.setAttribute("pageId", pageId);
		for (PersonalSystemParameter psp : pspList) {
			if (psp.getCodeValue().equals(pageType)) {
				pageType = psp.getContent1();
			}
		}
		request.setAttribute("pspList", pspList);
		return mapping.findForward("editPageFirst");
	}

	/**
	 * 验证页面是否存在 网页面输出一个字符串格式如下 :存在状态_pageId_模板类型
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
	public ActionForward validatePageExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteId = request.getParameter("siteId");
		String pageName = request.getParameter("pageName");
		String pageId = "";
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		TemplatePage tp = this.templatePageService.findBySiteIdAndPageName(siteId, pageName);
		if (tp == null) {
			out.print("0_");
		} else {
			pageId = tp.getPageId();
			// String filePath = tp.getPubAddr();
			// File file = new File(filePath);
			// if(file.exists()){
			out.print("1_" + pageId + "_" + tp.getType());
			// }else{
			// out.print("0_");
			// }
		}
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 查找portlet类型
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
	public ActionForward findPortletTypeByType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String[] codeTypes = request.getParameterValues("codeType");
		List<PersonalSystemParameter> psps = this.personalSystemParameterService
				.findByType(codeTypes);
		request.setAttribute("portletType", psps);
		return null;
	}

	/**
	 * 给模板加入权限控制位
	 * 
	 * @param template
	 *            template
	 * @param user
	 *            user
	 * @param tmplId
	 *            tmplId
	 * @return String
	 */
	private String addPower(String template, User user, String tmplId)
	{
		// 加入权限控制
		if (this.rbacService.isAdmin(user)) {
			String edit = "prefix=";
			String tempEdit = " isEdit=\"1\"" + " prefix=";
			if (template.indexOf(tempEdit) < 0) {
				template = template.replaceAll(edit, tempEdit);
			}
		} else {
			List<Group> groups = this.rbacService.getUserGroups(user);
			for (Group group : groups) {
				List<PlaceHolderGroup> phgList = this.placeHolderGroupService.findByGroupAndTmpl(
						group.getUuid(), tmplId);
				for (PlaceHolderGroup phg : phgList) {
					String edit = "prefix=\"\\" + phg.getPlaceHolderId() + "\"";
					String tempEdit = "prefix=\"" + phg.getPlaceHolderId() + "\"" + " isEdit=\"1\"";
					if (template.indexOf(tempEdit) < 0) {
						template = template.replaceAll(edit, edit + " isEdit=\"1\"");
					}
				}
			}
		}
		return template;
	}

	/**
	 * 给页面加入资源 包括版权、横幅、侧帘飘窗、菜单、
	 * 
	 * @param template
	 *            template
	 * @param siteId
	 *            siteId
	 * @return String
	 * 
	 */
	private String addResource(String template, String siteId)
	{

		SiteManage siteManage = this.siteManageService.findById(siteId).getParentSite();
		BaseInfo biBanner = null;
		// 加入横幅
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("topType", "07");
		map.put("siteId", siteManage.getSiteId());
		List<BaseInfo> biList = this.baseInfoService.findByProperty(map);
		// 如果有侧帘则加入
		map.put("topType", "02");
		List<BaseInfo> cBiList = this.baseInfoService.findByProperty(map);
		if (cBiList.size() > 0) {
			biBanner = cBiList.get(0);
			template = template.replaceAll("class=\"curtain\"", "class=\"curtain\" curtainUrl=\"/"
					+ siteManage.getSiteAddr() + "res_0203_" + siteManage.getSiteId() + ".html\"");
		}

		// 如果有飘窗则加入
		map.put("topType", "04");
		List<BaseInfo> fwbList = this.baseInfoService.findByProperty(map);
		if (fwbList.size() > 0) {
			biBanner = fwbList.get(0);
			template = template.replaceAll("class=\"floatwindow\"",
					"class=\"floatwindow\" fwUrl=\"/" + siteManage.getSiteAddr() + "res_04_"
							+ siteManage.getSiteId() + ".html\"");
		}
		// 如果有弹窗则加入
		map.put("topType", "08");
		List<BaseInfo> pops = this.baseInfoService.findByProperty(map);
		if (pops != null && !pops.isEmpty()) {
			template = template.replaceAll("class=\"popwindow\"", "class=\"popwindow\" popUrl=\"/"
					+ siteManage.getSiteAddr() + "pop.html\"");
		}
		String banner = "尚未设置横幅信息！";
		if (biList.size() > 0) {
			biBanner = biList.get(0);
			if (biBanner != null && biBanner.getSuspenPath() != null) {

				banner = "<OBJECT height=\"" + biBanner.getHeight() + "\" width=\""
						+ biBanner.getWidth()
						+ "\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\">"
						+ "<PARAM NAME=\"movie\" VALUE=\"/" + biBanner.getSuspenPath() + "\">"
						+ "<PARAM NAME=\"quality\" VALUE=\"high\">"
						+ "<PARAM NAME=\"wmode\" VALUE=\"Opaque\">"
						+ "<param name=\"allowScriptAccess\" value=\"always\" >" + "<embed src=\"/"
						+ biBanner.getSuspenPath() + "\" quality=\"autohigh\" wmode=\"opaque\""
						+ "      type=\"application/x-shockwave-flash\" width=\""
						+ biBanner.getWidth() + "\" width=\"" + biBanner.getHeight() + "\">"
						+ "</embed>" + "</OBJECT>";
				if (biBanner.getResCode() != null && biBanner.getResCode().equals("02")) {// 判断banner是swf还是图片
					banner = "<img src=\"/" + biBanner.getSuspenPath() + " \" width=\""
							+ biBanner.getWidth() + "\" height=\"" + biBanner.getHeight()
							+ "\"></img>";
				}
			}
		}
		// 由目录得到站点版权
		String copyright = siteManage.getCopyrightContent();
		if (copyright == null) {
			copyright = "该站点未设置版权信息";
		}

		template = template
				.replaceAll("\\*banner_", banner)
				.replaceAll(
						"class=\"menu\"",
						"class=\"menu\" menuUrl=\"/" + siteManage.getSiteAddr()
								+ siteManage.getSiteId() + ".html\"")
				.replaceAll("addcopyright\"", "addcopyright\" copyrightUrl=\"" + copyright + "\"");
		return template;
	}

}