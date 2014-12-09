package com.wt.uum2.constants;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * 业务名:日期类型格式处理方法
 * 功能说明: 
 * 编写日期:	2011-3-23
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UUMDateFormat extends java.text.DateFormat
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3069368573463451829L;

	/**
	 * 
	 * 方法说明：将字符串（yyyy-MM-dd HH:mm:ss）转换为日期型
	 *
	 * @param sDate "yyyy-MM-dd HH:mm:ss"格式字符串
	 * @return 转换后的日期型
	 */
	public Date switchStringToDate(String sDate)
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = df.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * 方法说明：取得n个月后的日期,返回Long型
	 *
	 * @param date 时间节点
	 * @param n 月数
	 * @return this Calendar's time value in milliseconds
	 */
	private Long getLongAfterCurrentDateWithMonth(Date date, int n)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTimeInMillis();
	}

	/**
	 * 
	 * 方法说明：取得n个月后的日期,返回Date型
	 *
	 * @param date 时间节点
	 * @param n 月数
	 * @return a Date object representing this Calendar's time value 
	 */
	public Date getDateAfterCurrentDateWithMonth(Date date, int n)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 
	 * 方法说明：将long型字符串转换为"yyyy-MM-dd HH:mm:ss"格式的字符串
	 *
	 * @param lDate 长整形
	 * @return 天数
	 */
	public String switchLongToDateFormat(Long lDate)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date(lDate));
	}

	/**
	 * 
	 * 方法说明：取得到期天数
	 *
	 * @param changeTime (修改时间)
	 * @param time (相差月份数)
	 * @return 天数
	 */
	public int getNumberOfDue(Date changeTime, int time)
	{
		long num = getLongAfterCurrentDateWithMonth(changeTime, time) - System.currentTimeMillis();
		long num1 = 1000 * 60 * 60 * 24L;
		return (int) (num / num1);
	}

	/**
	 * 
	 * 方法说明：取得到期天数
	 *
	 * @param changeTime 长整形
	 * @param time 长整形
	 * @return 天数
	 */
	public int getNumberOfDue(String changeTime, int time)
	{
		long num = getLongAfterCurrentDateWithMonth(switchStringToDate(changeTime), time)
				- System.currentTimeMillis();
		long num1 = 1000 * 60 * 60 * 24L;
		return (int) (num / num1);
	}

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date parse(String source, ParsePosition pos) {
		// TODO Auto-generated method stub
		return null;
	}

}
