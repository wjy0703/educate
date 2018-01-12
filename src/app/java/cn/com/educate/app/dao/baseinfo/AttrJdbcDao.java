package cn.com.educate.app.dao.baseinfo;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;


/**
 * 和下拉菜单项相关的类，涉及到的表包括Base_attr和 BASE_attr_value
 * 
 * @author xjs
 * 
 */
@Component
public class AttrJdbcDao {

	private static Logger logger = LoggerFactory.getLogger(AttrJdbcDao.class);

	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;

	/**
	 * 根据coding的值取得相应的下拉数据项
	 * 
	 * @param coding
	 * @return
	 */
	public List<Map<String, Object>> getAttrs(String coding) {
		//String sql = "SELECT ATTR.VALUE,ATTR.DESCRIPTION from BASE_ATTR ATTR , BASE_ATTR_TYPE ATTRTYPE where ATTR.TYPE_ID = ATTRTYPE.ID AND ATTRTYPE.CODING = ? ORDER BY ATTR.SORT_NO";
		//String sql = "SELECT ATTR.VALUE AS VALUE,ATTR.NAME AS DESCRIPTION  FROM XH_MATE_DATA ATTR , XH_MATEDATA_TYPE ATTRTYPE where ATTR.MATEDATATYPE_ID = ATTRTYPE.ID AND ATTRTYPE.TYPE_CODE = ? ";
		String sql = "select a.matename DESCRIPTION,a.vvalue VALUE from matedata a,matedatatype b where a.matetypeid=b.id and a.vtypes='0' and b.typecode=?";
		return this.jdbcTemplate.queryForList(sql, coding);
	}

	
}
