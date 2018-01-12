package cn.com.educate.app.service.reat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.dao.reat.BuyreatinfoDao;
import cn.com.educate.app.entity.login.Buyreatinfo;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;



//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class BuyreatinfoManager {

	private BuyreatinfoDao buyreatinfoDao;
	@Autowired
	public void setBuyreatinfoDao(BuyreatinfoDao buyreatinfoDao) {
		this.buyreatinfoDao = buyreatinfoDao;
	}
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	@Transactional(readOnly = true)
	public Page<Buyreatinfo> searchBuyreatinfo(final Page<Buyreatinfo> page, final Map<String,Object> filters) {
		return buyreatinfoDao.queryBuyreatinfo(page, filters);
	}
	@Transactional(readOnly = true)
	public Buyreatinfo getBuyreatinfo(Long id) {
		return buyreatinfoDao.get(id);
	}

	public void saveBuyreatinfo(Buyreatinfo entity) {
		buyreatinfoDao.save(entity);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteBuyreatinfo(Long id) {
		buyreatinfoDao.delete(id);
	}
	
	public boolean batchDelBuyreatinfo(String[] ids){
		
		try {
			for(String id: ids){
				deleteBuyreatinfo(Long.valueOf(id));
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void insertBuyreatinfo(String insertName,Map<String,Object> conditions){
		jdbcDao.insertBySqlTemplate(insertName, conditions);
	}
	
	public void updateBuyreatinfo(String updateName,Map<String,Object> conditions){
		jdbcDao.updateBySqlTemplate(updateName, conditions);
	}
	private Map<String, Object> fromBuyreatinfoEntity(Buyreatinfo buyreatinfo){
    	Map<String, Object> conditions = new HashMap<String, Object>();
    	conditions.put("id", buyreatinfo.getId());
    	conditions.put("busid", buyreatinfo.getBusid());
    	conditions.put("reatid", buyreatinfo.getReatid());
    	conditions.put("buytime", buyreatinfo.getBuytime());
    	conditions.put("paymoney", buyreatinfo.getPaymoney());
    	conditions.put("payway", buyreatinfo.getPayway());
    	conditions.put("payaccount", buyreatinfo.getPayaccount());
    	conditions.put("paycard", buyreatinfo.getPaycard());
    	conditions.put("reatname", buyreatinfo.getReatname());
    	conditions.put("cycke", buyreatinfo.getCycke());
    	conditions.put("price", buyreatinfo.getPrice());
    	conditions.put("createtime", buyreatinfo.getCreatetime());
    	conditions.put("modifytime", buyreatinfo.getModifytime());
    	conditions.put("createuser", buyreatinfo.getCreateuser());
    	conditions.put("modifyuser", buyreatinfo.getModifyuser());
    	return conditions;
    }
	@Transactional(readOnly = true)
	public List<Map<String,Object>> searchBuyreatinfo(String queryName,Map<String,Object> filter,JdbcPage page)
	{
		StringBuffer sql=new StringBuffer();
		String value = "";
		//所属企业
		if(filter.containsKey("busid")){
			value = String.valueOf(filter.get("busid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.busid = '").append(value).append("'");
				//sql = sql + " and a.busid = '" +  value + "'";
			}
		}
		//套餐id
		if(filter.containsKey("reatid")){
			value = String.valueOf(filter.get("reatid"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.reatid = '").append(value).append("'");
				//sql = sql + " and a.reatid = '" +  value + "'";
			}
		}
		//购买时间
		if(filter.containsKey("buytime")){
			value = String.valueOf(filter.get("buytime"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.buytime = '").append(value).append("'");
				//sql = sql + " and a.buytime = '" +  value + "'";
			}
		}
		//支付金额
		if(filter.containsKey("paymoney")){
			value = String.valueOf(filter.get("paymoney"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.paymoney = '").append(value).append("'");
				//sql = sql + " and a.paymoney = '" +  value + "'";
			}
		}
		//支付方式
		if(filter.containsKey("payway")){
			value = String.valueOf(filter.get("payway"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.payway = '").append(value).append("'");
				//sql = sql + " and a.payway = '" +  value + "'";
			}
		}
		//支付账户
		if(filter.containsKey("payaccount")){
			value = String.valueOf(filter.get("payaccount"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.payaccount = '").append(value).append("'");
				//sql = sql + " and a.payaccount = '" +  value + "'";
			}
		}
		//支付账号
		if(filter.containsKey("paycard")){
			value = String.valueOf(filter.get("paycard"));
			if(StringUtils.isNotEmpty(value)) {
				sql.append(" and a.paycard = '").append(value).append("'");
				//sql = sql + " and a.paycard = '" +  value + "'";
			}
		}
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
