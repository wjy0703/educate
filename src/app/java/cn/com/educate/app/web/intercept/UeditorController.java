package cn.com.educate.app.web.intercept;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.educate.app.web.InitSetupListener;

import com.baidu.ueditor.ActionEnter;


@Controller
@RequestMapping(value="/intercept/ueditor")
public class UeditorController {
	
	@RequestMapping(value="/umeditor")
	public String umeditor(HttpServletRequest request, Model model){
		String path="intercept/demo/";
		
		String flag = request.getParameter("flag");
		String saveRootPath = InitSetupListener.filePath;
		request.getSession().setAttribute("saveRootPath", saveRootPath);
		model.addAttribute("playFile", "中1.mp4");
		
		if(StringUtils.isEmpty(flag)){
			return path+"umeditor";
		}else{
			return path+"umeditor"+flag;
		}
	}
	/**
     * 百度富文本编辑器：图片上传
     * @param request
     * @param response
     */
    @RequestMapping("/uploadimage")
    public void uploadimage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
        ServletContext application= request.getSession().getServletContext();
        String rootPath = application.getRealPath( "/" );
        String saveRootPath = InitSetupListener.filePath;
        PrintWriter out = response.getWriter();
        out.write( new ActionEnter( request,saveRootPath, rootPath ).exec() );
    }
    /**
     * 显示图片
     */
    @RequestMapping("/imageLook")
    public String imageLook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newPath = InitSetupListener.filePath;
        request.getSession().setAttribute("impath", newPath);
    	return "intercept/demo/imageLook";
    }
}
