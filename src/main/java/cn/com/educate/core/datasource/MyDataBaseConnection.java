/**
 * 自建jdbc连接，用来在web应用启动时访问数据库。
 * @author jiangxd
 * crate at 2011-11-10
 */

package cn.com.educate.core.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.educate.app.web.InitSetupListener;
import cn.com.educate.core.utils.DesEncrypt;

public class MyDataBaseConnection {
	
	private static Logger log = LoggerFactory.getLogger(MyDataBaseConnection.class);
	
	private static String username;
	
	private static String password;
	
	private static String dburl;
	
	private static String dbdriver;
	
	/**
	 * 根据jndi取得数据库的连接对象
	 * @param jndi
	 * @return
	 * @author jiangxd
	 * crate at 2011-11-10
	 */
	public static Connection getConnection(String jndi){
		Connection con = null;
		InitialContext ic = null;
		DataSource ds = null;

		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup(jndi);
		} catch (NamingException e1) {
			log.error("MyDataBaseConnection.getConnection()" + e1);			
		}
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			log.error("MyDataBaseConnection.getConnection()" + e);
		}
		return con;
	}

	/**
	 * 根据属性文件中的jdbc连接配置信息获取数据库连接对象。
	 * @return
	 * @author jiangxd
	 * create at 2011-11-10
	 */
	public static Connection getConnection(){
		Properties prop = new Properties();
		String sep = File.separator;
		try {
			FileInputStream fis = new FileInputStream(InitSetupListener.rootPath+sep+"WEB-INF"+sep+"classes"+sep+"application.properties");
			prop.load(fis);
			fis.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		dbdriver = prop.getProperty("jdbc.driver");
		username = prop.getProperty("jdbc.username");
		password = prop.getProperty("jdbc.password");
		if (password.indexOf("ENC(")==0){
    		password = DesEncrypt.decodeString (password.substring(4, password.length()-1));
		}
		dburl = prop.getProperty("jdbc.url");
		Connection con = null;
		try {
			Class.forName(dbdriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(dburl,username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * 关闭数据库连接对象，防止内存泄漏。
	 * @param con
	 * @param pstmt
	 * @param rs
	 * @author jiangxd
	 * create at 2011-11-10
	 */
	
	public static void destoryConnection(Connection con,PreparedStatement pstmt,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
