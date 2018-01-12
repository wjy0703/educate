<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${ctx}/ext/department/department.js"></script>

<style type="text/css">
ul.rightTools {
	float: right;
	display: block;
}

ul.rightTools li {
	float: left;
	display: block;
	margin-left: 5px
}
</style>

<div class="pageContent" style="padding: 5px">
	<input type="hidden" id="parentId" name="parent.id"/>
	<div>

		<div layoutH="12"
			style="float: left; display: block; overflow: auto; width: 260px; border: solid 1px #CCC; line-height: 21px; background: #fff">
			<ul class="tree treeFolder">
				<li><a href="#" onclick="setSelectedOrg({id:'0'})">城市管理</a>
					<ul>${result}
					</ul></li>
			</ul>
		</div>

		<div id="cfmdBox" class="unitBox" style="margin-left: 246px;">

			<div class="panelBar">
				<ul class="toolBar">
					<!--  <li><a class="add" href="baseinfo/addTreeDep?id=0" target="dialog" mask="true" width="400" title="添加机构"><span>添加</span></a></li> -->
				</ul>
			</div>


			<div class="pageContent"
				style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
				<table class="table" width="100%" nowrapTD="false">
					<thead>
						<tr>
							<th width="20px">名称</th>
							<th width="40px">城市编码</th>
							<th width="40px">简称</th>
							<th width="40px">是否在用</th>
						</tr>
					</thead>
					<tr>
						<td>&nbsp;</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</table>
				<table class="table" width="100%" layoutH="130" nowrapTD="false">
					<thead>
						<tr>
							<th width="28" align="center">序号</th>
							<th width="80">城市名称</th>
							<th width="80">城市编码</th>
							<th width="100">城市全拼</th>
							<th width="100">是否在用</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
							<tr>
								<td>&nbsp;</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
					</tbody>
				</table>
				<div class="panelBar"></div>
			</div>
		</div>
	</div>
</div>
