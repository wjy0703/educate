package cn.com.educate.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.educate.app.dao.NamedJdbcDao;




public class SessionInvalidation implements HttpSessionListener {
    private Logger logger = LoggerFactory.getLogger(SessionInvalidation.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
    	logger.warn("=====sessionCreated==========" + sdf.format(new Date()));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
       HttpSession session = event.getSession();
       logger.warn("=====sessionDestroyed=======" + sdf.format(new Date()));
      try{
          String login_user = (String)session.getAttribute("loginTo");
          logger.warn("=====sessionDestroyed=====login_user==" + login_user);
//          NamedJdbcDao namedJdbcDao = OperatelogManager.staticDao;
          NamedJdbcDao namedJdbcDao = (NamedJdbcDao)this.getObjectFromApplication(session.getServletContext(), "namedJdbcDao"); 
          String ip = (String)session.getAttribute("ip");
          logger.warn("=====sessionDestroyed======ip==" + ip);
          Object obj =  session.getAttribute("SPRING_SECURITY_CONTEXT");
          if(obj != null){
              SecurityContext sc =  (SecurityContext)obj;
              
              KeyHolder keyHolder = new GeneratedKeyHolder(); 
              Map param = new HashMap();
              param.put("createuser", login_user);
              param.put("ip",ip);
              param.put("remarks", "退出系统");
//            namedJdbcDao.getJdbcTemplate().update("insert into operatelog (createuser,createtime,ip,remarks) values(:createuser,sysdate(),:ip,:remarks)", param);
              MapSqlParameterSource  sps  = new MapSqlParameterSource(param);
              namedJdbcDao.getJdbcTemplate().update("insert into operatelog (createuser,createtime,ip,remarks) values(:createuser,sysdate(),:ip,:remarks)", sps,keyHolder);
              Long generatedId = keyHolder.getKey().longValue();
              

              logger.warn("=====sessionDestroyed====namedJdbcDao===generatedId===" + generatedId + "===" + sdf.format(new Date()));
          }
      }catch(Exception e){
          e.printStackTrace();
      }
    }
    
    private Object getObjectFromApplication(ServletContext servletContext,String beanName){  
        //通过WebApplicationContextUtils 得到Spring容器的实例。  
        ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        //返回Bean的实例。  
        return application.getBean(beanName);  
    } 
}    


