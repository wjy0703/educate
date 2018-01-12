package cn.com.educate.app.web.baseinfo;

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
import org.springframework.web.servlet.ModelAndView;

import cn.com.educate.app.entity.login.Businessinfo;
import cn.com.educate.app.entity.login.Organizeinfo;
import cn.com.educate.app.entity.security.Userinfo;
import cn.com.educate.app.service.baseinfo.OrganizeinfoManager;
import cn.com.educate.app.service.security.OperatorDetails;
import cn.com.educate.core.orm.Page;
import cn.com.educate.core.security.springsecurity.SpringSecurityUtils;
import cn.com.educate.core.web.DwzResult;
import cn.com.educate.core.web.ServletUtils;

@Controller
@RequestMapping(value="/organize")
public class OrganizeinfoController {
	private OrganizeinfoManager organizeinfoManager;
	@Autowired
	public void setOrganizeinfoManager(OrganizeinfoManager organizeinfoManager) {
		this.organizeinfoManager = organizeinfoManager;
	}
	/**
	 * 初始化树并获取机构下人员
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/initTree")
	public String initTree(HttpServletRequest request, Model model){
	    String treeStr = organizeinfoManager.buildOrganiByTopId("setSelectedOrg");
	    model.addAttribute("result",treeStr);
//	    List<Userinfo> listEmp = organizeinfoManager.findEmpByTree(Id);
//        model.addAttribute("listEmp", listEmp);
//        Organizeinfo organi = organizeinfoManager.getOrgani(Id);
//        model.addAttribute("organi", organi);
		return "customer/departmentIndex";
	}
	/**
     * 初始化树并获取机构下人员M
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value="/getUserByTreeId/{Id}")
    public String getUserByTreeId(@PathVariable Long Id, HttpServletRequest request, Model model){
        List<Userinfo> listUser = organizeinfoManager.findUserByTree(Id);
        model.addAttribute("listUser", listUser);
        Organizeinfo organi = organizeinfoManager.getOrgani(Id);
        model.addAttribute("organi", organi);
        return "customer/depEmpIndex";
    }
	
	/**
	 * 添加组织机构  2013-08-07
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addTreeDep")
	public String addTreeDep(Model model, HttpServletRequest request){
		String id = request.getParameter("id");
		Organizeinfo organi = organizeinfoManager.getOrgani(new Long(id));
		model.addAttribute("upOrgani", organi);
//		model.addAttribute("organiDes", organizeinfoManager.getOrganiDes(organi.getOrgname(), organi.getOrgflag()));
		return "customer/departmentInput";
	}
	
	/**
	 * 调整组织机构  2013-08-07
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/editTreeDep")
	public String editTreeDep(Model model, HttpServletRequest request){
		String id = request.getParameter("id");
		Organizeinfo organi = organizeinfoManager.getOrgani(new Long(id));
		Organizeinfo upOrgani = organizeinfoManager.getOrgani(organi.getParentid());
		model.addAttribute("organi", organi);
		model.addAttribute("upOrgani", upOrgani);
//		model.addAttribute("organiDes", organi.getOrganiDes());
		return "customer/departmentInput";
	}
	
	/**
	 * 保存组织机构  2013-08-07
	 * @param organi
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/saveTree",method=RequestMethod.POST)
	public String saveTree(@ModelAttribute("organi") Organizeinfo organi, HttpServletRequest request, HttpServletResponse response){
	    String parentId = request.getParameter("orgLookup.id");//原来是在页面给的值  现在在后台获取父id 并保存
	    String busid = request.getParameter("orgLookup.busid");//原来是在页面给的值  现在在后台获取父id 并保存
        if(parentId != null){
            organi.setParentid(Long.parseLong(parentId)); 
            Businessinfo bus = new Businessinfo();
            bus.setId(Long.parseLong(busid));
            organi.setBusinessinfo(bus);
//            organi.setBusid(Long.parseLong(busid));
        }
       organizeinfoManager.saveOrgani(organi);
	   DwzResult success = new DwzResult("200","保存成功","rel_initTree","","closeCurrent","");
	   ServletUtils.renderJson(response, success);
	   return null;
	}
	
	@RequestMapping(value = "/getTreeDept")
	public String getTreeDept(HttpServletRequest request, Model model) {
		OperatorDetails operator = (OperatorDetails)SpringSecurityUtils.getCurrentUser();
		model.addAttribute("result",organizeinfoManager.buildOrganiByType());
		model.addAttribute("operator",operator);
		return "customer/treeLookup";
	}
	
	@RequestMapping(value="/listOrganizeinfo")
	public String listOrganizeinfo(HttpServletRequest request, Model model){
		// 处理分页的参数
		Page<Organizeinfo> page = new Page<Organizeinfo>();
		String pageSize = request.getParameter("numPerPage");
		if (StringUtils.isNotBlank(pageSize)){
			page.setPageSize(Integer.valueOf(pageSize));
		}
		String pageNo = request.getParameter("pageNum");
		if(StringUtils.isNotBlank(pageNo)){
			page.setPageNo(Integer.valueOf(pageNo));
		}
		String orderBy = request.getParameter("orderField");
		if(StringUtils.isNotBlank(orderBy)){
			page.setOrderBy(orderBy);
		}
		String order = request.getParameter("orderDirection");
		if(StringUtils.isNotBlank(order)){
			page.setOrder(order);
		}
		
		Map<String, Object> map = ServletUtils.getParametersStartingWith2(request, "filter_");		
		
		organizeinfoManager.searchOrganizeinfo(page, map);
	
		model.addAttribute("page", page);
		model.addAttribute("map", map);
		return "folder/organizeinfoIndex";
		
	}
	
	@RequestMapping(value="/saveOrganizeinfo",method=RequestMethod.POST)
	public String saveOrganizeinfo(@ModelAttribute("organizeinfo") Organizeinfo organizeinfo, HttpServletRequest request, HttpServletResponse response){
		
		organizeinfoManager.saveOrganizeinfo(organizeinfo);

		DwzResult success = new DwzResult("200","保存成功","rel_listOrganizeinfo","","closeCurrent","");
		ServletUtils.renderJson(response, success);
		
		return null;
	}
	
	@RequestMapping(value="/addOrganizeinfo", method=RequestMethod.GET)
	public ModelAndView addOrganizeinfo(){
		return new ModelAndView("folder/organizeinfoInput", "organizeinfo", new Organizeinfo());
	}
	
	@RequestMapping(value="/editOrganizeinfo/{Id}", method=RequestMethod.GET)
	public ModelAndView editOrganizeinfo(@PathVariable Long Id){
		Organizeinfo organizeinfo = organizeinfoManager.getOrganizeinfo(Id);
		return new ModelAndView("folder/organizeinfoInput", "organizeinfo", organizeinfo);
	}

	@RequestMapping(value="/delOrganizeinfo/{Id}")
	public String delUser(@PathVariable Long Id, HttpServletResponse response){
		organizeinfoManager.deleteOrganizeinfo(Id);
		DwzResult success = new DwzResult("200","删除成功","rel_listOrganizeinfo","","","");
		ServletUtils.renderJson(response, success);
		return null;
	}
	
	@RequestMapping(value="/batchdelOrganizeinfo")
	public String batchDelUser(HttpServletRequest request, HttpServletResponse response){
		String ids = request.getParameter("ids");
		String[] Ids = ids.split(",");
		boolean isSuccess = organizeinfoManager.batchDelOrganizeinfo(Ids);
		
		DwzResult success = null;
		if (isSuccess){
			success = new DwzResult("200","删除成功","rel_listOrganizeinfo","","","");
		}
		else{
			success = new DwzResult("300","删除失败","rel_listOrganizeinfo","","","");
		}
		ServletUtils.renderJson(response, success);
		return null;
	}
	
	
}
