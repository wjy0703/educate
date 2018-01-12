package cn.com.educate.app.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 通过xls获取到更改记录信息
 * @author xjs
 *
 */
public class ChangeRecordXlsUtils {
	  public static void main(String[] args) throws Exception{
	    	List<ChangeRecord> changeRecords = getChangeRecords(new FileInputStream("F:\\变更\\建外财富中西离职员工交割变更确认书20130609.xls"));
	    	for(ChangeRecord changeRecord :changeRecords){
	    		System.out.println(changeRecord);
	    	}
	    	System.out.println(changeRecords.size());
	    	System.out.println("****************88");
		}
    public static List<ChangeRecord> getChangeRecords(InputStream fileInputStream){
    	POIFSFileSystem fs= null;
    	HSSFWorkbook wb = null;   
        try{
	    	 fs= new POIFSFileSystem(fileInputStream);   
	    	 wb = new HSSFWorkbook(fs);   
	    }catch(Exception e){
	    	e.printStackTrace();
		}
    	HSSFSheet 	sheet = wb.getSheetAt(0);   
    	Iterator<Row> rows = sheet.rowIterator();
    	List<ChangeRecord> changeRecords = new ArrayList();
    	while(rows.hasNext()){
    		ChangeRecord changeRecord = getChangeRecordFromRow(rows.next());
    		if(changeRecord != null){
    			changeRecords.add(changeRecord);
    		}
    	}
    	return changeRecords;
    }

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
    
	/**
	 * 通过行和列号，取得日期
	 * @param row
	 * @param index
	 * @return
	 */
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
    
    
}
