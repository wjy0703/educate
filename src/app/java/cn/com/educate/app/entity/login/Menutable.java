package cn.com.educate.app.entity.login;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.com.educate.core.orm.hibernate.AuditableEntity;

/**
 * 菜单.
 * 
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 * 关于属性的annotation 要么全放到属性定义的前面，要么全放到方法的前面，放到方法的前面要注意
 * 都放到get方法的前面。
 * @author jiangxd
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "menutable")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menutable extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -276242824197821671L;
	private String external;//是否是外部页面
	/**是否是外部页面*/
	@Column(columnDefinition=DEF_STR2)
	public String getExternal() {
		return this.external;
	}
	/**是否是外部页面*/
	public void setExternal(String external) {
		this.external = external;
	}
	private String fresh;//是否相同页面引用中打开
	/**是否相同页面引用中打开*/
	@Column(columnDefinition=DEF_STR2)
	public String getFresh() {
		return this.fresh;
	}
	/**是否相同页面引用中打开*/
	public void setFresh(String fresh) {
		this.fresh = fresh;
	}
	private Long levelid;//菜单编号
	/**菜单编号*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getLevelid() {
		return this.levelid;
	}
	/**菜单编号*/
	public void setLevelid(Long levelid) {
		this.levelid = levelid;
	}
	private String menuname;//菜单名
	/**菜单名*/
	@Column(columnDefinition=DEF_STR64)
	public String getMenuname() {
		return this.menuname;
	}
	/**菜单名*/
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	private Long menutype;//菜单类型
	/**菜单类型*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getMenutype() {
		return this.menutype;
	}
	/**菜单类型*/
	public void setMenutype(Long menutype) {
		this.menutype = menutype;
	}
	private String menuurl;//菜单地址
	/**菜单地址*/
	@Column(columnDefinition=DEF_STR256)
	public String getMenuurl() {
		return this.menuurl;
	}
	/**菜单地址*/
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	private String rel;//REL引用地址
	/**REL引用地址*/
	@Column(columnDefinition=DEF_STR50)
	public String getRel() {
		return this.rel;
	}
	/**REL引用地址*/
	public void setRel(String rel) {
		this.rel = rel;
	}
	private Long sortno;//序号
	/**序号*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getSortno() {
		return this.sortno;
	}
	/**序号*/
	public void setSortno(Long sortno) {
		this.sortno = sortno;
	}
	private String vsts;//菜单状态-弃用
	/**菜单状态-弃用*/
	@Column(columnDefinition=DEF_STR2)
	public String getVsts() {
		return this.vsts;
	}
	/**菜单状态-弃用*/
	public void setVsts(String vsts) {
		this.vsts = vsts;
	}
	private String target;//页面目标
	/**页面目标*/
	@Column(columnDefinition=DEF_STR16)
	public String getTarget() {
		return this.target;
	}
	/**页面目标*/
	public void setTarget(String target) {
		this.target = target;
	}
	private String title;//页面标题
	/**页面标题*/
	@Column(columnDefinition=DEF_STR64)
	public String getTitle() {
		return this.title;
	}
	/**页面标题*/
	public void setTitle(String title) {
		this.title = title;
	}
//	private Long parentid;//上级菜单
//	/**上级菜单*/
//	@Column(columnDefinition=DEF_NUM10)
//	public Long getParentid() {
//		return this.parentid;
//	}
//	/**上级菜单*/
//	public void setParentid(Long parentid) {
//		this.parentid = parentid;
//	}
	
	private Menutable parent;	
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "parentid",unique= false, nullable=true, insertable=true, updatable=true)
	public Menutable getParent() {
		return parent;
	}
	public void setParent(Menutable parent) {
		this.parent = parent;
	}
	private String vtypes;//菜单状态
	/**菜单状态*/
	@Column(columnDefinition=DEF_STR2)
	public String getVtypes() {
		return this.vtypes;
	}
	/**菜单状态*/
	public void setVtypes(String vtypes) {
		this.vtypes = vtypes;
	}
	private String vsystype;//系统属性
	/**系统属性*/
	@Column(columnDefinition=DEF_STR4)
	public String getVsystype() {
		return this.vsystype;
	}
	/**系统属性*/
	public void setVsystype(String vsystype) {
		this.vsystype = vsystype;
	}
	@Transient
	public String getAttrTarget(){
		String _target = target;
		if (null==target || "".equals(target) || "null".equals(target)){
			_target = "navTab";
		}
		return "target=\"" + _target + "\" ";
	}
	
	@Transient
	public String getAttrRel(){
		String _rel = rel;
		if (null==rel || "".equals(rel) || "null".equals(rel)){
			_rel = "rel_"+ getId();
		}
		return "rel=\"" + _rel + "\" ";
	}

	@Transient
	public String getAttrExternal(){
		if (external.equals("Y")){
			return "external=\"true\" ";
		}
		else if(external.equals("N")){
			return "external=\"false\" ";
		}
		else{
			return "";
		}
	}

	@Transient
	public String getAttrFresh(){
		if (fresh.equals("N")){
			return "fresh=\"false\" ";
		}
		else if (fresh.equals("Y")){
			return "fresh=\"true\" ";
		}
		else{
			return "";
		}
	}
	
	@Transient
	public String getAttrTitle(){		
		if (null!=title && !"".equals(title) && !"null".equals(title)){
			return "title=\"" + title + "\" ";
		}
		else{
			return "";
		}
	}

	@Transient
	public String getAttrMenuUrl(){
		if (null!=menuurl && !"".equals(menuurl) && !"null".equals(menuurl)){
			if(menuurl.indexOf("http://")==0){
				return "href=\"" + menuurl + "\" ";
			}
			else if(menuurl.indexOf("javascript:")==0){
				return "href=\"" + menuurl + "\" ";
			}
			else{
				return "href=\"_CTX_" + menuurl + "\" ";
			}
		}
		else{
			return "";
		}
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
