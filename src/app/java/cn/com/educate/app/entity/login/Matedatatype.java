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
 * 字典类型表.
 * 
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 * 关于属性的annotation 放到get方法的上面。
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "matedatatype")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Matedatatype extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -1276242824197821671L;
	
	private String typecode;//类型编码
	/**类型编码*/
	@Column(columnDefinition=DEF_STR16)
	public String getTypecode() {
		return this.typecode;
	}
	/**类型编码*/
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	private String typedes;//描述
	/**描述*/
	@Column(columnDefinition=DEF_STR128)
	public String getTypedes() {
		return this.typedes;
	}
	/**描述*/
	public void setTypedes(String typedes) {
		this.typedes = typedes;
	}
	private String typename;//名称
	/**名称*/
	@Column(columnDefinition=DEF_STR128)
	public String getTypename() {
		return this.typename;
	}
	/**名称*/
	public void setTypename(String typename) {
		this.typename = typename;
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
}
