package com.wt.hea.common.util;

/**
 * 
 * @author 操作数据结果集接口定义
 * 
 * @param <T>
 */
public interface IDataResultSet<T> extends Iterable<T>
{
	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean add(T e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param index
	 *            index
	 * @param e
	 *            e
	 */
	public void add(int index, T e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param index
	 *            index
	 * @return T
	 */
	T get(int index);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return boolean
	 */
	boolean isEmpty();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param index
	 *            index
	 * @return T
	 * 
	 */
	public T remove(int index);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean remove(T e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return int
	 */
	public int size();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return T[]
	 */
	T[] toArray();
}
