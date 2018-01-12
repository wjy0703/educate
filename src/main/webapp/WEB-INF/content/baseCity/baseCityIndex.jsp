<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
function deleteTree(cityId){
	if(cityId == null || cityId == "" || cityId == "0"){
		alertMsg.error("请选择城市！");
		return;
	}
	alertMsg.confirm("确定删除该城市！", {
		okCall: function(){
			$.ajax({
				  url: "cityinfo/delDep",
				  cache: false,
				  global: false,
				  async: false,
				  type:'post',
				  data:{id:cityId},
				  success: function(data){
					  if(data.success){
						  alertMsg.info("城市删除成功！");
						  navTab.reloadFlag('rel_listBaseCity');
					  }
				  }
				}); 
		}
	});
}
</script>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageContent" style="padding:0px">
<input type="hidden" id="parentId" name="parent.id"/>
	<div>
		<div id="cfmdBox" class="unitBox" style="">
			
				<div class="panelBar">
					<ul class="toolBar">
						<li><a class="add" href="cityinfo/addTreeDep?id=${baseCity.id }" target="dialog" mask="true" width="400" title="添加"><span>添加</span></a></li>
						<li><a class="edit" href="cityinfo/editTreeDep?id=${baseCity.id }" target="dialog" mask="true" width="400" title="修改"><span>修改</span></a></li>
					</ul>
				</div>
			<div class="pageContent"
				style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
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
							<td><div id="name">${baseCity.vname }</div></td>
							<td><div id="organiCode">${baseCity.coding }</div></td>
							<td><div id="levelMess">${baseCity.pinyin }</div></td>
							<td><div id="organiFlag">
							<sen:vtoName coding="vtypes" value="${baseCity.vtypes}"/>
							</div></td>
						</tr>
				</table>
				<table class="table" width="100%" layoutH="130" nowrapTD="false">
					<thead>
						<tr>
							<th width="28" align="center">序号</th>
							<th width="80">城市名称</th>
							<th width="80">城市编码</th>
							<th width="100">城市全拼</th>
							<th width="100">地区名称</th>
							<th width="100">是否在用</th>
							<th width="100">操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${listEmp}" var="baseCity" varStatus="st">
						<tr>
							<td>${st.index+1 }</td>
							<td>${baseCity.vname }</td>
							<td>${baseCity.coding  }</td>
							<td>${baseCity.pinyin} </td>
							<td>${baseCity.cname} </td>
							<td><sen:vtoName coding="vtypes" value="${baseCity.vtypes}"/></td>
							<td><a class="btnEdit" href="cityinfo/editTreeDep?id=${baseCity.id }" target="dialog" mask="true" width="400" title="修改县区">修改</a>
								<a class="btnDel" href="#" onclick="deleteTree(${baseCity.id })"><span>删除</span></a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div class="panelBar"></div>
			</div>
		</div>
	</div>
</div>
