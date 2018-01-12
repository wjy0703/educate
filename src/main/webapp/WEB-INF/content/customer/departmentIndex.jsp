<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${ctx}/ext/department/department.js"></script>

<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>

<div class="pageContent" style="padding:5px">
<input type="hidden" id="organiId" name="organiId"/>
	<div>

		<div layoutH="12" 	style="float:left; display:block; overflow:auto; width:260px; border:solid 1px #CCC; line-height:21px; background:#fff">
			 <ul class="tree treeFolder">
	      		<li><a href="#" onclick="setSelectedOrg({id:'0'})">组织机构</a>
	       			<ul>
						${result}
		  			</ul>
		  		</li>
		  </ul>
		</div>

		<div id="cfmdBox" class="unitBox" style="margin-left:246px;">
			
				<div class="panelBar">
					<ul class="toolBar">
						<li><a class="add" href="organize/addTreeDep?id=0" target="dialog" rel="TreeDept1" mask="true" width="400" title="添加机构"><span>添加机构</span></a></li>
					</ul>
				</div>
			

			<div class="pageContent"
				style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
				<table class="table" width="100%" nowrapTD="false">
					<thead>
						<tr>
							<th width="20px">机构名称</th>
							<th width="40px">机构编码</th>
							<th width="40px">级别描述</th>
							<th width="40px">是否在用</th>
						</tr>
					</thead>
						<tr>
							<td><div id="name">${organi.orgname }</div></td>
							<td><div id="id">${organi.id }</div></td>
							<td><div id="orgflag"><sen:vtoName coding="orgflags" value="${organi.orgflag}"/></div></td>
							<td><div id="vtypes"><sen:vtoName coding="vtypes" value="${organi.vtypes}"/></div></td>
						</tr>
				</table>
				<table class="table" width="100%" layoutH="130" nowrapTD="false">
					<thead>
						<tr>
							<th width="28" align="center">序号</th>
							<th width="80">账户</th>
							<th width="80">姓名</th>
							<th width="100">姓别</th>
							<th width="100">岗位</th>
							<th width="100">公司</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
				<div class="panelBar"></div>
			</div>
		</div>
	</div>
</div>
