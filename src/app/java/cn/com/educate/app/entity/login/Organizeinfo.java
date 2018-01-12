package cn.com.educate.app.entity.login;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

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
 * Organizeinfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "organizeinfo")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organizeinfo extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -4276242824197821671L;
	private String orgname;//名称
	/**名称*/
	@Column(columnDefinition=DEF_STR50)
	public String getOrgname() {
		return this.orgname;
	}
	/**名称*/
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	private String address;//地址
	/**地址*/
	@Column(columnDefinition=DEF_STR512)
	public String getAddress() {
		return this.address;
	}
	/**地址*/
	public void setAddress(String address) {
		this.address = address;
	}
	private String orgflag;//类别（行业、区域、门店）
	/**类别（行业、区域、门店）*/
	@Column(columnDefinition=DEF_STR2)
	public String getOrgflag() {
		return this.orgflag;
	}
	/**类别（行业、区域、门店）*/
	public void setOrgflag(String orgflag) {
		this.orgflag = orgflag;
	}
	private String vtypes;//属性（在用、停用）
	/**属性（在用、停用）*/
	@Column(columnDefinition=DEF_STR2)
	public String getVtypes() {
		return this.vtypes;
	}
	/**属性（在用、停用）*/
	public void setVtypes(String vtypes) {
		this.vtypes = vtypes;
	}
	/*
	private Long busid;//所属企业
	*//**所属企业*//*
	@Column(columnDefinition=DEF_NUM10)
	public Long getBusid() {
		return this.busid;
	}
	*//**所属企业*//*
	public void setBusid(Long busid) {
		this.busid = busid;
	}
	*/
	/**所属企业*/
	private Businessinfo businessinfo;
	//一对一定义
	@OneToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY)
	@JoinColumn(name="busid", unique= false, nullable=true, insertable=true, updatable=true)
	public Businessinfo getBusinessinfo() {
		return businessinfo;
	}
	public void setBusinessinfo(Businessinfo businessinfo) {
		this.businessinfo = businessinfo;
	}
	
	private Long parentid;//上级ID
	/**上级ID*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getParentid() {
		return this.parentid;
	}
	/**上级ID*/
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	private String parentids;//所有上级
	/**所有上级*/
	@Column(columnDefinition=DEF_STR512)
	public String getParentids() {
		return this.parentids;
	}
	/**所有上级*/
	public void setParentids(String parentids) {
		this.parentids = parentids;
	}
}
