package com.wt.hea.webbuilder.struts.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.webbuilder.model.PersonalSystemParameter;
import com.hirisun.hea.api.domain.User;

/**
 * 个性化系统参数控制器
 * 
 * @author xiaoqi
 * @version 1.0.1.201008
 * @author pu
 * @version 1.0.2.20101223 1、修改了findAllByPersonal调转到findAllOverAll，findAllOverAll跳转到页面。
 *          2、在findAllOverAll中添加json数据返回，为了定制首页 3、修改页面调用findAllByCodeType为findAllByPersonal
 */
public class PersonalSysParamAction extends DispatchAction
{

	/**
	 * 获取日志文件实例
	 */
	private final Logger log = LoggerService.getInstance(this.clazz);

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
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String pspId = request.getParameter("pspIds");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			if (pspId != null && !"".equals(pspId)) {
				String[] pspIds = pspId.split(",");

				// pspIds = new String [pspId.length()];
				// pspIds = pspId.split(",");
				for (String tempId : pspIds) {
					PersonalSystemParameter psp = this.personalSystemParameterService
							.findById(tempId);
					psp.setCodeValue("1");
					this.personalSystemParameterService.update(psp);
				}
				// 取消选择
				List<PersonalSystemParameter> pspList = this.personalSystemParameterService
						.findByIdsOut(pspIds, new String[] { "overall", "personal" });
				for (PersonalSystemParameter psp : pspList) {
					psp.setCodeValue("0");
					this.personalSystemParameterService.update(psp);
				}
			} else {
				List<PersonalSystemParameter> pspList = this.personalSystemParameterService
						.findAll();
				for (PersonalSystemParameter psp : pspList) {
					psp.setCodeValue("0");
					this.personalSystemParameterService.update(psp);
				}
			}
			out.print("1");
			out.flush();
			out.close();
		} catch (Exception e) {
			out.print("0");
			out.flush();
			out.close();
			e.printStackTrace();
		}
		String logMessage = "用户" + user.getName() + "设置系统参数";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return null;
	}

	/**
	 * 查找用户参数
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
	public ActionForward findAllByPersonal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// String codeType = request.getParameter("codeType");
		// if(codeType == null ||(codeType != null && "".equals(codeType))){
		// codeType = "personal";
		// }
		User user = WebUtil.getSessionUser(request);

		List<PersonalSystemParameter> psps = this.personalSystemParameterService.findByProperty(
				"codeType", "personal");
		request.getSession().setAttribute("personalsysParams", psps);
		String logMessage = "用户" + user.getName() + "浏览用户参数";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("toOverAll");
	}

	/**
	 * 查找全局参数
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
	public ActionForward findAllOverAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// String codeType = request.getParameter("codeType");
		// if(codeType == null ||(codeType != null && "".equals(codeType))){
		// codeType = "overall";
		// }
		User user = WebUtil.getSessionUser(request);
		String type = request.getParameter("type");

		List<PersonalSystemParameter> psps = this.personalSystemParameterService.findByProperty(
				"codeType", "overall");
		String logMessage = "用户" + user.getName() + "用户浏览全局参数";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		if (type.equalsIgnoreCase("admin")) {// 用于页面配置
			request.getSession().setAttribute("overAllsysParams", psps);
			return mapping.findForward("sysparam");
		} else {// 用户页面定制
			JSONArray jo = JSONArray.fromObject(psps);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(jo.toString());
			out.flush();
			out.close();
			return null;
		}
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
	public ActionForward findAllByCodeType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		String[] codetype = request.getParameterValues("codetype");
		List<PersonalSystemParameter> psps = this.personalSystemParameterService
				.findByType(codetype);
		request.setAttribute("sysParams", psps);
		String type = "";
		for (String code : codetype) {
			type = type.concat(code);
		}
		String logMessage = "用户" + user.getName() + "查找类型为" + type + "的系统参数";

		log.saveLog(logMessage, request);
		log.info(logMessage, this.clazz);
		return mapping.findForward("sysparam");
	}

}
