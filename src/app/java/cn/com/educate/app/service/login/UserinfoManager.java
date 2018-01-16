package cn.com.educate.app.service.login;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.dao.login.AuthorityDao;
import cn.com.educate.app.dao.login.MenutableDao;
import cn.com.educate.app.dao.login.RoleinfoDao;
import cn.com.educate.app.dao.login.UserinfoDao;
import cn.com.educate.app.entity.login.Menutable;
import cn.com.educate.app.entity.security.Authority;
import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.service.ServiceException;
import cn.com.educate.app.util.PropertiesUtils;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;
import cn.com.educate.core.utils.EncodeUtils;
import cn.com.educate.core.utils.ReflectionUtils;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserinfoManager {
	private static Logger logger = LoggerFactory.getLogger(UserinfoManager.class);
	
	
	private UserinfoDao userinfoDao;
	@Autowired
	public void setUserinfoDao(UserinfoDao userinfoDao) {
		this.userinfoDao = userinfoDao;
	}
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	private RoleinfoDao roleinfoDao;
    private AuthorityDao authorityDao;
    private MenutableDao menutableDao;
    @Autowired
	public void setRoleinfoDao(RoleinfoDao roleinfoDao) {
		this.roleinfoDao = roleinfoDao;
	}
    @Autowired
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
    @Autowired
	public void setMenutableDao(MenutableDao menutableDao) {
		this.menutableDao = menutableDao;
	}
	@Transactional(readOnly = true)
	public Page<Userinfo> searchUserinfo(final Page<Userinfo> page, final Map<String,Object> filters) {
		return userinfoDao.queryUserinfo(page, filters);
	}
	@Transactional(readOnly = true)
	public Userinfo getUserinfo(Long id) {
		return userinfoDao.get(id);
	}

	public void saveUserinfo(Userinfo entity) {
		userinfoDao.save(entity);
	}

	@Transactional(readOnly = true)
	public List<Map<String,Object>> searchUserinfo(String queryName,Map<String,Object> filter,JdbcPage page)
	{
		StringBuffer sql=new StringBuffer();
		String value = "";
		//账户
		if(filter.containsKey("account")){
			value = String.valueOf(filter.get("account"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.account = '").append(value).append("'");
				//sql = sql + " and a.account = '" +  value + "'";
			}
		}
		//密码
		if(filter.containsKey("password")){
			value = String.valueOf(filter.get("password"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.password = '").append(value).append("'");
				//sql = sql + " and a.password = '" +  value + "'";
			}
		}
		//角色
		if(filter.containsKey("rolesid")){
			value = String.valueOf(filter.get("rolesid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.rolesid = '").append(value).append("'");
				//sql = sql + " and a.rolesid = '" +  value + "'";
			}
		}
		//所属机构
		if(filter.containsKey("orgid")){
			value = String.valueOf(filter.get("orgid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.orgid = '").append(value).append("'");
				//sql = sql + " and a.orgid = '" +  value + "'";
			}
		}
		//姓名
		if(filter.containsKey("vname")){
			value = String.valueOf(filter.get("vname"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vname = '").append(value).append("'");
				//sql = sql + " and a.vname = '" +  value + "'";
			}
		}
		//性别
		if(filter.containsKey("sex")){
			value = String.valueOf(filter.get("sex"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.sex = '").append(value).append("'");
				//sql = sql + " and a.sex = '" +  value + "'";
			}
		}
		//证件号码
		if(filter.containsKey("card")){
			value = String.valueOf(filter.get("card"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.card = '").append(value).append("'");
				//sql = sql + " and a.card = '" +  value + "'";
			}
		}
		//联系方式
		if(filter.containsKey("phone")){
			value = String.valueOf(filter.get("phone"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.phone = '").append(value).append("'");
				//sql = sql + " and a.phone = '" +  value + "'";
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
		//修改时间
		if(filter.containsKey("modifytime")){
			value = String.valueOf(filter.get("modifytime"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.modifytime = '").append(value).append("'");
				//sql = sql + " and a.modifytime = '" +  value + "'";
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
		//修改人
		if(filter.containsKey("modifyuser")){
			value = String.valueOf(filter.get("modifyuser"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.modifyuser = '").append(value).append("'");
				//sql = sql + " and a.modifyuser = '" +  value + "'";
			}
		}
		//属性（在用、停用）
		if(filter.containsKey("vtypes")){
			value = String.valueOf(filter.get("vtypes"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vtypes = '").append(value).append("'");
				//sql = sql + " and a.vtypes = '" +  value + "'";
			}
		}
		//所属企业
		if(filter.containsKey("busid")){
			value = String.valueOf(filter.get("busid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.busid = '").append(value).append("'");
				//sql = sql + " and a.busid = '" +  value + "'";
			}
		}
		//岗位
		if(filter.containsKey("post")){
			value = String.valueOf(filter.get("post"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.post = '").append(value).append("'");
				//sql = sql + " and a.post = '" +  value + "'";
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
	
	public void insertUserinfo(String insertName,Map<String,Object> conditions){
		jdbcDao.insertBySqlTemplate(insertName, conditions);
	}
	
	public void updateUserinfo(String updateName,Map<String,Object> conditions){
		jdbcDao.updateBySqlTemplate(updateName, conditions);
	}
	
	public void saveUser(Userinfo entity) {
		if(entity.getPassword().length()==0){
			Userinfo obj = userinfoDao.get(entity.getId());
			obj.setAccount(entity.getAccount());
			obj.setVtypes(entity.getVtypes());
			obj.setVname(entity.getVname());
			obj.setRoleinfo(entity.getRoleinfo());
//			userinfoDao.save(obj);
//			jdbcDao.updateBySqlTemplate("updateUserinfo", fromUserinfoEntity(obj));
			entity.setPassword(userinfoDao.get(entity.getId()).getPassword());
//			userinfoDao.merge(entity);
		}
		else if (entity.getPassword().length()!=32){
			entity.setPassword(EncodeUtils.getMd5PasswordEncoder(entity.getPassword(),entity.getAccount()));
			userinfoDao.save(entity);
//			jdbcDao.updateBySqlTemplate("updateUserinfo", fromUserinfoEntity(entity));
		}
		else{
			userinfoDao.save(entity);
//			jdbcDao.insertBySqlTemplate("insertUserinfo", fromUserinfoEntity(entity));
		}
		
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUserinfo(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		
		userinfoDao.delete(id);
	}

	public boolean batchDelUserinfo(String[] ids){
		
		try {
			for(String id: ids){
				deleteUserinfo(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 0;
	}

	/**
	 * 使用属性过滤条件查询用户.
	 */
	@Transactional(readOnly = true)
	public Page<Userinfo> searchUser(final Page<Userinfo> page, final Map<String,Object> filters) {
		return userinfoDao.queryUserinfo(page, filters);
	}
	
	@Transactional(readOnly = true)
	public Userinfo findUserByLoginName(String account) {
		Userinfo user = new Userinfo();
		
		user = userinfoDao.findUniqueBy("account", account);
		if(user==null || user.getRoleinfo() == null || !"0".equals(user.getVtypes())){
			user = null;
		}
		
		return user;
	}
	
	public void resetPass(String[] Ids) {
//		String hql = "from Userinfo where 1=1";
//		Object[] obj = null;
//		List<Userinfo> l =  userinfoDao.find(hql, obj);
//		for(Userinfo u : l){
//			if(!u.getLoginName().equals("admin")){
//				u.setPassword(EncodeUtils.getMd5PasswordEncoder("abc123",u.getLoginName()));
//				//saveUser(u);
//				userinfoDao.save(u);
//			}
//		}
		Userinfo u =  new Userinfo();
		for(String id: Ids){
			u  = getUserinfo(Long.valueOf(id));
			u.setPassword(EncodeUtils.getMd5PasswordEncoder("abc123",u.getAccount()));
			userinfoDao.save(u);
		}
		//return userinfoDao.findUniqueBy("loginName", loginName);
	}
	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newAccount, String oldAccount) {
		return userinfoDao.isPropertyUnique("account", newAccount, oldAccount);
	}

	//-- Roleinfo Manager --//
	@Transactional(readOnly = true)
	public Roleinfo getRole(Long id) {
		return roleinfoDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Roleinfo> getAllRole() {
		return roleinfoDao.getAll("id", true);
	}

	/**
	 * 返回所有的角色，已经与用户关联的角色需要做出标志
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Roleinfo> getMergedUserRoleAndAllRole(List<Roleinfo> userRoles) {
		List<Roleinfo> allRoles = roleinfoDao.getRolesBySts();
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

	
	public void saveRole(Roleinfo entity) {
		roleinfoDao.save(entity);
	}

	public void deleteRole(Long id) {
		roleinfoDao.delete(id);
	}

	public List<Roleinfo> getRoleListByIds(	List<Long> ids) {		
		return roleinfoDao.findByIds(ids);
	}

	public void searchRole(Page<Roleinfo> page, Map<String, Object> params) {
		roleinfoDao.queryRoleinfo(page, params);		
	}

	public boolean batchDelRole(String[] ids) {
		try {
			for(String id: ids){
				deleteRole(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean isRoleNameUnique(String newValue, String oldValue) {
		return roleinfoDao.isPropertyUnique("rolename", newValue, oldValue);
		
	}
	public boolean isUserAccountUnique(String newValue, String oldValue) {
		return userinfoDao.isPropertyUnique("account", newValue, oldValue);
		
	}

	public List<Authority> getAuthorityListByIds(List<Long> ids) {
		return authorityDao.findByIds(ids);
	}
	
	public List<Menutable> getMenuListByIds(List<Long> ids) {
		return menutableDao.findByIds(ids);
	}

	public List<Authority> getMergedRoleAuthAndAllAuth(List<Authority> roleAuths) {
		List<Authority> allAuths = authorityDao.queryAuthorities();
		for(Authority a1: roleAuths){
			allAuths.remove(a1);
		}
		return allAuths;
	}
	
	public List<Menutable> getMergedRoleMenu(List<Menutable> roleMenu) {
		Map<String,Object> params = new HashMap<String,Object>();
		PropertiesUtils.putBusidCheck(params);
		List<Menutable> allMenu = menutableDao.queryMenutable(params);
		for(Menutable menu : roleMenu){
			allMenu.remove(menu);
		}
		return allMenu;
	}
	
	public List<Map<String,Object>> getMergedRoleMenu(List<Menutable> menu, List<Menutable> roleMenu) {
		List<Map<String,Object>> tree = new LinkedList<Map<String, Object>>();
		for(Menutable m : menu){
			boolean check = false;
			Map<String, Object> modelMap = new HashMap<String, Object>();
			for(Menutable m1 : roleMenu){
				if(m.getId() == m1.getId()){
					check = true;
					break;
				}
			}
			modelMap.put("menu", m);
			modelMap.put("checked", check);
			tree.add(modelMap);
		}
		return tree;
	}
	
	public List<Menutable> getMenusByLevels(Integer levelId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("levelid", levelId);
		PropertiesUtils.putBusidCheck(params);
		return menutableDao.getMenusByLevel(params);
	}
	
	public String[] buildMenuByTopId(List<Menutable> menuRoleList){
		String[] res1 = new String[2];
		StringBuffer chenkedNode = new StringBuffer();
		List<Menutable> menuList = getMenusByLevels(new Integer(1));
		StringBuffer tree = new StringBuffer();	
		//tree.append("<ul class=\"tree treeFolder\"><li><a href=\"#\">组织机构</a><ul>");
		for(Menutable m : menuList){
			String[] res = buildMenuNode(m.getId(), menuRoleList);
			chenkedNode.append(res[0]);
			tree.append(res[1]);
		}
		//tree.append("</ul></li></ul>");
		res1[0] = chenkedNode.toString();
		res1[1] = tree.toString();
		return res1;
	}
	
	private String[] buildMenuNode(Long orgId, List<Menutable> menuRoleList){
		String[] res = new String[2];
		StringBuffer chenkedNode = new StringBuffer();
		Menutable menu = menutableDao.get(orgId);
		String chenked = "";
		boolean isChenked = getCheckedMenu(menu, menuRoleList);
		if(isChenked){
			chenked = "checked=true";
			chenkedNode.append("{id:'"+menu.getId()+"', name:'"+menu.getMenuname()+"'}|");
		}
		StringBuffer node = new StringBuffer();
		node.append("<li><a tname="+menu.getMenuname()+" tvalue="+menu.getId()+" "+chenked+">"+menu.getMenuname()+"</a>");
		List<Menutable> menuList = getMenuByParent(orgId);
		if(menuList != null && menuList.size()>0 ){
			node.append("<ul>");
			for (Menutable m : menuList) {
				String[] ss = buildMenuNode(m.getId(), menuRoleList);
				chenkedNode.append(ss[0]);
				node.append(ss[1]);
			}
			node.append("</ul>");
		}
		node.append("</li>");
		res[0] = chenkedNode.toString();
		res[1] = node.toString();
		return res;
	}
	
	private boolean getCheckedMenu(Menutable menu, List<Menutable> menuRoleList){
		boolean isChenked = false;
		for (Menutable m : menuRoleList) {
			if(m.getId() == menu.getId()){
				isChenked = true;
				break;
			}
		}
		return isChenked;
	}
	/*
	 * 重载传入查询条件map
	 * YY
	 */
    public List<Menutable> getMenusByLevels(Map<String,Object> params) {
    	PropertiesUtils.putBusidCheck(params);
        return menutableDao.getMenusByLevel(params);
    }
	/**
     * 重载方法原方法保留，加入一个map作为参数查询系统属性
     * @author yuyang
     */
    public String[] buildMenuByTopId(List<Menutable> menuRoleList,Map<String,Object> params){
        String[] res1 = new String[2];
        StringBuffer chenkedNode = new StringBuffer();
        List<Menutable> menuList = getMenusByLevels(params);
        StringBuffer tree = new StringBuffer(); 
        //tree.append("<ul class=\"tree treeFolder\"><li><a href=\"#\">组织机构</a><ul>");
        for(Menutable m : menuList){
            params.put("menuId", m.getId());
            String[] res = buildMenuNode(menuRoleList,params);
            chenkedNode.append(res[0]);
            tree.append(res[1]);
        }
        //tree.append("</ul></li></ul>");
        res1[0] = chenkedNode.toString();
        res1[1] = tree.toString();
        return res1;
    }
    /**
     * 重载方法加入map查询条件
     * @author yuyang
     */
    private String[] buildMenuNode(List<Menutable> menuRoleList,Map<String,Object> params){
        String[] res = new String[2];
        StringBuffer chenkedNode = new StringBuffer();
        Long menuId= Long.parseLong(params.get("menuId")+"");
        Menutable menu = menutableDao.get(menuId);
        String chenked = "";
        boolean isChenked = getCheckedMenu(menu, menuRoleList);
        if(isChenked){
            chenked = "checked=true";
            chenkedNode.append("{id:'"+menu.getId()+"', name:'"+menu.getMenuname()+"'}|");
        }
        StringBuffer node = new StringBuffer();
        node.append("<li><a tname="+menu.getMenuname()+" tvalue="+menu.getId()+" "+chenked+">"+menu.getMenuname()+"</a>");
        List<Menutable> menuList = getMenuByParent(menuId);
        if(menuList != null && menuList.size()>0 ){
            node.append("<ul>");
            for (Menutable m : menuList) {
                params.put("menuId", m.getId());
                String[] ss = buildMenuNode(menuRoleList,params);
                chenkedNode.append(ss[0]);
                node.append(ss[1]);
            }
            node.append("</ul>");
        }
        node.append("</li>");
        res[0] = chenkedNode.toString();
        res[1] = node.toString();
        return res;
    }
	
	public List<Menutable> getMenuByParent(Long parentId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentid", parentId);
		PropertiesUtils.putBusidCheck(params);
		return menutableDao.getMenusByParent(params);
	}

	//-- Authority Manager --//
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}

	public void searchAuthority(Page<Authority> page, Map<String, Object> params) {
		authorityDao.queryAuthority(page, params);	
		
	}
	
	public void saveAuthority(Authority auth) {
		authorityDao.save(auth);
	}
	
	public Authority getAuthority(Long id) {
		return authorityDao.get(id);
	}

	public void deleteAuthority(Long id) {
		authorityDao.delete(id);
		
	}

	public boolean batchDelAuth(String[] ids) {
		try {
			for(String id: ids){
				deleteAuthority(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isAuthNameUnique(String newValue, String oldValue) {
		return authorityDao.isPropertyUnique("name", newValue, oldValue);
	}



	public String getTypeByCode(String TYPE_CODE){
		/*
		StringBuffer sql = new StringBuffer("select xma.name,xma.value ");
		sql.append("    from xhcf_mate_data xma,xhcf_matedata_type xmatype");
		sql.append("    where xma.matedatatype_id = xmatype.id ");
		sql.append("          and  xma.state='0'");
		sql.append("          and xmatype.type_code='").append(TYPE_CODE).append("'");
		sql.append("          ");
        */
		StringBuffer sql = new StringBuffer("select a.matename name,a.vvalue value ");
		sql.append("  from matedata a,matedatatype b ");
		sql.append("  where a.matetypeid=b.id ");
		sql.append("  and a.vtypes='0' ");
		sql.append("  and b.typecode='").append(TYPE_CODE).append("'");
		sql.append("  ");
		
    	List<Map<String, Object>> list = jdbcDao.searchByMergeSql(sql.toString());
    	String seq = "0";
    	if(null != list && list.size()>0){
    		Map<String,Object> map = list.get(0);
    		java.math.BigDecimal count = (java.math.BigDecimal)map.get("MONEY");
    		seq = count.toString();
    	}
		return seq+"";
	}
	
	public String getLoginCityData1(Long id){
		String res = "";
		String sql = "select id from BASE_ZZJG t start with t.id = "+id+" connect by nocycle t.parent_id = prior id";
		List<Map<String, Object>> list = jdbcDao.searchByMergeSql(sql);
		if(list.size() > 0){
			res = ReflectionUtils.convertElementPropertyToString(list, "ID", ",");
		}
		return res;
	}
	
	public boolean isLoginPass(Userinfo u){
		boolean isRes = false;
		if(u != null){
			String md5PassWord = EncodeUtils.getMd5PasswordEncoder("abc123",u.getAccount());
			if(u.getPassword() != null && u.getPassword().equals(md5PassWord)){
				isRes = true;
			}
		}
		return isRes;
	}
}
