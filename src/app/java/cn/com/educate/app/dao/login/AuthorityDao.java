package cn.com.educate.app.dao.login;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.security.Authority;
import cn.com.educate.app.util.PropertiesUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.orm.hibernate.HibernateDao;

@Component
public class AuthorityDao extends HibernateDao<Authority, Long>{
	public List<Authority> queryAuthorities(){
		String hql = "from Authority authority where authority.vtype=? order by authority.id asc";
		return this.find(hql, "0");
	}
	
	public List<Authority> queryAuthorities(Map<String,Object> params){
//        if (params == null ) {
//            return this.queryAuthorities();
//        }
        String hql = "from Authority authority where 1=1 and authority.vtype = '0' ";
//        PropertiesUtils.putBusidCheck(params);
        if (params.containsKey("vsystype")) {
            hql = hql + " and authority.vsystype = :vsystype ";
        }else{
        	 PropertiesUtils.putBusidCheck(params);
        	 if (params.containsKey("vsystype")) {
                 hql = hql + " and authority.vsystype = :vsystype ";
             }
        }
        hql = hql + " order by authority.id asc ";
        return this.find(hql, params);
    }
	
	public Page<Authority> queryAuthority(Page<Authority> page, Map<String, Object> params) {
		String hql = "from Authority authority where 1=1";
		
		if(params.containsKey("name")){
			hql = hql + " and aname like '%'||:name||'%'";
		}

		if(params.containsKey("cname")){
			hql = hql + " and cname like '%'||:cname||'%'";
		}
		
		if(params.containsKey("path")){
			hql = hql + " and vpath like '%'||:path||'%'";
		}

		if(params.containsKey("vtype")){
			hql = hql + " and vtype = :vtype";
		}
		
		if (page.getOrderBy()!=null){
			hql = hql + " order by " + page.getOrderBy() + " " + page.getOrder();
		}
		return this.findPage(page, hql, params);
	}
	
}
