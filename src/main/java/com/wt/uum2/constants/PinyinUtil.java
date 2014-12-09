package com.wt.uum2.constants;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * <pre>
 * 业务名:拼音操作工具类
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
public class PinyinUtil
{

	/**
	 * 方法说明：将字符串转换成拼音数组
	 * 
	 * @param src
	 *            src
	 * @return String[]
	 */
	public String[] stringToPinyin(String src)
	{
		return stringToPinyin(src, false, null);
	}

	/**
	 * 方法说明：将字符串转换成拼音数组
	 * 
	 * @param src
	 *            src
	 * @param separator
	 *            separator
	 * @return String[]
	 */
	public String[] stringToPinyin(String src, String separator)
	{
		return stringToPinyin(src, true, separator);
	}

	/**
	 * 方法说明：将字符串转换成拼音数组
	 * 
	 * @param src
	 *            src
	 * @param isPolyphone
	 *            是否查出多音字的所有拼音
	 * @param separator
	 *            多音字拼音之间的分隔符
	 * @return String[]
	 */
	public String[] stringToPinyin(String src, boolean isPolyphone, String separator)
	{
		// 判断字符串是否为空
		if ("".equals(src) || null == src) {
			return null;
		}
		char[] srcChar = src.toCharArray();
		int srcCount = srcChar.length;
		String[] srcStr = new String[srcCount];

		for (int i = 0; i < srcCount; i++) {
			srcStr[i] = charToPinyin(srcChar[i], isPolyphone, separator);
		}
		return srcStr;
	}

	/**
	 * 方法说明：将单个字符转换成拼音
	 * 
	 * @param src
	 *            src
	 * @param isPolyphone
	 *            isPolyphone
	 * @param separator
	 *            separator
	 * @return String
	 */
	public String charToPinyin(char src, boolean isPolyphone, String separator)
	{
		// 创建汉语拼音处理类
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		StringBuffer tempPinying = new StringBuffer();

		// 如果是中文
		if (src > 128) {
			try {
				// 转换得出结果
				String[] strs = PinyinHelper.toHanyuPinyinStringArray(src, defaultFormat);

				// 是否查出多音字，默认是查出多音字的第一个字符
				if (isPolyphone && null != separator) {
					for (int i = 0; i < strs.length; i++) {
						tempPinying.append(strs[i]);
						if (strs.length != (i + 1)) {
							// 多音字之间用特殊符号间隔起来
							tempPinying.append(separator);
						}
					}
				} else {
					tempPinying.append(strs[0]);
				}

			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else {
			tempPinying.append(src);
		}

		return tempPinying.toString();

	}

	/**
	 * 方法说明：hanziToPinyin
	 * 
	 * @param hanzi
	 *            hanzi
	 * @return String
	 */
	public String hanziToPinyin(String hanzi)
	{
		return hanziToPinyin(hanzi, " ");
	}

	/**
	 * 方法说明：将汉字转换成拼音
	 * 
	 * @param hanzi
	 *            hanzi
	 * @param separator
	 *            separator
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public String hanziToPinyin(String hanzi, String separator)
	{
		// 创建汉语拼音处理类
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		String pinyingStr = "";
		try {
			pinyingStr = PinyinHelper.toHanyuPinyinString(hanzi, defaultFormat, separator);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pinyingStr;
	}

	/**
	 * 方法说明：将字符串数组转换成字符串
	 * 
	 * @param str
	 *            str
	 * @param separator
	 *            各个字符串之间的分隔符
	 * @return String
	 */
	public String stringArrayToString(String[] str, String separator)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append(str[i]);
			if (str.length != (i + 1)) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 方法说明：简单的将各个字符数组之间连接起来
	 * 
	 * @param str
	 *            str
	 * @return String
	 */
	public String stringArrayToString(String[] str)
	{
		return stringArrayToString(str, "");
	}

	/**
	 * 方法说明：将字符数组转换成字符串
	 * 
	 * @param ch
	 *            ch
	 * @param separator
	 *            各个字符串之间的分隔符
	 * @return String
	 */
	public String charArrayToString(char[] ch, String separator)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			sb.append(ch[i]);
			if (ch.length != (i + 1)) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * 方法说明：将字符数组转换成字符串
	 * 
	 * @param ch
	 *            ch
	 * @return String
	 */
	public String charArrayToString(char[] ch)
	{
		return charArrayToString(ch, " ");
	}

	/**
	 * 方法说明：取汉字的首字母
	 * 
	 * @param src
	 *            src
	 * @param isCapital
	 *            是否是大写
	 * @return char[]
	 */
	public char[] getHeadByChar(char src, boolean isCapital)
	{
		// 如果不是汉字直接返回
		if (src <= 128) {
			return new char[] { src };
		}
		// 获取所有的拼音
		String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);

		// 创建返回对象
		int polyphoneSize = pinyingStr.length;
		char[] headChars = new char[polyphoneSize];
		int i = 0;
		// 截取首字符
		for (String s : pinyingStr) {
			char headChar = s.charAt(0);
			// 首字母是否大写，默认是小写
			if (isCapital) {
				headChars[i] = Character.toUpperCase(headChar);
			} else {
				headChars[i] = headChar;
			}
			i++;
		}

		return headChars;
	}

	/**
	 * 方法说明：取汉字的首字母(默认是大写)
	 * 
	 * @param src
	 *            src
	 * @return char[]
	 */
	public char[] getHeadByChar(char src)
	{
		return getHeadByChar(src, true);
	}

	/**
	 * 方法说明：查找字符串首字母
	 * 
	 * @param src
	 *            src
	 * @return String[]
	 */
	public String[] getHeadByString(String src)
	{
		return getHeadByString(src, true);
	}

	/**
	 * 方法说明：查找字符串首字母
	 * 
	 * @param src
	 *            src
	 * @param isCapital
	 *            是否大写
	 * @return String[]
	 */
	public String[] getHeadByString(String src, boolean isCapital)
	{
		return getHeadByString(src, isCapital, null);
	}

	/**
	 * 方法说明：查找字符串首字母
	 * 
	 * @param src
	 *            src
	 * @param isCapital
	 *            是否大写
	 * @param separator
	 *            分隔符
	 * @return String[]
	 */
	public String[] getHeadByString(String src, boolean isCapital, String separator)
	{
		char[] chars = src.toCharArray();
		String[] headString = new String[chars.length];
		int i = 0;
		for (char ch : chars) {

			char[] chs = getHeadByChar(ch, isCapital);
			StringBuffer sb = new StringBuffer();
			if (null != separator) {
				int j = 1;

				for (char ch1 : chs) {
					sb.append(ch1);
					if (j != chs.length) {
						sb.append(separator);
					}
					j++;
				}
			} else {
				sb.append(chs[0]);
			}
			headString[i] = sb.toString();
			i++;
		}
		return headString;
	}

	/**
	 * 方法说明：字符串转拼音
	 * 
	 * @param string
	 *            string
	 * @return String
	 */
	public static String stringToPinYin(String string)
	{
		PinyinUtil pu = new PinyinUtil();
		return pu.stringArrayToString(pu.stringToPinyin(string));
	}

	/**
	 * 方法说明：字符串转拼音首字母
	 * 
	 * @param string
	 *            string
	 * @return String
	 */
	public static String stringToHeadPinYin(String string)
	{
		PinyinUtil pu = new PinyinUtil();
		return pu.stringArrayToString(pu.getHeadByString(string)).toLowerCase();
	}

	/**
	 * 方法说明：main
	 * 
	 * @param args
	 *            args
	 */
	public static void main(String[] args)
	{
		// System.out.println(PinyinUtil.stringToHeadPinYin("我靠").toString());
		// System.out.println(PinyinUtil.stringToPinYin("我的心肝爱上aaa"));
	}

}
