package cn.com.educate.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class JksqStateUtils {
    /**
     * 判断借款申请是否可以编辑
     *
     * @param xhjksq
     * @return 
     * @author xjs
     * @date 2013-9-16 下午4:58:22
     */
    public static boolean isCanChange(Map<String,Object> xhjksq){
        String state = xhjksq.get("STATE") != null ? xhjksq.get("STATE").toString(): "";
        String appState = xhjksq.get("APP_STATE") != null ? xhjksq.get("APP_STATE").toString(): "";
        if("2".equals(appState)){ //2代表提交变更申请后允许任何条件都可以修改
            return true;
        }else{
            int index = state.lastIndexOf(".B");
            if(index >= 0 )//包含.代表的是各种退回操作，包括外访
                return true;
            
            List<String>  canEditState =  new ArrayList<String>();
            canEditState.add("0");
            canEditState.add("22");
            canEditState.add("01");
            canEditState.add("02");
            canEditState.add("30");            
            if(canEditState.contains(state)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断是否可以修改
     *
     * @param jksq
     * @return
     * @author xjs
     * @date 2013-9-20 上午9:05:34
     */
    public static boolean isApplyChange(Map<String, Object> jksq) {
        String appState = jksq.get("APP_STATE") != null ? jksq.get("APP_STATE").toString(): "";
        if(isCanChange(jksq)){
            return false;
        }else{
            if("1".equals(appState)){
                return false;
            }else{
                return true;
            }
        }
    }
    
   
    /**
     * 
     * 根据借款类型判断是否应该评分
     * 
     * @param jkType
     * @return
     * @author xjs
     * @date 2013-9-20 上午9:06:10
     */
    public static boolean isShouldScore(String jkType){
        //C D E W分别代表了需要评分的借款类型
        return "C".equals(jkType)||"D".equals(jkType)||"E".equals(jkType)||"W".equals(jkType);
    }
    
}
