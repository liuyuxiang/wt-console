package com.wt.uum2.web.wicket.panel.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.Model;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

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
public class SyncOnOffPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	/**
	 * @param id
	 *            id
	 */
	public SyncOnOffPanel(String id)
	{
		super(id);
		Form<Void> eventForm = new Form<Void>("eventForm");
		add(eventForm);
		List<Boolean> radioList = new ArrayList<Boolean>();
		radioList.add(true);
		radioList.add(false);
		final Map<Boolean, String> RADIOMAP = new HashMap<Boolean, String>();
		RADIOMAP.put(true, "是");
		RADIOMAP.put(false, "否");
		ChoiceRenderer<Boolean> choiceRenderer = new ChoiceRenderer<Boolean>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Boolean object)
			{
				return RADIOMAP.get(object);
			}

		};
		final RadioChoice<Boolean> RADIOCHOICE = new RadioChoice<Boolean>("radio",
				new Model<Boolean>(getSyncOnOffService().isSyncOn()), radioList, choiceRenderer);
		eventForm.add(RADIOCHOICE.setSuffix(""));
		eventForm.add(new Button("submitForm") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				if (RADIOCHOICE.getModelObject()) {
					getSyncOnOffService().syncOn();
				} else {
					getSyncOnOffService().syncOff();
				}

			}

		});
	}

}
