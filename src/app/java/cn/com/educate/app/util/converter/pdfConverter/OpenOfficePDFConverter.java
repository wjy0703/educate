package cn.com.educate.app.util.converter.pdfConverter;

import java.io.File;
import java.net.ConnectException;

import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import cn.com.educate.app.Constants;
import cn.com.educate.app.util.converter.utils.FileUtils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class OpenOfficePDFConverter implements PDFConverter{
	private static  OfficeManager officeManager;
	private static String OFFICE_HOME = Constants.OPENOFFICE_HOME;
	private static int port[] = {Integer.parseInt(Constants.OPENOFFICE_PORT)};
	
	public void convert2PDF(String inputFile1,String outputFile1) {   

		File inputFile = new File(inputFile1); 
		File outputFile = new File(outputFile1); 
		// connect to an OpenOffice.org instance running on port 8100 
		

			OpenOfficeConnection connection = new SocketOpenOfficeConnection(port[0]); 
			try {
				connection.connect();
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			// convert 
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection); 
			converter.convert(inputFile, outputFile); 
			// close the connection 
			connection.disconnect(); 
		
	
    }  

	public void convert2PDF(String inputFile) {
		String pdfFile = FileUtils.getFilePrefix(inputFile)+".pdf";
		convert2PDF(inputFile,pdfFile);
		
	}
	public static void startService(){
	DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
    try {
    	System.out.println("准备启动openOffice服务....");
        configuration.setOfficeHome(OFFICE_HOME);//设置OpenOffice.org安装目录
        configuration.setPortNumbers(port); //设置转换端口，默认为8100
        configuration.setTaskExecutionTimeout(1000 * 60 * 5L);//设置任务执行超时为5分钟
        configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);//设置任务队列超时为24小时
     
        officeManager = configuration.buildOfficeManager();
        officeManager.start();	//启动服务
        System.out.println("office转换服务启动成功!");
    } catch (Exception ce) {
        System.out.println("office转换服务启动失败!详细信息:" + ce);
    }
}

public static void stopService(){
      System.out.println("关闭office转换服务....");
        if (officeManager != null) {
            officeManager.stop();
        }
        System.out.println("关闭office转换成功!");
}
}
