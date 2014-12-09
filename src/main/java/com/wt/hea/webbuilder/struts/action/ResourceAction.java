package com.wt.hea.webbuilder.struts.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.model.ResourceSite;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 资源操作控制器
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ResourceAction extends BaseAction
{

	/**
	 * 获取日志文件实例
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

	/**
	 * 资源侧帘,log,baner资源删除统一Acton
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
	public ActionForward deleteResource(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		request.setAttribute("siteUuid", siteUuid);
		User user = WebUtil.getSessionUser(request);
		String resId = request.getParameter("resId");
		ResourceSite resource = this.resourceService.findById(resId);

		// 删除用户所选择的资源

		boolean isDeleted = false;
		if (resource != null) {
			String resPath = this.rootPath + resource.getResPath();
			File file = new File(resPath);
			if (file.exists()) {
				boolean flag = file.delete();
				if (flag) {
					log.info("delete files successfully");
				}
			}
			this.resourceService.delete(resource);
			isDeleted = true;
		}

		// 恢复jsp视图
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("siteId", siteUuid);
		map2.put("resType", (resource == null ? "" : resource.getResType()));

		map2.put("resStatus", "01");
		List<ResourceSite> list2 = this.resourceService.findByProperty(map2);
		request.setAttribute("resourceList", list2);

		request.setAttribute("siteUuid", siteUuid);
		if (resource != null) {
			if (LEFTSIDESHADE.equals(resource.getResType())) {
				if (isDeleted) {
					request.setAttribute("message", "删除左侧帘资源成功!");
					String logMessage = "用户" + user.getName() + "删除左侧帘资源";

					log.saveLog(logMessage, request);
					log.info(logMessage, this.clazz);
				}
				return mapping.findForward("site_leftSideShadeUpload");
			} else if (RIGHTSIDESHADE.equals(resource.getResType())) {
				if (isDeleted) {
					request.setAttribute("message", "删除右侧帘资源成功!");
					String logMessage = "用户" + user.getName() + "删除右侧帘资源";

					log.saveLog(logMessage, request);
					log.info(logMessage, this.clazz);
				}
				return mapping.findForward("site_rightSideShadeUpload");
			} else if (FLOATWINDOW.equals(resource.getResType())) {
				if (isDeleted) {
					request.setAttribute("message", "删除飘窗资源成功!");
					String logMessage = "用户" + user.getName() + "删除飘窗资源";

					log.saveLog(logMessage, request);
					log.info(logMessage, this.clazz);
				}
				return mapping.findForward("site_floatWinUpload");
			} else if (LOGO.equals(resource.getResType())) {
				if (isDeleted) {
					request.setAttribute("message", "删除Logo资源成功!");
					String logMessage = "用户" + user.getName() + "删除Logo资源";

					log.saveLog(logMessage, request);
					log.info(logMessage, this.clazz);
				}
				return mapping.findForward("site_logoUpload");
			} else if (BANNER.equals(resource.getResType())) {
				if (isDeleted) {
					request.setAttribute("message", "删除banner资源成功!");
					String logMessage = "用户" + user.getName() + "删除banner资源";

					log.saveLog(logMessage, request);
					log.info(logMessage, this.clazz);
				}
				return mapping.findForward("site_bannerUpload");
			} else if (POPWINDOW.equals(resource.getResType())) {
				if (isDeleted) {
					request.setAttribute("message", "删除弹出窗口资源成功!");
					String logMessage = "用户" + user.getName() + "删除弹出窗口资源";

					log.saveLog(logMessage, request);
					log.info(logMessage, this.clazz);
				}
				return mapping.findForward("site_popWindowUpload");
			}
		}
		return null;
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
	public ActionForward resourceIsUsed(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String resId = request.getParameter("resId");

		List<BaseInfo> biList = this.baseInfoService.findByProperty("resId", resId);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (biList != null && biList.size() > 0) {
			out.print("1");
		} else {
			out.print("0");
		}
		return null;
	}
}
