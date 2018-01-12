package cn.com.educate.app.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * 拷贝hibernate相关的类，忽略null值的拷贝，同时对其中的List进行相应的处理
 * @author xjs
 * @date 2013-9-8 下午10:23:50
 */
public class HibernateAwareBeanUtilsBean extends BeanUtilsBean{
    
    private Logger log = LoggerFactory.getLogger(HibernateAwareBeanUtilsBean.class);
    
    
    private static List<String> ignoreNames;
    static{
        ignoreNames = new ArrayList<String>();
        ignoreNames.add("id");
        ignoreNames.add("createtime");
        ignoreNames.add("createuser");
        ignoreNames.add("modifytime");
        ignoreNames.add("modifyuser");
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if(value==null)return;
        if(ignoreName(name)) return;
        if(value instanceof List){
            List newList = (List)value;
            try {
                Object obj = getPropertyUtils().getSimpleProperty(dest, name);
                if(obj instanceof List){
                    //cast the dest.name to before; And oldList must contain ID 
                    List oldList = (List)obj;
                    for(int index = 0 ; index < oldList.size() ; index++ ){
                        Object old = oldList.get(index);
                        if(newList.contains(old)){
                            //findFirst and copy variables
                            Object newO = null;
                            for(int ni = 0 ; ni < newList.size() ; ni++){
                                if(old.equals(newList.get(ni))){
                                    newO = newList.get(ni);
                                    break;
                                }
                            }
                            this.copyProperties(old, newO);
                            newList.remove(newO);
                        }
                    }
                    oldList.addAll(newList); 
                }else{
                    log.error("no reson to run here! check the property name please");
                }
            } catch (NoSuchMethodException e) {
                log.error("No get method for " + dest + ": " + name);
                e.printStackTrace();
            }
            return;
        }
        super.copyProperty(dest, name, value);
    }

    
    public void addIgnoreName(String name){
        ignoreNames.add(name);
    }
    
    private boolean ignoreName(String name) {        
        return ignoreNames.contains(name);        
    }    

}