package cn.com.educate.app.service.baseinfo;
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
public class SingleAddressLoader extends CacheLoader<Long,String> {

	@Autowired
	private JdbcDao jdbcDao;
	
	@Override
	public String load(Long id) throws Exception {
//		 City city = cityDao.get(id);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("sql", " a.id="+id);
		List<Map<String, Object>> cityMap = jdbcDao.searchBySqlTemplate("queryCityinfoList", conditions);
			
		Cityinfo city =MapVsBean.mapToBean(cityMap.get(0), new Cityinfo()); 
		
		return city!= null ? city.getVname() : "";
	}

}