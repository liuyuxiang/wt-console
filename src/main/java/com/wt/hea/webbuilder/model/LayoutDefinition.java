package com.wt.hea.webbuilder.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <pre>
 * 业务名:布局定义实体bean
 * 功能说明: 描述布局信息
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class LayoutDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 布局编号
	 * 由数据库维护uuid.hex
	 */
	private String layoutCode ;
	
	/**
	 * 布局名称
	 */
	private String layoutName ;
	
	/**
	 * 布局内容
	 */
	private String layoutContent ;
	
	/**
	 * 布局缩略图路径
	 */
	private String layoutPicPath ;
	
	/**
	 * 创建日期
	 */
	private Date createDate ;
	
	/**
	 * 修改日期
	 */
	private Date modifyDate ;

	/**
	 * 布局列数
	 */
	private int column;
	
	public int getColumn(){
		return column;
	}
	public String getLayoutCode() {
		return layoutCode;
	}

	public void setLayoutCode(String layoutCode) {
		this.layoutCode = layoutCode;
	}

	public String getLayoutName() {
		return layoutName;
	}

	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}

	public String getLayoutContent() {
		return layoutContent;
	}

	public void setLayoutContent(String layoutContent) {
		this.layoutContent = layoutContent;
	}

	public String getLayoutPicPath() {
		return layoutPicPath;
	}

	public void setLayoutPicPath(String layoutPicPath) {
		this.layoutPicPath = layoutPicPath;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public void setColumn(int column)
	{
		this.column = layoutContent.split(";").length;
	}
	
	
	
}
