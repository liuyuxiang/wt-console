package com.wt.uum2.web.wicket.form;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ErrorClassAppender extends AbstractBehavior {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8227217053833521726L;

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		if (((FormComponent<?>) component).isValid() == false) {
			String cl = tag.getAttribute("class");
			if (cl == null) {
				tag.put("class", "error");
			} else {
				tag.put("class", "error " + cl);
			}
		}
	}
}
