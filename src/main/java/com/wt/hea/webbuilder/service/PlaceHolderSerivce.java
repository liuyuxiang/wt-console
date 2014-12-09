package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

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
public interface PlaceHolderSerivce
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(PlaceHolder e);

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id);

	/**
	 * @return List<PlaceHolder>
	 */
	public List<PlaceHolder> findAll();

	/**
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
	 */
	public void save(PlaceHolder e);

	/**
	 * @param e
	 *            e
	 * @return PlaceHolder
	 */
	public PlaceHolder update(PlaceHolder e);
}
