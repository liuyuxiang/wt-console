package com.wt.hea.webbuilder.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 模板布局的实体类
 * 
 * @author xiaoqi
 * 
 */
@SuppressWarnings("serial")
public class TemplateLayout implements Serializable
{

	/**
	 * 模板布局id
	 */
	private String tmplId;

	/**
	 * 模板布名称
	 */
	private String tmplName;

	/**
	 * 模板布局的状态 '模板状态 01 使用 02 停用';
	 */
	private String tmplStatus;

	/**
	 * 模板布局地址
	 */
	private String tmplAddr;

	/**
	 * 模板布局类型 '模板类型 01 固定 0101 首页模板 0102 二级模板 02 自动';
	 */
	private String tmplCode;

	/**
	 * 模板布局图片地址
	 */
	private String tmplPicAddr;

	/**
	 * 显示顺序
	 */
	private Integer dispSn;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 修改日期
	 */
	private Date modifyDate;

	/**
	 * 模板样式地址
	 */
	private String tmplStyleAddr;

	/**
	 * 占位符信息
	 */
	private List<PlaceHolder> placeHolderList;

	/**
	 * 应用id，用于标识模板属于那个应用
	 */
	private String appId;

	public List<PlaceHolder> getPlaceHolderList()
	{
		return placeHolderList;
	}

	public void setPlaceHolderList(List<PlaceHolder> placeHolderList)
	{
		this.placeHolderList = placeHolderList;
	}

	public String getTmplId()
	{
		return tmplId;
	}

	public void setTmplId(String tmplId)
	{
		this.tmplId = tmplId;
	}

	public String getTmplName()
	{
		return tmplName;
	}

	public void setTmplName(String tmplName)
	{
		this.tmplName = tmplName;
	}

	public String getTmplStatus()
	{
		return tmplStatus;
	}

	public void setTmplStatus(String tmplStatus)
	{
		this.tmplStatus = tmplStatus;
	}

	public String getTmplAddr()
	{
		return tmplAddr;
	}

	public void setTmplAddr(String tmplAddr)
	{
		this.tmplAddr = tmplAddr;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getModifyDate()
	{
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate)
	{
		this.modifyDate = modifyDate;
	}

	public String getTmplCode()
	{
		return tmplCode;
	}

	public void setTmplCode(String tmplCode)
	{
		this.tmplCode = tmplCode;
	}

	public Integer getDispSn()
	{
		return dispSn;
	}

	public void setDispSn(Integer dispSn)
	{
		this.dispSn = dispSn;
	}

	public String getTmplPicAddr()
	{
		return tmplPicAddr;
	}

	public void setTmplPicAddr(String tmplPicAddr)
	{
		this.tmplPicAddr = tmplPicAddr;
	}

	public String getTmplStyleAddr()
	{
		return tmplStyleAddr;
	}

	public void setTmplStyleAddr(String tmplStyleAddr)
	{
		this.tmplStyleAddr = tmplStyleAddr;
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
