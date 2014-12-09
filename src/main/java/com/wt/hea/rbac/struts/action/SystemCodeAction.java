package com.wt.hea.rbac.struts.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.infrastructure.event.impl.EventResponse;
import com.wt.hea.common.model.Page;
import com.wt.hea.common.model.SystemCode;
import com.wt.hea.rbac.struts.form.SystemCodeForm;

/**
 * <pre>
 * 业务名:
 * 功能说明: 基本数据的增删查改的action类
 * 编写日期:	2011-7-28
 * 作者:	mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SystemCodeAction extends BaseAction
{

	/**
	 * 
	 * 方法说明：保存数据
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
	 */

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		SystemCodeForm scf = (SystemCodeForm) form;
		SystemCode sc = scf.getSystemCode();
		this.systemCodeService.save(sc);
		EventResponse.getInstance().eventSimplePopBox("保存成功!", request);
		loadPage(mapping, form, request, response);
		return mapping.findForward("systemcodelist1");
	}

	/**
	 * 
	 * 方法说明：删除其中一条数据
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
	 * 
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String scId = request.getParameter("id");
		if (scId != null) {
			SystemCode systemCode = this.systemCodeService.findById(scId);
			this.systemCodeService.delete(systemCode);
			EventResponse.getInstance().eventSimplePopBox("删除成功！", request);
		}
		return loadPage(mapping, form, request, response);
	}

	/**
	 * 
	 * 方法说明：读取一条数据
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
	 */
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			SystemCode systemCode = this.systemCodeService.findById(id);
			request.setAttribute("systemCode", systemCode);
		}
		return mapping.findForward("updatesystemcode1");
	}

	/**
	 * 
	 * 方法说明：读取数据并进行修改
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
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		SystemCodeForm scf = (SystemCodeForm) form;
		SystemCode sc = scf.getSystemCode();
		if (StringUtils.isNotEmpty(sc.getId())) {
			this.systemCodeService.update(sc);
			EventResponse.getInstance().eventSimplePopBox("更新成功！", request);
		}
		List<SystemCode> scList = this.systemCodeService.findAll();
		request.setAttribute("scList", scList);
		return loadPage(mapping, form, request, response);
	}

	/**
	 * 
	 * 方法说明：找到所有的数据
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
	 */
	public ActionForward loadPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String perPageRecord = request.getParameter("perPageRecord");
		String currPageNum = request.getParameter("currPageNum");
		if (StringUtils.isNotEmpty(perPageRecord) && StringUtils.isNotEmpty(currPageNum)) {
			Page<SystemCode> page = new Page<SystemCode>(Integer.valueOf(perPageRecord),
					Integer.valueOf(currPageNum));
			page = this.systemCodeService.loadPage(page);
			if (page.getData().size() == 0) {
				page = new Page<SystemCode>(Integer.valueOf(perPageRecord), 1);
				page = this.systemCodeService.loadPage(page);
			}
			request.setAttribute("page", page);
		} else {
			Page<SystemCode> page = new Page<SystemCode>(20, 1);
			page = this.systemCodeService.loadPage(page);
			request.setAttribute("page", page);
		}
		return mapping.findForward("systemcodelist1");
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            orm
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward
	 */
	public ActionForward loadSystemCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.getParameter("id");

		return loadPage(mapping, form, request, response);
	}

}
