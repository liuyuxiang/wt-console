package com.wt.sso.web;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

public class ErrorClassAppender extends AbstractBehavior {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8227217053833521726L;

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		if (((FormComponent<?>) component).isValid() == false) {
			String cl = tag.getAttribute("class");
			if (cl == null) {
				tag.put("class", "error");
			} else {
				tag.put("class", "error " + cl);
			}
		}
	}
}
