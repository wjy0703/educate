package cn.com.educate.app.taglib;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;


import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.educate.app.service.baseinfo.AttrCacheManager;
import cn.com.educate.app.util.CreditHarmonyComputeUtilties;
import cn.com.educate.app.util.MetaDataTypeConverter;

@Component
@Scope("prototype")
public class AttrRadioTag extends SimpleTagSupport
{
	/**
	 * 类型 对应数据库XH_MATETYPE中的 TYPE_CODE字段
	 */
	String coding;
	
	String name;
	
	String defaultName;
	
	String clazz;
	
	String value;
	
	//单选按钮间隔  $nbsp;$nbsp;$nbsp;$nbsp;
	String split;
	//不显示内容
	String unShowName;
	
	//单选按钮位置
	boolean reverse;
	
	//开始
	int start ;
	
	//结束
	int end;
	
	
	
	//@Autowired
	AttrCacheManager attrCacheManager;
	
	@Override
	public void doTag() throws JspException, IOException {
		initManager();
		StringBuffer radios = new StringBuffer();		 
		List<Map<String, Object>> options = attrCacheManager.getAttrByCoding(MetaDataTypeConverter.getCodingName(coding));
		String input;
		start = getStart() >= 1 ? getStart() :1;
		end   = getEnd() >=1 ? getEnd() : options.size();
		for(int index = start-1 ; index <= end-1 ; index++){
		    Map<String, Object> option = options.get(index);
		    input = "";
		    boolean defaultWithNoValue = false;
            //没有选中的值，并且设定了默认值
            if(StringUtils.isEmpty(getValue()) && StringUtils.isNotEmpty(getDefaultName()) && StringUtils.equals(getDefaultName(), option.get("DESCRIPTION").toString())){
                defaultWithNoValue = true;
            }
            
            boolean checkedValue = false;
            if(StringUtils.isNotEmpty(getValue()) && StringUtils.equals(getValue(), option.get("VALUE").toString())) {
                checkedValue = true;
            }
            
            //判断不显示的内容
            if(StringUtils.isEmpty(unShowName) || CreditHarmonyComputeUtilties.getValueIn(unShowName, option.get("DESCRIPTION").toString())){
				if( defaultWithNoValue || checkedValue) {
				    if(StringUtils.isNotEmpty(getClazz()))
				        input = String.format("<input type=\"radio\" class=\"%s\" name=\"%s\"  value = \"%s\" checked=\"checked\"/>", getClazz(),getName(),option.get("VALUE").toString());
		            else
		                input = String.format("<input type=\"radio\" name=\"%s\"  value = \"%s\" checked=\"checked\"/>",getName(),option.get("VALUE").toString());
				    
				   
				}else{
				    
				    if(StringUtils.isNotEmpty(getClazz())){
				        
	                    input = String.format("<input type=\"radio\" class=\"%s\" name=\"%s\"  value = \"%s\"/>", getClazz(),getName(),option.get("VALUE").toString());
				    }
	                else{
	                    
	                    input = String.format("<input type=\"radio\" name=\"%s\"  value = \"%s\" />",getName(),option.get("VALUE").toString());
	                }
				}
				if(reverse){
					radios.append( option.get("DESCRIPTION") + input + getSplit());
				}
				else{
					radios.append( input +  option.get("DESCRIPTION") + getSplit());
				}
            }
		}
		getJspContext().getOut().write(radios.toString()); 
	}

	
	
	private void initManager(){
		if(attrCacheManager == null){
			ServletContext servletContext = ((PageContext)getJspContext()).getServletContext();
		    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    attrCacheManager = (AttrCacheManager) ctx.getBean("attrCacheManager");
		}
	}



	public String getCoding() {
		return coding;
	}



	public void setCoding(String coding) {
		this.coding = coding;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getClazz() {
		return clazz;
	}



	public void setClazz(String clazz) {
		this.clazz = clazz;
	}




	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



    
    public boolean isReverse() {
        return reverse;
    }



    
    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }



    
    public String getSplit() {
        if(StringUtils.isNotEmpty(split))
            return split;
        else
            return "";
    }

    
    public void setSplit(String split) {
        this.split = split;
    }



    
    public int getStart() {
        return start;
    }



    
    public void setStart(int start) {
        this.start = start;
    }



    
    public int getEnd() {
        return end;
    }



    
    public void setEnd(int end) {
        this.end = end;
    }



    
    public String getDefaultName() {
        return defaultName;
    }



    
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }



	public String getUnShowName() {
		return unShowName;
	}



	public void setUnShowName(String unShowName) {
		this.unShowName = unShowName;
	}
   
}