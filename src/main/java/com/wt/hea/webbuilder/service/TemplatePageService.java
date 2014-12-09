package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.TemplatePage;
import com.hirisun.hea.api.domain.User;

/**
 * 模板页面操作对象的接口定义
 * @author xiaoqi
 *
 */
public interface TemplatePageService {
	
	/**
	 * 方法说明：
	 *删除模板页面
	 * @param e TemplatePage对象
	 */
	public void delete(TemplatePage e);
	
	/**
	 * 方法说明：
	 *根据id删除TemplatePage信息
	 * @param id TemplatePage对象的Id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 *查找所有的TemplatePage对象
	 * @return TemplatePage对象列表
	 */
	public List<TemplatePage> findAll();
	
	/**
	 * 方法说明：
	 *根据id查找TemplatePage对象
	 * @param id TemplatePage对象Id
	 * @return TemplatePage对象
	 */
	public TemplatePage findById(Serializable id);
	
	/**
	 * 方法说明：
	 *根据属性查找TemplatePage信息
	 * @param property TemplatePage的属性
	 * @param value TemplatePage属性对应的值
	 * @return TemplatePage对象列表
	 */
	public List<TemplatePage> findByProperty(String property, Object value);
	
	/**
	 * 方法说明：
	 *获取TemplatePage分页信息
	 * @param pageModel 分页对象
	 * @return TemplatePage分页信息
	 */
	public Page<TemplatePage> loadPage(Page<TemplatePage> pageModel);
	
	/**
	 * 方法说明：
	 *保存TemplatePage信息
	 * @param e TemplatePage对象
	 */
	public void save(TemplatePage e);
	
	/**
	 * 方法说明：
	 *更新TemplatePage信息
	 * @param e TemplatePage对象
	 * @return TemplatePage对象
	 */
	public TemplatePage update(TemplatePage e);
	
	/**
	 * 方法说明：
	 *根据TemplatePage的属性排序后的所有TemplatePage信息
	 * @param property TemplatePage的属性
	 * @param isAsc 是否是升序
	 * @return TemplatePage对象列表
	 */
	public List<TemplatePage> findAll(String property, Boolean isAsc);
	
	/**
	 * 方法说明：
	 * 保存模板页面信息
	 * @param pageName 页面名称
	 * @param tmplId 模板id
	 * @param siteId 站点id
	 * @param themeCode 主题代码
	 * @param user 用户
	 */
	public void saveTemplate (String pageName ,String tmplId ,String siteId ,String themeCode ,User user);
	
	/**
	 * 方法说明：
	 * 根据站点id和页面名称查找TemplatePage信息
	 * @param siteId 站点id
	 * @param pageName 页面名称
	 * @return TemplatePage对象
	 */
	public TemplatePage findBySiteIdAndPageName(String siteId, String pageName);
}
