package com.wt.uum2.web.wicket.panel.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.hirisun.components.web.wicket.datepicker.DatePickerPanel;
import com.hirisun.components.web.wicket.datepicker.WdatePicker;
import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.CandidateItem;
import com.wt.uum2.web.wicket.StringDateTextField;
import com.wt.uum2.web.wicket.form.UUMAttributeTextField;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:
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
public class UserAttributePanel extends BaseUUMPanel {

	

	/**
	 * 
	 */
	private final Attribute attribute;
	/**
	 * 
	 */
	private final List<String> selectOptions;
	/**
	 * 
	 */
	private final AttributeType attrType;
	/**
	 * 
	 */
	private final Map<String, String> rendererMap;
	/**
	 * 
	 */
	private String selectedOption;

	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8312077966277099069L;

	/**
	 * 
	 */
	private final String oldValue;

	public void setTouch(boolean touch) {
		this.touch = touch;
	}

	/**
	 * 
	 */
	private boolean touch = false;

	public String getOldValue() {
		return oldValue;
	}

	/**
	 * 方法说明：值是否改变
	 * 
	 * @return boolean
	 */
	public final boolean isChanged() {
		if (touch) {
			return touch;
		} else {
			return !StringUtils.equals(oldValue, attribute.getValue());
		}
	}

	public final boolean isNew() {
		// TODO: attribute数据清理完成后(没有value==null的情况下),需要更换为注释方法
		// return oldValue == null && isChanged() ;
		return attribute.getUuid() == null && isChanged();
	}

	public final boolean isDelete() {
		return isChanged() && attribute.getValue() == null;
	}

	/**
	 * @param id
	 *            id
	 * @param attr
	 *            属性
	 */
	public UserAttributePanel(String id, Attribute attr) {
		super(id);

		oldValue = attr.getValue();
		// attrType =
		// getUUMService().getAttributeTypeByUuid(attr.getTypeUUID());
		attrType = attr.getType();
		attribute = attr;
		selectOptions = new ArrayList<String>();
		rendererMap = new HashMap<String, String>();

		List<CandidateItem> candidateItems = getUUMService()
				.getCandidateItemsByAttributeType(attrType);

		for (CandidateItem item : candidateItems) {
			selectedOption = candidateItems.get(0).getValue().toLowerCase();
			selectOptions.add(item.getValue().toLowerCase());
			rendererMap.put(item.getValue().toLowerCase(), item.getCaption());
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		String id="attribute";
		ChoiceRenderer<String> renderer = new ChoiceRenderer<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public Object getDisplayValue(String object) {
				return rendererMap.get(object) == null ? object
						: rendererMap.get(object);
			}
			@Override
			public String getIdValue(String object, int index) {
				return object.toString();
			}
		};
		
		if (attrType.getPageType().equalsIgnoreCase("select")) {
			DropDownChoicePanel listChoice = new DropDownChoicePanel(id,
					new PropertyModel<String>(attribute, "value"),
					selectOptions, renderer);
			add(listChoice);
			if (oldValue == null) {
				listChoice.setDefaultModelObject(selectedOption);
			} else {
				listChoice.setDefaultModelObject(oldValue.toLowerCase());
			}
		} else if (attrType.getPageType().equalsIgnoreCase("checkbox")) {
			
			CheckBoxMultipleChoicePanel checkBoxMultipleChoiceContent = new CheckBoxMultipleChoicePanel(
					id, selectOptions, renderer);
			add(checkBoxMultipleChoiceContent);
			if (oldValue == null) {
				checkBoxMultipleChoiceContent
						.setDefaultModelObject(selectedOption);
			} else {
				checkBoxMultipleChoiceContent.setDefaultModelObject(oldValue
						.toLowerCase());
			}
		} else if (attrType.getPageType().equalsIgnoreCase("radio")) {
			
			RadioChoicePanel radioChoiceContent = new RadioChoicePanel(id,
					new PropertyModel<String>(attribute, "value"), selectOptions,
					renderer);

			add(radioChoiceContent);
			if (oldValue == null) {
				radioChoiceContent.setDefaultModelObject(selectedOption);
			} else {
				radioChoiceContent
						.setDefaultModelObject(oldValue.toLowerCase());
			}
		} else if (attrType.getPageType().equalsIgnoreCase("textarea")) {
			TextArea textareaContent = new TextArea(id,
					new PropertyModel<String>(attribute, "value"));
			add(textareaContent);			
		} else {
			if (attrType.getId().equals("dataCameFrom")) {
				Label label = new Label(id,
						"1".equals(attribute.getValue()) ? "新增用户" : "ERP用户");
				add(label);
			} else if ("2".equals(attrType.getRule())) {
				String FMT_DATE_TIME = "yyyy/MM/dd";
				WdatePicker wpPicker = new WdatePicker();
				wpPicker.setDateFmt(FMT_DATE_TIME);
				wpPicker.setReadOnly(false);
				wpPicker.setIsShowClear(true);
				add(new DatePickerPanel<String>(id,new PropertyModel(attribute, "value"), wpPicker));
			} else {
				InputPanel text = new InputPanel(id,
						new PropertyModel<String>(attribute, "value"), attribute);
				add(text);
			}
		}
	}

	class DropDownChoicePanel extends Panel{

		public DropDownChoicePanel(final String id, IModel<String> model, final List<String> choices,
				final IChoiceRenderer<String> renderer) {
			super(id, model);
			DropDownChoice<String> listChoice = new DropDownChoice<String>("select",
					model,
					choices, renderer);
			add(listChoice);
		}	
	}
	class CheckBoxMultipleChoicePanel extends Panel{

		public CheckBoxMultipleChoicePanel(final String id, final List<String> choices,
				final IChoiceRenderer<String> renderer) {
			super(id);
			CheckBoxMultipleChoicePanel checkBoxMultipleChoiceContent = new CheckBoxMultipleChoicePanel(
					"checkBoxMultipleChoice", choices, renderer);
			add(checkBoxMultipleChoiceContent);
		}	
	}
	class RadioChoicePanel extends Panel{

		public RadioChoicePanel(final String id, IModel<String> model,final List<String> choices,
				final IChoiceRenderer<String> renderer) {
			super(id,model);
			RadioChoice<String> radioChoiceContent = new RadioChoice<String>("radiochoice",model, choices,
					renderer);

			add(radioChoiceContent.setSuffix(""));
		}	
	}
	class InputPanel extends Panel{

		public InputPanel(final String id, IModel<String> model,Attribute attribute) {
			super(id,model);
			UUMAttributeTextField text = new UUMAttributeTextField("input",
					model, attribute);
			add(text);
			add(new SimpleFeedbackPanel("feedback",
					new ComponentFeedbackMessageFilter(text))
					.setOutputMarkupId(true));
		}	
	}
	
}
