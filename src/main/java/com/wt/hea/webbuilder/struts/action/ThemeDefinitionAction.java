package com.wt.hea.webbuilder.struts.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.model.Page;
import com.wt.hea.common.util.UploadUtil;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.common.util.ZipUtils;
import com.wt.hea.webbuilder.model.ThemeDefinition;
import com.wt.hea.webbuilder.struts.form.ThemeDefinitionForm;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 主题定义
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ThemeDefinitionAction extends DispatchAction
{

	/**
	 * 获取日志实例
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

	/**
	 * 查询出所有主题信息<br>
	 * 并以List的形式存放于request对象中
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
		String currPage = request.getParameter("currPage");
		String perPageRecode = request.getParameter("perPage");
		Page<ThemeDefinition> themeList = null;
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
		themeList = new Page<ThemeDefinition>(Integer.valueOf(perPageRecode),
				Integer.valueOf(currPage));
		themeList = this.themeDefinitionService.loadPage(themeList);

		List<ThemeDefinition> themeDefList = (List<ThemeDefinition>) themeList.getData();
		for (ThemeDefinition td : themeDefList) {
			td.setThemePicPath("/" + this.contextPath + td.getThemePicPath());
		}
		request.getSession().setAttribute("themeDefList", themeList);
		String logMessage = "用户" + user.getName() + "浏览主题信息";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("themeList");
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
	public ActionForward findAllJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		return null;
	}

	/**
	 * 上传主题文件
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
	public ActionForward addTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ThemeDefinitionForm tdf = (ThemeDefinitionForm) form;
		User user = WebUtil.getSessionUser(request);
		FormFile file = tdf.getFile();
		String filePath = this.rootPath + this.contextPath;
		File tempFile = new File(filePath);
		if (tempFile != null && !tempFile.exists()) {
			if (tempFile.mkdirs()) {
				log.info("files create successfully!");
			}
		}
		if (file != null) {
			try {
				UploadUtil.upload(file.getFileData(),
						filePath + File.separator + file.getFileName());
				ZipUtils.decompress(filePath + File.separator + file.getFileName(), filePath
						+ File.separator);
				File zipFile = new File(filePath + File.separator + file.getFileName());
				if (zipFile.delete()) {
					log.info("zipfiles delete successfully!");
				}
			} catch (Exception e) {
				request.setAttribute("themeDefinition", tdf.getThemeDefinition());
				request.setAttribute("message", "资源格式错误,只能上传zip格式的资源包！");
				String logMessage = "用户" + user.getName() + "上传资源格式错误,只能上传zip格式的资源包";
				log.info(logMessage, this.clazz);
				return mapping.findForward("addTheme");
			}
		}
		ThemeDefinition themeDef = tdf.getThemeDefinition();
		themeDef.setThemePath(themeDef.getThemePath().replaceAll("\\\\", "/"));
		themeDef.setThemePicPath(themeDef.getThemePicPath().replaceAll("\\\\", "/"));
		themeDef.setCreateDate(new Date());
		themeDef.setModifyDate(new Date());
		this.themeDefinitionService.save(themeDef);
		String logMessage = "用户" + user.getName() + "添加主题" + themeDef.getThemeName();

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return findAll(mapping, form, request, response);
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
	public ActionForward deleteTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String themeCode = request.getParameter("themeCode");
		PrintWriter out = response.getWriter();
		if (themeCode != null && themeCode.length() > 0) {
			this.themeDefinitionService.deleteById(themeCode);
			out.print("1");
		} else {
			out.print("操作失败！");
		}
		String logMessage = "用户" + user.getName() + "删除主题" + themeCode;

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
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
	public ActionForward updateTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		ThemeDefinitionForm tdf = (ThemeDefinitionForm) form;
		ThemeDefinition themeDef = tdf.getThemeDefinition();
		themeDef.setModifyDate(new Date());
		if (tdf.getFile() != null) {
			try {
				String filePath = this.rootPath + this.contextPath;
				String sourcePath = filePath + File.separator + tdf.getFile().getFileName();
				UploadUtil.upload(tdf.getFile().getFileData(), sourcePath);
				ZipUtils.decompress(sourcePath, filePath);
				File zipFile = new File(sourcePath);
				if (zipFile.delete()) {
					log.info("删除zip文件成功!");
				}
			} catch (Exception e) {
				request.setAttribute("themeDefinition", tdf.getThemeDefinition());
				request.setAttribute("message", "资源格式错误,只能上传zip格式的资源包！");
				return mapping.findForward("modifyTheme");
			}
		}
		themeDef.setThemePath(themeDef.getThemePath().replaceAll("\\\\", "/"));
		themeDef.setThemePicPath(themeDef.getThemePicPath().replaceAll("\\\\", "/"));
		this.themeDefinitionService.update(themeDef);
		String logMessage = "用户" + user.getName() + "修改主题" + themeDef.getThemeName();

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return findAll(mapping, form, request, response);
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
	public ActionForward toUpdateTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String themeCode = request.getParameter("themeCode");
		ThemeDefinition theme = this.themeDefinitionService.findById(themeCode);
		request.setAttribute("themeDefinition", theme);
		return mapping.findForward("modifyTheme");
	}

	/**
	 * 方法说明：判断主题是否存在
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
	public ActionForward themeIsExists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String themeCode = request.getParameter("themeCode");
		if (themeCode != null) {
			ThemeDefinition td = this.themeDefinitionService.findById(themeCode);
			String themePath = this.rootPath + this.contextPath + td.getThemePath();
			File file = null;
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			if (themePath != null && !"".equals((themePath))) {
				file = new File(themePath);
				if (!file.exists()) {
					out.print("0");
					log.info("主题不存在" + themePath + "!");
				} else {
					out.print("1");
				}
			} else {
				out.print("0");
				log.info("主题不存在" + themePath + "!");
			}
		}
		return null;
	}
}
