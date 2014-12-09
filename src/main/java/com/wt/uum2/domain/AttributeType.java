package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	nautilus
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class AttributeType implements Serializable
{

	/**
	 * 
	 */
	public static final int NORMAL = 0;

	/**
	 * 
	 */
	public static final int NUMBER = 1;

	/**
	 * 
	 */
	public static final int DATE = 2;

	/**
	 * 
	 */
	public static final int EMPLOYEENUMBER = 3;

	/**
	 * 
	 */
	public static final int WORD = 4;

	/**
	 * 
	 */
	public static final int W_N = 5;

	/**
	 * 
	 */
	public static final int EMAIL = 6;

	/**
	 * 
	 */
	public static final int CHINESE = 7;

	/**
	 * 
	 */
	public static final int TELEPHONE = 8;

	/**
	 * 
	 */
	public static final int ERPCODE = 9;

	/**
	 * 
	 */
	private static final long serialVersionUID = -9114972771888008632L;

	/**
	 * 属性是否隐藏
	 */
	private Boolean hidden;

	/**
	 * 是否为多值属性
	 */
	private Boolean multivalued;

	/**
	 * 属性名
	 */
	private String name;

	/**
	 * 排序号
	 */
	private Long order;

	/**
	 * 属性对应的资源类型
	 */
	private Integer resourceType;

	/**
	 * 主键
	 */
	private String uuid;

	/**
	 * 属性类型所属的组
	 */
	private Set<Group> groups;

	/**
	 * 属性被管理的组
	 */
	private Set<Group> adminGroups;

	/**
	 * 属性编码
	 */
	private String id;

	/**
	 * 页面展示属性
	 */
	private String pageType;

	/**
	 * 候选项
	 */
	private List<CandidateItem> candidateItems;

	/**
	 * 扩展属性类别
	 */
	private String catagory;

	/**
	 * 扩展属性的规则（正则）
	 */
	private String rule;

	/**
	 * 扩展属性的长度
	 */
	private Long length;

	/**
	 * 正则表达式
	 */
	private String value;

	public String getValue()
	{
		return value;
	}

	protected void setValue(String value)
	{
		this.value = value;
	}

	public String getRule()
	{
		return rule;
	}

	/**
	 * 方法说明：setRule
	 *
	 * @param rule rule
	 */
	public void setRule(String rule)
	{
		String ruleStr = null;
		if (StringUtils.isBlank(rule)) {
			rule = ""+NORMAL;
		}
		int iRule = Integer.valueOf(rule);
		switch (iRule) {
		case NUMBER:
			ruleStr = "^[\\d|\\.]+$";
			break;
		case DATE:
			ruleStr = "^(?:[0-9]{2})?[0-9]{2}[-|/|\\.](1[0-2]|0?[1-9])[-|/|\\.](3[01]|[12][0-9]|0?[1-9])$";
			break;
		case EMPLOYEENUMBER:
			ruleStr = "^(\\d{15})$|^(\\d{17}([\\d|X]))$";
			break;
		case WORD:
			ruleStr = "^[a-zA-Z.\\s]+$";
			break;
		case W_N:
			ruleStr = "[\\d|\\w|\\.]+";
			break;
		case EMAIL:
			ruleStr = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)";
			break;
		case CHINESE:
			ruleStr = "^[\\u4e00-\\u9fa5]*$";
			break;
		case TELEPHONE:
			ruleStr = "^[0-9-._]+$";
			break;
		case ERPCODE:
			ruleStr = "^(\\d{8})$|^(\\d{10})$";
			break;
		default:
			break;
		}
		this.rule = rule;
		setValue(ruleStr);
	}

	public Long getLength()
	{
		return length;
	}

	public void setLength(Long length)
	{
		this.length = length;
	}

	public String getCatagory()
	{
		return catagory;
	}

	public void setCatagory(String catagory)
	{
		this.catagory = catagory;
	}

	public List<CandidateItem> getCandidateItems()
	{
		return candidateItems;
	}

	public void setCandidateItems(List<CandidateItem> candidateItems)
	{
		this.candidateItems = candidateItems;
	}

	public String getPageType()
	{
		return pageType;
	}

	public void setPageType(String pageType)
	{
		this.pageType = pageType;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Set<Group> getGroups()
	{
		return groups;
	}

	public void setGroups(Set<Group> groups)
	{
		this.groups = groups;
	}

	public Set<Group> getAdminGroups()
	{
		return adminGroups;
	}

	public void setAdminGroups(Set<Group> adminGroups)
	{
		this.adminGroups = adminGroups;
	}

	/**
	 * 
	 */
	public AttributeType()
	{
		super();
		multivalued = false;
		hidden = false;
		order = 0L;
	}

	public Boolean getHidden()
	{
		return hidden;
	}

	public Boolean getMultivalued()
	{
		return multivalued;
	}

	public String getName()
	{
		return name;
	}

	public Long getOrder()
	{
		return order;
	}

	public Integer getResourceType()
	{
		return resourceType;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setHidden(Boolean hidden)
	{
		this.hidden = hidden;
	}

	public void setMultivalued(Boolean multivalued)
	{
		this.multivalued = multivalued;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setOrder(Long order)
	{
		this.order = order;
	}

	public void setResourceType(Integer resourceType)
	{
		this.resourceType = resourceType;
	}

	@SuppressWarnings("unused")
	private void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
}
