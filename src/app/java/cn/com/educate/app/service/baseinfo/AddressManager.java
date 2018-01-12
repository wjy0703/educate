package cn.com.educate.app.service.baseinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.entity.login.Cityinfo;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;


@Component
public class AddressManager {
	private static Logger logger = LoggerFactory.getLogger(AddressManager.class);
    @Autowired
    AddressesLoader addressesLoader;
    
    @Autowired
    SingleAddressLoader singleAddressesLoader;
    
    @Autowired
    AttrCacheManager attrCacheManager;
    
	private  LoadingCache<Long,List<Cityinfo>> cache = null;
    
	
	private  LoadingCache<Long,String> singleCache = null;
	
	private JdbcDao jdbcDao;
	
	@Autowired
	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
    /**
     * 通过父ID查询
     * @param parentId
     * @return
     */
	public List<Cityinfo> getSonsByParentId(long parentId) {
		initialCacheIfNull();
		try {
				return cache.get(parentId);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}	
		return new ArrayList<Cityinfo>();
	}
	
	/**
	 * 通过ID查询
	 * @param id
	 * @return
	 * @throws ExecutionException 
	 */
	public String getCityNameById(Long id) {
		initialCacheIfNull();
		try {
			return singleCache.get(id);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "未知城市";
	}
	
	/**
	 * 同级别的城市
	 * @param id
	 * @return
	 */
	public List<Cityinfo> getSameLevelCities(long id){
		//
//		Cityinfo city = cityDao.get(id);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("sql", " a.id="+id);
		List<Map<String, Object>> cityMap = jdbcDao.searchBySqlTemplate("queryCityinfoList", conditions);
		
		return getSonsByParentId(Long.parseLong(cityMap.get(0).get("parentid")+""));
	}
	
	private void initialCacheIfNull() {
		if(cache == null){
			cache = CacheBuilder.newBuilder().maximumSize(200).expireAfterAccess(60, TimeUnit.MINUTES).build(addressesLoader);
		}
		if(singleCache == null){
			singleCache = CacheBuilder.newBuilder().maximumSize(200).expireAfterAccess(60, TimeUnit.MINUTES).build(singleAddressesLoader);
		}
	}
    /**
     * 清楚缓存
     */
	public void invalidateAll() {
		initialCacheIfNull();
		cache.invalidateAll();
		singleCache.invalidateAll();
	}
	
}
