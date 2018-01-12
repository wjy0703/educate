package cn.com.educate.app.taglib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hibernate.tool.hbm2x.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.com.educate.app.Constants;
import cn.com.educate.app.util.MetaDataTypeConverter;

@Component
@Scope("prototype")
public class CatchIpAddress extends SimpleTagSupport{
	
	String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		String name = "";
		name = getAddressByIP(value);
		getJspContext().getOut().write(name); 
	}
	
	public static String getAddressByIP(String strIP) {  
		String ipAdress = "局域网";
        try {  
            URL url = new URL(Constants.catch_ip+strIP);  
            URLConnection conn = url.openConnection();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
            String line = null;  
            StringBuffer result = new StringBuffer();  
            while ((line = reader.readLine()) != null) {  
                result.append(line);  
            }  
            reader.close();  
            String ipAddr = result.toString();  
            try {  
                JSONObject obj1= new JSONObject(ipAddr);  
                /*
                 {"content":{"point":{"y":"3515188.13","x":"13382905.27"},
                 			 "address":"浙江省杭州市",
                 			 "address_detail":{"street":"",
                 			                    "province":"浙江省",
                 			                    "city_code":179,
                 			                    "street_number":"",
                 			                    "district":"",
                 			                    "city":"杭州市"}
                 			 },
                  "status":0,
                  "address":"CN|浙江|杭州|None|CHINANET|0|0"}
                 */
                if("0".equals(obj1.get("status").toString())){  
//                JSONObject obj2= new JSONObject(obj1.get("content").toString());  
//                JSONObject obj3= new JSONObject(obj2.get("address_detail").toString());  
//                ipAdress = obj2.get("address").toString(); //浙江省杭州市
                //用”|“直接截取会有问题，需要先全部替换成”，“
                String adr = obj1.get("address").toString().replace("|", ",");
                String[] adress = adr.split(",");//[CN, 浙江, 杭州, None, CHINANET, 0, 0]
                ipAdress = adress[1]+adress[2];//浙江杭州
                
                }else{  
//                    return "读取失败";  
                }  
            } catch (JSONException e) {  
                e.printStackTrace();  
//                return "读取失败";  
            }  
              
        } catch (IOException e) {  
//            return "读取失败";  
        }  
        return ipAdress;  
    }  
}
