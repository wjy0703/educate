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
public class DepartmentLoader extends CacheLoader<String,List<SimpleDepartment>> {

	@Autowired
	NamedJdbcDao namedJdbcDao; 
	/**
	 * 根据key取得相应的门店  key取值为mendian 或 cfmd
	 */
	@Override
	public List<SimpleDepartment> load(String key) throws Exception {
	    Map<String,String> condition = new HashMap<String,String>();
//        String sql = "SELECT ID as id,ORGANI_CODE AS code,RGANI_NAME AS name FROM BASE_ZZJG WHERE LEVEL_MESS = :type OR RGANI_NAME='信和总部' ORDER BY PARENT_ID,ORGANI_CODE  " ;
	    String sql = "SELECT id as ID, orgname AS NAME FROM organizeinfo WHERE orgflag = :type OR orgname='组织机构' ORDER BY PARENTID";
        condition.put("type", key);
        return namedJdbcDao.getJdbcTemplate().query(sql, condition, ParameterizedBeanPropertyRowMapper.newInstance(SimpleDepartment.class));
	}
	
}
