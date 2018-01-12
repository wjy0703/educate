package cn.com.educate.app.dao.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Organizeinfo;
import cn.com.educate.app.util.PropertiesUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class OrganizeinfoDao extends HibernateDao<Organizeinfo, Long>{
	
	public List<Organizeinfo> getOrganiByParent(Long parentId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentid", parentId);
		PropertiesUtils.putBusidCheck(params);
		StringBuffer hql=new StringBuffer();
		hql.append("from Organizeinfo organi where organi.vtypes = '0' ");
//		String hql = "from Organizeinfo organi where organi.parentid=? and organi.orgflag = '0' order by organi.id";
		//所属企业
		if(params.containsKey("busid")){
//					hql = hql + " and busid = :busid";
//			hql.append(" and  FIND_IN_SET(busid,'-1,:busid')");
			hql.append(" and  businessinfo.id =:busid ");
		}
		//上级ID
		if(params.containsKey("parentid")){
//					hql = hql + " and parentid = :parentid";
			hql.append(" and parentid = :parentid");
		}
		hql.append("  order by organi.id");
		return this.find(hql.toString(), params);
	}
	public Page<Organizeinfo> queryOrganizeinfo(Page<Organizeinfo> page, Map<String, Object> params){
//		String hql = "from Organizeinfo organizeinfo where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Organizeinfo organizeinfo where 1=1");
		//名称
		if(params.containsKey("orgname")){
//			hql = hql + " and orgname = :orgname";
			hql.append(" and orgname = :orgname");
		}
		//地址
		if(params.containsKey("address")){
//			hql = hql + " and address = :address";
			hql.append(" and address = :address");
		}
		//类别（行业、区域、门店）
		if(params.containsKey("orgflag")){
//			hql = hql + " and orgflag = :orgflag";
			hql.append(" and orgflag = :orgflag");
		}
		//属性（在用、停用）
		if(params.containsKey("vtypes")){
//			hql = hql + " and vtypes = :vtypes";
			hql.append(" and vtypes = :vtypes");
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
		//所属企业
		if(params.containsKey("busid")){
//			hql = hql + " and busid = :busid";
			hql.append(" and businessinfo.busid = :busid");
		}
		//上级ID
		if(params.containsKey("parentid")){
//			hql = hql + " and parentid = :parentid";
			hql.append(" and parentid = :parentid");
		}
		//所有上级
		if(params.containsKey("parentids")){
//			hql = hql + " and parentids = :parentids";
			hql.append(" and parentids = :parentids");
		}
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
}
