package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.PlaceHolder;
import com.wt.hea.webbuilder.service.PlaceHolderSerivce;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-29
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PlaceHolderServiceImpl extends BaseService implements PlaceHolderSerivce
{

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.placeHolderDao.deleteById(id);
	}

	/**
	 * @return List<PlaceHolder>
	 */
	public List<PlaceHolder> findAll()
	{
		return this.placeHolderDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<PlaceHolder>
	 */
	public List<PlaceHolder> findByProperty(String property, Object value)
	{
		return this.placeHolderDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(PlaceHolder e)
	{
		this.placeHolderDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return PlaceHolder
	 */
	public PlaceHolder update(PlaceHolder e)
	{
		return this.placeHolderDao.update(e);
	}

	/**
	 * @param e
	 *            e
	 */
	public void delete(PlaceHolder e)
	{
		this.placeHolderDao.delete(e);
	}

}
