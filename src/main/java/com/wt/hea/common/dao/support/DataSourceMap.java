package com.wt.hea.common.dao.support;


import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 数据源映射BEAN
 * <p>
 * web层切换数据源范例: 
 * Index index=new Index(); 														<br/>
 * index.setIndexname("test"); 														<br/>
 * index.setIndexlevel(1); index.setIndexurl("url");								<br/>
 * this.indexService.save(index); //保存到Oracle数据库								<br/>
 * ContextHolder.setType(DataSourceMap.getInstance().get("dataSource2"));			<br/>
 * 
 * 
 * index=new Index(); index.setIndexname("test2"); index.setIndexlevel(1);			<br/>
 * index.setIndexurl("url"); this.indexService.save(index); //保存到MySQL数据库		<br/>
 * 
 * ContextHolder.setType(DataSourceMap.getInstance().get("dataSource1"));			<br/>
 * //切换到默认的oracle数据源															<br/>
 * 
 * 注：业务层切换数据源，代码类似														<br/>
 * </p>
 * 
 * @author 袁明敏
 * 
 */
public class DataSourceMap {
	/**
	 * 私有构造函数
	 */
	private DataSourceMap() {
	}

	/**
	 * 数据源描述单例对象
	 */
	private static DataSourceMap DS_MAP = null;
	
	/**
	 * xml配置，用于获取多个数据源配置信息
	 */
	private static HashMap<String, String> MAP = null;
	
	/**
	 * 应用系统默认数据源
	 */
	public static String DEFAULT_TARGET_DATASOURCE=null;
	static {
		load();
		initDefaultDataSource();
	}

	/**
	 * 初使化默认的数据源
	 */
	private static void initDefaultDataSource(){
		try {
			Document doc = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(
							Thread
									.currentThread()
									.getContextClassLoader()
									.getResource(
											"com/hirisun/hea/common/config/applicationContext-dataSource.xml")
									.getPath());
			NodeList eles = doc.getElementsByTagName("property");
			
			boolean haveDefalutDataSource=false;
			for(int i=0;i<eles.getLength();i++){
				Element e = (Element) eles.item(i);
				if("defaultTargetDataSource".equalsIgnoreCase(e.getAttribute("name"))){
					DEFAULT_TARGET_DATASOURCE=e.getAttribute("ref");
					haveDefalutDataSource=true;
					break;
				}
			}
			if(haveDefalutDataSource==false){
				
				throw new DataSourceConfigException("have no default target datasource,please check classpath xxx-dataSoruce.xml !");
			}
			eles = null;
			doc = null;
		}catch(DataSourceConfigException esc){
			esc.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取数据源映射配置
	 */
	private static void load() {
		MAP = new HashMap<String, String>();
		try {
			Document doc = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(
							Thread
									.currentThread()
									.getContextClassLoader()
									.getResource(
											"com/hirisun/hea/common/config/applicationContext-dataSource.xml")
									.getPath());
			NodeList dataSources = doc.getElementsByTagName("entry");
			for (int i = 0; i < dataSources.getLength(); i++) {
				Element e = (Element) dataSources.item(i);
				String key = e.getAttribute("key");
				String value = e.getAttribute("value-ref");
				MAP.put(key, value);
			}

			dataSources = null;
			doc = null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * 方法说明：获取单例对象
	 *
	 * @return 返回数据源映射对象
	 */
	public static DataSourceMap getInstance() {
		if (DS_MAP != null) {
			return DS_MAP;
		} else {
			DS_MAP = new DataSourceMap();
			return DS_MAP;
		}
	}

	/**
	 * 	切换数据源映射,例如:ContextHolder.setType(DataSourceMap.getInstance().get("dataSource2"));
	 * @param key 用于切换数据源时的标识
	 * @return 返回数据源标识字符串
	 */
	public String get(String key) {
		return MAP.get(key);
	}
}
