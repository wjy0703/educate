package cn.com.educate.app.web.login;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.educate.app.entity.login.Menutable;
import cn.com.educate.app.service.baseinfo.MenutableManager;


@Controller
@RequestMapping(value="/baseinfo")
public class MenuController {

	private Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenutableManager menutableManager;
	

	
	/**
	 * 获取顶级菜单 
	 * @return
	 */
	@RequestMapping(value = "/topmenus", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> getTopMenus(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Menutable> list = menutableManager.getMenusByLevel(new Integer(1));
		modelMap.put("total", list.size());
		logger.debug(list.size()+"");
		modelMap.put("data", list);
		modelMap.put("success", "true");
		return modelMap;
    }

	/**
	 * 根据顶级菜单查找其下所有的子菜单，并且按照上下级层属关系进行排序。
	 * @param Id 顶级菜单ID
	 * @param request
	 * @return 使用json格式返回菜单的html片段
	 */
	@RequestMapping(value = "/loadmenu/{Id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMenuStr(@PathVariable Long Id, HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Menutable> menus = new LinkedList<Menutable>();
		menutableManager.buildMenuByTopId(menus, Id);
		menus = menutableManager.buildMenuByTopId(menus);
		if(menus.size() == 0){
			menutableManager.buildMenuByTopId(menus, Long.valueOf("139"));
		}
		StringBuffer sb = new StringBuffer();
		int last_level = 0;
		String ctx = request.getContextPath();
		for (Menutable menu: menus){
			if(menu.getLevelid()==2 && last_level != 3){
				sb.append("<div class=\"accordionHeader\">");  
				sb.append("<h2><span>Folder</span>");
				sb.append(menu.getMenuname());
				sb.append("</h2>");
				sb.append("</div>");
			}
			if(menu.getLevelid()==3 && last_level==2){
				sb.append("<div class=\"accordionContent\">");
				sb.append("<ul class=\"tree treeFolder\">");
			}
			if(menu.getLevelid()==3){
				sb.append("<li><a "+StringUtils.replace(menu.getAttrMenuUrl(), "_CTX_", ctx)+menu.getAttrTarget()+menu.getAttrRel()+menu.getAttrTitle()+menu.getAttrExternal()+menu.getAttrFresh()+">"+menu.getMenuname()+"</a>");
				List<Menutable> sMenu = menutableManager.getMenusByLevel(4);
				sMenu = menutableManager.buildMenuByTopId(sMenu);
				if(sMenu.size() != 0){
					for(Menutable m : sMenu){
						if(m.getParent().getId() == menu.getId()){
							sb.append("<ul>");
							sb.append("<li><a "+StringUtils.replace(m.getAttrMenuUrl(), "_CTX_", ctx)+m.getAttrTarget()+m.getAttrRel()+m.getAttrTitle()+m.getAttrExternal()+m.getAttrFresh()+">"+m.getMenuname()+"</a></li>");
							sb.append("</ul>");
						}
					}
				}
				sb.append("</li>");
			}
			
			if(menu.getLevelid()==2 && last_level==3){
				sb.append("</ul>");
				sb.append("</div>");
				sb.append("<div class=\"accordionHeader\">");  
				sb.append("<h2><span>Folder</span>");
				sb.append(menu.getMenuname());
				sb.append("</h2>");
				sb.append("</div>");

			}
			last_level = Integer.parseInt(menu.getLevelid()+"");
		}
        modelMap.put("data", sb.toString());
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
		List<Menutable> menus = new LinkedList<Menutable>();
		List<Menutable> menus4 = new LinkedList<Menutable>();
		menutableManager.buildMenuByTopId(menus, Id);
		menus = menutableManager.buildMenuByTopId(menus);
		if(menus.size() == 0){
			menutableManager.buildMenuByTopId(menus, Long.valueOf("139"));
		}
		List<Menutable> sMenu = menutableManager.getMenusByLevel(4);
		menus4 = menutableManager.buildMenuByTopId(sMenu);
		model.addAttribute("menus", menus);
		model.addAttribute("sMenu", menus4);
		return "customer/menu";
	}
	
	/**
	 * 根据菜单的parent查询parent所属直接下一级菜单
	 * @param id 父级菜单ID
	 * @return
	 */
	@RequestMapping(value = "/menusbyparent", method = RequestMethod.GET)
	@ResponseBody
    public Map<String, Object> getMenus(@RequestParam(value="id",required=false) String id){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Menutable> list = menutableManager.getMenusByParent(Long.valueOf(id));
		modelMap.put("total", list.size());
		logger.debug(list.size()+"");
		modelMap.put("data", list);
		modelMap.put("success", "true");
		return modelMap;
    }
		
}
