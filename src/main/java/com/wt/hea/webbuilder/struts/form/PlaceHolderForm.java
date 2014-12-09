package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.webbuilder.model.PlaceHolder;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-20
 * 作者:	Mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class PlaceHolderForm extends ActionForm {

	/**
	 * 
	 */
	private PlaceHolder placeHolder;

	public PlaceHolder getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(PlaceHolder placeHolder) {
		this.placeHolder = placeHolder;
	}
}
