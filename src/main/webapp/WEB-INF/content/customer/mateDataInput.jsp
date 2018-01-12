<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="panel">
<div class="pageContent">
	<form method="post" action="${ctx}/role/saveZd"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${zd.id}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>字典名称：</label> <input name="matename" type="text" size="30"
					alt="请输入名称" value="${zd.matename }" class="required" />
			</p>
			<p>
				<label>字典参数：</label> <input name="vvalue" type="text" size="30"
					alt="请输入参数" value="${zd.vvalue }" class="required" />
			</p>
			<p>
				<label>字典类型：</label> <select name="code.id" class="required combox">
					<c:forEach items="${type}" var="type" varStatus="st">
						<option value="${type.id }"
							<c:if test="${zd.code.id==type.id}">selected</c:if>>${type.typename}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>字典状态：</label> <sen:select name="vtypes" coding="vtypes" clazz="combox" title="全部" value="${zd.vtypes}" />
			</p>
					<p>
				<label>银行编码：</label> <input name="bankcode" type="text" size="30"
					alt="请输入参数" value="${zd.bankcode }" class="" />
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
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
</div>