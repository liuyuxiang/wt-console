package com.wt.uum2.web.wicket.panel;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;

import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-15
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SimpleComponentFeedbackPanel extends SimpleFeedbackPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 * @param filter
	 *            filter
	 */
	public SimpleComponentFeedbackPanel(String id, Component filter)
	{
		super(id, new ComponentFeedbackMessageFilter(filter));
	}

}
