package cn.com.educate.app.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import cn.com.educate.core.utils.EncodeUtils;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.rarfile.FileHeader;


/**
 * 可以处理中文文件 名
 */
public class UnZip {
	private static final int buffer = 1024;

	public static class MapComparatorNo implements
			Comparator<Map<String, Object>> {

		public int compare(Map<String, Object> o1, Map<String, Object> o2) {

			String inNo1 = o1.get("idNo").toString();
			String inNo2 = o2.get("idNo").toString();

			if (Integer.valueOf(inNo1) > Integer.valueOf(inNo2)) {
				return 1;
			} else if (Integer.valueOf(inNo1) < Integer.valueOf(inNo2)) {
				return -1;
			} else {
				return 0;
			}
		}

	}

	public static void main(String[] args) {
		// String zipfile = "C:\\zip\\20130826_0451_wangyanxia_002.zip";
		// String descDir = "C:\\flie";
		// List<Map<String,Object>> listMap = unZip(zipfile, descDir, new
		// Long(1));
		// for(Map<String,Object> m : listMap){
		// if(m.get("typeName").equals("application")){
		// System.out.println(m.get("idNo"));
		// }
		// }
	}

	public static List<Map<String, Object>> unZip(
			List<Map<String, Object>> listMap, String zipPath, String fileName,
			String savepath, Long id, String fileId) {
		int count = -1;
		int index = -1;
		String fileNameTemp = "";
		boolean flag = false;

		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());

		savepath = savepath + id+"_"+fileId + "\\";
		File userDirectory = new File(savepath);
		if (userDirectory.isFile()) {
			 userDirectory.delete();
			 System.out.println("删除文件");
		} else if (!userDirectory.exists()) {
			userDirectory.mkdir();
		}

		try {

			if ("rar".equals(fileType)){
				try {
					unrar(listMap, zipPath, savepath, id, fileId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if ("zip".equals(fileType)) {
				
				File zip = new File(zipPath);
				
				if (!zip.isFile()) {
					return listMap;
				}
				
				ZipFile zipFile = new ZipFile(zipPath);
				
				Enumeration<?> entries = zipFile.getEntries();

				while (entries.hasMoreElements()) {
					Map<String, Object> map = new HashMap<String, Object>();
					byte buf[] = new byte[buffer];

					ZipEntry entry = (ZipEntry) entries.nextElement();

					String filename = entry.getName().replace(" ", ""); ;
					index = filename.lastIndexOf("/");
					if (index > -1) {
						filename = filename.substring(index + 1);
					}
					if(filename.indexOf("_") > -1){
						String[] fileNames = filename.substring(0,
								filename.lastIndexOf(".")).split("_");
						if(fileNames[0].length() == 3){
							map.put("typeName", fileNames[0]);
							map.put("idNo", fileNames[1]);
							map.put("jksqId", id);
							map.put("uploadId", fileId);
						}else{
							map.put("typeName", "99");
							map.put("idNo", 1);
							map.put("jksqId", id);
							map.put("uploadId", fileId);
						}
					}else{
						map.put("typeName", "99");
						map.put("idNo", 1);
						map.put("jksqId", id);
						map.put("uploadId", fileId);
					}
					if(filename != null && !filename.equals("")){
						filename = EncodeUtils.getMd5PasswordEncoder(filename,fileId) + filename.substring(filename.lastIndexOf("."), filename.length());
					}
					fileNameTemp = filename;
					filename = savepath + filename;

					flag = isPics(filename);
					if (flag){
						file = new File(filename);
						file.createNewFile();

						is = zipFile.getInputStream(entry);
						fos = new FileOutputStream(file);
						bos = new BufferedOutputStream(fos, buffer);

						while ((count = is.read(buf)) > -1) {
							bos.write(buf, 0, count);
						}
						map.put("imgSrc", id+"_"+fileId + "/" + fileNameTemp);
						listMap.add(map);
						fos.flush();
						fos.close();

						is.close();
					}
					
				}
				zipFile.close();
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				byte buf[] = new byte[buffer];
				flag = isPics(fileName);
				if (flag){
					
					File zip = new File(zipPath);
					
					if (!zip.isFile()) {
						return listMap;
					}
					
					InputStream fis = new BufferedInputStream(new FileInputStream(zip));
					if(fileName.indexOf("_") > -1){
						String[] fileNames = fileName.substring(0,
								fileName.lastIndexOf(".")).split("_");
						if(fileNames[0].length() == 3){
							map.put("typeName", fileNames[0]);
							map.put("idNo", fileNames[1]);
							map.put("jksqId", id);
							map.put("uploadId", fileId);
						}else{
							map.put("typeName", "99");
							map.put("idNo", 1);
							map.put("jksqId", id);
							map.put("uploadId", fileId);
						}
					}else{
						map.put("typeName", "99");
						map.put("idNo", 1);
						map.put("jksqId", id);
						map.put("uploadId", fileId);
					}
					if(fileName != null && !fileName.equals("")){
						fileName = EncodeUtils.getMd5PasswordEncoder(fileName,fileId) + fileName.substring(fileName.lastIndexOf("."), fileName.length());
					}
					file = new File(savepath + fileName.replace(" ", ""));
					file.createNewFile();
					fos = new FileOutputStream(file);
					bos = new BufferedOutputStream(fos, buffer);

					while ((count = fis.read(buf)) > -1) {
						bos.write(buf, 0, count);
					}
					map.put("imgSrc", id+"_"+fileId + "/" + fileName);
					listMap.add(map);
					fos.flush();
					fos.close();

					fis.close();
				}
					
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		Collections.sort(listMap, new MapComparatorNo());
		return listMap;
	}

	/**
	 * 解压rar格式压缩包。
	 * 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar
	 */
	private static List<Map<String, Object>> unrar(List<Map<String, Object>> listMap, String sourceRar, String destDir, Long id, String fileId)
			throws Exception {
		boolean flag = false;
		Archive a = null;
		FileOutputStream fos = null;
		String fileNameTemp = "";
		try {
			
			File rar = new File(sourceRar);
			
			if (!rar.isFile()) {
				return listMap;
			}
			
			a = new Archive(rar);
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				if (!fh.isDirectory()) {
					Map<String, Object> map = new HashMap<String, Object>();
					// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
					String compressFileName = fh.getFileNameString().replace(" ", "");
					String destFileName = "";
					int index = compressFileName.lastIndexOf("\\");
					if (index > -1) {
						compressFileName = compressFileName.substring(index + 1);
					}
					if(compressFileName.indexOf("_") > -1){
						String[] fileNames = compressFileName.substring(0,
								compressFileName.lastIndexOf(".")).split("_");
						if(fileNames[0].length() == 3){
							map.put("typeName", fileNames[0]);
							map.put("idNo", fileNames[1]);
							map.put("jksqId", id);
							map.put("uploadId", fileId);
						}else{
							map.put("typeName", "99");
							map.put("idNo", 1);
							map.put("jksqId", id);
							map.put("uploadId", fileId);
						}
					}else{
						map.put("typeName", "99");
						map.put("idNo", 1);
						map.put("jksqId", id);
						map.put("uploadId", fileId);
					}
					if(compressFileName != null && !compressFileName.equals("")){
						compressFileName = EncodeUtils.getMd5PasswordEncoder(compressFileName,fileId) + compressFileName.substring(compressFileName.lastIndexOf("."), compressFileName.length());
					}
					fileNameTemp = compressFileName;
					flag = isPics(fileNameTemp);
					if (flag){
						destFileName = destDir + compressFileName;
						// 3解压缩文件
						fos = new FileOutputStream(new File(destFileName));
						a.extractFile(fh, fos);
						map.put("imgSrc", id+"_"+fileId + "/" + fileNameTemp);
						listMap.add(map);
						fos.flush();
						fos.close();
					}
				}
				fh = a.nextFileHeader();
			}
			a.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMap;
	}
	
	public static boolean isPics(String filename) {
		boolean flag = false;

		if (filename.endsWith(".jpg") || filename.endsWith(".gif")
				|| filename.endsWith(".bmp") || filename.endsWith(".png"))
			flag = true;

		return flag;
	}
}
