package cn.com.educate.app.dao.reat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Buyreatinfo;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class BuyreatinfoDao extends HibernateDao<Buyreatinfo, Long>{

	public Page<Buyreatinfo> queryBuyreatinfo(Page<Buyreatinfo> page, Map<String, Object> params){
//		String hql = "from Buyreatinfo buyreatinfo where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Buyreatinfo buyreatinfo where 1=1");
		//所属企业
		if(params.containsKey("busid")){
//			hql = hql + " and busid = :busid";
			hql.append(" and busid = :busid");
		}
		//套餐id
		if(params.containsKey("reatid")){
//			hql = hql + " and reatid = :reatid";
			hql.append(" and reatid = :reatid");
		}
		//购买时间
		if(params.containsKey("buytime")){
//			hql = hql + " and buytime = :buytime";
			hql.append(" and buytime = :buytime");
		}
		//支付金额
		if(params.containsKey("paymoney")){
//			hql = hql + " and paymoney = :paymoney";
			hql.append(" and paymoney = :paymoney");
		}
		//支付方式
		if(params.containsKey("payway")){
//			hql = hql + " and payway = :payway";
			hql.append(" and payway = :payway");
		}
		//支付账户
		if(params.containsKey("payaccount")){
//			hql = hql + " and payaccount = :payaccount";
			hql.append(" and payaccount = :payaccount");
		}
		//支付账号
		if(params.containsKey("paycard")){
//			hql = hql + " and paycard = :paycard";
			hql.append(" and paycard = :paycard");
		}
		//套餐名称
		if(params.containsKey("reatname")){
//			hql = hql + " and reatname = :reatname";
			hql.append(" and reatname = :reatname");
		}
		//周期（月）
		if(params.containsKey("cycke")){
//			hql = hql + " and cycke = :cycke";
			hql.append(" and cycke = :cycke");
		}
		//套餐价格
		if(params.containsKey("price")){
//			hql = hql + " and price = :price";
			hql.append(" and price = :price");
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
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
	
	public List<Buyreatinfo> getBuyreatinfo(Map<String, Object> params){
		StringBuffer hql=new StringBuffer();
		hql.append("from Buyreatinfo buyreatinfo where 1=1");
		//所属企业
		if(params.containsKey("busid")){
//			hql = hql + " and busid = :busid";
			hql.append(" and busid = :busid");
		}
		return this.find(hql.toString(), params);
	}
}
