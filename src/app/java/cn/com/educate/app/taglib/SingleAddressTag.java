package cn.com.educate.app.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.educate.app.service.baseinfo.AddressManager;

@Component
@Scope("prototype")
public class SingleAddressTag extends SimpleTagSupport
{
	
	String id;
	
	//@Autowired
	AddressManager addressManager;
	
	
	@Override
	public void doTag() throws JspException, IOException {
		initManager();
		String newId = getId();
		String name = "";
		if(newId != null && !"".equals(newId)){
			name = addressManager.getCityNameById(Long.parseLong(newId));
		}
		getJspContext().getOut().write(name); 
	}
    
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	private void initManager(){
		if(addressManager == null){
			ServletContext servletContext = ((PageContext)getJspContext()).getServletContext();
		    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    addressManager = (AddressManager) ctx.getBean("addressManager");
		}
	}
	
}