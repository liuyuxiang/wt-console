package com.wt.uum2.web.wicket.panel.application;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:用户列表搜索页面操作条
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
public class AppSearchToolbarPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private DropDownChoice searchType;

	/**
	 * 
	 */
	private String content;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private TextField textfield;

	/**
	 * @param id
	 *            id
	 */
	public AppSearchToolbarPanel(String id)
	{
		this(id, null, null);
	}

	/**
	 * @param id
	 *            id
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 */
	public AppSearchToolbarPanel(String id, String searchcontent, String searchtype)
	{
		super(id);
		this.content = searchcontent;
		this.type = AppUserSearchType.valueOfString(searchtype).toString();
		addComponents();
	}

	/**
	 * 方法说明：
	 * 
	 */
	public void addComponents()
	{

		textfield = new TextField("text", new Model(content)) {

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				tag.put("onkeypress", "if (event.keyCode == 13) {return false;}");
			}
		};

		add(textfield.setOutputMarkupId(true));
		textfield.setLabel(Model.of("查询内容"));
		AjaxButton button = new AjaxButton("button") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				if (StringUtils.isEmpty(textfield.getValue())) {

					target.appendJavaScript("$('#" + textfield.getMarkupId() + "').select();");
					target.appendJavaScript("alert('请输入" + textfield.getLabel().getObject() + "')");
					target.focusComponent(textfield);
					return;
				}

				content = textfield.getValue();
				type = searchType.getValue();
				AppSearchToolbarPanel.this.onSubmit(target, form, content, type);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				for (FeedbackMessage feedbackMessage : textfield.getFeedbackMessages()) {
					target.focusComponent(feedbackMessage.getReporter());
					target.appendJavaScript("$('#" + feedbackMessage.getReporter().getMarkupId()
							+ "').select();");
					target.appendJavaScript("alert('"
							+ StringEscapeUtils.escapeJavaScript(feedbackMessage.getMessage()
									.toString()) + "');");
				}

			}

		};
		add(button);

		ChoiceRenderer<String> choiceRenderer = new ChoiceRenderer() {

			@Override
			public Object getDisplayValue(Object object)
			{
				return AppUserSearchType.getSearchTypeMap().get(object);
			}

			@Override
			public String getIdValue(Object object, int index)
			{

				return object.toString();
			}

		};
		searchType = new DropDownChoice("searchType", new Model(type),
				AppUserSearchType.getSearchTypeList(), choiceRenderer);
		add(searchType);
	}

	public String getContent()
	{
		return content;
	}

	public String getType()
	{
		return type;
	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param form
	 *            form
	 * @param content
	 *            content
	 * @param type
	 *            type
	 */
	public void onSubmit(AjaxRequestTarget target, Form<?> form, String content, String type)
	{

	}
}
