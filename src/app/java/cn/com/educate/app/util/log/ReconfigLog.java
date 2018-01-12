package cn.com.educate.app.util.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;



/**
 *
 * @author lionel
 * @date 2012-6-18 上午10:13:56
 */
public class ReconfigLog {
      
  
    
    
      
      /**
       * 动态改变输出日志的级别
       *
       * @param name  需要改变的类的名称，可以用正则表达式表示，例如com.test.MyDAO的级别，name可以为com.*或者com.test.*
       * @param lev 级别字符串，可取值 debug,info,error,fatal,warn
       * @author lionel
       * @date 2012-6-18 上午10:14:42
       */
      public static void changeLevel(String name,String lev){
          Level level = Level.toLevel(lev);
          @SuppressWarnings("unchecked")
          Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
          while(allLoggers.hasMoreElements()){
                Logger logger = allLoggers.nextElement();
                Pattern pattern = Pattern.compile(name);
                Matcher matcher = pattern.matcher(logger.getName());   
                if(matcher.matches()){
                    logger.setLevel(level);
                }
          }
      }
      /**
       * 动态改变根级的log4j级别     
       * @param lev 级别字符串，可取值 debug,info,error,fatal,warn
       * @author lionel
       * @date 2012-6-18 上午10:20:41
       */
      public static void changeRootLevel(String lev){
          Logger logger = LogManager.getRootLogger();
          Level level = Level.toLevel(lev);
          logger.setLevel(level);
      }
      
      
       /**
       * 改变不满足name的规则的log日志信息.
       * 
       * @param name 需要改变的类的名称，可以用正则表达式表示，例如com.test.MyDAO的级别，name可以为com.*或者com.test.*
       * @param lev 级别字符串，可取值 debug,info,error,fatal,warn
       * @author lionel
       * @date 2012-7-17 下午3:20:07
       */
      public static void changeRevserseLevel(String name,String lev){
          Level level = Level.toLevel(lev);
          @SuppressWarnings("unchecked")
          Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
          while(allLoggers.hasMoreElements()){
                Logger logger = allLoggers.nextElement();
                Pattern pattern = Pattern.compile(name);
                Matcher matcher = pattern.matcher(logger.getName());   
                if(!matcher.matches()){
                    logger.setLevel(level);
                }
          }
      }
      
      /**
       * 查找所有满足match指明条件的日志级别，match==null时，返回所有的日志级别
       *
       * @param match
       * @return
       * @author lionel
       * @date 2012-7-19 上午9:47:25
       */
      public static List<SingleLogInfo> getLogInfos(String level){
        List<SingleLogInfo> list = new ArrayList<SingleLogInfo>();
    
        if("ROOT".equals(level))
            return getRootLogInfos();
        @SuppressWarnings("unchecked")
        Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
        while (allLoggers.hasMoreElements()) {
            Logger logger = allLoggers.nextElement();
            if (logger.getLevel() != null) {
                if(logger.getLevel().toString().equalsIgnoreCase(level)){
                    SingleLogInfo info = new SingleLogInfo();
                    info.setName(logger.getName());
                    info.setLevel(logger.getLevel().toString());
                    list.add(info);
                }
            }
        }
        Collections.sort(list, new SortLogComparator());
        return list;
      }
      
      /**
       * 取得所有的logger
       *
       * @return
       * @author xjs
       * @date 2013-8-13 上午10:52:51
       */
      public static List<SingleLogInfo> getRootLogInfos(){
        List<SingleLogInfo> list = new ArrayList<SingleLogInfo>();
        Logger root = LogManager.getRootLogger();
        
        String rootLevel = "";
        if(root.getLevel() != null ) rootLevel = root.getLevel().toString();
        @SuppressWarnings("unchecked")
        Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
        while (allLoggers.hasMoreElements()) {
            Logger logger = allLoggers.nextElement();           
                SingleLogInfo info = new SingleLogInfo();
                info.setName(logger.getName());
                info.setLevel(logger.getLevel() != null ?logger.getLevel().toString() : rootLevel);
                list.add(info);
        }
        Collections.sort(list, new SortLogComparator());
        return list;        
      }
      
      /**
       * 查找所有满足指定名字
       *
       * @param match
       * @return
       * @author lionel
       * @date 2012-7-19 上午9:47:25
       */
      public static List<SingleLogInfo> getLogInfosByName(String name){
        List<SingleLogInfo> list = new ArrayList<SingleLogInfo>();
       Logger root = LogManager.getRootLogger();
        
        String rootLevel = "";
        if(root.getLevel() != null ) rootLevel = root.getLevel().toString();
        if(!StringUtils.hasText(name))
            return getRootLogInfos();
        @SuppressWarnings("unchecked")
        Enumeration<Logger> allLoggers = LogManager.getCurrentLoggers();
        while (allLoggers.hasMoreElements()) {
            Logger logger = allLoggers.nextElement();
            Pattern pattern = Pattern.compile(name);
            Matcher matcher = pattern.matcher(logger.getName());
            if (matcher.matches()) {
                SingleLogInfo info = new SingleLogInfo();
                info.setName(logger.getName());
                info.setLevel(logger.getLevel() != null ? logger.getLevel().toString() : rootLevel);
                list.add(info);
            }
        }
        Collections.sort(list, new SortLogComparator());
        return list;
      }
      
}
