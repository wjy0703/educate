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
		action="${ctx }/userinfo/listuser" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>用户名:</label> <input type="text"
					name="filter_account" value="${map.account}"/>
				</td>
				<td>
				<label>员工姓名:</label> <input type="text"
					name="filter_vname" value="${map.vname}"/>
					</td>
				<td>
				<label>状态:</label> 
				<sen:select clazz="combox required" name="filter_vtypes" id="filter_vtypes" coding="onjob" value="${map.vtypes }" title="请选择"/>
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
					<!-- <li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li> -->
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${ctx}/userinfo/adduser?sysTypeParam=${sysTypeParam}" target="navTab"><span>添加</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
				postType="string" href="${ctx }/userinfo/batchdeluser" class="delete"
				warn="请至少选择一个用户"><span>批量删除</span></a></li>
			<li><a class="edit" href="${ctx}/userinfo/edituser/{sid_user}?sysTypeParam=${sysTypeParam}"
				target="navTab" warn="请选择一个用户"><span>修改</span></a></li>
			<li class="line">line</li>
			
			<li><a target="selectedTodo" rel="ids" warn="请至少选择一个用户" postType="string" 
				 href="${ctx }/userinfo/resetPass" class="delete"
				><span>重置密码</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="140">
		<thead>
			<tr>
				<th width="28"><input type="checkbox" group="ids"
					class="checkboxCtrl"></th>
				<th width="60" >用户名</th>
				<th width="70">员工姓名</th>
				<th width="70">岗位</th>
				<th width="70">角色</th>
				<th width="30">状态</th>
				<th width="120" >当前登陆IP</th>
				<th width="70" >创建人</th>
				<th width="140">创建日期</th>
				<th width="70">修改人</th>
				<th width="140">修改日期</th>
				<th width="60">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="user" varStatus="st">
				<tr target="sid_user" rel="${user.id}">
					<td>
					<c:if test="${user.id!=0}">
					<input name="ids" value="${user.id}" type="checkbox">
					</c:if>
					</td>
					<td>${user.account }</td>
					<td>${user.vname }</td>
					<td><sen:vtoName coding="postType" value="${user.post}"/></td>
					<td>${user.roleinfo.rolename }</td>
					<td><sen:vtoName coding="onjob" value="${user.vtypes}"/></td>
					<td><a class="text-muted text-sm" href="http://www.baidu.com/s?wd=${user.nowip }" target="_blank">${user.nowip }</a>
					<sen:ipAddress value="${user.nowip }"/></td>
					<td>${user.createuser }</td>
					<td><fmt:formatDate value='${user.createtime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td>${user.modifyuser }</td>
					<td><fmt:formatDate value='${user.modifytime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td>
				   		<c:if test="${user.id!=0}">
					   		<a title="删除" target="ajaxTodo"
							href="${ctx }/userinfo/deluser/${user.id}" class="btnDel">删除</a> 
							<a title="编辑" target="navTab"
							href="${ctx }/userinfo/edituser/${user.id}" class="btnEdit">编辑</a>
				   		</c:if>
				   		<c:if test="${user.id==0 && canLook=='0'}">
							<a title="编辑" target="navTab"
							href="${ctx }/userinfo/edituser/${user.id}" class="btnEdit">编辑</a>
						</c:if>
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
					<c:if test="${page.pageSize=='10'}">selected</c:if>>10</option>
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
