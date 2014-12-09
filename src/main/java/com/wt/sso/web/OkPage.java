package com.wt.sso.web;

import org.apache.wicket.markup.html.basic.Label;

public class OkPage extends BasePage {

	public OkPage(String message) {
		super();
		add(new Label("message", message));
	}

}
