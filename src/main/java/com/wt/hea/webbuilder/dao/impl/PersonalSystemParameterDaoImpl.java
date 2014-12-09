package com.wt.hea.webbuilder.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.PersonalSystemParameterDao;
import com.wt.hea.webbuilder.model.PersonalSystemParameter;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 个性化系统参数Dao实现类
 * 编写日期:	2011-3-25
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PersonalSystemParameterDaoImpl extends
		AbstractHibernateDaoSupport<PersonalSystemParameter> implements
		PersonalSystemParameterDao {

	@Override
	public List<PersonalSystemParameter> findByProperty(String property,
			Object value) {
		return this.executeHqlQuery(
				"from PersonalSystemParameter as m where m." + property
						+ "=? order by dispSn", new Object[] { value });
	}

	/**
	 * 
	 * 方法说明： 根据ids返回已经包含在ids里的个性化参数列表
	 * 
	 * @param pspids
	 *            个性化参数ids
	 * @return 已经包含在ids里的个性化参数列表
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalSystemParameter> findByIdsIn(String[] pspids) {
		List<PersonalSystemParameter> pspList = this.getSession()
				.createCriteria(PersonalSystemParameter.class).add(
						Restrictions.in("codeId", pspids)).list();
		return pspList;
	}

	/**
	 * 
	 * 方法说明： 根据pspids和类型返回不包含在ids里的个性化参数列表
	 * 
	 * @param pspids
	 *            个性化参数ids
	 * @param codeType
	 *            系统参数类型
	 * @return 返回不包含在ids里的个性化参数列表
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalSystemParameter> findByIdsOut(String[] pspids,
			String codeType[]) {
		List<PersonalSystemParameter> pspList = this.getSession()
				.createCriteria(PersonalSystemParameter.class).add(
						Restrictions.not(Restrictions.in("codeId", pspids)))
				.add(Restrictions.in("codeType", codeType)).list();
		return pspList;
	}

	/**
	 * 
	 * 方法说明： 根据类型查询系统全局参数
	 * 
	 * @param codetype
	 *            系统全局参数类型
	 * @return 系统全局参数列表
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalSystemParameter> findByType(String[] codetype) {
		List<PersonalSystemParameter> pspList = this.getSession()
				.createCriteria(PersonalSystemParameter.class).add(
						Restrictions.in("codeType", codetype)).list();
		return pspList;
	}
}
