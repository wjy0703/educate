<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${ctx}/reatpackage/saveReatpackage" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${reatpackage.id}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>套餐名称：</label>
				<input name="reatname" type="text" size="30" value="${reatpackage.reatname}" class="required" maxlength="50" />
			</p>
			<p>
				<label>周期（月）：</label>
				<sen:select clazz="required combox" name="cycke" coding="cyckes" value="${reatpackage.cycke}" title="请选择"/>
			</p>
			<p>
				<label>套餐价格：</label>
				<input name="price" type="text" size="30" value="${reatpackage.price}" class="required" maxlength="8" />
			</p>
			<p>
				<label>属性：</label>
				<sen:select clazz="required combox" name="vtypes" coding="vtypes" value="${reatpackage.vtypes}" title="请选择" defaultName="在用"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
