package cn.com.educate.app.service.reat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.dao.reat.ReatpackageDao;
import cn.com.educate.app.entity.login.Reatpackage;
import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class ReatpackageManager {

	private ReatpackageDao reatpackageDao;
	@Autowired
	public void setReatpackageDao(ReatpackageDao reatpackageDao) {
		this.reatpackageDao = reatpackageDao;
	}
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	@Transactional(readOnly = true)
	public Page<Reatpackage> searchReatpackage(final Page<Reatpackage> page, final Map<String,Object> filters) {
		return reatpackageDao.queryReatpackage(page, filters);
	}
	@Transactional(readOnly = true)
	public Reatpackage getReatpackage(Long id) {
		return reatpackageDao.get(id);
	}

	public void saveReatpackage(Reatpackage entity) {
		reatpackageDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteReatpackage(Long id) {
		reatpackageDao.delete(id);
	}
	
	public boolean batchDelReatpackage(String[] ids){
		
		try {
			for(String id: ids){
				deleteReatpackage(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void insertReatpackage(String insertName,Map<String,Object> conditions){
		jdbcDao.insertBySqlTemplate(insertName, conditions);
	}
	
	public void updateReatpackage(String updateName,Map<String,Object> conditions){
		jdbcDao.updateBySqlTemplate(updateName, conditions);
	}
	private Map<String, Object> fromReatpackageEntity(Reatpackage reatpackage){
    	Map<String, Object> conditions = new HashMap<String, Object>();
    	conditions.put("id", reatpackage.getId());
    	conditions.put("reatname", reatpackage.getReatname());
    	conditions.put("cycke", reatpackage.getCycke());
    	conditions.put("price", reatpackage.getPrice());
    	conditions.put("createtime", reatpackage.getCreatetime());
    	conditions.put("modifytime", reatpackage.getModifytime());
    	conditions.put("createuser", reatpackage.getCreateuser());
    	conditions.put("modifyuser", reatpackage.getModifyuser());
    	conditions.put("vtypes", reatpackage.getVtypes());
    	return conditions;
    }
	@Transactional(readOnly = true)
	public List<Map<String,Object>> searchReatpackage(String queryName,Map<String,Object> filter,JdbcPage page)
	{
		StringBuffer sql=new StringBuffer();
		String value = "";
		//套餐名称
		if(filter.containsKey("reatname")){
			value = String.valueOf(filter.get("reatname"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.reatname = '").append(value).append("'");
				//sql = sql + " and a.reatname = '" +  value + "'";
			}
		}
		//周期（月）
		if(filter.containsKey("cycke")){
			value = String.valueOf(filter.get("cycke"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.cycke = '").append(value).append("'");
				//sql = sql + " and a.cycke = '" +  value + "'";
			}
		}
		//套餐价格
		if(filter.containsKey("price")){
			value = String.valueOf(filter.get("price"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.price = '").append(value).append("'");
				//sql = sql + " and a.price = '" +  value + "'";
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
    public List<Reatpackage> getReatForLook(Long reatid,Map<String, Object> params) {
        List<Reatpackage> allRoles = reatpackageDao.findReat(params);
        for(Reatpackage r1: allRoles){
            if(r1.getId() == reatid){
                r1.setFlag("Y");
                break;
            }
        }
        return allRoles;
    }
}
