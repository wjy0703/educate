package cn.com.educate.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**
 * 与json串操作相关的类
 * @author xjs
 *
 */
public class P2PJsonUtils {
	 
	/**
     * 将前台传递回的文件字符串转换成map
     * @param files
     * @return
     */
	public static List<Map<String, String>> filestrToMap(String files) {
		files = "[" + files + "]";
		JSONArray jsonArray = JSONObject.parseArray(files);
		List<Map<String,String>> fileNames = new ArrayList<Map<String,String>>();
		for(int index = 0; index < jsonArray.size(); index++){
 			Map<String,String> map = new HashMap<String,String>();
 			JSONArray json = jsonArray.getJSONArray(index);
 			//数组格式，包含一个object
 			JSONObject fileObject = json.getJSONObject(0);
 			String fileFormat = fileObject.getString("fileFormat");
 			String fileName = fileObject.getString("fileName");
 			String newFileName = fileObject.getString("newFileName");
 			map.put("fileFormat", fileFormat);
 			map.put("fileName", fileName);
 			map.put("newFileName", newFileName);
 			fileNames.add(map);
 		}
		
		return fileNames;
	}
}
