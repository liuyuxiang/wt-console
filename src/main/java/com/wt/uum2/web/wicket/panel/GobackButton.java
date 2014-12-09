package com.wt.uum2.web.wicket.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import com.wt.uum2.web.wicket.panel.index.DeptTreePanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-23
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class GobackButton extends AjaxButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 */
	public GobackButton(String id)
	{
		super(id);
		setDefaultFormProcessing(false);
	}

	/**
	 * @param id
	 *            id
	 * @param model
	 *            model
	 */
	public GobackButton(String id, IModel<String> model)
	{
		super(id, model);
		setDefaultFormProcessing(false);
	}

	/**
	 * @param id
	 *            id
	 * @param form
	 *            form
	 */
	public GobackButton(String id, Form<?> form)
	{
		super(id, form);
		setDefaultFormProcessing(false);
	}

	/**
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @param form
	 *            form
	 */
	public GobackButton(String id, IModel<String> model, Form<?> form)
	{
		super(id, model, form);
		setDefaultFormProcessing(false);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form)
	{
		DeptTreePanel deptTreePanel = (DeptTreePanel) getPage().get(
				"contentPanel:contentForm:treePanel");
		WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
				"contentPanel:contentForm:mainContainer");

		GobackButton.this.onClick(target, form, deptTreePanel, mainContainer, "mainPanel");
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form)
	{


	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param form
	 *            form
	 * @param deptTreePanel
	 *            deptTreePanel
	 * @param mainContainer
	 *            mainContainer
	 * @param panelId
	 *            panelId
	 */
	public abstract void onClick(AjaxRequestTarget target, Form<?> form,
			DeptTreePanel deptTreePanel, WebMarkupContainer mainContainer, String panelId);
}
