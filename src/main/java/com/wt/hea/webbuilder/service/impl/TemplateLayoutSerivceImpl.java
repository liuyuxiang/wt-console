package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.TemplateLayout;
import com.wt.hea.webbuilder.service.TemplateLayoutService;

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
public class TemplateLayoutSerivceImpl extends BaseService implements TemplateLayoutService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(TemplateLayout e)
	{
		this.templateLayoutDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.templateLayoutDao.deleteById(id);
	}

	/**
	 * @return List<TemplateLayout>
	 */
	public List<TemplateLayout> findAll()
	{
		return this.templateLayoutDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<TemplateLayout>
	 */
	public List<TemplateLayout> findByProperty(String property, Object value)
	{
		return this.templateLayoutDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(TemplateLayout e)
	{
		this.templateLayoutDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return TemplateLayout
	 */
	public TemplateLayout update(TemplateLayout e)
	{
		return this.templateLayoutDao.update(e);
	}

	/**
	 * @param id
	 *            id
	 * @return TemplateLayout
	 */
	public TemplateLayout findById(Serializable id)
	{
		// TODO Auto-generated method stub
		return this.templateLayoutDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return TemplateLayout
	 */
	public TemplateLayout findByPropertyUnique(String property, Object value)
	{

		return this.templateLayoutDao.findByPropertyUnique(property, value);
	}

	/**
	 * @param pageModel
	 *            pageModel
	 * @return Page<TemplateLayout>
	 */
	public Page<TemplateLayout> loadPage(Page<TemplateLayout> pageModel)
	{

		return this.templateLayoutDao.loadPage(pageModel);
	};

}
