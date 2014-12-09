package com.wt.uum2.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wt.uum2.bean.Setting;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class ConstantsController extends BaseController
{
	private Setting setting;

	public void setSetting(Setting setting)
	{
		this.setting = setting;
	}

	/**
	 * 方法说明：getConstants
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/script/constants")
	public void getConstants(ModelMap model, HttpServletResponse response)
	{
		response.setContentType("application/javascript;charset=utf-8");
		model.addAttribute("maxRecordPerPage", setting.getMaxRecordPerPage());
	}
}
