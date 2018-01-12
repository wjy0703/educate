package cn.com.educate.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String fm = "hfskajf.doc";
//		String newfm = DBUUID.getID();
//		String lastFm = newfm + fm.substring(fm.lastIndexOf("."), fm.length());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()));
	}

}
