package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.TemplatePortletInfo;
import com.wt.hea.webbuilder.service.TemplatePortletInfoService;

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
public class TemplatePortletInfoServiceImpl extends BaseService implements
		TemplatePortletInfoService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(TemplatePortletInfo e)
	{
		this.templatePortletInfoDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.templatePortletInfoDao.deleteById(id);
	}

	/**
	 * @return List<TemplatePortletInfo>
	 */
	public List<TemplatePortletInfo> findAll()
	{
		return this.templatePortletInfoDao.findAll();
	}

	/**
	 * @param id
	 *            id
	 * @return TemplatePortletInfo
	 */
	public TemplatePortletInfo findById(Serializable id)
	{
		return this.templatePortletInfoDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<TemplatePortletInfo>
	 */
	public List<TemplatePortletInfo> findByProperty(String property, Object value)
	{
		return this.templatePortletInfoDao.findByProperty(property, value);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return TemplatePortletInfo
	 */
	public TemplatePortletInfo findByPropertyUnique(String property, Object value)
	{
		return this.templatePortletInfoDao.findByPropertyUnique(property, value);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(TemplatePortletInfo e)
	{
		this.templatePortletInfoDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return TemplatePortletInfo
	 */
	public TemplatePortletInfo update(TemplatePortletInfo e)
	{
		return this.templatePortletInfoDao.update(e);
	}

}
