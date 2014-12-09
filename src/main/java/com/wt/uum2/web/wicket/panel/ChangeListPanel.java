package com.wt.uum2.web.wicket.panel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.Model;

/**
 * <pre>
 * 业务名:修改列表抽象类
 * 功能说明: 
 * 编写日期:	2011-9-27
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 * 
 * @param <T>
 *            类型
 */
public abstract class ChangeListPanel<T> extends FormComponentPanel<T>
{


	/**
	 * 
	 */
	private List<T> list;

	/**
	 * 
	 */
	private List<T> originalList;

	/**
	 * 
	 */
	private List<T> addList;

	/**
	 * 
	 */
	private List<T> removeList;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3894884509950758510L;

	/**
	 * @param id id
	 * @param list list
	 */
	public ChangeListPanel(String id, List<T> list) {
		super(id, new Model());
		initList(list);
	}

	/**
	 * 方法说明：初始化LIST
	 * 
	 * @param list
	 *            list
	 */
	private void initList(List<T> list) {
		if(list!=null){
			this.originalList = list;
		}else{
			this.originalList=new ArrayList<T>();
		}
		this.list = new LinkedList<T>(this.originalList);

		this.addList = new LinkedList<T>();
		this.removeList = new LinkedList<T>();
	}
	
	public List<T> getList() {
		return this.list;
	}

	public List<T> getAddList() {
		return this.addList;
	}

	public List<T> getRemoveList() {
		return this.removeList;
	}

	/**
	 * 方法说明：从列表删除一项
	 * 
	 * @param item
	 *            一项
	 */
	public void remove(T item) {

		addList.remove(item);

		if (originalList.contains(item)) {
			removeList.add(item);
		}

		list.remove(item);
	}

	/**
	 * 方法说明：向列表添加一项
	 * 
	 * @param item
	 *            一项
	 */
	public void add(T item)
	{
		removeList.remove(item);

		if (!originalList.contains(item)) {
			addList.add(item);
		}

		list.add(item);
	}

	/**
	 * 方法说明：
	 * 
	 */
	public void clear()
	{
		addList.clear();
		list.clear();
		removeList.clear();
		removeList.addAll(originalList);
	}
}
