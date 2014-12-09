package com.wt.hea.webbuilder.struts.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.model.PopWindow;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-20
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PopWindowAction extends BaseAction
{

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
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("topType", POPWINDOW);
		map.put("suspenStatus", "01");

		List<BaseInfo> bis = this.baseInfoService.findByProperty(map);
		if (bis != null && !bis.isEmpty()) {
			for (BaseInfo bi : bis) {
				PopWindow pw = this.popWindowService.findByTopId(bi.getTopId());
				bi.setPopWindow(pw);
			}
		}
		request.setAttribute("siteUuid", siteUuid);
		request.setAttribute("bis", bis);
		return mapping.findForward("poplists");
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
	public ActionForward deletePopWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String topId = request.getParameter("topId");
		String siteId = request.getParameter("siteId");
		PopWindow pw = this.popWindowService.findByTopId(topId);
		this.popWindowService.deleteById(pw.getPopId());
		BaseInfo bi = this.baseInfoService.findById(topId);
		String topType = bi.getTopType();
		this.baseInfoService.delete(bi);
		this.generatePopWindow(siteId, topType, request);
		return mapping.findForward("poplists");
	}
}
