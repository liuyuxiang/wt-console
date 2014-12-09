package com.wt.hea.common.model;

/***
 * 
 * <pre>
 * 业务名: SQL语句参数类型枚举定义
 * 功能说明:  SQL语句参数类型枚举定义
 * 编写日期:	2011-6-28
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-6-28
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public enum ParamType
{
	/****/
	ARRAY(2003),
	/****/
	BIGINT(-5),
	/****/
	BINARY(-2),
	/****/
	BIT(-7),
	/****/
	BLOB(2004),
	/****/
	BOOLEAN(16),
	/****/
	CHAR(1),
	/****/
	CLOB(2005),
	/****/
	DATALINK(70),
	/****/
	DATE(91),
	/****/
	DECIMAL(3),
	/****/
	DISTINCT(2001),
	/****/
	DOUBLE(8),
	/****/
	FLOAT(6),
	/****/
	INTEGER(4),
	/****/
	JAVA_OBJECT(2000),
	// LONGNVARCHAR(),
	/****/
	LONGVARBINARY(-4),
	/****/
	LONGVARCHAR(-1),
	// NCHAR,
	// NCLOB,
	/****/
	NULL(0),
	/****/
	NUMERIC(2),
	// NVARCHAR,
	/****/
	OTHER(1111),
	/****/
	REAL(7),
	/****/
	REF(2006),
	// ROWID,
	/****/
	SMALLINT(5),
	// SQLXML,
	// STRUCT,
	/****/
	TIME(92),
	/****/
	TIMESTAMP(93),
	/****/
	TINYINT(-6),
	/****/
	VARBINARY(-3),
	/****/
	VARCHAR(12);
	/****/
	@SuppressWarnings("unused")
	private final int value;

	/***
	 * xxx
	 * 
	 * @param value
	 *            xxs
	 */
	ParamType(int value)
	{
		this.value = value;
	}
}
