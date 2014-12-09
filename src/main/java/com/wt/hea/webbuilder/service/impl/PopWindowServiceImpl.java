package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;

import com.wt.hea.webbuilder.model.PopWindow;
import com.wt.hea.webbuilder.service.PopWindowService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-20
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PopWindowServiceImpl extends BaseService implements PopWindowService
{

	/**
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id)
	{
		// TODO Auto-generated method stub
		return this.popWindowDao.deleteById(id);
	}

	/**
	 * @param id
	 *            id
	 * @return PopWindow
	 */
	public PopWindow findById(Serializable id)
	{
		// TODO Auto-generated method stub
		return this.popWindowDao.findById(id);
	}

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(PopWindow e)
	{
		// TODO Auto-generated method stub
		return this.popWindowDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return PopWindow
	 */
	public PopWindow update(PopWindow e)
	{
		// TODO Auto-generated method stub
		return this.popWindowDao.update(e);
	}

	/**
	 * @param topId
	 *            topId
	 * @return PopWindow
	 */
	public PopWindow findByTopId(String topId)
	{

		return this.popWindowDao.findByTopId(topId);
	}
}
