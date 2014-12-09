package com.wt.uum2.web.wicket.page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.hirisun.components.other.project.ProjectResolver;
import com.hirisun.components.other.runtime.RuntimeResolver;
import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.wt.uum2.web.wicket.form.ErrorClassAppender;

/**
 * <pre>
 * 业务名:登录页面
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
public class LoginPage extends UUMBasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private static final Log LOGGER = LogFactory.getLog(LoginPage.class);

	/**
	 * 
	 */
	private PasswordTextField passwordField;

	/**
	 * 
	 */
	private TextField<String> usernameField;
	
	/**
	 * 
	 */
	private SimpleFeedbackPanel simpleFeedbackPanel;
	
	/**
	 * 
	 */
	private SimpleFeedbackPanel usernameFeedback;
	
	/**
	 * 
	 */
	public LoginPage() {

		super();
		
		Form<?> form = new Form<Void>("from") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				
				
			}

		};

		add(form);
		
		form.add(new AjaxButton("login"){
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(simpleFeedbackPanel);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				UsernamePasswordToken token = new UsernamePasswordToken(usernameField.getValue(), passwordField.getValue());
				
				try {
		            SecurityUtils.getSubject().login(token);
					setResponsePage(IndexPage.class);
		        } catch (AuthenticationException e) {
					error(getString("loginError"));
					target.add(simpleFeedbackPanel);
		        	LOGGER.error("Error authenticating. LoginServlet");
		            return ;
		        }
				
			}
			
		});

		form.add(new AjaxButton("reset")
        {

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(LoginPage.class);
			}

        }.setDefaultFormProcessing(false));

		usernameField = new RequiredTextField<String>("u",new Model<String>());
		usernameField.setLabel(Model.of(getString("username")));
		usernameField.add(new ErrorClassAppender());
		form.add(usernameField);

		usernameFeedback = new SimpleFeedbackPanel("usernameFeedback",
				new ComponentFeedbackMessageFilter(usernameField));
		form.add(usernameFeedback.setOutputMarkupId(true));

		passwordField = new PasswordTextField("p",new Model<String>());
		passwordField.setLabel(Model.of(getString("password")));
		passwordField.add(new ErrorClassAppender());
		if(RuntimeResolver.isDevMode()){
			passwordField.setRequired(false);
		}
		form.add(passwordField);

		form.add(new SimpleFeedbackPanel("passwordFeedback",
				new ComponentFeedbackMessageFilter(passwordField)).setOutputMarkupId(true));

		simpleFeedbackPanel = new SimpleFeedbackPanel("feedback");
		form.add(simpleFeedbackPanel.setOutputMarkupId(true));
	}
	
	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.renderCSSReference("style/" + ProjectResolver.getId()
				+ "/wstyle.css");
	}

}
