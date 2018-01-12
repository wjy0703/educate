/* 
 * department.js 组织机构操作js
 * MDY 2013-08-07
 */
function getDataValue(type, value){
	var $res = "";
	if(type == 0){
		if(value == '0'){
			$res = "是";
		}else{
			$res = "否";
		}
	}
	if(type == 1){
		if(value == 'null'){
			$res = "无";
		}else{
			$res = value;
		}
	}
	return $res;
}

function hideOrShow(value){
	var $panelBarMdy = $("#panelBarMdy");
	if(value == '0'){
		$panelBarMdy.hide();
	}else{
		$panelBarMdy.show();
	}
}

function setSelectedOrg(data){
	hideOrShow(data.id);
	$("#organiId").val(data.id);
	$("#name").html(data.name);
	$("#organiCode").html(data.organiCode);	
	$("#organiFlag").html(getDataValue(0, data.organiFlag));	
	$("#levelMess").html(getDataValue(1, data.levelMess));
	$("#addHrefClick").attr('href',"baseinfo/addTreeDep?id="+data.id); 
	$("#editHrefClick").attr('href',"baseinfo/editTreeDep?id="+data.id); 
}

function deleteTree(){
	var $organiId = $("#organiId").val();
	if($organiId == null || $organiId == "" || $organiId == "0"){
		alertMsg.error("请选择组织机构！");
		return;
	}
	alertMsg.confirm("确定删除该组织机构！", {
		okCall: function(){
			$.ajax({
				  url: "baseinfo/delDep",
				  cache: false,
				  global: false,
				  async: false,
				  type:'post',
				  data:{id:$organiId},
				  success: function(data){
					  if(data.success){
						  alertMsg.info("组织机构删除成功！");
						  navTab.reloadFlag('rel_initTree');
					  }
				  }
				}); 
		}
	});
}

$(function(){
	var $panelBarMdy = $("#panelBarMdy");
	$panelBarMdy.hide();
	$("#addHrefClick").attr('href','baseinfo/addTreeDep?id=0'); 
});

