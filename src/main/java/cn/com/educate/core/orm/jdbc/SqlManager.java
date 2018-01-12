package cn.com.educate.core.orm.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import cn.com.educate.extension.binder.JaxbBinder;
import cn.com.educate.extension.binder.JaxbBinder.CollectionWrapper;

public class SqlManager implements InitializingBean  {
	
	private Resource[] locations;
	private HashMap<String, String> map;       //所有 SQL的map
	private HashMap<String, String> common_map;//通用SQL的 map
	
	public Resource[] getLocations() {
		return locations;
	}
	
	public SqlManager(){
		map = new HashMap<String, String>();
		common_map = new HashMap<String, String>();
	}
	
	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public void afterPropertiesSet() throws Exception {
		JaxbBinder binder = new JaxbBinder(SqlMaps.class, CollectionWrapper.class);
		for (Resource resource : this.locations) {
			SqlMaps sqlmaps = binder.fromXml(resource.getURL().openStream());			
			
			for(CustomSql sql: sqlmaps.getSqlmaps()){
				map.put(sql.getName(), sql.getSqlString());	
				if(sql.getCommon() != null && 
						sql.getCommon().toLowerCase().equals("true")){
					String sqlName = "ibpm_" + sql.getName();
					common_map.put(sqlName, sql.getSqlString());
				}
			}
			
		}
	}
	
	public String getSqlByName(String sqlName, Map<String,?> conditions) throws Exception{
		if(map.containsKey(sqlName)){
			return SqlBuilder.getSql(map.get(sqlName), conditions);
		}
		else
			throw new Exception("no such sql for sqlname :" + sqlName);			
	}

	public String getMergeSqlByName(String sqlName, Map<String, Object> conditions) throws Exception{
		if(map.containsKey(sqlName)){
			//conditions.putAll(common_map);
			conditions.putAll(common_map);
			/*for(String sqlKey: common_map.keySet()){
				conditions.put(sqlKey, common_map.get(sqlKey));
			}*/
			String sql = SqlBuilder.getMergeSql(map.get(sqlName), conditions);
			return SqlBuilder.getMergeSql(sql, conditions);
		}
		else
			throw new Exception("no such sql for sqlname :" + sqlName);			
	}	

	

}
