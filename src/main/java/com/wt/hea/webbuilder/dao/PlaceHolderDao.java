package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.webbuilder.model.PlaceHolder;

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
public interface PlaceHolderDao extends EntityDao<PlaceHolder>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(PlaceHolder e);

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
	 * @return List<PlaceHolder>
	 */
	public List<PlaceHolder> findAll();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<PlaceHolder>
	 */
	public List<PlaceHolder> findByProperty(String property, Object value);

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(PlaceHolder e);

	/**
	 * @param e
	 *            e
	 * @return PlaceHolder
	 */
	public PlaceHolder update(PlaceHolder e);
}
