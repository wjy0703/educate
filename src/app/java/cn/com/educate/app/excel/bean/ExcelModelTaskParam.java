package cn.com.educate.app.excel.bean;

/**
 * 在Controller中调用Task方法是，需要将一些参数传递给Task，此类用来封装这些参数
 * @author lionel
 */
public class ExcelModelTaskParam {
    //保存目录
	private String dir;
    //每个sheet保存的行数
	private Integer RowsPerSheet;
    //记录ID
	private String recordNum;
    
    /** @return the dir */
    public String getDir() {
        return dir;
    }
    
    /**  @param dir the dir to set  */
    public void setDir(String dir) {
        this.dir = dir;
    }
    
    /** @return the rowsPerSheet */
    public Integer getRowsPerSheet() {
        return RowsPerSheet;
    }
    
    /**  @param rowsPerSheet the rowsPerSheet to set  */
    public void setRowsPerSheet(Integer rowsPerSheet) {
        RowsPerSheet = rowsPerSheet;
    }
    
    /** @return the fileId */
    public String getRecordNum() {
        return recordNum;
    }
    
    /**  @param fileId the fileId to set  */
    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }
    
}
