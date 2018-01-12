package cn.com.educate.app.service.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.educate.app.dao.NamedJdbcDao;
import cn.com.educate.app.dao.login.OperatelogDao;
import cn.com.educate.app.entity.login.Operatelog;
import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.Page;

//Spring Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class OperatelogManager {
    
    private static Logger logger = LoggerFactory.getLogger(OperatelogManager.class);
    
    public static NamedJdbcDao staticDao ; 
    
    private NamedJdbcDao namedJdbcDao;
    
    public static OperatelogDao staticOperLogDao;
    
    private OperatelogDao operatelogDao;

    @Autowired
    public void setXhOperateLogDao(OperatelogDao operatelogDao) {
        this.operatelogDao = operatelogDao;
        staticOperLogDao = this.operatelogDao;
    }

    @Autowired
    public void setNamedJdbcDao(NamedJdbcDao namedJdbcDao) {
        this.namedJdbcDao = namedJdbcDao;
        staticDao = this.namedJdbcDao ;
    }

    @Transactional(readOnly = true)
    public Page<Operatelog> searchOperatelog(final Page<Operatelog> page, final Map<String, Object> filters) {
    	return operatelogDao.queryOperatelog(page, filters);
    }

    
   
    @Transactional(readOnly = true)
    public Operatelog getOperatelog(Long id) {
        return operatelogDao.get(id);
    }

    public void saveOperatelog(Operatelog entity) {
    	operatelogDao.save(entity);
    }

    /**
     * 
     * @param queryName
     * @param page
     * @param map
     * @return
     * @author Administrator
     * @date 2014-4-8 下午1:30:12
     */
    public List<Map<String, Object>> listLogInfo(Map<String, Object> filter, JdbcPage page) {
        return namedJdbcDao.searchPagesByMergeSqlTemplate("queryOperateLog", conditions(filter), page);
    }

    public Map<String, Object> conditions(Map<String, Object> filter) {
        Map<String, Object> conditions = new HashMap<String, Object>();
        String sql = "";
        String value = "";
        // 主键
        if (filter.containsKey("id")) {
            value = String.valueOf(filter.get("id"));
            if (StringUtils.isNotEmpty(value)) {
                sql = sql + " and ID = '" + value + "'";
            }
        }
        // ip地址
        if (filter.containsKey("ip")) {
            value = String.valueOf(filter.get("ip"));
            if (StringUtils.isNotEmpty(value)) {
                sql = sql + " and IP = '" + value + "'";
            }
        }
        // 登录人
        if (filter.containsKey("createBy")) {
            value = String.valueOf(filter.get("createBy"));
            if (StringUtils.isNotEmpty(value)) {
                sql = sql + " and CREATE_BY LIKE '%" + value + "%'";
            }
        }
        if(filter.containsKey("startDate")){
            value = String.valueOf(filter.get("startDate"));
            if(StringUtils.isNotEmpty(value)) {
                sql = sql + " and CREATE_TIME >= to_date('"+value+"','yyyy-MM-dd')";
            }
        }
        if(filter.containsKey("endDate")){
            value = String.valueOf(filter.get("endDate"));
            if(StringUtils.isNotEmpty(value)) {
                sql = sql + " and CREATE_TIME  <=to_date('"+value+"','yyyy-MM-dd')";
            }
        }

        System.out.println("sql=======>" + sql);
        conditions.put("sql", sql);
        return conditions;
    }

    /**
     * 
     * 
     * @author chl 添加操作日志
     * @date 2014-4-8 下午3:50:27
     */
    public Long addLogInfo(String name,String logInfo, String ip) {
        logger.info("添加日志数据");
        Operatelog xhOperateLog = new Operatelog();
        xhOperateLog.setRemarks(logInfo);
        xhOperateLog.setIp(ip);
        xhOperateLog.setCreateuser(name);
        this.saveLogInfo(xhOperateLog);
        return xhOperateLog.getId();
    }

    public void saveLogInfo(Operatelog operatelog) {
        logger.info("保存日志数据");
        //operatelogDao.save(operatelog);
        namedJdbcDao.insertBySqlTemplate("insertOperatelog", fromOperatelogEntity(operatelog));
        logger.info("保存日志数据-成功");
    }
    public void saveLogInfo(Map<String, Object> conditions) {
        logger.info("保存日志数据");
        //operatelogDao.save(operatelog);
        namedJdbcDao.insertBySqlTemplate("insertOperatelog", conditions);
        logger.info("保存日志数据-成功");
    }
    private Map<String, Object> fromOperatelogEntity(Operatelog operatelog){
    	Map<String, Object> conditions = new HashMap<String, Object>();
    	conditions.put("id", operatelog.getId());
    	conditions.put("createtime", operatelog.getCreatetime());
    	conditions.put("modifytime", operatelog.getModifytime());
    	conditions.put("createuser", operatelog.getCreateuser());
    	conditions.put("modifyuser", operatelog.getModifyuser());
    	conditions.put("ip", operatelog.getIp());
    	conditions.put("remarks", operatelog.getRemarks());
    	return conditions;
    }
    
    @Transactional(readOnly = true)
    public List<Map<String,Object>> searchCjrxx(String queryName,Map<String,Object> filter){
        Map<String, Object> conditions = new HashMap<String,Object>();
        String sql="";
        if(filter.containsKey("logName")){
            String value = String.valueOf(filter.get("logName"));
            if(StringUtils.isNotEmpty(value)) {
                sql = sql + " t.createuser like '%" +  value + "%'";
            }
        }
        conditions.put("sql", sql);
        return  namedJdbcDao.searchByMergeSqlTemplate(queryName, conditions);
    }
}