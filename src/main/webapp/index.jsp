<%@ page import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils"%>
<%@ page import="cn.com.educate.app.service.security.OperatorDetails"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />

<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>

<title>Educate管理平台</title>

<link href="${ctx}/themes/default/style.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/themes/css/chpCore.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/uploadify/css/uploadify.css" rel="stylesheet"
	type="text/css" />
	<link rel="stylesheet" href="${ctx }/ext/resources/css/ext-all.css"
	type="text/css"></link>
<script type="text/javascript" src="${ctx }/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx }/ext/ext-all.js"></script>
<script type="text/javascript" src="${ctx }/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx }/ext/department.js"></script>
<script type="text/javascript" src="${ctx}/dwz/common/cardNumber.js"></script>
<!--[if IE]>
<link href="${ctx}/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->
<style type="text/css">
#header {
	height: 85px
}

#leftside,#container,#splitBar,#splitBarProxy {
	top: 90px
}
</style>
<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="${ctx}/dwz/chart/raphael.js"></script>
<script type="text/javascript" src="${ctx}/dwz/chart/g.raphael.js"></script>
<script type="text/javascript" src="${ctx}/dwz/chart/g.bar.js"></script>
<script type="text/javascript" src="${ctx}/dwz/chart/g.line.js"></script>
<script type="text/javascript" src="${ctx}/dwz/chart/g.pie.js"></script>
<script type="text/javascript" src="${ctx}/dwz/chart/g.dot.js"></script>
<script src="${ctx}/dwz/speedup.js" type="text/javascript"></script>
<script src="${ctx}/dwz/jquery-1.7.2.js" type="text/javascript"></script>
<script src="${ctx}/dwz/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/dwz/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/dwz/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.core.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.util.date.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.validate.method.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.barDrag.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.drag.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.tree.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.accordion.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.ui.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.theme.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.navTab.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.tab.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.resize.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.dialog.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.cssTable.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.stable.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.taskBar.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.ajax.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.pagination.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.database.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.datepicker.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.effects.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.panel.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.checkbox.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.history.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.combox.js" type="text/javascript"></script>
<script src="${ctx}/dwz/dwz.print.js" type="text/javascript"></script>
<!--
<script src="${ctx}/dwz/dwz.min.js" type="text/javascript"></script>
-->
<script src="${ctx}/dwz/dwz.regional.zh.js" type="text/javascript"></script>
<!-- 报表 -->
<script type="text/javascript" src="${ctx}/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/highcharts/exporting.js"></script>
<script type="text/javascript" src="${ctx}/dwz/jquery.messager.js"></script>
<script type="text/javascript" src="${ctx}/dwz/ajaxMessage/ajaxMessage.js"></script>
<script type="text/javascript" src="${ctx}/dwz/jsplit/jsplit.js"></script>
<script type="text/javascript" src="${ctx}/uploadify/threetwo/jquery.uploadify.min.js"></script>
<script>

function cqClick(){
	var cq = document.getElementsByName("changqi");
	var sfcq = document.getElementById("sfcq");
	var qjsxrq = document.getElementById("qjsxrq");
	if(cq[0].checked){
		sfcq.value = "1";
		qjsxrq.value="";
		qjsxrq.disabled = "disabled";
	}else{
		sfcq.value = "0";
		qjsxrq.disabled = "";
	}
}

	var isLogin = false;
	var isMess = false;
	function navHidden(){
		document.getElementById("navMenu").style.display="none";
	}
	
	function navShow(){
		document.getElementById("navMenu").style.display="";
	}
	
	function AddMoreRow() {
		var oRow = event.srcElement.parentNode.parentNode;
		var oTable = oRow.parentNode.parentNode;
		oNewRow = oTable.insertRow();
		for (i = 0; i < oRow.cells.length; i++) {
			oNewRow.insertCell().innerHTML = oRow.cells[i].innerHTML;
		}
		oRow.all("DelBtn").disabled = false;
		oNewRow.all("DelBtn").disabled = false;
	}
	function DeleteMoreRow() {
		var oRow = event.srcElement.parentNode.parentNode;
		var oTable = oRow.parentNode.parentNode;
		if (oTable.rows.length > 1) {
			oTable.deleteRow(oRow.rowIndex);
			if (oTable.rows.length == 1) {
				oTable.all("DelBtn").disabled = true;
			}
		}
	}
	
	function DeleteDemandPromRow(id) {
		//var oRow = event.srcElement.parentNode.parentNode;
		//var oTable = oRow.parentNode.parentNode;
		//if (oTable.rows.length > 1) {
		//oTable.deleteRow(oRow.rowIndex);
		//}
		document.getElementById("none"+id).style.display = "none";
		var promId = document.getElementById("promId").value;
		promId = promId.replace(id,''); 
		document.getElementById("promId").value = promId;
		var delePromId = document.getElementById("delePromId").value;
		delePromId += id+",";
		document.getElementById("delePromId").value = delePromId;
		//httpLocation();
	}
	function DeletePromRow(id) {
		var oRow = event.srcElement.parentNode.parentNode;
		var oTable = oRow.parentNode.parentNode;
		if (oTable.rows.length > 1) {
			oTable.deleteRow(oRow.rowIndex);
		}
		var promId = document.getElementById("promId").value;
		promId = promId.replace(id+",",''); 
		document.getElementById("promId").value = promId;
	}
	function DisabledKeyInput() {
		if (event.keyCode != 8 && event.keyCode != 46) {
			event.returnValue = http;//www.dwww.cn/News/2008-2/false;/
		}
	}
	
	
	function totals(totalId){
		var price = document.getElementById("prices"+totalId).value;
		var num = document.getElementById("nums"+totalId).value;
		var t = price*num;
		document.getElementById("totals"+totalId).value = fomatTotal(t+"");
	}
	
	function fomatTotal(tot){
		  var ss = tot.indexOf(".");
		  if(ss >= 1){
			  tot = tot.substring(0,ss+3);
		  }
		  return tot;
	}
	
	
	function onButtonSubmit(name, value){
		document.getElementById(name).value = value;
	}
	
	
	
	function lookLocation(lookId){
		document.getElementById("look").href="${ctx}/baseinfo/emplookup?lookId="+lookId;
	}
	
	function closedialog(param) {
		var uploadedFlag = document.getElementById("uploadedFlag");
		if("1" == uploadedFlag.value){
			navTabAjaxDone(param);
		}
		return true;
	}
	
	function subState(val){
		$("#state").val(val);
		return true;
	}
	
	function checkGouTong(val){
		if(val == 0){
			$("#toGouTongDiv").hide();
		}else{
			$("#toGouTongDiv").show();
		}
	}
	
</script>

<script type="text/javascript">	
	$(function() {
		DWZ.init("${ctx}/dwz/dwz.frag.xml", {
			loginUrl : "${ctx}/login_dialog",
			//loginTitle : "登录", // 弹出登录对话框
			loginUrl:"${ctx}/login.jsp",	// 跳到登录页面
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			}, //【可选】
			pageInfo : {
				pageNum : "pageNum",
				numPerPage : "numPerPage",
				orderField : "orderField",
				orderDirection : "orderDirection"
			}, //【可选】
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
				$("#themeList").theme({
					themeBase : "themes"
				});
				//$("#sidebar .toggleCollapse div").trigger("click");  
			}
		});
		jQuery.validator.addMethod("isIdCardNo", function (value, element) {
       			 return this.optional(element) || isIdCardNo(value);
    	}, "证件号码不正确");
    	jQuery.validator.addMethod("phoneNumber", function (value, element) {
       			 return this.optional(element) || phoneNumber(value);
    	}, "电话号码不正确");
    	jQuery.validator.addMethod("phone", function (value, element) {
       			 return this.optional(element) || phoneNumber(value);
    	}, "电话号码不正确");
	});
	
	function getLoginInfo(){
		
		$.ajax({
			  url: "${ctx}/baseinfo/getLoginInfo",
			  cache: false,
			  global: false,
			  async: false,
			  success: function(data){
				$("#userName").html(data.name);	
				isLogin = data.pass;
				isMess = data.isMess;
			  }
			}); 		
	}
	
	function isLoginPass(){

		//$.ajax({
			//  url: "${ctx}/baseinfo/getLoginInfo",
			//  cache: false,
			//  global: false,
			//  async: false,
			//  success: function(data){
				//alert(isLogin);
				if(isLogin){
					//if(window.confirm("您的密码是默认密码，是否确定修改?")){
						$("#password111").click(); 
					//}
				}
			  //}
			//}); 	
	}
	
	String.prototype.Trim = function()    {    return this.replace(/(^\s*)|(\s*$)/g, "");    }
	String.prototype.LTrim = function()    {    return this.replace(/(^\s*)/g, "");    }   
	String.prototype.RTrim = function()    {    return this.replace(/(\s*$)/g, "");    }

</script>

</head>

<body scroll="no">
<a id="password111" href="${ctx }/userinfo/password" target="dialog" title="请重设您的密码"  mask="true" width="400" onclick="clearTimeout(timer1);"></a>
	<input type="hidden" id="uploadedFlag" name="uploadedFlag" value="0" />
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="#">标志</a>
				<ul class="nav">
					<!-- 
					<li id="switchEnvBox"><a href="javascript:">（<span>北京</span>）切换城市</a>
						<ul>
							<li><a href="sidebar_1.html">北京</a>
							</li>
							<li><a href="sidebar_2.html">上海</a>
							</li>
							<li><a href="sidebar_2.html">南京</a>
							</li>
							<li><a href="sidebar_2.html">深圳</a>
							</li>
							<li><a href="sidebar_2.html">广州</a>
							</li>
							<li><a href="sidebar_2.html">天津</a>
							</li>
							<li><a href="sidebar_2.html">杭州</a>
							</li>
						</ul></li>
					 -->
					 <bjdv:validateContent type="1" funcId="系统-缓存管理"> 
				    <li>					
					   <a id="password" href="${ctx}/menus/cacheControl" target="dialog"
						mask="true" width="400" onclick="">缓存管理</a>
					</li>
					</bjdv:validateContent> 
					<li><img src="${ctx}/resources/user.png"
						width="16" height="16" />
					<div id="userName" style="line-height: 12px; color: #B9CCDA"></div>
					</li>
					<li>
					<img src="${ctx}/resources/group_key.png"
						width="16" height="16" />
					<a id="password" href="${ctx }/account/password" target="dialog"
						mask="true" width="400" onclick="">修改密码</a></li>
					<li>
					<img src="${ctx}/resources/message-unread.png" width="16" height="12" />
					<a href="${ctx }/message/listPublicMessage" rel="rel_listPublicMessage" target="navTab"><div id="messageSize" style="line-height: 12px; color: #B9CCDA">您的公告消息!</div></a></li>
					<!-- 
					<li><a href="#" target="_blank">博客</a>
					</li>
					<li><a href="#" onclick="navShow()">显示</a>
					</li>
					<li><a href="#" onclick="navHidden()">隐藏</a>
					</li>
					 -->
					<li><img src="${ctx}/resources/user_go.png" width="16"
						height="16" /><a href="${ctx}/j_spring_security_logout">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
			</div>

			<div id="navMenu">
				<ul>

				</ul>
			</div>
			<script type="text/javascript">
		function menuLoaded(){
		    var accordition = $('div#sidebar').find('div').get(2);
		    if(accordition){
		    	if($(accordition).html().trim().length > 0)
		    	return true;
		    }
		    return false;
		}
		
		/* function clickFirstMenu(){
			 if(menuLoaded()){
			   clearInterval(loopclick);
			 };
		} */
			
		$.get("${ctx}/menus/topmenus",{r:new Date().getTime()},function(data, status){
			if(status=="success"){
				var aa ="<ul>";
				var isMenu = false;
				$.each(data.data, function(i, item) {
					var a ="";
					if (i==0){
						a = " class=\"selected\"";
					}
					var bb = "<li"+a+"><a href=\"${ctx}"+item.menuUrl+"/"+item.id+"\"><span>"+item.menuName+"</span></a></li>";
					aa += bb;
					isMenu = true;
				});
				if(!isMenu){
					aa +="<li class=\"selected\"><a href=\"${ctx}/baseinfo/menuleft/139\"><span>个人信息</span></a></li>";
				}
				aa = aa + "</ul>";
				$("#navMenu").html(aa);
				
				/* setTimeout(function(){
					if(!menuLoaded()){
					   location.reload();
					}
				},500); */
			}
		});
		
		
</script>
		</div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar" id="leftmenu"></div>
			</div>
		</div>
		<script type="text/javascript">
getLoginInfo();
getLoginMessage();
/*首页消息提醒
ajaxGetExpireMessage();
*/
</script>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"> <span>
										<span class="home_icon">我的主页</span>
								</span>
							</a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="pageContent">
							<!-- 
						<div style="width:31%;margin:5px;float:left;min-height:100px">
						
							<div class="indexMessage" id="indexMessage">
								<div class="indexMessage01">
									<div  class="indexMessageTitle" id="indexMessage_title">
										到期提醒
									</div>
									<div style="clear: both;"></div>
								</div>
								<div class="indexMessage02">
									<div class="indexMessageContent" id="indexMessage_content_01">
							        </div>
								</div>
							</div>
							<div class="indexMessage" id="indexMessage" style="">
								<div class="indexMessage01">
									<div  class="indexMessageTitle" id="indexMessage_title">
										团队信息
									</div>
									<div style="clear: both;"></div>
								</div>
								<div class="indexMessage02">
									<div class="indexMessageContent" id="indexMessage_content_03">
							        </div>
								</div>
							</div>
							
						</div>
						<div style="width:31%;margin:5px;float:left;min-height:100px">
							
							<div class="indexMessage" id="indexMessage">
								<div class="indexMessage01">
									<div  class="indexMessageTitle" id="indexMessage_title">
										审批提醒
									</div>
									<div style="clear: both;"></div>
								</div>
								<div class="indexMessage02">
									<div class="indexMessageContent" id="indexMessage_content_02">
							        </div>
								</div>
							</div>
							<div class="indexMessage" id="indexMessage" style="">
								<div class="indexMessage01">
									<div  class="indexMessageTitle" id="indexMessage_title">
										生日提醒
									</div>
									<div style="clear: both;"></div>
								</div>
								<div class="indexMessage02">
									<div class="indexMessageContent" id="indexMessage_content_04">
							        </div>
								</div>
							</div>

						</div>
						
						<div style="width:31%;margin:5px;float:left;min-height:100px">
						
							<div class="indexMessage" id="indexMessage">
								<div class="indexMessage01">
									<div  class="indexMessageTitle" id="indexMessage_title">
										到期提醒
									</div>
									<div style="clear: both;"></div>
								</div>
								<div class="indexMessage02">
									<div class="indexMessageContent" id="indexMessage_content_05">
							        </div>
								</div>
							</div>
							<div class="indexMessage" id="indexMessage" style="">
								<div class="indexMessage01">
									<div  class="indexMessageTitle" id="indexMessage_title">
										团队信息
									</div>
									<div style="clear: both;"></div>
								</div>
								<div class="indexMessage02">
									<div class="indexMessageContent" id="indexMessage_content_06">
							        </div>
								</div>
							</div>
							
						</div>
					  -->
						</div>
					</div>
				</div>

			</div>
			<div id="chpMessageBox"></div>
		</div>
	</div>

	<div id="footer">Copyright &copy; 2012 www.CreditHarmony.cn</div>
<script>
var timer1 = setTimeout("isLoginPass()",500);
//document.onreadystatechange = isLoginPass;
$(document).ready(function(){
	$("#cc").jsplit({ 
		MaxW:"700px",
		MinW:"300px",
		FloatD:"left",
		IsClose:false,
		BgUrl:"url(${ctx}/dwz/jsplit/sp_bg.gif)",
		Bg:"right 0 repeat-y",
		Btn:{btn:true,
			oBg:{Out:"0 0",Hover:"-6px 0"},
			cBg:{Out:"-12px 0",Hover:"-18px 0"}},
		Fn:function(){}});
});
</script>
</body>
</html>