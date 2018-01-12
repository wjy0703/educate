package cn.com.educate.app.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;

import cn.com.educate.app.web.InitSetupListener;


public class FileUtil {

	public static String saveWebImgFile(MultipartFile imgFile){
		String webFilePath = "";
		if(imgFile.getSize() > 0){
			FileOutputStream fos = null;
			try {
				byte[] b = imgFile.getBytes();
				/* 构造文件路径 */
				String webRoot = "C:\\webUpload";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String datePath = sdf.format(new Date());
				String dirPath = webRoot + "/img/" + datePath;
				File dir = new File(dirPath);
				if(!dir.exists()){
					dir.mkdirs();
				}
				String fileName = imgFile.getOriginalFilename();
				String fileFormat = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
				String uuid = UUID.randomUUID().toString();
				webFilePath = dirPath+"/"+uuid+"."+fileFormat;
				File file = new File(webFilePath);
				
				dir.setWritable(true);
				file.setWritable(true);
				
				fos = new FileOutputStream(file);
				fos.write(b);
				
			} catch (IOException e) {
				throw new RuntimeException("文件上传失败！" ,e);
			}finally{
				if(fos != null){
					try {
						fos.close();
					} catch (IOException e) {
						throw new RuntimeException("文件上传->输出流关闭失败！！！！" ,e);
					}
				}
			}
		}
		
		return webFilePath;
	}
	
	
	public static String retAudit(String value){
		String result = "";
		if(null != value && !"".equals(value)){
			if("0".equals(value)){
				result="保存";
			}
			if("1".equals(value)){
				result="提交";
			}
			if("2".equals(value)){
				result="审批通过";
			}
			if("3".equals(value)){
				result="审批不通过";
			}
		}
		return result;
	}
	//1自购。0统购
	public static String retOrder(String value){
		String result = "统购";
		if(null != value && !"".equals(value)){
			if("0".equals(value)){
				result="统购";
			}
			if("1".equals(value)){
				result="自购";
			}
		}
		return result;
	}
	/**
	 * 
	 * @param downLoadPath 文件路径+文件名
	 * @param fileName 用户下载显示文件名
	 * @param request
	 * @param response
	 */
	public static void downLoad(String downLoadPath,String fileName, HttpServletRequest request,
			HttpServletResponse response){
	    File fileExist = new File(downLoadPath);
	    if(!fileExist.exists()){
	            downLoadPath = InitSetupListener.backDirectory + downLoadPath.substring(1);
	    }
	    
		try {
			java.io.BufferedInputStream bis = null;
			java.io.BufferedOutputStream bos = null;
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			// System.out.println(downLoadPath);
			try {
				long fileLength = new File(downLoadPath).length();
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader(
						"Content-disposition",
						"attachment; filename="
						// + new String(fileName.getBytes("UTF-8"), "GB2312"));
								+ URLEncoder.encode(
										fileName, "utf-8"));
				response.setHeader("Content-Length", String.valueOf(fileLength));
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[1024];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				// e.printStackTrace();
			} finally {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回盘符  --- 如文件不存在返回  D 或者 Z 代表存在哪个盘里面
     * 功能:压缩多个文件成一个zip文件
     * <p>作者 陈亚标 Jul 16, 2010 10:59:40 AM
     * @param srcfile：源文件列表
     * @param zipfile：压缩后的文件
     */
    public static String zipFiles(File[] srcfile,File zipfile){
        String destinationDirectory = "";
        if (srcfile != null){
            if(srcfile.length >= 1){
                File src = srcfile[0];
                if(!src.exists()){
                    destinationDirectory = InitSetupListener.backDirectory;
                    File[] tempFile = new File[srcfile.length];
                    for(int i=0;i<srcfile.length;i++){
                        tempFile[i] = new File(destinationDirectory + srcfile[i].getName().substring(1));                        
                    }
                    srcfile = tempFile;
                    //目标文件不改生成位置
//                    File tempZipFile = new File(destinationDirectory + zipfile.getName().substring(1));
//                    zipfile = tempZipFile;
                }
            }
        }else{
            return "nothing"; //
        }
        byte[] buf=new byte[1024];
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
            for(int i=0;i<srcfile.length;i++){
                FileInputStream in=new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                //out.putNextEntry(new ZipEntry(URLEncoder.encode(srcfile[i].getName(), "utf-8")));
                //out.setEncoding("utf-8");
                int len;
                while((len=in.read(buf))>0){
                    out.write(buf,0,len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return destinationDirectory;
    }
    
    /**
     * 功能:解压缩
     * <p>作者 陈亚标 Jul 16, 2010 12:42:20 PM
     * @param zipfile：需要解压缩的文件
     * @param descDir：解压后的目标目录
     */
    public static void unZipFiles(File zipfile,String descDir){
        try {
            ZipFile zf=new ZipFile(zipfile);
            for(Enumeration entries=zf.entries();entries.hasMoreElements();){
                ZipEntry entry=(ZipEntry) entries.nextElement();
                String zipEntryName=entry.getName();
                InputStream in=zf.getInputStream(entry);
                OutputStream out=new FileOutputStream(descDir+zipEntryName);
                byte[] buf1=new byte[1024];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
                System.out.println("解压缩完成.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
}
