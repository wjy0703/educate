package cn.com.educate.app.service.baseinfo;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.dao.baseinfo.AttrJdbcDao;

import com.google.common.cache.CacheLoader;


@Component("attrsLoader")
public class AttrsLoader extends CacheLoader<String,List<Map<String, Object>>> {

	@Autowired
	AttrJdbcDao attrJdbcDao; 
	
	@Override
	public List<Map<String, Object>> load(String coding) throws Exception {
		return attrJdbcDao.getAttrs(coding);
	}

}
