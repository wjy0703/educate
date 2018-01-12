package cn.com.educate.app.dao.login;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Operatelog;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class OperatelogDao extends HibernateDao<Operatelog, Long>{

	public Page<Operatelog> queryOperatelog(Page<Operatelog> page, Map<String, Object> params){
//		String hql = "from Operatelog operatelog where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Operatelog operatelog where 1=1");
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
		//IP地址
		if(params.containsKey("ip")){
//			hql = hql + " and ip = :ip";
			hql.append(" and ip = :ip");
		}
		//内容
		if(params.containsKey("remarks")){
//			hql = hql + " and remarks = :remarks";
			hql.append(" and remarks = :remarks");
		}
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
}
