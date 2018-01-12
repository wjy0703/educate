package cn.com.educate.app.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.tool.hbm2x.StringUtils;


import com.ibm.icu.text.DecimalFormat;

/**
 * 信和借贷相关计算工具
 * 
 * @author Songjf
 * 
 */
public class CreditHarmonyComputeUtilties {
	// 计算周期是否按照自然月算，true：是；false：否，按照30天算
	private static boolean dateFlag = true;
	private static int day = 30;

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
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
	/**
	 * 根据起始借款日期取出每月还款日 借款合同文件中还款日是每月15或30 公式中不考虑2月一天份 如果是2月份，客户的还款日是在2月份最后一天
	 * 
	 * @param firstDateOfBackMoney
	 * @return
	 */
	public static String getBackMoneyDateOfMonth(String firstDateOfBackMoney) {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		Date date;
		try {
			date = sdf.parse(firstDateOfBackMoney);
			calendar.setTime(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}

//		return String.valueOf(calendar.get(Calendar.DATE));
		String zdr = String.valueOf(calendar.get(Calendar.DATE));
		if(!"15".equals(zdr)){
			zdr = "30";
		}
		return zdr;
	}

	/**
	 * 根据起始借款日期取出每月还款日 借款合同文件中还款日是每月15或30 需配合getFirstDateOfBackMoney方法获取首个还款日 使用
	 * 
	 * @param firstDateOfBackMoney
	 * @return
	 */
	public static String getZdr(String firstDateOfBackMoney) {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		Date date;
		try {
			date = sdf.parse(firstDateOfBackMoney);
			calendar.setTime(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		String zdr = String.valueOf(calendar.get(Calendar.DATE));
		if (!zdr.equals("15")) {
			zdr = "30";
		}
		return zdr;
	}

	/**
	 * 计算第一个还款日期 ：如果借款日期是15日之前，第一个还款日是本月30日 闰年2月份第一个还款日是本月29日 平年2月份第一个还款日是本月28日,
	 * 如果日期是15日之后，首期还款日为下月15日
	 * 
	 * @param loan_date
	 *            借款日期
	 * @return 第一个还款日
	 */
	public static String getFirstDateOfBackMoney(String loanDate) {
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse(loanDate);

			calendar.setTime(date);
			if (calendar.get(Calendar.DATE) < 15) {
				if (calendar.get(Calendar.MONTH) == 1
						&& calendar.isLeapYear(calendar.get(Calendar.YEAR))){
					calendar.set(Calendar.DATE, 29);
				}else if (calendar.get(Calendar.MONTH) == 1
						&& !calendar.isLeapYear(calendar.get(Calendar.YEAR))){
					calendar.set(Calendar.DATE, 28);
				}else{
					calendar.set(Calendar.DATE, 30);
				}
				
			} else if(calendar.get(Calendar.DATE) == 31){
				if (calendar.get(Calendar.MONTH) == 0
						&& calendar.isLeapYear(calendar.get(Calendar.YEAR))){
					calendar.set(Calendar.DATE, 29);
				}else if (calendar.get(Calendar.MONTH) == 0
						&& !calendar.isLeapYear(calendar.get(Calendar.YEAR))){
					calendar.set(Calendar.DATE, 28);
				}else{
					calendar.set(Calendar.DATE, 30);
				}
				if (calendar.get(Calendar.MONTH) < 11) {
					calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				} else {
					calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
					calendar.set(Calendar.MONTH, 0);
				}
			}
			else{
				calendar.set(Calendar.DATE, 15);
				if (calendar.get(Calendar.MONTH) < 11) {
					calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				} else {
					calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
					calendar.set(Calendar.MONTH, 0);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return (calendar.get(Calendar.YEAR)) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DATE);
	}


	/**
	 * 根据当前还款日期获取下一个还款日期
	 * 
	 * @param firstDayOfBackMoney
	 *            首个还款日期
	 * @return 最后一个还款日期
	 */
	public static String getNextDateOfBackMoney(GregorianCalendar calendar) {

		try {

			if(calendar.get(Calendar.DATE) > 27){
				if(calendar.get(Calendar.MONTH) == 0 ){
					if (calendar.isLeapYear(calendar.get(Calendar.YEAR))){
						calendar.set(Calendar.DATE, 29);
					}else if (!calendar.isLeapYear(calendar.get(Calendar.YEAR))){
						calendar.set(Calendar.DATE, 28);
					}else{
						calendar.set(Calendar.DATE, 30);
					}
					calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				}else{
					if (calendar.get(Calendar.MONTH) < 11) {
						calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
					} else {
						calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
						calendar.set(Calendar.MONTH, 0);
					}
					calendar.set(Calendar.DATE, 30);
				}
			}else{
				calendar.set(Calendar.DATE, 15);
				if (calendar.get(Calendar.MONTH) < 11) {
					calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
				} else {
					calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
					calendar.set(Calendar.MONTH, 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String returnStringDate = (calendar.get(Calendar.YEAR)) + "-"
				+ ((calendar.get(Calendar.MONTH)) + 1) + "-"
				+ calendar.get(Calendar.DATE);// 12月份

		return returnStringDate;

	}
	/**
	 * 计算贷款剩余期限（月份）
	 * 
	 * @param firstDateOfBackMoney
	 *            首期还款日期
	 * @param loanMonths
	 *            贷款月份
	 * @return 贷款剩余月份
	 */
	public static Long getRemainingMonths(String firstDateOfBackMoney,
			Long loanMonths) {
		Long remainingMonths = new Long(0);
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse(firstDateOfBackMoney);
			Date nowDate = new Date();
			Date cd;
			calendar.setTime(date);
			String returnStringDate = "";// 12月份
			int yy = 0;
			if(!date.after(nowDate)){
				yy++;
				for(long i = 1 ; i < loanMonths;i++){
					//calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + Integer.parseInt((i+1)+""));//让日期加1 
					returnStringDate = getNextDateOfBackMoney(calendar);// 12月份
					cd = sdf.parse(returnStringDate);
					calendar.setTime(cd);
					System.out.println(sdf.format(cd)+"==="+cd.after(nowDate));
					System.out.println(returnStringDate);
					if(cd.after(nowDate)){
						break;
					}else{
						yy++;
					}
				}
			}
			remainingMonths = loanMonths-yy;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return remainingMonths;

	}

	/**
	 * 计算借款天数，借款日期与截止还款日期之间的天数
	 * 
	 * @param firstDate
	 *            借款日期
	 * @param secondDate
	 *            截止还款日期
	 * @return 借款天数
	 */
	public static long getDaysOfDates(String firstDate, String secondDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long days = 0;
		try {
			Date date1 = sdf.parse(firstDate);
			Date date2 = sdf.parse(secondDate);
			Calendar cal1 = new GregorianCalendar();
			Calendar cal2 = new GregorianCalendar();

			cal1.setTime(date1);
			cal2.setTime(date2);

			days = (cal2.getTimeInMillis() - cal1.getTimeInMillis())
					/ (1000 * 3600 * 24);// 从间隔毫秒变成间隔天数
		} catch (ParseException e) {
		}
		return days+1;

	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 取指定日期年份
	 * 
	 * @param loanDate
	 * @return
	 */
	public static int getYearbyDate(String loanDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int re = 0;
		try {
			Date date = sdf.parse(loanDate);
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			re = c.get(java.util.Calendar.YEAR);
		} catch (ParseException e) {
		}

		return re;
	}

	/**
	 * 取指定日期月份
	 * 
	 * @param loanDate
	 * @return
	 */
	public static int getMonthbyDate(String loanDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int re = 0;
		try {
			Date date = sdf.parse(loanDate);
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			re = c.get(java.util.Calendar.MONTH) + 1;
		} catch (ParseException e) {
		}

		return re;
	}

	/**
	 * 取指定日期日份
	 * 
	 * @param loanDate
	 * @return
	 */
	public static int getDaybyDate(String loanDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int re = 0;
		try {
			Date date = sdf.parse(loanDate);
			Calendar c = new GregorianCalendar();
			c.setTime(date);
			re = c.get(java.util.Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
		}

		return re;
	}

	/**
	 * 按目标长度补充原始数值，不足位数前面补0
	 * 
	 * @param i原始数值
	 * @param len目标长度
	 * @return
	 */
	public static String getAddStrLen(int i, int len) {
		int n = i;
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(len);
		formatter.setGroupingUsed(false);
		return formatter.format(n);
	}

	// /**
	// *
	// // * @param args
	// // * @throws ParseException
	// // */
	
	public static void main(String[] args) throws ParseException {
		//System.out.println(reKyzqjzSgjsrq("2012-02-28","30"));
		// System.out.println(getFirstDateOfBackMoney("2012-9-30"));
		// System.out.println(getLastDateOfBackMoney("2012-9-30",2))
		//System.out.println(reb7_27("2012-01-16", "15"));
		//System.out.println(getDaysOfDates("2012-4-24","2012-5-15"));
		//System.out.println(getFirstDateOfBackMoney("2013-01-31"));
		
		//
		// System.out
		// .println(getRemainingMonths("2012-10-15", Long.valueOf("6")));
		//System.out.println(getZdr(getFirstDateOfBackMoney("2012-1-7")));

		// System.out.println(getLastDateOfBackMoney("2012-2-28", 2));
		// String[] dates = getAllBackMoneyDates("2012-2-28", 24);
		// for (int i = 0; i < dates.length; i++) {
		// System.out.println("第" + (i + 1) + "期还款日：" + dates[i]);
		// }

		// String[] dates = getAllBackMoneyDates("2012-2-28", 24);
		// for (int i = 0; i < dates.length; i++) {
		// System.out.println("第"+(i+1)+"期还款日："+dates[i]);
		// }
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = null;
		 try {
		 date = sdf.parse("2013-09-22");
		 System.out.println("星期"+getWeek(date));
		 } catch (ParseException e) {
		 e.printStackTrace();
		 }

		// System.out.println(getLastDateOfBackMoney("2013-01-15", 12));
		// System.out.println(StringToDate(getLastDateOfBackMoney("2013-01-15",
		// 12), "yyyy-MM-dd"));getBackMoneyOfMonth
		// System.out.println("月利息：" + getFineMoney(12445.43, 16));
		// System.out.println("月本金：" + getBackMoneyOfMonth(251580, 0.01, 12));
		// 0+12%12
		// System.out.println(getLastDateOfBackMoney("2012-7-27", 12));
		// System.out.println(getDaysOfDates("2012-8-3", "2013-1-30"));// 364+31
		// System.out
		// .println((getCapitalOfDay(54470.00, "2012-8-3", "2013-1-30")));
		// System.out.println((getInterestOfDay(54470.00, 0.0101, 6,
		// getDaysOfDates("2012-8-3", "2013-1-30"))));
		// System.out.println(getRemainingMonths("2013-1-14", 6));
		

	}
	/*当前日期星期几
	 * 
	 */
	public static int getWeek(Date d) {
		Calendar cal = Calendar.getInstance();  
		cal.setTime(d);
		int w = cal.get(Calendar.DAY_OF_WEEK)-1;
		if (w <= 0) w = 7;
		//如果6是周六 ,周日为0
	  return w;
	  
	}
	/**
	 * 通过首期日期和资金出借回款方式计算销售折扣利率有效期限
	 * @param sqrq
	 * @param tzcpId
	 * @return
	 */
	public static String getXszklyxqx(String sqrq,String getTzcpZq){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sqrqDate = "";
		try {
			Date date = format.parse(sqrq);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			if(!getTzcpZq.equals("-1")){
				cd.add(Calendar.MONTH, Integer.parseInt(getTzcpZq));
			}else{
				cd.add(Calendar.YEAR, 1);
			}
			/*
			if(tzcpId==82){           //月息通 12个月
				cd.add(Calendar.YEAR, 1);
			}else if(tzcpId==83){     //季度盈 3个月
				cd.add(Calendar.MONTH, 3);
			}else if(tzcpId==85){     //年年盈 12个月
				cd.add(Calendar.YEAR, 1);
			}else if(tzcpId==84){     //双季盈 6个月
				cd.add(Calendar.MONTH, 6);
			}else if(tzcpId==81){     //信和通 12个月
				cd.add(Calendar.YEAR, 1);
			}else if(tzcpId==252512){ //信和宝 2年
				cd.add(Calendar.YEAR, 2);
			}else if(tzcpId==88){     //年年金 1年
				cd.add(Calendar.YEAR, 1);
			}
			*/
			sqrqDate = format.format(cd.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqrqDate;
	}

	
	
	
	/**
     * 通过起始还款日前，和借款期数，得到最终还款日
     * @param startTime
     * @param months
     * @return
     */
    public static String getEndTimeByStartTimeAndCountNew(String startTime , int months){
        Date startDate =  StringToDate(startTime,"yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();  
        cal.setTime(startDate);  
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if(month != Calendar.FEBRUARY || day <20){
            cal.add(Calendar.MONTH,-1);
            SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
            String curDate = s.format(cal.getTime());
            return curDate;   
        }else{
            //是2月28日，或者29日，如果是则返回截至日期的最后一天
            cal.add(Calendar.MONTH,-1);  
            int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH) == 31 ? cal.getActualMaximum(Calendar.DAY_OF_MONTH) :30 ;
            cal.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
            SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
            String curDate = s.format(cal.getTime());  
            return curDate;
        }
    }
    
    
    public static String getEndTimeByStartTimeAndCountday(String startTime , int days){
        Date startDate =  StringToDate(startTime,"yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();  
        cal.setTime(startDate);  
        //int month = cal.get(Calendar.MONTH);
        //int day = cal.get(Calendar.DAY_OF_MONTH);
        
        cal.add(Calendar.DAY_OF_MONTH,+days-1);
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
        String curDate = s.format(cal.getTime());
        return curDate;   
        
    }
    
    /**
     * 判断一个字符串是否在另一个已‘,’分割的字符串内
     * 目前用于枚举类屏幕特定内容
     * @param values
     * @param value
     * @return
     */
    public static boolean getValueIn(String values,String value){
    	boolean result = true;
    	if(StringUtils.isNotEmpty(values)){
    		String[] strs = values.split(",");
    		for(String s:strs){
    			if(s.equals(value)){
    				result = false;
    				break;
    			}
    		}
    	}
    	return result;
    }
    
}