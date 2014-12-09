package com.wt.hea.webbuilder.dao.impl;

import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.TemplatePageDao;
import com.wt.hea.webbuilder.model.TemplatePage;

/**
 * 模板页面数据访问对象
 * 
 * @author xiaoqi
 * 
 */
public class TemplatePageDaoImpl extends AbstractHibernateDaoSupport<TemplatePage> implements TemplatePageDao {

	/**
	 * 根据siteId和pageName查找到唯一的一个页面
	 * 
	 * @param siteId
	 *            siteId
	 * @param pageName
	 *            pageName
	 * @return 根据siteId和pageName查找到唯一的一个页面
	 */
	public TemplatePage findBySiteIdAndPageName(String siteId, String pageName) {
		TemplatePage tp = (TemplatePage) this.getSession().createCriteria(TemplatePage.class).add(Restrictions.eq("siteId", siteId))
				.add(Restrictions.eq("pageName", pageName)).uniqueResult();
		return tp;
	}

}
