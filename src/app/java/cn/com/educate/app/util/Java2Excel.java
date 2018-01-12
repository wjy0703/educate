package cn.com.educate.app.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 
 * java读取excel文件
 * 
 * 一个Excel文件的层次：Excel文件-> 工作表-> 行-> 单元格 对应到POI中，为：workbook-> sheet-> row-> cell
 * 
 */
public class Java2Excel {
	private HSSFWorkbook workbook = null;
	/**
	 * 替换并写入EXCEL内容
	 * @param inputPath 模板路径
	 * @param outPath 新文件路径
	 * @param data 传入数据
	 * data格式：1、变量替换  data.put("{a}", "1");
	 * 			2、列表插入  data.put("table", "2");//key：table；value：表格数量
					data.put("table1", "0@3");//key：table+数字(第？个表格)；value：表格参数。第?(从0开始)个sheet页@第?行开始
					data.put("table10@3", l);//key：table+数字(第？个表格) + 表格参数；value：List
					list 内容为Stirng[],第一行为 单元格列号（从0开始），以后为需要写入的内容
	 */
	public void toExcel(String inputPath, String outPath, HashMap data) {
		try {
			// 创建对Excel工作簿文件的引用
			workbook = new HSSFWorkbook(new FileInputStream(inputPath));
			//替换表格内容
			replaceValue(workbook, data);

			FileOutputStream fOut = new FileOutputStream(outPath);
			// 把相应的Excel 工作簿存盘
			workbook.write(fOut);
			fOut.flush();
			// 操作结束，关闭文件
			fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 替换数据
	 * 读取excel，遍历各个小格获取其中信息
	 * 
	 * 
	 * 注意： 1.sheet， 以0开始，以workbook.getNumberOfSheets()-1结束 2.row，
	 * 以0开始(getFirstRowNum)，以getLastRowNum结束 3.cell，
	 * 以0开始(getFirstCellNum)，以getLastCellNum结束, 结束的数目不知什么原因与显示的长度不同，可能会偏长
	 * 
	 * 
	 */
	private void replaceValue(HSSFWorkbook workbook, HashMap data){
		//获取表格Sheet页数，并遍历
		for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
			//如果Sheet页存在
			if (null != workbook.getSheetAt(numSheets)) {
				//获取Sheet页实例
				HSSFSheet aSheet = workbook.getSheetAt(numSheets);// 获得一个sheet
				//System.out.println(aSheet.getFirstRowNum());
				//遍历Sheet页内的有效行数
				for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet
						.getLastRowNum(); rowNumOfSheet++) {
					//判断当前行是否有效，若该行所有单元格都为空，则无效，（在单元格输入值，然后删掉或delete，会认为里面有值，最好整行删除）
					if (null != aSheet.getRow(rowNumOfSheet)) {
						//获取该行
						HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
						//System.out.println(aRow.getLastCellNum());
						//遍历该行有效列，判断条件同上
						for (short cellNumOfRow = 0; cellNumOfRow <= aRow
								.getLastCellNum(); cellNumOfRow++) {
							if (null != aRow.getCell(cellNumOfRow)) {
								//获取单元格实例
								HSSFCell aCell = aRow.getCell(cellNumOfRow);
								//获取单元格内容
								String value = aCell.getStringCellValue();
								//判断内容有值，且带有变量标记，且变量在map里存在
								if (StringUtils.isNotEmpty(value)
										&& value.indexOf("}") > 0
										&& data.containsKey(value)) {
									//重新赋值
									aCell.setCellValue(data.get(value) + "");
								}
							}
						}
					}
				}
			}
		}
		//判断map里是否存在表格，插入表格列表
		if (data.containsKey("table")) {
			addTable(workbook, data);
		}
		//判断map里是否存在表格，设置格式
		if (data.containsKey("cellType")) {
			resStCellStyle(workbook, data);
		}
	}
	//插入列表
	private void addTable(HSSFWorkbook workbook, HashMap data) {
		//获取表格数量
		int tableNum = Integer.parseInt(String.valueOf(data.get("table")));
		HSSFSheet aSheet;
		HSSFRow aRow;
		HSSFCell cell;
		String[] a,b,c,d;
		String cellvalue = "";
		HSSFCellStyle cellStyle;
        HSSFDataFormat format = workbook.createDataFormat();
        HSSFFont font;
		for (int tn = 1; tn <= tableNum; tn++) {
			if (data.containsKey("table" + tn)) {
				//获取表格参数  第?(从0开始)个sheet页@第?行开始
				String seetMes = String.valueOf(data.get("table" + tn));
				
				String[] sm = seetMes.split("@");
				//获取sheet页数
				int seetNum = Integer.parseInt(sm[0]);
				//获取起始行数
				int startRowNum = Integer.parseInt(sm[1]);
				//获取sheet页实例
				aSheet = workbook.getSheetAt(seetNum);
				//根据起始行数，创建一个新的行
				aRow = aSheet.createRow(startRowNum);
				//获取列表内容
				List l = (List) data.get("table" + tn + seetMes);
				//第一行为单元格参数
				a = (String[]) l.get(0);
				//从第二行开始遍历list
				for (int row = 1; row < l.size(); row++) {
					b = (String[]) l.get(row);
					//遍历  单元格参数 数组，用于创建单元格和写入值
					for (int ce = 0; ce < a.length; ce++) {
						//在当前行的某列，创建单元格
						c = a[ce].split("@");
						cell = aRow.createCell((short) Integer.parseInt(c[0]));
						//String style = c[1];
						//cellStyle.setWrapText(true); 
				        //format= workbook.createDataFormat();
						/*
						cellStyle = workbook.createCellStyle();
						if(!"v".equals(c[1])){
							this.setCellStyle(cellStyle, format, cell, c[1]);
							cell.setCellStyle(cellStyle);
						}
						*/
				        //获取要写入单元格的值
				        cellvalue = b[ce];
				        if(StringUtils.isNotEmpty(cellvalue)){
				        	//拆分，判断是否是长度为2的数组
				        	d = cellvalue.split("@");
				        	if(d.length==2){
				        		cellStyle = workbook.createCellStyle();
				        		font = workbook.createFont();
				        		//System.out.println("row==>" + row + ";;ce==>" + ce+ ";cellvalue===>" + cellvalue);
				        		//设置字体颜色
						        font.setColor(getColor(d[1]));
						        cellStyle.setFont(font);
						        //重置写入单元格的值
						        cellvalue = d[0];
						        cell.setCellStyle(cellStyle);
						        font = null;
				        	}
				        }
				        
						//写入数据
						cell.setCellValue(cellvalue);
						cellStyle = null;
						cell = null;
					}
					aRow = null;
					//起始行数+1，用于创建新行，
					startRowNum++;
					aRow = aSheet.createRow(startRowNum);
				}
				//遍历  单元格参数 数组，用于创建单元格和写入值
				for (int ce = 0; ce < a.length; ce++) {
					c = a[ce].split("@");
					cellStyle = workbook.createCellStyle();
					//cellStyle.setWrapText(true); 
			        //format= workbook.createDataFormat();
					if(!"v".equals(c[1])){
						this.setCellStyle(cellStyle, format, null, c[1]);
						aSheet.setDefaultColumnStyle((short) Integer.parseInt(c[0]), cellStyle);
					}
				}
			}
		}
	}
	
	private short getColor(String value){
		if("red".equals(value)){
			return HSSFColor.RED.index;
		}else{
			return HSSFColor.BLACK.index;
		}
	}
	private void resStCellStyle(HSSFWorkbook workbook, HashMap data){
		//获取表格数量
		int cellTypeNum = Integer.parseInt(String.valueOf(data.get("cellType")));
		HSSFSheet aSheet;
		HSSFRow aRow;
		HSSFCell cell;
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFDataFormat format= workbook.createDataFormat();
		for (int tn = 1; tn <= cellTypeNum; tn++) {
			if (data.containsKey("cellType" + tn)) {
				//获取表格参数  第?(从0开始)个sheet页@第?行开始
				String cellTypeMes = String.valueOf(data.get("cellType" + tn));
				
				String[] sm = cellTypeMes.split("@");
				//获取sheet页数
				int seetNum = Integer.parseInt(sm[0]);
				//获取起始行数
				int startRowNum = Integer.parseInt(sm[1]);
				//获取起始行数
				int startCellNum = Integer.parseInt(sm[2]);
				
				//获取sheet页实例
				aSheet = workbook.getSheetAt(seetNum);
				//根据起始行数，创建一个新的行
				aRow = aSheet.getRow(startRowNum);
				//获取列表内容
				cell = aRow.getCell((short)startCellNum);
				//this.setCellStyle(cellStyle, format, cell, sm[3]);
			}
		}
	}
	//设置单元格格式
	/*
	HSSFDataFormat的数据格式 ，内置数据类型 
编号 "General" 0 /"0" 1 /"0.00" 2 /"#,##0" 3 /"#,##0.00" 4 /"($#,##0_);($#,##0)" 5 /"($#,##0_);[Red]($#,##0)" 6 /
"($#,##0.00);($#,##0.00)" 7 /"($#,##0.00_);[Red]($#,##0.00)" 8 /"0%" 9 /"0.00%" 0xa /
"0.00E+00" 0xb /"# ?/?" 0xc /"# ??/??" 0xd /"m/d/yy" 0xe /"d-mmm-yy" 0xf /"d-mmm" 0x10 /"mmm-yy" 0x11 /"h:mm AM/PM" 0x12 /
"h:mm:ss AM/PM" 0x13 /"h:mm" 0x14 /"h:mm:ss" 0x15 /"m/d/yy h:mm" 0x16 /保留为过国际化用 0x17 - 0x24 /"(#,##0_);(#,##0)" 0x25 /
"(#,##0_);[Red](#,##0)" 0x26 /"(#,##0.00_);(#,##0.00)" 0x27 /"(#,##0.00_);[Red](#,##0.00)" 0x28 /
"_($*#,##0_);_($*(#,##0);_($* \"-\"_);_(@_)" 0x29 /"_(*#,##0.00_);_(*(#,##0.00);_(*\"-\"??_);_(@_)" 0x2a /
"_($*#,##0.00_);_($*(#,##0.00);_($*\"-\"??_);_(@_)" 0x2b /"_($*#,##0.00_);_($*(#,##0.00);_($*\"-\"??_);_(@_)" 0x2c /
"mm:ss" 0x2d /"[h]:mm:ss" 0x2e /"mm:ss.0" 0x2f /"##0.0E+0" 0x30 /"@" - This is text format 0x31 /
在上面表中，字符串类型所对应的是数据格式为"@"（最后一行），也就是HSSFDataFormat中定义的值为0x31（49）的那行。
Date类型的值的范围是0xe-0x11，本例子中的Date格式为""m/d/yy""，在HSSFDataFormat定义的值为0xe（14）。  
	 */
	private void setCellStyle(HSSFCellStyle cellStyle,HSSFDataFormat format,HSSFCell cell,String style){
		//默认文本
		//cellStyle.setDataFormat(format.getFormat("@"));
		if("v".equals(style)){
			cellStyle.setDataFormat(format.getFormat("@"));
		}else if("ymd".equals(style)){
			 cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));
		}else if("ymdh".equals(style)){
			 cellStyle.setDataFormat(format.getFormat("yyyy-m-d h:mm:ss"));
		}else if("N".equals(style)){
			 cellStyle.setDataFormat(format.getFormat("#,##0"));
		}else if("n1".equals(style)){
			 cellStyle.setDataFormat(format.getFormat("#,##0.0"));
		}else if("n2".equals(style)){
			 cellStyle.setDataFormat(format.getFormat("#,##0.00"));
		}else{
			cellStyle.setDataFormat(format.getFormat("@"));
		}
		if(null != cell){
			cell.setCellStyle(cellStyle);
		}
	}
	
	public static void main(String[] args) {
		Java2Excel je = new Java2Excel();
		HashMap data = new HashMap();
		data.put("{a}", "1");
		data.put("{b}", "2");
		data.put("{1}", "3");
		data.put("{2}", "4");
		data.put("{3}", "5");
		data.put("{4}", "6");
		data.put("table", "1");
		data.put("table1", "0@3");// 第一个seet页，第4行开始
		List l = new ArrayList();
		String[] a = new String[11];
		a[0] = "0@n2";
		a[1] = "1@v";
		a[2] = "2@v";
		a[3] = "4@v";
		a[4] = "5@v";
		a[5] = "6@v";
		a[6] = "7@v";
		a[7] = "8@v";
		a[8] = "9@N";
		a[9] = "10@n1";
		a[10] = "13@v";
		l.add(a);

		a = new String[11];
		a[0] = "10000";
		a[1] = "是否1";
		a[2] = "是否2";
		a[3] = "是否3";
		a[4] = "是否4";
		a[5] = "是否5";
		a[6] = "是否6";
		a[7] = "是否7";
		a[8] = "80000";
		// a[9]=m.get("SYHKYS")+"";
		// a[7]=m.get("HKZQ")+"";
		a[9] = "90000";
		a[10] = "是否10";
		l.add(a);
		a = new String[11];
		a[0] = "20000";
		a[1] = "是否12";
		a[2] = "是否22";
		a[3] = "是否32";
		a[4] = "是否42";
		a[5] = "是否52";
		a[6] = "是否62";
		a[7] = "是否72";
		a[8] = "820000";
		// a[9]=m.get("SYHKYS")+"";
		// a[7]=m.get("HKZQ")+"";
		a[9] = "920000";
		a[10] = "是否102";
		l.add(a);
		data.put("table10@3", l);// 第一个seet页，第4行开始
		je.toExcel("e:\\haoyilian.xls", "e:\\shengcheng.xls", data);
	}

}
