package cn.com.educate.app.excel.utils;


import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class GenerateSpecialXLS {

    
   
    private Workbook wb;
    private Map<String, CellStyle> styles;
    private int rowNum;
    private int rowPrevious;
    private Sheet sheet;
    
   
    
    public GenerateSpecialXLS(String sheetName){  
        wb = new HSSFWorkbook();        
        styles = createStyles(wb);
        rowNum = 0;
        sheet = wb.createSheet(sheetName);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
    }
    
    
     private static String[][] data_one = {
        
        {"进件地区", null,"申请产品", null,"申请金额",null,"申请期限",null},
        {"客户来源",null, "借款目的",",D,H"},
        {"身份信息,A,H,style:header"},
        {"客户姓名",null,null,null,"身份号码",",F,H"},
        {"户籍地",",B,C",null,null,"婚姻状况",",F,H"}
    };
    
    
    public  void createHeader(){
        rowPrevious = rowNum;
        Row titleRow = sheet.createRow(rowNum++);         
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("审批意见书");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));
    }
    
    public void createBaseInfo(String[][] data) {
        rowPrevious = rowNum;
        List<String> colSpans = new ArrayList();
        Row row;
        for(int index = 0; index < data.length ; index++){
            row = sheet.createRow(rowNum);
            String[] rowsName = data[index];
            String colSpan = "";
            colSpans.clear();
            for(int c = 0 ; c < rowsName.length ; c++){
                String value = rowsName[c];
                
                Cell cell = row.createCell(c);
                if(value != null && value.indexOf(",") >= 0){
                    colSpan = getColSpan(value,rowNum);
                    cell.setCellValue(value.split(",")[0]);
                    if(StringUtils.hasText(colSpan))
                        colSpans.add(colSpan);
                }else{
                    cell.setCellValue(value);
                }
                if(value != null && value.indexOf("style:") >= 0)                
                    cell.setCellStyle(styles.get(value.split("style:")[1]));
                else
                    cell.setCellStyle(styles.get("cell"));
            }
            for(int c = rowsName.length ; c<8;c++){
                Cell cell = row.createCell(c);
                cell.setCellStyle(styles.get("cell"));
            }
            for(String mergeCol:colSpans){             
                sheet.addMergedRegion(CellRangeAddress.valueOf(mergeCol));
            }
            rowNum++;
        }
    }

    private static String getColSpan(String value, int rowNum) {
        return "$" + value.split(",")[1] + "$" + (rowNum+1) +":$" +value.split(",")[2] +"$" +  (rowNum+1);
    }

    public static void main(String[] args) throws Exception {
       
        GenerateSpecialXLS specialXLS = new GenerateSpecialXLS("审批表");
        
        //审核表头部信息
        specialXLS.createHeader();
        
        specialXLS.createBaseInfo(data_one);
        
        specialXLS.setStyle();
         // Write the output to a file
        specialXLS.saveFile("D:\\timesheet2.xls");
         
    }
    
    
    public void saveFile(String file) {
        BufferedOutputStream out = null;
        try{
            if(wb instanceof XSSFWorkbook) file += "x";
            out = new BufferedOutputStream(new FileOutputStream(file));
            wb.write(out);
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void setStyle(){
        for(int index = 0 ; index < 8 ; index ++)
            sheet.setColumnWidth(index, 15*256); //30 characters wide
    }
    
   

    /**
      * Create a library of cell styles
      */
     private static Map<String, CellStyle> createStyles(Workbook wb){
         Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
         CellStyle style;
         Font titleFont = wb.createFont();
         titleFont.setFontHeightInPoints((short)18);
         titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
         style = wb.createCellStyle();
         style.setAlignment(CellStyle.ALIGN_CENTER);
         style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
         style.setFont(titleFont);
         styles.put("title", style);

         Font monthFont = wb.createFont();
         monthFont.setFontHeightInPoints((short)11);
         monthFont.setColor(IndexedColors.WHITE.getIndex());
         style = wb.createCellStyle();
         style.setAlignment(CellStyle.ALIGN_CENTER);
         style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
         style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
         style.setFillPattern(CellStyle.SOLID_FOREGROUND);
         style.setFont(monthFont);
         style.setWrapText(true);
         styles.put("header", style);

         style = wb.createCellStyle();
         style.setAlignment(CellStyle.ALIGN_CENTER);
         style.setWrapText(true);
         style.setBorderRight(CellStyle.BORDER_THIN);
         style.setRightBorderColor(IndexedColors.BLACK.getIndex());
         style.setBorderLeft(CellStyle.BORDER_THIN);
         style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
         style.setBorderTop(CellStyle.BORDER_THIN);
         style.setTopBorderColor(IndexedColors.BLACK.getIndex());
         style.setBorderBottom(CellStyle.BORDER_THIN);
         style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
         styles.put("cell", style);

         style = wb.createCellStyle();
         style.setAlignment(CellStyle.ALIGN_CENTER);
         style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
         style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
         style.setFillPattern(CellStyle.SOLID_FOREGROUND);
         style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
         styles.put("formula", style);

         style = wb.createCellStyle();
         style.setAlignment(CellStyle.ALIGN_CENTER);
         style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
         style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
         style.setFillPattern(CellStyle.SOLID_FOREGROUND);
         style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
         styles.put("formula_2", style);

         return styles;
     }
     
     /**
      * 得到当前处理的行数
      *
      * @return
      * @author xjs
      * @date 2013-9-20 下午11:04:22
      */
     public int getRowNum(){
         return rowNum;
     }
     
     /**
      * 
      * 得到对应的cell
      * @param row
      * @param col
      * @return
      * @author xjs
      * @date 2013-9-20 下午11:04:43
      */
     public Cell getCell(int row,int col){
         Row rowObject = sheet.getRow(row);
         return  rowObject.getCell(col);
     }

    public int getRowPrevious() {
        // TODO Auto-generated method stub
        return rowPrevious;
    }
}
