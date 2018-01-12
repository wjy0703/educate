package cn.com.educate.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.NamedJdbcDao;
import cn.com.educate.app.entity.security.SimpleDepartment;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;


/**
 * 和门店相关的类，根据子机构获得门店名称
 *
 * @author xjs
 * @date 2013-11-27 上午8:55:41
 */
//Spring Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class DepartmentManager {
 
    private static Logger logger = LoggerFactory.getLogger(DepartmentManager.class);
    
    @Autowired
    DepartmentLoader departmentLoader;

    @Autowired
    DepartmentSonLoader departmentSonLoader;

    @Autowired
    DepartmentNameLoader departmentNameLoader;
    
    @Autowired
    private  NamedJdbcDao namedJdbcDao;
    private  LoadingCache<String,List<SimpleDepartment>> departmentCache = null;
    private  LoadingCache<Long,List<SimpleDepartment>> departmentSonCache = null;
    private  LoadingCache<Long,String> departmentNameCache = null;
    
    public enum TYPE{LEND, BORROW}; //
    /**
     * 根据类型获得相应的门店  
     *
     * @param type TYPE.LEND，代表出借  TYPE.BORROW 代表借款端
     * @return
     * @author xjs
     * @date 2013-11-26 下午6:37:26
     */
    public List<SimpleDepartment> getDepartmentByType(TYPE type) {
        initialCacheIfNull();
        if (type == TYPE.BORROW) {
            try {
                return departmentCache.get("mendian");
            } catch (ExecutionException e) {
                if(logger.isErrorEnabled()){
                    logger.error("获取借款端门店时出现错误");
                }
            }
        } else {
            try {
                return departmentCache.get("cfmd");
            } catch (ExecutionException e) {
                if(logger.isErrorEnabled()){
                    logger.error("获取出借端门店时出现错误");
                }
            }
        }
        return null;
    }
    
    /**
     * 根据门店Id获取子机构ids集合，支持多门店ID，以,分隔
     *
     * @param parentId ','分割
     * @return 返回值类似 12,13
     * @author xjs
     * @date 2013-11-27 上午8:58:55
     */
    public String getDepartmentIds(String parentId){
        StringBuffer ids = new StringBuffer();
        List<SimpleDepartment> departments ;
        try {
        	String [] parentIds = parentId.split(",");
        	for(String s:parentIds){
	            departments = getDepartmentByParent(Long.parseLong(s));
	            for(SimpleDepartment dept : departments){
	                ids.append(dept.getId() + ",");
	            }
        	}
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.error("获取子门店时出现错误" + parentId);
            }
        }
        return ids.substring(0,ids.length()-1);
    }
    
    /**
     * 获得父机构下面的所有的组织机构
     *
     * @param id
     * @return
     * @author xjs
     * @date 2013-11-26 下午6:38:15
     */
    public List<SimpleDepartment> getDepartmentByParent(final Long id) {
        initialCacheIfNull();
        try {
            return departmentSonCache.get(id);
        } catch (ExecutionException e) {
            if(logger.isErrorEnabled()){
                logger.error("通过键值取得门店名字是出现错误： 子机构ID是： " + id);
            }
        }
       
        List<SimpleDepartment> ownList =  new ArrayList<SimpleDepartment>();
        SimpleDepartment sd = new SimpleDepartment();
        sd.setId(id);
        ownList.add(sd);
        return ownList;
    }
    
    /**
     * 根据id得到门店名称，注意是门店名称,如果是门店上级ID返回空字符串
     *
     * @param id
     * @return
     * @author xjs
     * @date 2013-11-26 下午8:03:48
     */
    public String getDepartmentName(final Long id) {
        initialCacheIfNull();
      //  invalidateAll();
        try {
            return departmentNameCache.get(id);
        } catch (ExecutionException e) {
            if(logger.isErrorEnabled()){
               logger.error("通过键值取得门店名字是出现错误： 子机构ID是： " + id);
            }
        }
        return "";
    }
    
    /**
     * 初始化缓存对象
     *
     * @author xjs
     * @date 2013-11-27 上午8:55:24
     */
    private void initialCacheIfNull() {
        if(departmentCache == null){
            departmentCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(60, TimeUnit.MINUTES).build(departmentLoader);
        }
        if(departmentSonCache == null){
            departmentSonCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(60, TimeUnit.MINUTES).build(departmentSonLoader);
        }
        if(departmentNameCache == null){
            departmentNameCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterAccess(60, TimeUnit.MINUTES).build(departmentNameLoader);
        }
    }
    
     /**
     * 清楚缓存
     */
    public void invalidateAll() {
        initialCacheIfNull();
        departmentCache.invalidateAll();
        departmentSonCache.invalidateAll();
        departmentNameCache.invalidateAll();
    }
    
    /**
     * 替代从缓存中取机构数据，因为追加了查询条件搜索
     */
    @Transactional(readOnly=true)
    public List<SimpleDepartment> searchDepartment(TYPE type,String rganiName) {
        Map<String,String> condition = new HashMap<String,String>();
        String sql = "SELECT T.id as ID, T.orgname as NAME," +
        		     "       F.orgname as PARENTNAME " +
        		     "  FROM organizeinfo T LEFT JOIN organizeinfo F ON (T.parentid = f.id) " +
        		     " WHERE (t.orgname='组织机构' or  T.orgflag = :type ) and t.vtypes='0' ";
        if (type == TYPE.BORROW) {
            condition.put("type", "mendian");
        } else {
            condition.put("type", "hangye");
        }
        
        if (StringUtils.isNotBlank(rganiName)) {
            sql += " AND T.RGANI_NAME LIKE :rganiName ";
            condition.put("rganiName", rganiName + "%");
        }
        
        sql += " ORDER BY T.parentid";
        return namedJdbcDao.getJdbcTemplate().query(sql, condition, ParameterizedBeanPropertyRowMapper.newInstance(SimpleDepartment.class));
    }
}
