package cn.com.educate.app.taglib;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hibernate.tool.hbm2x.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.educate.app.entity.login.Cityinfo;
import cn.com.educate.app.service.baseinfo.AddressManager;


@Component
@Scope("prototype")
public class AddressTag extends SimpleTagSupport
{
	
	
	//@Autowired
	AddressManager addressManager;
	
	/**
	 * 下啦项目的默认：如所有城市，所有省份等,必填
	 */
	String titles;
	/**
	 * 值
	 */
	String values;
	/**
	 * 下拉菜单所对应的名字，必填
	 */
	String names;
	
	boolean required = false;
	
	boolean selectedAll = true;
    
	
	static final String AB = "abcdefghijklmnopqrstuvwxyz";
	static Random rnd = new Random();

	static String randomString( int len ) 
	{
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		String[] titles = this.titles.split(",");
		String[] names = this.names.split(",");
		String[] values  = null;
		if(StringUtils.isNotEmpty(this.values))
			values = this.values.split(",");

		int length = titles.length;
		String[] ids = new String[length];
		for(int index = 0 ; index < length ; index++){
			ids[index] = randomString(15);
		}
		StringBuffer selects = new StringBuffer();
		PageContext pageContext = (PageContext) this.getJspContext();
		HttpServletRequest request =  (HttpServletRequest) pageContext.getRequest();
		
		String ctxPath = request.getSession().getServletContext().getContextPath();
		
		/** 形成第一级下拉的菜单 --- start --------------- */
		String province = names[0];
		String cityId = ids.length >= 2 ? ids[1] : "";
		String refUrl = " refUrl=\"" + ctxPath+"/addresses/getSons/id_{value}";
		//
		if(selectedAll){
			String nextTitle =  "";
			if(titles.length > 1){
				nextTitle = titles[1];
			}
			nextTitle = URLEncoder.encode(nextTitle,"UTF-8");
			//
			//encodeuri
			if(required)
				selects.append("<select class=\"combox required\" name=\""+province +"\" ref=\""+cityId+"\"" + refUrl + "?title="+nextTitle+"\">"); // 若乱码，nextTitle改成1代表城市
			else
				selects.append("<select class=\"combox\" name=\""+province +"\" ref=\""+cityId+"\"" + refUrl + "?title="+nextTitle+"\">");          // 若乱码，nextTitle改成1代表城市
				
		}
		else{
			if(required)
				selects.append("<select class=\"combox required\" name=\""+province +"\" ref=\""+cityId+"\"" + refUrl + "\">");
			else
				selects.append("<select class=\"combox\" name=\""+province +"\" ref=\""+cityId+"\"" + refUrl + "\">");
		}
		
		if(values != null && values.length > 0){
			selects.append(getTopOptions(Long.parseLong(values[0])));
		}else{
			selects.append(getTopOptions((long) -1));
		}
		selects.append("</select>");
		
		
		/** 形成第一级下拉的菜单 --- start --------------- */
		
		for(int index = 1 ; index < titles.length ; index++){
			/* 开始形成 select标签的开始*/
			//形成的 select的ID
			String id = ids.length >= (index + 1) ? ids[index] :"";
			//形成的 select的refID
			String refId = ids.length >= (index + 2 ) ? ids[ index + 1] :"";
			String refTitle = titles.length >= (index + 2 ) ? titles[ index + 1] :"";
			refTitle = URLEncoder.encode(refTitle,"UTF-8");
			if(required)
				selects.append("<select class=\"combox required\" name=\""+names[index]+"\" id=\""+id+"\"");
			else
				selects.append("<select class=\"combox\" name=\""+names[index]+"\" id=\""+id+"\"");
			
			if(StringUtils.isNotEmpty(refId)){
				selects.append(" ref=\""+refId+"\"");
				selects.append(refUrl);
				if(selectedAll)
					selects.append("?title="+refTitle);                 //若乱码，refTitle改为index+1传递数字
			}
			selects.append("\">");
            /* 形成select标签的结束 */ 
			//options部分,首先查看是否有设定值
			String value = null;
			if(values != null)
				value = values.length >= (index + 1) ? values[index] : "";
			//有设定值
		    if(StringUtils.isNotEmpty(value)){
		    	selects.append(getOptions(Long.parseLong(value),titles[index]));
		    }else{
		    	//未设定值，查看其上级节点是否有设定值，有则取回相应的记录
		    	String preValue = null;
				if(values != null)
					preValue = values.length >= index ? values[index-1] : "";
				
				if(StringUtils.isNotEmpty(preValue)){
					selects.append(getOptionsNoSelected(Long.parseLong(preValue),titles[index]));
				}else{
					selects.append(getOptions(-1,titles[index]));
				}
		    }
			/* select的结束标签       */
			selects.append("</select>");
		}
		getJspContext().getOut().write(selects.toString()); 
	}
	
	private String getOptionsNoSelected(long parentId, String title) {
		initManager();
		StringBuffer options = new StringBuffer();
		List<Cityinfo> provinces = addressManager.getSonsByParentId(parentId);
		if(selectedAll)
			options.append("<option value=\"\">" + title + "</option>");
		
		for(Cityinfo province : provinces){
				options.append("<option value=\""+ province.getId()+"\">" + province.getVname() + "</option>");
		}
		return options.toString();
	}

	/**
	 * 获取省份菜单
	 * @param Long 选择的省份id
	 */
	private String getTopOptions(long id) {
		initManager();
		StringBuffer options = new StringBuffer();
		List<Cityinfo> provinces = addressManager.getSonsByParentId(0);
		if(selectedAll)
			options.append("<option value=\"\">" + this.titles.split(",")[0] + "</option>");
		
		for(Cityinfo province : provinces){
			if(province.getId() == id)
				options.append("<option value=\""+ province.getId()+"\" selected=\"selected\">" + province.getVname() + "</option>");
			else
				options.append("<option value=\""+ province.getId()+"\">" + province.getVname() + "</option>");
		}
		return options.toString();
	}

	/**
	 * 非省份
	 * @param id   
	 * @param title
	 * @return
	 */
	private String getOptions(long id,String title){
		StringBuffer options = new StringBuffer();
		if(selectedAll)
			options.append("<option value=\"\">" + title + "</option>");
		if(id < 0){
			return options.toString();
		}
			
		initManager();
		List<Cityinfo> sons = addressManager.getSameLevelCities(id);
		for(Cityinfo son : sons){
			if(son.getId() == id)
				options.append("<option value=\""+ son.getId()+"\" selected=\"selected\">" + son.getVname() + "</option>");
			else
				options.append("<option value=\""+ son.getId()+"\">" + son.getVname() + "</option>");
		}
		return options.toString();
		
	}
	
	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
    
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	private void initManager(){
		if(addressManager == null){
			ServletContext servletContext = ((PageContext)getJspContext()).getServletContext();
		    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    addressManager = (AddressManager) ctx.getBean("addressManager");
		}
	}
	
}