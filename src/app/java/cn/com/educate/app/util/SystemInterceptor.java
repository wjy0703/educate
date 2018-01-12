package cn.com.educate.app.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Repository
public class SystemInterceptor extends HandlerInterceptorAdapter {

	private List<String> exceptUrls;  
	  
    public List<String> getExceptUrls() {  
        return exceptUrls;  
    }  
  
    public void setExceptUrls(List<String> exceptUrls) {  
        this.exceptUrls = exceptUrls;  
    }  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//String servleName = request.getServletPath();
		String servleName=request.getRequestURL().toString();    
        String ctx = request.getContextPath();
        
        String requestUri = request.getRequestURI();  
        if(requestUri.startsWith(request.getContextPath())){  
            requestUri = requestUri.substring(request.getContextPath().length(), requestUri.length());  
        }  
      //放行exceptUrls中配置的url  
        for (String url:exceptUrls  
             ) {  
            if(url.endsWith("/**")){  
                if (requestUri.startsWith(url.substring(0, url.length() - 3))) {  
                    return true;  
                }  
            } else if (requestUri.startsWith(url)) {  
                return true;  
            }  
        }  
        
        if(servleName.contains("loginSystemInterceptor")){
        	String loginTo = (String)request.getSession().getAttribute("loginTo");
            if(loginTo == null){
//            	PrintWriter out = response.getWriter();
//            	String url = ctx+"/login?error=5";
//            	out.println("<script language=\"javascript\">");
//            	out.println("window.top.location.href=\"" + url + "\";");
//            	out.println("</script>");
//            	out.close();  
            	request.getRequestDispatcher(ctx).forward(request, response);
            	
            	return false; 
            }
        }
		return super.preHandle(request, response, handler);
	}

}
