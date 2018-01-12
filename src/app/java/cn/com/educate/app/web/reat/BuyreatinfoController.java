package cn.com.educate.app.web.reat;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.educate.app.entity.login.Buyreatinfo;
import cn.com.educate.app.service.reat.BuyreatinfoManager;
import cn.com.educate.app.util.RequestPageUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.web.DwzResult;
import cn.com.educate.core.web.ServletUtils;

@Controller
@RequestMapping(value="/buyreatinfo")
public class BuyreatinfoController {
	private BuyreatinfoManager buyreatinfoManager;
	@Autowired
	public void setBuyreatinfoManager(BuyreatinfoManager buyreatinfoManager) {
		this.buyreatinfoManager = buyreatinfoManager;
	}
	
	@RequestMapping(value="/listBuyreatinfo")
	public String listBuyreatinfo(HttpServletRequest request, Model model){
		// 处理分页的参数
		Page<Buyreatinfo> page = new RequestPageUtils<Buyreatinfo>()
                .generatePage(request);
		
		Map<String, Object> map = ServletUtils.getParametersStartingWith2(request, "filter_");		
		
		buyreatinfoManager.searchBuyreatinfo(page, map);
	
		model.addAttribute("page", page);
		model.addAttribute("map", map);
		return "folder/buyreatinfoIndex";
		
	}
	
	@RequestMapping(value="/saveBuyreatinfo",method=RequestMethod.POST)
	public String saveBuyreatinfo(@ModelAttribute("buyreatinfo") Buyreatinfo buyreatinfo, HttpServletRequest request, HttpServletResponse response){
		
		buyreatinfoManager.saveBuyreatinfo(buyreatinfo);

		DwzResult success = new DwzResult("200","保存成功","rel_listBuyreatinfo","","closeCurrent","");
		ServletUtils.renderJson(response, success);
		
		return null;
	}
	
	@RequestMapping(value="/addBuyreatinfo", method=RequestMethod.GET)
	public String addBuyreatinfo(HttpServletRequest request, Model model){
		model.addAttribute("buyreatinfo", new Buyreatinfo());
		return "folder/buyreatinfoInput";
	}
	
	@RequestMapping(value="/editBuyreatinfo/{Id}", method=RequestMethod.GET)
	public String editBuyreatinfo(@PathVariable Long Id, Model model){
		Buyreatinfo buyreatinfo = buyreatinfoManager.getBuyreatinfo(Id);
		model.addAttribute("buyreatinfo",buyreatinfo);
		return "folder/buyreatinfoInput";
	}

	@RequestMapping(value="/delBuyreatinfo/{Id}")
	public String delUser(@PathVariable Long Id, HttpServletResponse response){
		buyreatinfoManager.deleteBuyreatinfo(Id);
		DwzResult success = new DwzResult("200","删除成功","rel_listBuyreatinfo","","","");
		ServletUtils.renderJson(response, success);
		return null;
	}
	
	@RequestMapping(value="/batchdelBuyreatinfo")
	public String batchDelUser(HttpServletRequest request, HttpServletResponse response){
		String ids = request.getParameter("ids");
		String[] Ids = ids.split(",");
		boolean isSuccess = buyreatinfoManager.batchDelBuyreatinfo(Ids);
		
		DwzResult success = null;
		if (isSuccess){
			success = new DwzResult("200","删除成功","rel_listBuyreatinfo","","","");
		}
		else{
			success = new DwzResult("300","删除失败","rel_listBuyreatinfo","","","");
		}
		ServletUtils.renderJson(response, success);
		return null;
	}
}
