package cn.com.educate.app.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import cn.com.educate.core.orm.JdbcPage;
import cn.com.educate.core.orm.jdbc.SqlManager;


@Component
public class JdbcDao {

	private static Logger logger = LoggerFactory.getLogger(JdbcDao.class);

	private SimpleJdbcTemplate jdbcTemplate;
	
	private SqlManager sqlManager;
	private ListMapper listMapper = new ListMapper();

	private class ListMapper implements RowMapper<Map<String, Object>> {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			HashMap<String, Object> result = new HashMap<String, Object>();
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (rs.getObject((metaData.getColumnName(i))) != null && "oracle.sql.CLOB".equals(rs.getObject((metaData.getColumnName(i))).getClass().getName())) {
					oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob(metaData.getColumnName(i));
					if (clob != null)
						result.put(metaData.getColumnName(i), clob.getSubString(1, (int) clob.length()));
				} else
					result.put(metaData.getColumnName(i), rs.getObject((metaData.getColumnName(i))));
			}
			return result;
		}
	}

	public SqlManager getSqlManager() {
		return sqlManager;
	}

	@Autowired
	public void setSqlManager(SqlManager sqlManager) {
		this.sqlManager = sqlManager;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> searchBySqlTemplate(String queryName, Map<String, ?> conditions) {
		try {
			String sql = sqlManager.getSqlByName(queryName, conditions);
			logger.debug(queryName +" sql: " + sql);
			logger.debug("sql map: " + conditions);
			return jdbcTemplate.query(sql, listMapper, conditions);
		} catch (Exception e) {
			logger.error("执行 SQL " + queryName +" 出错 ！！！！" + e.getMessage());
			return null;
		}

	}

	public void initPage(String queryName, Map<String, Object> conditions, JdbcPage page) {
		int total = page.getTotalCount();
		if (total == -1) {
			String sql;
			try {
				sql = sqlManager.getMergeSqlByName("cnt_" + queryName, conditions);
				total = this.jdbcTemplate.queryForInt(sql, conditions);
				logger.debug("Total Count: " + String.valueOf(total));
				page.setTotalCount(total);
			} catch (Exception e) {
				logger.error("执行 SQL " + queryName +" 出错 ！！！！" + e.getMessage());
			}
		}
	}

	public List<Map<String, Object>> searchByMergeSqlTemplate(String queryName,	Map<String, Object> conditions) {
		try {
			String sql = sqlManager.getMergeSqlByName(queryName, conditions);
			logger.debug(queryName +" sql: =" + sql);
			logger.debug("sql map: =" + conditions);
			return jdbcTemplate.query(sql, listMapper, conditions);
		} catch (Exception e) {
			logger.error("执行 SQL " + queryName +" 出错 ！！！！" + e.getMessage());
			return null;
		}

	}

	public List<Map<String, Object>> searchPagesByMergeSqlTemplate(String queryName, Map<String, Object> conditions, JdbcPage page) {
		try {
			if (page == null)
				return null;
			this.initPage(queryName, conditions, page);
			int page_end = page.getFirst() + page.getPageSize() - 1;//oracle使用参数
			conditions.put("start", page.getFirst());
			conditions.put("end", page_end);//oracle使用参数
			conditions.put("size", page.getPageSize());//mysql使用参数
			String sql = sqlManager.getMergeSqlByName("page_" + queryName, conditions);
			logger.debug(queryName +" sql: =" + sql);
			logger.debug("sql map: =" + conditions);
			return jdbcTemplate.query(sql, listMapper, conditions);
		} catch (Exception e) {
			logger.error("执行 SQL " + queryName +" 出错 ！！！！" + e.getMessage());
			return null;
		}

	}
	
	
	
	public List<Map<String, Object>> searchByMergeSqlTemplateAceess(String type,String queryName, Map<String, Object> conditions) {
		try {
			String sql = sqlManager.getMergeSqlByName(queryName, conditions);
			logger.debug(queryName +" sql: =" + sql);
			logger.debug("sql map: =" + conditions);
			//需要在次包一层权限
			return jdbcTemplate.query(sql, listMapper, conditions);
		} catch (Exception e) {
			logger.error("执行 SQL " + queryName +" 出错 ！！！！" + e.getMessage());
			return null;
		}

	}
	
	public List<Map<String, Object>> searchPagesByMergeSqlTemplateAceess(String type,String queryName, Map<String, Object> conditions, JdbcPage page) {
		try {
			if (page == null)
				return null;
			this.initPage(queryName, conditions, page);
			int page_end = page.getFirst() + page.getPageSize() - 1;
			conditions.put("start", page.getFirst());
			conditions.put("end", page_end);
			conditions.put("size", page.getPageSize());//mysql使用参数
			String sql = sqlManager.getMergeSqlByName("page_" + queryName, conditions);
			logger.debug(queryName +" sql: =" + sql);
			logger.debug("sql map: =" + conditions);
			//需要在次包一层权限
			return jdbcTemplate.query(sql, listMapper, conditions);
		} catch (Exception e) {
			logger.error("执行 SQL " + queryName +" 出错 ！！！！" + e.getMessage());
			return null;
		}

	}
	
	
	public void updateBySqlTemplate(String updateName, Map<String, ?> conditions) {
		try {
			String sql = sqlManager.getSqlByName(updateName, conditions);
			logger.debug(updateName +" sql: " + sql);
			logger.debug("sql map: " + conditions);
			jdbcTemplate.update(sql, conditions);
		} catch (Exception e) {
			logger.error("执行 SQL " + updateName +" 出错 ！！！！" + e.getMessage());
		}

	}
	
	public boolean insertBySqlTemplate(String insertName, Map<String, ?> conditions) {
		try {
			String sql = sqlManager.getSqlByName(insertName, conditions);
//			System.out.println(insertName +" sql: " + sql);
			logger.debug(insertName +" sql: " + sql);
			logger.debug("sql map: " + conditions);
			jdbcTemplate.update(sql, conditions);
			return true;
		} catch (Exception e) {
			logger.error("执行 SQL " + insertName +" 出错 ！！！！" + e.getMessage());
			return false;
		}

	}
	
	public List<Map<String, Object>> searchByMergeSql(String sql) {
		try {
			return jdbcTemplate.query(sql, listMapper, new HashMap<String, Object>());
		} catch (Exception e) {
			return null;
		}

	}
	
	public int searchByMergeSqlCount(String sql) {
		try {
			List<Map<String, Object>> res = jdbcTemplate.query(sql, listMapper, new HashMap<String, Object>());
			Map map = (Map)res.get(0);
			String count = map.get("COUNT").toString();
			return new Integer(count);
		} catch (Exception e) {
			return 0;
		}

	}
	
	/**
	 * 获取序列值
	 * @return
	 */
	public int getSequenceFro(String sql){
		return jdbcTemplate.queryForInt(sql);
	}
	/**
	 * 根据sql修改数据
	 */
	public void updateBySql(String sql){
		Map m = null;
		jdbcTemplate.update(sql, m);
	}
	
	/**
	 * 通过基本的sql查找
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public List<Map<String, Object>>  selectBySql(String sql,Object[] parameters){
		return jdbcTemplate.queryForList(sql, parameters);
	}
}
