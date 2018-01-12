<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="panel">
<div class="pageContent">
	<form method="post" action="${ctx}/role/saveMenu"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${menu.id}" />
		<input type="hidden" name="parent.id" value="${menu.parent.id}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>菜单名称：</label> <input name="menuname" type="text" size="30"
					alt="请输入菜单名称" value="${menu.menuname }" class="required" />
			</p>
			<p><label>URL菜单名称：</label> <input name="menuurl" type="text" size="30"
					alt="请输入菜单名称" value="${menu.menuurl }" class="required" /></p>
			<p></p>
			<p>
				<label>菜单标题：</label> <input name="title" type="text" size="30"
					value="${menu.title }" />
			</p>
			<p><label>菜单类型:</label> 
				<sen:select name="menutype" coding="menutype" clazz="combox" title="全部" value="${menu.menutype}" />
			</p>
			<p><label>系统类型:</label> 
				<sen:select name="vsystype" coding="systype" clazz="combox" title="全部" value="${menu.vsystype}" />
			</p>
			<p>
				<label>菜单状态：</label> 
				<sen:select name="vtypes" coding="vtypes" clazz="combox" title="全部" value="${menu.vtypes}" />
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
</div></div>
