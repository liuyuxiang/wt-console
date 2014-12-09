package nak.nsf.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-10-9
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Log4jSpringLevelConfigurer
{

	/**
	 * @param loggerName
	 *            loggerName
	 * @param level
	 *            level
	 */
	public Log4jSpringLevelConfigurer(String loggerName, Level level)
	{
		Logger.getLogger(loggerName).setLevel(level);
	}
}
