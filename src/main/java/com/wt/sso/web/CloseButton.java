package com.wt.sso.web;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Button;

public class CloseButton extends Button
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8011695863813261425L;

	public CloseButton(String id)
	{
		super(id);

		add(new AbstractBehavior() {
			public void onComponentTag(Component component, ComponentTag tag)
			{
				tag.put("onclick", "window.opener=null;window.open('','_self');window.close();");
			}
		});
	}
}
