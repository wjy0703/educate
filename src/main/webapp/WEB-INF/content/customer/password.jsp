<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="panel">
	<div class="pageContent">
		<form method="post" action="${ctx }/userinfo/savepassword"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<div class="pageFormContent" layoutH="55">
				<div class="unit">
					<label>用户名：</label> <input type="text" name="loginName" size="30"
						readonly="readonly" value="${loginName }" />
				</div>
				<div class="unit">
					<label>原密码：</label> <input type="password" name="oldpassword"
						size="30" class="required" />
				</div>
				<div class="unit">
					<label>输入新密码：</label> <input type="password" name="password" id="newPassword"
						size="30" class="required  alphanumeric" alt="请输入密码" minlength="6"
						maxlength="16" />
				</div>
								<div class="unit">
					<label>重新输入新密码：</label> <input type="password" name="password1" class="required" equalto="#newPassword"
						size="30" minlength="6"
						maxlength="16" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div></li>
						
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>

