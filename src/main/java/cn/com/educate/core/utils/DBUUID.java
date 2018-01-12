package cn.com.educate.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wjy
 * @since 2011-08-30
 */
public class DBUUID {
  public static String getID() {
    UUIDHexGenerator uuidHex = new UUIDHexGenerator();
    return uuidHex.generate().toString();
  }
  private static int startNUM=100000;
  private static final int endNUM=999999;

  public static String getCaseUUID(){
	  
	  SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
	  Date d = new Date();
	  StringBuffer buffer=new StringBuffer(f.format(d));
	  if(startNUM==endNUM)
		  startNUM=100000;
	  buffer.append(new Integer(startNUM++).toString());
	  return buffer.toString();
  }

  public static void main(String[] args) {
	  String id="";
    for (int i = 0; i < 10; i++) {
    	id=getID();
      System.out.println(id+"------"+id.length());
      id=getID();
      System.out.println(id+"------"+id.length());
    }
  }
    
}
