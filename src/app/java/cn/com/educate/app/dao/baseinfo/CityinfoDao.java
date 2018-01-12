package cn.com.educate.app.dao.baseinfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.Cityinfo;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class CityinfoDao extends HibernateDao<Cityinfo, Long>{

	public Page<Cityinfo> queryCityinfo(Page<Cityinfo> page, Map<String, Object> params){
//		String hql = "from Cityinfo cityinfo where 1=1";
		StringBuffer hql=new StringBuffer();
		hql.append("from Cityinfo cityinfo where 1=1");
		//别名
		if(params.containsKey("cname")){
//			hql = hql + " and cname = :cname";
			hql.append(" and cname = :cname");
		}
		//编码
		if(params.containsKey("coding")){
//			hql = hql + " and coding = :coding";
			hql.append(" and coding = :coding");
		}
		//等级
		if(params.containsKey("deptlevel")){
//			hql = hql + " and deptlevel = :deptlevel";
			hql.append(" and deptlevel = :deptlevel");
		}
		//地区名称
		if(params.containsKey("vname")){
//			hql = hql + " and vname = :vname";
			hql.append(" and vname = :vname");
		}
		//拼音
		if(params.containsKey("pinyin")){
//			hql = hql + " and pinyin = :pinyin";
			hql.append(" and pinyin = :pinyin");
		}
		//排序
		if(params.containsKey("sortno")){
//			hql = hql + " and sortno = :sortno";
			hql.append(" and sortno = :sortno");
		}
		//状态
		if(params.containsKey("vtypes")){
//			hql = hql + " and vtypes = :vtypes";
			hql.append(" and vtypes = :vtypes");
		}
		//上级地区
		if(params.containsKey("parentid")){
//			hql = hql + " and parentid = :parentid";
			hql.append(" and parentid = :parentid");
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
	
	/**
    *
    * @param parentId
    * @return
    * @author chl
    * @date 2014-1-24 上午8:43:01
    */
   public List<Cityinfo> getCityByParent(Long parentId) {
       String hql = "from Cityinfo city  where city.parent.id=? and city.vtypes='0'  order by city.id";
       return this.find(hql,parentId);
   }
}
