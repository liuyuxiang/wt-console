package com.wt.hea.common.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.wt.hea.common.model.ParamType;

/***
 * 
 * @author 袁明敏
 * @see org.springframework.jdbc.core.support.JdbcDaoSupport
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
@SuppressWarnings("unchecked")
public class JdbcDao extends JdbcDaoSupport
{

	/**
	 * 批量执行insert ,update ,delet 语句
	 * 
	 * @param sqls
	 *            多条sql语句
	 * @return 返回影响的行数
	 */
	public int batchUpdate(String... sqls)
	{
		int[] refectRows = getJdbcTemplate().batchUpdate(sqls);

		int cnt = 0;
		for (int i = 0; i < refectRows.length; i++) {
			cnt += refectRows[i];
		}
		return cnt;
	}

	/**
	 * 查询单个结果,如：select count(*) from table where ...
	 * 
	 * @param sql
	 *            查询语句,如:select * from tab t where t.filed1=? and t.filed2 like '%?%' ...
	 * @param paramValues
	 *            参数数组
	 * @return 返回单个结果值
	 */
	public int queryForInt(String sql, Object... paramValues)
	{
		return getJdbcTemplate().queryForInt(sql, paramValues);
	}

	/***
	 * 跟据带参数的sql查询得到结果集
	 * 
	 * @param sql
	 *            sql语句
	 * @param paramValues
	 *            sql语句传入的参数
	 * @return 返回二维结果集
	 */
	@SuppressWarnings("rawtypes")
	public Object[][] queryForResultSet(String sql, Object... paramValues)
	{

		final List LIST = new ArrayList();
		getJdbcTemplate().query(sql, paramValues, new RowCallbackHandler() {

			public void processRow(ResultSet rs) throws SQLException
			{
				List<Object> row = new ArrayList<Object>();

				int columnCount = rs.getMetaData().getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					row.add(rs.getObject(i));
				}
				LIST.add(row);
			}
		});

		// 处理二元数组
		Object[][] result = null;
		if (LIST.size() > 0) {
			List col = (List) LIST.get(0);
			if (col.size() > 0) {
				result = new Object[LIST.size()][col.size()];

				for (int i = 0; i < LIST.size(); i++) {
					List column = (List) LIST.get(i);

					for (int k = 0; k < column.size(); k++) {
						result[i][k] = column.get(k);
					}
				}
			}
		}

		return result;
	}

	/**
	 * 执行单条sql语句，
	 * 
	 * @param tableName
	 *            表名
	 * @param map
	 *            字段值:
	 * @return 返回插入是否成功
	 */
	public Boolean insert(String tableName, Map<String, Object> map)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + tableName + " ( ");

		String[] keys = map.keySet().toArray(new String[map.keySet().size()]);
		for (int i = 0; i < keys.length; i++) {
			if (i != keys.length - 1) {
				sql.append(keys[i] + " , ");
			} else {
				sql.append(keys[i]);
			}
		}
		sql.append(" ) values (");

		for (int i = 0; i < keys.length; i++) {
			if (i != keys.length - 1) {
				sql.append(" ? , ");
			} else {
				sql.append(" ? ");
			}
		}

		sql.append(" ) ");

		int effectRows = getJdbcTemplate().update(sql.toString(), map.values().toArray());
		return (effectRows > 0);

	}

	/**
	 * 
	 * 方法说明：存储过程调用，带结果输出(数据库执行后的返回结果输出)
	 * 
	 * @param procName
	 *            存储过程名称
	 * @param paramsValue
	 *            参数值
	 * @param map
	 *            输出参数map ， 键为参数所在的索引，值为数据类型
	 * @return 返回数据库执行过程后的返回值
	 */
	public Object excuteProcedure(final String procName, final Object[] paramsValue,
			final Map<Integer, ParamType> map)
	{
		String msg = this.getJdbcTemplate().execute(new ConnectionCallback<String>() {
			public String doInConnection(Connection conn) throws SQLException, DataAccessException
			{
				StringBuffer sql = new StringBuffer("{ ");

				sql.append(" ( ");
				for (int i = 0; i < paramsValue.length; i++) {
					if (i != paramsValue.length - 1) {
						sql.append(" ?, ");
					} else {
						sql.append(" ? ");
					}
				}
				sql.append(" ) ");
				sql.append(" }");
				CallableStatement proc = conn.prepareCall(sql.toString());
				for (int i = 0; i < paramsValue.length; i++) {
					proc.setObject(i + 1, paramsValue[i]); // 设置参数值
					Set<Entry<Integer, ParamType>> set = map.entrySet();
					for (Entry<Integer, ParamType> e : set) {
						if (e.getKey().intValue() == i + 1) {
							// Types t=e.getValue();
							// proc.registerOutParameter(i+1, );
							break;
						}
					}
				}

				proc.execute();
				return proc.getString(3);
			}
		});
		return msg;
	}

	/**
	 * 
	 * 方法说明：处理参数类型
	 * 
	 * @param p
	 *            参数类型
	 */
	@SuppressWarnings("unused")
	private void preparedProcessParamTypes(ParamType p)
	{
	}
}
