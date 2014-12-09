package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.TemplateLayout;

/**
 * <pre>
 * 业务名:
 * 功能说明: 模板布局Dao
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface TemplateLayoutDao extends EntityDao<TemplateLayout>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(TemplateLayout e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return List<TemplateLayout>
	 */
	public List<TemplateLayout> findAll();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<TemplateLayout>
	 */
	public List<TemplateLayout> findByProperty(String property, Object value);

	/**
	 * 方法说明： 根据属性查找templatelayout对象
	 * 
	 * @param property
	 *            template对象的属性
	 * @param value
	 *            property对应的值
	 * @return templatelayout对象
	 */
	public TemplateLayout findByPropertyUnique(String property, Object value);

	/**
	 * @param id
	 *            id
	 * @return TemplateLayout
	 */
	public TemplateLayout findById(Serializable id);

	/**
	 * @param e
	 *            e
	 * @return TemplateLayout
	 */
	public TemplateLayout update(TemplateLayout e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param pageModel
	 *            pageModel
	 * @return Page<TemplateLayout>
	 */
	public Page<TemplateLayout> loadPage(Page<TemplateLayout> pageModel);
}
