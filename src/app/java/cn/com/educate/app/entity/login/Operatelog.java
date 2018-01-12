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
 * Operatelog entity. 
 * @author MyEclipse Persistence Tools
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "operatelog")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Operatelog extends AuditableEntity{

	// Fields
	private static final long serialVersionUID = -5276242824197821671L;
	
	private String ip;//IP地址
	/**IP地址*/
	@Column(columnDefinition=DEF_STR256)
	public String getIp() {
		return this.ip;
	}
	/**IP地址*/
	public void setIp(String ip) {
		this.ip = ip;
	}
	private String remarks;//内容
	/**内容*/
	@Column(columnDefinition=DEF_STR1000)
	public String getRemarks() {
		return this.remarks;
	}
	/**内容*/
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
