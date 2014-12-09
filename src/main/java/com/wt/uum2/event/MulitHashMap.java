package com.wt.uum2.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class MulitHashMap extends HashMap<String, List<String>>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1525318917695235938L;

	/**
	 * 
	 */
	private Map<String, Integer> count;

	/**
	 * @param initialCapacity
	 *            initialCapacity
	 */
	public MulitHashMap(int initialCapacity)
	{
		super(initialCapacity);
		count = new HashMap<String, Integer>();
	}

	@Override
	public List<String> get(Object key)
	{
		return super.get(key);
	}

	/**
	 * 方法说明：getSingle
	 * 
	 * @param key
	 *            key
	 * @return String
	 */
	public String getSingle(Object key)
	{
		if (isMulitValue((String) key)) {
			throw new IllegalArgumentException("the value is not single! size=" + count.get(key));
		}
		if (CollectionUtils.isEmpty(super.get(key))) {
			return null;
		}
		return super.get(key).get(0);
	}

	/**
	 * 方法说明：isMulitValue
	 * 
	 * @param key
	 *            key
	 * @return boolean
	 */
	public boolean isMulitValue(String key)
	{
		boolean isMulit = false;
		Integer countValue = count.get(key);
		if (countValue != null && countValue > 1) {
			isMulit = true;
		}
		return isMulit;
	}

	@Override
	public List<String> put(String key, List<String> value)
	{
		Integer countValue = count.get(key);
		if (countValue == null) {
			countValue = 1;
		} else {
			countValue++;
		}
		count.put(key, countValue);
		return super.put(key, value);
	}

}
