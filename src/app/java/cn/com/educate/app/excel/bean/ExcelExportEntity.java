package cn.com.educate.app.excel.bean;


import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author lionel
 */
public class ExcelExportEntity {
    
	//查询的记录
    private List<Map<String, Object>> rows;
    //记录总个数
    private int total;

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }
}

