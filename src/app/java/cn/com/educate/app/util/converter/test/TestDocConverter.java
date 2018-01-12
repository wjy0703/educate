package cn.com.educate.app.util.converter.test;

import cn.com.educate.app.util.converter.docConverter.DocConverter;
//import cn.com.educate.app.util.converter.pdfConverter.JComPDFConverter;
import cn.com.educate.app.util.converter.pdfConverter.JacobPDFConverter;
import cn.com.educate.app.util.converter.pdfConverter.OpenOfficePDFConverter;
import cn.com.educate.app.util.converter.pdfConverter.PDFConverter;
import cn.com.educate.app.util.converter.swfConverter.SWFConverter;
import cn.com.educate.app.util.converter.swfConverter.SWFToolsSWFConverter;

public class TestDocConverter {
	public static void main(String[]args){
		PDFConverter pdfConverter = new OpenOfficePDFConverter();
		//PDFConverter pdfConverter = new JacobPDFConverter();
		//PDFConverter pdfConverter = new JComPDFConverter();
		SWFConverter swfConverter = new SWFToolsSWFConverter();
		DocConverter converter = new DocConverter(pdfConverter,swfConverter);
		String txtFile = "D:\\test\\txtTest.txt";
		String docFile = "D:\\test\\a.doc";
		String xlsFile = "D:\\test\\xlsTest.xlsx";
		String pptFile = "D:\\test\\pptTest.pptx";
		//converter.convert(txtFile);
		converter.convert(docFile);
		//converter.convert(xlsFile);
		//converter.convert(pptFile);
	}
}
