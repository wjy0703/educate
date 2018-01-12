package cn.com.educate.app.service.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.dao.login.AuthorityDao;
import cn.com.educate.app.dao.login.MenutableDao;
import cn.com.educate.app.dao.login.RoleinfoDao;
import cn.com.educate.app.entity.security.Authority;
import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AuthorityManager {

	private AuthorityDao authorityDao;
	@Autowired
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	private RoleinfoDao roleinfoDao;
    private MenutableDao menutableDao;
    @Autowired
	public void setRoleinfoDao(RoleinfoDao roleinfoDao) {
		this.roleinfoDao = roleinfoDao;
	}
    @Autowired
	public void setMenutableDao(MenutableDao menutableDao) {
		this.menutableDao = menutableDao;
	}
	
	@Transactional(readOnly = true)
	public Page<Authority> searchAuthority(final Page<Authority> page, final Map<String,Object> filters) {
		return authorityDao.queryAuthority(page, filters);
	}
	@Transactional(readOnly = true)
	public Authority getAuthority(Long id) {
		return authorityDao.get(id);
	}

	public void saveAuthority(Authority entity) {
		authorityDao.save(entity);
	}
	@Transactional(readOnly = true)
	public Roleinfo getRoleinfo(Long id) {
		return roleinfoDao.get(id);
	}
	
	public List<Authority> getMergedRoleinfoAuthAndAllAuth(List<Authority> roleAuths) {
        List<Authority> allAuths = authorityDao.queryAuthorities();
        for(Authority a1: roleAuths){
            allAuths.remove(a1);
        }
        return allAuths;
    }
	
	
	/*
     * 重载增加查询条件map
     */
	public List<Authority> getMergedRoleinfoAuthAndAllAuth(List<Authority> roleAuths, Map<String, Object> params) {
		List<Authority> allAuths = authorityDao.queryAuthorities(params);
		for(Authority a1: roleAuths){
			allAuths.remove(a1);
		}
		return allAuths;
	}
	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteAuthority(Long id) {
		authorityDao.delete(id);
	}
	
	public boolean batchDelAuthority(String[] ids){
		
		try {
			for(String id: ids){
				deleteAuthority(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void insertAuthority(String insertName,Map<String,Object> conditions){
		jdbcDao.insertBySqlTemplate(insertName, conditions);
	}
	
	public void updateAuthority(String updateName,Map<String,Object> conditions){
		jdbcDao.updateBySqlTemplate(updateName, conditions);
	}
	
	public boolean isAuthNameUnique(String newValue, String oldValue) {
		return authorityDao.isPropertyUnique("aname", newValue, oldValue);
	}
	@Transactional(readOnly = true)
	public List<Map<String,Object>> searchAuthority(String queryName,Map<String,Object> filter,JdbcPage page)
	{
		StringBuffer sql=new StringBuffer();
		String value = "";
		//修改人
		if(filter.containsKey("modifyuser")){
			value = String.valueOf(filter.get("modifyuser"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.modifyuser = '").append(value).append("'");
				//sql = sql + " and a.modifyuser = '" +  value + "'";
			}
		}
		//创建人
		if(filter.containsKey("createuser")){
			value = String.valueOf(filter.get("createuser"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.createuser = '").append(value).append("'");
				//sql = sql + " and a.createuser = '" +  value + "'";
			}
		}
		//修改时间
		if(filter.containsKey("modifytime")){
			value = String.valueOf(filter.get("modifytime"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.modifytime = '").append(value).append("'");
				//sql = sql + " and a.modifytime = '" +  value + "'";
			}
		}
		//创建时间
		if(filter.containsKey("createtime")){
			value = String.valueOf(filter.get("createtime"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.createtime = '").append(value).append("'");
				//sql = sql + " and a.createtime = '" +  value + "'";
			}
		}
		//别名
		if(filter.containsKey("cname")){
			value = String.valueOf(filter.get("cname"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.cname = '").append(value).append("'");
				//sql = sql + " and a.cname = '" +  value + "'";
			}
		}
		//标记
		if(filter.containsKey("vflag")){
			value = String.valueOf(filter.get("vflag"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vflag = '").append(value).append("'");
				//sql = sql + " and a.vflag = '" +  value + "'";
			}
		}
		//资源名称
		if(filter.containsKey("aname")){
			value = String.valueOf(filter.get("aname"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.aname = '").append(value).append("'");
				//sql = sql + " and a.aname = '" +  value + "'";
			}
		}
		//路径
		if(filter.containsKey("vpath")){
			value = String.valueOf(filter.get("vpath"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vpath = '").append(value).append("'");
				//sql = sql + " and a.vpath = '" +  value + "'";
			}
		}
		//状态
		if(filter.containsKey("vsts")){
			value = String.valueOf(filter.get("vsts"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vsts = '").append(value).append("'");
				//sql = sql + " and a.vsts = '" +  value + "'";
			}
		}
		//类型
		if(filter.containsKey("vtype")){
			value = String.valueOf(filter.get("vtype"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vtype = '").append(value).append("'");
				//sql = sql + " and a.vtype = '" +  value + "'";
			}
		}
		//系统属性
		if(filter.containsKey("vsystype")){
			value = String.valueOf(filter.get("vsystype"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vsystype = '").append(value).append("'");
				//sql = sql + " and a.vsystype = '" +  value + "'";
			}
		}
		
		if (page.getOrderBy()!=null){
//			sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
			sql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		
		System.out.println("sql=======>" + sql.toString());
		Map<String, Object> conditions = new HashMap<String,Object>();
		conditions.put("sql", sql.toString());
		
		return jdbcDao.searchPagesByMergeSqlTemplate(queryName, conditions, page);
	}
	
	@Transactional(readOnly = true)
    public List<Roleinfo> getMergedUserRoleAndAllRole(List<Roleinfo> userRoles,Map<String,Object> params) {
        List<Roleinfo> allRoles = roleinfoDao.findRole(params);
        Long lastFlagUserRoleId = 0L;
        for(Roleinfo r1: allRoles){
            if(r1.getId() < lastFlagUserRoleId){continue;}
            for(Roleinfo r2: userRoles){
                if(r1.getId() == r2.getId()){
                    r1.setFlag("Y");
                    lastFlagUserRoleId = r2.getId();
                    break;
                }
            }
        }
        return allRoles;
    }
}
