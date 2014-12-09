package com.wt.hea.rbac.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hirisun.hea.api.domain.NavNodeType;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 指标资源实体bean
 * 编写日期:	2010-3-22
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-22
 *    修改人：尹浩祺
 *    修改内容：去掉与组的关联
 * </pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "nav")
public class Index implements Serializable, Comparable<Index>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7057799771913264001L;
	/**
	 * 资源标识
	 */
	@XmlElement(name = "uuid")
	private String indexuuid;
	/**
	 * 资源名称
	 */
	@XmlElement(name = "name")
	private String indexname;
	/**
	 * 资源排序号
	 */
	@XmlElement(name = "orderNum")
	private Integer indexorder;
	/**
	 * 资源为超级链接时，点击跳转的目标窗体
	 */
	@XmlElement(name = "target")
	private String target;
	/**
	 * 资源为超级链接时的url
	 */
	@XmlElement(name = "url")
	private String indexurl;
	/**
	 * 资源扩展的url或字段
	 */
	private String indexmappedurl;
	/**
	 * 资源的父项id号
	 */
	@XmlElement(name = "parentUUID")
	private String parentindexuuid;
	/**
	 * 资源是否启用或禁用
	 */
	private String way; // 0禁用 1启用
	/**
	 * 资源类型
	 */
	private Integer indextype; // 0是子标 1是控件 2是站点 3是菜单
	/**
	 * 资源图标
	 */
	@XmlElement(name = "ico")
	private String indexicon;
	/**
	 * 资源的层级数
	 */
	@XmlElement(name = "level")
	private Integer indexlevel; // 资源结点层级数
	/**
	 * 资源描述
	 */
	@XmlElement(name = "desc")
	private String description;
	/**
	 * 资源是否有下级结点
	 */
	@XmlElement(name = "hasChild")
	private Integer hasChild; // 是否有下级结点
	/**
	 * 资源层级编码，避免递归查询用字段
	 */
	private String levelCode; // 资源层次编码
	/**
	 * 
	 */
	private Set<Index> subIndexes = new HashSet<Index>();
	/**
	 * 
	 */
	private Index parentIndex;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String indexIconPath;
	/**
	 * 
	 */
	private String indexIconOnPath;

	/**
	 * 
	 */
	private String styleClass;

	/**
	 * 
	 */
	private String styleClassOn;

	/**
	 * 
	 */
	@XmlElement(name = "appId")
	private String appId;

	/**
	 * 
	 */
	@XmlElement
	private IndexIcon icon = new IndexIcon();

	/**
	 * 
	 */
	@XmlElement
	private IndexCss css = new IndexCss();

	/**
	 * 
	 */
	@XmlElement(name = "wicketClass")
	private String wicketClass;
	/**
	 * 接收client参数
	 * add by DexinWang
	 */
	@XmlElement(name="invisible")
	private boolean invisible;
	
	/**
	 * 接收client参数
	 * add by DexinWang
	 */
	@XmlElement(name="bookmarkable")
	private boolean bookmarkable;
	/**
	 * 接收client参数
	 * add by DexinWang
	 */
	@XmlElement(name="type")
	private NavNodeType type;
	/**
	 * 重载默认构造
	 */
	public Index()
	{

	}

	/**
	 * 重载构造初始化级别code
	 * 
	 * @param levelCode
	 *            levelCode
	 */
	public Index(String levelCode)
	{
		this.levelCode = levelCode;
	}

	/**
	 * 
	 * 方法说明：扩展字段，记录资源其它信息或url
	 * 
	 * @return indexmappedurl
	 */
	public String getIndexmappedurl()
	{
		return indexmappedurl;
	}

	/**
	 * 
	 * 方法说明：扩展字段，记录资源其它信息或url
	 * 
	 * @param indexmappedurl
	 *            资源扩展的url或字段
	 */
	public void setIndexmappedurl(String indexmappedurl)
	{
		this.indexmappedurl = indexmappedurl;
	}

	/**
	 * 
	 * 方法说明：获到当前资源结点的父级结点
	 * 
	 * @return parentIndex
	 */
	public Index getParentIndex()
	{
		return parentIndex;
	}

	/**
	 * 
	 * 方法说明：设置当前资源结点的父级结点
	 * 
	 * @param parentIndex
	 *            父级
	 */
	public void setParentIndex(Index parentIndex)
	{
		this.parentIndex = parentIndex;
	}

	/**
	 * 
	 * 方法说明：获取当前资源的子级节点
	 * 
	 * @return subIndexes
	 */
	public Set<Index> getSubIndexes()
	{
		return subIndexes;
	}

	/**
	 * 
	 * 方法说明：设置资源的子级结点
	 * 
	 * @param subIndexes
	 *            子级
	 */
	public void setSubIndexes(Set<Index> subIndexes)
	{
		this.subIndexes = subIndexes;
	}

	/**
	 * 
	 * 方法说明：资源是否禁用，0禁用，1启用
	 * 
	 * @return way
	 */
	public String getWay()
	{
		return way;
	}

	/**
	 * 
	 * 方法说明：设置资源是否禁用
	 * 
	 * @param way
	 *            资源是否启用或禁用
	 */
	public void setWay(String way)
	{
		this.way = way;
	}

	/**
	 * 
	 * 方法说明：获取资源id
	 * 
	 * @return indexuuid
	 */
	public String getIndexuuid()
	{
		return indexuuid;
	}

	/**
	 * 
	 * 方法说明：设置资源id
	 * 
	 * @param indexuuid
	 *            资源id
	 */
	public void setIndexuuid(String indexuuid)
	{
		this.indexuuid = indexuuid;
	}

	/**
	 * 
	 * 方法说明：获取资源名称
	 * 
	 * @return 资源名称
	 */
	public String getIndexname()
	{
		return indexname;
	}

	/**
	 * 
	 * 方法说明：设置资源名称
	 * 
	 * @param indexname
	 *            资源名称
	 */
	public void setIndexname(String indexname)
	{
		this.indexname = indexname;
	}

	/**
	 * 
	 * 方法说明：获取资源的排序号
	 * 
	 * @return 资源的排序号
	 */
	public Integer getIndexorder()
	{
		return indexorder;
	}

	/**
	 * 
	 * 方法说明：设置资源的排序号
	 * 
	 * @param indexorder
	 *            排序号
	 */
	public void setIndexorder(Integer indexorder)
	{
		this.indexorder = indexorder;
	}

	/**
	 * 
	 * 方法说明：获取资源的跳转目标
	 * 
	 * @return 跳转目标
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * 
	 * 方法说明：设置资源的跳转目标
	 * 
	 * @param target
	 *            跳转目标
	 */
	public void setTarget(String target)
	{
		this.target = target;
	}

	/**
	 * 
	 * 方法说明：获取资源的url
	 * 
	 * @return url
	 */
	public String getIndexurl()
	{
		return indexurl;
	}

	/**
	 * 
	 * 方法说明：设置资源的url
	 * 
	 * @param indexurl
	 *            url
	 */
	public void setIndexurl(String indexurl)
	{
		this.indexurl = indexurl;
	}

	/**
	 * 
	 * 方法说明：获取资源的父级uuid
	 * 
	 * @return 父级uuid
	 */
	public String getParentindexuuid()
	{
		return parentindexuuid;
	}

	/**
	 * 
	 * 方法说明：设置资源的uuid
	 * 
	 * @param parentindexuuid
	 *            资源的uuid
	 */
	public void setParentindexuuid(String parentindexuuid)
	{
		this.parentindexuuid = parentindexuuid;
	}

	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((indexuuid == null) ? 0 : indexuuid.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Index)) {
			return false;
		}
		Index other = (Index) obj;
		if (indexuuid == null) {
			if (other.indexuuid != null) {
				return false;
			}
		} else if (!indexuuid.equals(other.indexuuid)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 方法说明：获取资源的类型，0是指标 1是控件 2是站点 3是菜单
	 * 
	 * @return 类型
	 */
	public Integer getIndextype()
	{
		return indextype;
	}

	/**
	 * 
	 * 方法说明：设置资源的类型，0是指标 1是控件 2是站点 3是菜单
	 * 
	 * @param indextype
	 *            类型
	 */
	public void setIndextype(Integer indextype)
	{
		this.indextype = indextype;
	}

	/**
	 * 
	 * 方法说明：获取资源的图标地址
	 * 
	 * @return 图标地址
	 */
	public String getIndexicon()
	{
		return indexicon;
	}

	/**
	 * 
	 * 方法说明：设置资源的指标图标地址
	 * 
	 * @param indexicon
	 *            图标地址
	 */
	public void setIndexicon(String indexicon)
	{
		this.indexicon = indexicon;
	}

	/**
	 * 
	 * 方法说明：获取资源的层级数
	 * 
	 * @return 层级数
	 */
	public Integer getIndexlevel()
	{
		return indexlevel;
	}

	/**
	 * 
	 * 方法说明：设置资源的层级数
	 * 
	 * @param indexlevel
	 *            层级数
	 */
	public void setIndexlevel(Integer indexlevel)
	{
		this.indexlevel = indexlevel;
	}

	/**
	 * 比较
	 * 
	 * @param o
	 *            资源
	 * @return 比较值
	 */
	public int compareTo(Index o)
	{
		if (o != null) {
			if (this.getIndexorder() == null) {
				this.setIndexorder(0);
			}
			if (o.getIndexorder() == null) {
				o.setIndexorder(0);
			}
			return Integer.valueOf(this.getIndexorder()) - Integer.valueOf(o.getIndexorder());
		}
		return 0;
	}

	/**
	 * 
	 * 方法说明：获取资源描述
	 * 
	 * @return 描述
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * 
	 * 方法说明：设置资源描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * 
	 * 方法说明：获取当前资源是否有子级结点
	 * 
	 * @return 是否有子级结点
	 */
	public Integer getHasChild()
	{
		return hasChild;
	}

	/**
	 * 
	 * 方法说明：设置资源有子级结点
	 * 
	 * @param hasChild
	 *            是否有子级结点
	 */
	public void setHasChild(Integer hasChild)
	{
		this.hasChild = hasChild;
	}

	/**
	 * 
	 * 方法说明：获取资源的创建时间
	 * 
	 * @return 创建时间
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * 
	 * 方法说明：设置资源的创建时间
	 * 
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	/**
	 * 
	 * 方法说明：获取资源图标的磁盘路径
	 * 
	 * @return 磁盘路径
	 */
	public String getIndexIconPath()
	{
		return indexIconPath;
	}

	/**
	 * 
	 * 方法说明：设置资源的磁盘路径
	 * 
	 * @param indexIconDiskPath
	 *            磁盘路径
	 */
	public void setIndexIconPath(String indexIconPath)
	{
		this.indexIconPath = indexIconPath;
		this.icon.setMidPath(indexIconPath);
	}

	/**
	 * 
	 * 方法说明：获取资源的层级编码，用于避免递归查询用
	 * 
	 * @return 层级编码
	 */
	public String getLevelCode()
	{
		return levelCode;
	}

	/**
	 * 
	 * 方法说明：设置层级编码
	 * 
	 * @param levelCode
	 *            层级编码
	 */
	public void setLevelCode(String levelCode)
	{
		this.levelCode = levelCode;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getWicketClass()
	{
		return wicketClass;
	}

	public void setWicketClass(String wicketClass)
	{
		this.wicketClass = wicketClass;
	}

	public String getStyleClass()
	{
		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
		this.css.setStyleClass(styleClass);
	}

	public String getIndexIconOnPath()
	{
		return indexIconOnPath;
	}

	public void setIndexIconOnPath(String indexIconOnPath)
	{
		this.indexIconOnPath = indexIconOnPath;
		this.icon.setMidOnMovePath(indexIconOnPath);
	}

	public String getStyleClassOn()
	{
		return styleClassOn;
	}

	public void setStyleClassOn(String styleClassOn)
	{
		this.styleClassOn = styleClassOn;
		this.css.setStyleClassOnMver(styleClassOn);
	}

	public IndexCss getCss()
	{
		return css;
	}

	public void setCss(IndexCss css)
	{
		this.css = css;
	}

	public IndexIcon getIcon()
	{
		return icon;
	}

	public void setIcon(IndexIcon icon)
	{
		this.icon = icon;
	}

	public void setInvisible(boolean invisible)
	{
		this.invisible = invisible;
		this.way=invisible?"1":"0";
	}

	public void setBookmarkable(boolean bookmarkable)
	{
		this.bookmarkable = bookmarkable;
	}

	public void setType(NavNodeType type)
	{
		this.type = type;
		this.indextype = type.getCode();
	}
}
