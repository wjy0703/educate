package cn.com.educate.app.util.converter.docConverter;

import cn.com.educate.app.util.converter.pdfConverter.PDFConverter;
import cn.com.educate.app.util.converter.swfConverter.SWFConverter;
import cn.com.educate.app.util.converter.utils.FileUtils;

public class DocConverter {

	private PDFConverter pdfConverter;
	private SWFConverter swfConverter;
	
	
	public DocConverter(PDFConverter pdfConverter, SWFConverter swfConverter) {
		super();
		this.pdfConverter = pdfConverter;
		this.swfConverter = swfConverter;
	}


	public  void convert(String inputFile,String swfFile){
		this.pdfConverter.convert2PDF(inputFile);
		String pdfFile = FileUtils.getFilePrefix(inputFile)+".pdf";
		this.swfConverter.convert2SWF(pdfFile, swfFile);
	}
	
	public void convert(String inputFile){
		this.pdfConverter.convert2PDF(inputFile);
		String pdfFile = FileUtils.getFilePrefix(inputFile)+".pdf";
		this.swfConverter.convert2SWF(pdfFile);
		
	}
	
	public void convertPdf(String inputFile){
		this.pdfConverter.convert2PDF(inputFile);
		
		
	}	
}
