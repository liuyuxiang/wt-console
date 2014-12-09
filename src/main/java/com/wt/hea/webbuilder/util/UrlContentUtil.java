package com.wt.hea.webbuilder.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import com.wt.hea.common.util.FileUtil;

/***
 * 跟据url获取html内容常用工具类
 * 
 * @author 袁明敏
 * 
 */
public class UrlContentUtil
{

	/**
	 * 
	 * @param urlStr
	 *            url地址
	 * @param path
	 *            生成的html内容保存到的地址
	 * @return html文件
	 */
	public static File getUrlContent(String urlStr, String path)
	{
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		InputStream in = null;
		InputStreamReader isr = null;
		try {
			URL url = new URL(urlStr);
			in = url.openStream();
			isr = new InputStreamReader(in, FileUtil.getEncoding());
			fos = new FileOutputStream(path);
			osw = new OutputStreamWriter(fos, FileUtil.getEncoding());
			// IOUtils.copy(in, fos);
			char[] b = new char[in.available()];
			int r;
			while ((r = isr.read(b)) != -1) {
				osw.write(b, 0, r);
			}
			osw.flush();
			return new File(path);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
				isr.close();
				in.close();
				fos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

}
