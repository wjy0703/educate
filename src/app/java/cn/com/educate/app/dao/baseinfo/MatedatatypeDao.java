package cn.com.educate.app.dao.baseinfo;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Matedatatype;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class MatedatatypeDao extends HibernateDao<Matedatatype, Long>{

	public Page<Matedatatype> queryMatedatatype(Page<Matedatatype> page, Map<String, Object> params){
//		String hql = "from Matedatatype matedatatype where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Matedatatype matedatatype where 1=1");
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
		//类型编码
		if(params.containsKey("typecode")){
//			hql = hql + " and typecode = :typecode";
			hql.append(" and typecode = :typecode");
		}
		//描述
		if(params.containsKey("typedes")){
//			hql = hql + " and typedes = :typedes";
			hql.append(" and typedes = :typedes");
		}
		//名称
		if(params.containsKey("typename")){
//			hql = hql + " and typename = :typename";
			hql.append(" and typename = :typename");
		}
		//状态
		if(params.containsKey("vtypes")){
//			hql = hql + " and vtypes = :vtypes";
			hql.append(" and vtypes = :vtypes");
		}
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
}
