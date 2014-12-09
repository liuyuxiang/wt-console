package com.wt.uum2.constants;

import org.apache.commons.lang.StringUtils;


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
public class Condition implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询中文名
	 */
	private String userName = "";

	/**
	 * 查询ID
	 */
	private String userId = "";

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public boolean isEmpty(){
		if(this==null){
			return true;
		}else if(StringUtils.isBlank(userId)&&StringUtils.isBlank(userName)){
			return true;
		}
		return false;
	}

	public boolean isNotEmpty(){
		return !isEmpty();
	}

	public static Condition newInstance(){
		return new Condition();
	}
	
	public Condition idLike(String idLike) {
		this.setUserId(idLike);
		return this;
	}

	public Condition nameLike(String nameLike) {
		this.setUserName(nameLike);
		return this;
	}
}
