package cn.com.educate.app.excel.bean;


/**
 * 
 * @author lionel
 */
public class ExcelParamOutsideBean {

    /**
     * 保存查询条件的参数，用mapp接收
     */
  
	//bean的名字
    private String serviceName;
    //操作用户id
    private String uid;
    
    //前台传递的数据
    private String jsonData;        
    
    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;        
    }

    /** @return the uid */
    public String getUid() {
        return uid;
    }

    
    /**  @param uid the uid to set  */
    public void setUid(String uid) {
        this.uid = uid;
    }


    /** @return the serviceName */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName
     *            the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }   

}
