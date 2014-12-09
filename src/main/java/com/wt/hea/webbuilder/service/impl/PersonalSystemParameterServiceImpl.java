package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.PersonalSystemParameter;
import com.wt.hea.webbuilder.service.PersonalSystemParameterService;

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
public class PersonalSystemParameterServiceImpl extends BaseService implements
		PersonalSystemParameterService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(PersonalSystemParameter e)
	{
		this.personalSystemParameterDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.personalSystemParameterDao.deleteById(id);
	}

	/**
	 * @return List<PersonalSystemParameter>
	 */
	public List<PersonalSystemParameter> findAll()
	{
		return this.personalSystemParameterDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<PersonalSystemParameter>
	 */
	public List<PersonalSystemParameter> findAll(String property, Boolean isAsc)
	{
		return this.personalSystemParameterDao.findAll(property, isAsc);
	}

	/**
	 * @param id
	 *            id
	 * @return PersonalSystemParameter
	 */
	public PersonalSystemParameter findById(Serializable id)
	{
		return this.personalSystemParameterDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<PersonalSystemParameter>
	 */
	public List<PersonalSystemParameter> findByProperty(String property, Object value)
	{
		return this.personalSystemParameterDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 * 
	 */
	public void save(PersonalSystemParameter e)
	{
		this.personalSystemParameterDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return PersonalSystemParameter
	 */
	public PersonalSystemParameter update(PersonalSystemParameter e)
	{
		return this.personalSystemParameterDao.update(e);
	}

	/**
	 * @param pspids
	 *            pspids
	 * @return List<PersonalSystemParameter>
	 */
	public List<PersonalSystemParameter> findByIdsIn(String[] pspids)
	{
		// TODO Auto-generated method stub
		return this.personalSystemParameterDao.findByIdsIn(pspids);
	}

	/**
	 * @param pspids
	 *            pspids
	 * @param codeType
	 *            codeType
	 * @return List<PersonalSystemParameter>
	 */
	public List<PersonalSystemParameter> findByIdsOut(String[] pspids, String codeType[])
	{
		// TODO Auto-generated method stub
		return this.personalSystemParameterDao.findByIdsOut(pspids, codeType);
	}

	/**
	 * @param codetype
	 *            codeType
	 * @return List<PersonalSystemParameter>
	 */
	public List<PersonalSystemParameter> findByType(String[] codetype)
	{

		return this.personalSystemParameterDao.findByType(codetype);
	}
}
