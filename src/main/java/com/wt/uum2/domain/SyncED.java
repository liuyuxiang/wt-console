package com.wt.uum2.domain;

import java.io.Serializable;

/**
 * @author Alex
 * 
 *         同步目的源
 */
public class SyncED implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930475255743128792L;

	/**
	 * 关键字--我们UUM自己设定
	 */
	private String uuid;

	/**
	 * 
	 */
	private String ldapFactory;

	/**
	 * 
	 */
	private String ldapUserName;

	/**
	 * 
	 */
	private String ldapUserPassWord;

	/**
	 * 
	 */
	private String ldapUrl;

	/**
	 * 
	 */
	private String baseDN;

	/**
	 * 
	 */
	private String userDN;

	/**
	 * 
	 */
	private String groupDN;

	/**
	 * 
	 */
	private String deptDN;

	/**
	 * 
	 */
	private String userKey;

	/**
	 * 
	 */
	private String groupKey;

	/**
	 * 
	 */
	private String deptKey;

	/**
	 * 
	 */
	private String userObjectClass;

	/**
	 * 
	 */
	private String deptObjectClass;

	/**
	 * 
	 */
	private String groupObjectClass;

	/**
	 * 
	 */
	private String dbDeptRoot;

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getLdapFactory()
	{
		return ldapFactory;
	}

	public void setLdapFactory(String ldapFactory)
	{
		this.ldapFactory = ldapFactory;
	}

	public String getLdapUserName()
	{
		return ldapUserName;
	}

	public void setLdapUserName(String ldapUserName)
	{
		this.ldapUserName = ldapUserName;
	}

	public String getLdapUserPassWord()
	{
		return ldapUserPassWord;
	}

	public void setLdapUserPassWord(String ldapUserPassWord)
	{
		this.ldapUserPassWord = ldapUserPassWord;
	}

	public String getLdapUrl()
	{
		return ldapUrl;
	}

	public void setLdapUrl(String ldapUrl)
	{
		this.ldapUrl = ldapUrl;
	}

	public String getBaseDN()
	{
		return baseDN;
	}

	public void setBaseDN(String baseDN)
	{
		this.baseDN = baseDN;
	}

	public String getUserDN()
	{
		return userDN;
	}

	public void setUserDN(String userDN)
	{
		this.userDN = userDN;
	}

	public String getGroupDN()
	{
		return groupDN;
	}

	public void setGroupDN(String groupDN)
	{
		this.groupDN = groupDN;
	}

	public String getDeptDN()
	{
		return deptDN;
	}

	public void setDeptDN(String deptDN)
	{
		this.deptDN = deptDN;
	}

	public String getUserKey()
	{
		return userKey;
	}

	public void setUserKey(String userKey)
	{
		this.userKey = userKey;
	}

	public String getGroupKey()
	{
		return groupKey;
	}

	public void setGroupKey(String groupKey)
	{
		this.groupKey = groupKey;
	}

	public String getDeptKey()
	{
		return deptKey;
	}

	public void setDeptKey(String deptKey)
	{
		this.deptKey = deptKey;
	}

	public String getUserObjectClass()
	{
		return userObjectClass;
	}

	public void setUserObjectClass(String userObjectClass)
	{
		this.userObjectClass = userObjectClass;
	}

	public String getDeptObjectClass()
	{
		return deptObjectClass;
	}

	public void setDeptObjectClass(String deptObjectClass)
	{
		this.deptObjectClass = deptObjectClass;
	}

	public String getGroupObjectClass()
	{
		return groupObjectClass;
	}

	public void setGroupObjectClass(String groupObjectClass)
	{
		this.groupObjectClass = groupObjectClass;
	}

	public String getDbDeptRoot()
	{
		return dbDeptRoot;
	}

	public void setDbDeptRoot(String dbDeptRoot)
	{
		this.dbDeptRoot = dbDeptRoot;
	}

}
