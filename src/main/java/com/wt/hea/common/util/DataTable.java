package com.wt.hea.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 数据表格的封装实现 表格内装载数据行
 * 
 * @author 袁明敏
 * 
 */
public class DataTable implements IDataResultSet<DataRow> {
	/**
	 * 
	 */
	private final List<DataRow> dataRow = new ArrayList<DataRow>();

	/**
	 * 添加数据行
	 * 
	 * @param e
	 *            数据行对象
	 * @return 添加成功返回真
	 */
	public boolean add(DataRow e) {
		return dataRow.add(e);
	}

	/**
	 * 添加数据行
	 * 
	 * @param index
	 *            索引
	 * @param e
	 *            数据行
	 */
	public void add(int index, DataRow e) {
		this.dataRow.add(index, e);
	}

	/**
	 * 方法说明:返回数据行
	 * 
	 * @param index
	 *            行索引
	 * @return DataRow 返回索引数据行
	 */
	public DataRow get(int index) {
		return this.dataRow.get(index);
	}

	/**
	 * 方法说明:判断数据行是否为空
	 * 
	 * @return 如果为空返回真
	 */
	public boolean isEmpty() {
		return this.dataRow.isEmpty();
	}

	/**
	 * 方法说明:移除数据行
	 * 
	 * @param index
	 *            行索引
	 * @return DataRow 返回已移除的数据行
	 */
	public DataRow remove(int index) {
		return this.dataRow.remove(index);
	}

	/**
	 * 方法说明:移除数据行
	 * 
	 * @param e
	 *            数据行
	 * @return boolean 移除成功返回真
	 */
	public boolean remove(DataRow e) {
		return this.dataRow.remove(e);
	}

	/**
	 * 方法说明:数据行大小
	 * 
	 * @return 数据行大小
	 */
	public int size() {
		return this.dataRow.size();
	}

	/**
	 * 方法说明:　转化为数据行数组　
	 * 
	 * @return 数据行数组
	 */
	public DataRow[] toArray() {
		return this.dataRow.toArray(new DataRow[this.dataRow.size()]);
	}

	/**
	 * 方法说明: 返回数据行iterator
	 * 
	 * @return 数据行iterator
	 */
	public Iterator<DataRow> iterator() {
		return this.dataRow.iterator();
	}

	/**
	 * 
	 * 方法说明：返回行集
	 * 
	 * @return 返回行集
	 */
	public Collection<DataRow> getRows() {
		return this.dataRow;
	}

}
