package com.wt.hea.webbuilder.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.FileUtil;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：yinhaoqi
 *    修改内容：废弃此工具类，统一使用com.wt.hea.common.util.FileUtils
 * </pre>
 */
public class FileIOUtil
{

	/**
	 * 获取日志实例
	 */
	private static Logger log = LoggerService.getInstance();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param path
	 *            path
	 * @return 文件内容
	 */
	public static String readFile(String path)
	{

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			File file = new File(path);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, FileUtil.getEncoding());
			br = new BufferedReader(isr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		char[] buf = new char[50];
		StringBuffer sb = new StringBuffer();
		try {
			if (br != null) {
				int i = br.read(buf);
				while (i != -1) {
					String content = new String(buf);
					try {
						content = content.substring(0, content.length());
					} catch (Exception e) {
						e.printStackTrace();
					}
					sb.append(content);
					if (i < 50) {
						buf = new char[i];
					} else {
						buf = new char[50];
					}
					i = br.read(buf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * 方法说明： 写文件操作
	 * 
	 * @param content
	 *            写入文件的内容
	 * @param file
	 *            目标文件
	 */
	public static void writeFile(StringBuffer content, File file)
	{
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, FileUtil.getEncoding());
			osw.write(new String(content));
			osw.flush();
		} catch (FileNotFoundException e) {
			log.info("文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * 方法说明： 删除一个文件
	 * 
	 * @param filePath
	 *            要删除的文件路径
	 * @return 是否删除成功boolean
	 */
	public static boolean deleteFile(String filePath)
	{
		File file = new File(filePath);
		boolean flag = false;
		if (file.isFile()) {
			flag = file.delete();
		}

		return flag;
	}

	/**
	 * 
	 * 方法说明： 文件读取操作
	 * 
	 * @param filePath
	 *            要操作的文件路径
	 * @return 文件的内容String
	 */
	public static String readFileByReadStream(String filePath)
	{
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			File file = new File(filePath);
			isr = new InputStreamReader(new FileInputStream(file), FileUtil.getEncoding());
			br = new BufferedReader(isr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		char[] buf = new char[1024];
		StringBuffer sb = new StringBuffer();
		try {
			if (br != null) {
				int i = br.read(buf);
				while (i != -1) {
					String content = new String(buf);
					try {
						content = content.substring(0, i);
					} catch (Exception e) {
						e.printStackTrace();
					}
					sb.append(content);
					if (i < 1024) {
						buf = new char[i];
					} else {
						buf = new char[1024];
					}
					i = br.read(buf);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

}
