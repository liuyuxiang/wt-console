package com.wt.hea.common.util;

import org.hibernate.type.AbstractStandardBasicType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

/**
 * Hibernate数据库持久层dao抽象基类BaseDao帮助类
 * 
 * @author 袁明敏
 * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
 * 
 * @version 1.1
 * @since 2010-07-01
 * 
 * 
 * 
 * 
 */
public final class HibernateTypeHelper
{

	/**
	 * 
	 * 方法说明：处理时间、日期类型
	 * 
	 * @param param
	 *            参数
	 * @return 返回hibernate类型
	 */
	@SuppressWarnings({ "rawtypes" })
	private static AbstractStandardBasicType processTime(Object param)
	{
		// 日期、时间
		if (param instanceof java.sql.Date) {
			return StandardBasicTypes.DATE;
		}
		if (param instanceof java.sql.Time) {

			return StandardBasicTypes.TIME;
		}
		if (param instanceof java.sql.Timestamp) {
			return StandardBasicTypes.TIMESTAMP;
		}
		if (param instanceof java.util.Date) {
			return StandardBasicTypes.DATE;
		}
		return null;
	}

	/**
	 * 
	 * 方法说明：处理字符类型
	 * 
	 * @param param
	 *            参数
	 * @return 返回hibernate类型
	 */
	@SuppressWarnings({ "rawtypes" })
	private static AbstractStandardBasicType processString(Object param)
	{
		if (param instanceof java.lang.Character) {
			return StandardBasicTypes.CHARACTER;
		}
		if (param instanceof java.lang.String) {
			return StandardBasicTypes.STRING;
		}
		return null;
	}

	/**
	 * 
	 * 方法说明：处理数值型
	 * 
	 * @param param
	 *            参数
	 * @return 返回hibernate类型
	 */
	@SuppressWarnings("rawtypes")
	private static AbstractStandardBasicType processNumeric(Object param)
	{

		if (param instanceof java.lang.Byte) {
			return StandardBasicTypes.BYTE;
		}
		if (param instanceof java.lang.Integer) {
			return StandardBasicTypes.INTEGER;
		}
		if (param instanceof java.lang.Short) {
			return StandardBasicTypes.SHORT;
		}
		if (param instanceof java.lang.Float) {
			return StandardBasicTypes.FLOAT;
		}
		if (param instanceof java.lang.Long) {
			return StandardBasicTypes.LONG;
		}
		if (param instanceof java.lang.Double) {
			return StandardBasicTypes.DOUBLE;
		}
		if (param instanceof java.math.BigDecimal) {
			return StandardBasicTypes.BIG_DECIMAL;
		}
		if (param instanceof java.math.BigInteger) {
			return StandardBasicTypes.BIG_INTEGER;
		}
		return null;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param param
	 *            参数
	 * @return 返回hibernate类型
	 */
	public static Type filter(Object param)
	{
		@SuppressWarnings("rawtypes")
		AbstractStandardBasicType type = null;

		// 参数的字符类型
		type = processString(param);
		if (type != null) {
			return type;
		}

		// 参数的日期时间类型
		type = processTime(param);
		if (type != null) {
			return type;
		}

		// 参数据数值类型
		type = processNumeric(param);
		if (type != null) {
			return type;
		}

		// 参数的布尔类型
		if (param instanceof java.lang.Boolean) {
			return StandardBasicTypes.BOOLEAN;
		}

		if (type == null) {
			try {
				throw new TypeException("过滤参数值异常,没有找到JAVA基本数据类型!");
			} catch (TypeException e) {
				e.printStackTrace();
			}
		}

		return type;
	}
}
