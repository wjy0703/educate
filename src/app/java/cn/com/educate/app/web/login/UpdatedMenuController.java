package cn.com.educate.app.web.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.tool.hbm2x.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.educate.app.entity.login.UpdatedMenu;
import cn.com.educate.app.service.baseinfo.AddressManager;
import cn.com.educate.app.service.baseinfo.AttrCacheManager;
import cn.com.educate.app.service.baseinfo.UpdatedMenuManager;
import cn.com.educate.app.service.security.OperatorDetails;
import cn.com.educate.app.util.DepartmentManager;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;

@Controller
@RequestMapping(value="/menus")
public class UpdatedMenuController {

	

	private Logger logger = LoggerFactory.getLogger(UpdatedMenuController.class);
	
	@Autowired
	private UpdatedMenuManager updatedMenuManager;

	@Autowired
	AddressManager addressManager;
	
	@Autowired
	AttrCacheManager attrCacheManager;
	
	@Autowired
	DepartmentManager departmentManager;
	
	
	/**
	 * 获取顶级菜单 xjs
	 * @return
	 */
	@RequestMapping(value = "/topmenus", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> getTopMenus(){		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		List<UpdatedMenu> set = updatedMenuManager.getTopMenuByUserId(operator.getUserId());
		modelMap.put("total", set.size());
		modelMap.put("data", set);
		modelMap.put("success", "true");
		return modelMap;
    }
	/**
	 * 获取顶级菜单 xjs 包括子二三级菜单，树形使用
	 * @return
	 */
	@RequestMapping(value = "/menusAll", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getTopMenusAll(){		
	    Map<String, Object> modelMap = new HashMap<String, Object>();
	    OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
	    List<UpdatedMenu> set = updatedMenuManager.getTopMenuByUserId(operator.getUserId());
	    for (int i = 0; i < set.size(); i++) {
	        UpdatedMenu menu = set.get(i);
	        menu.setSubMenu( updatedMenuManager.getSecondMenu(operator.getUserId(), menu.getId()));
        }
	    modelMap.put("total", set.size());
	    modelMap.put("data", set);
	    modelMap.put("success", "true");
	    return modelMap;
	}
	
	/**
	 * 根据顶级菜单ID获取其下所有的菜单，并且按照上下级层属关系进行排序。
	 * @param Id 顶级菜单ID
	 * @param model
	 * @return 返回菜单对象列表，在jsp页面中生成菜单的html片段。
	 */
	@RequestMapping(value = "/menuleft/{Id}")
	public String menuleft(@PathVariable Long Id, Model model){
		OperatorDetails operator = SpringSecurityUtils.getCurrentUser();
		List<UpdatedMenu> menus = updatedMenuManager.getSecondMenu(operator.getUserId(), Id);
		model.addAttribute("menus",menus);
		return "customer/updatedMenu";
	}
	
	/**
	 * 清楚缓存
	 * @return
	 */
	@RequestMapping(value = "/invalidateAll")
	@ResponseBody
    public String invalidateAll() {
        updatedMenuManager.invalidateAll();
        return "success";
    }
	
	
	/**
	 * 指定类型的缓存，此处写的不好，应该所有的Manager继承一个父类，提供invalidateAll
	 * @return
	 */
	@RequestMapping(value = "/invalidate/{type}")
	@ResponseBody
    public String invalidateByType(@PathVariable String type){		
		if(StringUtils.equals(type, "address")){
			addressManager.invalidateAll();
			return "success";
		}else if (StringUtils.equals(type, "enums")){
			attrCacheManager.invalidateAll();
			return "success";
		}else if (StringUtils.equals(type,"dept")){
		    departmentManager.invalidateAll();
			return "success";
		}else if (StringUtils.equals(type,"menu")){
		    updatedMenuManager.invalidateAll();
            return "success";
        }else if (StringUtils.equals(type,"user")){
            return "success";
        }else if (StringUtils.equals(type,"all")){
            addressManager.invalidateAll();
            attrCacheManager.invalidateAll();
            departmentManager.invalidateAll();
            updatedMenuManager.invalidateAll();
            departmentManager.invalidateAll();
            return "success";
        }
		else{
		    return "false";
		}
    }
	
	/**
	 * 
	 *
	 * @param Id
	 * @param model
	 * @return
	 * @author xjs
	 * @date 2013-12-11 下午2:12:41
	 */
	@RequestMapping(value = "/cacheControl")
    public String cacheControl(){
        return "cache/cacheControl";
    }
	
	
}
