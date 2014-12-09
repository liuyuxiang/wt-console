package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.LayoutDefinition;
import com.wt.hea.webbuilder.service.LayoutDefinitionService;

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
public class LayoutDefinitionServiceImpl extends BaseService implements LayoutDefinitionService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(LayoutDefinition e)
	{
		this.layoutDefinitionDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.layoutDefinitionDao.deleteById(id);
	}

	/**
	 * @return List<LayoutDefinition>
	 */
	public List<LayoutDefinition> findAll()
	{
		return this.layoutDefinitionDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<LayoutDefinition>
	 */
	public List<LayoutDefinition> findAll(String property, Boolean isAsc)
	{
		return this.layoutDefinitionDao.findAll(property, isAsc);
	}

	/**
	 * @param id
	 *            id
	 * @return LayoutDefinition
	 */
	public LayoutDefinition findById(Serializable id)
	{
		return this.layoutDefinitionDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<LayoutDefinition>
	 */
	public List<LayoutDefinition> findByProperty(String property, Object value)
	{
		return this.layoutDefinitionDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 * 
	 */
	public void save(LayoutDefinition e)
	{
		this.layoutDefinitionDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return LayoutDefinition
	 */
	public LayoutDefinition update(LayoutDefinition e)
	{
		return this.layoutDefinitionDao.update(e);
	}

}
