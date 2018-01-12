package cn.com.educate.app.entity.login;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.com.educate.core.orm.hibernate.AuditableEntity;

/**
 * Cityinfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "cityinfo")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cityinfo extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -2276242824197821671L;
	private String cname;//别名
	/**别名*/
	@Column(columnDefinition=DEF_STR64)
	public String getCname() {
		return this.cname;
	}
	/**别名*/
	public void setCname(String cname) {
		this.cname = cname;
	}
	private String coding;//编码
	/**编码*/
	@Column(columnDefinition=DEF_STR16)
	public String getCoding() {
		return this.coding;
	}
	/**编码*/
	public void setCoding(String coding) {
		this.coding = coding;
	}
	private Long deptlevel;//等级
	/**等级*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getDeptlevel() {
		return this.deptlevel;
	}
	/**等级*/
	public void setDeptlevel(Long deptlevel) {
		this.deptlevel = deptlevel;
	}
	private String vname;//地区名称
	/**地区名称*/
	@Column(columnDefinition=DEF_STR128)
	public String getVname() {
		return this.vname;
	}
	/**地区名称*/
	public void setVname(String vname) {
		this.vname = vname;
	}
	private String pinyin;//拼音
	/**拼音*/
	@Column(columnDefinition=DEF_STR256)
	public String getPinyin() {
		return this.pinyin;
	}
	/**拼音*/
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	private Long sortno;//排序
	/**排序*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getSortno() {
		return this.sortno;
	}
	/**排序*/
	public void setSortno(Long sortno) {
		this.sortno = sortno;
	}
	private String vtypes;//状态
	/**状态*/
	@Column(columnDefinition=DEF_STR2)
	public String getVtypes() {
		return this.vtypes;
	}
	/**状态*/
	public void setVtypes(String vtypes) {
		this.vtypes = vtypes;
	}
	/*
	private Long parentid;//上级地区
	*//**上级地区*//*
	@Column(columnDefinition=DEF_NUM10)
	public Long getParentid() {
		return this.parentid;
	}
	*//**上级地区*//*
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	*/
	/**上级地区*/
	private Cityinfo parent;
	
	/**上级地区*/
	@ManyToOne(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public Cityinfo getParent() {
		return parent;
	}
	
	/**上级地区*/
	public void setParent(Cityinfo parent) {
		this.parent = parent;
	}
	
	
}
