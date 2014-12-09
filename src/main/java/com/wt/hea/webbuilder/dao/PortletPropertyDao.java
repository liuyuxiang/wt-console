package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.PortletProperty;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: portlet管理Service
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface PortletPropertyDao extends EntityDao<PortletProperty>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(PortletProperty e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return List<PortletProperty>
	 */
	public List<PortletProperty> findAll();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<PortletProperty>
	 */
	public List<PortletProperty> findAll(String property, Boolean isAsc);

	/**
	 * @param id
	 *            id
	 * @return PortletProperty
	 */
	public PortletProperty findById(Serializable id);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<PortletProperty>
	 */
	public List<PortletProperty> findByProperty(String property, Object value);

	/**
	 * @param e
	 *            e
	 * @return PortletProperty
	 */
	public PortletProperty update(PortletProperty e);

	/**
	 * @param page
	 *            page
	 * @return Page<PortletProperty>
	 */
	public Page<PortletProperty> loadPage(Page<PortletProperty> page);
}
