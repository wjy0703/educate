package cn.com.educate.app.web.login;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.service.login.UserinfoManager;
import cn.com.educate.app.service.security.OperatelogManager;
import cn.com.educate.app.service.security.OperatorDetails;
import cn.com.educate.app.util.RandomValidateCode;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;


@Controller
@RequestMapping(value="/baseinfo")
public class CustomerController {
	
	private Logger logger = LoggerFactory.getLogger(CustomerController.class);
	@Autowired
	private OperatelogManager operatelogManager;
	
	private UserinfoManager userinfoManager;
	@Autowired
	public void setUserinfoManager(UserinfoManager userinfoManager) {
		this.userinfoManager = userinfoManager;
	}
	
	@RequestMapping(value="/getImg")
	public String getImg(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);//输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
	@RequestMapping(value = "/getLoginInfo")
	@ResponseBody
	public Map<String, Object> getLoginInfo(HttpServletRequest request,HttpSession session){
		OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		String ip = getIpAddr(request);//一下几行是 登陆成功将信息添加到操作日志
//        String name = (String)session.getAttribute("loginTo");//取登陆账号
//        NamedJdbcDao namedJdbcDao = OperatelogManager.staticDao;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("createuser", operator.getUsername());
        param.put("ip",ip);
        param.put("remarks","登陆系统");
        operatelogManager.saveLogInfo(param);
//        Operatelog opl = new Operatelog();
//        opl.setCreateuser(operator.getUsername());
//        opl.setIp(ip);
//        opl.setRemarks("登陆系统");
//        operatelogManager.saveOperatelog(opl);
		String sex = operator.getSex();
		String sexs = "先生/女士";
		if(sex != null && sex.equals("0")){
			sexs = "先生";
		}else{
			sexs = "女士";
		}
		Userinfo user = userinfoManager.getUserinfo(operator.getUserId());
		
		user.setOldip(user.getNowip());
		user.setOldlogtime(user.getNowlogtime());
		
		user.setNowlogtime(new Timestamp(new Date().getTime()));
		user.setNowip(getIpAddr(request));
		
		userinfoManager.saveUserinfo(user);
		/*
		String organiName = "未分组";
		Organizeinfo organi = user.getOrganizeinfo();
		String parentName = "";
		if(organi != null){
			organiName = organi.getOrgname();
			//System.out.println(organi.getParentId());
			if(organi.getParentid() != null && organi.getParentid() != 0){
				Organizeinfo organiPar = userinfoManager.gerOrgani(organi.getParentid());
				if(organiPar!=null){
					parentName = organiPar.getOrgname()+"-";
				}
			}
		}
		*/
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean isMess = false;
//        modelMap.put("name", "欢迎您，"+user.getBusinessinfo().getBusiname()+"-"+parentName+organiName+"-"+user.getVname()+sexs+"！");
		modelMap.put("name", "欢迎您，"+user.getVname()+sexs+"！");
		modelMap.put("success", "true");
		modelMap.put("isMess", isMess);
		modelMap.put("pass", userinfoManager.isLoginPass(user));
		return modelMap;
	}
	/* 获取客户端IP地址*/
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
        }
        return ip;
        }
    
}
