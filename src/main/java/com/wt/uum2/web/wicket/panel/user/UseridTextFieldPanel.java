package com.wt.uum2.web.wicket.panel.user;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.wt.uum2.constants.PinyinUtil;
import com.wt.uum2.domain.Department;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:用户名用户ID面板
 * 功能说明: 
 * 编写日期:	2011-9-27
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UseridTextFieldPanel extends BaseUUMPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	final private String oldId;

	/**
	 * 
	 */
	public TextField<String> useridTextField;

	/**
	 * 
	 */
	public SimpleFeedbackPanel useridFeedback;

	/**
	 * 
	 */
	private IModel<String> model;

	/**
	 * @param id id
	 * @param model model
	 */
	public UseridTextFieldPanel(String id,IModel<String> model) {
		
		
		super(id);
		
		setOutputMarkupId(true);

		this.model=model;
		oldId = model.getObject();

		useridTextField = new RequiredTextField<String>("userid",model);
		
		useridTextField.add(new AjaxFormComponentUpdatingBehavior("onblur"){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e) {
				super.onError(target, e);
				target.add(useridFeedback);
			}

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(useridFeedback);
			}

			
			
		});

		useridTextField.add(new IValidator<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void validate(IValidatable<String> validatable) {

				if (!StringUtils.equals(oldId ,validatable.getValue())) {
					if (getUUMService().existUserId(validatable.getValue())) {
						ValidationError error = new ValidationError()
								.setMessage("用户标识已经存在，请重新输入！");
						validatable.error(error);
					}
				}

			}

		}).setOutputMarkupId(true);

		add(useridTextField);

		useridFeedback=new SimpleFeedbackPanel("useridFeedback",
				new ComponentFeedbackMessageFilter(useridTextField));
		
		add(useridFeedback.setOutputMarkupId(true));
	}

	/**
	 * 方法说明：获取用户ID
	 *
	 * @param name 姓名
	 * @param dept 部门
	 * @param target target
	 */
	public void genUserId(String name, Department dept, AjaxRequestTarget target) {
		String userId = String.valueOf("");
		String prefix = String.valueOf("");
		if (StringUtils.isNotBlank(dept.getUuid())) {
			if (getUUMService().isVirtualUser(dept)) {
				prefix = dept.getCode() + "_";
			}
		}
		userId = prefix + userId;
		if (StringUtils.isNotBlank(name)) {
			userId = prefix + PinyinUtil.stringToPinYin(name);
			if (userId.length() > 16) {
				userId = prefix + PinyinUtil.stringToHeadPinYin(name);
			}
			userId = getUUMService().getNotExistsUserId(userId);
		}
		model.setObject(userId);
		useridTextField.valid();
		if (target != null) {
			target.add(this);
		}
	}

	public String getOldId() {
		return oldId;
	}

	public TextField<String> getUseridTextField() {
		return useridTextField;
	}

}
