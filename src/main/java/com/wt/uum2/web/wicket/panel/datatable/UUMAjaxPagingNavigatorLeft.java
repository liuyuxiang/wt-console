package com.wt.uum2.web.wicket.panel.datatable;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.MaximumValidator;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:UUM分页左块
 * 功能说明: 
 * 编写日期:	2011-11-3
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class UUMAjaxPagingNavigatorLeft extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6652183898105113692L;
	final int MAXRECORDPERPAGE = getSetting().getMaxRecordPerPage();
	Pager pager;
	/**
	 * @param id
	 *            id
	 * @param pager
	 *            pager
	 */
	public UUMAjaxPagingNavigatorLeft(String id, final Pager pager)
	{

		super(id);
		this.pager=pager;
		
		add(new Label("totalPage", new PropertyModel<Integer>(pager, "quantityOfData")));

		add(new RowsPerForm("rowsPerForm"));
		add(new GoForm("goForm"));
	}

	/**
	 * 方法说明：跳页操作
	 * 
	 * @param target
	 *            target
	 * @param pager
	 *            pager
	 */
	public abstract void gotoPage(AjaxRequestTarget target, Pager pager);

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param form
	 *            form
	 * @param feedbackMessages
	 *            feedbackMessages
	 */
	protected void onError(AjaxRequestTarget target, Form<?> form,
			List<FeedbackMessage> feedbackMessages)
	{
		for (FeedbackMessage feedbackMessage : feedbackMessages) {
			target.focusComponent(feedbackMessage.getReporter());
			target.appendJavaScript("$('#" + feedbackMessage.getReporter().getMarkupId()
					+ "').select();");
			target.appendJavaScript("alert('"
					+ StringEscapeUtils.escapeJavaScript(feedbackMessage.getMessage().toString())
					+ "');");
		}
	}
	private class RowsPerForm extends Form<Void>{

		public RowsPerForm(String id)
		{
			super(id);
			final RequiredTextField ROWSPERTEXT = new RequiredTextField("rowsPerText",
					new PropertyModel<Integer>(pager, "pageSize"));
			add(ROWSPERTEXT.setOutputMarkupId(true));
			ROWSPERTEXT.setLabel(new ResourceModel("rowsPer"));
			if (StringUtils.isNotEmpty(String.valueOf(MAXRECORDPERPAGE))) {
				ROWSPERTEXT.add(new MaximumValidator(MAXRECORDPERPAGE));
			}
			AjaxButton rowsPerButton = new AjaxButton("rowsPerButton") {

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form)
				{
					pager.setCurrentPage(1);
					pager.compute();
					gotoPage(target, pager);

				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form)
				{
					List<FeedbackMessage> feedbackMessages = new ArrayList<FeedbackMessage>();
					feedbackMessages.addAll(ROWSPERTEXT.getFeedbackMessages());
					//feedbackMessages.addAll(GOTEXT.getFeedbackMessages());
					UUMAjaxPagingNavigatorLeft.this.onError(target, form, feedbackMessages);
				}

			};
			add(rowsPerButton);
		}
		
	}
	
	private class GoForm extends Form<Void>{

		public GoForm(String id)
		{
			super(id);
			final RequiredTextField GOTEXT = new RequiredTextField("goText",
					new PropertyModel<Integer>(pager, "currentPage"));
			GOTEXT.setLabel(new ResourceModel("go"));
			add(GOTEXT.setOutputMarkupId(true));

			

			AjaxButton goButton = new AjaxButton("goButton") {

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form)
				{
					pager.compute();
					gotoPage(target, pager);
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form)
				{
					List<FeedbackMessage> feedbackMessages = new ArrayList<FeedbackMessage>();
					//feedbackMessages.addAll(ROWSPERTEXT.getFeedbackMessages());
					feedbackMessages.addAll(GOTEXT.getFeedbackMessages());
					UUMAjaxPagingNavigatorLeft.this.onError(target, form, feedbackMessages);
				}

			};
			add(goButton);
		}
		
	}
}
