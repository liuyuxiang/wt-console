package com.wt.hea.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 扩展第三方字符串处理工具类
 * 编写日期:	2011-11-9
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class StringUtil {

	/**
	 * 方法说明： 缩略字符串
	 * 
	 * @param str
	 *            要压缩的字符串
	 * @param maxWidth
	 *            压缩后的长度
	 * @return 压缩后的字符串
	 */
	public static String abbreviate(String str, int maxWidth) {
		return StringUtils.abbreviate(str, maxWidth);
	}

	/**
	 * 方法说明： 压缩字符串
	 * 
	 * @param str
	 *            要压缩的字符串
	 * @param offset
	 *            偏移量
	 * @param maxWidth
	 *            压缩后显示的长度
	 * @return 压缩后的字符串
	 */
	public static String abbreviate(String str, int offset, int maxWidth) {
		return StringUtils.abbreviate(str, offset, maxWidth);
	}

	/**
	 * 方法说明： 是否包含某个字符
	 * 
	 * @param str
	 *            要检测的字符串
	 * @param searchChar
	 *            searchChar
	 * @return 是否包含某个字符
	 */
	public static boolean contains(String str, char searchChar) {
		return StringUtils.contains(str, searchChar);
	}

	/**
	 * 方法说明： 是否包含某个字符
	 * 
	 * @param str
	 *            要检测的字符串
	 * @param searchStr
	 *            str
	 * @return 是否包含
	 */
	public static boolean contains(String str, String searchStr) {
		return StringUtils.contains(str, searchStr);
	}

	/**
	 * 方法说明： 统计str中包含sub的次数
	 * 
	 * @param str
	 *            str
	 * @param sub
	 *            sub
	 * @return 被包含的次数
	 */
	public static int countMatches(String str, String sub) {
		return StringUtils.countMatches(str, sub);
	}

	/**
	 * 方法说明： 如果为空白时返回默认字符串， 空白指：null，""，tab，回车，空格，和其它非显示字符
	 * 
	 * @param str
	 *            str
	 * @param defaultStr
	 *            给定的默认字符
	 * @return 返回默认字符
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return StringUtils.defaultString(str, defaultStr);
	}

	/**
	 * 方法说明： 如果为空时返回默认字符串，空指：null,""
	 * 
	 * @param str
	 *            源字符串
	 * @param defaultStr
	 *            默认字符串
	 * @return 字符串
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return StringUtils.defaultIfEmpty(str, defaultStr);
	}

	/**
	 * 方法说明： 反转字符串
	 * 
	 * @param str
	 *            要反转字符串
	 * @return 反转后的字符串
	 */
	public static String reverse(String str) {
		return StringUtils.reverse(str);
	}

	/**
	 * 方法说明： 截取tag标记之间的字符串 如果没有返回null
	 * 
	 * @param str
	 *            源字符串
	 * @param tag
	 *            标记字符串
	 * @return 标记字符串之间的内容
	 */
	public static String substringBetween(String str, String tag) {
		return StringUtils.substringBetween(str, tag);
	}

	/**
	 * 方法说明： 截取从open开始到close结束的字符串
	 * 
	 * @param str
	 *            源字符串
	 * @param open
	 *            开始字符
	 * @param close
	 *            结束字符
	 * @return 截取后的字符串
	 */
	public static String substringBetween(String str, String open, String close) {
		return StringUtils.substringBetween(str, open, close);
	}
}
