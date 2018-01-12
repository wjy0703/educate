package cn.com.educate.core.orm.hibernate;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 含审计信息的Entity基类.
 * 
 * @author jiangxd
 */
@MappedSuperclass
public class AuditableEntity extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4325869609939728747L;
	
	private String modifyuser;//修改人
	/**修改人*/
	@Column(columnDefinition=DEF_STR40, insertable = false)
	public String getModifyuser() {
		return this.modifyuser;
	}
	/**修改人*/
	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}
	
	private String createuser;//创建人
	/**创建人*/
	@Column(columnDefinition=DEF_STR40, updatable = false)
	public String getCreateuser() {
		return this.createuser;
	}
	/**创建人*/
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	private Timestamp modifytime;//修改时间
	/**修改时间*/
	@Column(insertable = false)
	public Timestamp getModifytime() {
		return this.modifytime;
	}
	/**修改时间*/
	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}
	private Timestamp createtime;//创建时间
	/**创建时间*/
	@Column(updatable = false)
	public Timestamp getCreatetime() {
		return this.createtime;
	}
	/**创建时间*/
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}
