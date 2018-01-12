package cn.com.educate.app.service.baseinfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.dao.JdbcDao;
import cn.com.educate.app.entity.login.Cityinfo;
import cn.com.educate.app.util.MapVsBean;

import com.google.common.cache.CacheLoader;


/**
 * 缓存类，键值为: ID ->下级城市，或地区名
 * @author xjs
 *
 */
@Component
public class AddressesLoader extends CacheLoader<Long,List<Cityinfo>> {

	@Autowired
	private JdbcDao jdbcDao;
	
	@Override
	public List<Cityinfo> load(Long parentId) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("sql", " a.parentid="+parentId + " order by id");
		List<Map<String, Object>> cityMap = jdbcDao.searchBySqlTemplate("queryCityinfoList", conditions);
		/*List<Cityinfo> cityList = new ArrayList<Cityinfo>();
		for(Map<String, Object> city:cityMap){
			cityList.add(getCityinfoFromMap(city));
		}*/
		return MapVsBean.mapsToObjects(cityMap,Cityinfo.class);
	}
	
	
}