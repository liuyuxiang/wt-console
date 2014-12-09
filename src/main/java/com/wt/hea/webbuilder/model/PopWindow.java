package com.wt.hea.webbuilder.model;

import java.io.Serializable;

/**
 * <pre>
 * 业务名:
 * 功能说明: 弹出窗口基本信息
 * 编写日期:	2011-12-20
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PopWindow implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4539940875477154936L;

	/**
	 * 
	 */
	private String popId;

	/**
	 * 
	 */
	private String siteId;

	/**
	 * 
	 */
	private String topId;

	/**
	 * 
	 */
	private String popTitle;

	/**
	 * 
	 */
	private String popContent;

	/**
	 * 
	 */
	private String popStatus;

	/**
	 * 
	 */
	private String startTime;

	/**
	 * 
	 */
	private String endTime;

	/**
	 * 
	 */
	private String popUrl;

	/**
	 * 
	 */
	private Integer width;

	/**
	 * 
	 */
	private Integer height;

	public String getPopId()
	{
		return popId;
	}

	public void setPopId(String popId)
	{
		this.popId = popId;
	}

	public String getSiteId()
	{
		return siteId;
	}

	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}

	public String getTopId()
	{
		return topId;
	}

	public void setTopId(String topId)
	{
		this.topId = topId;
	}

	public String getPopTitle()
	{
		return popTitle;
	}

	public void setPopTitle(String popTitle)
	{
		this.popTitle = popTitle;
	}

	public String getPopContent()
	{
		return popContent;
	}

	public void setPopContent(String popContent)
	{
		this.popContent = popContent;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public String getPopUrl()
	{
		return popUrl;
	}

	public void setPopUrl(String popUrl)
	{
		this.popUrl = popUrl;
	}

	public String getPopStatus()
	{
		return popStatus;
	}

	public void setPopStatus(String popStatus)
	{
		this.popStatus = popStatus;
	}

	public Integer getWidth()
	{
		return width;
	}

	public void setWidth(Integer width)
	{
		this.width = width;
	}

	public Integer getHeight()
	{
		return height;
	}

	public void setHeight(Integer height)
	{
		this.height = height;
	}

}
