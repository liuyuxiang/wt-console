package com.wt.uum2.domain;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	nautilus
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "dept")
public class Department extends Resource
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3921351427616685867L;

	/**
	 * 部门所在的审核组
	 */
	private List<DepartmentAuthor> departmentAuthors;

	/**
	 * 机构代码(编号)
	 */
	private String code;

	/**
	 * 是否有子机构
	 */
	private boolean hasChildren;

	/**
	 * 机构名
	 */
	@XmlElement
	private String name;

	/**
	 * 父机构
	 */
	private Department parent;

	/**
	 * 父机构UUID外键
	 */
	@XmlElement
	private String parentUUID;

	/**
	 * 机构uuid路径
	 */
	@XmlElement
	private String path;

	/**
	 * 部门编码
	 */
	@XmlElement(name="code")
	private String deptCode;

	/**
	 * 单位编码
	 */
	private String orgCode;

	/**
	 * 父部门编码
	 */
	private String deptParentCode;

	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	/**
	 * 部门当前审核级别
	 */
	private Long currentAuthorLevel;

	/**
	 * 部门层次
	 */
	private int level;

	/**
	 * rtx编码
	 */
	private Long rtxCode;

	/**
	 * 
	 */
	public Department()
	{
		super();
		setType(2);
		hasChildren = true;
	}

	public Long getRtxCode()
	{
		return rtxCode;
	}

	public void setRtxCode(Long rtxCode)
	{
		this.rtxCode = rtxCode;
	}

	public Long getCurrentAuthorLevel()
	{
		return currentAuthorLevel;
	}

	public void setCurrentAuthorLevel(Long currentAuthorLevel)
	{
		this.currentAuthorLevel = currentAuthorLevel;
	}

	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public List<DepartmentAuthor> getDepartmentAuthors()
	{
		return departmentAuthors;
	}

	public void setDepartmentAuthors(List<DepartmentAuthor> departmentAuthors)
	{
		this.departmentAuthors = departmentAuthors;
	}

	public String getDeptCode()
	{
		return deptCode;
	}

	public void setDeptCode(String deptCode)
	{
		this.deptCode = deptCode;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getDeptParentCode()
	{
		return deptParentCode;
	}

	public void setDeptParentCode(String deptParentCode)
	{
		this.deptParentCode = deptParentCode;
	}

	public String getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public Department getParent()
	{
		return parent;
	}

	public String getParentUUID()
	{
		return parentUUID;
	}

	public String getPath()
	{
		return path;
	}

	public boolean isHasChildren()
	{
		return hasChildren;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public void setHasChildren(boolean hasChildren)
	{
		this.hasChildren = hasChildren;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 方法说明：setParent
	 * 
	 * @param parent
	 *            parent
	 */
	public void setParent(Department parent)
	{
		this.parent = parent;
		if (parent != null) {
			this.setParentUUID(parent.getUuid());
			this.setDeptParentCode(parent.getDeptCode());
		}
	}

	public void setParentUUID(String parentUUID)
	{
		this.parentUUID = parentUUID;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	/**
	 * @return the level
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

}
