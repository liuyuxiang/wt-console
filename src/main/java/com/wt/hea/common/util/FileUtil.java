package com.wt.hea.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.FileCopyUtils;

import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;

/**
 * 文件操作工具类
 * 
 * @author 袁明敏
 * 
 */
public class FileUtil
{
	/**
	 * 
	 */
	private static Logger logger = LoggerService.getInstance();
	/**
	 * 
	 */
	private static String OSEncoding = "UTF-8";

	/**
	 * 
	 */
	private FileUtil()
	{
	}

	static {
		Properties props = PropertiesUtil
				.getProp("/com/hirisun/hea/webbuilder/config/webbuilder.properties");
		OSEncoding = props.getProperty("encode");
	}

	/**
	 * 文本文件写入
	 * 
	 * @param filePath
	 *            文件名
	 * @param content
	 *            文件字符串内容
	 * @return 写入成功返回真
	 */
	public static boolean fileWrite(String filePath, String content)
	{
		FileOutputStream fileOutputStream = null;
		FileChannel fileChannel = null;
		FileLock lock = null;
		try {
			File file = new File(filePath);
			fileOutputStream = new FileOutputStream(file);
			fileChannel = fileOutputStream.getChannel();
			lock = fileChannel.tryLock();
			fileChannel.write(ByteBuffer.wrap(content.getBytes()));

		} catch (IOException e) {
			logger.error(e);
			return false;
		} finally {
			try {
				if (lock != null) {
					lock.release();
				}
			} catch (Exception lockex) {
				throw new RuntimeException(lockex.getMessage());
			} finally {

				try {
					if (fileChannel != null) {
						fileChannel.close();
					}
				} catch (Exception fileChannelEx) {
					throw new RuntimeException(fileChannelEx.getMessage());
				} finally {

					try {
						if (fileOutputStream != null) {
							fileOutputStream.close();
						}
					} catch (Exception ext) {
						throw new RuntimeException(ext.getMessage());
					}
				}
			}
		}
		return true;
	}

	/**
	 * 文本文件读取 修改读取utf-8文件的标识，该标识影响使用已修正。 modifyby:yinhaoqi
	 * 
	 * @param filePath
	 *            文件名
	 * @return 返回文件字符串内容
	 */
	public static String fileRead(String filePath)
	{
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);
			byte content[] = FileCopyUtils.copyToString(bufferedReader).getBytes(
					FileUtil.getEncoding());
			if (Integer.toHexString(content[0]).toUpperCase().equals("FFFFFFEF")
					&& Integer.toHexString(content[1]).toUpperCase().equals("FFFFFFBB")
					&& Integer.toHexString(content[2]).toUpperCase().equals("FFFFFFBF")) {
				byte[] t = new byte[content.length - 3];
				for (int i = 3; i < content.length; i++) {
					t[i - 3] = content[i];
				}
				return new String(t);
			}
			return new String(content, FileUtil.getEncoding());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} finally {

				try {
					if (fileReader != null) {
						fileReader.close();
					}
				} catch (Exception ex) {
					throw new RuntimeException(ex.getMessage());
				}
			}
		}
		return null;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 删除成功返回真
	 */
	public static boolean removeFile(String filePath)
	{
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isFile())
				return file.delete();
		}
		return false;
	}

	/**
	 * 建立文件夹
	 * 
	 * @param folderPath
	 *            文件夹路径
	 */
	public static void createFolder(String folderPath)
	{
		File file = new File(folderPath + File.separator);
		if (!file.exists()) {
			boolean flag = file.mkdir();
			if (flag) {

			}
		}
	}

	/***
	 * 新建单个文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 创建成功返回真
	 */
	public static File createFile(String filePath)
	{
		File file = new File(filePath);
		if (file.exists()) {
			boolean flag = file.delete();
			if (flag) {

			}
		}
		file = new File(filePath);
		return file;
	}

	/**
	 * 记录要删除的路径
	 */
	private static File m_root;
	/**
	 * 文件目录
	 */
	@SuppressWarnings({ "rawtypes" })
	private static List m_dirs;

	/**
	 * 功能：获取所有文件和文件夹，存储在m_dirs中 注意：递归调用
	 * 
	 * @param tempRoot
	 *            文件路径
	 */
	@SuppressWarnings("unchecked")
	private static void visitAll(File tempRoot)
	{
		try {
			// 获取指定路径下的所有文件
			File[] dirs = tempRoot.listFiles();
			if (dirs != null) {
				// 将文件数组转换成List对象
				@SuppressWarnings("rawtypes")
				List dirslist = Arrays.asList(dirs);
				// 将dirslist完全添加到m_dirs中
				m_dirs.addAll(dirslist);
				// 递归调用
				for (int i = 0; i < dirslist.size(); i++) {
					visitAll((File) dirslist.get(i));
				}
			}
		} catch (Exception ex) {
			logger.error("error in visitAll : " + ex.getMessage());
		}

	}

	/**
	 * 功能：删除文件或文件夹 注意：使用倒叙删除，先删除文件，然后删除空文件夹
	 */
	private static void rootDelete()
	{
		try {
			if (m_dirs != null) {
				// 使用倒叙循环删除（先删除文件，再删除文件夹）
				for (int i = m_dirs.size() - 1; i >= 0; i--) {
					File f = (File) m_dirs.remove(i);// 获取之后删除list中的数据
					// 删除数据
					if (!f.delete()) {
						logger.info("文件路径:" + f.toString() + " 不存在");
					}
				}
			} else {
				logger.info("获取文件list列表（m_dirs）为空");
			}
		} catch (Exception ex) {
			logger.error("error in rootDelete : " + ex.getMessage());
		}
	}

	/**
	 * 功能：删除文件夹方法
	 * 
	 * @param path
	 *            要删除的文件夹路径(java.io.File类型)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void removeFolder(String path)
	{
		try {
			File dir = new File(path);
			m_root = dir;
			m_dirs = new ArrayList();

			if (!m_root.isDirectory()) { // 判断输入的是否为路径
				logger.info(m_root.toString() + " 不是路径");
			} else {
				// 输出m_dirs中记录的值
				// for(int i=0 ; i<m_dirs.size() ; i++){
				// }
				m_dirs.add(m_root);

				visitAll(m_root); // 获取指定路径下的所有文件已经文件夹（递归调用）
				rootDelete(); // 删除list中的所有文件（倒叙循环删除）
			}
		} catch (Exception ex) {
			logger.error("error in deleteDirs : " + ex.getMessage());
		}
	}

	/**
	 * 
	 * 
	 * 方法说明：获取操作系统编码
	 * 
	 * @return 返回编码表示
	 */
	public static String getEncoding()
	{
		return OSEncoding;
	}

	/**
	 * 方法说明： 获取主文件名
	 * 
	 * <pre>
	 * a/b/c.txt --> c
	 * a.txt     --> a
	 * a/b/c     --> c
	 * a/b/c/    --> ""
	 * </pre>
	 * 
	 * @param fileName
	 *            文件名
	 * @return 主文件名
	 */
	public static String getBaseName(String fileName)
	{
		return FilenameUtils.getBaseName(fileName);
	}

	/**
	 * 方法说明： 获取文件扩展名
	 * 
	 * <pre>
	 * foo.txt --> "txt" a/b/c.jpg --> "jpg" a/b.txt/c --> "" a/b/c --> ""
	 * </pre>
	 * 
	 * @param fileName
	 *            文件名
	 * @return 扩展名
	 */
	public static String getExtension(String fileName)
	{
		return FilenameUtils.getExtension(fileName);
	}

	/**
	 * 方法说明： 移除文件扩展名
	 * 
	 * @param fileName
	 *            文件名
	 * @return 去掉扩展名得文件名
	 */
	public static String removeExtension(String fileName)
	{
		return FilenameUtils.removeExtension(fileName);
	}

	/**
	 * 方法说明： 根据当前操作系统转路径中的路径分隔符
	 * 
	 * @param path
	 *            文件路径
	 * @return 转换后的文件路径
	 */
	public static String separatorsToSystem(String path)
	{
		return FilenameUtils.separatorsToSystem(path);
	}

	/**
	 * 方法说明： 根据设定的编码从输入流返回一个字符串
	 * 
	 * @param input
	 *            输入流
	 * @param encoding
	 *            编码
	 * @return 输入流内字符串
	 * @throws IOException
	 */
	public static String toString(InputStream input, String encoding) throws IOException
	{
		return IOUtils.toString(input, encoding);
	}

	/**
	 * 方法说明： 根据url获取url内容
	 * 
	 * @param url
	 *            url
	 * @param encoding
	 *            编码
	 * @return url内容
	 * @throws IOException
	 */
	public static String toString(URL url, String encoding) throws IOException
	{
		InputStream inputStream = url.openStream();
		try {
			return toString(inputStream, encoding);
		} finally {
			inputStream.close();
		}
	}

}
