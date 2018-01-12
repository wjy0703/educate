/**
 * @author jiangxd
 * crate at 2011-11-20
 * 使用DWZwebUI框架，返回ajax对象的封装。
 */
package cn.com.educate.core.web;

public class DwzResult {

	/**
	 * statusCode: 200 -- 成功;
	 *             300 -- 错误;
	 *             301 -- 超时;
	 */
	private String statusCode   = "";
	
	private String message      = "";
	private String navTabId     = "";
	private String rel          = "";
	private String callbackType = "";
	private String forwardUrl   = "";
	
	public DwzResult(String statusCode, String message, String navTabId,
			String rel, String callbackType, String forwardUrl) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.rel = rel;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
	}
	
	public DwzResult() {
		
	}

	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	
}
