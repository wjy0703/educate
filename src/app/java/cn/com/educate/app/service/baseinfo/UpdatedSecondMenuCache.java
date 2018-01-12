package cn.com.educate.app.service.baseinfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.dao.baseinfo.UpdatedMenuDao;
import cn.com.educate.app.entity.login.UpdatedMenu;

import com.google.common.cache.CacheBuilder;

@Component
public class UpdatedSecondMenuCache extends UpdatedMenuCacheParent {

	@Autowired
	UpdatedMenuDao updatedMenuDao; 
	
	@Override
	protected void initialCacheIfNull() {
		if(menuCache == null){
			menuCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.MINUTES).build(new UpdatedSecondMenuLoader(updatedMenuDao));
		}
	}
    
	private class UpdatedSecondMenuLoader extends UpdatedMenuLoader {
		
	    public UpdatedSecondMenuLoader(UpdatedMenuDao updatedMenuDao){
	    	super(updatedMenuDao);
	    }
	    
		@Override
		public List<UpdatedMenu> getUpdatedMenu(long roleId, long parentId) {
			return updatedMenuDao.getSecondMenuByRoleID(roleId, parentId);
		}

	}
	
	/*
    
	@Autowired
	UpdatedMenuDao updatedMenuDao; 
	
	private  LoadingCache<String,List<UpdatedMenu>> menuCache = null;
	

	public List<UpdatedMenu> getSecondMenuByRoleID(long roleId, long parentId) {
		initialCacheIfNull();
		String roleIdAndParentId = roleId + ":" + parentId;
		try {
			return menuCache.get(roleIdAndParentId);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<UpdatedMenu> (); 
	}
		
    
	public void invalidateAll(){
		initialCacheIfNull();
		menuCache.invalidateAll();
	}
	
	private class UpdatedMenuLoader extends CacheLoader<String,List<UpdatedMenu>> {
		@Override
		public List<UpdatedMenu> load(String roleIdAndParentId) throws Exception {
			String[] values = roleIdAndParentId.split(":");
			long roleId = Long.parseLong(values[0]);
			long parentId = Long.parseLong(values[1]);
			return updatedMenuDao.getSecondMenuByRoleID(roleId, parentId);
		}

	}
	
	private void initialCacheIfNull() {
		if(menuCache == null){
			menuCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.MINUTES).build(new UpdatedMenuLoader());
		}
	}
	*/
	
}
