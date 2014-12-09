package com.wt.hea.webbuilder.model;

import java.io.Serializable;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 首页基本信息BEAN
 * 编写日期:	2011-3-22
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class BaseInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 首页信息id(流水id)
	 */
	private String topId;
	/**
	 * 站点id标识该资源属于哪个站点
	 */
	private String siteId;
	/**
	 * 站点编号扩展字段
	 */
	private String siteNo;
	/**
	 * 站点名称
	 */
	private String siteName;
	/**
	 * 首页资源类型
	 */
	private String topType;
	/**
	 * 首页资源使用状态
	 */
	private String suspenStatus;
	/**
	 * 首页资源的显示位置
	 */
	private String suspenPath;
	/**
	 * 点击资源时的url
	 */
	private String suspenUrl;
	/**
	 * 资源的物理地址
	 */
	private String suspenAddr;
	/**
	 * 站点对象
	 */
	private SiteManage siteManage;
	/**
	 * 资源代码
	 */
	private String resCode;
	/**
	 * 资源id
	 */
	private String resId;
	/**
	 * 资源宽度
	 */
	private Integer width;
	/**
	 * 资源高度
	 */
	private Integer height;

	/**
	 * 
	 */
	private PopWindow popWindow = new PopWindow();

	public String getResId()
	{
		return resId;
	}

	public void setResId(String resId)
	{
		this.resId = resId;
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

	public String getTopId()
	{
		return topId;
	}

	public void setTopId(String topId)
	{
		this.topId = topId;
	}

	public String getSiteId()
	{
		return siteId;
	}

	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}

	public String getSiteNo()
	{
		return siteNo;
	}

	public void setSiteNo(String siteNo)
	{
		this.siteNo = siteNo;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public String getTopType()
	{
		return topType;
	}

	public void setTopType(String topType)
	{
		this.topType = topType;
	}

	public String getSuspenStatus()
	{
		return suspenStatus;
	}

	public void setSuspenStatus(String suspenStatus)
	{
		this.suspenStatus = suspenStatus;
	}

	public String getSuspenPath()
	{
		return suspenPath;
	}

	public void setSuspenPath(String suspenPath)
	{
		this.suspenPath = suspenPath;
	}

	public String getSuspenUrl()
	{
		return suspenUrl;
	}

	public void setSuspenUrl(String suspenUrl)
	{
		this.suspenUrl = suspenUrl;
	}

	public String getSuspenAddr()
	{
		return suspenAddr;
	}

	public void setSuspenAddr(String suspenAddr)
	{
		this.suspenAddr = suspenAddr;
	}

	public SiteManage getSiteManage()
	{
		return siteManage;
	}

	public void setSiteManage(SiteManage siteManage)
	{
		this.siteManage = siteManage;
	}

	public String getResCode()
	{
		return resCode;
	}

	public void setResCode(String resCode)
	{
		this.resCode = resCode;
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((topId == null) ? 0 : topId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseInfo)) {
			return false;
		}
		BaseInfo other = (BaseInfo) obj;
		if (topId == null) {
			if (other.topId != null) {
				return false;
			}
		} else if (!topId.equals(other.topId)) {
			return false;
		}
		return true;
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
