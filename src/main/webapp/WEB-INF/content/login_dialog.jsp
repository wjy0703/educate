<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent">

	<form method="post" action="${ctx}/j_spring_security_check"
		class="pageForm"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>用户名ma：</label> <input type="text" name="j_username" size="30"
					class="required" />
			</div>
			<div class="unit">
				<label>密码：</label> <input type="password" name="j_password"
					size="30" class="required" />
			</div>
			<input type="hidden" name="spring-security-redirect"
				value="/account/ajaxloginsuccess" />
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
