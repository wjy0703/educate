/**
 * 初始化加载属性表信息的监听器
 * @author jiangxd
 * create at 2011-11-10
 */
package cn.com.educate.app.web;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.educate.app.Constants;
import cn.com.educate.core.datasource.MyDataBaseConnection;



public class InitSetupListener implements ServletContextListener {
//	private static  OfficeManager officeManager;
//	private static int port[] = {8100};
	
	private Logger logger = LoggerFactory.getLogger(InitSetupListener.class);

	public static String rootPath;
	public static String filePath;
	public static String backDirectory;
//	public static String swfServer;
//	public static String swfBackServer;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
//		 System.out.println("关闭office转换服务....");
//	        if (officeManager != null) {
//	            officeManager.stop();
//	        }
//	        System.out.println("关闭office转换成功!");
		System.out.println("启动---InitSetupListener.contextDestroyed()---"+sdf.format(new Date()));
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//startService();
		rootPath = arg0.getServletContext().getRealPath("/");
		System.out.println("rootPath==="+rootPath);
		filePath = "D:" + File.separator + "xhFile" + File.separator;
		backDirectory = "Z";
		/*
		swfServer = "http://210.51.3.64:8888/CHPxhFile";
		swfBackServer = "http://210.51.3.64:8888/BackCHPxhFile";
		System.out.println("filePath==="+filePath);
		loadAttrInfo();
       logger.debug(rootPath);
		*/
		System.out.println("启动---InitSetupListener.contextInitialized()--"+sdf.format(new Date()));
	}
	
	/**
	 * 从数据库中完全加载所有的属性表信息
	 * @author jiangxd
	 */
	/*public static void loadAttrInfo(){
		Connection con = MyDataBaseConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TreeMap<String, List<Map<String,Object>>> map = new TreeMap<String, List<Map<String,Object>>>();
		String preCoding = null;
		try {
			pstmt = con.prepareStatement("select a.coding, b.* from base_attr_type a, base_attr b where a.id = b.type_id order by b.type_id,b.sort_no",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			Map<String,Object> attr = null;
			List<Map<String,Object>> attrType = null;
			while(rs.next()){
				attr = new LinkedHashMap<String, Object>();
				if(!rs.getString("CODING").trim().equals(preCoding)){
					if(preCoding!=null){
						map.put(preCoding, attrType);
					}
					attrType = new LinkedList<Map<String, Object>>();
				}
				preCoding = rs.getString("CODING").trim();
				attr.put("id", rs.getLong("ID"));				
				attr.put("value", rs.getString("VALUE").trim());
				attr.put("keyName", rs.getString("KEY_NAME").trim());
				attr.put("des", rs.getString("DESCRIPTION").trim());
				attrType.add(attr);
				//末尾进行的判断
				if(rs.isLast()){
					map.put(preCoding, attrType);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MyDataBaseConnection.destoryConnection(con, pstmt, rs);
		}
		Constants.Attr_Map = map;
	}*/

	/**
	 * 根据属性类型表的属性编码刷新需要变化的属性信息。
	 * @param coding  属性类型编码，对应数据库表base_attr_type的coding字段值。
	 * @author jiangxd
	 * create by 2011-11-10
	 */
	/*
	public static void replaceAttr2Map(String coding){
    	Connection con = MyDataBaseConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String,Object>> attrType = new  LinkedList<Map<String,Object>>();
		
		try {
			pstmt = con.prepareStatement("select a.coding, b.* from baseattrtype a, base_attr b where a.id = b.type_id and a.coding='" + coding + "' order by b.sort_no");
			rs = pstmt.executeQuery();
			Map<String,Object> attr = null;
			
			while(rs.next()){
				attr = new LinkedHashMap<String, Object>();
				attr.put("id", rs.getLong("ID"));				
				attr.put("value", rs.getString("VALUE").trim());
				attr.put("keyName", rs.getString("KEY_NAME").trim());
				attr.put("des", rs.getString("DESCRIPTION").trim());
				attrType.add(attr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			MyDataBaseConnection.destoryConnection(con, pstmt, rs);
		}
		Constants.Attr_Map.put(coding, attrType);
    }
	*/
}
