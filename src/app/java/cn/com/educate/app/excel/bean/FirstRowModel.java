package cn.com.educate.app.excel.bean;

/**
 * 处理合并表头的辅助类
 * @author xjs
 *
 */
public class FirstRowModel {
    String name ;
    int beginColumn;
    int endColumn;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getBeginColumn() {
        return beginColumn;
    }
    
    public void setBeginColumn(int beginColumn) {
        this.beginColumn = beginColumn;
    }
    
    public int getEndColumn() {
        return endColumn;
    }
    
    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    @Override
    public String toString() {
        return "FirstRowModel [name=" + name + ", beginColumn=" + beginColumn + ", endColumn=" + endColumn + "]";
    }
    

}
