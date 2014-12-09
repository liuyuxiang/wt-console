package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.webbuilder.model.PersonalSystemParameter;

/**
 * <pre>
 * 业务名:
 * 功能说明: 个性化系统参数Dao
 * 编写日期:	2011-3-25
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface PersonalSystemParameterDao extends
		EntityDao<PersonalSystemParameter> {

	/**
	 * 删除个性化系统参数
	 * @param e 个性化系统参数
	 */
	public void delete(PersonalSystemParameter e);

	
	/**
	 * 查找所有个性化系统参数
	 * @return 个性化系统参数列表
	 */
	public List<PersonalSystemParameter> findAll();
	
	/**
	 * 根据property属性按isAsc排序后的所有个性化系统参数
	 * @param property 排序字段
	 * @param isAsc 是否升序true or false
	 * @return 排序后的个性化参数列表
	 */
	public List<PersonalSystemParameter> findAll(String property, Boolean isAsc);
	
	/**
	 * 根据id查询个性化系统参数
	 * @param id 个性化系统参数id
	 * @return 个性化系统参数对象
	 */
	public PersonalSystemParameter findById(Serializable id);
	
	/**
	 * 根据property的值查询符合条件的个性化参数对象
	 * @param property 查询条件属性
	 * @param value 查询条件属性值
	 * @return 系统全局参数对象列表
	 */
	public List<PersonalSystemParameter> findByProperty(String property,
			Object value);
	
	/**
	 * @param e 个性化系统参数对象
	 * @return 更新后的个性化系统参数对象
	 */
	public PersonalSystemParameter update(PersonalSystemParameter e);
	
	/**
	 * 
	 * 方法说明：
	 *	根据ids返回已经包含在ids里的个性化参数列表
	 * @param pspids 个性化参数ids
	 * @return 已经包含在ids里的个性化参数列表
	 */
	public List<PersonalSystemParameter> findByIdsIn (String [] pspids );
	
	/**
	 * 
	 * 方法说明：
	 *	根据pspids和类型返回不包含在ids里的个性化参数列表
	 * @param pspids 个性化参数ids
	 * @param codeType 系统参数类型
	 * @return 返回不包含在ids里的个性化参数列表
	 */
	public List<PersonalSystemParameter> findByIdsOut (String [] pspids , String codeType[]);
	
	/**
	 * 
	 * 方法说明：
	 *	根据类型查询系统全局参数
	 * @param codetype 系统全局参数类型
	 * @return 系统全局参数列表
	 */
	public List<PersonalSystemParameter> findByType(String [] codetype);
}
