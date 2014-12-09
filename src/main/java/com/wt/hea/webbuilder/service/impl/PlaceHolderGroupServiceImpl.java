package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.PlaceHolderGroup;
import com.wt.hea.webbuilder.service.PlaceHolderGroupService;

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
public class PlaceHolderGroupServiceImpl extends BaseService implements PlaceHolderGroupService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(PlaceHolderGroup e)
	{
		this.placeHolderGroupDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.placeHolderGroupDao.deleteById(id);
	}

	/**
	 * @return List<PlaceHolderGroup>
	 */
	public List<PlaceHolderGroup> findAll()
	{
		return this.placeHolderGroupDao.findAll();
	}

	/**
	 * @param groupId
	 *            groupId
	 * @param tmplId
	 *            tmplId
	 * @return List<PlaceHolderGroup>
	 */
	public List<PlaceHolderGroup> findByGroupAndTmpl(String groupId, String tmplId)
	{
		return this.placeHolderGroupDao.findByGroupAndTmpl(groupId, tmplId);
	}

	/**
	 * @param id
	 *            id
	 * @return PlaceHolderGroup
	 */
	public PlaceHolderGroup findById(Serializable id)
	{
		return this.placeHolderGroupDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<PlaceHolderGroup>
	 */
	public List<PlaceHolderGroup> findByProperty(String property, Object value)
	{
		return this.placeHolderGroupDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(PlaceHolderGroup e)
	{
		this.placeHolderGroupDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return PlaceHolderGroup
	 */
	public PlaceHolderGroup update(PlaceHolderGroup e)
	{
		return this.placeHolderGroupDao.update(e);
	}

}
