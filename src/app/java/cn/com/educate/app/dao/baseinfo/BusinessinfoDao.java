package cn.com.educate.app.dao.baseinfo;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Businessinfo;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class BusinessinfoDao extends HibernateDao<Businessinfo, Long>{

	public Page<Businessinfo> queryBusinessinfo(Page<Businessinfo> page, Map<String, Object> params){
//		String hql = "from Businessinfo businessinfo where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Businessinfo businessinfo where 1=1");
		//公司账户
		if(params.containsKey("busiaccount")){
//			hql = hql + " and busiaccount = :busiaccount";
			hql.append(" and busiaccount like '%'||:busiaccount||'%'");
		}
		//公司名
		if(params.containsKey("businame")){
//			hql = hql + " and businame = :businame";
			hql.append(" and businame like '%'||:businame||'%'");
		}
		//法人
		if(params.containsKey("corporation")){
//			hql = hql + " and corporation = :corporation";
			hql.append(" and corporation like '%'||:corporation||'%'");
		}
		//证件号码
		if(params.containsKey("card")){
//			hql = hql + " and card = :card";
			hql.append(" and card = :card");
		}
		//联系方式
		if(params.containsKey("phone")){
//			hql = hql + " and phone = :phone";
			hql.append(" and phone = :phone");
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
		//属性（在用、欠费、停用）
		if(params.containsKey("vtypes")){
//			hql = hql + " and vtypes = :vtypes";
			hql.append(" and vtypes = :vtypes");
		}
		//套餐类型
		if(params.containsKey("tctypes")){
//			hql = hql + " and tctypes = :tctypes";
			hql.append(" and tctypes = :tctypes");
		}
		//生效时间
		if(params.containsKey("starttime")){
//			hql = hql + " and starttime = :starttime";
			hql.append(" and starttime = :starttime");
		}
		//到期时间
		if(params.containsKey("overtime")){
//			hql = hql + " and overtime = :overtime";
			hql.append(" and overtime = :overtime");
		}
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
}
