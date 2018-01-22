<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="ext/operationjs/showoperation.js" type="text/javascript"></script>
远在天边--${playFile }---${ctx }/upload/${playFile }远在天边.rmvb
<object id="video" width="400" height="200" border="0" classid="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA">
<param name="ShowDisplay" value="0">
<param name="ShowControls" value="1">
<param name="AutoStart" value="1">
<param name="AutoRewind" value="0">
<param name="PlayCount" value="0">
<param name="Appearance value="0 value=""">
<param name="BorderStyle value="0 value=""">
<param name="MovieWindowHeight" value="240">
<param name="MovieWindowWidth" value="320">
<embed width="400" height="200" border="0" showdisplay="0" showcontrols="1" playcount="0" moviewindowheight="240" moviewindowwidth="320" 
filename="${ctx }/upload/远在天边.rmvb" src="Mbar.avi">
</embed>
</object>
