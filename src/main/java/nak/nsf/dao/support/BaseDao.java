package nak.nsf.dao.support;

/**
 * <pre>
 * 业务名:
 * 功能说明: 基础Dao接口
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 * 
 * @param <T> 泛型
 */
public interface BaseDao<T>
{

	/**
	 * 删除对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
	public void delete(T object);

	/**
	 * 得到对象映射数据
	 * <p>
	 * 
	 * @param uuid
	 *            主键
	 * @return orm类实例
	 */
	public T read(String uuid);

	/**
	 * 保存新对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
	public void save(T object);

	/**
	 * 保存或更新对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
	public void saveOrUpdate(T object);

	/**
	 * 更新对象映射数据
	 * <p>
	 * 
	 * @param object
	 *            orm类实例
	 */
	public void update(T object);

	/**
	 * 方法说明：deleteAll
	 * 
	 * @param className
	 *            className
	 */
	public void deleteAll(String className);
}
