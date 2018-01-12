package cn.com.educate.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 * 自定义的java.util.Date的类型转换器
 *
 */
public class UtilDateConverter implements Converter {
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	@Override
	public Object convert(Class arg0, Object arg1) {
		
		Object obj = null;
		if(java.util.Date.class == arg0){
			if(arg1 != null && arg1 instanceof String){
				DateFormat df = new SimpleDateFormat(DATE_PATTERN);
				
				try {
					obj = df.parse((String)arg1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		return obj;
	}

}
