package com.wt.uum2.web.wicket.panel;

import java.io.Serializable;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-9
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ConfirmationAnswer implements Serializable
{

	/**
	 * 
	 */
	private boolean answer;

	/**
	 * @param answer
	 *            answer
	 */
	public ConfirmationAnswer(boolean answer)
	{
		this.answer = answer;
	}

	public boolean isAnswer()
	{
		return answer;
	}

	public void setAnswer(boolean answer)
	{
		this.answer = answer;
	}
}