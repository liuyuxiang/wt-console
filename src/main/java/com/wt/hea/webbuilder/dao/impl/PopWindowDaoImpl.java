package com.wt.hea.webbuilder.dao.impl;

import java.io.Serializable;

import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.PopWindowDao;
import com.wt.hea.webbuilder.model.PopWindow;

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
public class PopWindowDaoImpl extends AbstractHibernateDaoSupport<PopWindow> implements
		PopWindowDao
{

	/**
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id)
	{
		// TODO Auto-generated method stub
		return super.deleteById(id);
	}

	/**
	 * @param id
	 *            id
	 * @return PopWindow
	 */
	public PopWindow findById(Serializable id)
	{
		// TODO Auto-generated method stub
		return super.findById(id);
	}

	/**
	 * @param e
	 *            e
	 * 
	 * @return boolean
	 */
	public boolean save(PopWindow e)
	{
		// TODO Auto-generated method stub
		return super.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return PopWindow
	 */
	public PopWindow update(PopWindow e)
	{
		// TODO Auto-generated method stub
		return super.update(e);
	}

	/**
	 * @param topId
	 *            topId
	 * @return PopWindow
	 */
	public PopWindow findByTopId(String topId)
	{
		PopWindow pop = (PopWindow) this.getSession().createCriteria(PopWindow.class)
				.add(Restrictions.eq("topId", topId)).uniqueResult();
		return pop;
	}

}
