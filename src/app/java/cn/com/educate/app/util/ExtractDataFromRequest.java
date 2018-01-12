package cn.com.educate.app.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 通过request获得上传的数据
 *
 * @author xjs
 * @date 2013-8-10 下午1:05:41
 */
public class ExtractDataFromRequest {
    
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
  

    /**
     * 根据请求返回记录的集合
     *
     * @param request                  请求
     * @param startOfDataRow           数据开始
     * @param format                   映射关系
     * @return
     * @author xjs
     * @date 2013-8-10 下午1:06:16
     */
    public static  List<Map<String, Object>> extractData(HttpServletRequest request ,int startOfDataRow,Map<Integer, Object> format){
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
        try {
            List<?> items = upload.parseRequest(request);
            FileItem item = null;
            for (int i = 0 ;i < items.size(); i++){
                item = (FileItem) items.get(i);
                if (!item.isFormField() && item.getName().length() > 0) {
                    InputStream stream = item.getInputStream();
                    datas.addAll(XlsToMapUtils.getRecords(stream, startOfDataRow, format));
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 根据请求返回记录的集合                        数据从第一行开始
     *
     * @param request                  请求
     * @param format                   映射关系
     * @return
     * @author xjs
     * @date 2013-8-10 下午1:06:16
     */
    public static List<Map<String, Object>> extractData(HttpServletRequest request, Map<Integer, Object> format) {
        return extractData(request, 1,format);
    }
}
