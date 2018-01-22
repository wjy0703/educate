<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="ext/operationjs/showoperation.js" type="text/javascript"></script>
远在天边--${playFile }---${ctx }/upload/${playFile }
<object id="NSPlay" width=200 height=180 classid="CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab&#35;Version=6,4,5,715" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject" align="right" hspace="5">
<!-- ASX File Name -->
<param name="AutoRewind" value=1>
<!-- Display Controls -->
<param name="ShowControls" value="1">
<!-- Display Position Controls -->
<param name="ShowPositionControls" value="0">
<!-- Display Audio Controls -->
<param name="ShowAudioControls" value="1">
<!-- Display Tracker Controls -->
<param name="ShowTracker" value="0">
<!-- Show Display -->
<param name="ShowDisplay" value="0">
<!-- Display Status Bar -->
<param name="ShowStatusBar" value="0">
<!-- Diplay Go To Bar -->
<param name="ShowGotoBar" value="0">
<!-- Display Controls -->
<param name="ShowCaptioning" value="0">
<!-- Player Autostart -->
<param name="AutoStart" value=1>
<!-- Animation at Start -->
<param name="Volume" value="-2500">
<param name="AnimationAtStart" value="0">
<!-- Transparent at Start -->
<param name="TransparentAtStart" value="0">
<!-- Do not allow a change in display size -->
<param name="AllowChangeDisplaySize" value="0">
<!-- Do not allow scanning -->
<param name="AllowScan" value="0">
<!-- Do not show contect menu on right mouse click -->
<param name="EnableContextMenu" value="0">
<!-- Do not allow playback toggling on mouse click -->
<param name="ClickToPlay" value="0">
</object>
最简单的播放代码远在天边.rmvb
<embed src="${ctx }/upload/鸟.mkb"
loop="true" width="200" height="150" >
