
function canExport(orgid,month){
	if(!orgid || !month)
		return false;
	if(orgid == "")
		return false;
	if(month == "")
		return false;
	return true;
}
debugger;
$(function(){

	$("a.exportSalaryExcel").click(function(){
		var orgid = $("#cfmd_orgid").val();
		var month = $("input[id='cfmd_date']").val();
        if(canExport(orgid,month)){		
			var $this = $(this);
			var title = $this.attr("title");
			if (title) {
				alertMsg.confirm(title, {
					okCall: function(){
						var url = $this.attr("href");
						var fullUrl = url+"?filter_orgid="+orgid + "&filter_date="+month;
						window.location = fullUrl;
					}
				});
				return false;
			}else{
				var url = $this.attr("href");
				var fullUrl = url+"?filter_orgid="+orgid + "&filter_date="+month;
				window.location = fullUrl;
				return false;
			} 
			
        }else{
        	alertMsg.warn("查询条件不足，选择后再导出");
        	return false;
        }
	});
});

/*

debugger;
if(!$(document).attr("canExport")){
	$(document).attr("canExport",false);
}
$(function(){
	$("#enableExport").click(function(){
		$(document).attr("canExport",true);
	});
	$("select[name='filter_orgid']").change(function(){
		$(document).attr("canExport",false);
	});
	$("input[name='filter_date']").change(function(){
		$(document).attr("canExport",false);;
	});
	
	$('.exportExcel').click(function(event){
	   var $this = $(this);
	   if(!$(document).attr("canExport")){
	   		alertMsg.confirm("请选择条件,并且点击检索按钮后再导出!!!!");
	   		return false;
	   }
	   var title = $this.attr("title");
	   if (title) {
				alertMsg.confirm(title, {
					okCall: function(){_doExport($this);}
				});
		} else {_doExport($this);}
		event.preventDefault();
	});
	
	
});*/

