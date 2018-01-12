package cn.com.educate.app.service.baseinfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.dao.login.MenutableDao;
import cn.com.educate.app.dao.login.UserinfoDao;
import cn.com.educate.app.entity.login.Menutable;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.service.security.OperatorDetails;
import cn.com.educate.app.util.PropertiesUtils;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class MenutableManager {

	private MenutableDao menutableDao;
	@Autowired
	public void setMenutableDao(MenutableDao menutableDao) {
		this.menutableDao = menutableDao;
	}
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	@Autowired
	private UserinfoDao userinfoDao;
	
	@Transactional(readOnly = true)
	public Page<Menutable> searchMenutable(final Page<Menutable> page, final Map<String,Object> filters) {
		return menutableDao.queryMenutable(page, filters);
	}
	@Transactional(readOnly = true)
	public Menutable getMenutable(Long id) {
		return menutableDao.get(id);
	}

	public void saveMenutable(Menutable entity) {
		menutableDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteMenutable(Long id) {
		menutableDao.delete(id);
	}
	
	public boolean batchDelMenutable(String[] ids){
		
		try {
			for(String id: ids){
				deleteMenutable(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void insertMenutable(String insertName,Map<String,Object> conditions){
		jdbcDao.insertBySqlTemplate(insertName, conditions);
	}
	
	public void updateMenutable(String updateName,Map<String,Object> conditions){
		jdbcDao.updateBySqlTemplate(updateName, conditions);
	}
	private Map<String, Object> fromMenutableEntity(Menutable menutable){
    	Map<String, Object> conditions = new HashMap<String, Object>();
    	conditions.put("id", menutable.getId());
    	conditions.put("external", menutable.getExternal());
    	conditions.put("fresh", menutable.getFresh());
    	conditions.put("levelid", menutable.getLevelid());
    	conditions.put("menuname", menutable.getMenuname());
    	conditions.put("menutype", menutable.getMenutype());
    	conditions.put("menuurl", menutable.getMenuurl());
    	conditions.put("rel", menutable.getRel());
    	conditions.put("sortno", menutable.getSortno());
    	conditions.put("vsts", menutable.getVsts());
    	conditions.put("target", menutable.getTarget());
    	conditions.put("title", menutable.getTitle());
    	conditions.put("vtypes", menutable.getVtypes());
    	conditions.put("modifyuser", menutable.getModifyuser());
    	conditions.put("createuser", menutable.getCreateuser());
    	conditions.put("modifytime", menutable.getModifytime());
    	conditions.put("createtime", menutable.getCreatetime());
    	return conditions;
    }
	@Transactional(readOnly = true)
	public List<Map<String,Object>> searchMenutable(String queryName,Map<String,Object> filter,JdbcPage page)
	{
		StringBuffer sql=new StringBuffer();
		String value = "";
		//是否是外部页面
		if(filter.containsKey("vexternal")){
			value = String.valueOf(filter.get("vexternal"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vexternal = '").append(value).append("'");
				//sql = sql + " and a.vexternal = '" +  value + "'";
			}
		}
		//是否相同页面引用中打开
		if(filter.containsKey("fresh")){
			value = String.valueOf(filter.get("fresh"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.fresh = '").append(value).append("'");
				//sql = sql + " and a.fresh = '" +  value + "'";
			}
		}
		//菜单编号
		if(filter.containsKey("levelid")){
			value = String.valueOf(filter.get("levelid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.levelid = '").append(value).append("'");
				//sql = sql + " and a.levelid = '" +  value + "'";
			}
		}
		//菜单名
		if(filter.containsKey("menuname")){
			value = String.valueOf(filter.get("menuname"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.menuname = '").append(value).append("'");
				//sql = sql + " and a.menuname = '" +  value + "'";
			}
		}
		//菜单类型
		if(filter.containsKey("menutype")){
			value = String.valueOf(filter.get("menutype"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.menutype = '").append(value).append("'");
				//sql = sql + " and a.menutype = '" +  value + "'";
			}
		}
		//菜单地址
		if(filter.containsKey("menuurl")){
			value = String.valueOf(filter.get("menuurl"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.menuurl = '").append(value).append("'");
				//sql = sql + " and a.menuurl = '" +  value + "'";
			}
		}
		//REL引用地址
		if(filter.containsKey("rel")){
			value = String.valueOf(filter.get("rel"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.rel = '").append(value).append("'");
				//sql = sql + " and a.rel = '" +  value + "'";
			}
		}
		//序号
		if(filter.containsKey("sortno")){
			value = String.valueOf(filter.get("sortno"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.sortno = '").append(value).append("'");
				//sql = sql + " and a.sortno = '" +  value + "'";
			}
		}
		//菜单状态
		if(filter.containsKey("vsts")){
			value = String.valueOf(filter.get("vsts"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vsts = '").append(value).append("'");
				//sql = sql + " and a.vsts = '" +  value + "'";
			}
		}
		//页面目标
		if(filter.containsKey("target")){
			value = String.valueOf(filter.get("target"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.target = '").append(value).append("'");
				//sql = sql + " and a.target = '" +  value + "'";
			}
		}
		//页面标题
		if(filter.containsKey("title")){
			value = String.valueOf(filter.get("title"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.title = '").append(value).append("'");
				//sql = sql + " and a.title = '" +  value + "'";
			}
		}
		//上级菜单
		if(filter.containsKey("parentid")){
			value = String.valueOf(filter.get("parentid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.parentid = '").append(value).append("'");
				//sql = sql + " and a.parentid = '" +  value + "'";
			}
		}
		//系统属性
		if(filter.containsKey("vtypes")){
			value = String.valueOf(filter.get("vtypes"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.vtypes = '").append(value).append("'");
				//sql = sql + " and a.vtypes = '" +  value + "'";
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
		
		if (page.getOrderBy()!=null){
//			sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
			sql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		
		System.out.println("sql=======>" + sql.toString());
		Map<String, Object> conditions = new HashMap<String,Object>();
		conditions.put("sql", sql.toString());
		
		return jdbcDao.searchPagesByMergeSqlTemplate(queryName, conditions, page);
	}
	
	
	public List<Menutable> getMenusByParent(Long parentId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentid", parentId);
		PropertiesUtils.putBusidCheck(params);
		return menutableDao.getMenusByParent(params);
	}
	
	public List<Menutable> buildMenuByTopId(List<Menutable> menus) {
		List<Menutable> menus2 = new LinkedList<Menutable>();
		OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		Userinfo u = userinfoDao.get(operator.getUserId());
		List<Menutable> mList = u.getRoleinfo().getMenuList();// r1.getMenuList();
		for (Menutable menu : menus) {
			for (Menutable menu1 : mList) {
				if (menu.getId() == menu1.getId()) {
					menus2.add(menu);
					break;
				}
			}
		}
		return menus2;
	}
	
	public void buildMenuByTopId(List<Menutable> menus, Long parentId) {
		List<Menutable> menuList = getMenusByParent(parentId);
		if (menuList != null) {
			for (Menutable m : menuList) {
				menus.add(m);
				buildMenuByTopId(menus, m.getId());
			}
		}
	}
	
	public List<Menutable> getMenusByLevel(Integer levelId) {

		List<Menutable> menus2 = new LinkedList<Menutable>();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("levelid", Long.parseLong(levelId+""));
		PropertiesUtils.putBusidCheck(params);
		List<Menutable> menus = menutableDao.getMenusByLevel(params);

		if (levelId == 1) {
			OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
			Userinfo u = userinfoDao.get(operator.getUserId());
				List<Menutable> mList = u.getRoleinfo().getMenuList();
				for (Menutable menu : menus) {
					for (Menutable menu1 : mList) {
						if (menu.getId() == menu1.getId()) {
							menus2.add(menu);
							break;
						}
					}
			}
		} else {
			menus2 = menus;
		}

		return menus2;
	}
}
