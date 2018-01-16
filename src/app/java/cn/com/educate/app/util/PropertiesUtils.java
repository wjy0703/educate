package cn.com.educate.app.util;

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

import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.app.service.security.OperatorDetails;
import cn.com.educate.app.web.InitSetupListener;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;
import cn.com.educate.core.utils.DBUUID;

public class PropertiesUtils
{
  private static final String DEFAULT_ENCODING = "UTF-8";
  private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
  private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
  private static ResourceLoader resourceLoader = new DefaultResourceLoader();
  
  
  public static Properties loadProperties(String[] locations)
    throws IOException
  {
    Properties props = new Properties();

    label172: for (String location : locations)
    {
      logger.debug("Loading properties file from classpath:" + location);

      InputStream is = null;
      try {
        Resource resource = resourceLoader.getResource(location);
        is = resource.getInputStream();
        propertiesPersister.load(props, new InputStreamReader(is, DEFAULT_ENCODING));
      } catch (IOException ex) {
        logger.info("Could not load properties from classpath:" + location + ": " + ex.getMessage());

        if (is == null) break label172;
        is.close();
      }
      finally
      {
        if (is != null)
          is.close();
      }
    }

    return props;
  }
  /**
   * 根据职务判断是否只查询当前商家
   */
  public static void putBusidCheck(Map<String, Object> params)
  {
	  OperatorDetails operator = (OperatorDetails)SpringSecurityUtils.getCurrentUser();
//	  params.put("busid", -1L);
//	  params.put("orgid", -1L);
	  params.put("vsystype", "-1");
	  if(null != operator){
		  List<Roleinfo> roleList = operator.getRoleList();
		  String role = "1";
		  for(Roleinfo r : roleList){
			  if(r.getId()==0){
				  role="0";
				  break;
			  }
		  }
		  String post = operator.getPositionCode();
		  if(StringUtils.isNotEmpty(post)){
			  logger.warn("====PropertiesUtils======putBusidCheck======post======:::"+post);
			  if(post.equals("0") && role.equals("0")){//超级管理员
//				  params.remove("busid");
//				  params.remove("orgid");
				  params.remove("vsystype");
			  }else 
				  {if (post.equals("1")){//商家管理员
//					  params.put("busid", operator.getBusid());
					  //查询业务属性的资源和菜单
					  params.put("vsystype", "1");//
				  }else if (post.equals("2")){//店铺管理员
//					  params.put("busid", operator.getBusid());
//					  params.put("orgid", operator.getDeptId());
					  params.put("vsystype", "1");
				  }else{
//					  params.put("busid", operator.getBusid());
//					  params.put("orgid", operator.getDeptId());
					  params.put("vsystype", "1");
				  }
			  }
		  }
	  }
  }

  /**
   * 根据职务判断是否页面显示系统内容,1不显示，0可以显示
   */
  public static String putBusidLook()
  {
	  OperatorDetails operator = (OperatorDetails)SpringSecurityUtils.getCurrentUser();
	  String canLook= "1";
	  
	  if(null != operator){
		  List<Roleinfo> roleList = operator.getRoleList();
		  String role = "1";
		  for(Roleinfo r : roleList){
			  if(r.getId()==0){
				  role="0";
				  break;
			  }
		  }
		  String post = operator.getPositionCode();
//		  Long busid = operator.getBusid();
//		  if(StringUtils.isNotEmpty(post) && busid >= 0){
			  logger.warn("====PropertiesUtils======putBusidLook======post======:::"+post);
			  if(post.equals("0") && role.equals("0")){//超级管理员
				  canLook ="0";
			  }
//		  }
	  }
	  return canLook;
  }
  public static List<Map<String, String>> upFile(HttpServletRequest request, String path)
  {
    List fileNames = new ArrayList();
    try {
      fileNames = upFileFullFunctions(request, path);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileNames;
  }

  public static List<Map<String, String>> upFileFullFunctions(HttpServletRequest request, String path)
    throws Exception
  {
    List fileNames = new ArrayList();
    Map map = new HashMap();
    DiskFileItemFactory factory = new DiskFileItemFactory();

    factory.setSizeThreshold(10240000);

    String base = InitSetupListener.filePath + path + 
      File.separator;

    File file = new File(base);
    if (!(file.exists()))
      file.mkdirs();
    factory.setRepository(file);
    ServletFileUpload upload = new ServletFileUpload(factory);

    upload.setFileSizeMax(10002400000L);

    upload.setSizeMax(10002400000L);
    upload.setHeaderEncoding(DEFAULT_ENCODING);
    try
    {
      List items = upload.parseRequest(request);
      FileItem item = null;
      String fileName = null;
      for (int i = 0; i < items.size(); ++i) {
        item = (FileItem)items.get(i);

        if ((!(item.isFormField())) && (item.getName().length() > 0)) {
          String fm = item.getName();

          map.put("fileFormat", fm.substring(fm.lastIndexOf(".") + 1, fm.length()));
          String newFm = DBUUID.getID() + fm.substring(fm.lastIndexOf("."), fm.length());
          map.put("fileName", fm);

          map.put("newFileName", newFm);

          fileName = base + File.separator + newFm;

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