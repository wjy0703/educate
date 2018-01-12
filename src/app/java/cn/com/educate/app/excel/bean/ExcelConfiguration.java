package cn.com.educate.app.excel.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Administrator
 */

public class ExcelConfiguration {
    
    
    
    private String dir; /* 导出文件存放目录 */
    private Integer rowsPerSheet; /* sheet页最大记录数 */
    private Integer rowsUpperLimit; /* 允许导出最大记录数 */
    private Integer maxAllowedQuene; /* 运行最大的排队个数 */
    private Map<String, ExcelModelConfiguration> singleConfigMap; /* 保存模块对应的不同的配置信息 */

    public static Map<Integer,Object> getConfigureColumnMap(ExcelConfiguration excelConfiguration,String key){
        Map<String, ExcelModelConfiguration> singleMap = excelConfiguration.getSingleConfigMap();
        Set set = singleMap.keySet();
        ExcelModelConfiguration model = singleMap.get(key);
        String[] columns = model.getReportColumns();
        Map<Integer,Object> columnMap = new HashMap<Integer,Object>();
        for (int i = 0; i < columns.length; i++) {
            columnMap.put(i, columns[i]);
        }
        return columnMap;
    }
    
    /** @return the dir */
    public String getDir() {
        return dir;
    }

    /**
     * @param dir
     *            the dir to set
     */
    public void setDir(String dir) {
        this.dir = dir;
    }


    /** @return the maxAllowedQuene */
    public Integer getMaxAllowedQuene() {
        return maxAllowedQuene;
    }

    /**
     * @param maxAllowedQuene
     *            the maxAllowedQuene to set
     */
    public void setMaxAllowedQuene(Integer maxAllowedQuene) {
        this.maxAllowedQuene = maxAllowedQuene;
    }

    
    

    
    /** @return the rowsPerSheet */
    public Integer getRowsPerSheet() {
        return rowsPerSheet;
    }

    
    /**  @param rowsPerSheet the rowsPerSheet to set  */
    public void setRowsPerSheet(Integer rowsPerSheet) {
        this.rowsPerSheet = rowsPerSheet;
    }

    
    /** @return the rowsUpperLimit */
    public Integer getRowsUpperLimit() {
        return rowsUpperLimit;
    }

    
    /**  @param rowsUpperLimit the rowsUpperLimit to set  */
    public void setRowsUpperLimit(Integer rowsUpperLimit) {
        this.rowsUpperLimit = rowsUpperLimit;
    }

    /** @return the singleConfigMap */
    public Map<String, ExcelModelConfiguration> getSingleConfigMap() {
        return singleConfigMap;
    }

    /**
     * @param singleConfigMap
     *            the singleConfigMap to set
     */
    public void setSingleConfigMap(Map<String, ExcelModelConfiguration> singleConfigMap) {
        this.singleConfigMap = singleConfigMap;
    }

}
