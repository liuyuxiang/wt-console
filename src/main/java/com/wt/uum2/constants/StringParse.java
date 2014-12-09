package com.wt.uum2.constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import nak.xsecret.xaes.XAes;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class StringParse
{

	/**
	 * 密钥
	 */
	private static String key = "hello the world!";// 密钥

	/**
	 * xsso解密
	 * 
	 * @param inputString
	 *            inputString
	 * @return String
	 */
	public static String decryptData(String inputString)
	{
		XAes xAes = new XAes();
		return xAes.decryptData(inputString, key);
	}

	/**
	 * xsso加密
	 * 
	 * @param inputString
	 *            inputString
	 * @return String
	 */
	public static String encryptData(String inputString)
	{
		XAes xAes = new XAes();
		return xAes.encryptData(inputString, key);
	}

	/**
	 * MD5散列方式
	 * 
	 * @param s
	 *            s
	 * @return String
	 */
	public static String md5(String s)
	{
		return DigestUtils.md5Hex(s);
	}

	public static String digest(String s, String key)
	{
		byte[] bytes = s.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(key);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		md.update(bytes);
		bytes = md.digest();
		return bytesToHex(bytes);
	}

	private static String bytesToHex(byte[] b)
	{
		char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < b.length; j++) {
			buf.append(hexChars[(b[j] >> 4) & 0x0f]);
			buf.append(hexChars[b[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * SHA1散列
	 * 
	 * @param s
	 *            s
	 * @return String
	 */
	public static String sha1(String s)
	{
		return DigestUtils.shaHex(s);
	}

	/**
	 * SHA1散列并且base64加密
	 * 
	 * @param s
	 *            s
	 * @return String
	 */
	public static String sha1ToBase64String(String s)
	{
		return encodeBase64String(DigestUtils.sha(s));
	}

	/**
	 * 方法说明：
	 *
	 * @param b byte
	 * @return
	 */
	private static String encodeBase64String(byte[] b)
	{
		return Base64.encodeBase64String(b);
	}

	/**
	 * 方法说明：加密
	 *
	 * @param str String
	 * @return
	 */
	public static String encodeBase64String(String str)
	{
		return encodeBase64String(str.getBytes());
	}

	/**
	 * 方法说明：解密
	 *
	 * @param str String
	 * @return
	 */
	public static String decodeBase64String(String str)
	{
		return new String(Base64.decodeBase64(str));
	}

}
