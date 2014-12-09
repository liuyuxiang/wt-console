package com.wt.sso.web;

import java.io.Serializable;

public class SSOUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -733136077018403346L;

	private String password;

	private String username;

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
