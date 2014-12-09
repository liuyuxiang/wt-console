package com.wt.hea.common.util;

/**
 * 
 * <pre>
 * 业务名: 页面组件引用bean，tab，jquery，日历等组件引用,
 * 通过添加一个servlet的linstener构建el表达式页面引用，样例代码如下：
 * ${componentConfig.jqueryPath}
 * ${componentConfig.dhtmlTreePath}
 * 功能说明: 
 * 编写日期:	2011-9-2
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-9-2
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public final class WebComponentConfig
{
	/**
	 * 上下文路径
	 */
	private String contextPath = "";

	/**
	 * @param contextPath
	 *            上下文路径
	 */
	public WebComponentConfig(String contextPath)
	{
		this.contextPath = contextPath;
	}

	/**
	 * 方法说明： 获取dialog的资源文件
	 * 
	 * @return dialog所需要用到的资源文件
	 */
	public String getDialogPath()
	{
		StringBuffer html = new StringBuffer("<!-- 弹出窗口 -->");
		html.append("<link href='" + contextPath
				+ "/common/component/dialog/css/default.css' rel='stylesheet' type='text/css' >");
		html.append("<script src='" + contextPath
				+ "/common/component/dialog/script/lhgcore.min.js'></script>");
		html.append("<script src='" + contextPath
				+ "/common/component/dialog/script/lhgdialog.min.js'></script>");
		html.append("<!--  弹出窗口 End: -->");
		return html.toString();
	}

	/**
	 * 方法说明： 获取滑动菜单的资源文件
	 * 
	 * @return 滑动菜单的资源文件
	 */
	public String getSdMenuPath()
	{
		StringBuffer html = new StringBuffer("<!-- 侧边滑动菜单 -->");
		html.append("<link href='" + contextPath
				+ "/common/component/sdmenu/css/sdmenu.css' rel='stylesheet' type='text/css' >");
		html.append("<script src='" + contextPath
				+ "/common/component/sdmenu/script/sdmenu.js'></script>");
		html.append("<!--  侧边滑动菜单 End: -->");
		return html.toString();
	}

	/**
	 * 方法说明： 获取jquery的底层js路径
	 * 
	 * @return jquery的底层js路径
	 */
	public String getJqueryPath()
	{
		return "<script src='" + contextPath
				+ "/common/script/jquery/jquery-1.4.2.min.js' type='text/javascript'></script>";
	}

	/**
	 * 方法说明： 获取dhtmlTree组件的资源文件，包括样式和js
	 * 
	 * @return 资源文件路径
	 */
	public String getDhtmlTreePath()
	{
		StringBuffer html = new StringBuffer("<!-- ajax树状结构所需文件 -->");
		html.append("<link href='"
				+ contextPath
				+ "/common/component/dhtmlxtree/css/dhtmlxtree.css' rel='stylesheet' type='text/css' >");
		html.append("<script src='" + contextPath
				+ "/common/component/dhtmlxtree/script/dhtmlxcommon.js'></script>");
		html.append("<script src='" + contextPath
				+ "/common/component/dhtmlxtree/script/dhtmlxtree.js'></script>");
		html.append("<!-- ajax树状结构所需文件 End:-->");
		return html.toString();
	}

	/**
	 * 方法说明： 获取tab控件的资源文件路径
	 * 
	 * @return tab控件的资源文件路径
	 */
	public String getJerichotabPath()
	{
		StringBuffer html = new StringBuffer("<!-- tab页 -->");
		html.append("<link href='"
				+ contextPath
				+ "/common/component/jerichotab/css/jquery.jerichotab.css' type='text/css' rel='stylesheet' />");
		html.append("<script src='"
				+ contextPath
				+ "/common/component/jerichotab/script/jquery.jerichotab.js' type='text/javascript' ></script>");
		html.append("<!-- tab页 End: -->");
		return html.toString();
	}

	/**
	 * 方法说明： 获取日历组件的资源文件
	 * 
	 * @return 日历组件的资源文件
	 */
	public String getDatePickPath()
	{
		return "<script type='text/javascript' src='" + contextPath
				+ "/common/component/DatePicker/WdatePicker.js'></script>";
	}

	/**
	 * 方法说明：
	 * 
	 * @return String
	 */
	public String getTabControlPath()
	{
		return "";
	}

	/**
	 * 方法说明： jqueryui的js 如果使用相关组件请引用相应组件的js，该模块以后不再更新
	 * 
	 * @return jqueryui的所需的js
	 */
	@Deprecated
	public String getJqueryUiPath()
	{
		StringBuffer path = new StringBuffer("");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/heaconsole/script/jqueryui/jquery.ui.core.js'></script>");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/heaconsole/script/jqueryui/jquery.ui.widget.js'></script>");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/heaconsole/script/jqueryui/jquery.ui.mouse.js'></script>");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/heaconsole/script/jqueryui/jquery.ui.button.js'></script>");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/heaconsole/script/jqueryui/jquery.ui.draggable.js'></script>");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/heaconsole/script/jqueryui/jquery.ui.position.js'></script>");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/heaconsole/script/jqueryui/jquery.ui.dialog.js'></script>");
		return path.toString();
	}

	/**
	 * 方法说明：
	 * 
	 * @return String
	 */
	public String getValidationPath()
	{
		StringBuffer path = new StringBuffer("");
		path.append("<link href='"
				+ contextPath
				+ "/common/component/validator/css/validation.css' type='text/css' rel='stylesheet' />");
		path.append("<script src='"
				+ contextPath
				+ "/common/component/validator/script/validation.js' type='text/javascript'></script>");
		return path.toString();
	}

	/**
	 * 方法说明：
	 * 
	 * @return String
	 */
	public String getEasySilderPath()
	{
		StringBuffer path = new StringBuffer("");
		path.append("<link href='" + contextPath
				+ "/common/component/easyslider/css/screen.css' type='text/css' rel='stylesheet'>");
		path.append("<script type='text/javascript' src='" + contextPath
				+ "/common/component/easyslider/script/easySlider1.7.js'></script>");
		return path.toString();
	}

}
