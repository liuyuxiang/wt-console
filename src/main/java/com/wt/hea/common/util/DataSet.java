package com.wt.hea.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 存储过程数据输出类,兼带输出参数加输出结果集.或两者都有之
 * 
 * @author 袁明敏
 * 
 */
public class DataSet implements IDataResultSet<DataTable> {
	/** 数据表格 **/
	/**
	 * 
	 */
	private final List<DataTable> dataTables = new ArrayList<DataTable>();

	/** 输出参数 **/
	/**
	 * 
	 */
	private Object[] outputParams = null;

	/**
	 * 
	 * 方法说明： 获取输出参数
	 * 
	 * @return Object[] 返回输出参数
	 */
	public Object[] getOutputParams() {
		return outputParams;
	}

	/**
	 * 
	 * 方法说明： 设置输出参数
	 * 
	 * @param outputParams
	 *            设置输出参数
	 */
	public void setOutputParams(Object[] outputParams) {
		this.outputParams = outputParams;
	}

	/**
	 * 
	 * 方法说明： 设置输出参数
	 * 
	 * @return DataTable[] 转化数组
	 */
	public DataTable[] toArray() {
		return this.dataTables.toArray(new DataTable[this.dataTables.size()]);
	}

	/**
	 * 
	 * 方法说明： 添加数据表格
	 * 
	 * @param dataTable
	 *            数据表格
	 * @return DataTable[] 转化数组
	 */
	public boolean add(DataTable dataTable) {
		return this.dataTables.add(dataTable);
	}

	/**
	 * 方法说明:添加数据表
	 * 
	 * @param index
	 *            表格列表索引
	 * @param dataTable
	 *            数据表格
	 * 
	 */
	public void add(int index, DataTable dataTable) {
		this.dataTables.add(index, dataTable);

	}

	/**
	 * 方法说明:获取数据表格
	 * 
	 * @param index
	 *            表格列表索引
	 * @return dataTable 返回数据表格
	 */
	public DataTable get(int index) {
		return this.dataTables.get(index);
	}

	/**
	 * 方法说明:是否为空
	 * 
	 * @return boolean 如果为空返回真
	 * 
	 */
	public boolean isEmpty() {
		return this.dataTables.isEmpty();
	}

	/**
	 * 方法说明:跟据索引号移除表格
	 * 
	 * @param index
	 *            索引号
	 * @return DataTable 返回移除的数据表格
	 */
	public DataTable remove(int index) {
		return this.dataTables.remove(index);
	}

	/**
	 * 方法说明:跟据索引号移除表格
	 * 
	 * @param dataTable
	 *            数据表格
	 * @return DataTable 返回移除的数据表格
	 */
	public boolean remove(DataTable dataTable) {
		return this.dataTables.remove(dataTable);
	}

	/**
	 * 方法说明:返回数据表格大小
	 * 
	 * @return int　数据集内的数据表格大小
	 */
	public int size() {
		return this.dataTables.size();
	}

	/**
	 * 方法说明:数据表格iterate
	 * 
	 * @return 返回iterante对象
	 */
	public Iterator<DataTable> iterator() {
		return this.dataTables.iterator();
	}
}
