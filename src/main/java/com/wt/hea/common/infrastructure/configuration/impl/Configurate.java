package com.wt.hea.common.infrastructure.configuration.impl;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.wt.hea.common.infrastructure.configuration.Configuration;

/**
 * 
 * <pre>
 * 业务名:配置管理
 * 功能说明: 配置管理实现
 * 编写日期:	2011-3-31
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-31
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class Configurate implements Configuration, BeanFactoryAware
{
	/**
	 * 私有构造函数
	 */
	private Configurate()
	{
	}

	/**
	 * 
	 */
	private static Configurate configuration;

	/**
	 * 
	 * 方法说明：获取单例对象
	 * 
	 * @return 返回单例对象
	 */
	public static Configurate getInstance()
	{
		if (configuration == null) {
			configuration = new Configurate();
		}
		return configuration;
	}

	/**
	 * spring容器上下文对象
	 */
	protected BeanFactory beanFactory;

	/**
	 * 会话工厂对象，用于CRUD的获取数据源等
	 */
	private SessionFactory sessionFactory;

	/**
	 * 
	 * 方法说明：资源配置打开
	 * 
	 */
	public void open()
	{
		sessionFactory = (SessionFactory) getApplicationBean("heaSessionFactory");
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
	}

	/**
	 * 
	 * 方法说明：资源配置关闭，加收资源和提交事务
	 * 
	 */
	public void close()
	{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager
				.getResource(sessionFactory);
		Session s = holder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.closeSession(holder.getSession());
	}

	/**
	 * 获取配置文件里的bean
	 * 
	 * @param beanId
	 *            配置文件唯一的bean 标识
	 * @return 返回bean对象
	 */
	private Object getApplicationBean(String beanId)
	{
		return beanFactory.getBean(beanId);
	}

	/**
	 * 获取配置文件里的bean
	 * 
	 * @param beanId
	 *            配置文件唯一的bean 标识
	 * @return 返回bean对象
	 */
	public Object getBean(String beanId)
	{
		return beanFactory.getBean(beanId);
	}

	/**
	 * 
	 * 方法说明：获取属性文件中的值
	 * 
	 * @param key
	 *            属性文件key名称
	 * @param propFilePath
	 *            属性文件路径(含文件名)
	 * @return 返回属性值
	 */
	public String getPropValue(String key, String propFilePath)
	{
		// 配置文件加载
		try {
			Properties prop = PropertiesLoaderUtils.loadAllProperties(propFilePath);
			Set<Entry<Object, Object>> set = prop.entrySet();
			for (Entry<Object, Object> e : set) {
				if (e.getKey() != null) {
					if (key.equals(e.getKey())) {
						return e.getValue().toString();
					}
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	/** {@inheritDoc} */
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException
	{
		// TODO Auto-generated method stub

	}
}
