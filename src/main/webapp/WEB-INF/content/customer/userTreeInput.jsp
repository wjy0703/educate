﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/themes/js/checkRoleValue.js"></script>
<div class="panel">
<div class="pageContent">
	<form method="post" action="${ctx}/userinfo/saveTreeUser"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${userinfo.id}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>员工账户：</label> <input name="account" id="account" type="text" size="30"
					 value="${userinfo.account }" class="required" onblur="vUserAccount('${userinfo.account }','account','员工账户');" />
			</p>
			<p>
				<label>员工姓名：</label> <input name="vname" type="text" size="30"
					value="${userinfo.vname }" class="required" />
			</p>
			<p>
				<label>证件号码：</label> <input name="card" type="text" size="30"
					alt="请输入证件号码" value="${userinfo.card }" class="required"/>
			</p>
			<p>
				<label>状态：</label> 
				<sen:select clazz="combox required" name="vtypes" id="vtypes" coding="onjob" value="${auth.vtypes }" title="请选择" defaultName="在职"/>
			</p>
			<p>
				<label>性别：</label> 
				<sen:select clazz="combox required" name="sex" id="sex" coding="sexType" value="${userinfo.sex }" title="请选择"/>
			</p>
			<p>
				<label>岗位：</label> 
				<c:if test="${canLook=='1'}">
					<sen:select clazz="combox required" name="post" id="post" coding="postType" value="${userinfo.post }" title="请选择" 
					unShowName="超级管理员"/>
				</c:if>
				<c:if test="${canLook=='0'}">
					<sen:select clazz="combox required" name="post" id="post" coding="postType" value="${userinfo.post }" title="请选择" 
					/>
				</c:if>
			</p>
			<div class="divider"></div>
			<p>
				<label>手机号码：</label> <input name="phone" type="text" size="30"
					value="${userinfo.phone }" />
			</p>
			<div class="divider"></div>
			<p>
				<label>邮箱：</label> <input name="mail" type="text" size="30"
					value="${userinfo.mail }" />
			</p>
			<p>
					<label>角色：</label>
					<!-- 
					<input type="hidden" name="orgLookup.id" value="${userinfo.roleinfo.id}" />
					<input type="text" name="orgLookup.rolename" size="30" readonly="readonly" value="${userinfo.roleinfo.rolename }" />
					<a href="${ctx }/userinfo/getRole?busid=${userinfo.businessinfo.id}" lookupGroup="orgLookup" width="400" height="400">角色</a>
					 -->
					<input type="hidden" name="roleinfo.id" value="${userinfo.roleinfo.id}" >
					<input type="text" name="roleinfo.name" size="30" 
						readonly="readonly" value="${userinfo.roleinfo.rolename}">
					<a class="btnLook"
						href="${ctx }/role/findrole?userId=${user.id}&busid=${userinfo.businessinfo.id}"
						lookupGroup="roleinfo">选择角色</a>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close" onclick="closeDialog('${userinfo.organizeinfo.id}')">关闭并刷新</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div></div>
<script>
function closeDialog(param){
	$("#"+param).click();
}
</script>