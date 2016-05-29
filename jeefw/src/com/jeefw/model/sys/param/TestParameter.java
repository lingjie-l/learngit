package com.jeefw.model.sys.param;

import java.util.Date;

import core.support.ExtJSBaseParameter;

/**
 * 测试的参数类 @
 */
public class TestParameter extends ExtJSBaseParameter {
	private int id;
	private String xingming;
	private Date shengri;
	private float gongzi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getXingming() {
		return xingming;
	}

	public void setXingming(String xingming) {
		this.xingming = xingming;
	}

	public Date getShengri() {
		return shengri;
	}

	public void setShengri(Date shengri) {
		this.shengri = shengri;
	}

	public float getGongzi() {
		return gongzi;
	}

	public void setGongzi(float gongzi) {
		this.gongzi = gongzi;
	}

}
