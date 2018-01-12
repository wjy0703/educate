package cn.com.educate.app.excel.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * 设置excel导出的格式，主要是列的顺序，主要用到了前三个成员变量，其他的为辅助变量，方便配置
 * @author lionel
 */
public class ExcelModelConfiguration {
    
    private String title;
    private String[] reportMetas;
    private String[] reportColumns;
    private String[] reportColumnsWidth;
    //以下三个为配置信息
    private String reportColumnsWidthStr;
    private String reportMetaStr;
    private String reportMetalColumnStr;
    private List<FirstRowModel> headerRows = null;     
    
    
    
    public String[] getReportColumnsWidth() {
        return reportColumnsWidth;
    }

    
    public void setReportColumnsWidth(String[] reportColumnsWidth) {
        this.reportColumnsWidth = reportColumnsWidth;
    }
    
    

    
    public String getReportColumnsWidthStr() {
        return reportColumnsWidthStr;
    }


    
    public void setReportColumnsWidthStr(String reportColumnsWidthStr) {
        String[] temp = reportColumnsWidthStr.split(",");
        this.setReportColumnsWidth(temp);
        this.reportColumnsWidthStr = reportColumnsWidthStr;
    }


    /** @return the title */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the reportMetas */
    public String[] getReportMetas() {
        return reportMetas;
    }

    /**
     * @param reportMetas
     *            the reportMetas to set
     */
    public void setReportMetas(String[] reportMetas) {
        this.reportMetas = reportMetas;
    }

    /** @return the reportColumns */
    public String[] getReportColumns() {
        return reportColumns;
    }

    /**
     * @param reportColumns
     *            the reportColumns to set
     */
    public void setReportColumns(String[] reportColumns) {
        this.reportColumns = reportColumns;
    }
    
    
    /**
     * @param reportMetaStr
     *            the reportMetaStr to set
     */
    public void setReportMetaStr(String reportMetaStr) {
        List<FirstRowModel> headerRows = null;        
        if (reportMetaStr.indexOf('[') != -1) {
            headerRows = new ArrayList<FirstRowModel>();
            StringBuffer sb = new StringBuffer(reportMetaStr);
            for (int i = 0; i < sb.length(); i++) {                
                if (sb.charAt(i) == '[') {
                    int match = sb.indexOf("]", i);
                    String middle = sb.substring(i + 1, match);
                    int spanColumn = countOfColon(middle) + 1;
                    sb.delete(match, match + 1);
                    String previousStr = sb.substring(0, i);
                    int startColumn = countOfColon(previousStr);
                    FirstRowModel model = new FirstRowModel();
                    int previousPos = previousStr.lastIndexOf(",");
                    sb.delete(previousPos + 1, i + 1);
                    String name = previousStr.substring(previousPos + 1);
                    model.setBeginColumn(startColumn);
                    model.setEndColumn(startColumn + spanColumn - 1);
                    model.setName(name);
                    headerRows.add(model);
                    i = i - name.length() - 1;      
                }
            }
           reportMetaStr = sb.toString();          
        }        
        this.setHeaderRows(headerRows);
        String[] reportMetas = reportMetaStr.split(",");
        this.setReportMetas(reportMetas);
        this.reportMetaStr = reportMetaStr;
    }

    /**
     * @param reportMetalColumnStr
     *            the reportMetalColumnStr to set
     */
    public void setReportMetalColumnStr(String reportMetalColumnStr) {
        reportMetalColumnStr = reportMetalColumnStr.toUpperCase();
        String[] reportColumns = reportMetalColumnStr.split(",");
        this.setReportColumns(reportColumns);
        this.reportMetalColumnStr = reportMetalColumnStr;
    }

    /** @return the reportMetaStr */
    public String getReportMetaStr() {
        return reportMetaStr;
    }

    /** @return the reportMetalColumnStr */
    public String getReportMetalColumnStr() {
        return reportMetalColumnStr;
    }
 
    public List<FirstRowModel> getHeaderRows() {
        return headerRows;
    }
    
    public void setHeaderRows(List<FirstRowModel> headerRows) {
        this.headerRows = headerRows;
    }
    private int countOfColon(String content) {
        int count = 0;
        for (int i = 0; i < content.length(); i++) {
            if (',' == content.charAt(i))
                count++;
        }
        return count;
    }
    
}
