package com.wt.uum2.constants;

import java.io.Serializable;
import java.util.List;

import nak.nsf.pager.Pager;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 * @param <T>
 */
public class UserPageResult<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2390354243197027609L;
	/**
	 * 分页数据
	 */
	private List<T> list;//
	/**
	 * 分页页码
	 */
	private Pager pager;//
		
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
}
