package com.wt.uum2.web.wicket.panel.user.password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.validation.validator.StringValidator;

import com.hirisun.components.other.project.ProjectResolver;
import com.hirisun.components.web.wicket.button.AjaxImageButton;
import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.hirisun.rd1.secret.Decryptor;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.StringParse;
import com.wt.uum2.constants.UUMDateFormat;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.page.user.password.ModifyPasswordType;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.user.CreateUserPanel;

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
public abstract class UpdateUserPasswordPanel extends BaseUUMPanel
{
	/**
	 * 要修改密码的用户
	 */
	private User user;
	/**
	 * 参数用户ID
	 */
	private StringValue userid;
	/**
	 * 参数userCn
	 */
	private StringValue userCn;
	/**
	 * 参数区分来源
	 */
	private ModifyPasswordType type;

	/**
	 * 表单
	 */
	private Form<Void> form;
	/**
	 * 确定按钮
	 */
	private AjaxButton submitButton;

	/**
	 * 当类型是portal时重庆会用到的参数
	 */
	private String flag;
	/**
	 * 当类型是reset时才显示的信息组件
	 */
	private Label restMsg;

	/**
	 * 
	 */
	private Label userName;

	/**
	 * 用户ID输入框
	 */
	private RequiredTextField<String> useridTextField;
	/**
	 * 旧密码输入框
	 */
	private PasswordTextField oldpasswordTextField;
	/**
	 * 新密码输入框
	 */
	private PasswordTextField password;
	/**
	 * 确认密码输入框
	 */
	private PasswordTextField repassword;
	/**
	 * 图片按钮
	 */
	private AjaxImageButton sumbitImg;
	/**
	 * 返回按钮
	 */
	private AjaxButton gobackButton;
	/**
	 * table容器
	 */
	private WebMarkupContainer backgrouptd;
	/**
	 * 当类型时reset时参数
	 */
	private StringValue key;
	/**
	 * table容器的tbody
	 */
	private WebMarkupContainer tbody;
	/**
	 * 信息
	 */
	private String msg;
	/**
	 * 密码的扩展属性
	 */
	private PasswordAttrListPanel pwdAttrs;

	/**
	 * @param id
	 *            id
	 * @param userid
	 *            userid
	 * @param userCn
	 *            userCn
	 * @param type
	 *            type
	 * @param key
	 *            key
	 * @param flag
	 *            flag
	 */
	public UpdateUserPasswordPanel(String id, StringValue userid, StringValue userCn,
			ModifyPasswordType type, StringValue key, String flag)
	{
		super(id);
		this.userid = userid;
		this.userCn = userCn;
		this.type = type;
		this.key = key;
		this.flag = flag;

		initForm();
	}

	/**
	 * @param id
	 *            id
	 * @param user
	 *            user
	 * @param type
	 *            type
	 */
	public UpdateUserPasswordPanel(String id, User user, ModifyPasswordType type)
	{
		super(id);
		this.user = user;
		this.type = type;

		initForm();
	}

	/**
	 * 方法说明：初始化FORM
	 * 
	 */
	public void initForm()
	{
		switch (type) {
		case reset:
			checkKeyHandler(key);
			break;
		default:
			if (user == null) {
				if (userid.isEmpty()) {
					userid = userCn;
				}

				if (!userid.isEmpty()) {
					user = getUUMService().getUserByUserId(userid.toString());
				} else {
					user = getUUMService().getLoginUser();
				}

			}
			break;
		}

		int minlength = getSetting().getUserPasswordMinLength();
		int maxlength = getSetting().getUserPasswordMaxLength();
		form = new Form<Void>("form");
		add(form);
		backgrouptd = new WebMarkupContainer("backgrouptd");
		form.add(backgrouptd);
		tbody = new WebMarkupContainer("tbody");
		backgrouptd.add(tbody);

		final SimpleFeedbackPanel FEEDBACK = new SimpleFeedbackPanel("feedback");
		form.add(FEEDBACK.setOutputMarkupId(true));

		userName = new Label("userName", user.getName());
		tbody.add(userName.setOutputMarkupId(true).setVisible(false));

		useridTextField = new RequiredTextField<String>("userid", new Model<String>());
		tbody.add(useridTextField.setEnabled(false));
		oldpasswordTextField = new PasswordTextField("oldpassword", new Model<String>());
		oldpasswordTextField.setLabel(Model.of("旧密码"));
		oldpasswordTextField.add(new StringValidator.LengthBetweenValidator(minlength, maxlength));
		tbody.add(oldpasswordTextField.setRequired(true).setOutputMarkupId(true).setVisible(false));
		password = new PasswordTextField("password", new Model<String>());
		password.setLabel(Model.of("新密码"));

		password.add(new StringValidator.LengthBetweenValidator(minlength, maxlength));
		tbody.add(password.setRequired(true).setOutputMarkupId(true));

		repassword = new PasswordTextField("repassword", new Model<String>());
		repassword.setLabel(Model.of("确认密码"));
		repassword.add(new StringValidator.LengthBetweenValidator(minlength, maxlength));
		tbody.add(repassword.setRequired(true).setOutputMarkupId(true));

		sumbitImg = new AjaxImageButton("submitImg", new ContextRelativeResource(
				"/style/default/images/qd.gif"), form) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{

				if (type == ModifyPasswordType.portal
						&& !checkUserPassword(user, oldpasswordTextField.getValue())) {
					if ("gdgs".equals(flag)) {
						error("密码输入错误或者不是供电公司账户!");
					} else {
						error("旧密码输入错误!");
					}
					focusComponent(target, oldpasswordTextField, FEEDBACK);
					return;
				}
				if (StringUtils.equals(user.getPlainPassword(), password.getValue())) {
					error("请不要使用原始密码！");
					focusComponent(target, password, FEEDBACK);
					return;
				}
				if (!updateUserPasswordValidateHandle(target, FEEDBACK)) {
					return;
				}
				boolean status = updateUserPasswordHandle(user, user.getPlainPassword(),
						password.getValue());
				if (status) {
					confirmCloseHandle("修改密码成功！", target);
				} else {
					confirmCloseHandle("修改密码失败！", target);
				}

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(FEEDBACK);
			}

		};
		form.add(sumbitImg);
		submitButton = new AjaxButton("submitButton") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				if (oldpasswordTextField.isVisible()) {
					if (!checkUserPassword(user, oldpasswordTextField.getValue())) {
						error("密码输入错误！");
						focusComponent(target, oldpasswordTextField, FEEDBACK);
						return;
					}
				}
				if (StringUtils.equals(user.getPlainPassword(), password.getValue())) {
					// info("修改成功！");
					// target.add(FEEDBACK);
					goTo(target);
					return;
				}
				if (!updateUserPasswordValidateHandle(target, FEEDBACK)) {
					return;
				}

				if (updateUserPasswordHandle(user, user.getPlainPassword(), password.getValue())) {
					// info("修改成功！");
					// target.add(FEEDBACK);

					goTo(target);
				}

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(FEEDBACK);
			}
		};

		form.add(submitButton.setVisible(false));
		gobackButton = new AjaxButton("goback") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				// setResponsePage(new RedirectPage(urlBuilder.toString()));

				WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");
				mainContainer.addOrReplace(new CreateUserPanel("mainPanel", user, null));
				target.add(mainContainer);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{

			}

		};
		form.add(gobackButton.setDefaultFormProcessing(false).setVisible(false));

		String declare = "密码只能为" + minlength + "--" + maxlength + "位数字，英文及特殊字符的任意组合！";
		Label declareLabel = new Label("declare", declare);
		tbody.add(declareLabel);
		pwdAttrs = new PasswordAttrListPanel("attr0", new ArrayList(), new ArrayList());
		tbody.add(pwdAttrs.setVisible(false).setEnabled(false));

		restMsg = new Label("restMsg", new PropertyModel(this, "msg"));

		add(restMsg.setVisible(false));
		setVisibleByType();

	}

	/**
	 * 方法说明：goTo
	 * 
	 * @param target
	 *            target
	 */
	public abstract void goTo(AjaxRequestTarget target);

	/**
	 * 方法说明：根据类型设置组件是否显示
	 * 
	 */
	public void setVisibleByType()
	{

		switch (type) {
		case reset:
			if (StringUtils.isNotEmpty(msg)) {
				form.setVisible(false);
				restMsg.setVisible(true);
			}
			useridTextField.setDefaultModel(new PropertyModel<String>(user, "id"));
			if (StringUtils.equals(ProjectResolver.getId(), "zdj")) {
				userName.setVisible(true);
				backgrouptd.add(new AttributeModifier("class", "tab"));
				backgrouptd.add(new AttributeModifier("cellPadding", "0"));
				backgrouptd.add(new AttributeModifier("cellspacing", "0"));
			} else {
				backgrouptd.add(new AttributeModifier("class", "bluebackgroup"));
				backgrouptd.add(new AttributeModifier("cellspacing", "4"));
				useridTextField.add(new AttributeModifier("class", "inputstyle"));
				oldpasswordTextField.add(new AttributeModifier("class", "inputstyle"));
				password.add(new AttributeModifier("class", "inputstyle"));
				repassword.add(new AttributeModifier("class", "inputstyle"));

			}
			break;

		case portal:

			oldpasswordTextField.setVisible(true);

			if ("gdgs".equals(flag)) {
				useridTextField.setEnabled(true);
				user = new User();
			} else {
				useridTextField.setDefaultModel(new PropertyModel<String>(user, "id"));
			}

			if (StringUtils.equals(ProjectResolver.getId(), "zdj")) {
				userName.setVisible(true);
				backgrouptd.add(new AttributeModifier("class", "tab"));
				backgrouptd.add(new AttributeModifier("cellPadding", "0"));
				backgrouptd.add(new AttributeModifier("cellspacing", "0"));
			} else {
				backgrouptd.add(new AttributeModifier("class", "bluebackgroup"));

				backgrouptd.add(new AttributeModifier("cellspacing", "4"));
				useridTextField.add(new AttributeModifier("class", "inputstyle"));
				oldpasswordTextField.add(new AttributeModifier("class", "inputstyle"));
				password.add(new AttributeModifier("class", "inputstyle"));
				repassword.add(new AttributeModifier("class", "inputstyle"));
			}
			break;

		default:
			useridTextField.setDefaultModel(new PropertyModel<String>(user, "id"));
			addPWDAttributes();
			sumbitImg.setVisible(false);
			submitButton.setVisible(true);
			gobackButton.setVisible(true);
			if (StringUtils.equals(ProjectResolver.getId(), "zdj")) {
				userName.setVisible(true);
				backgrouptd.add(new AttributeModifier("class", "tab"));
				backgrouptd.add(new AttributeModifier("cellPadding", "0"));
				backgrouptd.add(new AttributeModifier("cellspacing", "0"));
			} else {
				backgrouptd.add(new AttributeModifier("cellspacing", "1"));
				backgrouptd.add(new AttributeModifier("class", "tableborder1"));
				tbody.add(new AttributeModifier("bgcolor", "#FFFFF"));
			}
			form.add(new AttributeModifier("id", "doForm"));
			break;
		}
	}

	/**
	 * 方法说明： 检查新密码与用户密码是否相同
	 * 
	 * @param u
	 *            用户
	 * @param oldpassword
	 *            密码
	 * @return 是否相同
	 */
	public boolean checkUserPassword(User u, String oldpassword)
	{
		boolean isequal = false;
		if ("gdgs".equals(flag) && type == ModifyPasswordType.portal) {
			user = getUUMService().getUserByUserId(useridTextField.getInput());
			if (user != null) {
				Department dept;
				try {
					dept = getUUMService().getDepartmentByDeptCode(
							getUUMService().getUserPrimaryDepartment(user).getOrgCode());
				} catch (Exception e) {
					return false;
				}
				if (user.getPassword() == null && dept.getName().contains("供电有限责任公司")) {
					isequal = true;
				} else {
					if (user.getPlainPassword().equals(oldpassword)
							&& dept.getName().contains("供电有限责任公司")) {
						isequal = true;
					}
				}
			}
			return isequal;
		}

		if (u.getPassword() == null) {
			isequal = true;
		} else {
			if (InitParameters.isPlainPassword()) {
				isequal = u.getPlainPassword().equals(oldpassword);
			} else {
				if (InitParameters.getMD5EncodePassTurnOn() != null
						&& InitParameters.getMD5EncodePassTurnOn().equals("true")) {
					oldpassword = StringParse.md5(oldpassword);
				}
				isequal = u.getPassword().equals(oldpassword);
			}
		}
		return isequal;
	}

	/**
	 * 方法说明：设置组件获得焦点并全选内容
	 * 
	 * @param target
	 *            target
	 * @param component
	 *            component
	 * @param feedback
	 *            feedback
	 */
	public void focusComponent(AjaxRequestTarget target, Component component, FeedbackPanel feedback)
	{
		target.appendJavaScript("$('#" + component.getMarkupId() + "').select();");
		target.focusComponent(component);
		target.add(feedback);

	}

	/**
	 * 方法说明：更新密码操作
	 * 
	 * @param user
	 *            user
	 * @param oldpassword
	 *            oldpassword
	 * @param newpassword
	 *            newpassword
	 * @return 是否成功
	 */
	public boolean updateUserPasswordHandle(User user, String oldpassword, String newpassword)
	{

		boolean status = true;
		if (InitParameters.isPlainPassword()) {
			user.setPlainPassword(newpassword);
		} else {
			if ("true".equals(InitParameters.getMD5EncodePassTurnOn())) {
				newpassword = StringParse.md5(newpassword);
			}
			user.setPassword(newpassword);
		}
		try {
			getUUMService().updateUser(user);
			UUMDateFormat df = new UUMDateFormat();
			getUUMService().modifyResourceAttribute(null, user, "pwdChangeTime",
					df.switchLongToDateFormat(System.currentTimeMillis()));
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		// 同步用户start
		// 修改所有应用系统密码
		Map<String, String[]> map = new HashMap<String, String[]>();
		if (InitParameters.isCqGroupAuthor()) {
			Map<String, String[]> cqMap = changeAttributePasswordCQ(user, newpassword);
			for (Map.Entry<String, String[]> entry : cqMap.entrySet()) {
				map.put(entry.getKey(), new String[] { oldpassword, user.getPlainPassword() });
			}
		}
		map.put("userPassword", new String[] { oldpassword, newpassword });
		Event event = getEventFactory().createEventUpdateUser(user.getUuid(), map);
		getEventListenerHandler().handle(event);

		return status;

	}

	/**
	 * 方法说明：修改用户密码扩展属性
	 * 
	 * @param user
	 *            user
	 * @param password
	 *            password
	 * @return 获得修改的扩展属性
	 */
	public Map<String, String[]> changeAttributePasswordCQ(User user, String password)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		if ("true".equals(InitParameters.getChangeAllAppPwd())) {
			List<String> keys = new ArrayList<String>();
			List<AttributeType> attypes = getUUMService().getAttributeTypeByResource(user,
					Integer.valueOf(0), "3");
			if (CollectionUtils.isNotEmpty(attypes)) {
				for (int i = 0; i < attypes.size(); i++) {
					AttributeType at = attypes.get(i);
					if (at.getId().endsWith("Pwd") || at.getId().endsWith("Password")) {
						keys.add(at.getId());
					}
				}
			}
			List<Attribute> atts = getUUMService().getAttributesByAttributeTypeIdKey(user, keys);
			if (CollectionUtils.isNotEmpty(atts)) {
				for (int i = 0; i < atts.size(); i++) {
					Attribute attribute = atts.get(i);
					String attributeOldValue = attribute.getValue();
					if (InitParameters.isPlainPassword()) {
						attribute.setValue(password);
					}
					if (StringUtils.isBlank(attributeOldValue)) {
						getUUMService().saveAttribute(attribute);
					} else {
						getUUMService().updateAttribute(attribute);
					}
				}
			}
			for (String key : keys) {
				map.put(key, new String[] { user.getId(), password });
			}
		}

		return map;
	}

	/**
	 * 方法说明：匹配用户密码是否合法
	 * 
	 * @param value
	 *            value
	 * @return 不匹配的错误信息列表
	 */
	public List<String> matchesPassword(String value)
	{
		List<String> errorMsgs = new ArrayList<String>();
		if (getSetting().getUserPasswordIsBeginEN()) {
			if (!Pattern.compile("^[a-zA-Z]").matcher(value).find()) {
				errorMsgs.add("密码必须字母开头！");
			}
		}
		if (getSetting().getUserPasswordIsMustHaveNUM()) {
			if (!Pattern.compile("\\d+").matcher(value).find()) {
				errorMsgs.add("密码必须有数字！");

			}
		}
		if (getSetting().getUserPasswordIsMustHaveEN()) {
			if (!Pattern.compile("[A-Za-z]+").matcher(value).find()) {
				errorMsgs.add("密码必须有字母！");

			}
		}
		if (getSetting().isUserPasswordIsMustHaveSpecialCharacters()) {
			String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）—_+|{}【】‘；：”“’。，、？]";
			if (!Pattern.compile(regEx).matcher(value).find()) {
				errorMsgs.add("密码中必须包含特殊字符！");
			}
		}
		/*if (Pattern.compile("\\s+").matcher(value).find()) {
			errorMsgs.add("密码不能包含空格！");
		}*/

		return errorMsgs;
	}

	/**
	 * 方法说明：检查key状态
	 * 
	 * @param key
	 *            key
	 */
	public void checkKeyHandler(StringValue key)
	{
		user = new User();
		if (key.isEmpty()) {
			msg = "key异常";
			return;
		}
		Decryptor decryptor = new Decryptor();
		String context = "";
		try {
			context = decryptor.decryptBase642String("resetPassword", key.toString());
		} catch (Exception e) {
			msg = "key异常";
			return;
		}

		if (context.split(",").length != 2) {
			msg = "key异常";
			return;
		}
		Long currentTimeMillis = Long.valueOf(context.split(",")[0]);
		if ((System.currentTimeMillis() - currentTimeMillis) > (24 * 60 * 60 * 1000)) {
			msg = "key超时";
			return;
		}
		String userId = String.valueOf(context.split(",")[1]);
		if (!getUUMService().existUserId(userId)) {
			msg = "user异常";
			return;
		}
		user = getUUMService().getUserByUserId(userId);
		if (user.getStatus() != ResourceStatus.NORMAL.intValue()) {
			msg = "user状态异常";
			return;
		}
	}

	/**
	 * 方法说明：添加用户密码相关的扩展属性组件
	 * 
	 */
	public void addPWDAttributes()
	{
		Set<String> key = new HashSet<String>();
		key.add("pwdChangeTime");
		key.add("pwdDueTime");
		key.add("periodValidity");
		List<AttributeType> attributeTypeList = getUUMService().getAttributeTypeByKeySet(key);
		List<Attribute> attributeList = new ArrayList<Attribute>();
		for (AttributeType ttributeType : attributeTypeList) {
			attributeList.addAll(getUUMService().getAttributesByResAndType(user, ttributeType));
		}
		if (CollectionUtils.isNotEmpty(attributeList)) {
			pwdAttrs.replaceWith(
					new PasswordAttrListPanel(pwdAttrs.getId(), attributeList, attributeTypeList))
					.setEnabled(false);
		}
	}

	/**
	 * 方法说明：密码校验
	 * 
	 * @param target
	 *            target
	 * @param feedback
	 *            feedback
	 * @return 是否通过校验
	 */
	public boolean updateUserPasswordValidateHandle(AjaxRequestTarget target, FeedbackPanel feedback)
	{
		boolean validate = true;
		if (!StringUtils.equals(repassword.getValue(), password.getValue())) {
			error("两次输入的密码不一致！");
			focusComponent(target, repassword, feedback);
			validate = false;
			return validate;
		}

		List<String> errorMsgs = matchesPassword(password.getValue());
		if (CollectionUtils.isNotEmpty(errorMsgs)) {
			for (String errorMsg : errorMsgs) {
				error(errorMsg);
				focusComponent(target, password, feedback);
			}
			validate = false;
			return validate;
		}
		target.add(feedback);
		return validate;

	}
}
