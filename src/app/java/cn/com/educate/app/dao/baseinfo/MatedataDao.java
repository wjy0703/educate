package cn.com.educate.app.dao.baseinfo;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Matedata;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class MatedataDao extends HibernateDao<Matedata, Long>{

	public Page<Matedata> queryMatedata(Page<Matedata> page, Map<String, Object> params){
//		String hql = "from Matedata matedata where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Matedata matedata where 1=1");
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
		//名称
		if(params.containsKey("matename")){
//			hql = hql + " and matename = :matename";
//			hql = hql + " and rolename like '%'||:name||'%'";
			hql.append(" and matename like '%'||:matename||'%' ");
		}
		if(params.containsKey("code")){
			String code = params.get("code").toString();
			params.put("code", Long.valueOf(code));
			hql.append(" and code.id =:code ");
//			filters.remove("code");
		}
		//状态
		if(params.containsKey("vtypes")){
//			hql = hql + " and vtypes = :vtypes";
			hql.append(" and vtypes = :vtypes");
		}
		//值
		if(params.containsKey("vvalue")){
//			hql = hql + " and vvalue = :vvalue";
			hql.append(" and vvalue = :vvalue");
		}
		//编码类型
		if(params.containsKey("matetypeid")){
//			hql = hql + " and matetypeid = :matetypeid";
			hql.append(" and matetypeid = :matetypeid");
		}
		//银行类补充一列银行代码
		if(params.containsKey("bankcode")){
//			hql = hql + " and bankcode = :bankcode";
			hql.append(" and bankcode = :bankcode");
		}
		if (page.getOrderBy()!=null){
//			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
			hql.append(" order by ").append(page.getOrderBy()).append(" ").append(page.getOrder());
		}
		return this.findPage(page, hql.toString(), params);
	}
}
