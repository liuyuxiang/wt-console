package com.wt.hea.common.infrastructure.event.impl;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * <pre>
 * 业务名: 提供类的代理对象,CGLIG实现依赖于Cglib.jar 
 * 功能说明:  提供类的代理对象,调用方式 :  
 * IndexService ser=(IndexService)CglibProxy.getInstance().createProxy(IndexServiceImpl.class);
 * 编写日期:	2011-4-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-4-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class CglibProxy implements MethodInterceptor
{

	/**
	 * 代理对象单例
	 */
	private static CglibProxy CGLIB_PROXY = null;

	/**
	 * 私用构造，用于实现单例
	 */
	private CglibProxy()
	{
	}

	/**
	 * 
	 * 方法说明：返回单例对象
	 * 
	 * @return 返回代理类单例对象
	 */
	public static CglibProxy getInstance()
	{
		if (CGLIB_PROXY == null) {
			CGLIB_PROXY = new CglibProxy();
		}
		return CGLIB_PROXY;
	}

	/**
	 * 增强类
	 * 
	 */
	private Enhancer enhancer = new Enhancer();

	/**
	 * 
	 * 方法说明：创建代理对象
	 * 
	 * @param clazz
	 *            目标对象的类型
	 * @return 代理对象
	 */
	@SuppressWarnings("rawtypes")
	public Object createProxy(Class clazz)
	{
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}

	/**
	 * 
	 * 方法说明：代理对象的方法调用栏截，加入横切
	 * 
	 * 
	 * @param obj
	 *            代理的目标对象
	 * @param method
	 *            代理的目标对象方法
	 * @param args
	 *            代理的目标对象方法参数
	 * @param proxy
	 *            方法代理对象
	 * @return 返回代理对象后的值
	 */
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
			throws Throwable
	{

		Object result = null;
		try {
			result = proxy.invokeSuper(obj, args);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CommonException(ex.getMessage());
		}
		return result;
	}
}
