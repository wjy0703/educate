package cn.com.educate.app.entity.security;

/**
 * 组织机构的实体类
 *
 * @author xjs
 * @date 2013-11-26 下午6:41:29
 */
public class SimpleDepartment implements java.io.Serializable{

    private long id;
    
    private String code ;
    
    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

    
    public String getCode() {
        return code;
    }

    
    public void setCode(String code) {
        this.code = code;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    private String name ;
    
    private String parentName;


    
    public String getParentName() {
        return parentName;
    }


    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    
}
