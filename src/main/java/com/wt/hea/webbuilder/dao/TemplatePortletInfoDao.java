package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-20
 * 作者:	Mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface TemplatePortletInfoDao extends EntityDao<TemplatePortletInfo>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(TemplatePortletInfo e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return List<TemplatePortletInfo>
	 */
	public List<TemplatePortletInfo> findAll();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param id
	 *            id
	 * @return TemplatePortletInfo
	 */
	public TemplatePortletInfo findById(Serializable id);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<TemplatePortletInfo>
	 */
	public List<TemplatePortletInfo> findByProperty(String property, Object value);

	/**
	 * 方法说明： 根据TemplatePortletInfo的属性查找TemplatePortletInfo对象
	 * 
	 * @param property
	 *            TemplatePortletInfo的属性
	 * @param value
	 *            property对应的属性值
	 * @return TemplatePortletInfo对象
	 */
	public TemplatePortletInfo findByPropertyUnique(String property, Object value);

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(TemplatePortletInfo e);

	/**
	 * @param e
	 *            e
	 * @return TemplatePortletInfo
	 */
	public TemplatePortletInfo update(TemplatePortletInfo e);

}
