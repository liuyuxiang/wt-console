package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.model.PopWindow;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 首页资源信息form
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class BaseInfoForm extends ActionForm
{
	/**
	 * 页面资源信息bean
	 */
	private BaseInfo baseInfo = new BaseInfo();

	/**
	 * 
	 */
	private PopWindow popWindow = new PopWindow();

	/**
	 * 上传文件
	 */
	private FormFile file;

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
	}

	public BaseInfo getBaseInfo()
	{
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo)
	{
		this.baseInfo = baseInfo;
	}

	public PopWindow getPopWindow()
	{
		return popWindow;
	}

	public void setPopWindow(PopWindow popWindow)
	{
		this.popWindow = popWindow;
	}

}
