package cn.com.educate.app.entity.login;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.com.educate.core.orm.hibernate.AuditableEntity;

/**
 * Reatpackage entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "reatpackage")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reatpackage extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -2276242824197821671L;
	
	private String reatname;//套餐名称
	/**套餐名称*/
	@Column(columnDefinition=DEF_STR50)
	public String getReatname() {
		return this.reatname;
	}
	/**套餐名称*/
	public void setReatname(String reatname) {
		this.reatname = reatname;
	}
	private String cycke;//周期（月）
	/**周期（月）*/
	@Column(columnDefinition=DEF_STR3)
	public String getCycke() {
		return this.cycke;
	}
	/**周期（月）*/
	public void setCycke(String cycke) {
		this.cycke = cycke;
	}
	private String price;//套餐价格
	/**套餐价格*/
	@Column(columnDefinition=DEF_STR8)
	public String getPrice() {
		return this.price;
	}
	/**套餐价格*/
	public void setPrice(String price) {
		this.price = price;
	}
	private String vtypes;//属性
	/**属性*/
	@Column(columnDefinition=DEF_STR2)
	public String getVtypes() {
		return this.vtypes;
	}
	/**属性*/
	public void setVtypes(String vtypes) {
		this.vtypes = vtypes;
	}
	
	private String flag;
	@Column(columnDefinition=DEF_STR1)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
