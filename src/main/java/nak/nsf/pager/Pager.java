package nak.nsf.pager;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Pager implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5568987450848048932L;

	/**
	 * 当前页码
	 */
	private int currentPage;

	/**
	 * 数据结束数字
	 */
	private int dataEnd;

	/**
	 * 数据开始数字
	 */
	private int dataStart;

	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage;

	/**
	 * 是否有上一页
	 */
	private boolean hasPreviousPage;

	/**
	 * 最后一页页码
	 */
	private int lastPage;

	/**
	 * 下一页页码
	 */
	private int nextPage;

	/**
	 * 当前页大小
	 */
	private int pageSize;

	/**
	 * 上一页页码
	 */
	private int previousPage;

	/**
	 * 限制页码
	 */
	private int quantityLimit;

	/**
	 * 数据长度
	 */
	private int quantityOfData;

	/**
	 * 
	 */
	public Pager()
	{
		super();
		initialize();
		currentPage = 0;
		pageSize = 0;
		quantityOfData = 0;
		compute();
	}

	/**
	 * 方法说明：compute
	 * 
	 */
	public void compute()
	{
		if (currentPage <= 0) {
			currentPage = 1;
		}

		if (pageSize <= 0) {
			pageSize = 15;
		}

		if (quantityOfData < 0) {
			quantityOfData = 0;
		}

		if (quantityLimit > 0 && quantityOfData > quantityLimit) {
			quantityOfData = quantityLimit;
		}

		if (0 != quantityOfData) {

			lastPage = quantityOfData / pageSize;

			if (0 != quantityOfData % pageSize) {
				lastPage += 1;
			}

			if (currentPage > lastPage) {
				currentPage = lastPage;
			}

			dataStart = (currentPage - 1) * pageSize;

			dataEnd = dataStart + pageSize - 1;

			if (dataEnd > quantityOfData) {
				dataEnd = quantityOfData;
			}

		} else {
			lastPage = 1;
			currentPage = 1;
		}

		if (currentPage != lastPage) {
			nextPage = currentPage + 1;
		} else {
			nextPage = -1;
		}

		if (currentPage != 1) {
			previousPage = currentPage - 1;
		} else {
			previousPage = -1;
		}

		if (currentPage < lastPage) {
			hasNextPage = true;
		} else {
			hasNextPage = false;
		}

		if (currentPage > 1) {
			hasPreviousPage = true;
		} else {
			hasPreviousPage = false;
		}
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public int getDataEnd()
	{
		return dataEnd;
	}

	public int getDataStart()
	{
		return dataStart;
	}

	public int getFirstPage()
	{
		return 1;
	}

	public int getLastPage()
	{
		return lastPage;
	}

	public int getNextPage()
	{
		return nextPage;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getPreviousPage()
	{
		return previousPage;
	}

	public int getQuantityLimit()
	{
		return quantityLimit;
	}

	public int getQuantityOfData()
	{
		return quantityOfData;
	}

	/**
	 * 方法说明：initialize
	 * 
	 */
	public void initialize()
	{
		this.currentPage = 1;
		this.pageSize = 20;
		this.quantityLimit = -1;
		this.quantityOfData = -1;
		this.lastPage = -1;
		this.dataStart = -1;
		this.dataEnd = -2;
		this.nextPage = 1;
		this.previousPage = 1;
		this.hasNextPage = false;
		this.hasPreviousPage = false;
	}

	public boolean isHasNextPage()
	{
		return hasNextPage;
	}

	public boolean isHasPreviousPage()
	{
		return hasPreviousPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public void setQuantityLimit(int quantityLimit)
	{
		this.quantityLimit = quantityLimit;
	}

	public void setQuantityOfData(int quantityOfData)
	{
		this.quantityOfData = quantityOfData;
	}

	public void setQuantityOfData(long quantityOfData)
	{
		this.quantityOfData = (int) quantityOfData;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * 方法说明：toString
	 *
	 * @return String
	 */
	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}
}
