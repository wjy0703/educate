<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<form id="pagerForm"
	action="${ctx }/authority/findauthority?roleId=${role.id}">
	<input type="hidden" name="pageNum" value="1" /> <input type="hidden"
		name="numPerPage" value="10000" /> <input type="hidden"
		name="orderField" value="" /> <input type="hidden"
		name="orderDirection" value="" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post"
		action="${ctx }/authority/findauthority?roleId=${role.id}"
		onsubmit="return dwzSearch(this, 'dialog');">
		<div class="searchBar">
			<!--<ul class="searchContent">
			  <li>
				<label>部门名称:</label>
				<input class="textInput" name="orgName" value="" type="text">
			</li>	  
			<li>
				<label>部门编号:</label>
				<input class="textInput" name="orgNum" value="" type="text">
			</li>
			<li>
				<label>部门经理:</label>
				<input class="textInput" name="leader" value="" type="text">
			</li>
				<li>
				<label>上级部门:</label>
				<input class="textInput" name="parentOrg.orgName" value="" type="text">
			</li> 
		</ul>-->
			<div class="subBar">
				<ul>
					<!-- <li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li> -->
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" multLookup="orgId" warn="请选择角色">选择带回</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="128" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl"
					group="orgId" /></th>
				<th>资源名称</th>
				<th>资源描述</th>
				<th>资源路径</th>
				<th>状态</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result1}" var="auth" varStatus="st">
				<tr>
					<td><input type="checkbox" name="orgId"
						value="{id:'${auth.id }', name:'${auth.aname}'}" checked /></td>
					<td>${auth.aname}</td>
					<td>${auth.cname }</td>
					<td>${auth.vpath }</td>
					<td><sen:vtoName coding="vtypes" value="${auth.vtype}"/></td>
					<td></td>
				</tr>
			</c:forEach>
			<c:forEach items="${result}" var="auth" varStatus="st">
				<tr>
					<td><input type="checkbox" name="orgId"
						value="{id:'${auth.id }', name:'${auth.aname}'}" /></td>
					<td>${auth.aname}</td>
					<td>${auth.cname }</td>
					<td>${auth.vpath }</td>
					<td><sen:vtoName coding="vtypes" value="${auth.vtype}"/></td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!--
	<div class="panelBar">
		<div class="pages">
			<span>每页</span>

			<select name="numPerPage" onchange="dwzPageBreak({targetType:dialog, numPerPage:'10'})">
				<option value="10" selected="selected">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			<span>条，共2条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="2" numPerPage="10000" pageNumShown="1" currentPage="1"></div>
	</div>  -->
</div>