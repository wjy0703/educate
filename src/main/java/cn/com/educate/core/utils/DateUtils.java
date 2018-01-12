package cn.com.educate.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

	private static String defaultDatePattern = "yyyy-MM-dd";
	
	 /**
    * 获得默认的 date pattern
    */
   public static String getDatePattern() {
       return defaultDatePattern;
   }

   /**
    * 返回预设Format的当前日期字符串
    */
   public static String getToday() {
       Date today = new Date();
       return format(today);
   }

   /**
    * 使用预设Format格式化Date成字符串
    */
   public static String format(Date date) {
       return format(date, getDatePattern());
   }

   /**
    * 使用参数Format格式化Date成字符串
    */
   public static String format(Date date, String pattern) {
       String returnValue = "";

       if (date != null) {
           SimpleDateFormat df = new SimpleDateFormat(pattern);
           returnValue = df.format(date);
       }

       return (returnValue);
   }

   /**
    * 使用预设格式将字符串转为Date
    */
   public static Date parse(String strDate) throws ParseException {
       return parse(strDate, getDatePattern());
   }

   /**
    * 使用参数Format将字符串转为Date
    */
   public static Date parse(String strDate, String pattern) throws ParseException {
       SimpleDateFormat df = new SimpleDateFormat(pattern);
       return df.parse(strDate);
   }

   /**
    * 在日期上增加数个整月
    */
   public static Date addMonth(Date date, int n) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.add(Calendar.MONTH, n);
       return cal.getTime();
   }
   
   /**
    * 在日期上增加数个整日(n为负数则是减少数日)
    */
   public static Date addDay(Date date, int n) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.add(Calendar.DAY_OF_MONTH, n);
       return cal.getTime();
   }
   
   /**
    * 在日期上增加数个小时(n为负数则是减少数小时)
    */
   public static Date addHour(Date date, int n) {
       Calendar cal = Calendar.getInstance();
       cal.setTime(date);
       cal.add(Calendar.HOUR_OF_DAY, n);
       return cal.getTime();
   }
   
   public static Date parseDate(String str, String[] parsePatterns) throws ParseException {
   	return DateUtils.parseDate(str,parsePatterns);
	 }
   
   /**
	 * 字符串转化为日期,通用性相对较强
	 * @param dateString	具有日期格式的字符串
	 * @param DataFormat	日期格式
	 * @return Date
	 */
	public static Date stringToDate(String dateString, String DataFormat) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DataFormat);
			date = sdf.parse(dateString);
		} catch (ParseException ex) {
			return null;
		}
		return date;
	}

   /**
    * 求出两个时间段的时间差（精确到天/小时/分）
    */
   public static String timeLeft(Date timeNow, Date timeLimit) {
       if (timeNow == null || timeLimit == null) {
           return "0";
       }
       long now = timeNow.getTime();
       long limit = timeLimit.getTime();
       int day = (int) (Math.abs(now - limit) / (3600000*24));
       int hour = (int)(Math.abs(now - limit)%(3600000*24))/3600000;
       int minute = (int)((Math.abs(now - limit)%(3600000*24))%3600000)/60000;
       String timeLeft = "0";
       StringBuffer sb = new StringBuffer();
       if(now<limit){
           sb.append("剩余").append(day).append("天").append(hour).append("小时").append(minute).append("分");
       }
       if(now>limit){
           sb.append("超过").append(day).append("天").append(hour).append("小时").append(minute).append("分");
       }
       timeLeft = sb.toString();
       return timeLeft;
   }

   /**
    * 时间一是否超过时间二
    */
   public static String isExceed(Date timeNow, Date timeLimit) {
       if (timeNow == null || timeLimit == null) {
           return "false";
       }
       long now = timeNow.getTime();
       long limit = timeLimit.getTime();
       if(now > limit){
           return "true";
       }
       return "false";
   }
   
   /**
    * 求出两个时间段的时间差(精确到小时)
    */
   public static int timeInterval(Date timeNow, Date timeLimit) {
       if (timeNow == null || timeLimit == null) {
           return 0;
       }
       long now = timeNow.getTime();
       long limit = timeLimit.getTime();
       int interval = (int) ((now - limit) / 3600000);
       return interval;
   }
   
   /**
	 * 返回java.sql.Date类型的字段值。
	 * @return 
	 */
	public static java.sql.Date getSqlDate(){			
		return new java.sql.Date(new java.util.Date().getTime());	
	}
	
	/**
	 * 返回java.sql.Timestamp类型的字段值。
	 * 
	 * @return 有日期和时间（毫秒级）
	 */
	public static java.sql.Timestamp getSqlTimestamp(){		
		return new java.sql.Timestamp(new java.util.Date().getTime());	
	}

}
