package cn.com.educate.app.service.baseinfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.hibernate.tool.hbm2x.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.util.MetaDataTypeEnum;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

@Component("attrCacheManager")
public class AttrCacheManager {
    
//	@Autowired
//	EnumsLoader enumsLoader;
	
	@Autowired
	AttrsLoader attrsLoader;
	
//	private  LoadingCache<String,Map<String,Object>> enumCache = null;
	private  LoadingCache<String,List<Map<String, Object>>> attrsCache = null;

	/**
	 * 根据value值取得相应的数据 例如根据状体值30得到相应的状态信息,此方法定义的不好
	 * 
	 * @param value
	 * @return
	 */
	/*public Map getAttrByValue(String value) {
		initialCacheIfNull();
		try {
			return (Map)enumCache.get(value);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}	
		return null;
	}
	*/
	/**
	 * 根据编码返回对应的下拉列表
	 * @param coding
	 * @return
	 */
	public List<Map<String, Object>> getAttrByCoding(String coding) {
		initialCacheIfNull();
		try {
			return attrsCache.get(coding);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	/**
     * 根据编码返回对应的下拉列表
     * @param coding
     * @return
     */
    public List<Map<String, Object>> getAttrByCoding(MetaDataTypeEnum coding) {
        return getAttrByCoding(coding.toString());
    }
	
	/**
	 * 
	 * @param coding
	 * @param value
	 * @return
	 */
	public String getAttrName(MetaDataTypeEnum coding,String value){
		return getAttrName(coding.toString(),value);
	}
	/**
     * 
     * @param coding
     * @param value
     * @return
     */
    public String getAttrName(String coding,String value){
        List<Map<String, Object>> values = getAttrByCoding(coding);
        for(Map<String, Object> item:values){
            if(StringUtils.equals(value, item.get("VALUE").toString())){
                return item.get("DESCRIPTION").toString();
            }
        }
        return "";
    }
	
	
	private void initialCacheIfNull() {
//		if(enumCache == null){
//			enumCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.MINUTES).build(enumsLoader);
//		}
		if(attrsCache == null){
			attrsCache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.MINUTES).build(attrsLoader);
		}
	}
	
	 /**
     * 清楚缓存
     */
	public void invalidateAll() {
		initialCacheIfNull();
//		enumCache.invalidateAll();
		attrsCache.invalidateAll();
	}
	
	
}
