package com.wt.hea.webbuilder.struts.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.util.BeanHelper;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.LayoutDefinition;
import com.wt.hea.webbuilder.model.PersonalSystemParameter;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;
import com.wt.hea.webbuilder.model.UserPage;
import com.wt.hea.webbuilder.model.UserPersonalInfo;
import com.wt.hea.webbuilder.struts.form.PageColumns;
import com.wt.hea.webbuilder.struts.form.PagePortlets;
import com.wt.hea.webbuilder.struts.form.Pages;
import com.wt.hea.webbuilder.struts.form.PagesContainer;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserPageAction extends DispatchAction
{

	/**
	 * 添加一个page页
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
		// 用于判断用户是添加空白页面还是推荐页面

		String dispSn = request.getParameter("dispSn");
		String tempIds = request.getParameter("pageIds");
		String[] templatePageIds = null;
		String[] pageNos;

		if (tempIds != null) {
			templatePageIds = tempIds.split(";");
		}
		String returnValue = "";

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html charset=utf-8");
		PrintWriter out = response.getWriter();

		User user = WebUtil.getSessionUser(request);
		UserPage userPage = new UserPage();
		int disp = Integer.valueOf(dispSn);
		if (templatePageIds == null || (templatePageIds != null && templatePageIds.length == 0)) {
			userPage.setLayoutCode("layoutCode1");
			userPage.setThemeCode("theme1");
			userPage.setPageTitle("我的页面");
			userPage.setDispSn(disp);
			userPage.setUserPersonalInfo(null);
			userPage.setUserId(user.getId());
			userPage.setUserName(user.getName());
			userPage.setUserNo(user.getUuid());
			this.userPageService.save(userPage);
			String pageId = userPage.getPageId();
			returnValue = pageId;
		} else { // 添加推荐页面
			int i = 0;
			pageNos = new String[templatePageIds.length];// 推荐页面的页面编号
			for (String tempPageId : templatePageIds) {

				TemplatePage templatePage = this.templatePageService.findById(tempPageId);
				pageNos[i] = templatePage.getPageNo();
				BeanHelper.copyProperties(userPage, templatePage);
				userPage.setPageId(null);
				userPage.setUserId(user.getId());
				userPage.setUserName(user.getName());
				userPage.setUserNo(user.getUuid());
				userPage.setDispSn(disp);
				disp += 1;
				i++;
				this.userPageService.save(userPage);
				for (TemplatePortletInfo tpi : templatePage.getTemplatePortletInfoList()) {
					UserPersonalInfo upi = new UserPersonalInfo();
					BeanHelper.copyProperties(upi, tpi);
					upi.setUserId(user.getId());
					upi.setUserName(user.getName());
					upi.setUserNo(user.getUuid());
					upi.setUserPage(userPage);
					this.userPersonalInfoService.save(upi);
				}
			}
			List<UserPage> userPageList = this.userPageService.findByUserIdAndNos(user.getId(),
					pageNos);

			returnValue = this.getJSON(userPageList);
		}
		out.print(returnValue);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 通过接收一个tab页的ID删除一个tab页
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
		String pageId = request.getParameter("pageId");
		UserPage userPage = this.userPageService.findById(pageId);
		if (userPage != null) {
			for (UserPersonalInfo upi : userPage.getUserPersonalInfo()) {

				this.userPersonalInfoService.delete(upi);
			}
			this.userPageService.delete(userPage);
		}

		return null;
	}

	/**
	 * 
	 * 通过tab的ID更新页面的信息
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
	 * 
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String pageId = request.getParameter("id");

		UserPage userPage = this.userPageService.findById(pageId);
		//
		userPage.setPageTitle("");

		this.userPageService.update(userPage);
		return null;
	}

	/**
	 * 通过用户id查找出该用户的个性化信息
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
	public ActionForward findByUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String style = "green";
		User user = WebUtil.getSessionUser(request);
		String userId = user.getId();
		// 查找出该用户有几个tabPage
		List<UserPage> userPageList = this.userPageService.findByUserId(userId);
		// 查找默认的页面
		if (userPageList != null && !userPageList.isEmpty()) {
			List<TemplatePage> tpList = this.templatePageService.findByProperty("ifDefault", 1);
			List<UserPersonalInfo> upiList = new ArrayList<UserPersonalInfo>();
			List<TemplatePortletInfo> tpiList;
			for (TemplatePage tp : tpList) {
				UserPage up = new UserPage();
				BeanHelper.copyProperties(up, tp);
				up.setUserId(userId);
				up.setUserName(user.getName());
				up.setUserNo(user.getUuid());
				up.setPageId(null);
				this.userPageService.save(up);
				tpiList = tp.getTemplatePortletInfoList();
				for (TemplatePortletInfo tpi : tpiList) {
					UserPersonalInfo upi = new UserPersonalInfo();
					BeanHelper.copyProperties(upi, tpi);
					upi.setUserPage(up);
					upi.setUserId(userId);
					upi.setUserName(user.getName());
					upi.setUserNo(user.getUuid());
					upi.setPortletId(null);
					this.userPersonalInfoService.save(upi);
					upiList.add(upi);
				}
				up.setUserPersonalInfo(upiList);
				userPageList.add(up);
			}
			style = this.themeDefinitionService.findById(userPageList.get(0).getThemeCode())
					.getThemeName();
			String jo = this.getJSON(userPageList);

			request.getSession().setAttribute("style", style);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json; charset=utf-8");
			PrintWriter out = response.getWriter();

			out.print(jo.toString());
			out.flush();
			out.close();
		}
		return null;
	}

	/**
	 * 
	 * @param userPageList
	 *            userPageList
	 * @return String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getJSON(List<UserPage> userPageList)
	{

		PagesContainer pagesContainer = new PagesContainer();
		List<Pages> pageList = new ArrayList<Pages>();
		if (userPageList != null && userPageList.isEmpty()) {
			for (int i = 0; i < userPageList.size(); i++) {
				// 先声明一个容器，用于存放列的
				List columnList = new ArrayList();
				UserPage userPage = userPageList.get(i);
				// 把页面加载到pagesContainer中
				Pages pages = new Pages();

				pages.setId(userPage.getPageId());
				pages.setLayoutCode(userPage.getLayoutCode());
				pages.setText(userPage.getPageTitle());
				pages.setThemeCode(userPage.getThemeCode());
				pages.setThemeContent(this.themeDefinitionService.findById(userPage.getThemeCode())
						.getThemeContent());
				// 找出每个页面中有几个portlet
				List<UserPersonalInfo> userPersonalInfoList = userPage.getUserPersonalInfo();

				LayoutDefinition layoutDefinition = this.layoutDefinitionService.findById(userPage
						.getLayoutCode());

				List columns = new ArrayList();
				for (int k = 0; k < layoutDefinition.getLayoutContent().split(";").length; k++) {
					PageColumns pageColumn = new PageColumns();
					pageColumn.setWidth(layoutDefinition.getLayoutContent().split(";")[k]);

					columnList.add(new ArrayList());
					PagePortlets portlet;
					for (int j = 0; j < userPersonalInfoList.size(); j++) {
						UserPersonalInfo userPersonalInfo = userPersonalInfoList.get(j);
						if (k + 1 == userPersonalInfo.getSortNo()) {

							if (columnList != null && columnList.size() > k) {
								portlet = new PagePortlets();
								portlet.setId(userPersonalInfo.getId());
								portlet.setTitle(userPersonalInfo.getShowName());
								portlet.setType(userPersonalInfo.getType());
								portlet.setActionUrl(userPersonalInfo.getFuncAction());
								portlet.setEditUrl(userPersonalInfo.getEditAction());

								List temp = (List) columnList.get(k);
								temp.add(portlet);
								columnList.remove(k);
								columnList.add(temp);
							}
						}
					}
					List<PagePortlets> portlets = new ArrayList<PagePortlets>();
					portlets = (List<PagePortlets>) columnList.get(k);
					pageColumn.setPortlets(portlets);
					columns.add(pageColumn);
				}
				pages.setColumns(columns);
				pageList.add(pages);
			}
		}
		// 加入用户个性化的一些设置
		List<PersonalSystemParameter> pspList = this.personalSystemParameterService
				.findByType(new String[] { "overall", "personal" });// findByProperty("codeType",
																	// "personal");
		pagesContainer.setPages(pageList);

		/**
		 * 从纪录转变到属性，设置相应状态
		 */
		for (PersonalSystemParameter psp : pspList) {
			try {
				BeanHelper.forceSetProperty(pagesContainer, psp.getCodeName(), psp.getCodeValue());
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		JSONObject jo = JSONObject.fromObject(pagesContainer);
		return jo.toString();
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
	public ActionForward saveTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String themeCode = request.getParameter("theme");
		UserPage userPage = null;
		List<UserPage> ups = this.userPageService.findByUserId(user.getId());
		if (ups != null && !ups.isEmpty()) {
			userPage = ups.get(0);
		}
		if (userPage != null) {
			userPage.setThemeCode(themeCode);
			this.userPageService.update(userPage);
		} else {
			userPage = new UserPage();
			userPage.setThemeCode(themeCode);
			userPage.setUserId(user.getId());
			userPage.setUserName(user.getName());
			this.userPageService.save(userPage);
		}
		request.getSession().setAttribute("theme", userPage.getThemeCode());
		return null;
	}

}
