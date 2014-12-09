package com.wt.hea.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 数据行的封装
 * 
 * @author 袁明敏
 * 
 */
public class DataRow implements IDataResultSet<Object> {
	/**
	 * 
	 */
	private List<Object> elements = new ArrayList<Object>();

	/**
	 * 
	 */
	public DataRow() {
	};

	/**
	 * 
	 * @param elements
	 *            数据行的元素
	 */
	public DataRow(Object[] elements) {
		this.elements = Arrays.asList(elements);
	}

	/**
	 * 
	 * @param elements
	 *            数据行的元素
	 */
	public DataRow(List<Object> elements) {
		this.elements = elements;
	}

	/**
	 * 添加某个元素到某行的某个位置
	 * 
	 * @param index
	 *            索引
	 * @param e
	 *            数据
	 * 
	 */
	public void add(int index, Object e) {
		this.elements.add(index, e);
	}

	/**
	 * 添加元素到行
	 * 
	 * @param e
	 *            数据
	 * @return 添加成功返回真
	 */
	public boolean add(Object e) {
		return this.elements.add(e);
	}

	/**
	 * 获取当前行的某个位置元素
	 * 
	 * @param index
	 *            索引
	 * @return 数据对象
	 */
	public Object get(int index) {
		return this.elements.get(index);
	}

	/**
	 * 判断当前行是否为空
	 * 
	 * @return 为空返回真
	 */
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	/**
	 * 快速访问
	 * 
	 * @return iterate
	 */
	public Iterator<Object> iterator() {
		return this.elements.iterator();
	}

	/**
	 * 删除某索引的元素
	 * 
	 * @param index
	 *            索引
	 * @return 返回已删的对象
	 */
	public Object remove(int index) {
		return this.elements.remove(index);
	}

	/**
	 * 删除对象
	 * 
	 * @param e
	 *            e
	 * @return 删除成功返回真
	 */
	public boolean remove(Object e) {
		return this.elements.remove(e);
	}

	/**
	 * 返回行内元素个数
	 * 
	 * @return 元素大小
	 */
	public int size() {
		return this.elements.size();
	}

	/**
	 * 行元素转化为数组
	 * 
	 * @return array
	 */
	public Object[] toArray() {
		return this.elements.toArray(new Object[this.elements.size()]);
	}

}
