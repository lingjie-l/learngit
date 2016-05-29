package com.jeefw.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.base.Objects;
import com.jeefw.model.sys.param.TestParameter;

import core.support.DateSerializer;

/**
 * 测试的实体类
 * @
 */
@Entity
@Table(name = "test")
@Cache(region = "all", usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "maxResults", "firstResult", "topCount", "sortColumns", "cmd", "queryDynamicConditions", "sortedConditions", "dynamicProperties", "success", "message", "sortColumnsString", "flag" })
public class Test extends TestParameter{
	
	private static final long serialVersionUID = -7641965502056006035L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "xingming", length = 50, nullable = false)
	private String xingming;
	@Column(name = "shengri")
	@Temporal(TemporalType.DATE)
	private Date shengri;
	@Column(name = "gongzi")
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
	public void setXingming( String xingming) {
		this.xingming = xingming;
	}
	@JsonSerialize(using = DateSerializer.class)
	public Date getShengri() {
		return shengri;
	}
	public void setShengri( Date shengri) {
		this.shengri = shengri;
	}
	public float getGongzi() {
		return gongzi;
	}
	public void setGongzi( float gongzi) {
		this.gongzi = gongzi;
	}
	public Test() {
		
	}
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Test other = (Test) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.xingming, other.xingming) && Objects.equal(this.shengri, other.shengri) && Objects.equal(this.gongzi, other.gongzi);
	}

	public int hashCode() {
		return Objects.hashCode(this.id, this.xingming, this.shengri, this.gongzi);
	}
}
