package cn.com.educate.app.entity.security;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import cn.com.educate.app.entity.login.Menutable;
import cn.com.educate.core.orm.hibernate.AuditableEntity;
import cn.com.educate.core.utils.ReflectionUtils;

/**
 * Roleinfo entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "roleinfo")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Roleinfo extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -1490869389529844637L;
	
	public Roleinfo() {
		super();
	}
	public Roleinfo(Long id, String rolename) {
		this.id = id;
		this.rolename = rolename;
	}
	private String rolename;//名称
	/**名称*/
	@Column(columnDefinition=DEF_STR20, nullable = false, unique = true)
	public String getRolename() {
		return this.rolename;
	}
	/**名称*/
	public void setRolename(String rolename) {
		this.rolename = rolename;
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
	
	private List<Authority> authorityList = new LinkedList<Authority>();
	
	
	private List<Menutable> menuList = new LinkedList<Menutable>();
	//多对多定义
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH },targetEntity=Authority.class,fetch = FetchType.LAZY)
	//中间表定义,表名采用默认命名规则
	@JoinTable(name = "roleauthority", joinColumns = { @JoinColumn(name = "roleid") }, inverseJoinColumns = { @JoinColumn(name = "authorityid") })
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序.
	@OrderBy("id")
	//集合中对象id的缓存.
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Authority> getAuthorityList() {
		return authorityList;
	}
	
	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH },targetEntity=Menutable.class,fetch = FetchType.LAZY)
	@JoinTable(name = "rolemenu", joinColumns = { @JoinColumn(name = "roleid") }, inverseJoinColumns = { @JoinColumn(name = "menuid") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Menutable> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menutable> menuList) {
		this.menuList = menuList;
	}
//	private List<Userinfo> userList = new LinkedList<Userinfo>();
//
//	@ManyToMany(targetEntity=Userinfo.class, mappedBy="roleinfo",fetch = FetchType.LAZY)
//	public List<Userinfo> getUserList() {
//		return userList;
//	}
//
//	public void setUserList(List<Userinfo> userList) {
//		this.userList = userList;
//	}
	private String authNames;
	
	@Transient
	public String getAuthNames() {
		authNames =  ReflectionUtils.convertElementPropertyToString(authorityList, "aname", ", ");
		return authNames;
	}
	private String menuNames;
	@Transient
	public String getMenuNames() {
		menuNames = ReflectionUtils.convertElementPropertyToString(menuList, "menuname", ", ");
		return menuNames;
	}
	private String authCNames;
	@Transient
	public String getAuthCNames() {
		authCNames = ReflectionUtils.convertElementPropertyToString(authorityList, "cname", ", ");
		return authCNames;
	}
	
	private String authIds;
	@Transient
	public String getAuthIds() {
		authIds = ReflectionUtils.convertElementPropertyToString(authorityList, "id", ",");
		return authIds;
	}
	
	private String menuIds;
	@Transient
	public String getMenuIds() {
		menuIds = ReflectionUtils.convertElementPropertyToString(menuList, "id", ", ");
		return menuIds;
	}
	
	@Transient
	public List<Menutable> getMenuOne() {
		return ReflectionUtils.convertElementPropertyToListValue(menuList, 1);
	}
	
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getAuthIdList() {
		return ReflectionUtils.convertElementPropertyToList(authorityList, "id");
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
