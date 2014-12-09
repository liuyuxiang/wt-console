package com.wt.uum2.web.wicket.common.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.Model;

/**
 * <pre>
 * 业务名:LIST操作PANEL
 * 功能说明: 
 * 编写日期:	2014年4月8日
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class ChangeListPanel<T> extends FormComponentPanel
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
	 * @param id
	 *            id
	 * @param list
	 *            list
	 */
	public ChangeListPanel(String id, List<T> list)
	{
		super(id, new Model());
		initList(list);
	}

	/**
	 * 方法说明：
	 * 
	 * @param list
	 *            list
	 */
	private void initList(List<T> list)
	{
		if (list != null) {
			this.originalList = list;
		} else {
			this.originalList = new ArrayList<T>();
		}
		this.list = new LinkedList<T>(this.originalList);

		this.addList = new LinkedList<T>();
		this.removeList = new LinkedList<T>();
	}

	public List<T> getOriginalList()
	{
		return originalList;
	}

	public List<T> getList()
	{
		return this.list;
	}

	public List<T> getAddList()
	{
		return this.addList;
	}

	public List<T> getRemoveList()
	{
		return this.removeList;
	}

	/**
	 * 方法说明：
	 * 
	 * @param item
	 *            item
	 */
	public void remove(T item)
	{

		addList.remove(item);

		if (originalList.contains(item)) {
			removeList.add(item);
		}

		list.remove(item);
	}

	/**
	 * 方法说明：
	 * 
	 * @param item
	 *            item
	 */
	public void add(T item)
	{
		removeList.remove(item);

		if (!originalList.contains(item) && !addList.contains(item)) {
			addList.add(item);
		}
		if (!list.contains(item)) {
			list.add(item);
		}
	}

	public void clear()
	{
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
			T t = iterator.next();
			iterator.remove();
			remove(t);
		}
	}
}
