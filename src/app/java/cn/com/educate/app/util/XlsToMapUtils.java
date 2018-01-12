package cn.com.educate.app.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;

/**
 * 通过xls获取数据信息
 * @author xjs
 *
 */
public class XlsToMapUtils {
   
	  
	/**
	 * 根据xml返回记录的集合
	 *
	 * @param fileInputStream          输入流
	 * @param startOfDataRow           数据开始
	 * @param format                   映射关系
	 * @return
	 * @author xjs
	 * @date 2013-8-9 下午8:56:49
	 */
    public static List<Map<String,Object>> getRecords(InputStream fileInputStream,int startOfDataRow,Map<Integer,Object> format){
    	POIFSFileSystem fs= null;
    	Workbook wb = null;   
        try{
	    	 fs= new POIFSFileSystem(fileInputStream);   
	    	 wb = new HSSFWorkbook(fs);   
	    }catch(Exception e){
	    	e.printStackTrace();
		}
    	Sheet 	sheet = wb.getSheetAt(0);   
    	Iterator<Row> rows = sheet.rowIterator();
    	List<Map<String,Object>> changeRecords = new ArrayList<Map<String,Object>>();
    	//跳过非数据行
    	while(rows.hasNext() && startOfDataRow >0 ){
    	    rows.next();
    	    startOfDataRow--;
    	}
    	while(rows.hasNext()){
    	    Map<String,Object> record = getRecordFromRow(rows.next(),format);
    		changeRecords.add(record);
    	}
    	
    	return changeRecords;
    }
    
    /**
     * 根据xml返回记录的集合默认从第二行开始扫描
     *
     * @param fileInputStream
     * @param format
     * @return
     * @author xjs
     * @date 2013-8-9 下午8:58:10
     */
    public static List<Map<String,Object>> getRecords(InputStream fileInputStream,Map<Integer,Object> format){
         return getRecords(fileInputStream,1,format);
    }
    
    /**
     * 返回当前行表示的数据
     *
     * @param row
     * @param mapping
     * @return
     * @author xjs
     * @date 2013-8-9 下午8:58:47
     */
	private static Map<String, Object> getRecordFromRow(Row row, Map<Integer,Object> mapping) {
        Map<String, Object> record = new HashMap<String, Object>();
        for (Integer column : mapping.keySet()) {
            try {
                Cell cell = row.getCell(column);
                if (cell == null)
                    continue;
                Object value = null;
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        value = value != null ?StringUtils.trimAllWhitespace(value.toString()):null;
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        value = cell.getNumericCellValue();
                        if(getColumnName(mapping.get(column)).endsWith("DATE")){
                           value =  cell.getDateCellValue();
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = cell.getBooleanCellValue();
                        break;
                }
                record.put(getColumnName(mapping.get(column)), value);
            } catch (Exception e) {
                record.put("error", 1);
            }
        }
	    return record;
    }
	
	
	/**
	 * 取得数据列对应的名称 定义此方法
	 *
	 * @param object
	 * @return
	 * @author xjs
	 * @date 2013-8-9 下午8:06:28
	 */
    private static String getColumnName(Object object) {
       
        return object.toString();
    }
    
  /*  
    private static ChangeRecord getChangeRecordFromRow(Row row) {
        
		Cell cell = row.getCell(0);
		try{
			if(cell.getNumericCellValue()>0){
				int rowNum = (int)cell.getNumericCellValue();
				String name = row.getCell(1).getStringCellValue();
				String userNumAndLendNum = row.getCell(2).getStringCellValue();
				String lendDate = getDateValueFromCell(row,3);
				double lendAmount = 0;
				try{
					lendAmount = row.getCell(4).getNumericCellValue();
				}catch(Exception e){
					@SuppressWarnings("unused")
					String lendAmountString = row.getCell(4).getStringCellValue();
					lendAmount = stringTodouble(lendAmountString);
				}
				String lendType = row.getCell(5).getStringCellValue();
				String oldCrmName = row.getCell(6).getStringCellValue();
				String crmName = row.getCell(7).getStringCellValue();
				String ccaName = row.getCell(8).getStringCellValue();
				String changeDate = getDateValueFromCell(row,9);
				ChangeRecord changeRecord = new ChangeRecord(rowNum, name, userNumAndLendNum, lendDate, lendAmount, lendType, oldCrmName, crmName, ccaName, changeDate);
				return changeRecord;
			}
		}catch(Exception e){
		}
		return null;
	}

	private static double stringTodouble(String lendAmountString) {
		StringBuffer sb = new StringBuffer();
		for(int index = 0 ; index < lendAmountString.length() ; index++){
		   char letter = lendAmountString.charAt(index);
		   if((letter >= '0' && letter <='9') || letter == '.' || letter == '。'){
			   sb.append(letter);
		   }
		}
		return Double.parseDouble(sb.toString());
	}
    
	*//**
	 * 通过行和列号，取得日期
	 * @param row
	 * @param index
	 * @return
	 *//*
	private static String getDateValueFromCell(Row row, int index){
		String dataStr = null;
		try{
			dataStr = row.getCell(index).getStringCellValue();
		}catch(Exception e){
			@SuppressWarnings("unused")
			Date tmpLendDate= row.getCell(index).getDateCellValue();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
			dataStr = format.format(tmpLendDate);
		}
		return dataStr;
	}
    */
    
}
