package cn.com.educate.app.web.reat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.educate.app.entity.login.Businessinfo;
import cn.com.educate.app.entity.login.Buyreatinfo;
import cn.com.educate.app.entity.login.Reatpackage;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.service.baseinfo.BusinessinfoManager;
import cn.com.educate.app.service.reat.BuyreatinfoManager;
import cn.com.educate.app.service.reat.ReatpackageManager;
import cn.com.educate.app.util.HibernateAwareBeanUtilsBean;
import cn.com.educate.app.util.RequestPageUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.web.DwzResult;
import cn.com.educate.core.web.ServletUtils;

@Controller
@RequestMapping(value="/businessinfo")
public class BusinessinfoController {
	private BusinessinfoManager businessinfoManager;
	@Autowired
	public void setBusinessinfoManager(BusinessinfoManager businessinfoManager) {
		this.businessinfoManager = businessinfoManager;
	}
	@Autowired
	private ReatpackageManager reatpackageManager;
	@Autowired
	private BuyreatinfoManager buyreatinfoManager;
	
	@RequestMapping(value="/listBusinessinfo")
	public String listBusinessinfo(HttpServletRequest request, Model model){
		// 处理分页的参数
		Page<Businessinfo> page = new RequestPageUtils<Businessinfo>()
                .generatePage(request);
		
		Map<String, Object> map = ServletUtils.getParametersStartingWith2(request, "filter_");		
		
		businessinfoManager.searchBusinessinfo(page, map);
	
		model.addAttribute("page", page);
		model.addAttribute("map", map);
		return "reat/businessinfoIndex";
		
	}
	
	@RequestMapping(value="/saveBusinessinfo",method=RequestMethod.POST)
	public String saveBusinessinfo(@ModelAttribute("businessinfo") Businessinfo businessinfo, HttpServletRequest request, HttpServletResponse response){
		String oldreatid = request.getParameter("oldreatid");
		if(!StringUtils.isEmpty(oldreatid) && Long.parseLong(oldreatid) == businessinfo.getReatpackage().getId()){
			Businessinfo bus = businessinfoManager.getBusinessinfo(businessinfo.getId());
	        try {
	            // 拷贝页面的值
	        	 new HibernateAwareBeanUtilsBean().copyProperties(bus, businessinfo);
	        	 businessinfoManager.saveBusinessinfo(bus);
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	            throw new RuntimeException("拷贝企业记录出现错误，请联系管理员");
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	            throw new RuntimeException("拷贝企业记录出现错误，请联系管理员");
	        }
		}else{
			businessinfoManager.saveBusinessinfoAll(businessinfo);
		}

		DwzResult success = new DwzResult("200","保存成功","rel_listBusinessinfo","","closeCurrent","");
		ServletUtils.renderJson(response, success);
		
		return null;
	}
	
	@RequestMapping(value="/addBusinessinfo", method=RequestMethod.GET)
	public String addBusinessinfo(HttpServletRequest request, Model model){
		model.addAttribute("businessinfo", new Businessinfo());
		return "reat/businessinfoInput";
	}
	
	@RequestMapping(value="/editBusinessinfo/{Id}", method=RequestMethod.GET)
	public String editBusinessinfo(@PathVariable Long Id, Model model){
		Businessinfo businessinfo = businessinfoManager.getBusinessinfo(Id);
		model.addAttribute("businessinfo",businessinfo);
		return "reat/businessinfoInput";
	}

	@RequestMapping(value="/delBusinessinfo/{Id}")
	public String delUser(@PathVariable Long Id, HttpServletResponse response){
		businessinfoManager.deleteBusinessinfo(Id);
		DwzResult success = new DwzResult("200","删除成功","rel_listBusinessinfo","","","");
		ServletUtils.renderJson(response, success);
		return null;
	}
	
	@RequestMapping(value="/batchdelBusinessinfo")
	public String batchDelUser(HttpServletRequest request, HttpServletResponse response){
		String ids = request.getParameter("ids");
		String[] Ids = ids.split(",");
		boolean isSuccess = businessinfoManager.batchDelBusinessinfo(Ids);
		
		DwzResult success = null;
		if (isSuccess){
			success = new DwzResult("200","删除成功","rel_listBusinessinfo","","","");
		}
		else{
			success = new DwzResult("300","删除失败","rel_listBusinessinfo","","","");
		}
		ServletUtils.renderJson(response, success);
		return null;
	}
	
	@RequestMapping(value="/chkBusi")
	public String chkBusi(HttpServletRequest request, HttpServletResponse response) {
		try {
			String propertyName = request.getParameter("propertyName");
			String newValue = URLDecoder.decode(request.getParameter(propertyName), "UTF-8");
			String oldValue = URLDecoder.decode(request.getParameter("oldValue"), "UTF-8");
			String errmes = URLDecoder.decode(request.getParameter("errmes"), "UTF-8");
			response.setContentType("text/html;charset=utf-8");
			//println("propertyName===>" + propertyName + ";newValue==>" + newValue + ";oldValue==>" +oldValue + ";errmes==>" +errmes);
			if (businessinfoManager.isBusiAccUnique( newValue, oldValue)) {
				//ServletUtils.renderText(response, "true");
			} else {
				//ServletUtils.render(response, "false", "hello");
				ServletUtils.renderText(response, errmes + "已经存在");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/findreat")
	public String findreat( @RequestParam(value="reatid",required=false) String reatid,
	                HttpServletRequest request , Model model){
		Map<String,Object> params = new HashMap<String,Object>();
		String reatname = request.getParameter("reatname");
		if(!StringUtils.isEmpty(reatname)){
			params.put("reatname", reatname);
		}
		if(StringUtils.isEmpty(reatid)){
			reatid="-999";
		}
		List<Reatpackage> list = reatpackageManager.getReatForLook(Long.parseLong(reatid),params);
		model.addAttribute("result", list);
		model.addAttribute("reatid", reatid);
		return "reat/reatlookup";
	}
	
	
	@RequestMapping(value="/buyreatinfo/{Id}", method=RequestMethod.GET)
	public String buyreatinfo(@PathVariable Long Id, Model model){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("busid", Id);
		List<Buyreatinfo> buyreat = businessinfoManager.getBuyreatinfo(params);
		model.addAttribute("buyreat",buyreat);
		Businessinfo businessinfo = businessinfoManager.getBusinessinfo(Id);
		model.addAttribute("businessinfo",businessinfo);
		return "reat/buyreatinfoLook";
	}
}
