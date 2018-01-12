<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="cn.com.educate.core.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<form id="pagerForm" method="post" action="#rel#">
	<input type="hidden" name="pageNum" value="${page.pageNo }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
	<input type="hidden" name="orderField" value="${page.orderBy}" />
	<input type="hidden" name="orderDirection" value="${page.order}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx }/reatpackage/listReatpackage" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				<label>套餐名称:</label>
				<input type="text" name="filter_reatname" value="${map.reatname}"/>
			</td>
			<td>
				<label>周期（月）:</label>
				<sen:select name="filter_cycke" coding="cyckes" clazz="combox" title="全部" value="${map.cycke}" />
			</td>
			<td>
				<label>套餐价格:</label>
				<input type="text" name="filter_price" value="${map.price}"/>
			</td>
			<td>
				<label>属性:</label>
				<sen:select name="filter_vtypes" coding="vtypes" clazz="combox" title="全部" value="${map.vtypes}" />
			</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<!-- <li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li> -->
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/reatpackage/addReatpackage" target="navTab"><span>添加</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="${ctx }/reatpackage/batchdelReatpackage" class="delete" warn="请至少选择一个"><span>批量删除</span></a></li>
			<li><a class="edit" href="${ctx}/reatpackage/editReatpackage/{sid_user}" target="navTab" warn="请选择一个用户"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="28"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80" orderField="reatname" class="asc">套餐名称</th>
				<th width="80" orderField="cycke" class="asc">周期（月）</th>
				<th width="80" orderField="price" class="asc">套餐价格</th>
				<th width="80" orderField="vtypes" class="asc">属性</th>
				<th width="80" orderField="createtime" class="asc">创建时间</th>
				<th width="80" orderField="modifytime" class="asc">修改时间</th>
				<th width="80" orderField="createuser" class="asc">创建人</th>
				<th width="80" orderField="modifyuser" class="asc">修改人</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="user" varStatus="st">
			<tr target="sid_user" rel="${user.id}">
				<td><input name="ids" value="${user.id}" type="checkbox"></td>
				<td>${user.reatname}</td>
				<td><sen:vtoName coding="cyckes" value="${user.cycke}"/></td>
				<td>${user.price}</td>
				<td><sen:vtoName coding="vtypes" value="${user.vtypes}"/></td>
				<td>${user.createuser }</td>
				<td><fmt:formatDate value='${user.createtime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
				<td>${user.modifyuser }</td>
				<td><fmt:formatDate value='${user.modifytime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
				<td>
					<a title="删除" target="ajaxTodo" href="${ctx }/reatpackage/delReatpackage/${user.id}" class="btnDel">删除</a>
					<a title="编辑" target="navTab" href="${ctx }/reatpackage/editReatpackage/${user.id}" class="btnEdit">编辑</a>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="2" <c:if test="${page.pageSize=='2'}">selected</c:if>>2</option>
				<option value="10" <c:if test="${page.pageSize=='10'}">selected</c:if>>10</option>
				<option value="20" <c:if test="${page.pageSize=='20'}">selected</c:if>>20</option>
				<option value="50" <c:if test="${page.pageSize=='50'}">selected</c:if>>50</option>
				<option value="100" <c:if test="${page.pageSize=='100'}">selected</c:if>>100</option>
				<option value="200" <c:if test="${page.pageSize=='200'}">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${page.totalCount}" numPerPage="${page.pageSize }" pageNumShown="10" currentPage="${page.pageNo }"></div>

	</div>
</div>
