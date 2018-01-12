package cn.com.educate.app.service.security;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import cn.com.educate.app.entity.security.Roleinfo;


/**
 * 扩展SpringSecurity的WebAuthenticationDetails类, 增加登录时间属性和角色属性.
 * 
 * @author calvin
 */
public class OperatorDetails extends User {
	private static final long serialVersionUID = 1919464185097508773L;

	private Date loginTime;

	private List<Roleinfo> roleList;

	private Long userId;
	
	private String ctiCode;
	
	private Long deptId;
	
	private String cityInData;
	
	private Long LoginEmployeeId;
	
	private String positionCode;
	
	private String sex;
	
	private Long busid;
	
	private String busname;
	
	private String orgname;
	
	private String busacc;
	
	public String getBusacc() {
		return busacc;
	}

	public void setBusacc(String busacc) {
		this.busacc = busacc;
	}

	public Long getBusid() {
		return busid;
	}

	public void setBusid(Long busid) {
		this.busid = busid;
	}

	public OperatorDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities)
			throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public List<Roleinfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Roleinfo> roleList) {
		this.roleList = roleList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCtiCode() {
		return ctiCode;
	}

	public void setCtiCode(String ctiCode) {
		this.ctiCode = ctiCode;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getCityInData() {
		return cityInData;
	}

	public void setCityInData(String cityInData) {
		this.cityInData = cityInData;
	}

	public Long getLoginEmployeeId() {
		return LoginEmployeeId;
	}

	public void setLoginEmployeeId(Long loginEmployeeId) {
		LoginEmployeeId = loginEmployeeId;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBusname() {
		return busname;
	}

	public void setBusname(String busname) {
		this.busname = busname;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
}
