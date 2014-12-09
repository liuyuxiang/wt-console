package com.wt.uum2.web.wicket.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-1
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class YesNoPanel extends Panel {

	/**
	 * @param id
	 *            id
	 * @param message
	 *            message
	 * @param modalWindow
	 *            modalWindow
	 * @param answer
	 *            answer
	 */
	public YesNoPanel(String id, String message, final ModalWindow modalWindow,
			final ConfirmationAnswer answer) {
		super(id);

		Form yesNoForm = new Form("yesNoForm");

		MultiLineLabel messageLabel = new MultiLineLabel("message", message);
		yesNoForm.add(messageLabel);
		modalWindow.setTitle("请确认");
		modalWindow.setInitialHeight(150);
		modalWindow.setInitialWidth(250);

		AjaxButton yesButton = new AjaxButton("yesButton", yesNoForm) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					answer.setAnswer(true);
					modalWindow.close(target);
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}

		};

		AjaxButton noButton = new AjaxButton("noButton", yesNoForm) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				if (target != null) {
					answer.setAnswer(false);
					modalWindow.close(target);
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}

		};

		yesNoForm.add(yesButton);
		yesNoForm.add(noButton);

		add(yesNoForm);
	}
}
