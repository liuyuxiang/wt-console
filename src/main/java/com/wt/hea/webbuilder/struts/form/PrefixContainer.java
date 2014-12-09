package com.wt.hea.webbuilder.struts.form;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-21
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PrefixContainer {

	/**
	 * 
	 */
	private List<Prefix> prefixes = new ArrayList<Prefix>();

	public List<Prefix> getPrefixes() {
		return prefixes;
	}

	public void setPrefixes(List<Prefix> prefixes) {
		this.prefixes = prefixes;
	}
}
