package cn.com.educate.app.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/*
 * Excel 导入
 */
public class ExportExcelAndImportExcel {
	
	private static Logger logger=Logger.getLogger(ExportExcelAndImportExcel.class);
	private static DiskFileItemFactory factory;
    private static ServletFileUpload upload;
    private static  final int SIZE_THRESHOLD = 10240000;
    private static  final long FILE_SIZE_MAX = 10002400000l;
    private static  final long SIZEMAX = 10002400000l;
    private static  final String HEADER_ENCODING = "UTF-8";

    static{
        factory = new DiskFileItemFactory();
        factory.setSizeThreshold(SIZE_THRESHOLD);
        upload = new ServletFileUpload(factory);        
        upload.setFileSizeMax(FILE_SIZE_MAX );
        upload.setSizeMax(SIZEMAX );
        upload.setHeaderEncoding(HEADER_ENCODING );
    }

//初始化
	public ExportExcelAndImportExcel() {
		logger.debug("Excel 导入导出");
	}
	/**批量导入读取文件 request,TXT分隔符,起始行数,结束行数,结束列数 */
	public static List<String[]> readFile(HttpServletRequest request,String flag,int startLine,int endLine,int endColumn){
		List<String[]> result = new ArrayList<String[]>();
		//String uploadDir = request.getSession().getServletContext().getRealPath(strPath)+ "/";
		//logger.info("文件上传的路径为" + uploadDir);
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//Iterator fileNames = multipartRequest.getFileNames();
		try {
			List<?> items = upload.parseRequest(request);
			FileItem item = null;
	        for (int a = 0 ;a < items.size(); a++){
	            item = (FileItem) items.get(a);
	            if (!item.isFormField() && item.getName().length() > 0) {
                	InputStream stream = item.getInputStream();
            		HSSFWorkbook work = new HSSFWorkbook(stream);
            		HSSFSheet aSheet = work.getSheetAt(0);//获得第一个sheet
            		HSSFRow aRow;
            		HSSFCell cell;
            		int rsRows = aSheet.getLastRowNum()+1;//获得所有的行
            		logger.info("rsRows = "  + rsRows);
            		int rsCell = 0;
            		if(endLine>0 && endLine<rsRows){
            			rsRows = endLine;
            		}
            		int maxcell = 0;
            		String[] temp;//定义存放数组的值
            		for (int i = startLine; i < rsRows; i++) {
            			aRow = aSheet.getRow(i);
            			rsCell = aRow.getLastCellNum();//获得所有的列数
            			if(rsCell < endColumn){
            				maxcell = rsCell;
            			}else{
            				maxcell = endColumn;
            			}
            			temp = new String[maxcell];//数组初始化
            			for (int j = 0; j < maxcell; j++) {
            				cell = aRow.getCell(j);
            				if(null != cell){
            					temp[j] = cell.getStringCellValue();//取单元格的值
            				}else{
            					temp[j] = "";
            				}
            			}
            			result.add(temp);
            		}
	            }
	        }
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("批量导入读取文件  失败：" + e.getMessage() );
		}
		logger.info("批量导入读取文件  +readFile" );
		return result;
	}
	
}
