package com.wt.uum2.web.wicket.form;


import org.apache.commons.lang.StringUtils;
import org.apache.wicket.validation.validator.PatternValidator;

public class AttributePatternValidator extends PatternValidator {

	private String resourceKey;
	public AttributePatternValidator(String pattern,String resourceKey) {
		super(pattern);
		this.resourceKey=resourceKey;
	}

	@Override
	protected String resourceKey() {
		if(StringUtils.isBlank(resourceKey)){
			return super.resourceKey();
		}
		return resourceKey;
	}
}
