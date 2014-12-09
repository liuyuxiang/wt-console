package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.ThemeDefinition;
import com.wt.hea.webbuilder.service.ThemeDefinitionService;

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
public class ThemeDefinitionServiceImpl extends BaseService implements ThemeDefinitionService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(ThemeDefinition e)
	{
		this.themeDefinitionDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.themeDefinitionDao.deleteById(id);
	}

	/**
	 * @return List<ThemeDefinition>
	 */
	public List<ThemeDefinition> findAll()
	{
		return this.themeDefinitionDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<ThemeDefinition>
	 */
	public List<ThemeDefinition> findAll(String property, Boolean isAsc)
	{
		return this.themeDefinitionDao.findAll(property, isAsc);
	}

	/**
	 * @param id
	 *            id
	 * @return ThemeDefinition
	 */
	public ThemeDefinition findById(Serializable id)
	{
		return this.themeDefinitionDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<ThemeDefinition>
	 */
	public List<ThemeDefinition> findByProperty(String property, Object value)
	{
		return this.themeDefinitionDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(ThemeDefinition e)
	{
		this.themeDefinitionDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return ThemeDefinition
	 */
	public ThemeDefinition update(ThemeDefinition e)
	{
		return this.themeDefinitionDao.update(e);
	}

	/**
	 * @param pageModel
	 *            pageModel
	 * @return Page<ThemeDefinition>
	 */
	public Page<ThemeDefinition> loadPage(Page<ThemeDefinition> pageModel)
	{
		// TODO Auto-generated method stub
		return this.themeDefinitionDao.loadPage(pageModel);
	}

}
