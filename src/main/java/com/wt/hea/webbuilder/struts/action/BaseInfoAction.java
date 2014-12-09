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
import com.wt.hea.common.util.FileUtil;
import com.wt.hea.common.util.UploadUtil;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.model.PopWindow;
import com.wt.hea.webbuilder.model.ResourceSite;
import com.wt.hea.webbuilder.model.SiteManage;
import com.wt.hea.webbuilder.struts.form.BaseInfoForm;
import com.hirisun.hea.api.domain.User;

/***
 * "首页资源基本信息"页面业务流转action 封装资源上传等功能
 * 
 * @author 袁明敏
 * 
 */
public class BaseInfoAction extends BaseAction
{
	/**
	 * 获取日志文件实例
	 */
	private final Logger log = LoggerService.getInstance();

	/**
	 * 首页弹出窗口设置初使化
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
	public ActionForward setIndexPopWinInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		String topId = request.getParameter("topId");
		User user = WebUtil.getSessionUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("resType", POPWINDOW);
		List<ResourceSite> list = this.resourceService.findByProperty(map);

		BaseInfo bi = null;
		PopWindow pw = null;
		if (topId != null && !"".equals(topId)) {
			bi = this.baseInfoService.findById(topId);
			pw = this.popWindowService.findByTopId(topId);
			bi.setPopWindow(pw);
		}

		String logMessage = "用户" + user.getName() + "访问设置弹出窗口";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);

		request.setAttribute("resourceList", list);
		request.setAttribute("siteUuid", siteUuid);
		request.setAttribute("bi", bi);
		return mapping.findForward("site_popWinSetting");
	}

	/**
	 * 首页飘窗设置
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
	public ActionForward setIndexPopWin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		BaseInfoForm bif = (BaseInfoForm) form;
		PopWindow pw = bif.getPopWindow();
		String suspenUrl = request.getParameter("suspenUrl"); // 飘窗的url地址，手工输入
		String suspenAddr = request.getParameter("suspenAddr"); // 坐标xy值
		String topId = request.getParameter("topId");
		String siteUuid = request.getParameter("siteUuid");
		String resId = request.getParameter("resId");
		String resPath = request.getParameter("resPath");
		String suspenStatus = "01";
		User user = WebUtil.getSessionUser(request);

		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		ResourceSite resource = new ResourceSite();
		if (resId != null && !"".equals(resId)) {
			resource = this.resourceService.findById(resId);
		}

		BaseInfo baseInfo = new BaseInfo();
		PopWindow popWindow = new PopWindow();
		if (topId != null && !topId.equals("")) {
			baseInfo = this.baseInfoService.findById(topId);
			popWindow = this.popWindowService.findByTopId(topId);
		}
		baseInfo.setResCode(resource.getResCode());
		baseInfo.setSiteId(siteUuid);
		baseInfo.setSiteManage(siteManage);
		baseInfo.setSiteName(siteManage.getSiteName());
		baseInfo.setSiteNo(siteUuid);
		baseInfo.setSuspenAddr(suspenAddr);
		baseInfo.setSuspenPath(resource.getResPath());
		baseInfo.setSuspenStatus(suspenStatus);
		baseInfo.setSuspenUrl((suspenUrl == null ? "" : suspenUrl));
		baseInfo.setTopType(POPWINDOW);
		baseInfo.setResId(resId);
		baseInfo.setHeight(resource.getResHeight());
		baseInfo.setWidth(resource.getResWidth());
		popWindow.setEndTime(pw.getEndTime());
		popWindow.setPopTitle(pw.getPopTitle());
		popWindow.setSiteId(siteUuid);
		popWindow.setStartTime(pw.getStartTime());
		popWindow.setWidth(pw.getWidth());
		popWindow.setHeight(pw.getHeight());
		if (topId != null && !topId.equals("")) {
			this.baseInfoService.update(baseInfo);
			this.popWindowService.update(popWindow);
		} else {
			this.baseInfoService.save(baseInfo);
			popWindow.setTopId(baseInfo.getTopId());
			this.popWindowService.save(popWindow);
		}

		// modifyby_xiaoqi
		this.generatePopWindow(siteUuid, POPWINDOW, request);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("siteId", siteUuid);
		map2.put("resType", POPWINDOW);
		List<ResourceSite> list2 = this.resourceService.findByProperty(map2);
		request.setAttribute("resourceList", list2);
		request.setAttribute("bi", baseInfo);
		request.setAttribute("siteUuid", siteUuid);
		request.setAttribute("resId", resId);
		request.setAttribute("resPath", resPath);

		request.setAttribute("message", "设置弹出窗口成功!");
		String logMessage = "用户" + user.getName() + "绑定弹出窗口资源到" + siteManage.getSiteName();
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_popWinSetting");
	}

	/**
	 * 首页飘窗设置初使化
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            response
	 * @param response
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 *             Exception
	 */
	public ActionForward setIndexFloatWinInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		User user = WebUtil.getSessionUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("resType", FLOATWINDOW);
		List<ResourceSite> list = this.resourceService.findByProperty(map);

		String logMessage = "用户" + user.getName() + "访问设置飘窗";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);

		request.setAttribute("resourceList", list);
		request.setAttribute("siteUuid", siteUuid);
		return mapping.findForward("site_floatWinSetting");
	}

	/**
	 * 首页飘窗设置
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
	public ActionForward setIndexFloatWin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String suspenUrl = request.getParameter("suspenUrl"); // 飘窗的url地址，手工输入

		String suspenAddr = request.getParameter("suspenAddr"); // 左上角00;右上角01;左下角03;右下角04;居中:05
		// ;飘浮06;
		String suspenAddrValues = request.getParameter("suspenAddrValues");
		String suspenStatus = request.getParameter("suspenStatus");
		String topId = request.getParameter("topId");
		User user = WebUtil.getSessionUser(request);
		if (suspenStatus == null) {
			suspenStatus = LEFTSIDESHADE;
		} else {
			suspenStatus = "01";
		}

		String siteUuid = request.getParameter("siteUuid");
		request.setAttribute("siteUuid", siteUuid);
		SiteManage siteManage = this.siteManageService.findById(siteUuid);

		String resId = request.getParameter("resId");
		request.setAttribute("resId", resId);
		ResourceSite resource = this.resourceService.findById(resId);

		String resPath = request.getParameter("resPath");
		request.setAttribute("resPath", resPath);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("topType", resource.getResType());
		BaseInfo baseInfo = new BaseInfo();
		if (topId != null && !topId.equals("")) {
			baseInfo = this.baseInfoService.findById(topId);
		}
		baseInfo.setResCode(resource.getResCode());
		baseInfo.setSiteId(siteUuid);
		baseInfo.setSiteManage(siteManage);
		baseInfo.setSiteName(siteManage.getSiteName());
		baseInfo.setSiteNo(siteUuid);
		baseInfo.setSuspenAddr(suspenAddr + ":" + suspenAddrValues);
		baseInfo.setSuspenPath(resource.getResPath());
		baseInfo.setSuspenStatus(suspenStatus);
		baseInfo.setSuspenUrl((suspenUrl == null ? "" : suspenUrl));
		baseInfo.setTopType(resource.getResType());
		baseInfo.setResId(resId);
		baseInfo.setHeight(resource.getResHeight());
		baseInfo.setWidth(resource.getResWidth());
		if (topId != null && !topId.equals("")) {
			this.baseInfoService.update(baseInfo);
		} else {
			this.baseInfoService.save(baseInfo);
		}

		// modifyby_xiaoqi
		this.generateFloatWindow(siteUuid, FLOATWINDOW);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("siteId", siteUuid);
		map2.put("resType", FLOATWINDOW);
		List<ResourceSite> list2 = this.resourceService.findByProperty(map2);
		request.setAttribute("resourceList", list2);
		request.setAttribute("baseInfo", baseInfo);

		request.setAttribute("message", "设置飘窗成功!");
		String logMessage = "用户" + user.getName() + "绑定飘窗资源到" + siteManage.getSiteName();
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_floatWinSetting");
	}

	/**
	 * 生成飘窗的静态页面 注意处理固定和漂浮的两种类型
	 * 
	 * @param siteId
	 *            siteId
	 * @param topType
	 *            topType
	 */
	private void generateFloatWindow(String siteId, String topType)
	{
		// 生成静态页面
		String floatWindow = "";
		String script = "<script type=\"text/javascript\" src=\"/hea/heaconsole/script/webbuilder/remove.js\"></script>"
				+ "<script type=\"text/javascript\" src=\"/hea/heaconsole/script/webbuilder/YlFloat.js\"></script>";
		String context = "";
		String runScript = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("topType", topType);
		map.put("siteId", siteId);
		map.put("suspenStatus", "01");
		List<BaseInfo> biList = this.baseInfoService.findByProperty(map);
		String filePath = this.rootPath + this.siteManageService.findById(siteId).getSiteAddr()
				+ "res_04_" + siteId + ".html";
		if (biList != null && biList.size() > 0) {
			for (BaseInfo bi : biList) {
				context = context
						+ "<div id=\"floatwin"
						+ bi.getTopId()
						+ "\" style=\"position:absolute;z-index:999999\"> "
						+ "<div align=\"right\">"
						+ "<span style=\"width: 40px;position:relative;top:20px;right:1px;cursor:pointer\" onclick=\"closeWin1('floatwin"
						+ bi.getTopId() + "')\">关闭</span> 	" + "</div>" + "	<a href=\""
						+ bi.getSuspenUrl() + "\" target=\"_blank\"> " + "		<img style=\"height:"
						+ bi.getHeight() + "px;width:" + bi.getWidth() + "px\" src=\"/"
						+ bi.getSuspenPath() + "\" border=\"0\">" + "	</a> " + "</div> ";
				String[] p = bi.getSuspenAddr().split(":");
				String x = p[1];
				String y = p[2];
				if (bi.getSuspenAddr() != null && LOGO.equals(p[0])) {// 固定方式
					runScript = runScript + "var topValue = "
							+ ("0".equals(y) ? "0" : "document.body.clientHeight") + ";"
							+ "var positionValue = " + ("0".equals(x) ? "\"left\"" : "\"right\"")
							+ ";" + "$(\"#floatwin" + bi.getTopId() + "\").jFloat({"
							+ "position:positionValue," + "top:topValue," + "height:"
							+ bi.getHeight() + "," + "width:" + bi.getWidth() + ","
							+ ("0".equals(x) ? "left" : "right") + ":20" + "});";

				} else {// 悬浮方式
					runScript = runScript + "var fw" + bi.getTopId() + " = new AdMove(\"floatwin"
							+ bi.getTopId() + "\"," + ("0".equals(x) ? 0 : -1) + ","
							+ ("0".equals(y) ? 0 : -1) + ");" + "fw" + bi.getTopId() + ".Run();";
				}
			}
			floatWindow = script + context + "<script>" + runScript + "</script>";
			FileUtil.fileWrite(filePath, floatWindow);
		} else {
			File file = new File(filePath);
			if (file.exists()) {
				boolean flag = file.delete();
				if (flag) {

				}
			}
		}
	}

	/**
	 * 侧帘资源上传
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
	public ActionForward uploadSideShadeInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteUuid = request.getParameter("siteUuid");
		request.setAttribute("siteUuid", siteUuid);
		String type = request.getParameter("resType");
		if (LEFTSIDESHADE.equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resStatus", "01");
			map.put("siteId", siteUuid);
			map.put("resType", LEFTSIDESHADE);
			List<ResourceSite> list = this.resourceService.findByProperty(map);
			request.setAttribute("resourceList", list);
			String logMessage = "用户" + user.getName() + "浏览了一个资源类型为02的资源";
			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
			return mapping.findForward("site_leftSideShadeUpload");
		} else if (RIGHTSIDESHADE.equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resStatus", "01");
			map.put("siteId", siteUuid);
			map.put("resType", RIGHTSIDESHADE);
			List<ResourceSite> list = this.resourceService.findByProperty(map);
			request.setAttribute("resourceList", list);
			String logMessage = "用户" + user.getName() + "上传了一个资源类型为03的资源";
			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
			return mapping.findForward("site_rightSideShadeUpload");
		} else if (FLOATWINDOW.equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resStatus", "01");
			map.put("siteId", siteUuid);
			map.put("resType", FLOATWINDOW);
			List<ResourceSite> list = this.resourceService.findByProperty(map);
			request.setAttribute("resourceList", list);
			String logMessage = "用户" + user.getName() + "浏览了一个资源类型为04的资源";
			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
			return mapping.findForward("site_floatWinUpload");
		} else if (LOGO.equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resStatus", "01");
			map.put("siteId", siteUuid);
			map.put("resType", LOGO);
			List<ResourceSite> list = this.resourceService.findByProperty(map);
			request.setAttribute("resourceList", list);
			String logMessage = "用户" + user.getName() + "浏览了一个资源类型为05的资源";
			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
			return mapping.findForward("site_logoUpload");
		} else if (BANNER.equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resStatus", "01");
			map.put("siteId", siteUuid);
			map.put("resType", BANNER);
			List<ResourceSite> list = this.resourceService.findByProperty(map);
			request.setAttribute("resourceList", list);
			String logMessage = "用户" + user.getName() + "浏览了一个资源类型为07的资源";
			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
			return mapping.findForward("site_bannerUpload");
		} else if (POPWINDOW.equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resStatus", "01");
			map.put("siteId", siteUuid);
			map.put("resType", POPWINDOW);
			List<ResourceSite> list = this.resourceService.findByProperty(map);
			request.setAttribute("resourceList", list);
			String logMessage = "用户" + user.getName() + "浏览了一个资源类型为08的资源";
			log.saveLog(logMessage, request);
			log.info(logMessage, this.clazz);
			return mapping.findForward("site_popWinUpload");
		}

		return null;
	}

	/**
	 * 侧帘,log,baner资源上传统一Acton
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
	public ActionForward uploadSideShade(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);// )
		String appId = request.getParameter("appId");
		try {
			BaseInfoForm baseForm = (BaseInfoForm) form;
			byte[] fileContent = baseForm.getFile().getFileData();
			String height = request.getParameter("resHeight");
			String width = request.getParameter("resWidth");

			String siteUuid = request.getParameter("siteUuid");
			SiteManage siteManage = this.siteManageService.findById(siteUuid);
			String publishFolde = siteManage.getSiteAddr();

			// 跟据不同的资源类型生成约定的文件名,约定格式为 : "res"+类型编号+日期+原文件名后缀,(注:02代表 左侧帘 03 右侧帘 04 飘窗 05 Logo)
			String fileType = request.getParameter("resCode");
			String type = request.getParameter("resType");
			String path = "res";
			String dateStr = System.currentTimeMillis() + "";
			String fileName = baseForm.getFile().getFileName();
			String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			path = publishFolde + File.separator + path;
			// String relativePath=path+fileName;
			path = this.rootPath + path;
			if (LEFTSIDESHADE.equals(type)) {
				path = path + "_" + LEFTSIDESHADE + "_" + dateStr + suffix;
				UploadUtil.upload(fileContent, path);

				request.setAttribute("siteUuid", siteUuid);
				request.setAttribute("message", baseForm.getFile().getFileName() + "  文件上传成功!");

				/***** 资源添加到资源表 *****/
				ResourceSite resource = new ResourceSite();
				resource.setFileSize(Long.valueOf(fileContent.length));
				resource.setResCode(fileType);
				File tempFile = new File(path);
				resource.setResName(tempFile.getName());
				resource.setResPath(publishFolde + File.separator + tempFile.getName());
				resource.setResStatus("01");
				resource.setResType(LEFTSIDESHADE);
				resource.setSiteId(siteUuid);
				resource.setAppId(appId);
				resource.setSiteName(siteManage.getSiteName());
				resource.setResHeight(Integer.parseInt(height));
				resource.setResWidth(Integer.parseInt(width));

				this.resourceService.save(resource);

				/***** 上传完毕获取img路径 ******/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("resStatus", "01");
				map.put("siteId", siteUuid);
				map.put("resType", LEFTSIDESHADE);
				List<ResourceSite> list = this.resourceService.findByProperty(map);
				request.setAttribute("resourceList", list);
				String logMessage = "用户" + user.getName() + "上传了一个资源类型为02的资源";
				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
				return mapping.findForward("site_leftSideShadeUpload");
			} else if (RIGHTSIDESHADE.equals(type)) {
				path = path + "_" + RIGHTSIDESHADE + "_" + dateStr + suffix;
				UploadUtil.upload(fileContent, path);

				request.setAttribute("siteUuid", siteUuid);
				request.setAttribute("message", baseForm.getFile().getFileName() + "  文件上传成功!");

				/***** 资源添加到资源表 *****/
				ResourceSite resource = new ResourceSite();
				resource.setFileSize(Long.valueOf(fileContent.length));
				resource.setResCode(fileType);
				File tempFile = new File(path);
				resource.setResName(tempFile.getName());
				resource.setResPath(publishFolde + File.separator + tempFile.getName());
				resource.setResHeight(Integer.parseInt(height));
				resource.setResWidth(Integer.parseInt(width));
				resource.setAppId(appId);
				resource.setResStatus("01");
				resource.setResType(RIGHTSIDESHADE);
				resource.setSiteId(siteUuid);
				resource.setSiteName(siteManage.getSiteName());
				this.resourceService.save(resource);

				/***** 上传完毕获取img路径 ******/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("resStatus", "01");
				map.put("siteId", siteUuid);
				map.put("resType", RIGHTSIDESHADE);
				List<ResourceSite> list = this.resourceService.findByProperty(map);
				request.setAttribute("resourceList", list);
				String logMessage = "用户" + user.getName() + "上传了一个资源类型为03的资源";
				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
				return mapping.findForward("site_rightSideShadeUpload");
			} else if (FLOATWINDOW.equals(type)) {
				path = path + "_" + FLOATWINDOW + "_" + dateStr + suffix;
				UploadUtil.upload(fileContent, path);

				request.setAttribute("siteUuid", siteUuid);
				request.setAttribute("message", baseForm.getFile().getFileName() + "  文件上传成功!");

				/***** 资源添加到资源表 *****/
				ResourceSite resource = new ResourceSite();
				resource.setFileSize(Long.valueOf(fileContent.length));
				resource.setResCode(fileType);
				resource.setAppId(appId);
				File tempFile = new File(path);
				resource.setResName(tempFile.getName());
				resource.setResPath(publishFolde + File.separator + tempFile.getName());
				resource.setResHeight(Integer.parseInt(height));
				resource.setResWidth(Integer.parseInt(width));
				resource.setResStatus("01");
				resource.setResType(FLOATWINDOW);
				resource.setSiteId(siteUuid);
				resource.setSiteName(siteManage.getSiteName());
				this.resourceService.save(resource);

				/***** 上传完毕获取img路径 ******/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("resStatus", "01");
				map.put("siteId", siteUuid);
				map.put("resType", FLOATWINDOW);
				List<ResourceSite> list = this.resourceService.findByProperty(map);
				request.setAttribute("resourceList", list);
				String logMessage = "用户" + user.getName() + "上传了一个资源类型为04的资源";
				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
				return mapping.findForward("site_floatWinUpload");
			} else if (LOGO.equals(type)) {
				path = path + "_" + LOGO + "_" + dateStr + suffix;
				UploadUtil.upload(fileContent, path);

				request.setAttribute("siteUuid", siteUuid);
				request.setAttribute("message", baseForm.getFile().getFileName() + "  文件上传成功!");

				/***** 资源添加到资源表 *****/
				ResourceSite resource = new ResourceSite();
				resource.setFileSize(Long.valueOf(fileContent.length));
				resource.setResCode(fileType); // 图片01.flash 02
				resource.setAppId(appId);
				File tempFile = new File(path);
				resource.setResName(tempFile.getName());
				resource.setResPath(publishFolde + File.separator + tempFile.getName());
				resource.setResHeight(Integer.parseInt(height));
				resource.setResWidth(Integer.parseInt(width));

				resource.setResStatus("01");
				resource.setResType(LOGO);
				resource.setSiteId(siteUuid);
				resource.setSiteName(siteManage.getSiteName());
				this.resourceService.save(resource);

				/***** 上传完毕获取img路径 ******/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("resStatus", "01");
				map.put("siteId", siteUuid);
				map.put("resType", LOGO);
				List<ResourceSite> list = this.resourceService.findByProperty(map);
				request.setAttribute("resourceList", list);
				String logMessage = "用户" + user.getName() + "上传了一个资源类型为05的资源";
				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
				return mapping.findForward("site_logoUpload");
			} else if (BANNER.equals(type)) {
				path = path + "_" + BANNER + "_" + dateStr + suffix;
				UploadUtil.upload(fileContent, path);

				request.setAttribute("siteUuid", siteUuid);
				request.setAttribute("message", baseForm.getFile().getFileName() + "  文件上传成功!");

				/***** 资源添加到资源表 *****/
				ResourceSite resource = new ResourceSite();
				resource.setFileSize(Long.valueOf(fileContent.length));
				resource.setResCode(fileType);// 图片01.flash 02
				resource.setAppId(appId);
				resource.setResName(new File(path).getName());
				File tempFile = new File(path);
				resource.setResName(tempFile.getName());
				resource.setResPath(publishFolde + File.separator + tempFile.getName());
				resource.setResHeight(Integer.parseInt(height));
				resource.setResWidth(Integer.parseInt(width));

				resource.setResStatus("01");
				resource.setResType(BANNER);
				resource.setSiteId(siteUuid);
				resource.setSiteName(siteManage.getSiteName());
				this.resourceService.save(resource);

				/***** 上传完毕获取img路径 ******/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("resStatus", "01");
				map.put("siteId", siteUuid);
				map.put("resType", BANNER);
				List<ResourceSite> list = this.resourceService.findByProperty(map);
				request.setAttribute("resourceList", list);
				String logMessage = "用户" + user.getName() + "上传了一个资源类型为07的资源";
				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
				return mapping.findForward("site_bannerUpload");
			} else if (POPWINDOW.equals(type)) {
				path = path + "_" + POPWINDOW + "_" + dateStr + suffix;
				UploadUtil.upload(fileContent, path);

				request.setAttribute("siteUuid", siteUuid);
				request.setAttribute("message", baseForm.getFile().getFileName() + "  文件上传成功!");

				/***** 资源添加到资源表 *****/
				ResourceSite resource = new ResourceSite();
				resource.setFileSize(Long.valueOf(fileContent.length));
				resource.setResCode(fileType);// 图片01.flash 02
				resource.setAppId(appId);
				resource.setResName(new File(path).getName());
				File tempFile = new File(path);
				resource.setResName(tempFile.getName());
				resource.setResPath(publishFolde + File.separator + tempFile.getName());
				resource.setResHeight(Integer.parseInt(height));
				resource.setResWidth(Integer.parseInt(width));

				resource.setResStatus("01");
				resource.setResType(POPWINDOW);
				resource.setSiteId(siteUuid);
				resource.setSiteName(siteManage.getSiteName());
				this.resourceService.save(resource);

				/***** 上传完毕获取img路径 ******/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("resStatus", "01");
				map.put("siteId", siteUuid);
				map.put("resType", POPWINDOW);
				List<ResourceSite> list = this.resourceService.findByProperty(map);
				request.setAttribute("resourceList", list);
				String logMessage = "用户" + user.getName() + "上传了一个资源类型为" + POPWINDOW + "的资源";
				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
				return mapping.findForward("site_popWindowUpload");
			}

			return mapping.findForward("message");
		} catch (Exception e) {
			log.info("文件上传失败", this.clazz);
			request.setAttribute("message", "文件上传失败!<br>" + e.getMessage());
			return mapping.findForward("message");
		}

	}

	/**
	 * 首页基本信息版权设置初使化
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
	public ActionForward setIndexCopyrightInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		request.setAttribute("copyrightContent", siteManage.getCopyrightContent());
		request.setAttribute("siteUuid", siteUuid);
		String logMessage = "用户" + user.getName() + "查看设置版权信息";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_copyrightSetting");
	}

	/**
	 * 首页基本信息版权设置
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
	public ActionForward setIndexCopyright(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteUuid = request.getParameter("siteUuid");
		String copyrightContent = request.getParameter("copyrightContent");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);

		siteManage.setCopyrightContent(copyrightContent);
		this.siteManageService.update(siteManage);

		request.setAttribute("message", "版权信息设置成功!");
		request.setAttribute("copyrightContent", copyrightContent);
		request.setAttribute("siteUuid", siteUuid);
		String logMessage = "用户" + user.getName() + "设置版权信息";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_copyrightSetting");
	}

	/**
	 * 首页基本信息Logo设置初使化
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
	public ActionForward setIndexLogoInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		User user = WebUtil.getSessionUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("resType", LOGO);
		List<ResourceSite> list = this.resourceService.findByProperty(map);
		request.setAttribute("resourceList", list);
		request.setAttribute("siteUuid", siteUuid);
		String logMessage = "用户" + user.getName() + "设置版权信息";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_logoSetting");
	}

	/**
	 * 首页基本信息Logo设置
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
	public ActionForward setIndexLogo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteUuid = request.getParameter("siteUuid");
		String resId = request.getParameter("resId");
		ResourceSite resource = this.resourceService.findById(resId);

		String resPath = request.getParameter("resPath");
		request.setAttribute("resPath", resPath);
		SiteManage siteManage = this.siteManageService.findById(siteUuid);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("topType", resource.getResType());
		List<BaseInfo> list = this.baseInfoService.findByProperty(map);
		if (list.size() == 1) {
			this.baseInfoService.delete(list.get(0));
		}

		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setResCode(resource.getResCode());
		baseInfo.setSiteId(siteUuid);
		baseInfo.setSiteManage(siteManage);
		baseInfo.setSiteName(siteManage.getSiteName());
		baseInfo.setSiteNo(siteUuid);
		baseInfo.setSuspenStatus(resource.getResStatus());
		baseInfo.setTopType(resource.getResType());
		baseInfo.setSiteManage(siteManage);
		baseInfo.setSuspenPath(resource.getResPath());
		baseInfo.setSuspenAddr(resource.getResPath());
		baseInfo.setSuspenUrl(resource.getResPath());
		baseInfo.setResId(resId);
		baseInfo.setHeight(resource.getResHeight());
		baseInfo.setWidth(resource.getResWidth());
		this.baseInfoService.save(baseInfo);

		siteManage.getBaseInfos().add(baseInfo);
		siteManage.setLogoPath(resPath);
		this.siteManageService.update(siteManage);

		// 获取当前站点下的logo资源，用于显示在走马灯里
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("siteId", siteUuid);
		map2.put("resType", LOGO);
		List<ResourceSite> list2 = this.resourceService.findByProperty(map2);
		request.setAttribute("resourceList", list2);

		request.setAttribute("siteUuid", siteUuid);
		request.setAttribute("message", "设置log成功!");
		String logMessage = "用户" + user.getName() + "设置logo资源";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_logoSetting");
	}

	/**
	 * 首页banner设置初使化
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
	public ActionForward setIndexBannerInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		User user = WebUtil.getSessionUser(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("resType", BANNER);
		List<ResourceSite> list = this.resourceService.findByProperty(map);
		request.setAttribute("resourceList", list);
		request.setAttribute("siteUuid", siteUuid);
		String logMessage = "用户" + user.getName() + "浏览banner资源";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_bannerSetting");
	}

	/**
	 * 首页基本信息Logo设置
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
	public ActionForward setIndexBanner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);

		String resId = request.getParameter("resId");
		ResourceSite resource = this.resourceService.findById(resId);

		String resPath = request.getParameter("resPath");
		request.setAttribute("resPath", resPath);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("topType", resource.getResType());
		List<BaseInfo> list = this.baseInfoService.findByProperty(map);
		if (list.size() == 1) {
			this.baseInfoService.delete(list.get(0));
		}
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setResCode(resource.getResCode());
		baseInfo.setSiteId(siteUuid);
		baseInfo.setSiteManage(siteManage);
		baseInfo.setSiteName(siteManage.getSiteName());
		baseInfo.setSiteNo(siteUuid);
		baseInfo.setSuspenAddr(resource.getResPath());
		baseInfo.setSuspenPath(resource.getResPath());
		baseInfo.setSuspenStatus(resource.getResStatus());
		baseInfo.setSuspenUrl(resource.getResPath());
		baseInfo.setTopType(resource.getResType());
		baseInfo.setResId(resId);
		baseInfo.setHeight(resource.getResHeight());
		baseInfo.setWidth(resource.getResWidth());
		this.baseInfoService.save(baseInfo);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("siteId", siteUuid);
		map2.put("resType", BANNER);
		List<ResourceSite> list2 = this.resourceService.findByProperty(map2);
		request.setAttribute("resourceList", list2);

		request.setAttribute("siteUuid", siteUuid);
		request.setAttribute("message", "设置Banner成功!");
		String logMessage = "用户" + user.getName() + "浏览banner资源";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_bannerSetting");
	}

	/**
	 * 侧帘资源设置初使化
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
	public ActionForward setSiderShadeInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteUuid = request.getParameter("siteUuid");
		String resType = request.getParameter("resType");
		request.setAttribute("resType", resType);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("resType", resType);
		List<ResourceSite> list = this.resourceService.findByProperty(map);
		request.setAttribute("resourceList", list);
		request.setAttribute("siteUuid", siteUuid);

		String logMessage = "用户" + user.getName() + "设置banner资源";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		if (LEFTSIDESHADE.equals(resType)) {
			return mapping.findForward("site_leftSideShadeSetting");
		} else if (LEFTSIDESHADE.equals(resType)) {
			return mapping.findForward("site_rightSideShadeSetting");
		}
		return null;
	}

	/**
	 * 侧帘资源设置
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
	public ActionForward setSiderShade(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteUuid = request.getParameter("siteUuid");
		SiteManage siteManage = this.siteManageService.findById(siteUuid);
		String suspernUrl = request.getParameter("suspernUrl");
		String resId = request.getParameter("resId");
		String suspenAddr = request.getParameter("suspenAddr");
		String topId = request.getParameter("topId");

		ResourceSite resource = this.resourceService.findById(resId);

		request.setAttribute("suspernUrl", suspernUrl);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("topType", resource.getResType());
		map.put("suspenAddr", suspenAddr);
		BaseInfo baseInfo = new BaseInfo();
		if (topId != null && topId.length() > 0) {
			baseInfo = this.baseInfoService.findById(topId);
		}
		baseInfo.setResCode(resource.getResCode());
		baseInfo.setSiteId(siteUuid);
		baseInfo.setSiteManage(siteManage);
		baseInfo.setSiteName(siteManage.getSiteName());
		baseInfo.setSiteNo(siteUuid);
		baseInfo.setSuspenAddr(suspenAddr);
		baseInfo.setSuspenPath(resource.getResPath());
		baseInfo.setSuspenStatus(resource.getResStatus());
		baseInfo.setSuspenUrl(suspernUrl);
		baseInfo.setTopType(resource.getResType());
		baseInfo.setResId(resId);
		baseInfo.setHeight(resource.getResHeight());
		baseInfo.setWidth(resource.getResWidth());
		if (topId != null && topId.length() > 0) {
			this.baseInfoService.update(baseInfo);
		} else {
			this.baseInfoService.save(baseInfo);
		}
		// modifyby_xiaoqi
		this.generateCurtain(siteUuid, LEFTSIDESHADE);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("siteId", siteUuid);
		map2.put("resType", resource.getResType());
		List<ResourceSite> list2 = this.resourceService.findByProperty(map2);
		request.setAttribute("resourceList", list2);

		request.setAttribute("siteUuid", siteUuid);
		if (LEFTSIDESHADE.equals(resource.getResType())) {
			request.setAttribute("message", "设置左侧帘成功!");
			return mapping.findForward("site_leftSideShadeSetting");
		} else if (RIGHTSIDESHADE.equals(resource.getResType())) {
			request.setAttribute("message", "设置右侧帘成功!");
			return mapping.findForward("site_rightSideShadeSetting");
		}
		String logMessage = "用户" + user.getName() + "为" + siteManage.getSiteName() + "站点设置侧帘资源";
		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		request.setAttribute("message", "没有找到页面，请联系管理员!");
		return mapping.findForward("message");
	}

	/**
	 * 生成侧帘静态页面
	 * 
	 * @param siteId
	 *            siteId
	 * @param topType
	 *            topType
	 */
	private void generateCurtain(String siteId, String topType)
	{
		// 生成静态页面
		String floatWindow = "";
		String script = "<script type=\"text/javascript\" src=\"/hea/heaconsole/script/webbuilder/YlFloat.js\"></script>";
		String context = "";
		String runScript = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("topType", topType);
		map.put("siteId", siteId);
		// BaseInfoService bis = (BaseInfoService) SpringUtil.getBean("baseInfoService");
		// SiteManageService sms = (SiteManageService) SpringUtil.getBean("siteManageService");
		// List<BaseInfo> biList = bis.findByProperty(map);
		List<BaseInfo> biList = this.baseInfoService.findByProperty(map);
		String filePath = this.rootPath + this.siteManageService.findById(siteId).getSiteAddr()
				+ "res_0203_" + siteId + ".html";
		if (biList != null && biList.size() > 0) {
			for (BaseInfo bi : biList) {
				context = context + "<div id=\"floatwin" + bi.getTopId()
						+ "\" style=\"position:absolute;z-index:9999;\"> " + "	<a href=\""
						+ bi.getSuspenUrl() + "\" target=\"_blank\"> " + "		<img style=\"height:"
						+ bi.getHeight() + ";width:" + bi.getWidth() + "\" src=\"/"
						+ bi.getSuspenPath() + "\" border=\"0\">" + "	</a> " + "</div> ";
				String[] p = bi.getSuspenAddr().split(":");
				String x = p[1];
				String y = p[2];
				runScript = runScript + "var topValue = " + y + ";" + "var positionValue = "
						+ ("0".equals(x) ? "\"left\"" : "\"right\"") + ";" + "$(\"#floatwin"
						+ bi.getTopId() + "\").jFloat({" + "position:positionValue,"
						+ "top:topValue," + "height:" + bi.getHeight() + "," + "width:"
						+ bi.getWidth() + "," + ("0".equals(x) ? "left" : "right") + ":20" + "});";
			}

			floatWindow = script + context + "<script>" + runScript + "</script>";
			FileUtil.fileWrite(filePath, floatWindow);
		} else {
			File file = new File(filePath);
			if (file.exists()) {
				boolean flag = file.delete();
				if (flag) {

				}
			}
		}
	}

	/**
	 * 某站点下的侧帘,log,baner资源删除统一Acton
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
		User user = WebUtil.getSessionUser(request);
		String resId = request.getParameter("resId");
		ResourceSite resource = this.resourceService.findById(resId);

		// 删除用户所选择的资源
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("topType", resource.getResType());
		map.put("resId", resId);
		List<BaseInfo> list = this.baseInfoService.findByProperty(map);

		boolean isDeleted = false;
		if (list.size() == 1) {
			PopWindow pop = this.popWindowService.findByTopId(list.get(0).getTopId());
			this.popWindowService.deleteById(pop.getPopId());
			this.baseInfoService.delete(list.get(0));
			this.generateCurtain(siteUuid, resource.getResType());
			this.generateFloatWindow(siteUuid, resource.getResType());
			isDeleted = true;
		}

		// 恢复jsp视图
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("siteId", siteUuid);
		map2.put("resType", resource.getResType());
		List<ResourceSite> list2 = this.resourceService.findByProperty(map2);
		request.setAttribute("resourceList", list2);

		request.setAttribute("siteUuid", siteUuid);
		if (LEFTSIDESHADE.equals(resource.getResType())) {
			if (isDeleted) {
				request.setAttribute("message", "删除左侧帘成功!");
				String logMessage = "用户" + user.getName() + "删除站点左侧帘";

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			} else {
				request.setAttribute("message", "您还没有将此资源设置为侧帘,请设置侧帘!");
			}
			return mapping.findForward("site_leftSideShadeSetting");
		} else if (RIGHTSIDESHADE.equals(resource.getResType())) {
			if (isDeleted) {
				request.setAttribute("message", "删除右侧帘成功!");
				String logMessage = "用户" + user.getName() + "删除站点右侧帘";

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			} else {
				request.setAttribute("message", "您还没有设置右侧帘,请设置左侧帘!");
			}
			return mapping.findForward("site_rightSideShadeSetting");
		} else if (FLOATWINDOW.equals(resource.getResType())) {
			if (isDeleted) {
				request.setAttribute("message", "删除飘窗成功!");
				String logMessage = "用户" + user.getName() + "删除站点飘窗";

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			} else {
				request.setAttribute("message", "您还没有设置飘窗,请设置飘窗!");
			}
			return mapping.findForward("site_floatWinSetting");
		} else if (LOGO.equals(resource.getResType())) {
			if (isDeleted) {
				request.setAttribute("message", "删除Logo成功!");
				String logMessage = "用户" + user.getName() + "删除站点Logo";

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			} else {
				request.setAttribute("message", "您还没有设置Logo,请设置Logo!");
			}
			return mapping.findForward("site_logoSetting");
		} else if (BANNER.equals(resource.getResType())) {
			if (isDeleted) {
				request.setAttribute("message", "删除banner成功!");
				String logMessage = "用户" + user.getName() + "删除站点banner";

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			} else {
				request.setAttribute("message", "您还没有设置banner,请设置banner!");
			}
			return mapping.findForward("site_bannerSetting");
		} else if (POPWINDOW.equals(resource.getResType())) {
			if (isDeleted) {
				request.setAttribute("message", "删除弹出窗口资源成功!");
				String logMessage = "用户" + user.getName() + "删除站点弹出窗口成功";

				log.saveLog(logMessage, request);
				log.info(logMessage, this.clazz);
			} else {
				request.setAttribute("message", "您还没有设置弹出窗口资源,请设置弹出窗口资源!");
			}
			return mapping.findForward("site_popWinSetting");
		}

		return null;
	}

	/***
	 * 显示某个站点已经置过的侧帘资源、飘窗资源 、Logo 、Banner
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
	public ActionForward showSettedShadeInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String siteUuid = request.getParameter("siteUuid");
		request.setAttribute("siteUuid", siteUuid);
		User user = WebUtil.getSessionUser(request);
		String topType = request.getParameter("topType");
		request.setAttribute("topType", topType);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteUuid);
		map.put("suspenStatus", "01");
		map.put("topType", topType);
		List<BaseInfo> list = this.baseInfoService.findByProperty(map);
		request.setAttribute("baseInfos", list);
		String logMessage = "用户" + user.getName() + "浏览站点已经设置的资源";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("site_showSettedShade");
	}

	/**
	 * 查看资源设置的状态 biId + topType + suspenAddr + status + suspenUrl + resCode
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
	public ActionForward showSettedShadeValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String siteId = request.getParameter("siteId");
		String suspenPath = request.getParameter("suspenPath");
		String topType = request.getParameter("topType");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteId);
		map.put("suspenPath", suspenPath);
		map.put("topType", topType);

		List<BaseInfo> bis = this.baseInfoService.findByProperty(map);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (bis != null && bis.size() > 0) {
			BaseInfo bi = bis.get(0);
			if (POPWINDOW.equals(topType)) {
				PopWindow pop = this.popWindowService.findByTopId(bi.getTopId());
				out.print(bi.getTopId() + "_" + bi.getTopType() + "_" + bi.getSuspenAddr() + "_"
						+ bi.getSuspenStatus() + "_" + bi.getSuspenUrl() + "_" + pop.getPopTitle()
						+ "_" + pop.getStartTime() + "_" + pop.getEndTime() + "_" + pop.getHeight()
						+ "_" + pop.getWidth());
			} else {
				out.print(bi.getTopId() + "_" + bi.getTopType() + "_" + bi.getSuspenAddr() + "_"
						+ bi.getSuspenStatus() + "_" + bi.getSuspenUrl() + "_" + bi.getResCode());
			}
		} else {
			out.print("");
		}
		String logMessage = "用户" + user.getName() + "查看资源设置的状态";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
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
	 */
	public ActionForward showSettedPopValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		return null;
	}

}
