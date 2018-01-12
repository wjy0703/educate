package cn.com.educate.app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public ModelAndView login() {		
		return new ModelAndView("login");
	}
	
	@RequestMapping("/loginError")
	public void loginError(HttpServletRequest request,
			HttpServletResponse response) throws Exception{	
		String ctx = request.getContextPath();
		String loginTo = (String)request.getSession().getAttribute("loginTo");
        if(loginTo == null){
        	request.getRequestDispatcher(ctx).forward(request, response);
//        	PrintWriter out;
//				try {
//					out = response.getWriter();
//					String url = ctx+"/login";
//		        	out.println("<script language=\"javascript\">");
//		        	out.println("window.top.location.href=\"" + url + "\";");
//		        	out.println("</script>");
//		        	out.close();  
//				} catch (java.io.IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
        }
	}

	@RequestMapping("/login_dialog")
	public ModelAndView ajaxlogin() {		
		return new ModelAndView("login_dialog");
	}

}
