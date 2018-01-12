package cn.com.educate.app.entity.login;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.com.educate.core.orm.hibernate.AuditableEntity;

/**
 * Matedata entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "matedata")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Matedata extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -3276242824197821671L;
	
	private String matename;//名称
	/**名称*/
	@Column(columnDefinition=DEF_STR128)
	public String getMatename() {
		return this.matename;
	}
	/**名称*/
	public void setMatename(String matename) {
		this.matename = matename;
	}
	private String vtypes;//状态
	/**状态*/
	@Column(columnDefinition=DEF_STR4)
	public String getVtypes() {
		return this.vtypes;
	}
	/**状态*/
	public void setVtypes(String vtypes) {
		this.vtypes = vtypes;
	}
	private String vvalue;//值
	/**值*/
	@Column(columnDefinition=DEF_STR128)
	public String getVvalue() {
		return this.vvalue;
	}
	/**值*/
	public void setVvalue(String vvalue) {
		this.vvalue = vvalue;
	}
	/*
	private Long matetypeid;//编码类型
	*//**编码类型*//*
	@Column(columnDefinition=DEF_NUM10)
	public Long getMatetypeid() {
		return this.matetypeid;
	}
	*//**编码类型*//*
	public void setMatetypeid(Long matetypeid) {
		this.matetypeid = matetypeid;
	}
	*/
	//编码类型
	private Matedatatype code;
	//一对一定义
	@OneToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="matetypeid", unique= false, nullable=true, insertable=true, updatable=true)
	public Matedatatype getCode() {
		return code;
	}

	public void setCode(Matedatatype code) {
		this.code = code;
	}
	
	
	private String bankcode;//银行类补充一列银行代码
	/**银行类补充一列银行代码*/
	@Column(columnDefinition=DEF_STR16)
	public String getBankcode() {
		return this.bankcode;
	}
	/**银行类补充一列银行代码*/
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
}
