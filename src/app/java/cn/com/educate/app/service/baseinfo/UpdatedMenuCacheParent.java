package cn.com.educate.app.service.baseinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.com.educate.app.entity.login.UpdatedMenu;

import com.google.common.cache.LoadingCache;


public abstract class UpdatedMenuCacheParent {

	protected  LoadingCache<String,List<UpdatedMenu>> menuCache = null;
	

	public List<UpdatedMenu> getMenuByRoleID(long roleId, long parentId) {
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
	
	
	
	protected abstract void initialCacheIfNull();
	/*{
		if(menuCache == null){
			menuCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.MINUTES).build(new UpdatedMenuLoader());
		}
	}*/
	
	
}
