package cn.com.educate.app.util.ajaxdata;

/**
 * 数据信息
 *
 * @author xjs
 * @date 2013-8-15 下午4:41:16
 */
public class ErrorInfosBean {

    private String id;              //信息唯一标志
    private String errorMsg;        //信息描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}