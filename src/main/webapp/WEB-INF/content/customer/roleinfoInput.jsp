<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/themes/js/checkRoleValue.js"></script>
<div class="panel">
<div class="pageContent">
	<form method="post" action="${ctx}/role/saverole"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${role.id}" />
		<input type="hidden" name="busid" value="${role.busid}" />
		<input type="hidden" name="busacc" id="busacc" value="${operator.busacc}" />
		<input type="hidden" name="canLook" id="canLook" value="${canLook}" />
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>角色名称：</label> <input name="rolename" id="rolename" type="text" size="30"
					alt="请输入角色名称，3-32字符" value="${role.rolename }" class="required"
					minlength="3" maxlength="32"
					onblur="vRoleName('${role.rolename }','rolename','角色名称');" />
			</p>
			<p>
				<label>状态：</label> 
				<sen:select clazz="combox required" name="vtypes" id="vtypes" coding="vtypes" value="${role.vtypes }" title="请选择" defaultName="在用"/>
			</p>
			<div class="divider"></div>

			<dl class="nowrap">
				<dt>拥有菜单</dt>
				<dd>
					<input name="menu.id" value="${role.menuIds}" type="hidden">
					<textarea name="menu.name" class="readonly" readonly="readonly"
						style="width: 93%;">${role.menuNames}</textarea>
					<a class="btnLook"
						href="${ctx }/authority/findMenu?roleId=${role.id}"
						lookupGroup="menu">选择菜单</a>
				</dd>
			</dl>

			<div class="divider"></div>

			<dl class="nowrap">
				<dt>拥有资源</dt>
				<dd>
					<input name="authoritys.id" value="${role.authIds}" type="hidden">
					<textarea name="authoritys.name" class="readonly"
						readonly="readonly" style="width: 93%;">${role.authNames}</textarea>
					<a class="btnLook"
						href="${ctx }/authority/findauthority?roleId=${role.id}"
						lookupGroup="authoritys">选择资源</a>
				</dd>
			</dl>

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
