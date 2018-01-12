/*
 * $HeadURL: https://springside.googlecode.com/svn/springside3/trunk/modules/core/src/main/java/org/springside/modules/utils/PropertiesUtils.java $
 * $Id: PropertiesUtils.java 1097 2010-05-22 13:11:45Z calvinxiu $
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package cn.com.educate.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import cn.com.educate.app.service.security.OperatorDetails;
import cn.com.educate.app.web.InitSetupListener;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;

/**
 * Properties Util函数.
 * 
 * @author calvin
 */
public class PropertiesUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * 载入多个properties文件, 相同的属性最后载入的文件将会覆盖之前的载入.
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... locations) throws IOException {
		Properties props = new Properties();

		for (String location : locations) {

			logger.debug("Loading properties file from classpath:" + location);

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				propertiesPersister.load(props, new InputStreamReader(is, DEFAULT_ENCODING));
			} catch (IOException ex) {
				logger.info("Could not load properties from classpath:" + location + ": " + ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}
	
	/**
	 * 借款端 设置tableName
	 * 后期可独立调用
	 * @return
	 */
	public static String getLoanSql(){
	    return getLoanSql("a");
	}
	
	/**
	 * 主表过滤数据查询SQL 借款端 Loan
	 * @param filter 过滤条件
	 * @return 返回sql
	 */
	public static String getLoanSql(String tableName){
		String sql = "";
		String value = "";
		OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		String positionCode = operator.getPositionCode();
		if(StringUtils.isNotEmpty(positionCode)) {
			//团队经理
			if(positionCode.equals("0002")){
				value = String.valueOf(operator.getDeptId());
				if(StringUtils.isNotEmpty(value)) {
					sql = sql + " and "+tableName+".organi_id = " +value +" ";
				}
			}
			//城市经理
			if(positionCode.equals("0003")){
				value = operator.getCityInData();
				if(StringUtils.isNotEmpty(value)) {
				sql = sql + " and "+tableName+".organi_id in ("+value+")";
				}
			}
		}
		return sql;
	}
	
	/**
	 * 出借端 设置tableName
	 * 后期可独立调用
	 * @return
	 */
	public static String getLendSql(){
	    return getLendSql("a");
	}
	
	/**
	 * 主表过滤数据查询SQL 出借端
	 * @param filter 过滤条件
	 * @return 返回sql
	 */
	public static String getLendSql(String tableName){
		String sql = "";
		String value = "";
		OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		String positionCode = operator.getPositionCode();
		if(StringUtils.isNotEmpty(positionCode)) {
			//团队经理
			if(positionCode.equals("0002")){
				value = String.valueOf(operator.getDeptId());
				if(StringUtils.isNotEmpty(value)) {
					sql = sql + " and "+tableName+".organi_id = " +value +" ";
				}
			}
			//城市经理
			if(positionCode.equals("0003")){
				value = operator.getCityInData();
				if(StringUtils.isNotEmpty(value)) {
				sql = sql + " and "+tableName+".organi_id in ("+value+")";
				}
			}
			//客户经理
			if(positionCode.equals("0001")){
				value = String.valueOf(operator.getLoginEmployeeId());
				if(StringUtils.isNotEmpty(value)) {
					sql = sql + " and "+tableName+".employee_crm=" + value+"" ;
				}
			}
		}
		return sql;
	}
	
	/**
	 * 车借端 设置tableName
     * 后期可独立调用
	 * @return
	 */
	public static String getCarSql(){
	    return getCarSql("a");
	}
	
	/**
	 * 主表过滤数据查询SQL 车借
	 * @param filter 过滤条件
	 * @return 返回sql
	 */
	public static String getCarSql(String tableName){
		String sql = "";
		String value = "";
		OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		String positionCode = operator.getPositionCode();
		if(StringUtils.isNotEmpty(positionCode)) {
			//团队经理
			if(positionCode.equals("0002")){
				value = String.valueOf(operator.getLoginEmployeeId());
				if(StringUtils.isNotEmpty(value)) {
					sql = sql + " and "+tableName+".TEAM_LEADER_ID = " +value +" ";
				}
			}
			//城市经理
			if(positionCode.equals("0003")){
				value = operator.getCityInData();
				if(StringUtils.isNotEmpty(value)) {
				sql = sql + " and "+tableName+".organi_id in ("+value+")";
				}
			}
			//客户经理
			if(positionCode.equals("0001")){
				value = String.valueOf(operator.getLoginEmployeeId());
				if(StringUtils.isNotEmpty(value)) {
					sql = sql + " and "+tableName+".CUSTOMER_LEADER_ID=" + value+"" ;
				}
			}
		}
		return sql;
	}
	
	/**
	 * 上传附件（支持多附件上传）
	 * @param request
	 * @param path （存储文件夹，如：upload）
	 * @return 返回上传文件的文件名
	 */
	public static List<Map<String,String>> upFile(HttpServletRequest request,String path){
		List<Map<String,String>> fileNames = new ArrayList<Map<String,String>>();
		try {
			fileNames = upFileFullFunctions(request,path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileNames;
	}
	
	/**
	 * 上传附件（支持多附件上传）
	 * @param request
	 * @param path （存储文件夹，如：upload）
	 * @return 返回上传文件的文件名
	 * @throws Exception 
	 */
	public static List<Map<String,String>> upFileFullFunctions(HttpServletRequest request,String path) throws Exception{
		List<Map<String,String>> fileNames = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		// 设置临时文件存储位置
//		String base= request.getRealPath(path);
		String base = InitSetupListener.filePath + path
				+ File.separator;
//		System.out.println("base====" + base);
//		String base = "e:/upload";DBUUID
		File file = new File(base);
		if(!file.exists())
			file.mkdirs();
		factory.setRepository(file);
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(10002400000l);
		// 设置整个request的最大值
		upload.setSizeMax(10002400000l);
		upload.setHeaderEncoding("UTF-8");
		/*Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);
		if(randomInt > 50){
			throw new Exception("测试出错");
		}*/
		try {
			List<?> items = upload.parseRequest(request);
			FileItem item = null;
			String fileName = null;
			for (int i = 0 ;i < items.size(); i++){
				item = (FileItem) items.get(i);
				// 保存文件
				if (!item.isFormField() && item.getName().length() > 0) {
					String fm = item.getName();
					//文件格式
					map.put("fileFormat", fm.substring(fm.lastIndexOf(".")+1, fm.length()));
					String newFm = DBUUID.getID() + fm.substring(fm.lastIndexOf("."), fm.length());
					map.put("fileName", fm);
					//System.out.println("原名称：=========="+fm);
					map.put("newFileName", newFm);
					//System.out.println("新名称：=========="+newFm);
					fileName = base + File.separator + newFm;
					//System.out.println("fileName====" + fileName);
					fileNames.add(map);
					item.write(new File(fileName));
				}
			}
		} catch (Exception e) {
		   throw e;
		}
		return fileNames;
	}
	
}
