package com.wt.hea.webbuilder.model;

import java.io.Serializable;
import java.util.List;

/**
 * 用户展现定制系统全局参数
 * @author xiaoqi
 *
 */
@SuppressWarnings("serial")
public class PersonalSystemParameter implements Serializable {

	/**
	 * 代码标识
	 */
	private String codeId ;

	/**
	 * 代码分类<br>
	 * 全局标识：overall
	 * 用户个性标识：personal
	 */
	private String codeType ;
	
	/**
	 * 代码值
	 */
	private String codeValue ;
	
	/**
	 * 代码名称
	 * codeName：
	 * isDrag isRefresh isClose isEdit
	 */
	private String codeName ;
	
	/**
	 * 上级代码
	 */
	private String parentCode ;
	
	/**
	 * 显示序号
	 */
	private Integer dispSn ;
	
	/**
	 * 代码内容1
	 */
	private String content1;
	
	/**
	 * 代码内容2
	 */
	private String content2;
	
	/**
	 * 代码内容3
	 */
	private String content3;
	
	/**
	 * 代码内容4
	 */
	private String content4;
	
	/**
	 * 代码内容5
	 */
	private String content5;

	/**
	 * 系统参数列表
	 */
	private List<PersonalSystemParameter> subpspList ;
	/**
	 * 系统参数列表
	 */
	private PersonalSystemParameter parentPersonalSystemParameter ;
	
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getDispSn() {
		return dispSn;
	}

	public void setDispSn(Integer dispSn) {
		this.dispSn = dispSn;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public String getContent3() {
		return content3;
	}

	public void setContent3(String content3) {
		this.content3 = content3;
	}

	public String getContent4() {
		return content4;
	}

	public void setContent4(String content4) {
		this.content4 = content4;
	}

	public String getContent5() {
		return content5;
	}

	public void setContent5(String content5) {
		this.content5 = content5;
	}

	public List<PersonalSystemParameter> getSubpspList() {
		return subpspList;
	}

	public void setSubpspList(List<PersonalSystemParameter> subpspList) {
		this.subpspList = subpspList;
	}

	public PersonalSystemParameter getParentPersonalSystemParameter() {
		return parentPersonalSystemParameter;
	}

	public void setParentPersonalSystemParameter(
			PersonalSystemParameter parentPersonalSystemParameter) {
		this.parentPersonalSystemParameter = parentPersonalSystemParameter;
	}

}
