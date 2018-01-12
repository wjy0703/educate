<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>

<form id="pagerForm" method="post" action="#rel#">
	<input type="hidden" name="pageNum" value="${page.pageNo }" /> <input
		type="hidden" name="numPerPage" value="${page.pageSize}" /> <input
		type="hidden" name="orderField" value="${page.orderBy}" /> <input
		type="hidden" name="orderDirection" value="${page.order}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);"
		action="${ctx }/role/listZd" method="post">
		<div class="searchBar">
			<ul class="searchContent">
				<li><label>字典名称:</label> <input type="text" name="filter_matename" value="${map.matename}"/>
				</li>
				<li><label>字典状态:</label> <sen:select name="filter_vtypes" coding="vtypes" clazz="combox" title="全部" value="${map.vtypes}" />
				</li>
				<li><label>字典类型:</label> <select class="combox"
					name="filter_code">
						<option value="">全部</option>
						<c:forEach items="${type}" var="type" varStatus="st">
							<option value="${type.id }" <c:if test="${map.code==type.id}">selected</c:if>>${type.typename }</option>
						</c:forEach>
				</select></li>
			</ul>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">检索</button>
							</div>
						</div></li>
					<!-- <li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li> -->
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/role/addZp" target="navTab"><span>添加</span></a></li>
			<li><a class="edit" href="${ctx}/role/editZd/{sid_zd}"
				target="navTab" warn="请选择一个产品"><span>修改</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="28"><input type="checkbox" group="ids"
					class="checkboxCtrl"></th>
				<th width="100" orderField="matename" class="asc">字典名称</th>
				<th width="100" orderField="vvalue">字典参数</th>
				<th width="100" align="center" orderField="code">字典类型</th>
				<th width="100" align="center" orderField="state">字典状态</th>
				<th width="100" align="center" orderField="state">银行编码</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="zd" varStatus="st">
				<tr target="sid_zd" rel="${zd.id}">
					<td><input name="ids" value="${zd.id}" type="checkbox"></td>
					<td>${zd.matename }</td>
					<td>${zd.vvalue }</td>
					<td>${zd.code.typename }</td>
					<td><sen:vtoName coding="vtypes" value="${zd.vtypes}"/></td>
					<td>${zd.bankcode }</td>
					<td><a title="删除" target="ajaxTodo"
						href="${ctx }/role/delZd/${zd.id}" class="btnDel">删除</a> <a
						title="编辑" target="navTab" href="${ctx }/role/editZd/${zd.id}"
						class="btnEdit">编辑</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span> <select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10"
					<c:if test="${page.pageSize=='2'}">selected</c:if>>2</option>
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
