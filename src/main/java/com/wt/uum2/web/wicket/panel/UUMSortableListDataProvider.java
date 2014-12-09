package com.wt.uum2.web.wicket.panel;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * <pre>
 * 业务名:用户列表数据提供
 * 功能说明: 可以再在其基础上添加排序功能
 * 编写日期:	2011-11-3
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
public class UUMSortableListDataProvider<T extends Serializable> extends SortableDataProvider<T>
{
	/**
	 * 
	 */
	private List<T> list;

	/**
	 * @param list
	 *            st
	 */
	public UUMSortableListDataProvider(List<T> list)
	{
		super();
		this.list = list;
	}

	/**
	 * @param first
	 *            first
	 * @param count
	 *            count
	 * @return 迭代
	 */
	public Iterator<? extends T> iterator(int first, int count)
	{
		return list.subList(first, first + count).iterator();
	}

	/**
	 * @return 列表长度
	 */
	public int size()
	{
		return list == null ? 0 : list.size();
	}

	/**
	 * @param object
	 *            object
	 * @return IModel
	 */
	public IModel<T> model(T object)
	{
		return Model.of(object);
	}

}
