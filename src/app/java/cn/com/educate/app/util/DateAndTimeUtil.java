package cn.com.educate.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTimeUtil {
	private static final SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
//	private static final SimpleDateFormat ymdh = new SimpleDateFormat("yyyy-MM-dd HH:mi:ss");
    /*** 
     * 日期月份减一个月 
     *  
     * @param datetime 
     *            日期(2014-11) 
     * @return 2014-10 
     */  
    public static String dateFormat(String datetime) {  
        Date date = null;  
        try {  
            date = ymd.parse(datetime);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        Calendar cl = Calendar.getInstance();  
        cl.setTime(date);  
        cl.add(Calendar.MONTH, -1);  
        date = cl.getTime();  
        return ymd.format(date);  
    }  
    
    public static Date dateAddMonth(Date date,int addOrMinus) {  
        Calendar cl = Calendar.getInstance();  
        cl.setTime(date);  
        cl.add(Calendar.MONTH, addOrMinus);  
        cl.getTime();  
        return cl.getTime();  
    } 
  
    public static String dateFormat(Date date) {  
        return ymd.format(date);  
    }  
    
    public static void main(String[] args) {
		System.out.println(dateFormat(dateAddMonth(new Date(),999)));
	}
  
    /**** 
     * 传入具体日期 ，返回具体日期减一个月。 
     *  
     * @param date 
     *            日期(2014-04-20) 
     * @return 2014-03-20 
     * @throws ParseException 
     */  
    public static String subMonth(String date) throws ParseException {  
        Date dt = ymd.parse(date);  
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(dt);  
  
        rightNow.add(Calendar.MONTH, -1);  
        Date dt1 = rightNow.getTime();  
        String reStr = ymd.format(dt1);  
  
        return reStr;  
    }  
    /**
	 * 得到几天前的时间 return string
	 */
	public static String getDateBefore(String d, int day) {
		return dateToString(getDateBefore(StringToDate(d, "yyyy-MM-dd"), day));
	}

	/**
	 * 得到几天前的时间 return date
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}
	/**
	 * 得到几天后的时间 return string
	 */
	public static String getDateAfter(String d, int day) {
		return dateToString(getDateAfter(StringToDate(d, "yyyy-MM-dd"), day));
	}
	/**
	 * 得到几天后的时间
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
	
	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Date类型转String类型
	 * 
	 * @param date
	 * @return 字符串时间格式
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String str = formatDate.format(date);
		Date time = null;
		try {
			time = formatDate.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate.format(time);
	}
	
	/**
     * Date类型转String类型
     * 
     * @param date
     * @return 字符串时间格式 yyyy-MM-dd hh:mm:ss
     */
    public static String dateToStringTime(Date date) {
        String str = ymd.format(date);
        Date time = null;
        try {
            time = ymd.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ymd.format(time);
    }
	
	/**
	 * Date类型转String类型
	 * 
	 * @param date
	 * @return 字符串时间格式
	 */
	public static String dateToString(String dateFormat) {
		SimpleDateFormat formatDate = new SimpleDateFormat(dateFormat);
		String str = formatDate.format(new Date());
		Date time = null;
		try {
			time = formatDate.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate.format(time);
	}
}
