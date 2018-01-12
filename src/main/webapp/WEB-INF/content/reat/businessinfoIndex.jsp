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
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${ctx }/businessinfo/listBusinessinfo" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				<label>公司账户:</label>
				<input type="text" name="filter_busiaccount" value="${map.busiaccount}"/>
			</td>
			<td>
				<label>公司名:</label>
				<input type="text" name="filter_businame" value="${map.businame}"/>
			</td>
			<td>
				<label>法人:</label>
				<input type="text" name="filter_corporation" value="${map.corporation}"/>
			</td>
			<td>
				<label>属性:</label>
				<sen:select name="filter_vtypes" coding="vtypes" clazz="combox" title="全部" value="${map.vtypes}" />
			</td>
			</tr>
			<tr>
			<td>
				<label>套餐周期:</label>
				<sen:select name="filter_tctypes" coding="cyckes" clazz="combox" title="全部" value="${map.tctypes}" />
			</td>
			<td>
				<label>生效时间:</label>
				<input type="text" name="filter_starttime" value="${map.starttime}"/>
			</td>
			<td>
				<label>到期时间:</label>
				<input type="text" name="filter_overtime" value="${map.overtime}"/>
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
			<li><a class="add" href="${ctx}/businessinfo/addBusinessinfo" target="navTab"><span>添加</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="${ctx }/businessinfo/batchdelBusinessinfo" class="delete" warn="请至少选择一个"><span>批量删除</span></a></li>
			<li><a class="edit" href="${ctx}/businessinfo/editBusinessinfo/{sid_user}" target="navTab" warn="请选择一个用户"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="#" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="28"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80" orderField="busiaccount" class="asc">公司账户</th>
				<th width="80" orderField="businame" class="asc">公司名</th>
				<th width="80" orderField="corporation" class="asc">法人</th>
				<th width="80" orderField="card" class="asc">证件号码</th>
				<th width="80" orderField="phone" class="asc">联系方式</th>
				<th width="80" orderField="vtypes" class="asc">属性</th>
				<th width="80" orderField="tctypes" class="asc">套餐周期</th>
				<th width="80" orderField="starttime" class="asc">生效日期</th>
				<th width="80" orderField="overtime" class="asc">到期日期</th>
				<th width="70">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="user" varStatus="st">
			<tr target="sid_user" rel="${user.id}">
				<td><input name="ids" value="${user.id}" type="checkbox"></td>
				<td>${user.busiaccount}</td>
				<td>${user.businame}</td>
				<td>${user.corporation}</td>
				<td>${user.card}</td>
				<td>${user.phone}</td>
				<td><sen:vtoName coding="vtypes" value="${user.vtypes}"/></td>
				<td><sen:vtoName coding="cyckes" value="${user.tctypes}"/></td>
				<td><fmt:formatDate value='${user.starttime}' pattern='yyyy-MM-dd' /></td>
				<td><fmt:formatDate value='${user.overtime}' pattern='yyyy-MM-dd' /></td>
				<td>
					<a title="删除" target="ajaxTodo" href="${ctx }/businessinfo/delBusinessinfo/${user.id}" class="btnDel">删除</a>
					<a title="编辑" target="navTab" href="${ctx }/businessinfo/editBusinessinfo/${user.id}" class="btnEdit">编辑</a>
					<a title="历史" target="dialog" href="${ctx }/businessinfo/buyreatinfo/${user.id}" class="btnView" 
					title="套餐购买历史" mask="true" width="600" height="420" >历史</a>
				
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
