<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${ctx}/themes/js/checkValue.js"></script>
<div class="panel">
	<div class="pageContent">
		<form method="post" action="${ctx }/cityinfo/saveTree"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone)">
			<input type="hidden" id="parentId" name="parent.id" value="${upBaseCity.id }"/>
			<input type="hidden" id="id" name="id" value="${baseCity.id }"/>
			<div class="pageFormContent" layoutH="65">
				<div class="unit">
					<label>上级城市：</label>
					<input type="text" name="" size="30" readonly="readonly" value="${upBaseCity.vname }" />
				</div>
				<div class="unit">
					<label>城市名称：</label>
					<input    type="text" name="vname" id="cityname" size="30" class="required" value="${baseCity.vname }" />
				</div>
				<div class="unit">
					<label>城市编码：</label>
					<input type="text" name="coding" id="coding" size="30" value="${baseCity.coding }" />
					
				</div>
				<div class="unit">
					<label>城市区号：</label>
					<input type="text" name="cname" id="cname" size="30" value="${baseCity.cname }" />
				</div>
				<div class="unit">
					<label>简称/全拼：</label>
					<input type="text" name="pinyin" size="30" class="required" value="${baseCity.pinyin }" 
					 />
				</div>
				<div class="unit">
					<label>是否在用：</label>
					<sen:select clazz="combox required" name="vtypes" id="vtypes" coding="vtypes" value="${baseCity.vtypes }" title="请选择" defaultName="在用"/>
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

