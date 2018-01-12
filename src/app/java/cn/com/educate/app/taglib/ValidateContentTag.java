package cn.com.educate.app.taglib;

/**
 * 本标签处理类实现身份验证，传入参数为type,例如在页头中加入<validate:pass type="1" funcId="?"/>
 * ?以#号隔开代表或关系
 * ?以&号隔开代表与关系
 */
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;


public class ValidateContentTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String type = "";

	private String funcId = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public int doStartTag() {
		try{
			boolean exists = false;
			User user = SpringSecurityUtils.getCurrentUser();
			for(GrantedAuthority ga:user.getAuthorities()){
				String[] auth = ga.getAuthority().split("_");
				if(funcId != null && funcId.equals(auth[1])){
					exists = true;
					break;
				}
			}
			if (type.equals("1")) {
				if (exists)
					return (EVAL_BODY_INCLUDE);
				else {
					return (SKIP_BODY);
				}
			}
			return (EVAL_BODY_INCLUDE);
		}catch(Exception e){
			//log.error("session已过期");
		}
		return 1;
	}

	public int doEndTag()  {
		return (EVAL_PAGE);
	}
	
}