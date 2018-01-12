package cn.com.educate.app.web.baseinfo;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.educate.app.entity.login.Cityinfo;
import cn.com.educate.app.service.baseinfo.CityinfoManager;
import cn.com.educate.core.web.DwzResult;
import cn.com.educate.core.web.ServletUtils;

import com.et.mvc.JsonView;

@Controller
@RequestMapping(value="/cityinfo")
public class CityinfoController {
	private CityinfoManager cityinfoManager;
	@Autowired
	public void setCityinfoManager(CityinfoManager cityinfoManager) {
		this.cityinfoManager = cityinfoManager;
	}
	/**
     * 初始化省市
     * @param request
     * @param model
     * @return
     * @author chl
     * @date 2014-1-24 上午9:29:10
     */
    @RequestMapping(value = "/listBaseCity")
    public String listBaseCity(HttpServletRequest request, Model model) {
        String treeStr = cityinfoManager.buildCityByTopId("setSelectedOrg");
        model.addAttribute("result", treeStr);
        return "baseCity/basCityIndex";
    }

    /**
     * 省市下的县区
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getEmpByTreeId/{Id}")
    public String getEmpByTreeId(@PathVariable Long Id, HttpServletRequest request, Model model) {
        List<Cityinfo> listEmp = cityinfoManager.findEmpByTree(Id);
        model.addAttribute("listEmp", listEmp);
        Cityinfo baseCity = cityinfoManager.getBaseCity(Id);
        model.addAttribute("baseCity", baseCity);
        return "baseCity/baseCityIndex";
    }

    /**
     * 添加
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/addTreeDep")
    public String addTreeDep(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        Cityinfo baseCity = cityinfoManager.getBaseCity(new Long(id));
        model.addAttribute("upBaseCity", baseCity);
        return "baseCity/baseCityInput";
    }

    /**
     * 保存城市
     * @param organi
     * @param request
     * @param response
     * @author chl
     * @date 2014-1-24 下午4:34:18
     */
    @RequestMapping(value = "/saveTree", method = RequestMethod.POST)
    public String saveTree(@ModelAttribute("baseCity") Cityinfo baseCity, HttpServletRequest request,
            HttpServletResponse response) {
        cityinfoManager.saveCityinfo(baseCity);
        DwzResult success = new DwzResult("200", "保存成功", "rel_listBaseCity", "", "closeCurrent", "");
        ServletUtils.renderJson(response, success);
        return null;
    }

    /**
     * 修改城市
     * @param model
     * @param request
     * @author chl
     * @date 2014-1-24 下午5:15:26
     */
    @RequestMapping(value = "/editTreeDep")
    public String editTreeDep(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        Cityinfo baseCity = cityinfoManager.getBaseCity(new Long(id));
        Cityinfo upBaseCity = cityinfoManager.getBaseCity(baseCity.getParent().getId());
        model.addAttribute("baseCity", baseCity);
        model.addAttribute("upBaseCity", upBaseCity);
        return "baseCity/baseCityInput";
    }

    // --------------------------------------验证 开始-----------------------------------

    @RequestMapping(value = "/chkValue")
    public String chkValue(HttpServletRequest request, HttpServletResponse response) {
        try {
            String propertyName = request.getParameter("propertyName");
            String newValue = URLDecoder.decode(request.getParameter(propertyName), "UTF-8");
            String oldValue = URLDecoder.decode(request.getParameter("oldValue"), "UTF-8");
            String errmes = URLDecoder.decode(request.getParameter("errmes"), "UTF-8");
            response.setContentType("text/html;charset=utf-8");
            if (cityinfoManager.isCheckUnique(propertyName, newValue, oldValue)) {
            } else {
                ServletUtils.renderText(response, errmes + "已经存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --------------------------------------验证 结束-----------------------------------

    /**
     * 删除城市
     * @param request
     * @param response
     * @author chl
     * @date 2014-1-26 下午12:55:09
     */
    @RequestMapping(value = "/delDep")
    public String delDep(HttpServletRequest request, HttpServletResponse response) {
        String json = "";
        String id = request.getParameter("id");
        boolean flag = cityinfoManager.delDep(id);
        try {
            if (flag) {
                json = new JsonView("success:true,id:" + id).toString();
            } else {
                json = new JsonView("success:false,msg:该市下有县区，不允许删除！").toString();
            }
            response.setContentType("text/json; charset=utf-8");
            response.getWriter().println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
