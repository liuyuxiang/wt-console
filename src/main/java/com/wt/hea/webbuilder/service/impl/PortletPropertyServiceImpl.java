package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.PortletProperty;
import com.wt.hea.webbuilder.service.PortletPropertyService;

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
public class PortletPropertyServiceImpl extends BaseService implements PortletPropertyService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(PortletProperty e)
	{
		this.portletPropertyDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id)
	{
		return this.portletPropertyDao.deleteById(id);
	}

	/**
	 * @return List<PortletProperty>
	 */
	public List<PortletProperty> findAll()
	{
		return this.portletPropertyDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<PortletProperty>
	 */
	public List<PortletProperty> findAll(String property, Boolean isAsc)
	{
		return this.portletPropertyDao.findAll(property, isAsc);
	}

	/**
	 * @param id
	 *            id
	 * @return PortletProperty
	 */
	public PortletProperty findById(Serializable id)
	{
		return this.portletPropertyDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<PortletProperty>
	 */
	public List<PortletProperty> findByProperty(String property, Object value)
	{
		return this.portletPropertyDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(PortletProperty e)
	{
		return this.portletPropertyDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return PortletProperty
	 */
	public PortletProperty update(PortletProperty e)
	{
		return this.portletPropertyDao.update(e);
	}

	/**
	 * @param page
	 *            page
	 * @return Page<PortletProperty>
	 */
	public Page<PortletProperty> loadPage(Page<PortletProperty> page)
	{
		return this.portletPropertyDao.loadPage(page);
	}

}
