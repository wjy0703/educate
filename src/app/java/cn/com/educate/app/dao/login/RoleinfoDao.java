package cn.com.educate.app.dao.login;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class RoleinfoDao extends HibernateDao<Roleinfo, Long>{

	public Page<Roleinfo> queryRoleinfo1(Page<Roleinfo> page, Map<String, Object> params){
//		String hql = "from Roleinfo roleinfo where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Roleinfo roleinfo where 1=1");
		//名称
		if(params.containsKey("rolename")){
//			hql = hql + " and rolename = :rolename";
			hql.append(" and rolename = :rolename");
		}
		//创建时间
		if(params.containsKey("createtime")){
//			hql = hql + " and createtime = :createtime";
			hql.append(" and createtime = :createtime");
		}
		//修改时间
		if(params.containsKey("modifytime")){
//			hql = hql + " and modifytime = :modifytime";
			hql.append(" and modifytime = :modifytime");
		}
		//创建人
		if(params.containsKey("createuser")){
//			hql = hql + " and createuser = :createuser";
			hql.append(" and createuser = :createuser");
		}
		//修改人
		if(params.containsKey("modifyuser")){
//			hql = hql + " and modifyuser = :modifyuser";
			hql.append(" and modifyuser = :modifyuser");
		}
		//属性
		if(params.containsKey("vtypes")){
//			hql = hql + " and vtypes = :vtypes";
			hql.append(" and vtypes = :vtypes");
		}
		//所属企业
		if(params.containsKey("busid")){
//			hql = hql + " and busid = :busid";
			hql.append(" and busid = :busid");
		}
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Roleinfo> getRolesBySts(){
		Criteria criteria = createCriteria();
		Criterion criterion = Restrictions.eq("vtypes", "0");
		criteria.add(criterion);
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
	

	public Page<Roleinfo> queryRoleinfo(Page<Roleinfo> page, Map<String, Object> params) {
		String hql = "from Roleinfo role where 1=1";
		
		if(params.containsKey("name")){
			hql = hql + " and rolename like '%'||:name||'%'";
		}
		if(params.containsKey("vtypes")){
			hql = hql + " and vtypes = :vtypes";
		}
		if(params.containsKey("busid")){
			hql = hql + " and busid = :busid";
		}
		if (page.getOrderBy()!=null){
			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
		}
		return this.findPage(page, hql, params);
	}
	
	public List<Roleinfo> findRole(Map<String, Object> params) {
	    String hql = "from Roleinfo role where 1=1 and vtypes = '0' ";
	    if(params.containsKey("busid")){
			hql = hql + " and busid = :busid ";
		}
	    if(params.containsKey("rolename")){
			hql = hql + " and rolename = :rolename ";
		}
        hql = hql + " order by role.id asc" ;
	    return this.find(hql, params);
	}
}
