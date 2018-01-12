package cn.com.educate.app.taglib;

import java.io.IOException;
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
import cn.com.educate.app.util.MetaDataTypeConverter;


/**
 * 枚举类转换成对应的值
 *
 * @author xjs
 * @date 2013-8-9 下午3:34:40
 */
@Component
@Scope("prototype")
public class ValueToNameTag extends SimpleTagSupport
{
	/**
	 * 类型 对应数据库BASE_ATTR_TYPE中的 coding字段
	 */
	String coding;
	
	String value;
	
	String reconsider;
	
	//@Autowired
	AttrCacheManager attrCacheManager;
	
	// 仅仅信审能看具体的状态（其他均显示成待信审）        
    private static List<Map<String, Object>> auditItems;
    // 仅仅复议可见的状态（其他均显示成待复议）
    private static List<Map<String, Object>> auditCheckItems;
    
	
	@Override
	public void doTag() throws JspException, IOException {
		initManager();
		String name = "";
		/*
		if("jksqState".equals(coding)){
		    OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		    //是否隐藏具体状态...
		    name = StateToTdTag.getHideState(operator,reconsider,value, auditItems, auditCheckItems);
		    
		    if(StringUtils.isEmpty(name)){
	            name = StateToTdTag.stateChangeName(operator,reconsider,value,auditItems,auditCheckItems);
	        }	
		    if( auditItems != null && auditItems.size() > 0 && auditCheckItems != null && auditCheckItems.size() > 0){
    		    if(StringUtils.isEmpty(name) && "1".equals(reconsider) && JksqStateUtils.auditStateNeedPrefix(value)){
    		        name = attrCacheManager.getAttrName(MetaDataTypeConverter.getCodingName(coding),value);
    		        name = "复议" + name;
                }
		    }
		}
		*/
		if(StringUtils.isEmpty(name))
		    name = attrCacheManager.getAttrName(MetaDataTypeConverter.getCodingName(coding),value);
		getJspContext().getOut().write(name); 
	}

	
	private void initManager(){
		if(attrCacheManager == null){
			ServletContext servletContext = ((PageContext)getJspContext()).getServletContext();
		    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    attrCacheManager = (AttrCacheManager) ctx.getBean("attrCacheManager");
		}
		/*
		if(auditItems == null){
             auditItems = attrCacheManager.getAttrByCoding(MetaDataTypeEnum.AUDIT_STATE_HIDE);
             auditCheckItems = attrCacheManager.getAttrByCoding(MetaDataTypeEnum.AUDIT_CHECK_STATE_HIDE);
        }
		*/
	}
    
	/**
	 * 设置static变量为 ---- 空值
	 *
	 * @author xjs
	 * @date 2014-2-19 下午5:33:18
	 */
	public static void  invalidateStaticListNull(){
	        auditItems       =  null;
	        auditCheckItems  =  null;
	}


	public String getCoding() {
		return coding;
	}



	public void setCoding(String coding) {
		this.coding = coding;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
    
    public String getReconsider() {
        return reconsider;
    }
    
    public void setReconsider(String reconsider) {
        this.reconsider = reconsider;
    }
    
}