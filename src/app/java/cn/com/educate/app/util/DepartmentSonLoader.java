package cn.com.educate.app.util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import cn.com.educate.app.dao.NamedJdbcDao;
import cn.com.educate.app.entity.security.SimpleDepartment;

import com.google.common.cache.CacheLoader;

/**
 * 
 * 
 * @author xjs
 * @date 2013-11-18 下午10:42:28
 */
@Component
public class DepartmentSonLoader extends CacheLoader<Long,List<SimpleDepartment>> {

    @Autowired
    NamedJdbcDao namedJdbcDao; 
    

    /**
     * 根据机构获得子机构，包括子机构的子机构
     * 
     * author: xjs 
     */
    @Override
    public List<SimpleDepartment> load(Long parentId) throws Exception {
        Map<String,Object> condition = new HashMap<String,Object>();
        //String sql = "SELECT ID as id ,ORGANI_CODE  as code ,RGANI_NAME AS name FROM BASE_ZZJG START WITH ID = :parentId CONNECT BY PRIOR ID = PARENT_ID " ;
        String sql = "SELECT id as ID,orgname as NAME FROM organizeinfo WHERE FIND_IN_SET(id,queryOrganizeinfoForChild(:parentId))";
        condition.put("parentId", parentId);
        return namedJdbcDao.getJdbcTemplate().query(sql, condition, ParameterizedBeanPropertyRowMapper.newInstance(SimpleDepartment.class));
    }
    
}
