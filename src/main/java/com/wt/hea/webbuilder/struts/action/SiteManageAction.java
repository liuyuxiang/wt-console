package com.wt.hea.webbuilder.struts.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.hirisun.components.data.DateUtils;
import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.FileUtil;
import com.wt.hea.common.util.I18NResourceUtil;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.model.ResourceSite;
import com.wt.hea.webbuilder.model.SiteManage;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;
import com.wt.hea.webbuilder.struts.form.SiteManageForm;
import com.wt.hea.webbuilder.util.FileIOUtil;
import com.wt.hea.webbuilder.util.GenerateMenu;
import com.wt.hea.webbuilder.util.UrlContentUtil;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-22
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SiteManageAction extends DispatchAction
{

	/**
	 * 获取log实例
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
	public ActionForward forward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SiteManage rootSite = this.siteManageService.findById(this.rootSite);
		request.setAttribute("siteManage", rootSite);
		return mapping.findForward("nav_addSite");
	}

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
	public ActionForward initNav(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeCode", "01");

		List<SiteManage> sites = this.siteManageService.findByProperty(map);
		SiteManage rootSite = this.siteManageService.findById(this.rootIndex);
		sites.remove(rootSite);

		sites.add(0, rootSite);
		request.setAttribute("sites", sites);
		return mapping.findForward("nav_sidebar");
	}

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
	public ActionForward addSite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SiteManageForm myForm = (SiteManageForm) form;
		SiteManage sm = myForm.getSiteManage();
		String parentUuid = request.getParameter("siteManage.parentSiteNo");
		SiteManage parentSiteManage = this.siteManageService.findById(parentUuid);
		sm.setParentSite(parentSiteManage);

		this.siteManageService.save(sm);

		request.setAttribute("message", "站点添加成功!");
		return mapping.findForward("message");
	}

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
	public ActionForward initUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		SiteManage site = this.siteManageService.findById(siteUuid);

		request.setAttribute("siteManage", site);

		return mapping.findForward("nav_updateSite");
	}

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
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		SiteManageForm myForm = (SiteManageForm) form;
		SiteManage sm = myForm.getSiteManage();
		this.siteManageService.update(sm);
		request.setAttribute("message", "更新成功");
		return mapping.findForward("message");

	}

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
	public ActionForward chooseParent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		// String indexuuid=request.getParameter("indexuuid");
		// Index index=this.indexService.findById(indexuuid);

		if (this.rbacService.isAdmin(user)) {
			List<Index> list = this.indexService.findAll();
			Index rootIndex = this.indexService.findById(this.rootIndex);
			list.remove(rootIndex);
			list.add(0, rootIndex);
			// list.remove(index);
			request.setAttribute("indexs", list);
		}
		return mapping.findForward("nav_chooseParentNavigate");
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * 方法说明： 在某个站点下添加导航
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
	public ActionForward addNavigates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SiteManageForm sForm = (SiteManageForm) form;
		Index index = sForm.getIndex();
		Index parent = this.indexService.findById(index.getParentindexuuid());
		String indexLevel = this.indexService.createLevelCodeById(parent.getIndexuuid());
		index.setParentIndex(parent);
		index.setCreateTime(new Date());
		index.setIndexlevel(indexLevel.length() / 5);
		index.setIndextype(3);
		index.setLevelCode(indexLevel);
		this.indexService.save(index);
		request.setAttribute("message", "添加导航成功!");
		return mapping.findForward("message");
	}

	/**
	 * 方法说明 选择父级导航结点
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
	public ActionForward chooseParentNavigate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		String navId = siteManage.getNavRootId();
		if (navId != null) {
			Index navIndex = this.indexService.findById(navId);
			if (navIndex != null) {
				List<Index> indexs = this.indexService.findChildsById(navId);
				indexs.add(0, navIndex);
				request.setAttribute("indexs", indexs);
				return mapping.findForward("nav_chooseParentNavigate");
			} else {
				Index temp = new Index();
				String indexLevel = this.indexService.createLevelCodeById(this.indexService
						.findById(this.rootmenu).getIndexuuid());
				temp.setIndexname(siteManage.getSiteName() + "导航结构");
				temp.setParentIndex(this.indexService.findById(this.rootmenu));
				temp.setIndexorder(0);
				temp.setWay("1");
				temp.setIndexlevel(indexLevel.length() / 5);
				temp.setLevelCode(indexLevel);
				temp.setIndextype(3);
				temp.setCreateTime(new Date());
				this.indexService.save(temp);
				List<Index> indexs = new ArrayList<Index>();
				indexs.add(temp);
				request.setAttribute("indexs", indexs);

				siteManage.setNavRootId(temp.getIndexuuid());
				this.siteManageService.update(siteManage);
				return mapping.findForward("nav_chooseParentNavigate");
			}
		} else {

			Index temp = new Index();
			String indexLevel = this.indexService.createLevelCodeById(this.indexService.findById(
					this.rootmenu).getIndexuuid());
			temp.setIndexname(siteManage.getSiteName() + "导航结构");
			temp.setParentIndex(this.indexService.findById(this.rootmenu));
			temp.setIndexorder(0);
			temp.setWay("1");
			temp.setIndexlevel(indexLevel.length() / 5);
			temp.setLevelCode(indexLevel);
			temp.setIndextype(3);
			temp.setCreateTime(new Date());
			this.indexService.save(temp);
			// this.indexService.save(temp);
			List<Index> indexs = new ArrayList<Index>();
			indexs.add(temp);
			request.setAttribute("indexs", indexs);

			siteManage.setNavRootId(temp.getIndexuuid());
			this.siteManageService.update(siteManage);
			return mapping.findForward("nav_chooseParentNavigate");
		}

	}

	/**
	 * 方法说明 导航列表树形显示
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
	public ActionForward listNavigate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		String indexuuid = siteManage.getNavRootId();
		if (indexuuid == null) {
			request.setAttribute("message", siteManage.getSiteName() + "站点，没有菜单数据");
			return mapping.findForward("message");
		}
		List<Index> indexs = this.indexService.findChildsById(indexuuid);

		if (siteManage.getNavRootId() == null) {
			request.setAttribute("message", "该站点没有设置导航!");
			return mapping.findForward("message");
		}
		Index rootIndex = this.indexService.findById(siteManage.getNavRootId());
		indexs.add(0, rootIndex);
		request.setAttribute("indexs", indexs);

		request.setAttribute("siteUuid", siteUuid);
		return mapping.findForward("nav_navigateSidebar");
	}

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
	public ActionForward initUpdateNavigate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String indexuuid = request.getParameter("indexuuid");
		Index index = this.indexService.findById(indexuuid);
		request.setAttribute("index", index);
		return mapping.findForward("nav_updateNavigate");
	}

	/**
	 * 方法说明 更新一个导航 增加禁用的属性by xiaoqi
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
	public ActionForward updateNavigate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		SiteManageForm siteMangeForm = (SiteManageForm) form;
		Index index = siteMangeForm.getIndex();
		Index temp = this.indexService.findById(index.getIndexuuid());
		temp.setIndexurl(index.getIndexurl());
		temp.setIndexname(index.getIndexname());
		temp.setIndexorder(index.getIndexorder());
		temp.setWay(index.getWay());
		this.indexService.updateIndexGroup(temp.getIndexuuid(),
				this.indexService.findGroupByIndexID(temp.getIndexuuid()));
		index.setParentIndex(temp.getParentIndex());
		this.indexService.update(temp);
		request.setAttribute("message", "更新导航成功!");
		return mapping.findForward("message");
	}

	/**
	 * 方法说明： 跟据站点生成导航菜单jsp的预览
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
	public ActionForward generateMenuPriview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String styleFlag = request.getParameter("styleFlag");

		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		String path = request.getSession().getServletContext().getRealPath("/modules/")
				+ "/temp.xml";
		if (siteManage.getNavRootId() == null) {
			request.setAttribute("message", "当前站点没有设置导航菜单结点!");
			return mapping.findForward("message");
		}
		File file = GenerateMenu.createBannersMenu(siteManage.getNavRootId(), this.indexService,
				path, styleFlag);
		String content = FileIOUtil.readFile(file.getAbsolutePath());
		content = content.replaceAll("<\\?xml[^>]+>", "");
		request.setAttribute("menu", content);
		request.setAttribute("styleFlag", styleFlag);
		request.setAttribute("siteUuid", siteUuid);
		return mapping.findForward("nav_bannerMenuPriview");
	}

	/**
	 * 方法说明 生成单个纯菜单jsp页
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
	public ActionForward forwardMenuContentPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String styleFlag = request.getParameter("styleFlag");

		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		String path = request.getRealPath("/modules/") + "temp.xml";
		File file = GenerateMenu.createBannersMenu(siteManage.getNavRootId(), this.indexService,
				path, styleFlag);
		String content = FileIOUtil.readFile(file.getAbsolutePath());
		content = content.replaceAll("<\\?xml[^>]+>", "");
		request.setAttribute("menu", content);
		return mapping.findForward("nav_bannerMenuContent");
	}

	/**
	 * 方法说明： 跟据站点生成导航静态html页,（横向导航菜单)
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
	public ActionForward createNavigateHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		String styleFlag = request.getParameter("styleFlag");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);

		String url = "";// "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+request.getServletPath()+"?action=forwardMenuContentPage&siteUuid="+siteUuid
		// + "&styleFlag=" + styleFlag;
		url = request.getRequestURL().toString() + "?action=forwardMenuContentPage&siteUuid="
				+ siteUuid + "&styleFlag=" + styleFlag;
		String path = request.getSession().getServletContext().getRealPath("/modules") + "/"
				+ siteUuid + "_" + DateUtils.getCurrDate("yyyyMMddhhmmss") + ".html";
		if (siteManage.getSiteAddr() != null) {
			path = this.rootPath + siteManage.getSiteAddr();
			path = path + "/" + siteUuid + ".html";
		}

		UrlContentUtil.getUrlContent(url, path);
		request.setAttribute("message", "生成静态页成功");
		return mapping.findForward("message");
	}

	/**
	 * 方法说明： 生成树状导航菜单预览
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
	public ActionForward createTreeMenuHtmlPrivew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		String rootIndexuuid = siteManage.getNavRootId();
		if (rootIndexuuid == null) {
			request.setAttribute("message", "当前站点没有设置导航菜单结点!");
			return mapping.findForward("message");
		}
		StringBuffer html = GenerateMenu.createTreeMenu(rootIndexuuid, this.indexService, 2,
				"mytree");
		request.setAttribute("html", html);
		return mapping.findForward("nav_TreeMenuPriview");
	}

	/**
	 * 方法说明： 生成树状导航菜单静态页
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
	public ActionForward createTreeMenuHtml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);

		String url = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + request.getServletPath()
				+ "?action=forwardTreeMenuContentPage&siteUuid=" + siteUuid;
		String path = request.getRealPath("/modules") + "/" + siteUuid + "_"
				+ DateUtils.getCurrDate("yyyyMMddhhmmss") + ".html";
		if (siteManage.getSiteAddr() != null) {
			path = this.rootPath + siteManage.getSiteAddr();
			path = path + "/" + siteUuid + ".html";
		}

		UrlContentUtil.getUrlContent(url, path);

		request.setAttribute("message", "生成静态页成功");
		return mapping.findForward("message");
	}

	/**
	 * 生成单个纯树状菜单jsp页
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
	public ActionForward forwardTreeMenuContentPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);

		String rootIndexuuid = siteManage.getNavRootId();
		StringBuffer html = GenerateMenu.createTreeMenu(rootIndexuuid, this.indexService, 2,
				"mytree");
		request.setAttribute("html", html);
		return mapping.findForward("nav_TreeMenuContent");

	}

	/**
	 * 根据用户id查询站点树
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
	public ActionForward findSitesByUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// String siteId = request.getParameter("siteId");
		// this.perTreeService.delete(this.siteManageService.findById(siteId));
		return null;
	}

	/**
	 * 批量建站
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
	public ActionForward batchCreateSites(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// String siteParam = "朝阳供电公司,cy:海淀区供电公司,hd";
		// String siteId = "8a0d82af2b9f158d012ba08f18930020" ;
		String siteParam = request.getParameter("siteParam");
		String siteId = request.getParameter("siteId");
		String[] siteParams = siteParam.split(":");
		User user = WebUtil.getSessionUser(request);
		List<Group> groups = this.rbacService.getUserGroups(user);
		SiteManage sm = this.siteManageService.findById(siteId);
		for (int i = 0; i < siteParams.length; i++) {
			String[] tempName = siteParams[i].split(",");
			SiteManage tempSite = new SiteManage();// 站点
			tempSite.setCreateTime(new Date());
			tempSite.setParentSite(sm.getParentSite());
			tempSite.setUserNo("wpsadmin");
			tempSite.setTypeCode("01");
			tempSite.setSiteName(tempName[0]);
			tempSite.setSiteAddr(this.contextPath + tempName[1] + "/");
			tempSite.setParentSiteNo(sm.getParentSite().getSiteId());
			tempSite.setSiteStatus("1");
			this.siteManageService.save(tempSite);
			String siteIndexId = this.rootIndex.equals(tempSite.getParentSiteNo()) ? this.rootSite
					: tempSite.getParentSiteNo();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("indexuuid", tempSite.getSiteId());
			map.put("indexname", tempSite.getSiteName());
			map.put("parentindexuuid", siteIndexId);
			map.put("indexorder", "0");
			map.put("indextype", 3);
			this.jdbcIndexService.insert("lfs_index", map);
			Index index = this.indexService.findById(sm.getSiteId());
			for (Group group : groups) {
				List<String> groupids = new ArrayList<String>();
				groupids.add(group.getUuid());
				Set<Index> indexes = new HashSet<Index>(this.indexService.findIndexByGroupID(
						groupids, 2));
				indexes.add(index);
				// group.setIndexes(indexes);
				this.indexService.updateIndexGroup(new ArrayList<Index>(indexes), group.getUuid());
				// this.groupService.update(group);
			}
			SiteManage content = new SiteManage();// 目录
			Set<SiteManage> smSet = sm.getSubSites();
			for (SiteManage csm : smSet) {
				content.setCreateTime(new Date());
				content.setParentSite(tempSite);
				content.setSiteAddr(tempSite.getSiteAddr() + "index/");
				content.setUserNo("wpsadmin");
				content.setTypeCode("02");
				content.setSiteName("首页");
				content.setParentSiteNo(tempSite.getSiteId());
				content.setSiteStatus("1");
				this.siteManageService.save(content);
				String folder = this.rootPath + content.getSiteAddr();
				File file = new File(folder);
				if (!file.exists()) {
					if (file.mkdirs()) {
						log.info("创建目录成功!");
					}
				}
				// 页面
				TemplatePage temptp = this.templatePageService.findByProperty("siteId",
						csm.getSiteId()).get(0);
				TemplatePage tp = new TemplatePage();
				tp.setSiteId(content.getSiteId());
				tp.setSiteManage(content);
				tp.setSiteName(content.getSiteName());
				tp.setPageName("index.html");
				tp.setTemplateLayout(temptp.getTemplateLayout());
				tp.setTmplId(temptp.getTmplId());
				tp.setType("01");
				tp.setThemeCode(temptp.getThemeCode());
				this.templatePageService.save(tp);
				// 页面占位符
				List<TemplatePortletInfo> tpiList = temptp.getTemplatePortletInfoList();
				for (TemplatePortletInfo tpi : tpiList) {
					TemplatePortletInfo temptpi = new TemplatePortletInfo();
					if ("CMSPortlet".equals(tpi.getPortletType())) {
						String[] tempFunc = tpi.getFuncAction().split("portletId=")[1].split("_");
						temptpi.setFuncAction(tempName[1] + tempFunc[1] + tempFunc[2]);
					} else {
						temptpi.setFuncAction(tpi.getFuncAction());
					}
					temptpi.setPageId(tp.getPageId());
					temptpi.setPlaceHolderId(tpi.getPlaceHolderId());
					temptpi.setPortletId(tpi.getPortletId());
					temptpi.setPortletType(tpi.getPortletType());
					temptpi.setPlaceHolderType(tpi.getPlaceHolderType());
					temptpi.setHtmlCode(tpi.getHtmlCode());
					temptpi.setPortletProperty(tpi.getPortletProperty());
					temptpi.setTemplatePageInfo(tp);
					this.templatePortletInfoService.save(temptpi);
				}

			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print("1");
		return null;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sites
	 *            sites
	 * @param parentSite
	 *            parentSite
	 */
	@SuppressWarnings("unused")
	private void getSubSites(List<SiteManage> sites, SiteManage parentSite)
	{
		for (SiteManage s : sites) {
			if ("01".equals(s.getTypeCode())) {// 站点

				getSubSites(new ArrayList<SiteManage>(s.getSubSites()), s);
			} else {
				List<TemplatePage> tps = this.templatePageService.findByProperty("siteId",
						s.getSiteId());
				for (TemplatePage tp : tps) {
					TemplatePage tempTp = new TemplatePage();
					tempTp.setPageAddr("");
				}
			}

		}
	}

	/**
	 * 
	 * 方法说明：删除站点
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
	public ActionForward deleteSite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteId = request.getParameter("siteId");
		User user = WebUtil.getSessionUser(request);
		SiteManage site = this.siteManageService.findById(siteId);
		if (!("02".equals(site.getTypeCode()))) {// 删除站点在指标里的数据
			this.deleteSites(site, user);
			this.deleteSiteResources(site, user);
		} else {
			this.deletePages(site);
		}
		this.siteManageService.delete(site);
		String logMessage = "用户" + user.getName() + "删除站点" + site.getSiteName();

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		// user = this.rbacService.getUserByUuid(user.getUuid());

		return null;
	}

	/**
	 * 
	 * 方法说明： 递归删除该站点下的所有子站点的资源
	 * 
	 * @param sm
	 *            sm
	 * @param user
	 *            user
	 */
	private void deleteSites(SiteManage sm, User user)
	{
		Set<SiteManage> sms = sm.getSubSites();
		if (sms != null && !sms.isEmpty()) {
			for (SiteManage s : sms) {
				if (!("02".equals(s.getTypeCode()))) {
					this.deleteSites(s, user);
					this.deleteSiteResources(s, user);
				} else {
					this.deletePages(s);
				}
			}
		}
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sm
	 *            sm
	 * @param user
	 *            user
	 */
	private void deleteSiteResources(SiteManage sm, User user)
	{
		List<BaseInfo> biList = this.baseInfoService.findByProperty("siteId", sm.getSiteId());
		List<ResourceSite> rList = this.resourceService.findByProperty("siteId", sm.getSiteId());
		Index siteIndex = this.indexService.findById(sm.getSiteId());
		String navId = sm.getNavRootId();
		if (navId != null) {
			Index index = this.indexService.findById(sm.getNavRootId());
			if (index != null) {
				this.indexService.delete(index);
			}
		}
		// 删除绑定的资源
		if (biList != null) {
			for (BaseInfo bi : biList) {
				this.baseInfoService.delete(bi);
			}
		}
		// 删除站点下的资源
		if (rList != null) {
			for (ResourceSite r : rList) {
				this.resourceService.delete(r);
			}
		}
		if (siteIndex != null) {
			this.indexService.delete(siteIndex);
			// if (!rbacService.isAdmin(user)) {
			// List<Group> groups = this.rbacService.getUserGroups(user);
			// for (Group group : groups) {
			// List<String> groupIds = new ArrayList<String>();
			// groupIds.add(group.getUuid());
			// Set<Index> sites = new HashSet<Index>(this.indexService.findIndexByGroupID(
			// groupIds, 2));
			// sites.remove(siteIndex);
			// this.indexService
			// .updateIndexGroup(new ArrayList<Index>(sites), group.getUuid());
			// }
			// }
			String filePath = this.rootPath + sm.getSiteAddr();
			FileUtil.removeFolder(filePath);
		}
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sm
	 *            sm
	 */
	private void deletePages(SiteManage sm)
	{
		List<TemplatePage> tpList = this.templatePageService.findByProperty("siteId",
				sm.getSiteId());
		for (TemplatePage tp : tpList) {
			List<TemplatePortletInfo> tpiList = tp.getTemplatePortletInfoList();
			for (TemplatePortletInfo tpi : tpiList) {
				this.templatePortletInfoService.delete(tpi);
			}
			this.templatePageService.delete(tp);
		}
	}

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
	public ActionForward deleteSite1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteId = request.getParameter("siteId");
		User user = WebUtil.getSessionUser(request);
		List<Group> groups = this.rbacService.getUserGroups(user);
		SiteManage sm = this.siteManageService.findById(siteId);
		List<BaseInfo> biList = this.baseInfoService.findByProperty("siteId", sm.getSiteId());
		List<ResourceSite> rList = this.resourceService.findByProperty("siteId", sm.getSiteId());
		Index siteIndex = this.indexService.findById(sm.getSiteId());
		try {
			if (!("02".equals(sm.getTypeCode()))) {// 删除站点在指标里的数据
				String navId = sm.getNavRootId();
				if (navId != null) {
					Index index = this.indexService.findById(sm.getNavRootId());
					if (index != null) {
						this.indexService.delete(index);
					}
				}
				// 删除绑定的资源
				for (BaseInfo bi : biList) {
					this.baseInfoService.delete(bi);
				}
				// 删除站点下的资源
				for (ResourceSite r : rList) {
					this.resourceService.delete(r);
				}
				Set<SiteManage> subSiteManages = sm.getSubSites();
				for (SiteManage siteManage : subSiteManages) {
					List<TemplatePage> tpList = this.templatePageService.findByProperty("siteId",
							siteManage.getSiteId());
					for (TemplatePage tp : tpList) {
						List<TemplatePortletInfo> tpiList = tp.getTemplatePortletInfoList();
						for (TemplatePortletInfo tpi : tpiList) {
							this.templatePortletInfoService.delete(tpi);
						}
						this.templatePageService.delete(tp);
					}
					// this.siteManageService.delete(siteManage);
				}
				if (siteIndex != null) {
					this.indexService.delete(siteIndex);
				}
				for (Group group : groups) {
					List<String> groupIds = new ArrayList<String>();
					groupIds.add(group.getUuid());
					Set<Index> sites = new HashSet<Index>(this.indexService.findIndexByGroupID(
							groupIds, 2));
					sites.remove(siteIndex);
					// group.setIndexes(sites);
					// this.groupService.update(group);
					this.indexService
							.updateIndexGroup(new ArrayList<Index>(sites), group.getUuid());
				}
			} else {
				List<TemplatePage> tpList = this.templatePageService.findByProperty("siteId",
						sm.getSiteId());
				for (TemplatePage tp : tpList) {
					List<TemplatePortletInfo> tpiList = tp.getTemplatePortletInfoList();
					for (TemplatePortletInfo tpi : tpiList) {
						this.templatePortletInfoService.delete(tpi);
					}
					this.templatePageService.delete(tp);
				}
			}

			this.siteManageService.delete(this.siteManageService.findById(siteId));
			String filePath = this.rootPath + sm.getSiteAddr();
			FileUtil.removeFolder(filePath);
		} catch (RuntimeException e) {
			log.debug("站点删除失败", this.clazz);
			throw new RuntimeException(I18NResourceUtil.getValue("delete_sites_faild",
					request.getLocale()));
		}
		return null;
	}
}
