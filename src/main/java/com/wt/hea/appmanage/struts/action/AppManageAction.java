package com.wt.hea.appmanage.struts.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.appmanage.model.AppManage;
import com.wt.hea.appmanage.struts.form.AppInfos;
import com.wt.hea.appmanage.struts.form.AppManageForm;
import com.wt.hea.common.model.Page;
import com.wt.hea.common.model.SystemCode;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.webbuilder.model.ResourceSite;
import com.wt.hea.webbuilder.model.TemplateLayout;
import com.wt.hea.webbuilder.model.ThemeDefinition;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-26
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class AppManageAction extends BaseAction
{
	/**
	 * 
	 */
	private static final String ADDAPP2 = "addapp2";

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
	public ActionForward appList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// TODO Auto-generated method stub
		String currPage = request.getParameter("currPage");
		String perPageRecode = request.getParameter("perPage");
		// Verify validator = Verifier.getInstance();
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
		Page<AppManage> appManages = new Page<AppManage>(Integer.valueOf(perPageRecode),
				Integer.valueOf(currPage));
		appManages = this.appManageService.loadPage(appManages);

		request.setAttribute("apps", appManages);
		return mapping.findForward("applist");
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
	@Deprecated
	public ActionForward addApp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		AppManageForm amf = (AppManageForm) form;
		AppManage am = amf.getAppManage();
		String indexId = request.getParameter("id");
		this.appManageService.update(am);
		SystemCode sc = new SystemCode();
		sc.setRegCode(APPLINKS);
		List<SystemCode> scs = this.systemCodeService.findByExample(sc);
		sc.setRegCode("site_link");
		List<SystemCode> siteScs = this.systemCodeService.findByExample(sc);
		if (am.getAppType().equals("02")) {
			scs.addAll(siteScs);
		}
		Index index = new Index();
		index.setIndexname(am.getAppName());
		index.setParentIndex(this.indexService.findById(indexId));
		index.setParentindexuuid(indexId);
		Index i = this.indexService.update(index);
		for (SystemCode s : scs) {
			index = new Index();
			index.setIndexname(s.getRegName());
			index.setIndexurl(s.getRegValue());
			index.setParentindexuuid(i.getIndexuuid());
			index.setParentIndex(i);
			this.indexService.update(index);
		}
		return mapping.findForward("applist");
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
	public ActionForward deleteApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		String appNo = "";
		if (id != null && !"".equals(id)) {
			AppManage am = this.appManageService.findById(id);
			appNo = am.getAppNo();
			this.appManageService.deleteById(id);
			List<Index> indexes = this.indexService.findByProperty("appId", id);
			for (Index i : indexes) {
				if (i != null && i.getIndexuuid() != null
						&& !"hea_indexroot_0".equals(i.getIndexuuid())) {
					this.indexService.delete(i);
					if (!"".equals(appNo)) {
						List<Index> is = this.indexService.findByProperty("appId", appNo);
						for (Index idx : is) {
							if (!"hea_indexroot_0".equals(i.getIndexuuid())) {
								this.indexService.delete(idx);
							}
						}
					}
				}
			}
		} else {
			request.setAttribute("message", "删除失败，此应用可能已被删除！");
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print("1");
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
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		String indexId = request.getParameter("indexId");

		if (id != null && !"".equals(id)) {
			AppManage am = this.appManageService.findById(id);
			request.setAttribute("am", am);
		}
		request.setAttribute("indexId", indexId);
		return mapping.findForward("updateapp");
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
	public ActionForward updateApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AppManageForm amf = (AppManageForm) form;
		AppManage am = amf.getAppManage();
		this.appManageService.update(am);
		return mapping.findForward("applist");
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
	 *             Exception
	 */
	public ActionForward addApp1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String forwardPath = request.getParameter("url");
		AppManageForm amf = (AppManageForm) form;

		AppManage am = amf.getAppManage();
		String indexId = request.getParameter("id"); // 多应用app_id，也就是lfs_index.indexuuid
		boolean flag = false;
		if (am.getAppId() == null) {
			flag = true;
		}
		am = this.appManageService.update(am);
		if (flag) {
			SystemCode sc = new SystemCode();
			sc.setRegCode(APPLINKS);
			List<SystemCode> scs = this.systemCodeService.findByExample(sc);
			sc.setRegCode("site_link");
			List<SystemCode> siteScs = this.systemCodeService.findByExample(sc);
			if (am.getAppType().equals("02")) {
				scs.addAll(siteScs);
			}
			Index index = new Index();
			index.setIndexname(am.getAppName());
			index.setParentIndex(this.indexService.findById(indexId));
			index.setParentindexuuid(indexId);
			index.setIndextype(5);
			index.setIndexorder(0);
			index.setWay("1");
			index.setAppId(am.getAppNo());
			Index i = this.insertIndex(am, indexId);
			for (SystemCode s : scs) {
				index = new Index();
				index.setIndexname(s.getRegName());
				index.setIndexurl(s.getRegValue() + "&appId=" + am.getAppNo());
				index.setParentindexuuid(i.getIndexuuid());
				index.setParentIndex(i);
				index.setIndextype(5);
				index.setWay("1");
				index.setIndexorder(s.getRegOrder());
				index.setAppId(am.getAppNo());

				index = this.indexService.update(index);
			}
		} else {
			Index index = this.indexService.findById(am.getAppId());
			if (index != null) {
				index.setIndexname(am.getAppName());
				this.indexService.update(index);
			}
		}
		request.setAttribute("indexId", indexId);
		request.setAttribute("appId", am.getAppId());
		String message = "<script>window.location.href='" + request.getContextPath()
				+ "/heaAppManageAction.hea?action=appList&treeNodeId=" + indexId
				+ "';top.refreshTree(\"" + indexId + "\")</script>";
		if (ADDAPP2.equals(forwardPath)) {
			message = "<script>top.refreshTree(\"" + indexId + "\")</script>";
		}
		request.setAttribute("message", message);
		return mapping.findForward(forwardPath);
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
	 *             Exception
	 */
	public ActionForward addApp2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String appId = (String) request.getAttribute("appId");
		String id = request.getParameter("id");
		if (id == null) {
			id = (String) request.getAttribute("id");
		}
		// logo资源
		List<ResourceSite> logoInfos = this.getAppResources(appId, LOGO);
		request.setAttribute("logoInfos", logoInfos);
		// banner资源
		List<ResourceSite> banners = this.getAppResources(appId, BANNER);
		request.setAttribute("banners", banners);
		// 主题信息
		List<ThemeDefinition> themes = this.themeDefinitionService.findByProperty("appId", appId);
		request.setAttribute("themes", themes);
		// 布局信息
		List<TemplateLayout> layouts = this.templateLayoutService.findByProperty("appId", appId);
		SystemCode appLogo = this.getAppInfo(appId, LOGOTYPE);
		SystemCode appBanner = this.getAppInfo(appId, BANNERTYPE);
		SystemCode appTheme = this.getAppInfo(appId, THEMETYPE);
		AppManage am = this.appManageService.findById(appId);
		request.setAttribute("appLogo", appLogo);
		request.setAttribute("appBanner", appBanner);
		request.setAttribute("appTheme", appTheme);
		request.setAttribute("am", am);
		request.setAttribute("layouts", layouts);
		request.setAttribute("appId", appId);
		request.setAttribute("id", id);
		return mapping.findForward("addapp2page");
	}

	/**
	 * 方法说明： 准备第二步信息方便修改
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
	public ActionForward toUpdateApp2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String appId = request.getParameter("appId");
		request.setAttribute("appId", appId);
		return this.addApp2(mapping, form, request, response);
	}

	/**
	 * 方法说明： 第二步保存
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
	public ActionForward saveApp2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		AppManageForm amf = (AppManageForm) form;
		AppInfos ai = amf.getAppInfos();
		AppManage am = amf.getAppManage();
		String appStatus = am.getAppStatus();
		am = this.appManageService.findById(am.getAppId());
		SystemCode sc = null;
		if (ai.getBannerSystemId() != null && !"".equals(ai.getBannerSystemId())) {
			sc = this.systemCodeService.findById(ai.getBannerSystemId());
			sc.setRegValue(ai.getBannerId());
			this.systemCodeService.update(sc);
		} else if (ai.getBannerId() != null) {
			sc = new SystemCode();
			ResourceSite resource = this.resourceService.findById(ai.getBannerId());
			sc.setAppId(am.getAppNo());
			sc.setRegCode(BANNERTYPE);
			sc.setRegName(resource.getResName());
			sc.setRegValue(ai.getBannerId());
			this.systemCodeService.save(sc);
		}
		if (ai.getLogoSystemId() != null && !"".equals(ai.getLogoSystemId())) {
			sc = this.systemCodeService.findById(ai.getLogoSystemId());
			sc.setRegValue(ai.getLogoId());
			this.systemCodeService.update(sc);
		} else if (ai.getLogoId() != null) {
			ResourceSite resource = this.resourceService.findById(ai.getLogoId());
			sc = new SystemCode();
			sc.setAppId(am.getAppNo());
			sc.setRegCode(LOGOTYPE);
			sc.setRegName(resource.getResName());
			sc.setRegValue(ai.getLogoId());
			this.systemCodeService.save(sc);
		}
		if (ai.getThemeSystemId() != null && !"".equals(ai.getThemeSystemId())) {
			sc = this.systemCodeService.findById(ai.getThemeSystemId());
			sc.setRegValue(ai.getThemeId());
			this.systemCodeService.update(sc);
		} else if (ai.getThemeId() != null) {
			ThemeDefinition td = this.themeDefinitionService.findById(ai.getThemeId());
			sc = new SystemCode();
			sc.setAppId(am.getAppNo());
			sc.setRegCode(THEMETYPE);
			sc.setRegName(td.getThemeName());
			sc.setRegValue(ai.getThemeId());
			this.systemCodeService.save(sc);
		}
		am.setAppStatus(appStatus);
		this.appManageService.update(am);
		request.setAttribute("appId", am.getAppId());
		request.setAttribute("id", id);
		return this.addApp2(mapping, form, request, response);
	}

	/**
	 * 方法说明：
	 * 
	 * @param am
	 *            am
	 * @param indexId
	 *            indexId
	 * @return insertindex
	 */
	private Index insertIndex(AppManage am, String indexId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("indexname", am.getAppName());
		map.put("parentindexuuid", indexId);
		map.put("indextype", 5);
		map.put("indexorder", 0);
		map.put("way", "1");
		map.put("app_Id", am.getAppNo());
		map.put("indexuuid", am.getAppId());
		this.jdbcIndexService.insert(LFSINDEX, map);
		Index index = this.indexService.findById(am.getAppId());
		return index;
	}

	/**
	 * 方法说明： 获取应用下相应类型的资源
	 * 
	 * @param appId
	 *            appId
	 * @param resType
	 *            resType
	 * @return Resource
	 */
	private List<ResourceSite> getAppResources(String appId, String resType)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appId);
		map.put("resType", resType);
		List<ResourceSite> resources = this.resourceService.findByProperty(map);
		return resources;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param appId
	 *            appId
	 * @param regCode
	 *            regCode
	 * @return SystemCode
	 */
	private SystemCode getAppInfo(String appId, String regCode)
	{
		AppManage am = this.appManageService.findById(appId);
		SystemCode sc = new SystemCode();
		sc.setAppId(am.getAppNo());
		sc.setRegCode(regCode);
		List<SystemCode> scs = this.systemCodeService.findByExample(sc);
		if (scs != null && !scs.isEmpty()) {
			sc = scs.get(0);
		}
		return sc;
	}

	/**
	 * 注册资源表中的资源类型
	 */
	private final static String LOGO = "05";

	/**
	 * 注册资源表中的资源类型
	 */
	private final static String BANNER = "07";

	/**
	 * syscode中的banner的注册reg_code值
	 */
	private final static String BANNERTYPE = "banner";

	/**
	 * syscode中的logo的注册reg_code值
	 */
	private final static String LOGOTYPE = "logo";

	/**
	 * syscode中的的注册reg_code值
	 */
	private final static String THEMETYPE = "theme";

	/**
	 * syscode中的资源的注册reg_code值
	 */
	private final static String APPLINKS = "app_link";

	/**
	 * jdbc操作lfsindex表
	 */
	private final static String LFSINDEX = "LFS_INDEX";
}
