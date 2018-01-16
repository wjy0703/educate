package cn.com.educate.app.service.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.entity.security.Authority;
import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.service.security.OperatorDetails;

import com.google.common.collect.Sets;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	private UserinfoManager userinfoManager;
	@Autowired
	public void setUserinfoManager(UserinfoManager userinfoManager) {
		this.userinfoManager = userinfoManager;
	}

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		logger.warn("loadUserByUsername ========= username=======:"+username);
		Userinfo user = userinfoManager.findUserByLoginName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + username + " 不存在");
		}
		//userinfoManager.getRole(user.getRolesid())
		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

		//-- mini-web示例中无以下属性, 暂时全部设为true. --//
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		List<Roleinfo> roleList = new ArrayList<Roleinfo>();;
		OperatorDetails userDetails = new OperatorDetails(user.getAccount(), user.getPassword(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
		//加入登录时间信息和用户角色
		userDetails.setLoginTime(new Date());
		roleList.add(user.getRoleinfo());
		userDetails.setRoleList(roleList);
		userDetails.setUserId(user.getId());
		userDetails.setCtiCode(user.getVname());
		userDetails.setSex(user.getSex());
//		userDetails.setDeptId(user.getOrgid());
//		userDetails.setBusid(user.getBusid());
//		userDetails.setBusid(user.getBusinessinfo().getId());
		userDetails.setLoginEmployeeId(user.getId());
		userDetails.setPositionCode(user.getPost());
//		userDetails.setBusname(user.getBusinessinfo().getBusiname());
//		userDetails.setBusacc(user.getBusinessinfo().getBusiaccount());
//		if(null != user.getOrganizeinfo()){
//			userDetails.setDeptId(user.getOrganizeinfo().getId());
//			userDetails.setOrgname(user.getOrganizeinfo().getOrgname());
//		}
		return userDetails;

	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(Userinfo user) {
		Set<GrantedAuthority> authSet = Sets.newHashSet();
		for (Authority authority : user.getRoleinfo().getAuthorityList()) {
			authSet.add(new GrantedAuthorityImpl(authority.getPrefixedName()));
		}
		return authSet;
	}

	
}
