package cn.com.educate.app.service.baseinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.dao.baseinfo.MatedataDao;
import cn.com.educate.app.dao.baseinfo.MatedatatypeDao;
import cn.com.educate.app.dao.login.RoleinfoDao;
import cn.com.educate.app.entity.login.Matedata;
import cn.com.educate.app.entity.login.Matedatatype;
import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class RoleinfoManager {
	
	private MatedataDao matedataDao;
	@Autowired
	public void setMatedataDao(MatedataDao matedataDao) {
		this.matedataDao = matedataDao;
	}
	private MatedatatypeDao matedatatypeDao;
	@Autowired
	public void setMatedatatypeDao(MatedatatypeDao matedatatypeDao) {
		this.matedatatypeDao = matedatatypeDao;
	}
	private RoleinfoDao roleinfoDao;
	@Autowired
	public void setRoleinfoDao(RoleinfoDao roleinfoDao) {
		this.roleinfoDao = roleinfoDao;
	}
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	@Transactional(readOnly = true)
	public Page<Matedata> searchMatedata(final Page<Matedata> page, final Map<String,Object> filters) {
		return matedataDao.queryMatedata(page, filters);
	}
	@Transactional(readOnly = true)
	public Matedata getMatedata(Long id) {
		return matedataDao.get(id);
	}

	public void saveMatedata(Matedata entity) {
		matedataDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteMatedata(Long id) {
		matedataDao.delete(id);
	}
	
	public boolean batchDelMatedata(String[] ids){
		
		try {
			for(String id: ids){
				deleteMatedata(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	@Transactional(readOnly = true)
	public Page<Matedatatype> searchMatedatatype(final Page<Matedatatype> page, final Map<String,Object> filters) {
		return matedatatypeDao.queryMatedatatype(page, filters);
	}
	@Transactional(readOnly = true)
	public Matedatatype getMatedatatype(Long id) {
		return matedatatypeDao.get(id);
	}

	public void saveMatedatatype(Matedatatype entity) {
		matedatatypeDao.save(entity);
	}
	public List<Matedatatype> getAllType() {
		return matedatatypeDao.findAllType();
	}
	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteMatedatatype(Long id) {
		matedatatypeDao.delete(id);
	}
	
	public boolean batchDelMatedatatype(String[] ids){
		
		try {
			for(String id: ids){
				deleteMatedatatype(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Transactional(readOnly = true)
	public Page<Roleinfo> searchRoleinfo(final Page<Roleinfo> page, final Map<String,Object> filters) {
		return roleinfoDao.queryRoleinfo(page, filters);
	}
	@Transactional(readOnly = true)
	public Roleinfo getRoleinfo(Long id) {
		return roleinfoDao.get(id);
	}

	public void saveRoleinfo(Roleinfo entity) {
		roleinfoDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteRoleinfo(Long id) {
		roleinfoDao.delete(id);
	}
	
	public boolean batchDelRoleinfo(String[] ids){
		
		try {
			for(String id: ids){
				deleteRoleinfo(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void insertRoleinfo(String insertName,Map<String,Object> conditions){
		jdbcDao.insertBySqlTemplate(insertName, conditions);
	}
	
	public void updateRoleinfo(String updateName,Map<String,Object> conditions){
		jdbcDao.updateBySqlTemplate(updateName, conditions);
	}
	private Map<String, Object> fromRoleinfoEntity(Roleinfo roleinfo){
    	Map<String, Object> conditions = new HashMap<String, Object>();
    	conditions.put("id", roleinfo.getId());
    	conditions.put("rolename", roleinfo.getRolename());
    	conditions.put("createtime", roleinfo.getCreatetime());
    	conditions.put("modifytime", roleinfo.getModifytime());
    	conditions.put("createuser", roleinfo.getCreateuser());
    	conditions.put("modifyuser", roleinfo.getModifyuser());
    	conditions.put("vtypes", roleinfo.getVtypes());
    	conditions.put("busid", roleinfo.getBusid());
    	return conditions;
    }
	@Transactional(readOnly = true)
	public List<Map<String,Object>> searchRoleinfo(String queryName,Map<String,Object> filter,JdbcPage page)
	{
		StringBuffer sql=new StringBuffer();
		String value = "";
		//名称
		if(filter.containsKey("rolename")){
			value = String.valueOf(filter.get("rolename"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.rolename = '").append(value).append("'");
				//sql = sql + " and a.rolename = '" +  value + "'";
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
		//属性
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
		
		if (page.getOrderBy()!=null){
//			sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
			sql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		
		System.out.println("sql=======>" + sql.toString());
		Map<String, Object> conditions = new HashMap<String,Object>();
		conditions.put("sql", sql.toString());
		
		return jdbcDao.searchPagesByMergeSqlTemplate(queryName, conditions, page);
	}
}
