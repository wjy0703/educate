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
 * Buyreatinfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "buyreatinfo")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Buyreatinfo extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -2276242824197821671L;
	
	private Long busid;//所属企业
	/**所属企业*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getBusid() {
		return this.busid;
	}
	/**所属企业*/
	public void setBusid(Long busid) {
		this.busid = busid;
	}
	private Long reatid;//套餐id
	/**套餐id*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getReatid() {
		return this.reatid;
	}
	/**套餐id*/
	public void setReatid(Long reatid) {
		this.reatid = reatid;
	}
	private Timestamp buytime;//购买时间
	/**购买时间*/
	@Column(insertable = false)
	public Timestamp getBuytime() {
		return this.buytime;
	}
	/**购买时间*/
	public void setBuytime(Timestamp buytime) {
		this.buytime = buytime;
	}
	private String paymoney;//支付金额
	/**支付金额*/
	@Column(columnDefinition=DEF_STR8)
	public String getPaymoney() {
		return this.paymoney;
	}
	/**支付金额*/
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	private String payway;//支付方式
	/**支付方式*/
	@Column(columnDefinition=DEF_STR20)
	public String getPayway() {
		return this.payway;
	}
	/**支付方式*/
	public void setPayway(String payway) {
		this.payway = payway;
	}
	private String payaccount;//支付账户
	/**支付账户*/
	@Column(columnDefinition=DEF_STR20)
	public String getPayaccount() {
		return this.payaccount;
	}
	/**支付账户*/
	public void setPayaccount(String payaccount) {
		this.payaccount = payaccount;
	}
	private String paycard;//支付账号
	/**支付账号*/
	@Column(columnDefinition=DEF_STR30)
	public String getPaycard() {
		return this.paycard;
	}
	/**支付账号*/
	public void setPaycard(String paycard) {
		this.paycard = paycard;
	}
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
}
