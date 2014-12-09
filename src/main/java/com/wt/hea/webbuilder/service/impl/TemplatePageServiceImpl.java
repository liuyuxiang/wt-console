package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.common.util.BeanHelper;
import com.wt.hea.webbuilder.model.PlaceHolder;
import com.wt.hea.webbuilder.model.TemplateLayout;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;
import com.wt.hea.webbuilder.model.ThemeDefinition;
import com.wt.hea.webbuilder.service.TemplatePageService;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-22
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class TemplatePageServiceImpl extends BaseService implements TemplatePageService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(TemplatePage e)
	{
		this.templatePageDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.templatePageDao.deleteById(id);
	}

	/**
	 * @return List<TemplateLayout>
	 */
	public List<TemplatePage> findAll()
	{
		return this.templatePageDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<TemplateLayout>
	 */
	public List<TemplatePage> findAll(String property, Boolean isAsc)
	{
		return this.templatePageDao.findAll(property, isAsc);
	}

	/**
	 * @param id
	 *            id
	 * @return TemplatePage
	 */
	public TemplatePage findById(Serializable id)
	{
		return this.templatePageDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<TemplatePage>
	 */
	public List<TemplatePage> findByProperty(String property, Object value)
	{
		return this.templatePageDao.findByProperty(property, value);
	}

	/**
	 * @param pageModel
	 *            pageModel
	 * @return Page<TemplatePage>
	 */
	public Page<TemplatePage> loadPage(Page<TemplatePage> pageModel)
	{

		return this.templatePageDao.loadPage(pageModel);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(TemplatePage e)
	{
		this.templatePageDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return TemplatePage
	 */
	public TemplatePage update(TemplatePage e)
	{
		return this.templatePageDao.update(e);
	}

	/**
	 * 根据siteId和pageName查找到唯一的一个页面
	 * 
	 * @param siteId
	 *            siteId
	 * @param pageName
	 *            pageName
	 * @return TemplatePage
	 */
	public TemplatePage findBySiteIdAndPageName(String siteId, String pageName)
	{
		return this.templatePageDao.findBySiteIdAndPageName(siteId, pageName);
	}

	/**
	 * @param pageName
	 *            pageName
	 * @param tmplId
	 *            tmplId
	 * @param siteId
	 *            siteId
	 * @param themeCode
	 *            themeCode
	 * @param user
	 *            user
	 */
	public void saveTemplate(String pageName, String tmplId, String siteId, String themeCode,
			User user)
	{
		TemplatePage tp = new TemplatePage();
		tp.setPageName(pageName);
		tp.setTmplId(tmplId);
		tp.setSiteId(siteId);
		tp.setUserNo(user.getUuid());
		tp.setPubTime(new Date());
		TemplateLayout tl = this.templateLayoutDao.findById(tmplId);
		List<PlaceHolder> phList = tl.getPlaceHolderList();
		List<TemplatePortletInfo> tpiList = new ArrayList<TemplatePortletInfo>();
		tp.setTemplateLayout(tl);
		ThemeDefinition themeDef = this.themeDefinitionDao.findById(themeCode);
		tp.setThemeCode(themeDef.getThemeCode());
		this.templatePageDao.save(tp);
		for (PlaceHolder ph : phList) {
			TemplatePortletInfo tpi = new TemplatePortletInfo();
			try {
				BeanHelper.copyProperties(tpi, ph);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tpi.setTemplatePageInfo(tp);
			tpiList.add(tpi);
			this.templatePortletInfoDao.save(tpi);
		}
	}

}
