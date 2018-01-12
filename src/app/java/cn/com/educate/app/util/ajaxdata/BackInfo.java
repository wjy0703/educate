package cn.com.educate.app.util.ajaxdata;

import java.util.List;

/**
 * 返回前台的数据格式
 *
 * @author xjs
 * @date 2013-8-15 下午4:40:21
 */
public class BackInfo {

    private int count; //个数，通常是成功处理个数用于批量处理中 
    List<ErrorInfosBean> errors; //错误数据个数

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ErrorInfosBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorInfosBean> errors) {
        this.errors = errors;
    }

}