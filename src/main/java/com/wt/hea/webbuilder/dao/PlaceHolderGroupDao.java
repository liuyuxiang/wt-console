package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.webbuilder.model.PlaceHolderGroup;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 模板权限控制Dao
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface PlaceHolderGroupDao extends EntityDao<PlaceHolderGroup>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(PlaceHolderGroup e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return List<PlaceHolderGroup>
	 */
	public List<PlaceHolderGroup> findAll();

	/**
	 * @param id
	 *            id
	 * @return PlaceHolderGroup
	 */
	public PlaceHolderGroup findById(Serializable id);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<PlaceHolderGroup>
	 */
	public List<PlaceHolderGroup> findByProperty(String property, Object value);

	/**
	 * @param e
	 *            e
	 * @return PlaceHolderGroup
	 */
	public PlaceHolderGroup update(PlaceHolderGroup e);

	/**
	 * 方法说明： 根据模板id和组id获取占位符信息
	 * 
	 * @param groupId
	 *            groupid
	 * @param tmplId
	 *            tmplId
	 * @return 占位符信息列表
	 */
	public List<PlaceHolderGroup> findByGroupAndTmpl(String groupId, String tmplId);
}
