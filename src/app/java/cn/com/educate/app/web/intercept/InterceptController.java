package cn.com.educate.app.web.intercept;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.educate.app.entity.security.Authority;
import cn.com.educate.app.service.baseinfo.AuthorityManager;
import cn.com.educate.app.util.RequestPageUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.web.ServletUtils;

@Controller
@RequestMapping(value="/intercept/authority")
public class InterceptController {
	@Autowired
	private AuthorityManager authorityManager;
	
	@RequestMapping(value="/listauth")
	public String listrole(HttpServletRequest request, Model model){
		Page<Authority> page = new RequestPageUtils<Authority>()
                .generatePage(request);
		
		Map<String, Object> params = ServletUtils.getParametersStartingWith2(request, "filter_");
		authorityManager.searchAuthority(page, params);
		model.addAttribute("page", page);
		model.addAttribute("map", params);
		return "intercept/reat/authorityIndex";
	}
	
	@RequestMapping(value="/playvideo")
	public String playvideo(HttpServletRequest request, Model model){
		String path="intercept/player/";
		String playFlag = request.getParameter("playFlag");
		
		model.addAttribute("playFile", "中1.mp4");
		if(StringUtils.isEmpty(playFlag)){
			return path+"mediaPlayer";
		}else{
			return path+playFlag+"Player";
		}
		
//		return "intercept/player/mediaPlayer";
	}
}
