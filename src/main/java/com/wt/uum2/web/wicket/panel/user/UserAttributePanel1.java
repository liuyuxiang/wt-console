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
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;

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
@Deprecated
public class UserAttributePanel1 extends BaseUUMPanel {

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
    public UserAttributePanel1(String id, Attribute attr) {
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
	initComponents();
    }

    /**
     * 方法说明：初始化
     * 
     */
    protected void initComponents() {
    	ChoiceRenderer renderer = new ChoiceRenderer() {

    	    @Override
    	    public Object getDisplayValue(Object object) {

    		return rendererMap.get(object) == null ? object : rendererMap
    			.get(object);
    	    }

    	    @Override
    	    public String getIdValue(Object object, int index) {
    		return object.toString();
    	    }

    	};
	DropDownChoice listChoice = new DropDownChoice("selectContent",
		new PropertyModel<String>(attribute, "value"), selectOptions,renderer);
	add(listChoice.setVisible(false));
	CheckBoxMultipleChoice checkBoxMultipleChoiceContent = new CheckBoxMultipleChoice(
		"checkBoxMultipleChoiceContent", new PropertyModel<String>(
			attribute, "value"), selectOptions,renderer);
	checkBoxMultipleChoiceContent.setVisible(false);
	add(checkBoxMultipleChoiceContent);

	RadioChoice radioChoiceContent = new RadioChoice("radioChoiceContent",
		new PropertyModel<String>(attribute, "value"), selectOptions,
		renderer);

	add(radioChoiceContent.setSuffix("").setVisible(false));

	TextArea textareaContent = new TextArea("textareaContent",
		new PropertyModel<String>(attribute, "value"));
	textareaContent.setVisible(false);
	add(textareaContent);

	// TextField text = new TextField("inputContent", new PropertyModel(
	// attribute, "value"));
	UUMAttributeTextField text = new UUMAttributeTextField("inputContent",
		new PropertyModel<String>(attribute, "value"), attribute);
	add(text.setVisible(false));
	add(new SimpleFeedbackPanel("feedback",
		new ComponentFeedbackMessageFilter(text))
		.setOutputMarkupId(true));
	Label label = new Label("labelContent",
		"1".equals(attribute.getValue()) ? "新增用户" : "ERP用户");
	add(label.setVisible(false));

	StringDateTextField dateTextField = new StringDateTextField(
		"dateContent", new PropertyModel(attribute, "value"),
		"yyyy/MM/dd");
	dateTextField.add(new DatePicker());
	add(dateTextField.setVisible(false));

	if (attrType.getPageType().equalsIgnoreCase("select")) {
	    listChoice.setVisible(true);
	    if (oldValue == null) {
		listChoice.setDefaultModelObject(selectedOption);
	    } else {
		listChoice.setDefaultModelObject(oldValue.toLowerCase());
	    }
	} else if (attrType.getPageType().equalsIgnoreCase("checkbox")) {
	    checkBoxMultipleChoiceContent.setVisible(true);
	    if (oldValue == null) {
		checkBoxMultipleChoiceContent
			.setDefaultModelObject(selectedOption);
	    } else {
		checkBoxMultipleChoiceContent.setDefaultModelObject(oldValue
			.toLowerCase());
	    }
	} else if (attrType.getPageType().equalsIgnoreCase("radio")) {
	    radioChoiceContent.setVisible(true);
	    if (oldValue == null) {
		radioChoiceContent.setDefaultModelObject(selectedOption);
	    } else {
		radioChoiceContent
			.setDefaultModelObject(oldValue.toLowerCase());
	    }
	} else if (attrType.getPageType().equalsIgnoreCase("textarea")) {
	    textareaContent.setVisible(true);
	} else {
	    if (attrType.getId().equals("dataCameFrom")) {
		label.setVisible(true);
	    } else if ("2".equals(attrType.getRule())) {
		dateTextField.setVisible(true);
	    } else {
		text.setVisible(true);
	    }
	}

    }

}
