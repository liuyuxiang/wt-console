package com.wt.hea.common.util;

import java.io.FileOutputStream;
import java.io.IOException;


/***
 * 通用的文件上传工具类
 * @author 袁明敏
 *
 */
public class UploadUtil {
	
	/***
	 * 获取文件byte, 保存到HTTP服务器磁盘
	 * @param fileData 文件的二进制数组
	 * @param path 服务器路径
	 * @return 上传成功返回真
	 */
	public static boolean upload(byte[] fileData,String path){
		if(fileData==null)return false;
		
		if(fileData.length==0)return false;
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(fileData);
			fos.flush();
			fos.close();
			return true;
		} catch (IOException ioException) {
			ioException.printStackTrace();
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
