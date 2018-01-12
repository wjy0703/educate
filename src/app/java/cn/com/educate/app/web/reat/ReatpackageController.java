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

import cn.com.educate.app.entity.login.Reatpackage;
import cn.com.educate.app.service.reat.ReatpackageManager;
import cn.com.educate.app.util.RequestPageUtils;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.web.DwzResult;
import cn.com.educate.core.web.ServletUtils;

@Controller
@RequestMapping(value="/reatpackage")
public class ReatpackageController {
	private ReatpackageManager reatpackageManager;
	@Autowired
	public void setReatpackageManager(ReatpackageManager reatpackageManager) {
		this.reatpackageManager = reatpackageManager;
	}
	
	@RequestMapping(value="/listReatpackage")
	public String listReatpackage(HttpServletRequest request, Model model){
		// 处理分页的参数
		Page<Reatpackage> page = new RequestPageUtils<Reatpackage>()
                .generatePage(request);
		
		Map<String, Object> map = ServletUtils.getParametersStartingWith2(request, "filter_");		
		
		reatpackageManager.searchReatpackage(page, map);
	
		model.addAttribute("page", page);
		model.addAttribute("map", map);
		return "reat/reatpackageIndex";
		
	}
	
	@RequestMapping(value="/saveReatpackage",method=RequestMethod.POST)
	public String saveReatpackage(@ModelAttribute("reatpackage") Reatpackage reatpackage, HttpServletRequest request, HttpServletResponse response){
		
		reatpackageManager.saveReatpackage(reatpackage);

		DwzResult success = new DwzResult("200","保存成功","rel_listReatpackage","","closeCurrent","");
		ServletUtils.renderJson(response, success);
		
		return null;
	}
	
	@RequestMapping(value="/addReatpackage", method=RequestMethod.GET)
	public String addReatpackage(HttpServletRequest request, Model model){
		model.addAttribute("reatpackage", new Reatpackage());
		return "reat/reatpackageInput";
	}
	
	@RequestMapping(value="/editReatpackage/{Id}", method=RequestMethod.GET)
	public String editReatpackage(@PathVariable Long Id, Model model){
		Reatpackage reatpackage = reatpackageManager.getReatpackage(Id);
		model.addAttribute("reatpackage",reatpackage);
		return "reat/reatpackageInput";
	}

	@RequestMapping(value="/delReatpackage/{Id}")
	public String delUser(@PathVariable Long Id, HttpServletResponse response){
		reatpackageManager.deleteReatpackage(Id);
		DwzResult success = new DwzResult("200","删除成功","rel_listReatpackage","","","");
		ServletUtils.renderJson(response, success);
		return null;
	}
	
	@RequestMapping(value="/batchdelReatpackage")
	public String batchDelUser(HttpServletRequest request, HttpServletResponse response){
		String ids = request.getParameter("ids");
		String[] Ids = ids.split(",");
		boolean isSuccess = reatpackageManager.batchDelReatpackage(Ids);
		
		DwzResult success = null;
		if (isSuccess){
			success = new DwzResult("200","删除成功","rel_listReatpackage","","","");
		}
		else{
			success = new DwzResult("300","删除失败","rel_listReatpackage","","","");
		}
		ServletUtils.renderJson(response, success);
		return null;
	}
}
