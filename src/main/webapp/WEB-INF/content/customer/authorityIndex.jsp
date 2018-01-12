<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<script src="ext/operationjs/showoperation.js" type="text/javascript"></script>
<form id="pagerForm" method="post" action="#rel#">
	<input type="hidden" name="pageNum" value="${page.pageNo }" /> <input
		type="hidden" name="numPerPage" value="${page.pageSize}" /> <input
		type="hidden" name="orderField" value="${page.orderBy}" /> <input
		type="hidden" name="orderDirection" value="${page.order}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);"
		action="${ctx }/authority/listauth" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>资源名称:</label> <input type="text" name="filter_name" value="${map.name }"/></td>
					<td><label>资源描述:</label> <input type="text"
						name="filter_cname" value="${map.cname }"/></td>
					<td><label>资源路径:</label> <input type="text" name="filter_path" value="${map.path }"/></td>
					<td><label>状态:</label> 
					<sen:select name="filter_vtype" coding="vtypes" clazz="combox" title="全部" value="${map.vtype}" />
					
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">检索</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		   <bjdv:validateContent type="1" funcId="资源管理-添加">
			<li><a class="add" href="${ctx}/authority/addauth" target="navTab"><span>添加</span></a></li>
			</bjdv:validateContent>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
				postType="string" href="${ctx }/authority/batchdelauth" class="delete"
				warn="请至少选择一个用户"><span>批量删除</span></a></li>
			<li><a class="edit" href="${ctx}/authority/editauth/{sid_auth}"
				target="navTab" warn="请选择一个用户"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport"
				targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="28"><input type="checkbox" group="ids"
					class="checkboxCtrl"></th>
				<th width="80" orderField="aname" class="asc">资源名称</th>
				<th width="80" orderField="cname">资源描述</th>
				<th width="100" orderField="vpath">资源路径</th>
				<th width="40" orderField="vtype">状态</th>
				<th width="40">系统属性</th>
				<th width="80" orderField="createuser">创建人</th>
				<th width="80" align="center" orderField="createtime">创建日期</th>
				<th width="80">修改人</th>
				<th width="80">修改日期</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="auth" varStatus="st">
				<tr target="sid_auth" rel="${auth.id}">
					<td><input name="ids" value="${auth.id}" type="checkbox"></td>
					<td>${auth.aname }</td>
					<td>${auth.cname }</td>
					<td>${auth.vpath }</td>
					<td><sen:vtoName coding="vtypes" value="${auth.vtype}"/></td>
					<td><sen:vtoName coding="systype" value="${auth.vsystype}"/></td>
					<td>${auth.createuser }</td>
					<td><fmt:formatDate value='${auth.createtime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td>${auth.modifyuser }</td>
					<td><fmt:formatDate value='${auth.modifytime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td><a title="删除" target="ajaxTodo"
						href="${ctx }/authority/delauth/${auth.id}" class="btnDel">删除</a> <a
						title="编辑" target="navTab"
						href="${ctx }/authority/editauth/${auth.id}" class="btnEdit">编辑</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span> <select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10"
					<c:if test="${page.pageSize=='10'}">selected</c:if>>2</option>
				<option value="20"
					<c:if test="${page.pageSize=='20'}">selected</c:if>>20</option>
				<option value="50"
					<c:if test="${page.pageSize=='50'}">selected</c:if>>50</option>
				<option value="100"
					<c:if test="${page.pageSize=='100'}">selected</c:if>>100</option>
				<option value="200"
					<c:if test="${page.pageSize=='200'}">selected</c:if>>200</option>
			</select> <span>条，共${page.totalCount}条</span>
		</div>

		<div class="pagination" targetType="navTab"
			totalCount="${page.totalCount}" numPerPage="${page.pageSize }"
			pageNumShown="10" currentPage="${page.pageNo }"></div>

	</div>
</div>
