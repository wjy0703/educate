package cn.com.educate.app.entity.security;

import java.sql.Timestamp;

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
 * Userinfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "userinfo")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Userinfo extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -1958107672378804945L;
	private String account;//账户
	/**账户*/
	@Column(columnDefinition=DEF_STR20, nullable = false, unique = true)
	public String getAccount() {
		return this.account;
	}
	/**账户*/
	public void setAccount(String account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return account;
	}
	
	private String password;//密码
	/**密码*/
	@Column(columnDefinition=DEF_STR50)
	public String getPassword() {
		return this.password;
	}
	/**密码*/
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	private String vname;//姓名
	/**姓名*/
	@Column(columnDefinition=DEF_STR20)
	public String getVname() {
		return this.vname;
	}
	/**姓名*/
	public void setVname(String vname) {
		this.vname = vname;
	}
	private String sex;//性别
	/**性别*/
	@Column(columnDefinition=DEF_STR2)
	public String getSex() {
		return this.sex;
	}
	/**性别*/
	public void setSex(String sex) {
		this.sex = sex;
	}
	private String card;//证件号码
	/**证件号码*/
	@Column(columnDefinition=DEF_STR30)
	public String getCard() {
		return this.card;
	}
	/**证件号码*/
	public void setCard(String card) {
		this.card = card;
	}
	private String phone;//联系方式
	/**联系方式*/
	@Column(columnDefinition=DEF_STR20)
	public String getPhone() {
		return this.phone;
	}
	/**联系方式*/
	public void setPhone(String phone) {
		this.phone = phone;
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
	
	private String post;//岗位
	/**岗位*/
	@Column(columnDefinition=DEF_STR4)
	public String getPost() {
		return this.post;
	}
	/**岗位*/
	public void setPost(String post) {
		this.post = post;
	}
	
	/**角色*/
	private Roleinfo roleinfo;
	//一对一定义
	@OneToOne(cascade=CascadeType.REFRESH,fetch = FetchType.LAZY)
	@JoinColumn(name="rolesid", unique= false, nullable=true, insertable=true, updatable=true)
	public Roleinfo getRoleinfo() {
		return roleinfo;
	}
	public void setRoleinfo(Roleinfo roleinfo) {
		this.roleinfo = roleinfo;
	}
	
	private String mail;
	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(columnDefinition=DEF_STR128)
	public String getMail() {
		return mail;
	}
	
	private Timestamp oldlogtime;//上次登陆时间
	/**上次登陆时间*/
	@Column(insertable = false)
	public Timestamp getOldlogtime() {
		return this.oldlogtime;
	}
	/**上次登陆时间*/
	public void setOldlogtime(Timestamp oldlogtime) {
		this.oldlogtime = oldlogtime;
	}
	private Timestamp nowlogtime;//上次登陆时间
	/**上次登陆时间*/
	@Column(insertable = false)
	public Timestamp getNowlogtime() {
		return this.nowlogtime;
	}
	/**上次登陆时间*/
	public void setNowlogtime(Timestamp nowlogtime) {
		this.nowlogtime = nowlogtime;
	}
	private String oldip;//上次登陆IP
	/**上次登陆IP*/
	@Column(columnDefinition=DEF_STR100)
	public String getOldip() {
		return this.oldip;
	}
	/**上次登陆IP*/
	public void setOldip(String oldip) {
		this.oldip = oldip;
	}
	private String nowip;//本次登陆IP
	/**本次登陆IP*/
	@Column(columnDefinition=DEF_STR100)
	public String getNowip() {
		return this.nowip;
	}
	/**本次登陆IP*/
	public void setNowip(String nowip) {
		this.nowip = nowip;
	}
}
