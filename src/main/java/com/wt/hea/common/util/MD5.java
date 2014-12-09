package com.wt.hea.common.util;
import java.security.MessageDigest;

/**
 * 
 * <pre>
 * 业务名: MD5加密类
 * 功能说明: MD5加密类,实现MD5加密算法
 * 编写日期:	2011-5-14
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-14
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class MD5{ 
	
	/**
	 * 
	 * 方法说明：MD5加密
	 *
	 * @param s 需要加密的字符串
	 * @return 返回加密后的字符
	 */
    public static String md5(String s){ 
        char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; 
        try { 
            byte[] bytes = s.getBytes(); 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            md.update(bytes); 
            bytes = md.digest(); 
            int j = bytes.length; 
            char[] chars = new char[j * 2]; 
            int k = 0; 
            for (int i = 0; i < bytes.length; i++) { 
                byte b = bytes[i]; 
                chars[k++] = hexChars[b >>> 4 & 0xf]; 
               chars[k++] = hexChars[b & 0xf]; 
            } 
            return new String(chars); 
        } 
        catch (Exception e){ 
            return null; 
        } 
    }
}