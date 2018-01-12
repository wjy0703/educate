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
 * Roleauthority entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "roleauthority")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Roleauthority extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -2276242824197821671L;
	private Long roleid;//角色id
	/**角色id*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getRoleid() {
		return this.roleid;
	}
	/**角色id*/
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	private Long authorityid;//按钮id
	/**按钮id*/
	@Column(columnDefinition=DEF_NUM10)
	public Long getAuthorityid() {
		return this.authorityid;
	}
	/**按钮id*/
	public void setAuthorityid(Long authorityid) {
		this.authorityid = authorityid;
	}
}
