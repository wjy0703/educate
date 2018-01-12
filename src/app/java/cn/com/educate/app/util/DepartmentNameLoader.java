package cn.com.educate.app.util;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.educate.app.entity.security.SimpleDepartment;

import com.google.common.cache.CacheLoader;

/**
 * 
 * 
 * @author xjs
 * @date 2013-11-18 下午10:42:28
 */
@Component
public class DepartmentNameLoader extends CacheLoader<Long,String> {

	@Autowired
	DepartmentManager departmentManager; 
	

	/**
	 * 根据机构获得子机构，包括子机构的子机构
	 * 
	 * author: xjs 
	 */
	@Override
	public String load(Long id) throws Exception {
	    
	    List<SimpleDepartment> borrows = departmentManager.getDepartmentByType(DepartmentManager.TYPE.BORROW);
	    if(borrows != null)
    	    for(SimpleDepartment dept  : borrows){
    	         if(id == dept.getId()){
    	             return dept.getName();
    	         }else{
    	             List<SimpleDepartment> sons = departmentManager.getDepartmentByParent(dept.getId());
    	             for(SimpleDepartment son  : sons){
    	                if(son.getId() == id){
    	                    return dept.getName();
    	                }
    	             }
    	         }
    	        
    	    }
	    List<SimpleDepartment> lends = departmentManager.getDepartmentByType(DepartmentManager.TYPE.LEND);
	    if(lends != null)
    	    for(SimpleDepartment dept  : lends){
                if(id == dept.getId()){
                    return dept.getName();
                }else{
                    List<SimpleDepartment> sons = departmentManager.getDepartmentByParent(dept.getId());
                    for(SimpleDepartment son  : sons){
                       if(son.getId() == id){
                           return dept.getName();
                       }
                    }
                }    
           }
	    
        return "";
	}
	
}
