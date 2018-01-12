package cn.com.educate.app.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PublicService {
	
	private static String[] zcYc = new String[12];// 正常格式日期
	
	public String getDateStr(Date date){
		String str = "";
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	        try {  
	        		str = sdf.format(date);  
    	       } catch (Exception e) {  
    	            e.printStackTrace();  
    	} 
    	return str;
	}
	
	public String getDateStrFomat(String time, String hkr){
		String day = "";
		String fomat = "";
		String monNew = "";
		String timeNew = time.substring(0, 10);
		String timeNew1 = time.substring(10, time.length());
		String[] times = timeNew.split("-");
		if(times[1].equals("01")){
			day = getMonthStartAndEnd(time, 1);
		}else{
			day = times[2];
		}
		if(times[1].equals("02")){
			day = hkr;
		}
		Integer mon = Integer.valueOf(times[1])+1;
		System.out.println(mon);
		if(mon < 10){
			monNew = "0" + mon;
		}else{
			monNew = mon+"";
		}
		fomat = times[0] + "-" + monNew + "-" + day + timeNew1;
		System.out.println(fomat);
		System.out.println(getMonthStartAndEnd(time,1));
		return fomat;
	}
	
	private static String getMonthStartAndEnd(String date, int offset) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(date.split("-")[0]));
		cal.set(Calendar.MONTH, Integer.valueOf(date.split("-")[1]) - 1);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, offset + 1);
		cal.add(Calendar.DATE, -1);
		String end = sdf.format(cal.getTime());
		return end;
	}
	
	public static class MapComparatorTotalNo implements Comparator<Map<String, Object>> {

		public int compare(Map<String, Object> o1, Map<String, Object> o2) {

			String inNo1 = o1.get("total").toString();
			String inNo2 = o2.get("total").toString();

			if (Double.valueOf(inNo1) > Double.valueOf(inNo2)) {
				return -1;
			}else if (Double.valueOf(inNo1) < Double.valueOf(inNo2)) {
				return 1;
			} else {
				return 0;
			}
		}

	}

	/**
	 * 格式化时间  2013-7-24
	 * @return
	 */
	public String getFormatDate(){
		Date date = new Date();
		SimpleDateFormat fat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return fat.format(date);
	}
	
	/**
	 * 格式化时间  2013-7-24
	 * @return
	 */
	public String getFormatDateMonth(){
		Date date = new Date();
		SimpleDateFormat fat = new SimpleDateFormat("yyyy-MM");
		return fat.format(date);
	}
	
	/**
	 * 格式化时间  2013-8-23
	 * @return
	 */
	public String getFormatDateMonth(String date){
		String formatDate = "";
		if(date != null && !("").equals(date)){
			SimpleDateFormat fat = new SimpleDateFormat("yyyy-MM");
			formatDate = fat.format(date);
		}
		return formatDate;
	}
	
	/**
	 * 格式化时间  2013-7-24
	 * @return
	 */
	public static String getDateMonth(){
		Date date = new Date();
		SimpleDateFormat fat = new SimpleDateFormat("yyyy-MM");
		return fat.format(date);
	}
	
	/**
	 * 当前月后1年时间  2013-08-01
	 * @return
	 */
	public String[] getYearMonth() {
		int leng = 0;
		for (int i = -12; i < 0; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, i);
			calendar.add(Calendar.MONTH, i+1);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			String monthStr = "0";
			if(month < 10){
				monthStr +=month;
			}else{
				monthStr = month+"";
			}
			zcYc[leng] = year + "-" + monthStr;
			leng++;
		}
		return zcYc;
	}
	
	/**
	 * 当前月后1年时间  2013-08-01
	 * @return
	 */
	public String[] getYearMonths() {
		int leng = 0;
		for (int i = -12; i < 0; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, i);
			calendar.add(Calendar.MONTH, i+1);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			String monthStr = "0";
			if(month < 10){
				monthStr +=month;
			}else{
				monthStr = month+"";
			}
			zcYc[leng] = year + "" + monthStr;
			leng++;
		}
		return zcYc;
	}
	
	public String getMapValue(Map<String, Object> map, String key){
		String mapValue = "";
		if (map.containsKey(key)) {
			mapValue = String.valueOf(map.get(key));
		}
		return mapValue;
	}
}
