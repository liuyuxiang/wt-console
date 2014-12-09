package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.ThemeDefinition;

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
public interface ThemeDefinitionDao extends EntityDao<ThemeDefinition>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(ThemeDefinition e);

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
	 * @return List<ThemeDefinition>
	 */
	public List<ThemeDefinition> findAll();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<ThemeDefinition>
	 */
	public List<ThemeDefinition> findAll(String property, Boolean isAsc);

	/**
	 * @param id
	 *            id
	 * @return ThemeDefinition
	 */
	public ThemeDefinition findById(Serializable id);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<ThemeDefinition>
	 */
	public List<ThemeDefinition> findByProperty(String property, Object value);

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(ThemeDefinition e);

	/**
	 * @param e
	 *            e
	 * @return ThemeDefinition
	 */
	public ThemeDefinition update(ThemeDefinition e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param pageModel
	 *            pageModel
	 * @return Page<ThemeDefinition>
	 */
	public Page<ThemeDefinition> loadPage(Page<ThemeDefinition> pageModel);
}
