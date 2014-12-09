package com.wt.sso.web;

import org.apache.wicket.markup.html.basic.Label;

public class ErrorPage extends BasePage {

	public ErrorPage(String errorMessage) {
		super();
		add(new Label("message", errorMessage));
	}

}
