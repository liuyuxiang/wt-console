package com.wt.hea.rbac.struts.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/***
 * 
 * <pre>
 * 业务名:多应用登陆后页面定制action
 * 功能说明: 多应用登陆后页面定制action
 * 编写日期:	2011-6-5
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-6-5
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class AppPageAction extends BaseAction{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fullPath=request.getSession().getServletContext().getRealPath("modules");
		File file=new File(fullPath);
		List<String> appDirList=new ArrayList<String>();
		for(String i: file.list()){
			if(i.startsWith("app")){
				appDirList.add(i);
			}
		}
		request.setAttribute("appDirList", appDirList);
		return mapping.findForward("listAppDir");
	}
}
