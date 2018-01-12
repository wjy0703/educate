package cn.com.educate.app.web.baseinfo;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.com.educate.app.entity.security.Authority;
import cn.com.educate.app.entity.security.Roleinfo;
import cn.com.educate.app.service.baseinfo.AuthorityManager;
import cn.com.educate.app.service.login.UserinfoManager;
import cn.com.educate.app.util.RequestPageUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.web.DwzResult;
import cn.com.educate.core.web.ServletUtils;

@Controller
@RequestMapping(value="/authority")
public class AuthorityController {
	
	@Autowired
	private AuthorityManager authorityManager;
	@Autowired
	private UserinfoManager userinfoManager;
	
	@RequestMapping(value="/listauth")
	public String listrole(HttpServletRequest request, Model model){
		Page<Authority> page = new RequestPageUtils<Authority>()
                .generatePage(request);
		
		Map<String, Object> params = ServletUtils.getParametersStartingWith2(request, "filter_");
		authorityManager.searchAuthority(page, params);
		model.addAttribute("page", page);
		model.addAttribute("map", params);
		return "customer/authorityIndex";
	}
	
	@RequestMapping(value="/saveauth",method=RequestMethod.POST)
	public String save(@ModelAttribute("auth") Authority auth, HttpServletRequest request, HttpServletResponse response){

		authorityManager.saveAuthority(auth);

		DwzResult success = new DwzResult("200","保存成功","rel_listauth","","closeCurrent","");
		ServletUtils.renderJson(response, success);
		return null;
	}	
	@RequestMapping(value="/addauth", method=RequestMethod.GET)
	public ModelAndView add(){
		return new ModelAndView("customer/authorityInput", "auth", new Authority());
	}
	
	@RequestMapping(value="/editauth/{Id}", method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable Long Id){
		Authority auth = authorityManager.getAuthority(Id);
		return new ModelAndView("customer/authorityInput", "auth", auth);
	}

	@RequestMapping(value="/delauth/{Id}")
	public String delUser(@PathVariable Long Id, HttpServletResponse response){
		authorityManager.deleteAuthority(Id);
		DwzResult success = new DwzResult("200","删除成功","rel_listauth","","","");
		ServletUtils.renderJson(response, success);
		return null;
	}

	@RequestMapping(value="/batchdelauth")
	public String batchDelUser(HttpServletRequest request, HttpServletResponse response){
		String ids = request.getParameter("ids");
		String[] Ids = ids.split(",");
		boolean isSuccess = authorityManager.batchDelAuthority(Ids);
		
		DwzResult success = null;
		if (isSuccess){
			success = new DwzResult("200","删除成功","rel_listauth","","","");
		}
		else{
			success = new DwzResult("300","删除失败","rel_listauth","","","");
		}
		ServletUtils.renderJson(response, success);
		return null;
	}
/* 替换	
    @RequestMapping(value="/findauthority")
    public String findrole( @RequestParam(value="roleId",required=false) String roleId, Model model){
        Roleinfo r = new Roleinfo();
        if(!StringUtils.isBlank(roleId)){
            r = authorityManager.getRoleinfo(Long.valueOf(roleId));
        }
        
        List<Authority> list = authorityManager.getMergedRoleinfoAuthAndAllAuth(r.getAuthorityList());
        model.addAttribute("result", list);
        model.addAttribute("result1", r.getAuthorityList());
        model.addAttribute("role", r);
        return "account/resourcelookup";
    }
   */ 
	@RequestMapping(value="/findauthority")
	public String findrole( @RequestParam(value="roleId",required=false) String roleId, 
	        HttpServletRequest request, Model model){
		Roleinfo r = new Roleinfo();
		if(!StringUtils.isBlank(roleId)){
			r = authorityManager.getRoleinfo(Long.valueOf(roleId));
		}
		Map<String,Object> params = new HashMap<String,Object>();
		List<Authority> list = authorityManager.getMergedRoleinfoAuthAndAllAuth(r.getAuthorityList(),params);
		model.addAttribute("result", list);
		model.addAttribute("result1", r.getAuthorityList());
		model.addAttribute("role", r);
		return "customer/resourcelookup";
	}

	@RequestMapping(value="/findMenu")
	public String findMenu( @RequestParam(value="roleId",required=false) String roleId,
	        HttpServletRequest request, Model model){
		Roleinfo r = new Roleinfo();
		if(!StringUtils.isBlank(roleId)){
			r = authorityManager.getRoleinfo(Long.valueOf(roleId));
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("levelid", Long.parseLong("1"));
		String[] res = userinfoManager.buildMenuByTopId(r.getMenuList(),params);
		model.addAttribute("menuStr", res[0]);
		model.addAttribute("result1", res[1]);
		model.addAttribute("role", r);
		return "customer/menulookup";
	}
	
	@RequestMapping(value="/chkauth")
	public String checkLoginName(HttpServletRequest request, HttpServletResponse response) {
		try {
			String propertyName = request.getParameter("propertyName");
			String newValue = URLDecoder.decode(request.getParameter(propertyName), "UTF-8");
			String oldValue = URLDecoder.decode(request.getParameter("oldValue"), "UTF-8");
			String errmes = URLDecoder.decode(request.getParameter("errmes"), "UTF-8");
			response.setContentType("text/html;charset=utf-8");
			//println("propertyName===>" + propertyName + ";newValue==>" + newValue + ";oldValue==>" +oldValue + ";errmes==>" +errmes);
			if (authorityManager.isAuthNameUnique( newValue, oldValue)) {
				//ServletUtils.renderText(response, "true");
			} else {
				//ServletUtils.render(response, "false", "hello");
				ServletUtils.renderText(response, errmes + "已经存在");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String newValue = request.getParameter("name");
//		String oldValue = request.getParameter("oldname");
/*
		if (authorityManager.isAuthNameUnique(newValue, oldValue)) {
			ServletUtils.renderText(response, "true");
		} else {
			ServletUtils.renderText(response, "false");
		}
		*/
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}


}
