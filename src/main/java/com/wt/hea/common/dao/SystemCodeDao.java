package com.wt.hea.common.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.common.model.SystemCode;

/**
 * 
 * <pre>
 * 业务名:CRUD 系统常量 接口(维护应用系统的最高常量值,以及值义)
 * 功能说明: CRUD 系统常量 接口(维护应用系统的最高常量值,以及值义)
 * 编写日期:	2011-5-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public interface SystemCodeDao extends EntityDao<SystemCode>
{
	/**
	 * 方法说明: 添加一条常量表记录
	 * 
	 * @param systemCode
	 *            常量实体
	 * @return boolean
	 */
	public boolean save(SystemCode systemCode);

	/**
	 * 方法说明：删除系统常量模型
	 * 
	 * @param systemCode
	 *            常量实体
	 */
	public void delete(SystemCode systemCode);

	/**
	 * 方法说明: 根据id查找常量表记录
	 * 
	 * @param id
	 *            常量实体id
	 * @return 常量模型
	 */
	public SystemCode findById(Serializable id);

	/**
	 * 方法说明：查找所有常量表记录
	 * 
	 * @return 返回list集合
	 */
	public List<SystemCode> findAll();

	/**
	 * 方法说明：更常量表一条记录
	 * 
	 * @param systemCode
	 *            系统编码模型
	 * @return 返回更新后的常量对象
	 */
	public SystemCode update(SystemCode systemCode);

	/**
	 * 
	 * 方法说明：根据系统常量范例查询记录
	 * 
	 * @param obj
	 *            常量实体范例对象
	 * @return 返回结果集
	 */
	public List<SystemCode> findByExample(SystemCode obj);

	/**
	 * 
	 * 方法说明：分页加载系统全局常量
	 * 
	 * @param pageModel
	 *            pageModel
	 * @return Page<SystemCode>
	 */
	public Page<SystemCode> loadPage(Page<SystemCode> pageModel);
}