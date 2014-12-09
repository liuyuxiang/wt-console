package com.wt.hea.common.infrastructure.event;

/**
 * 
 * <pre>
 * 业务名:客户化的事件处理回调
 * 功能说明: 客户化的事件自定义处理
 * 编写日期:	2011-3-28
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-28
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public abstract interface CustomOperation {
	/**
	 * 
	 * 方法说明：用户匿名接口类实现该方法，处理事件对象
	 *
	 * @param obj 事件对象
	 * @return 返回是否处理成功
	 */
	public Boolean operation(Object obj);
}
