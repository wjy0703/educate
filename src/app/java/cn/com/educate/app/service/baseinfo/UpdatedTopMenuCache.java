package cn.com.educate.app.service.baseinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.dao.baseinfo.UpdatedMenuDao;
import cn.com.educate.app.entity.login.UpdatedMenu;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Component
public class UpdatedTopMenuCache {
	
	@Autowired
	UpdatedMenuDao updatedMenuDao; 
	
	private  LoadingCache<Long,List<UpdatedMenu>> menuCache = null;
	


	public List<UpdatedMenu> getTopMenuByRoleID(long roleId) {
		initialCacheIfNull();
		try {
			return menuCache.get(roleId);
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
    
	private class UpdatedMenuLoader extends CacheLoader<Long,List<UpdatedMenu>> {
		@Override
		public List<UpdatedMenu> load(Long roleId) throws Exception {
			return updatedMenuDao.getTopMenuByRoleID(roleId);
		}

	}
	
	private void initialCacheIfNull() {
		if(menuCache == null){
			menuCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.MINUTES).build(new UpdatedMenuLoader());
		}
	}
}
