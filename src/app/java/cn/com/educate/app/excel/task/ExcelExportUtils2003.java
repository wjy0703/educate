package cn.com.educate.app.excel.task;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import org.slf4j.Logger;

import cn.com.educate.app.excel.bean.ExcelExportEntity;
import cn.com.educate.app.excel.bean.ExcelModelConfiguration;
import cn.com.educate.app.excel.bean.ExcelModelTaskParam;
import cn.com.educate.app.excel.bean.ExcelParamOutsideBean;
import cn.com.educate.app.excel.bean.FirstRowModel;

public class ExcelExportUtils2003 {
	
	private ExcelModelTaskParam excelModelTaskParam;
	private ExcelModelConfiguration excelModelConfiguration;
	private Logger log = null;
	private ExcelParamOutsideBean excelParamOutsideBean;
	
	
	
	public ExcelExportUtils2003(ExcelModelTaskParam excelModelTaskParam,
			ExcelModelConfiguration excelModelConfiguration,
			ExcelParamOutsideBean excelParamOutsideBean) {
		super();
		this.excelModelTaskParam = excelModelTaskParam;
		this.excelModelConfiguration = excelModelConfiguration;
		this.excelParamOutsideBean = excelParamOutsideBean;
	}

	public void exportExcelToFile(ExcelExportEntity result,HttpServletResponse response) {
	        
		   
		    // 导出
	        BufferedOutputStream bos = null;
	        InputStream is = null;
	        HSSFWorkbook workbook = null;
	        HSSFSheet sheet = null;
	        HSSFCellStyle style = null;
	        HSSFFont font = null;
	        HSSFRow row = null;
	        HSSFCell cell = null;
	        
	        String filePath = null;
	        Integer rowUpperLimit = 50000;
	             
	        
	        try {
	            
	            String fullPath = excelModelTaskParam.getDir()
	                    + DateFormatUtils.format(new Date(), "yyyyMMdd") + File.separator;
	            String fileName = excelModelConfiguration.getTitle()
	                    + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")
	                    + ".xls";
	            filePath = fullPath + fileName;
	            // 创建根文件夹，并判断是否存在
	            File file = new File(fullPath);
	            if (!file.exists())
	                file.mkdirs();
	            
	            List<Map<String, Object>> rows = result.getRows();
	            int total = result.getTotal();
	            // 创建新的Excel 工作簿
	            int size = rows.size();
//	            stateTrans(rows, stateColumns); TODO
	            workbook = new HSSFWorkbook();
	            Map<String, CellStyle> styles = createStyles(workbook); 
	            
	            Integer rowsPerSheet = excelModelTaskParam.getRowsPerSheet();
	            // 计算sheet数量
	            int sheetCount = (size / rowsPerSheet)
	                    + (size % rowsPerSheet == 0 ? 0 : 1);
	            sheetCount = size == 0 ? 1:sheetCount;
	            // 生成一个样式
	            style = workbook.createCellStyle();
	            font = workbook.createFont();
	            setStyle(style, font, 0);

	            // 把字体应用到当前的样式
	            style.setFont(font);
	            String[] meta = excelModelConfiguration.getReportMetas();
	            String[] column = excelModelConfiguration.getReportColumns();
	            String[] columnWidth = excelModelConfiguration.getReportColumnsWidth();
	            List<FirstRowModel> headerRows = excelModelConfiguration.getHeaderRows();
	            
	            // 分sheet导出
	            for (int i = 0; i < sheetCount; i++) {                
	                sheet = workbook.createSheet(excelModelConfiguration.getTitle() + i);  
	                // 当前sheet的数据量
	                int sheetSize = sheetCount == i + 1 ? size - i
	                        * rowsPerSheet : rowsPerSheet;
	                // 标题设置
	                // 产生表格标题行
	                int increment = 1; //设置的是标题行的个数
	                if(headerRows != null){
	                    createFirstHeader(sheet,headerRows);
	                    createNormalHeader(sheet,meta,headerRows,1);
	                    increment = 2;
	                }else{
	                    createSingleNormalHeader(sheet,meta,style,0); 
	                }
	                int jr  = 0;
	                for ( ; jr < sheetSize; jr++) {
	                    row = sheet.createRow(jr + increment);// 内容从1行起++
	                    Map<String, Object> map = rows.get(i*rowsPerSheet+jr);

	                    for (int k = 0; k < column.length; k++) {
	                        cell = row.createCell(k);
	                        Object value = map.get(column[k]);
	                        if( value instanceof BigDecimal){
	                        	BigDecimal dvalue = (BigDecimal)value;
	                        	cell.setCellValue(dvalue.doubleValue());
	                        	cell.setCellStyle(styles.get("leftAlign"));
	                        }else{
	                        	cell.setCellValue(addZero(map.get(column[k])));
	                        }
	                    }
	                }
	                if(size==0){
	                    row = sheet.createRow(jr + increment);
	                    cell = row.createCell(0);
	                    cell.setCellValue("没有符合条件的数据可以导出");                    
	                }else if(i == sheetCount-1 && total > rowUpperLimit){
	                    row = sheet.createRow(jr + increment);
	                    cell = row.createCell(0);
	                    cell.setCellValue("最大导出记录数为"+rowUpperLimit+"条，超出部分数据未导出");
	                }
	                
	                for(int columnNum =0;columnNum < columnWidth.length;columnNum++){
	                    sheet.setColumnWidth(columnNum, 256*(Integer.parseInt(columnWidth[columnNum])));
	                }
	             
	            }
	           
	            bos = new BufferedOutputStream(new FileOutputStream(filePath));
	            workbook.write(bos);
	            bos.flush();
	            //bos.close(); 
	            response.setContentType("application/xls");   
                response.setHeader("Content-Disposition", "attachment; filename=" +  java.net.URLEncoder.encode(fileName, "UTF-8"));
	            is = new FileInputStream(filePath);
	            IOUtils.copy(is, response.getOutputStream());
	            File fileTemp = new File(filePath);
	            fileTemp.delete();
	        } catch (Exception e) {
	            log.error("导出时出现异常，操作人：+" + excelParamOutsideBean.getUid(), e);
	        } finally {
	            try {
	                if (bos != null) {
	                    bos.flush();
	                    bos.close();
	                    is.close();
	                }
	            } catch (IOException e) {
	                log.error("导出时关闭IO流异常，操作人：+" + excelParamOutsideBean.getUid(), e);
	            }
	        }
	    }
	 
	  private static String addZero(Object obj) {
	        if (obj == null)
	            return "";
	        String object = obj.toString();
	        if (object == null)
	            return null;
	        if (object.indexOf(".") == 0) 
	            object = 0 + object;
	        if (object.indexOf("-.") != -1) 
	            object = "-0." + object.replaceAll("-.", "");
	        return object;
	    }
	  
	  private static void setStyle(HSSFCellStyle style, HSSFFont font, int flag) {
	        
	        short scolor = flag == 1 ? HSSFColor.WHITE.index
	                : HSSFColor.LIGHT_CORNFLOWER_BLUE.index;
	        short fcolor = flag == 1 ? HSSFColor.RED.index : HSSFColor.VIOLET.index;
	        // short length =
	        // flag==1?HSSFCellStyle.ALIGN_CENTER:HSSFCellStyle.ALIGN_RIGHT;
	    
	        // 设置这些样式
	        style.setFillForegroundColor(scolor);
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    
	        // 生成一个字体
	        font.setColor(fcolor);
	        font.setFontHeightInPoints((short) 12);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    }
	  private  void createFirstHeader(HSSFSheet sheet, List<FirstRowModel> headerRows) {
	        Map<String, CellStyle>  styles = createStyles(sheet.getWorkbook());
	        HSSFRow row = null;
	        HSSFCell cell = null;
	        row = sheet.createRow(0);  
	        int first = 0;
	        boolean flag = true;//控制单元格的显示背景色
	        for(FirstRowModel model : headerRows){
	            int startColumn = model.getBeginColumn();
	            int endColumn = model.getEndColumn();
	            //String columnName = model.getName();
	            for(int blank = first;blank<startColumn;blank++){
	                cell = row.createCell(blank);
	            }
	            cell = row.createCell(startColumn);
	            cell.setCellValue(model.getName());            
	            sheet.addMergedRegion(new CellRangeAddress(0,0,startColumn,endColumn));
	            CellStyle style = null;
	            if(flag){
	                flag = false;
	                style = styles.get("even");
	            }else{
	                style = styles.get("odd");
	                flag = true;
	            }
	            cell.setCellStyle(style);
	            first = endColumn + 1;            
	        }
	        
	    }
	    /**
	     * 创建保护两个头行的首行
	     *
	     * @param sheet
	     * @param columns
	     * @param headerRows
	     * @param rowNum
	     * @author lionel
	     */
	    private  void createNormalHeader(HSSFSheet sheet, String[] columns, List<FirstRowModel> headerRows, int rowNum) {
	        Map<String, CellStyle>  styles = createStyles(sheet.getWorkbook());
	        HSSFRow row = null;
	        HSSFCell cell = null;
	        row = sheet.createRow(rowNum);
	        String[] cellS = new String[columns.length];
	        for(int index = 0 ; index<cellS.length ; index++){
	            cellS[index] = "nocolor";
	        }
	        boolean flag = true;
	        //设置每个单元格的样式
	        for(FirstRowModel model : headerRows){
	           int startColumn = model.getBeginColumn();
	           int endColumn =  model.getEndColumn();
	           String style = null;
	           if(flag){
	               flag = false;
	               style = "subeven";
	           }else{
	               flag = true;
	               style = "subodd";
	           }
	           
	           for(int index = startColumn;index <=endColumn;index++){
	               cellS[index] = style;
	           }
	            
	        }        
	        
	        for (int j = 0; j < columns.length; j++) {
	            cell = row.createCell(j);            
	            cell.setCellValue(columns[j]);   
	            cell.setCellStyle(styles.get(cellS[j]));
	        }
	    }
	    /**
	     * 
	     *
	     * @param sheet
	     * @param columns
	     * @param headerRows
	     * @param rowNum 就是零，其实不用传值
	     * @author lionel
	     */
	    private  void createSingleNormalHeader(HSSFSheet sheet, String[] meta, HSSFCellStyle style, int rowNum) {
	        HSSFRow row = null;
	        HSSFCell cell = null;
	        row = sheet.createRow(rowNum);        
	        // int[] columnWidth = new int[meta.length];
	        for (int j = 0; j < meta.length; j++) {
	            cell = row.createCell(j);
	            cell.setCellStyle(style);
	            cell.setCellValue(meta[j]);                   
	        }
	    }
	    
	    private  Map<String, CellStyle> createStyles(Workbook wb){
	        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	        
	        Font font = wb.createFont();
	        font.setFontHeightInPoints((short)15);
	        font.setColor(IndexedColors.BLUE.getIndex());
	        
	        Font subfont = wb.createFont();
	        subfont.setFontHeightInPoints((short)15);
	        subfont.setColor(IndexedColors.DARK_BLUE.getIndex());
	        
	        CellStyle style;       
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setFont(font);
	        styles.put("even", style);
	        
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(IndexedColors.PINK.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setFont(font);
	        styles.put("odd", style);
	        
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor((short)(IndexedColors.BRIGHT_GREEN.getIndex()-1));
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setFont(subfont);
	        styles.put("subeven", style);
	        
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor((short)(IndexedColors.PINK.getIndex()-1));
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setFont(subfont);
	        styles.put("subodd", style);
	        
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFont(subfont);
	        styles.put("nocolor", style); 
	        
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_LEFT);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        //style.setFont(subfont);
	        styles.put("leftAlign", style); 
	        
	        return styles;
	    }
}
