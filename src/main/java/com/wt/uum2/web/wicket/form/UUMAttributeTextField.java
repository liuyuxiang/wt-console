package com.wt.uum2.web.wicket.form;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;

/**
 * <pre>
 * 业  务  名:    
 * 功能说明: 添加扩展属性验证规则     
 * 编写日期: 2012-6-21
 * 作者: 朱宇航 - Hirisun
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UUMAttributeTextField extends TextField<String>
{

	/**
     * 
     */
	private static final long serialVersionUID = 1914500578828138646L;
	private static final Map<String, String> ATTRIBUTETYPERULE = new HashMap<String, String>();
	static {
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.NUMBER), "数字类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.DATE), "日期类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.EMPLOYEENUMBER), "身份证类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.WORD), "英文类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.W_N), "英数字类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.EMAIL), "电子邮箱类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.CHINESE), "中文类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.DATE), "电话号码类型");
		ATTRIBUTETYPERULE.put(String.valueOf(AttributeType.ERPCODE), "ERP编码类型");
	}
	private Attribute attribute;

	/**
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @param attribute
	 *            attribute
	 */
	public UUMAttributeTextField(String id, IModel<String> model, Attribute attribute)
	{
		super(id, model);
		setAttribute(attribute);
		if (attribute != null) {
			final AttributeType attrType = getAttribute().getType();
			if (attrType != null && "text".equals(attrType.getPageType())) {

				if (!StringUtils.equals(String.valueOf(AttributeType.NORMAL), attrType.getRule())
						&& StringUtils.isNotBlank(attrType.getValue())) {
					add(new PatternValidator(attrType.getValue()){
						@Override
						protected String resourceKey() {
							if(StringUtils.isNotBlank(attrType.getRule())){
								return attrType.getRule();
							}
							return super.resourceKey();
						}
					});
					setLabel(new Model<String>(ATTRIBUTETYPERULE.get(attrType.getRule())));
				}
				if (attrType.getLength() != null)
					add(new StringValidator.MaximumLengthValidator(attrType.getLength().intValue()));
			}
		}
	}

	public Attribute getAttribute()
	{
		return attribute;
	}

	public void setAttribute(Attribute attribute)
	{
		this.attribute = attribute;
	}
	
	
	@Override
	public void validate() {
		super.validate();
		
	}
}
