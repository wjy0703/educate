package cn.com.educate.app.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;  
        HttpServletResponse response = (HttpServletResponse) arg1; 
        
        String yanzhengm = request.getParameter("j_captcha");
        String sessionyanz = (String)request.getSession(true).getAttribute("RANDOMVALIDATECODEKEY");
        String ctx = request.getContextPath();
        if(sessionyanz == null){
        	response.sendRedirect(ctx+"/login?error=4");
        }else{
        	if(yanzhengm.toLowerCase().equals(sessionyanz.toLowerCase())){
        		request.getSession().setAttribute("loginTo", request.getParameter("j_username"));
        	    String ip = getIpAddr(request);//将ip地址放到session中  留在session失效时用
        	    request.getSession().setAttribute("ip", ip);
            	arg2.doFilter(request, response); 
            }else{
            	request.setAttribute("error", "4");
            	response.sendRedirect(ctx+"/login?error=4");
            }
        }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
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