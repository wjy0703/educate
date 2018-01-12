package cn.com.educate.app.dao.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Organizeinfo;
import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.util.PropertiesUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class UserinfoDao extends HibernateDao<Userinfo, Long>{
	
	public List<Userinfo> find(Map<String, Object> params){
//		PropertiesUtils.putBusidCheck(params);
		StringBuffer hql=new StringBuffer();
		hql.append("from Userinfo u where u.vtypes='0' ");
//		String hql = "from Organizeinfo organi where organi.parentid=? and organi.orgflag = '0' order by organi.id";
		//所属企业
		if(params.containsKey("orgid")){
//					hql = hql + " and busid = :busid";
			hql.append(" and  organizeinfo.id=:orgid ");
		}
		hql.append("  order by u.id");
		return this.find(hql.toString(), params);
	}
	public Page<Userinfo> queryUserinfo(Page<Userinfo> page, Map<String, Object> params){
//		String hql = "from Userinfo userinfo where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Userinfo userinfo where 1=1");
		
		//账户
		if(params.containsKey("account")){
//			hql = hql + " and account = :account";
//			hql.append(" and account = :account");
			hql.append(" and account like '%'||:account||'%'");
		}
		//密码
		if(params.containsKey("password")){
//			hql = hql + " and password = :password";
			hql.append(" and password = :password");
		}
		//角色
		if(params.containsKey("rolesid")){
//			hql = hql + " and rolesid = :rolesid";
			hql.append(" and rolesid = :rolesid");
		}
		if(params.containsKey("organi.id")){
			String id = params.get("organi.id").toString();
			hql.append(" and organizeinfo.id = "+Long.valueOf(id));
		}
		//姓名
		if(params.containsKey("vname")){
//			hql = hql + " and vname = :vname";
//			hql.append(" and vname = :vname");
			hql.append(" and vname like '%'||:vname||'%'");
		}
		//性别
		if(params.containsKey("sex")){
//			hql = hql + " and sex = :sex";
			hql.append(" and sex = :sex");
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
		//属性（在用、停用）
		if(params.containsKey("vtypes")){
//			hql = hql + " and vtypes = :vtypes";
			hql.append(" and vtypes = :vtypes");
		}
		//所属企业
		if(params.containsKey("busid")){
//			hql = hql + " and busid = :busid";
			hql.append(" and businessinfo.id = :busid");
		}else{
			PropertiesUtils.putBusidCheck(params);
			if(params.containsKey("busid")){
//				hql = hql + " and busid = :busid";
				hql.append(" and businessinfo.id = :busid");
			}
		}
		//岗位
		if(params.containsKey("post")){
//			hql = hql + " and post = :post";
			hql.append(" and post = :post");
		}
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
	
}
