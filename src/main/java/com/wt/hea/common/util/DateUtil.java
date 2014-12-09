package com.wt.hea.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <pre>
 * 业务名:通用日期操作类,提供获取当前时间，格式化时间简易操作
 * 功能说明: 获取日期或格式化时间简易操作
 * 编写日期:	2011-5-14
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-14
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class DateUtil
{

	/**
	 * 获取当前日期字符串
	 * 
	 * @param pattern
	 *            日期格式模式
	 * @return 返回格式化后的当前日期
	 */
	public static String getCurrDate(String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		if (StringUtils.isEmpty(pattern)) {
			simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		} else {
			simpleDateFormat.applyPattern(pattern);
		}
		Calendar currentDate = Calendar.getInstance(Locale.CHINA);
		return simpleDateFormat.format(currentDate.getTime());
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @param pattern
	 *            日期格式模式
	 * @return 返回格式化后的当前日期
	 */
	public static Date getYmdhmsDate(String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simpleDateFormat.parse(pattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            日期模式
	 * @return 返回格式化后的日期字符串表示
	 */
	public static String format(Date date, String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(pattern == null ? "yyyy-MM-dd HH:mm:ss" : pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 获取当前日期是星期几 ,added by:李丰川
	 * 
	 * @return 返回星期几表示
	 */
	public static String getWeekDay()
	{

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		}
		return null;
	}

	/**
	 * 得到当前日期的月首 格式为：2009-08-01
	 * 
	 * @return 返回日是期串
	 */
	public static String monthFist()
	{
		Calendar localTime = Calendar.getInstance();
		String strY = null;// 日期属性：日
		int x = localTime.get(Calendar.YEAR); // 日期属性：年
		int y = localTime.get(Calendar.MONTH) + 1; // 日期属性：月
		strY = y >= 10 ? String.valueOf(y) : ("0" + y); // 组合月份
		return x + "-" + strY + "-01"; // 最后组合成yyyy-mm-dd形式字符串
	}

	/**
	 * 得到上个月月首 格式为：2009-08-01
	 * 
	 * @return 返回日期格式
	 */
	public static String beforeMonth()
	{
		Calendar localTime = Calendar.getInstance();
		localTime.add(Calendar.MONTH, -1); // 通过提取这个月计算上个月号
		String strz = null;
		int x = localTime.get(Calendar.YEAR); // 得到年
		int y = localTime.get(Calendar.MONTH) + 1; // 得到月
		strz = y >= 10 ? String.valueOf(y) : ("0" + y);
		return x + "-" + strz + "-01";
	}

	/**
	 * 给定的日期加一个月 格式为：2009-08-01
	 * 
	 * @param strdate
	 *            　开始日期
	 * @return 返回日期格式
	 */
	public static String addMonth(String strdate)
	{

		Date date = new Date(); // 构造一个日期型中间变量

		String dateresult = null; // 返回的日期字符串
		// 创建格式化格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 加减日期所用
		GregorianCalendar gc = new GregorianCalendar();

		try {
			date = df.parse(strdate); // 将字符串格式化为日期型
		} catch (ParseException e) {
			e.printStackTrace();
		}

		gc.setTime(date); // 得到gc格式的时间

		gc.add(2, 1); // 2表示月的加减，年代表1依次类推(周,天。。)
		// 把运算完的时间从新赋进对象
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		// 在格式化回字符串时间
		dateresult = df.format(gc.getTime());

		return dateresult;
	}

	/**
	 * 判端date1是否在date2之前；当date1的时间早于date是返回true date1，date的格式为：2009-08-01
	 * 
	 * @param date1
	 *            日期
	 * @param date
	 *            日期
	 * @return 当date1的时间早于date2是返回true
	 */
	public static boolean isDate10Before(String date1, String date)
	{
		try {
			DateFormat df = DateFormat.getDateInstance();
			return df.parse(date1).before(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 方法说明:给定的日期减一个月 格式为：2009-08-01
	 * 
	 * @param strdate
	 *            　给定的日期串
	 * @return 返回减一个月的日期字符串
	 */
	public static String subMonth(String strdate)
	{

		Date date = new Date(); // 构造一个日期型中间变量

		String dateresult = null; // 返回的日期字符串
		// 创建格式化格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 加减日期所用
		GregorianCalendar gc = new GregorianCalendar();

		try {
			date = df.parse(strdate); // 将字符串格式化为日期型
		} catch (ParseException e) {
			e.printStackTrace();
		}

		gc.setTime(date); // 得到gc格式的时间

		gc.add(2, -1); // 2表示月的加减，年代表1依次类推(周,天。。)
		// 把运算完的时间从新赋进对象
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		// 在格式化回字符串时间
		dateresult = df.format(gc.getTime());

		return dateresult;
	}

	/**
	 * 给定的日期减一天 格式为：2009-08-01
	 * 
	 * @param strdate
	 *            给定的日期
	 * @return 返回日期串
	 */
	public static String subDay(String strdate)
	{

		Date date = new Date(); // 构造一个日期型中间变量

		String dateresult = null; // 返回的日期字符串
		// 创建格式化格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 加减日期所用
		GregorianCalendar gc = new GregorianCalendar();

		try {
			date = df.parse(strdate); // 将字符串格式化为日期型
		} catch (ParseException e) {
			e.printStackTrace();
		}

		gc.setTime(date); // 得到gc格式的时间

		gc.add(5, -1); // 2表示月的加减，年代表1依次类推(３周....5天。。)
		// 把运算完的时间从新赋进对象
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		// 在格式化回字符串时间
		dateresult = df.format(gc.getTime());

		return dateresult;
	}

	/**
	 * 拆分给定字符串构造本月月初 格式为：2009-08-01
	 * 
	 * @param strdate
	 *            　给定的日期串
	 * @return 返回日期串
	 */
	public static String giveMonthFist(String strdate)
	{

		// 以“－”为分隔符拆分字符串
		String strArray[] = strdate.split("-");

		String tempyear = strArray[0]; // 得到字符串中的年

		String tempmonth = strArray[1]; // 得到字符串中的月

		// 拼接成月首字符串
		return tempyear + "-" + tempmonth + "-01";
	}

	/**
	 * 拆分给定字符串构造本月月末 格式为：2009-08-01
	 * 
	 * @param strdate
	 *            　给定的日期串
	 * @return 返回日期串
	 */
	public static String giveMonthLast(String strdate)
	{
		// 先得到下个月的同一天
		String addmonth = DateUtil.addMonth(strdate);

		// 得到下个月的月初
		String monthfirst = DateUtil.giveMonthFist(addmonth);

		// 下个月月初减一天为本月月末
		String subday = DateUtil.subDay(monthfirst);
		return subday;
	}

	/**
	 * 拆分给定字符串构造上个月月初 格式为：2009-08-01
	 * 
	 * @param strdate
	 *            　给定的日期串
	 * @return 返回日期串
	 */
	public static String giveBeforeMonthFirst(String strdate)
	{
		// 调用得到上个月的函数
		String beforemonth = DateUtil.subMonth(strdate);

		// 调用构造月初的函数
		return DateUtil.giveMonthFist(beforemonth);
	}

	/**
	 * 拆分给定字符串构造上个月月末 格式为：2009-08-01 *
	 * 
	 * @param strdate
	 *            　给定的日期串
	 * @return 返回日期串
	 */
	public static String giveBeforeMonthLast(String strdate)
	{
		// 先调用函数得到本月月初
		String monthfirst = DateUtil.giveMonthFist(strdate);

		// 调用当前日期减一天方法得到上个月月末
		return DateUtil.subDay(monthfirst);
	}

	/**
	 * 给定的日期得到年月 格式为：2009-08-01 *
	 * 
	 * @param yrmoday
	 *            　给定的日期串
	 * @return 返回日期串
	 */
	public static String giveyrmo(String yrmoday)
	{
		// 以“－”为分隔符拆分字符串
		String strArray[] = yrmoday.split("-");

		String tempyear = strArray[0]; // 得到字符串中的年

		String tempmonth = strArray[1]; // 得到字符串中的月

		// 拼接成月首字符串
		return tempyear + "-" + tempmonth; // 最后组合成yyyy-mm形式字符串

	}

	/**
	 * 两个日期做减法，返回相差天数
	 * 
	 * @throws ParseException
	 * @throws ParseException
	 *             *
	 * @param date1
	 *            　给定的日期串1
	 * @param date2
	 *            　给定的日期串2
	 * @return 返回日期串相差天数
	 */
	public static long datesub(Date date1, Date date2) throws ParseException
	{

		long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime() - date2.getTime() : date2
				.getTime() - date1.getTime();

		// 日期相减得到相差的日期
		long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1
				.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1
				.getTime()) / (24 * 60 * 60 * 1000);

		return day + l;
	}

	/**
	 * 根据给定的年月构造日期月首字符串
	 * 
	 * @param yr
	 *            年
	 * @param mo
	 *            月
	 * @return 根据给定的年月构造日期月首字符串
	 */
	public static String giveMonthFist(Integer yr, Integer mo)
	{

		// 拼接成月首字符串
		if (mo >= 10) {
			return yr + "-" + mo + "-01";
		} else {
			return yr + "-" + "0" + mo + "-01";
		}

	}

	/**
	 * 根据给定的年月构造年月字符串
	 * 
	 * @param yr
	 *            年
	 * @param mo
	 *            月
	 * @return 构造年月字符串
	 */
	public static String giveYrMo(Integer yr, Integer mo)
	{

		// 拼接成月首字符串
		if (mo >= 10) {
			return yr + "-" + mo;
		} else {
			return yr + "-" + "0" + mo;
		}

	}

	/**
	 * 给定年月字串返回一个整型月份 格式为：2009-08-01
	 * 
	 * @param yrmoday
	 *            年
	 * @return 给定年月字串返回一个整型月份 格式为：2009-08-01
	 */
	public static Integer returnmo(String yrmoday)
	{
		// 以“－”为分隔符拆分字符串
		String strArray[] = yrmoday.split("-");

		String tempmonth = strArray[1]; // 得到字符串中的月

		return new Integer(tempmonth);
	}

	/**
	 * 给定年月字串返回一个整型年份 格式为：2009-08-01
	 * 
	 * @param yrmoday
	 *            年月字串份
	 * @return 给定年月字串返回一个整型年份 格式为：2009-08-01
	 */
	public static Integer returnyr(String yrmoday)
	{
		// 以“－”为分隔符拆分字符串
		String strArray[] = yrmoday.split("-");

		String tempmonth = strArray[0]; // 得到字符串中的月

		return new Integer(tempmonth);
	}

	/**
	 * 给定的两个日期作比较，返回bool的类型 格式为：2009-08-01
	 * 
	 * @param startdate
	 *            给定的日期１
	 * @param enddate
	 *            给定的日期２
	 * @throws ParseException
	 *             解析异常
	 * @return 给定的两个日期作比较，返回bool的类型
	 * 
	 */
	public static boolean boolcompara(String startdate, String enddate) throws ParseException
	{

		if (DateFormat.getDateInstance().parse(startdate)
				.compareTo(DateFormat.getDateInstance().parse(startdate)) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @todo:判断时间date1是否在时间date2之前 时间格式 2008-08-08 16:16:34
	 * @param date1
	 *            给定的日期
	 * @param date2
	 *            给定的日期
	 * @return 判断时间date1是否在时间date2之前
	 */
	public static boolean isDateBefore(String date1, String date2)
	{
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	//
	//
	/**
	 * 判断当前时间是否在时间date2之前, 时间格式 2005-4-21 16:16:34
	 * 
	 * @param date2
	 *            给定的日期
	 * @return 判断当前时间是否在时间date2之前,如是,返回真
	 */
	public static boolean isDateBefore(String date2)
	{
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

}
