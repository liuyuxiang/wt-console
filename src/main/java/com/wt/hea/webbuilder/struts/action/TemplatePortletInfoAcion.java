package com.wt.hea.webbuilder.struts.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.PortletProperty;
import com.wt.hea.webbuilder.model.SiteManage;
import com.wt.hea.webbuilder.model.TemplateLayout;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;
import com.wt.hea.webbuilder.struts.form.Prefix;
import com.wt.hea.webbuilder.struts.form.PrefixContainer;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 模板页面Action
 * 编写日期:	2011-4-7
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class TemplatePortletInfoAcion extends DispatchAction
{

	/**
	 * 获取日志实例
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

	/**
	 * 保存占位符中填充的信息
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
	public ActionForward savePageInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		PrintWriter out = response.getWriter();
		try {
			String tmplId = request.getParameter("tmplId");
			String siteId = request.getParameter("siteId");
			String pageName = request.getParameter("pageName");
			String themeCode = request.getParameter("themeCode");
			String pageId = request.getParameter("pageId");
			String type = request.getParameter("pageType");
			TemplatePage page = this.templatePageService.findById(pageId);
			if (page == null)
				page = new TemplatePage();

			page.setPageName(pageName == null ? page.getPageName() : pageName);
			if (siteId == null) {
				siteId = page.getSiteId();
			}
			SiteManage site = this.siteManageService.findById(siteId);
			page.setSiteManage(site);
			page.setSiteName(site.getParentSite().getSiteName());
			page.setSiteNo(site.getSiteNo());
			page.setPageAddr("");
			// 固定模板方式
			page.setType(type == null ? page.getType() : type);
			page.setPubTime(new Date());
			page.setPubAddr(this.rootPath + site.getSiteAddr() + File.separator
					+ (pageName == null ? page.getPageName() : pageName));
			if (tmplId == null) {
				tmplId = page.getTmplId();
			}
			TemplateLayout layout = this.templateLayoutService.findById(tmplId);
			page.setTemplateLayout(layout);
			page.setUserNo(user.getId());
			page.setThemeCode(themeCode == null ? page.getThemeCode() : themeCode);
			if (page.getPageId() == null) {
				this.templatePageService.save(page);
				String logMessage = "用户" + user.getName() + "保存页面" + page.getPageName();

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			} else {
				this.templatePageService.update(page);
				String logMessage = "用户" + user.getName() + "更新页面" + page.getPageName();

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			}
			String savePrefix = "";
			String temp = request.getParameter("contents");
			JSONObject js = JSONObject.fromObject(temp);
			PrefixContainer pc = (PrefixContainer) JSONObject.toBean(js, PrefixContainer.class);
			JSONArray ja = JSONArray.fromObject(pc.getPrefixes());

			for (int i = 0; i < ja.size(); i++) {
				JSONObject o = JSONObject.fromObject(ja.get(i));
				Prefix prefix = (Prefix) JSONObject.toBean(o, Prefix.class);
				TemplatePortletInfo tpi = this.templatePortletInfoService
						.findById(prefix.getTpId());
				if (tpi == null)
					tpi = new TemplatePortletInfo();
				tpi.setFuncAction(prefix.getDataurl());
				PortletProperty p = this.portletPropertyService.findById(prefix.getPortletId());
				tpi.setPortletProperty(p);
				tpi.setTemplatePageInfo(page);
				tpi.setPlaceHolderId(prefix.getPrefix());
				tpi.setPlaceHolderType(prefix.getType());
				tpi.setPortletType(prefix.getPortletType());
				if (prefix.getHtmlCode() != null) {
					tpi.setHtmlCode(prefix.getHtmlCode().replaceAll("<", "&lt;")
							.replaceAll(">", "&gt;"));
				}
				if (tpi.getTpId() == null) {
					this.templatePortletInfoService.save(tpi);

					savePrefix = savePrefix.concat(prefix.getPrefix().concat(":")
							.concat(tpi.getTpId().concat(";")));

				} else {
					this.templatePortletInfoService.update(tpi);
				}
			}
			if (savePrefix.endsWith(";")) {
				savePrefix = savePrefix.substring(0, savePrefix.length() - 1);
			}
			out.print(page.getPageId() + "-" + savePrefix);
		} catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
		}
		out.flush();
		out.close();

		return null;
	}

	/**
	 * 删除portlet的内容
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
	public ActionForward deletePortletInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String tpId = request.getParameter("tpId");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (tpId != null && !"".equals(tpId)) {
			this.templatePortletInfoService.deleteById(tpId);
			out.print("1");
		} else {
			out.print("0");
		}
		return null;
	}
}
