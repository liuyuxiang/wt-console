package com.wt.uum2.web.wicket;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.model.IModel;

/**
 * <pre>
 * 业务名:日期控件
 * 功能说明: 
 * 编写日期:	2011-9-27
 * 作者:	Xiaoguoying
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class StringDateTextField extends TextField<String> implements
		ITextFormatProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String textFormat;

	/**
	 * @param id id
	 * @param type id
	 * @param textFormat id
	 */
	public StringDateTextField(String id, Class<String> type,String textFormat) {
		super(id, type);
		this.textFormat=textFormat;
	}

	/**
	 * @param id id
	 * @param model model
	 * @param type type
	 * @param textFormat textFormat
	 */
	public StringDateTextField(String id, IModel<String> model,
			Class<String> type,String textFormat) {
		super(id, model, type);
		this.textFormat=textFormat;
	}

	/**
	 * @param id id
	 * @param model model
	 * @param textFormat textFormat
	 */
	public StringDateTextField(String id, IModel<String> model,String textFormat) {
		super(id, model);
		this.textFormat=textFormat;
	}

	/**
	 * @param id id
	 * @param textFormat textFormat
	 */
	public StringDateTextField(String id,String textFormat) {
		super(id);
		this.textFormat=textFormat;
	}

	public String getTextFormat() {
		return textFormat;
	}

}
