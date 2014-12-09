package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.PersonalSystemParameter;

/**
 * 
 * <pre>
 * 业务名:展现定制Service
 * 功能说明: 
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface PersonalSystemParameterService {

	/**
	 * 
	 * 方法说明：
	 * 删除一条系统参数
	 *
	 * @param e 系统参数对象
	 */
	public void delete(PersonalSystemParameter e);

	/**
	 * 
	 * 方法说明：
	 * 根据id删除个性化信息
	 * @param id PersonalSystemParameter对象Id
	 */
	public void deleteById(Serializable id);

	/**
	 * 方法说明：
	 * 查找所有参数信息
	 * @return 参数信息
	 */
	public List<PersonalSystemParameter> findAll();

	/**
	 * 方法说明：
	 *查找所有个性化参数信息并根据property属性排序
	 * @param property 个性化信息 的property
	 * @param isAsc 是否是升序
	 * @return 个性化信息列表
	 */
	public List<PersonalSystemParameter> findAll(String property, Boolean isAsc);

	/**
	 * 方法说明：
	 * 根据id查找个性化信息
	 * @param id 个性化信息id
	 * @return PersonalSystemParameter对象
	 */
	public PersonalSystemParameter findById(Serializable id);

	/**
	 * 方法说明：
	 *根据属性查找PersonalSystemParameter
	 * @param property PersonalSystemParameter的property
	 * @param value PersonalSystemParameter的property值
	 * @return 符合条件的PersonalSystemParameter对象列表
	 */
	public List<PersonalSystemParameter> findByProperty(String property,
			Object value);

	/**
	 * 保存个性化信息
	 * @param e 个性化信息对象PersonalSystemParameter
	 */
	public void save(PersonalSystemParameter e);

	/**
	 * 方法说明：
	 *更新个性化信息
	 * @param e PersonalSystemParameter对象
	 * @return PersonalSystemParameter对象
	 */
	public PersonalSystemParameter update(PersonalSystemParameter e);
	/**
	 * 包含的ids
	 * @param pspids 个性化参数ids
	 * @return 个性化信息列表
	 */
	public List<PersonalSystemParameter> findByIdsIn (String [] pspids );
	
	/**
	 * 不包含ids的
	 * @param pspids 个性化参数ids
	 * @param codeType 参数类型
	 * @return 个性化信息列表
	 */
	public List<PersonalSystemParameter> findByIdsOut (String [] pspids ,String codeType[]);
	
	/**
	 * 
	 * 方法说明：
	 *根据类型查找个性化信息
	 * @param codetype 个性化信息类型代码
	 * @return PersonalSystemParameter对象列表
	 */
	public List<PersonalSystemParameter> findByType(String [] codetype);

}
