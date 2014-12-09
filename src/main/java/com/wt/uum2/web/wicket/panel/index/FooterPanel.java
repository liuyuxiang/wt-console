package com.wt.uum2.web.wicket.panel.index;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-16
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class FooterPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 */
	public FooterPanel(String id)
	{
		super(id);
		AjaxSelfUpdatingTimerBehavior ajaxSelfUpdatingTimerBehavior = new AjaxSelfUpdatingTimerBehavior(
				Duration.seconds(30));
		add(new Label("copyright", Model.of(getSetting().getCopyRight())).setEscapeModelStrings(
				false).add(ajaxSelfUpdatingTimerBehavior));

	}

}
