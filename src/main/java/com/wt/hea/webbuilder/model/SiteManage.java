package com.wt.hea.webbuilder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 站点管理实体类
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */

public class SiteManage implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 站点首页基本信息Bean
	 */
	private Set<BaseInfo> baseInfos;

	/**
	 * 站点类型（目录、站点）
	 */
	private String typeCode;

	/**
	 * 版权信息
	 */
	private String copyrightContent;

	/**
	 * logo地址
	 */
	private String logoPath;
	/**
	 * 站点下的导航根id
	 */
	private String navRootId;

	/**
	 * 站点id
	 */
	private String siteId;

	/**
	 * 站点编号
	 */
	private String siteNo;

	/**
	 * 站点名称
	 */
	private String siteName;

	/**
	 * 父级站点编号
	 */
	private String parentSiteNo;

	/**
	 * 父级站点名称
	 */
	private String parentSiteName;

	/**
	 * 显示顺序
	 */
	private Integer dispSn;

	/**
	 * 站点状态
	 */
	private String siteStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 用户编号
	 */
	private String userNo;

	/**
	 * 部门编号
	 */
	private String deptNo;

	/**
	 * 站点根目录地址
	 */
	private String siteAddr;

	/**
	 * 应用id，站点隶属于哪个应用
	 */
	private String appId;

	/**
	 * 上级站点
	 */
	private SiteManage parentSite;

	/**
	 * 下级站点
	 */
	private Set<SiteManage> subSites;

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

	public String getParentSiteNo()
	{
		return parentSiteNo;
	}

	public void setParentSiteNo(String parentSiteNo)
	{
		this.parentSiteNo = parentSiteNo;
	}

	public Integer getDispSn()
	{
		return dispSn;
	}

	public void setDispSn(Integer dispSn)
	{
		this.dispSn = dispSn;
	}

	public String getSiteStatus()
	{
		return siteStatus;
	}

	public void setSiteStatus(String siteStatus)
	{
		this.siteStatus = siteStatus;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getUserNo()
	{
		return userNo;
	}

	public void setUserNo(String userNo)
	{
		this.userNo = userNo;
	}

	public String getDeptNo()
	{
		return deptNo;
	}

	public void setDeptNo(String deptNo)
	{
		this.deptNo = deptNo;
	}

	public String getParentSiteName()
	{
		return parentSiteName;
	}

	public void setParentSiteName(String parentSiteName)
	{
		this.parentSiteName = parentSiteName;
	}

	public String getSiteAddr()
	{
		return siteAddr;
	}

	public void setSiteAddr(String siteAddr)
	{
		this.siteAddr = siteAddr;
	}

	public SiteManage getParentSite()
	{
		return parentSite;
	}

	public void setParentSite(SiteManage parentSite)
	{
		this.parentSite = parentSite;
	}

	public Set<SiteManage> getSubSites()
	{
		return subSites;
	}

	public void setSubSites(Set<SiteManage> subSites)
	{
		this.subSites = subSites;
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((siteId == null) ? 0 : siteId.hashCode());
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
		if (!(obj instanceof SiteManage)) {
			return false;
		}
		SiteManage other = (SiteManage) obj;
		if (siteId == null) {
			if (other.siteId != null) {
				return false;
			}
		} else if (!siteId.equals(other.siteId)) {
			return false;
		}
		return true;
	}

	public String getNavRootId()
	{
		return navRootId;
	}

	public void setNavRootId(String navRootId)
	{
		this.navRootId = navRootId;
	}

	public String getLogoPath()
	{
		return logoPath;
	}

	public void setLogoPath(String logoPath)
	{
		this.logoPath = logoPath;
	}

	public String getCopyrightContent()
	{
		return copyrightContent;
	}

	public void setCopyrightContent(String copyrightContent)
	{
		this.copyrightContent = copyrightContent;
	}

	public String getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(String typeCode)
	{
		this.typeCode = typeCode;
	}

	public Set<BaseInfo> getBaseInfos()
	{
		return baseInfos;
	}

	public void setBaseInfos(Set<BaseInfo> baseInfos)
	{
		this.baseInfos = baseInfos;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

}
