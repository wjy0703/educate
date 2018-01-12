<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="panel">
	<div class="pageContent">
		<form method="post" action="${ctx }/organize/saveTree"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<%-- 上级机构的id拿到后台处理了 <input type="hidden" id="parentId" name="parentId" value="${upOrgani.id }"/> --%>
			<input type="hidden" id="id" name="id" value="${organi.id }"/>
			<div class="pageFormContent" layoutH="55">
				<div class="unit">
					<label>上级机构：</label>
					<input type="hidden" name="orgLookup.busid" value="${upOrgani.businessinfo.id}" />
					<input type="hidden" name="orgLookup.id" value="${upOrgani.id}" />
					<input type="text" name="orgLookup.name" size="30" readonly="readonly" value="${upOrgani.orgname }" />
					<a href="${ctx }/organize/getTreeDept" lookupGroup="orgLookup" width="400" height="400">选择机构</a>
				</div>
				<div class="unit">
					<label>机构名称：</label>
					<input type="text" name="orgname" size="30" class="required" value="${organi.orgname }" ${res }/>
				</div>
				<div class="unit">
					<label>机构地址：</label>
					<input type="text" name="address" size="30" class="required" value="${organi.address }" ${res }/>
				</div>
				<div class="unit">
					<label>级别描述：</label>
					<sen:select clazz="combox required" name="orgflag" id="orgflag" coding="orgflags" value="${organi.orgflag }" title="请选择" defaultName="门店"/>
				</div>
				<div class="unit">
					<label>是否在用：</label>
					<sen:select clazz="combox required" name="vtypes" id="vtypes" coding="vtypes" value="${organi.vtypes }" title="请选择" defaultName="在用"/>
				</div>
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
	</div>
</div>

