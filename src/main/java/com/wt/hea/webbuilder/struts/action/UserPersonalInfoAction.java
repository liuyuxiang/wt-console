package com.wt.hea.webbuilder.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.PortletProperty;
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
 * 功能说明: 页面中的portlet信息控制
 * 编写日期:	2011-3-29
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserPersonalInfoAction extends DispatchAction
{
	/**
	 * 保存、更新页面中的portlet信息
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
		String portletId = request.getParameter("portletId");
		String pageId = request.getParameter("pageId");

		User user = WebUtil.getSessionUser(request);
		UserPage userPage = this.userPageService.findById(pageId);
		PortletProperty portletProperty = this.portletPropertyService.findById(portletId);
		UserPersonalInfo userPersonalInfo = new UserPersonalInfo();

		userPersonalInfo.setDispSn(1);
		userPersonalInfo.setEditAction(portletProperty.getEditAction());
		userPersonalInfo.setFuncAction(portletProperty.getFuncAction());
		userPersonalInfo.setPageId(userPage.getPageId());
		userPersonalInfo.setPortletId(portletProperty.getPortletId());
		userPersonalInfo.setPortletProperty(portletProperty);
		userPersonalInfo.setPortletSatus("1");
		userPersonalInfo.setRowNo(1);
		userPersonalInfo.setShowName(portletProperty.getPortletName());
		userPersonalInfo.setSortNo(1);
		userPersonalInfo.setTitleUrl(portletProperty.getTitleUrl());
		userPersonalInfo.setType(portletProperty.getPortletType());
		userPersonalInfo.setUserId(user.getId());
		userPersonalInfo.setUserName(user.getName());
		userPersonalInfo.setUserNo(user.getUuid());
		userPersonalInfo.setUserPage(userPage);
		this.userPersonalInfoService.save(userPersonalInfo);
		List<UserPersonalInfo> userPersonalList = userPage.getUserPersonalInfo();
		for (UserPersonalInfo upi : userPersonalList) {

			if (upi.getSortNo() == 1) {
				upi.setDispSn(upi.getDispSn() + 1);
				this.userPersonalInfoService.update(upi);
			}
		}
		// this.userPageService.update(userPage);
		return null;
	}

	/**
	 * 
	 * 方法说明： 删除用户页面portlet信息
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

		return null;
	}

	/**
	 * 通过id删除该portlet信息 request参数为portletId
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
		String portletId = request.getParameter("portletId");
		this.userPersonalInfoService.deleteById(portletId);
		return null;
	}

	/**
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

		return null;
	}

	/**
	 * 通过request参数“id”查找出portlet信息
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
		// String userId = request.getParameter("id");
		// UserPersonalInfo upi = this.userPersonalInfoService.findById(userId);

		return null;
	}

	/**
	 * 通过一个页面portlet个性化的JSON信息<br>
	 * 把个性化信息持久化到数据库<br>
	 * JSON的格式：<br>
	 * request参数名tabs(JSON格式)
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
	@SuppressWarnings("static-access")
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("utf-8");

		String temp = request.getParameter("tabs");
		JSONObject js = JSONObject.fromObject(temp);
		PagesContainer pc = (PagesContainer) js.toBean(js, PagesContainer.class);
		JSONArray ja = JSONArray.fromObject(pc.getPages());

		for (int i = 0; i < ja.size(); i++) {
			JSONObject o = JSONObject.fromObject(ja.get(i));
			Pages pages = (Pages) JSONObject.toBean(o, Pages.class);
			// 操作页面
			UserPage userPage = this.userPageService.findById(pages.getId());
			userPage.setPageTitle(pages.getText());// 得到页面标题
			userPage.setLayoutCode(pages.getLayoutCode());// 布局编号
			userPage.setDispSn(i);
			userPage.setThemeCode(pages.getThemeCode());
			JSONArray jsonColumns = JSONArray.fromObject(pages.getColumns());
			for (int j = 0; j < jsonColumns.size(); j++) {
				JSONObject jsonColumn = JSONObject.fromObject(jsonColumns.get(j));
				PageColumns pageColumn = (PageColumns) JSONObject.toBean(jsonColumn,
						PageColumns.class);
				pageColumn.getWidth();// 得到列宽度

				JSONArray jsonPortlets = JSONArray.fromObject(pageColumn.getPortlets());
				for (int k = 0; k < jsonPortlets.size(); k++) {
					JSONObject jsonPortlet = JSONObject.fromObject(jsonPortlets.get(k));
					PagePortlets pagePortlet = (PagePortlets) JSONObject.toBean(jsonPortlet,
							PagePortlets.class);

					UserPersonalInfo userPersonalInfo = this.userPersonalInfoService
							.findById(pagePortlet.getId());//
					userPersonalInfo.setDispSn(k + 1);
					userPersonalInfo.setSortNo(j + 1);
					this.userPersonalInfoService.update(userPersonalInfo);

				}
			}
			this.userPageService.update(userPage);
		}
		return null;
	}

}
