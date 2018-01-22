<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="ext/operationjs/showoperation.js" type="text/javascript"></script>
远在天边--${playFile }---${ctx }/upload/${playFile }鸟.mkb，远在天边.rmvb
<OBJECT ID=video1 CLASSID="clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA" HEIGHT=288 WIDTH=352>
<param name="_ExtentX" value="9313">
<param name="_ExtentY" value="7620">
<param name="AUTOSTART" value="0">
<param name="SHUFFLE" value="0">
<param name="PREFETCH" value="0">
<param name="NOLABELS" value="0">
<param name="SRC" value="">
<param name="CONTROLS" value="ImageWindow">
<param name="CONSOLE" value="Clip1">
<param name="LOOP" value="0">
<param name="NUMLOOP" value="0">
<param name="CENTER" value="0">
<param name="MAINTAINASPECT" value="0">
<param name="BACKGROUNDCOLOR" value="&#35;000000">
<embed src="${ctx }/upload/${playFile }" type="audio/x-pn-realaudio-plugin" console="Clip1" controls="ImageWindow" HEIGHT="288" WIDTH="352" >
</OBJECT>
