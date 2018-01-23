<%@ page contentType="text/html; charset=utf-8" language="java"%>  
<%@ page import=" java.sql.*,java.util.*,java.io.* " errorPage=""%>  
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://"  
            + request.getServerName() + ":" + request.getServerPort()  
            + path + "/";  
%>  
  
<%  
    out.clear();  
    out = pageContext.pushBody();  
    response.setContentType("image/png ");  
    try {  
        String imgName = request.getParameter("fileName");  
        String p1 = (String) session.getAttribute("impath");//获取配置文件中设置的图片上传路径  
        String path1 = p1+ imgName;//图片绝对路径  
        String path2 = p1+"/ueditor/error.jpg";  
        //判断该路径下的文件是否存在  
        File file = new File(path1);  
        if (file.exists() && !imgName.equals("")) {  
            DataOutputStream temps = new DataOutputStream(  
                    response.getOutputStream());  
            DataInputStream in = new DataInputStream(  
                    new FileInputStream(path1));  
  
            byte[] b = new byte[2048];  
            while ((in.read(b)) != -1) {  
                temps.write(b);  
                temps.flush();  
            }  
  
            in.close();  
            temps.close();  
        } else {  
            DataOutputStream temps = new DataOutputStream(  
                    response.getOutputStream());  
            DataInputStream in = new DataInputStream(  
                    new FileInputStream(path2));  
  
            byte[] b = new byte[2048];  
            while ((in.read(b)) != -1) {  
                temps.write(b);  
                temps.flush();  
            }  
  
            in.close();  
            temps.close();  
        }  
  
    } catch (Exception e) {  
        out.println(e.getMessage());  
    }  
%>  