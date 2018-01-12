package cn.com.educate.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.springframework.util.StringUtils;

/**
 *
 * @author lucas
 */
public class DateConvert implements Converter {

    public Object convert(Class arg0, Object arg1) {
        String p = (String)arg1;
        if(p== null || p.trim().length()==0){
            return null;
        }   
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(p.trim());
        }
        catch(Exception e){
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                return df.parse(p.trim());
            } catch (ParseException ex) {
                return null;
            }
        }
        
    }
    
    public static Date convert(String dateStr,String pattern) {
        if(StringUtils.hasText(pattern)){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Date date = null;
            try {
                date = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }else{
            return null;
        }
    }
    
    public static void main(String[] args) {
        String str = "1970-1-1";
        Date d = convert(str,"yyyy-MM-dd");
        double di = d.getTime() + 41500;
        Date ddf = new Date((long)di);
        System.out.println(ddf);
    }

}
