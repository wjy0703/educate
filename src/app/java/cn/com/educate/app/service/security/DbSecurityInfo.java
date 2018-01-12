package cn.com.educate.app.service.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.login.AuthorityDao;
import cn.com.educate.app.dao.login.RoleinfoDao;
import cn.com.educate.app.entity.security.Authority;

@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class DbSecurityInfo {

	
	@SuppressWarnings("unused")
	private RoleinfoDao roleDao;
	private AuthorityDao authorityDao;
	
	public Map<String, Collection<ConfigAttribute>> loadResourceDefine(){
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
//		List<Role> roles = roleDao.getAll("id", true);
//		for (Role role : roles) {
//			ConfigAttribute ca = new SecurityConfig(role.getName());
//			List<Authority> authoritys = role.getAuthorityList();
//			
//			for (Authority authority : authoritys) {
//				if (resourceMap.containsKey(authority.getPath())) {
//					resourceMap.get(authority.getPath()).add(ca);
//				} else {
//					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
//					atts.add(ca);
//					resourceMap.put(authority.getPath(), atts);
//				}
//			}
//		}
		List<Authority> authorities = authorityDao.queryAuthorities();
		for (Authority authority : authorities){
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
			ConfigAttribute configAttribute = new SecurityConfig(authority.getPrefixedName());
			configAttributes.add(configAttribute);
			resourceMap.put(authority.getVpath(), configAttributes);
		}
		
		return resourceMap;
	}
	
	@Autowired	
	public void setRoleDao(RoleinfoDao roleDao) {
		this.roleDao = roleDao;
	}
	
	@Autowired	
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}


}
