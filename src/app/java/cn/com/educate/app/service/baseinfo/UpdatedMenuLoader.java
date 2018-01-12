package cn.com.educate.app.service.baseinfo;

import java.util.List;

import cn.com.educate.app.dao.baseinfo.UpdatedMenuDao;
import cn.com.educate.app.entity.login.UpdatedMenu;

import com.google.common.cache.CacheLoader;



public abstract class UpdatedMenuLoader extends CacheLoader<String,List<UpdatedMenu>> {
	
	protected UpdatedMenuDao updatedMenuDao; 
	
	public UpdatedMenuLoader(UpdatedMenuDao updatedMenuDao){
		this.updatedMenuDao = updatedMenuDao;
	}
	
	@Override
	public List<UpdatedMenu> load(String roleIdAndParentId) throws Exception {
		String[] values = roleIdAndParentId.split(":");
		long roleId = Long.parseLong(values[0]);
		long parentId = Long.parseLong(values[1]);
		return getUpdatedMenu(roleId,parentId);
		//return updatedMenuDao.getThirdMenuByRoleID(roleId, parentId);
	}
	
	public abstract List<UpdatedMenu> getUpdatedMenu(long roleId,long parentId);

}