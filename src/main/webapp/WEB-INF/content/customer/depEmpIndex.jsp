<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${ctx}/ext/department/department.js"></script>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageContent" style="padding:0px">
<input type="hidden" id="organiId" name="organiId"/>
	<div>
		<div id="cfmdBox" class="unitBox" style="">
			
				<div class="panelBar">
					<ul class="toolBar">
						<li><a class="add" href="organize/addTreeDep?id=${organi.id }"  target="dialog" rel="TreeDept1" mask="true" width="400" title="添加机构"><span>添加机构</span></a></li>
						 <bjdv:validateContent type="1" funcId="组织机构-添加员工">
						<li><a class="add" href="${ctx}/userinfo/addTreeUser/${organi.id }" target="navTab" rel="rel_addTreeUser" mask="true" width="400" title="添加员工"><span>添加员工</span></a></li>
						</bjdv:validateContent>
						<li><a class="edit" href="organize/editTreeDep?id=${organi.id }" target="dialog" mask="true" rel="TreeDept1" width="400" title="调整机构"><span>调整机构</span></a></li>
						<li><a class="delete" href="#" onclick="deleteTree(${organi.id })"><span>删除</span></a></li>
						<li><a title="确定这些员工要离职吗?" target="selectedTodo" rel="ids" warn="请至少选择一个员工"
								 href="${ctx }/userinfo/deparmentLeavePost" class="delete" postType="string"><span>离职</span></a></li>
					</ul>
				</div>
			

			<div class="pageContent"
				style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
				<table class="table" width="100%" nowrapTD="false">
					<thead>
						<tr>
							<th width="20px">机构名称</th>
							<th width="40px">所属公司</th>
							<th width="40px">级别描述</th>
							<th width="40px">是否在用</th>
						</tr>
					</thead>
						<tr>
							<td><div id="name">${organi.orgname }</div></td>
							<td><div id="businame">${organi.businessinfo.businame }</div></td>
							<td><div id="orgflag"><sen:vtoName coding="orgflags" value="${organi.orgflag}"/></div></td>
							<td><div id="vtypes"><sen:vtoName coding="vtypes" value="${organi.vtypes}"/></div></td>
						</tr>
				</table>
				<table class="table" width="100%" layoutH="130" nowrapTD="false">
					<thead>
						<tr>
							<th width="28"><input type="checkbox" group="ids"
								class="checkboxCtrl"></th>
							<th width="28" align="center">序号</th>
							<th width="80">账户</th>
							<th width="80">姓名</th>
							<th width="100">姓别</th>
							<th width="100">岗位</th>
							<th width="100">公司</th>
							<th width="100">状态</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${listUser}" var="user" varStatus="st">
						<tr>
							<td><input name="ids" value="${user.id}" type="checkbox"></td>
							<td>${st.index+1 }</td>
							<td>${user.account }</td>
							<td>${user.vname }</td>
							<td><sen:vtoName coding="sexType" value="${user.sex}"/></td>
							<td><sen:vtoName coding="postType" value="${user.post}"/></td>
							<td>${user.businessinfo.businame }</td>
							<td><sen:vtoName coding="onjob" value="${user.vtypes}"/></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div class="panelBar"></div>
			</div>
		</div>
	</div>
</div>