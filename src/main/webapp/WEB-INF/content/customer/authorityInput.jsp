<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/themes/js/checkValue.js"></script>
<div class="panel">
<div class="pageContent">
	<form method="post" action="${ctx}/authority/saveauth"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${auth.id}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>资源名称：</label> <input name="aname" id="aname" type="text" size="30"
					alt="请输入资源名称，3-32字符" value="${auth.aname }" class="required"
					minlength="3" maxlength="32"
					onblur="vAuthName('${auth.aname }','aname','资源名称');" 
					 />
			</p>
			<p>
				<label>角色描述：</label> <input name="cname" type="text" size="30"
					value="${auth.cname }" />
			</p>
			<p>
				<label>资源路径：</label> <input name="vpath" type="text" size="30"
					value="${auth.vpath }" />
			</p>
			<p>
				<label>状态：</label> 
				<sen:select clazz="combox required" name="vtype" id="vtype" coding="vtypes" value="${auth.vtype }" title="请选择" defaultName="在用"/>
			</p>
			<p>
				<label>系统属性：</label> 
				<sen:select clazz="combox required" name="vsystype" id="vsystype" coding="systype" value="${auth.vsystype }" title="请选择" defaultName="业务"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
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
</div></div>
