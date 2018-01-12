<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/themes/js/checkBusiAccount.js"></script>
<div class="pageContent">
	<form method="post" action="${ctx}/businessinfo/saveBusinessinfo" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<input type="hidden" name="id" value="${businessinfo.id}"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>公司账户：</label>
				<input name="busiaccount" id="busiaccount" type="text" size="30"
					alt="请输入资源名称，3-32字符" value="${businessinfo.busiaccount }" class="required"
					minlength="3" maxlength="32"
					onblur="vBusiAcc('${businessinfo.busiaccount }','busiaccount','公司账户');" 
					 />
			</p>
			<p>
				<label>公司名：</label>
				<input name="businame" type="text" size="30" value="${businessinfo.businame}" class="required" maxlength="40" />
			</p>
			<p>
				<label>法人：</label>
				<input name="corporation" type="text" size="30" value="${businessinfo.corporation}" class="required" maxlength="10" />
			</p>
			<p>
				<label>证件号码：</label>
				<input name="card" type="text" size="30" value="${businessinfo.card}" class="required" maxlength="30" />
			</p>
			<p>
				<label>联系方式：</label>
				<input name="phone" type="text" size="30" value="${businessinfo.phone}" class="required" maxlength="20" />
			</p>
			<p>
				<label>属性：</label>
				<sen:select clazz="required combox" name="vtypes" coding="vtypes" value="${businessinfo.vtypes}" title="请选择" defaultName="在用"/>
			</p>
			<p>
				<label>套餐类型：</label>
				<input type="hidden" name="oldreatid" value="${businessinfo.reatpackage.id}" >
				<input type="hidden" name="reatpackage.id" value="${businessinfo.reatpackage.id}" >
					<input type="text" name="reatpackage.name" size="30" 
						readonly="readonly" value="${businessinfo.reatpackage.reatname}">
					<a class="btnLook"
						href="${ctx }/businessinfo/findreat?reatid=${businessinfo.reatpackage.id}"
						lookupGroup="reatpackage">选择套餐</a>
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
