package com.wt.hea.webbuilder.struts.form;

/**
 * 页面portlet辅助对象 用于辅助生成json 其属性对应com.wt.hea.webbuilder.model.UserPersonalInfo
 * 
 * @author xiaoqi
 * 
 */
public class PagePortlets
{

	/**
	 * 对应上面所指出bean中的portletid
	 */
	private String id;

	/**
	 * 
	 */
	private String title;

	/**
	 * 获取数据时的URL
	 */
	private String actionUrl;

	/**
	 * 点标题时的URL
	 */
	private String titleUrl;

	/**
	 * 
	 */
	private String type;

	/**
	 * 
	 */
	private String editUrl;

	/**
	 * 
	 */
	private String isDrag;

	/**
	 * 
	 */
	private String isEdit;

	/**
	 * 
	 */
	private String isClose;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getActionUrl()
	{
		return actionUrl;
	}

	public void setActionUrl(String actionUrl)
	{
		this.actionUrl = actionUrl;
	}

	public String getEditUrl()
	{
		return editUrl;
	}

	public void setEditUrl(String editUrl)
	{
		this.editUrl = editUrl;
	}

	public String getIsDrag()
	{
		return isDrag;
	}

	public void setIsDrag(String isDrag)
	{
		this.isDrag = isDrag;
	}

	public String getIsEdit()
	{
		return isEdit;
	}

	public void setIsEdit(String isEdit)
	{
		this.isEdit = isEdit;
	}

	public String getIsClose()
	{
		return isClose;
	}

	public void setIsClose(String isClose)
	{
		this.isClose = isClose;
	}

	public String getTitleUrl()
	{
		return titleUrl;
	}

	public void setTitleUrl(String titleUrl)
	{
		this.titleUrl = titleUrl;
	}

}
