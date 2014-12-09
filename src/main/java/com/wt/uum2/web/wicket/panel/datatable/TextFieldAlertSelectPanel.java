package com.wt.uum2.web.wicket.panel.datatable;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:输入框验证panel
 * 功能说明: 
 * 编写日期:	2011-10-24
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 * 
 * @param <T>
 *            类型
 */
public class TextFieldAlertSelectPanel<T> extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5842083808406988455L;
	/**
	 * 
	 */
	private String msg;
	/**
	 * 
	 */
	private TextField<T> textfield;
	/**
	 * 
	 */
	private AjaxButton button;
	

	/**
	 * @param id id
	 */
	public TextFieldAlertSelectPanel(String id)
	{
		this(id,null,null);
	}
	/**
	 * @param id id
	 * @param model model
	 */
	public TextFieldAlertSelectPanel(String id,IModel<T> model)
	{
		this(id,model,null);
	}
	/**
	 * @param id id
	 * @param model model
	 * @param submitmsg msg
	 */
	public TextFieldAlertSelectPanel(String id, IModel<T> model,String submitmsg)
	{
		super(id);
		this.msg=submitmsg;
		
		textfield=new TextField<T>("text", model);
		add(textfield.setOutputMarkupId(true));
		button = new AjaxButton("button") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4593314055916382798L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				target.appendJavaScript("$('#"+textfield.getMarkupId()+"').select();");
				getSession().cleanupFeedbackMessages();
				TextFieldAlertSelectPanel.this.onSubmit(target, form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.appendJavaScript("$('#" + textfield.getMarkupId() + "').select();");
				if(StringUtils.isNotBlank(msg)){
					target.appendJavaScript("alert('"+StringEscapeUtils.escapeJavaScript(msg)+"')");
					return;
				}else if(textfield.hasErrorMessage()){
					for (FeedbackMessage feedbackMessage : textfield.getFeedbackMessages()) {
						target.appendJavaScript("alert('" + StringEscapeUtils.escapeJavaScript(feedbackMessage.getMessage().toString()) + "');");
					}
					return;
				}
				TextFieldAlertSelectPanel.this.onError(target, form);
			}
		};
		add(button);
	}

	/**
	 * 方法说明：提交
	 * 
	 * @param target
	 *            target
	 * @param form
	 *            form
	 */
	public void onSubmit(AjaxRequestTarget target, Form<?> form)
	{
	}

	/**
	 * 方法说明：当出错时
	 * 
	 * @param target
	 *            　target
	 * @param form
	 *            　form
	 */
	public void onError(AjaxRequestTarget target, Form<?> form)
	{
	}
	public TextField<T> getTextfield()
	{
		return textfield;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public AjaxButton getButton()
	{
		return button;
	}
	public void setButton(AjaxButton button)
	{
		this.button = button;
	}
	
	
}
