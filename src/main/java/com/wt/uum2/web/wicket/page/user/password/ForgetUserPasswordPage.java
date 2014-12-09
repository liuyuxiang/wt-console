package com.wt.uum2.web.wicket.page.user.password;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.page.UUMBasePage;

/**
 * <pre>
 * 业务名:忘记密码功能
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
public class ForgetUserPasswordPage extends UUMBasePage
{
	/**
	 * 输入框
	 */
	private RequiredTextField<String> userid;
	/**
	 * 
	 */
	public ForgetUserPasswordPage()
	{
		Form<Void> form = new Form<Void>("form");
		add(form);
		final SimpleFeedbackPanel FEEDBACK=new SimpleFeedbackPanel("feedback");
		form.add(FEEDBACK.setOutputMarkupId(true));
		userid=new RequiredTextField<String>("userid",new Model<String>());
		
		form.add(userid.setOutputMarkupId(true));
		AjaxButton submitbutton=new AjaxButton("submitbutton"){

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				focusComponent(target,userid,FEEDBACK);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				
				String msg=	checkUserStatus(userid.getValue());
				if(StringUtils.isNotBlank(msg)){
					info(msg);
					focusComponent(target,userid,FEEDBACK);
					return;
				}
				msg="操作成功，请留意邮件！";
				info(msg);
				confirmCloseHandle(msg,target);
				focusComponent(target,userid,FEEDBACK);
			}
			
		};
		form.add(submitbutton);
		
		AjaxButton resetbutton=new AjaxButton("resetbutton"){

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				setResponsePage(ForgetUserPasswordPage.class);
			}
			
		};
		form.add(resetbutton.setDefaultFormProcessing(false));
		
	}
	/**
	 * 方法说明：检查输入用户ID是否正常
	 *
	 * @param userId userId
	 * @return 检查信息
	 */
	public String checkUserStatus(String userId){
		String msg = null;
		if (!getUUMService().existUserId(userId)) {
			msg = "不存在该用户，或者账号异常，请联系管理员！";
			return msg;
		}
		User user = getUUMService().getUserByUserId(userId);
		List<String> keys = new ArrayList<String>();
		keys.add("mail");
		String address = null;
		try {
			address = getUUMService().getAttributesByAttributeTypeIdKey(user, keys).get(0).getValue();
		} catch (Exception e) {
			msg = "该用户不存在邮箱账号，或者账号异常，请联系管理员！";
			e.printStackTrace();
			return msg;
		}
		if(address==null){
			msg = "该用户不存在邮箱账号，或者账号异常，请联系管理员！";
			return msg;
		}
		if (address.indexOf("@") == -1) {
			msg = "该用户不存在邮箱账号，或者账号异常，请联系管理员！";
			return msg;
		}
		InternetAddress to = new InternetAddress();
		to.setAddress(address);
		try {
			to.setPersonal(user.getId() + ";" + user.getName());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			msg = "用户异常，请联系管理员！";
			return msg;
		}
		getSendMail().resetPasswordMail(to);
		return msg;
	}
	
	/**
	 * 方法说明：焦点
	 *
	 * @param target target
	 * @param component component
	 * @param feedback feedback
	 */
	public void focusComponent(AjaxRequestTarget target,Component component,FeedbackPanel feedback){
		target.appendJavaScript("$('#"+component.getMarkupId()+"').select();");
		target.add(feedback);
		target.focusComponent(component);
		target.add(component);
	}
}
