package com.wt.uum2.domain;

import java.io.Serializable;

/**
 * @author noah
 * 
 */
public class GroupPath implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7904320125042505816L;

	private Group elder;

	private String elderUUID;

	private Group junior;

	private String juniorUUID;

	private String uuid;

	/**
	 * @return the elder
	 */
	public Group getElder() {
		return elder;
	}

	/**
	 * @return the elderUUID
	 */
	public String getElderUUID() {
		return elderUUID;
	}

	/**
	 * @return the junior
	 */
	public Group getJunior() {
		return junior;
	}

	/**
	 * @return the juniorUUID
	 */
	public String getJuniorUUID() {
		return juniorUUID;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param elder
	 *            the elder to set
	 */
	public void setElder(Group elder) {
		this.elder = elder;
	}

	/**
	 * @param elderUUID
	 *            the elderUUID to set
	 */
	public void setElderUUID(String elderUUID) {
		this.elderUUID = elderUUID;
	}

	/**
	 * @param junior
	 *            the junior to set
	 */
	public void setJunior(Group junior) {
		this.junior = junior;
	}

	/**
	 * @param juniorUUID
	 *            the juniorUUID to set
	 */
	public void setJuniorUUID(String juniorUUID) {
		this.juniorUUID = juniorUUID;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
