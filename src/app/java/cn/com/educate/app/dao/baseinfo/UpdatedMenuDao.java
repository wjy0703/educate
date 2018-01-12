package cn.com.educate.app.dao.baseinfo;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.login.UpdatedMenu;


/**
 * 和菜单相关的操作
 * 
 * @author xjs
 * 
 */
@Component
public class UpdatedMenuDao {

	private static Logger logger = LoggerFactory
			.getLogger(UpdatedMenuDao.class);

	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
    
	private String sqlMain="SELECT MENU.id AS id,MENU.fresh AS fresh, MENU.external AS external,MENU.levelId AS levelId,MENU.menuName AS menuName,MENU.menuType AS menuType,MENU.menuUrl AS menuUrl,MENU.REL AS rel,MENU.sortNo AS sortNo,MENU.target AS target,MENU.title AS title FROM menutable MENU,rolemenu MR WHERE MENU.id = MR.menuid ";
	
	private String sqlTop= sqlMain + " AND MR.roleid = ? AND MENU.levelid = 1 AND MENU.vsts = '0' ORDER BY MENU.sortno";
	
	private String sqlNext=sqlMain + " AND MR.roleid = ? AND MENU.levelid = ? and MENU.parentid=? AND MENU.vsts = '0' ORDER BY MENU.sortno";
	/**
	 * 根据角色id 获取一级菜单
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	public List<UpdatedMenu> getTopMenuByRoleID(long roleId) {
//		String sql = "SELECT MENU.ID as id , MENU.FRESH as fresh , MENU.EXTERNAL as external, MENU.LEVEL_ID as levelId , MENU.MENU_NAME as menuName , MENU.MENU_TYPE as menuType ,MENU.MENU_URL as menuUrl ,MENU.REL as rel , MENU.SORT_NO as sortNo , MENU.TARGET as target , MENU.TITLE as title FROM BASE_MENU MENU , BASE_MENU_ROLE MR  where MENU.ID = MR.MENU_ID AND MR.ROLE_ID = ? AND MENU.LEVEL_ID = 1 AND MENU.STS = '0' ORDER BY MENU.SORT_NO ";
		return this.jdbcTemplate.query(sqlTop, ParameterizedBeanPropertyRowMapper
				.newInstance(UpdatedMenu.class), roleId);
	}

	/**
	 * 根据角色id 获取二级菜单
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	public List<UpdatedMenu> getSecondMenuByRoleID(long roleId, long parentId) {
//		String sql = "SELECT MENU.ID as id , MENU.FRESH as fresh ,  MENU.EXTERNAL as external, MENU.LEVEL_ID as levelId , MENU.MENU_NAME as menuName , MENU.MENU_TYPE as menuType ,MENU.MENU_URL as menuUrl ,MENU.REL as rel , MENU.SORT_NO as sortNo , MENU.TARGET as target , MENU.TITLE as title FROM BASE_MENU MENU , BASE_MENU_ROLE MR  where MENU.ID = MR.MENU_ID AND MR.ROLE_ID = ? AND MENU.LEVEL_ID = 2 AND MENU.HEIGER_MENU = ? AND MENU.STS = '0' ORDER BY MENU.SORT_NO";
		return this.jdbcTemplate.query(sqlNext, ParameterizedBeanPropertyRowMapper
				.newInstance(UpdatedMenu.class),
				new Object[] { roleId,2, parentId });
	}

	/**
	 * 根据角色id 获取三级菜单
	 * 
	 * @param roleId
	 * @param firstLevelId
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	public List<UpdatedMenu> getThirdMenuByRoleID(long roleId, long parentId) {
//		String sql = "SELECT MENU.ID as id , MENU.FRESH as fresh , MENU.EXTERNAL as external,  MENU.LEVEL_ID as levelId , MENU.MENU_NAME as menuName , MENU.MENU_TYPE as menuType ,MENU.MENU_URL as menuUrl ,MENU.REL as rel , MENU.SORT_NO as sortNo , MENU.TARGET as target , MENU.TITLE as title FROM BASE_MENU MENU , BASE_MENU_ROLE MR  where MENU.ID = MR.MENU_ID AND MR.ROLE_ID = ? AND MENU.LEVEL_ID = 3 AND MENU.HEIGER_MENU = ? AND MENU.STS = '0' ORDER BY MENU.SORT_NO";
		return this.jdbcTemplate.query(sqlNext, ParameterizedBeanPropertyRowMapper
				.newInstance(UpdatedMenu.class),
				new Object[] { roleId,3, parentId });
	}

	/**
	 * select ACCT_USER_ROLE.Role_Id from ACCT_USER_ROLE left join ACCT_USER on
	 * ACCT_USER.id = ACCT_USER_ROLE.User_Id where ACCT_USER.id = 7848
	 */
	public List<Map<String, Object>> getRoleIdsByUserId(long userId) {
		String sql = " select a.rolesid as ROLEID from userinfo a where a.id = ?";
		return jdbcTemplate.queryForList(sql, userId);
	}
}
