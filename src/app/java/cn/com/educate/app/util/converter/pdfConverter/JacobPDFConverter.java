package cn.com.educate.app.util.converter.pdfConverter;

import java.io.File;

import cn.com.educate.app.util.converter.utils.FileUtils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class JacobPDFConverter implements PDFConverter {
	private static final int wdFormatPDF = 17;
	private static final int xlTypePDF = 0;
	private static final int ppSaveAsPDF = 32;
	private static final int msoTrue = -1;
	private static final int msofalse = 0;
	
	public void convert2PDF(String inputFile, String pdfFile) {
		String suffix = FileUtils.getFileSufix(inputFile);
		File file = new File(inputFile);
		if(!file.exists()){
			System.out.println("�ļ������ڣ�");
			return;
		}
		if(suffix.equals("pdf")){
			System.out.println("PDF not need to convert!");
			return ;
		}
		if(suffix.equals("doc")||suffix.equals("docx")||suffix.equals("txt")){
			word2PDF(inputFile,pdfFile);
		}else if(suffix.equals("ppt")||suffix.equals("pptx")){
			ppt2PDF(inputFile,pdfFile);
		}else if(suffix.equals("xls")||suffix.equals("xlsx")){
			excel2PDF(inputFile,pdfFile);
		}else{
			System.out.println("�ļ���ʽ��֧��ת��!");
		}
	}

	public void convert2PDF(String inputFile) {
		String pdfFile = FileUtils.getFilePrefix(inputFile)+".pdf";
		convert2PDF(inputFile,pdfFile);
		
	}
	
	public static void word2PDF(String inputFile,String pdfFile){
		//��wordӦ�ó���
		ActiveXComponent app = new ActiveXComponent("Word.Application");
		//����word���ɼ�
		app.setProperty("Visible", false);
		//���word�����д򿪵��ĵ�,����Documents����
		Dispatch docs = app.getProperty("Documents").toDispatch();
		//����Documents������Open�������ĵ��������ش򿪵��ĵ�����Document
		Dispatch doc = Dispatch.call(docs,
									"Open",
									inputFile,
									false,
									true
									).toDispatch();
		//����Document�����SaveAs���������ĵ�����Ϊpdf��ʽ
		
		Dispatch.call(doc,
					"SaveAs",
					pdfFile,
					wdFormatPDF		//word����Ϊpdf��ʽ�ֵ꣬Ϊ17
					);
			/*		
		Dispatch.call(doc,
				"ExportAsFixedFormat",
				pdfFile,
				wdFormatPDF		//word����Ϊpdf��ʽ�ֵ꣬Ϊ17
				);
				*/
		//�ر��ĵ�
		Dispatch.call(doc, "Close",false);
		//�ر�wordӦ�ó���
		app.invoke("Quit", 0);
		
	}
	public static void excel2PDF(String inputFile,String pdfFile){
		ActiveXComponent app = new ActiveXComponent("Excel.Application");
		app.setProperty("Visible", false);
		Dispatch excels = app.getProperty("Workbooks").toDispatch();
		Dispatch excel = Dispatch.call(excels,
									"Open",
									inputFile,
									false,
									true
									).toDispatch();
		Dispatch.call(excel,
					"ExportAsFixedFormat",
					xlTypePDF,		
					pdfFile
					);
		Dispatch.call(excel, "Close",false);
		app.invoke("Quit");
		
		
	}
	public static void ppt2PDF(String inputFile,String pdfFile){
		
		ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
		//app.setProperty("Visible", msofalse);
		Dispatch ppts = app.getProperty("Presentations").toDispatch();
		
		Dispatch ppt = Dispatch.call(ppts,
									"Open",
									inputFile,
									true,//ReadOnly
									true,//Untitledָ���ļ��Ƿ��б���
									false//WithWindowָ���ļ��Ƿ�ɼ�
									).toDispatch();
		
		Dispatch.call(ppt,
					"SaveAs",
					pdfFile,
					ppSaveAsPDF	
					);
				
		Dispatch.call(ppt, "Close");
		
		app.invoke("Quit");
		
	}

}
