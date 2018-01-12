package cn.com.educate.app.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.educate.app.util.DepartmentManager;

/**
 *
 * @author xjs
 * @date 2013-8-9 下午3:34:40
 */
@Component
@Scope("prototype")
public class DeptToNameTag extends SimpleTagSupport
{
	/**
	 * 门店ID
	 */
	String id;
		
	//@Autowired
	DepartmentManager departmentManager;
	
	@Override
	public void doTag() throws JspException, IOException {
		initManager();
		String name = "";
		try {
		   if(StringUtils.hasText(getId()))
		       name = departmentManager.getDepartmentName(Long.parseLong(getId())); 
        } catch (Exception e) {
            e.printStackTrace();
        }
		       
		getJspContext().getOut().write(name); 
	}

	
	private void initManager(){
		if(departmentManager == null){
			ServletContext servletContext = ((PageContext)getJspContext()).getServletContext();
		    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    departmentManager = (DepartmentManager) ctx.getBean("departmentManager");
		}
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}
	
	
}