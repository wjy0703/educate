package cn.com.educate.app.service.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor
		implements Filter {

	private static final Logger logger = Logger
			.getLogger(MyFilterSecurityInterceptor.class);
	private List<String> exceptUrls;  
	  
    public List<String> getExceptUrls() {  
        return exceptUrls;  
    }  
  
    public void setExceptUrls(List<String> exceptUrls) {  
        this.exceptUrls = exceptUrls;  
    }  
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request1 = (HttpServletRequest) request;
		HttpServletResponse response1 = (HttpServletResponse) response;
		String url1 = request1.getRequestURI().toString();
		String loginTo = (String) request1.getSession().getAttribute("loginTo");
		
		/** 不拦截url和页面 ---开始---如果不需要此部分可直接删除*/
		String requestUri = request1.getRequestURI(); 
        if(requestUri.startsWith(request1.getContextPath())){  
            requestUri = requestUri.substring(request1.getContextPath().length(), requestUri.length());  
        }  
        //放行exceptUrls中配置的url  
        boolean result = false;
        for (String url:exceptUrls  
             ) {  
            if(url.endsWith("/**")){  
                if (requestUri.startsWith(url.substring(0, url.length() - 3))) {  
                	result = true;  
                }  
            } else if (requestUri.startsWith(url)) {  
            	result = true;  
            }  
        }  
        /** 不拦截url和页面---结束---如果不需要此部分可直接删除 */
        
		if(!result){
			if (!url1.contains("login") && !url1.contains("getImg")) {
				if (loginTo == null) {
					PrintWriter out;
	
					try {
						String ctx = request1.getContextPath();
						out = response1.getWriter();
						boolean isAjax = false;
						if (request1.getHeader("X-Requested-With") != null) {
							isAjax = true;
						}
						String url = ctx + "/login";
						if (isAjax) {
							url += "?error=5";
						}
	
						out.println("<script language=\"javascript\">");
						out.println("top.location.href=\"" + url + "\";");
						out.println("</script>");
						out.close();
						return;
					} catch (java.io.IOException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
		logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - start");
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
		logger.debug("doFilter(ServletRequest, ServletResponse, FilterChain) - end");
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void invoke(FilterInvocation fi) throws IOException,
			ServletException {
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}