package cn.com.educate.app.excel.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

public class JSON2Map {
    /**
     * JSON to MAP
     *
     * @param json
     * @return
     * @author lionel
     */
    public static  Map<String,Object> json2map(String json){      
        StringBuffer sb = new StringBuffer(json);        
        int colonPos = sb.indexOf(":");
        while(colonPos != -1){
            if(sb.charAt(colonPos+1) == '"' || sb.charAt(colonPos+1) == '{' ){
                colonPos = sb.indexOf(":",colonPos+1);
            }else{                
                sb.insert(colonPos+1, '"');
                int pos = sb.indexOf(",",colonPos+1);
                int posbrace = sb.indexOf("}",colonPos+1);
                //冒号的后面肯定有一个,或者一个}
                if(pos * posbrace > 0){ //全部找到了
                  pos = pos<posbrace ? pos : posbrace;
                }else{//有一个值为-1
                    pos = pos>posbrace ? pos : posbrace;
                }
                sb.insert(pos,'"');
                colonPos = sb.indexOf(":",colonPos+3);
            }
        }
        json = sb.toString();
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonObj = JSONObject.parseObject(json);        
        Set<String> s = jsonObj.keySet();
        for(String key : s){
           if("page".equals(key)){
              continue; 
           }
           Object value = jsonObj.get(key);
           if(!StringUtils.isEmpty(value.toString())){
               String UpperKey =  key.toUpperCase();
               if(UpperKey.indexOf("DATE")==-1){
                  map.put(key, value);
               }else{
                   if(!StringUtils.isEmpty((String)value)){
                       long d = Long.parseLong((String)value);
                       Date da =new Date(d);
                       map.put(key, da);
                   }
               }
           }
        }
        return map;        
    }
    /**
     * 
     *
     * @param json
     * @return
     * @author lionel
     */
    public static  Map<String,Object> fastjson2map(String json){
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(json)){
	        JSONObject jsonObj = JSONObject.parseObject(json,Feature.AllowISO8601DateFormat);        
	        Set<String> s = jsonObj.keySet();
	        for(String key : s){
	           if("page".equals(key)){
	              continue; 
	           }
	           Object value = jsonObj.get(key);
	           map.put(key, value);
	        }
        }
        return map;  
    }
}
