<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page
	import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page
	import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page
	import="org.springframework.security.core.AuthenticationException"%>

<%
	String loginInfo = "";
	String error = request.getParameter("error");
	if (error != null) {
		if (error.equals("1")) {
			loginInfo = "用户名或密码错误，请重新输入！";
		} else if (error.equals("3")) {
			loginInfo = "您已成功退出系统！";
		} else if (error.equals("4")) {
			loginInfo = "验证码错误，请重新输入！";
		} else if (error.equals("5")) {
			loginInfo = "登录超时，请重新登录！";
		}
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Educate管理平台</title>
<link href="${ctx}/resources/front.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resources/jquery.js" type="text/javascript"></script>
<script src="${ctx}/resources/front.js" type="text/javascript"></script>
<script src="${ctx}/themes/js/qrcode.js" type="text/javascript"></script>
<script src="${ctx}/themes/js/popup.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/regist.css" />

<script type="text/javascript">
	function clearcookie() {
		
	}
</script>
<script type="text/javascript">
	$(function() {
		$("#jvForm").validate();
	});
	function refresh(obj) {
		obj.src = "${ctx}/baseinfo/getImg?" + Math.random();
	}
	function submit() {

	}
</script>
</head>

<body	onload="return clearcookie() ;">
	<div class="container">
		<div class="toolbar box">
			<div class="toolbar-inner">

				<div class="fr">
				<a id="backTop" href="${ctx}/intercept/authority/listauth" onmouseover="showDiv('/intercept/authority/listauth',event);"
				onMouseOut="closeDiv()">返回首页</a>|183.128.152.168<sen:ipAddress value="183.128.152.168"/>|
				<a href="${ctx}/intercept/authority/playvideo" target="_blank">播放测试</a>|
				<a href="${ctx}/intercept/authority/playvideo?playFlag=rm" target="_blank">avi播放测试</a>|
					<a href="#"
						onClick="this.style.behavior='url(#default#homepage)';this.setHomePage('http://210.51.3.64/CHP');">设为主页</a>|
					<a
						onClick="window.external.addFavorite('http://210.51.3.64/CHP','Educate管理平台')"
						href="javascript:void(0);">加入收藏</a>
				<div id="Idiv" style="display:none; position:absolute; z-index:1000; background:#67a3d9;">
<div id="mou_head" style="width:100px; height:100px;">
<div id="qrcode"></div>
</div>
				</div>
				</div>
			</div>
		</div>
		<div class="main">
			<div class="header box">
				<div class="brand">
					<h1>
						<a href=""${ctx}/">
	<script type="text/javascript">
							
						</script>
	</a>
	</h1>
	</div>
	</div>

	<form method="post" id="jvForm" action="${ctx}/j_spring_security_check">
		<table width="800" border="0" align="center" cellpadding="0"
			cellspacing="5">
			<tr>
				<td height="30" align="left">

					<div style="color: red"></div>

				</td>
			</tr>
		</table>
		<table width="900" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="500" height="300" align="left"><img
					src="${ctx}/resources/llogo_abk.gif" /></td>
				<td>
					<div class="login-bg">
						<table width="96%" border="0" align="center" cellpadding="0"
							cellspacing="5">
							<tr>
								<td height="40" colspan="3"><img
									src="${ctx}/resources/login_title.gif" /></td>
							</tr>
							<tr>
								<td width="67" height="30" align="right">用户名：</td>
								<td colspan="2"><input type="text" id="j_username"
									name="j_username" class="input required" value="admin" /></td>
							</tr>
							<tr>
								<td height="30" align="right">密 码：</td>
								<td colspan="2"><input type="password" id="j_password"
									name="j_password" class="input required" value="123456" /></td>
							</tr>
							<tr>
								<td height="30" align="right">验证码：</td>
								<td colspan="2"><input type="text" id="j_captcha"
									name="j_captcha" class="input required" value="" /><img
									title="看不清，点击更换" onclick="javascript:refresh(this);"
									src="${ctx}/baseinfo/getImg" /></td>
							</tr>
							<tr>
								<td height="30" align="right">&nbsp;</td>
								<!--  <td colspan="2"><img src="${base}/captcha.svl" onclick="this.src='${base}/captcha.svl?d='+new Date()*1" width="100" height="35"/></td>-->
							</tr>
							<tr>
								<td height="40" colspan="3" align="center"><input
									type="submit" value=" 登 录 " class="login-button" />&nbsp;&nbsp;&nbsp;<a
									href="javascript:void(0);" class="forgot-password"
									onclick="alert('找回密码请联系系统管理员');">忘记密码？</a></td>
							</tr>
							<tr>
								<td height="30" colspan="3" align="center"
									style="font-size: 12px; color: #FF0000;"><%=loginInfo%></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>

		</table>

	</form>
	</div>

	<div class="footer">
		<div class="clearfix footer-inner">
			<p class="extralink">建议使用基于IE内核的浏览器,1024*768以上的显示分辨率</p>
			<p class="copyright">Copyright &copy; 2017-2027
				www.educate.cn, All Rights Reserved</p>
			<p class="extrainfo">
				<br />
			</p>
		</div>
	</div>
	</div>
</body>
</html>