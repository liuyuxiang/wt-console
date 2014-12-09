package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.TemplatePage;

/**
 * <pre>
 * 业务名:
 * 功能说明: 模板页面dao
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface TemplatePageDao extends EntityDao<TemplatePage>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(TemplatePage e);

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
	 * @return List<TemplatePage>
	 */
	public List<TemplatePage> findAll();

	/**
	 * @param id
	 *            id
	 * @return TemplatePage
	 */
	public TemplatePage findById(Serializable id);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<TemplatePage>
	 */
	public List<TemplatePage> findByProperty(String property, Object value);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param pageModel
	 *            pageModel
	 * @return Page<TemplatePage>
	 */
	public Page<TemplatePage> loadPage(Page<TemplatePage> pageModel);

	/**
	 * @param e
	 *            e
	 * 
	 * @return boolean
	 */
	public boolean save(TemplatePage e);

	/**
	 * @param e
	 *            e
	 * @return TemplatePage
	 */
	public TemplatePage update(TemplatePage e);

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<TemplatePage>
	 */
	public List<TemplatePage> findAll(String property, Boolean isAsc);

	/**
	 * 方法说明： 根据站点id和页面名称查找页面信息
	 * 
	 * @param siteId
	 *            站点id
	 * @param pageName
	 *            页面名称
	 * @return 页面信息
	 */
	public TemplatePage findBySiteIdAndPageName(String siteId, String pageName);

}
