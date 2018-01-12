package cn.com.educate.app.service.baseinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.dao.baseinfo.UpdatedMenuDao;
import cn.com.educate.app.entity.login.UpdatedMenu;


@Component
public class UpdatedMenuManager {
	
	@Autowired
	private UpdatedMenuDao updatedMenuDao;
	
	@Autowired
	private UpdatedTopMenuCache updatedTopMenuCache;
	
	@Autowired
	private UpdatedSecondMenuCache updatedSecondMenuCache;
	
	@Autowired
	private UpdatedThirdMenuCache updatedThirdMenuCache;
	
	
	/**
	 * 根据角色id 获取一级菜单
	 * @param roleId
	 * @return
	 */
	private List<UpdatedMenu> getTopMenuByRoleID(long roleId){
		return updatedTopMenuCache.getTopMenuByRoleID(roleId);
	}
	
	/**
	 * 根据角色id 获取二级菜单
	 * @param roleId
	 * @return
	 */
	private List<UpdatedMenu> getSecondMenuByRoleID(long roleId,long parentId){
		return updatedSecondMenuCache.getMenuByRoleID(roleId, parentId); 
//		return updatedSecondMenuCache.getSecondMenuByRoleID(roleId, parentId); 
	}
	
	/**
	 * 根据角色id 获取三级菜单
	 * @param roleId
	 * @param firstLevelId
	 * @return
	 */
	private List<UpdatedMenu> getThirdMenuByRoleID(long roleId,long parentId){
		return updatedThirdMenuCache.getMenuByRoleID( roleId, parentId);
	}
	
	/**
	 * select ACCT_USER_ROLE.Role_Id from ACCT_USER_ROLE left join ACCT_USER on ACCT_USER.id = ACCT_USER_ROLE.User_Id  where ACCT_USER.id = 7848

	 */
	private List<Map<String,Object>> getRoleIdsByUserId(long userId){
		return updatedMenuDao.getRoleIdsByUserId(userId);
	}
	
	public List<UpdatedMenu> getTopMenuByUserId(long userId){
		Set<UpdatedMenu> menus = new HashSet<UpdatedMenu>();
		List<Map<String,Object>> roleIds = getRoleIdsByUserId(userId);
		for(Map<String,Object> roleIdOb:roleIds){
			int roleId = Integer.parseInt(roleIdOb.get("ROLEID").toString());
			List<UpdatedMenu> roleMenu =  getTopMenuByRoleID(roleId);
			menus.addAll(roleMenu);
		}		
		return setToListMenu(menus);
	}
	
	public List<UpdatedMenu> getSecondMenu(long userId,long parentId){
		Set<UpdatedMenu> menus = new HashSet<UpdatedMenu>();
		List<Map<String,Object>> roleIds = getRoleIdsByUserId(userId);
		for(Map<String,Object> roleIdOb:roleIds){
			int roleId = Integer.parseInt(roleIdOb.get("ROLEID").toString());
			List<UpdatedMenu> roleMenu =  getSecondMenuByRoleID(roleId,parentId);
			menus.addAll(roleMenu);
		}
		Iterator<UpdatedMenu> it = menus.iterator();
		while(it.hasNext()){
			UpdatedMenu  menu = it.next();
			Set<UpdatedMenu> submenu = new HashSet<UpdatedMenu>();
			for(Map<String,Object> roleIdOb:roleIds){
				int roleId = Integer.parseInt(roleIdOb.get("ROLEID").toString());
				List<UpdatedMenu> roleMenu =  getThirdMenuByRoleID(roleId,menu.getId());
				submenu.addAll(roleMenu);
			}
			menu.setSubMenu(setToListMenu(submenu));
		}

		return setToListMenu(menus);
	}
	
	public void invalidateAll(){
		updatedTopMenuCache.invalidateAll();
		updatedSecondMenuCache.invalidateAll();
		updatedThirdMenuCache.invalidateAll();
		
	}
	
	private class  UpdatedMenuComparator implements Comparator<UpdatedMenu> {

		@Override
		public int compare(UpdatedMenu first, UpdatedMenu second) {
			 return first.getSortNo() - second.getSortNo();
		}
	   
	}
	
	private List<UpdatedMenu> setToListMenu(Set<UpdatedMenu> menus){
		List<UpdatedMenu> listMenus = new ArrayList<UpdatedMenu>();
		listMenus.addAll(menus);
		Collections.sort(listMenus, new UpdatedMenuComparator());
		return listMenus;
	}
	
}
