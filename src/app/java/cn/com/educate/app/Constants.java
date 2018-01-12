/**
 * 常量单元类
 * @author jiangxd
 * create at 2011-11-10
 */

package cn.com.educate.app;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Constants {
	
	/**
	 * 属性信息数据，在系统启动时加载，如果有修改，需要调用相关方法进行刷新
	 */
	public static TreeMap<String, List<Map<String,Object>>> Attr_Map = new TreeMap<String, List<Map<String,Object>>>();	 
	
	public static final String catch_ip = SysConfig.getInstance().getConfiguration().getString("CATCH_IP");
	public static final String sktHost = SysConfig.getInstance().getConfiguration().getString("sktHost");
	public static final String sktPort = SysConfig.getInstance().getConfiguration().getString("sktPort");
	public static final String sktUser = SysConfig.getInstance().getConfiguration().getString("sktUser");
	public static final String sktPasswd = SysConfig.getInstance().getConfiguration().getString("sktPasswd");
	public static final String PDF2SWF_PATH = SysConfig.getInstance().getConfiguration().getString("PDF2SWF_PATH");
	public static final String OPENOFFICE_HOME = SysConfig.getInstance().getConfiguration().getString("OPENOFFICE_HOME");
	public static final String OPENOFFICE_PORT = SysConfig.getInstance().getConfiguration().getString("OPENOFFICE_PORT");

	
	/**
	 * 根据属性类型编码，属性键名返回属性值
	 * @param attrTypeCoding 属性类型编码，对应数据库表base_attr_type的coding字段值。
	 * @param keyName  属性键名，对应数据库表base_attr的key_name字段的值.
	 * @return 返回属性值，对应数据库表base_attr的value字段的值.
	 * @author jiangxd
	 */
	public static String getAttrValue(String attrTypeCoding, String keyName){
		String result = "";
		List<Map<String, Object>> attrType = Constants.Attr_Map.get(attrTypeCoding);
		if (attrType != null){
			for (Map<String, Object> attr : attrType){
				if(attr.get("keyName").equals(keyName)){
					result = (String) attr.get("value");
					break;
				}
			}
		}
		return result;		
	}
	
	/**
	 * 根据属性类型编码，属性值返回属性键名
	 * @param attrTypeCoding 属性类型编码，对应数据库表base_attr_type的coding字段值。
	 * @param value  属性值，对应数据库表base_attr的value字段的值.
	 * @return 返回属性键名，对应数据库表base_attr的keyName字段的值.
	 * @author jiangxd
	 * create at 2011-11-11
	 */
	public static String getAttrKey(String attrTypeCoding, String value){
		String result = "";
		List<Map<String, Object>> attrType = Constants.Attr_Map.get(attrTypeCoding);
		if (attrType != null){
			for (Map<String, Object> attr : attrType){
				if(attr.get("value").equals(value)){
					result = (String) attr.get("keyName");
					break;
				}
			}
		}
		return result;		
	}
	
	/**
	 * 根据属性类型编码，属性值返回属性键名
	 * @param attrTypeCoding 属性类型编码，对应数据库表base_attr_type的coding字段值。
	 * @param value  属性值，对应数据库表base_attr的value字段的值.
	 * @return 返回属性键名，对应数据库表base_attr的keyName字段的值.
	 * @author 
	 * create at 2013-7-26
	 */
	public static String[] getAttrDesByValue(String attrTypeCoding, String value){
		String[] result = new String[2];
		List<Map<String, Object>> attrType = Constants.Attr_Map.get(attrTypeCoding);
		if (attrType != null){
			for (Map<String, Object> attr : attrType){
				if(attr.get("value").equals(value)){
					result[0] = (String) attr.get("keyName");
					result[1] = (String) attr.get("des");
					break;
				}
			}
		}
		return result;		
	}
	
	/**
	 * 根据属性类型编码，返回所有的属性列表
	 * @param attrTypeCoding 属性类型编码，对应数据库表base_attr_type的coding字段值。
	 * @return 返回属性值的list.
	 * @author jiangxd
	 */
	public static List<Map<String, Object>> getAttrList(String attrTypeCoding){
		return Constants.Attr_Map.get(attrTypeCoding);
	}

	/**
	 * 根据根据属性类型编码，属性键名返回该条属性记录
	 * @param attrTypeCoding 属性类型编码，对应数据库表base_attr_type的coding字段值。
	 * @param keyName 属性键名，对应数据库表base_attr的key_name字段的值.
	 * @return 返回一个属性记录Map
	 * @author jiangxd
	 */
	public static Map<String, Object> getAttr(String attrTypeCoding, String keyName){
		Map<String, Object> result = null;	
		List<Map<String, Object>> attrType = Constants.Attr_Map.get(attrTypeCoding);
		if (attrType != null){
			for (Map<String, Object> attr : attrType){
				if(attr.get("keyName").equals(keyName)){
					result = attr;
					break;
				}
			}
		}
		return result;
	}
	

}
