package cn.com.educate.app.taglib;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.hibernate.tool.hbm2x.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.educate.app.service.baseinfo.AttrCacheManager;
import cn.com.educate.app.util.CreditHarmonyComputeUtilties;
import cn.com.educate.app.util.MetaDataTypeConverter;

@Component
@Scope("prototype")
public class AttrTag extends SimpleTagSupport
{
	/**
	 * 类型 对应数据库XH_MATETYPE中的 TYPE_CODE字段
	 */
	String coding;
	
	String name;
	
	String clazz;
	
	String id;
	
	String value;
	
	private String defaultName;
	
	/**
	 * 是否显示提示选项
	 */
	boolean hasTitle = true;
	
	String title;
	
	String titleValue="";
	
	//@Autowired
	protected AttrCacheManager attrCacheManager;

	//不显示内容
	String unShowName;
	
	@Override
	public void doTag() throws JspException, IOException {
		
	    StringBuffer select = new StringBuffer();		 
		List<Map<String, Object>> options = getSelectOptions(coding);
		select.append("<select name= \"" + getName() + "\" class = \"" + getClazz() + "\"" );
		if(StringUtils.isNotEmpty(getId())){
			select.append(" id=\"" + getId() + "\">");			
		}else{
			select.append(">");			
		}
		if(isHasTitle()){
			if(StringUtils.isNotEmpty(getTitle())){
				select.append("<option value=\""+ getTitleValue()+"\">"+getTitle() +"</option>");
			}else{
				select.append("<option value=\""+ getTitleValue() +"\">请选择</option>");
			}
		}
		
		for(Map<String, Object> option : options){
		    
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
				if(defaultWithNoValue || checkedValue) {
					select.append("<option value=\"" + option.get("VALUE") + "\" selected=\"selected\">"+option.get("DESCRIPTION") +"</option>");
				}else{
					select.append("<option value=\"" + option.get("VALUE") + "\">"+ option.get("DESCRIPTION") +"</option>");
				}
            }
		}
		select.append("</select>");
		getJspContext().getOut().write(select.toString()); 
	}

	
	protected List<Map<String, Object>> getSelectOptions(String coding){
	    initManager();
	    return  attrCacheManager.getAttrByCoding(MetaDataTypeConverter.getCodingName(coding));
	}
	
	
	protected void initManager(){
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



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public boolean isHasTitle() {
		return hasTitle;
	}



	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getTitleValue() {
		return titleValue;
	}



	public void setTitleValue(String titleValue) {
		this.titleValue = titleValue;
	}
	
    public String getDefaultName() {
	        return defaultName;
	}

	  
    public void setDefaultName(String defaultName) {
	        this.defaultName = defaultName;
    }
    
    /**
     * 排序类
     *
     * @author xjs
     * @date 2014-2-12 下午4:44:36
     */
    private class ComparatorName implements Comparator<Map<String,Object>>{

        @Override
        public int compare(Map<String, Object> first, Map<String, Object> second) {
            String firstName =  first.get("DESCRIPTION") != null ? first.get("DESCRIPTION").toString() : "";
            String secondName =  second.get("DESCRIPTION") != null ? second.get("DESCRIPTION").toString() : "";
            return firstName.compareTo(secondName);
        }        
    }
    
    /**
     * 排序类
     *
     * @author xjs
     * @date 2014-2-12 下午4:44:36
     */
    private class ComparatorValue implements Comparator<Map<String,Object>>{

        @Override
        public int compare(Map<String, Object> first, Map<String, Object> second) {
           // String first = "" + option.get("VALUE");
    
            return 0;
        }        
    }
	
	public String getUnShowName() {
		return unShowName;
	}


	public void setUnShowName(String unShowName) {
		this.unShowName = unShowName;
	}
    
}