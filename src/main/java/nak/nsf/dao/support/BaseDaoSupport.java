package nak.nsf.dao.support;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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
 * 
 * @param <T> 泛型
 */
public abstract class BaseDaoSupport<T> extends HibernateDaoSupport implements BaseDao<T>
{

	/**
	 * 方法说明：escapeSQLLike
	 * 
	 * @param likeStr
	 *            likeStr
	 * @return String
	 */
	public String escapeSQLLike(String likeStr)
	{// sql转换关键字
		String str = StringUtils.replace(likeStr, "_", "/_");
		str = StringUtils.replace(str, "%", "/%");
		return StringEscapeUtils.escapeSql(str);
	}

	/**
	 * 方法说明：利用分词手段来处理用户名及id达到模糊查询要求
	 * 
	 * @param str
	 * @return
	 */
	protected String vagueValueHandle(String str)
	{
		String key = "%";
		StringBuilder val = new StringBuilder(key);
		val.append(str);
		val.append(key);
		return val.toString();
	}

	/**
	 * 方法说明：利用分词手段来处理用户名及id达到模糊查询要求
	 * 
	 * @param str
	 * @return
	 */
	protected String vagueSqlValueHandle(String str)
	{
		return vagueValueHandle(escapeSQLLike(str));
	}

	protected boolean isMysqlDB() {
		try {
			String driverName = getSession().connection().getMetaData().getDriverName();
			return StringUtils.contains(driverName, "MySQL");
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];

	protected final Log logger = LogFactory.getLog(getClass());

	/* (non-Javadoc)
	 * @see nak.nsf.dao.support.BaseDao#delete(java.lang.Object)
	 */
	/**
	 * 方法说明：delete
	 * 
	 * @param object
	 *            object
	 */
	public void delete(T object)
	{
		getSession().delete(getSession().merge(object));
	}

	/* (non-Javadoc)
	 * @see nak.nsf.dao.support.BaseDao#read(java.lang.String)
	 */
	/**
	 * 方法说明：read
	 * 
	 * @param uuid
	 *            uuid
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T read(String uuid)
	{
		return (T) getSession().createCriteria(entityClass).add(Restrictions.idEq(uuid))
				.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see nak.nsf.dao.support.BaseDao#save(java.lang.Object)
	 */
	/**
	 * 方法说明：save
	 * 
	 * @param object
	 *            object
	 */
	public void save(T object)
	{
		getSession().save(object);
	}

	/* (non-Javadoc)
	 * @see nak.nsf.dao.support.BaseDao#saveOrUpdate(java.lang.Object)
	 */
	/**
	 * 方法说明：saveOrUpdate
	 * 
	 * @param object
	 *            object
	 */
	public void saveOrUpdate(T object)
	{
		getSession().saveOrUpdate(object);
	}

	/* (non-Javadoc)
	 * @see nak.nsf.dao.support.BaseDao#update(java.lang.Object)
	 */
	/**
	 * 方法说明：update
	 * 
	 * @param object
	 *            object
	 */
	public void update(T object)
	{
		getSession().update(getSession().merge(object));
	}

	/* (non-Javadoc)
	 * @see nak.nsf.dao.support.BaseDao#deleteAll(java.lang.String)
	 */
	/**
	 * 方法说明：deleteAll
	 * 
	 * @param className
	 *            className
	 */
	public void deleteAll(String className)
	{
		getSession().createQuery("delete " + className).executeUpdate();
	}
}
