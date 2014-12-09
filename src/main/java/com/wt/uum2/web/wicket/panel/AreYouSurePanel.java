package com.wt.uum2.web.wicket.panel;

import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

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
public abstract class AreYouSurePanel extends Panel
{

	/**
	 * 
	 */
	protected ModalWindow confirmModal;
	/**
	 * 
	 */
	protected ConfirmationAnswer answer;
	/**
	 * 
	 */
	protected Map<String, String> modifiersToApply;

	/**
	 * @param id
	 *            id
	 * @param buttonName
	 *            buttonName
	 * @param modalMessageText
	 *            modalMessageText
	 */
	public AreYouSurePanel(String id, String buttonName, String modalMessageText)
	{
		super(id);
		answer = new ConfirmationAnswer(false);
		addElements(id, buttonName, modalMessageText);
	}

	/**
	 * 方法说明：
	 * 
	 * @param id
	 *            id
	 * @param buttonName
	 *            buttonName
	 * @param modalMessageText
	 *            modalMessageText
	 */
	protected void addElements(String id, String buttonName, String modalMessageText)
	{

		confirmModal = createConfirmModal(id, modalMessageText);

		Form form = new Form("confirmForm");
		add(form);

		AjaxButton confirmButton = new AjaxButton("confirmButton", new Model(buttonName)) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				confirmModal.show(target);
			}

			@Override
			protected void onError(AjaxRequestTarget arg0, Form<?> form)
			{
			}

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);
				if (getButtonCssClass() != null) {
					tag.put("class", getButtonCssClass());
				}

			}

		};

		form.add(confirmButton);

		form.add(confirmModal);

	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 */
	protected abstract void onConfirm(AjaxRequestTarget target);

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 */
	protected abstract void onCancel(AjaxRequestTarget target);

	/**
	 * 方法说明：
	 * 
	 * @param id
	 *            id
	 * @param modalMessageText
	 *            modalMessageText
	 * @return ModalWindow
	 */
	protected ModalWindow createConfirmModal(String id, String modalMessageText)
	{

		ModalWindow modalWindow = new ModalWindow("modal");
		modalWindow.setCookieName(id);
		modalWindow.setContent(new YesNoPanel(modalWindow.getContentId(), modalMessageText,
				modalWindow, answer));
		modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			public void onClose(AjaxRequestTarget target)
			{
				if (answer.isAnswer()) {
					onConfirm(target);
				} else {
					onCancel(target);
				}
			}
		});

		return modalWindow;
	}

	public String getButtonCssClass()
	{
		return null;
	}
}
