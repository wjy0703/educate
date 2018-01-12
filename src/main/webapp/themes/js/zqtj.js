//债权推荐选择带回，勿动-------------开始
	function DeleteMateRow(id) {
		var trId = document.getElementById('acc'+id);
		var oRow = event.srcElement.parentNode.parentNode;
		var oTable = oRow.parentNode.parentNode;
		if (oTable.rows.length >= 1) {
			oTable.deleteRow(trId.rowIndex);
		}
		var mateId = document.getElementById("mateId").value;
		mateId = mateId.replace(id+",",''); 
		document.getElementById("mateId").value = mateId;
		httpLocation();
	}
	var dataList = new Array(); //定义一数组
	//债权推荐选择带回，勿动
	function onCliMs(om){
		//alertMsg.info("om.length=="+om.length);
		
		var json = "";
		/*
		for(var i=0;i<om.length;i++){
	        if(om[i].checked){
	        	if(om[i].value != null && om[i].value != ""){
	        	json = json + om[i].value + ",";
	        	}
	        }
	    }
		*/
		for(var a = 0; a < dataList.length ; a ++){
			if(dataList[a] != ""){
				json = json + dataList[a] + ",";
			}
		}
		dataList = new Array();
		json = json.substring(0,json.length-1);
		fomatJson(json);
	}
	
	Array.prototype.remove=function(dx) 
	{ 
	    if(isNaN(dx)||dx>this.length){return false;} 
	    for(var i=0,n=0;i<this.length;i++) 
	    { 
	        if(this[i]!=this[dx]) 
	        { 
	            this[n++]=this[i] ;
	        } 
	    } 
	    this.length-=1 ;
	} ;
	function resetValue(om){
		for(var i=0;i<om.length;i++){
			for(var a = 0; a < dataList.length ; a ++){
				if(om[i].value == dataList[a]){
					var value = om[i].value.split("|");
					$("#orgId"+value[0]).attr('checked', true);
				}
			}
	    }
	}
	function dataClick(obj){
		//var leng = 0;
		if(obj.checked){
			dataList.push(obj.value);
		}else{
			//alert("nocheck==>" + obj.value);
			for(var a = 0; a < dataList.length ; a ++){
				if(obj.value == dataList[a]){
					dataList.remove(a);
					break;
				}
			}
			//alert(dataList.length);
		}
	}
	//债权推荐选择带回，勿动
	function fomatJson(json){
		var html = $("#msList").html();
		var html1 = "";
		html += fomatStartHtml();
		var strs= new Array(); //定义一数组
		var strs1= new Array(); //定义一数组
		//var strs2= new Array(); //定义一数组
		strs = json.split(","); //字符分割     
		var matid = $("#mateId").val();
		if(strs != ""){
			for (var i=0;i<strs.length ;i++ ){   
		        strs1 = strs[i].split("|");
		        //var total = 0;
	        	html1 += "<tr id=\"acc"+strs1[0]+"\">";
				html1 += "<td width=\"40px\" align=\"center\"><a href=\"#\" onClick=\"DeleteMateRow("+strs1[0]+")\">移除</a></td>";
				html1 += "<td width=\"100px\">"+strs1[1]+"</td>";
				html1 += "<td width=\"60px\">"+strs1[2]+"</td>";
				html1 += "<td width=\"80px\">"+strs1[8]+"</td>";
				html1 += "<td width=\"80px\">"+strs1[3]+"</td>";
				html1 += "<td width=\"40px\">"+strs1[9]+"</td>";
				html1 += "<td width=\"80px\"><input name=\"qishuOld"+strs1[0]+"\" id=\"qishuOld"+strs1[0]+"\" type=\"hidden\" value=\""+strs1[4]+"\"/>"+strs1[4]+"</td>";
				//html1 += "<td width=\"80\"><input name=\"qishu"+strs1[0]+"\" type=\"text\" size=\"5\" value=\"1\" alt=\"请输入期数\" class=\"required\" onblur=\"totalQS("+strs1[0]+")\"/></td>";
				//html1 += "<td width=\"10%\">"+strs1[5]+"</td>";
				html1 += "<td width=\"120px\">"+strs1[6]+"</td>";
				html1 += "<td width=\"120px\"><input name=\"numOld"+strs1[0]+"\" id=\"numOld"+strs1[0]+"\" type=\"hidden\" value=\""+strs1[7]+"\"/>"+strs1[7]+"</td>";
				html1 += "<td width=\"120px\"><input name=\"num"+strs1[0]+"\" id=\"num"+strs1[0]+"\" type=\"text\" size=\"10\" value=\"1\" alt=\"请输入金额\" class=\"required\" onblur=\"total("+strs1[0]+")\"/></td>";
		        html1 += "</tr>";
		        matid += strs1[0]+",";
			}
		}
		//document.getElementById("mateId").value = document.getElementById("mateId").value.substring(0,document.getElementById("mateId").value.length-1);
		$("#mateId").val(matid);
		html += html1;
		html += fomatEndHtml();
		$("#msList").html(html);
		httpLocation();
	}
	//债权推荐选择带回，勿动
	function setMateIdOld(){
		$("#mateId").val("");
	}
	//债权推荐选择带回，勿动
	function fomatStartHtml(){
		var html = "";
		html += "<table class=\"table\" targetType=\"dialog\" width=\"100%\">";
		/*
		html += "<thead>";
		html += "<tr>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\" align=\"center\">操作</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">借款人姓名</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">还款方式</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">申请日期</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">剩余还款期数</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">还款期数</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">借款用途</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">原始债权价值</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">可推荐债权价值</th>";
		html += "<th width=\"10%\" height=\"30\" bgcolor=\"#EDF1F6\">推荐额度</th>";
		html += "</tr>";
		html += "</thead>";
		*/
		html += "<tbody>";
		return html;
	}
	//债权推荐选择带回，勿动
	function fomatEndHtml(){
		var html = "";
		html += "</tbody>";
		html += "</table>";
		return html;
	}
	//债权推荐选择带回，勿动
	function totalQS(totalId){
		var qishuOld = $("#qishuOld"+totalId).val();
		var qishu = $("#qishu"+totalId).val();
		if(parseInt(qishu)>parseInt(qishuOld)){
			alertMsg.error("还款期数不能大于剩余还款期数");
			$("#qishu"+totalId).val(qishuOld);
		}
		var LAST_CJZQ = $("#LAST_CJZQ").val();
		if(LAST_CJZQ != "-1"){
			qishu = $("#qishu"+totalId).val();
			if(parseInt(LAST_CJZQ) < parseInt(qishu)){
				alertMsg.error("还款期数不能大于可推荐期数");
				$("#qishu"+totalId).val(LAST_CJZQ);
			}
		}
	}
	//债权推荐选择带回，勿动
	function total(totalId){
		var MONEY = $("#MONEY").val();
		var numOld = $("#numOld"+totalId).val();
		var num = $("#num"+totalId).val();
		if(parseFloat(num)>parseFloat(numOld)){
			alertMsg.error("推荐额度不能大于可推荐债权价值");
			$("#num"+totalId).val(numOld);
		}
		var mateId = $("#mateId").val();
		var strs = new Array(); //定义一数组
		var qishuNum= 0.0; 
		//var strs2= new Array(); //定义一数组
		strs = mateId.split(","); //字符分割    
		if(strs != ""){
			for (var i=0;i<strs.length ;i++ ){  
				if(strs[i] != null && strs[i] != ""){
					//alert(Number(document.getElementById("num"+strs[i]).value));
					qishuNum = qishuNum + Number($("#num"+strs[i]).val())*100000;
					//alert(qishuNum);
				}
			}
		}
		//alert("parseFloat(MONEY)=="+parseFloat(MONEY)+";qishuNum===>"+qishuNum/100000);
		num = document.getElementById("num"+totalId).value;
		if(parseFloat(MONEY) < qishuNum/100000){
			alertMsg.error("推荐额度总和不能大于未推荐金额");
			$("#num"+totalId).val((Number(num)*100000-(qishuNum - Number(MONEY)*100000))/100000);
		}
		/*
		if(parseFloat(MONEY) > qishuNum){
			if(parseFloat(MONEY) < parseFloat(numOld)){
				alertMsg.error("推荐额度总和必须等于未推荐金额");
				document.getElementById("num"+totalId).value = parseFloat(num)+(parseFloat(MONEY) - qishuNum);
			}
		}
		*/
	}
	//债权推荐选择带回，勿动
	function httpLocation(){
		var mateId = $("#mateId").val();
		var ZDR = $("#ZDR").val();
		var MONEY = $("#MONEY").val();
		var TZCP_ID = $("#TZCP_ID").val();
		var LAST_CJZQ = $("#LAST_CJZQ").val();
		var TZSQ_ID = $("#TZSQ_ID").val();
		$("#http").attr("href","jygl/findZqtj?TZSQ_ID="+TZSQ_ID+"&MONEY="+MONEY+"&TZCP_ID="+TZCP_ID+"&LAST_CJZQ="+LAST_CJZQ+"&ZDR="+ZDR+"&mateid="+mateId);
	}
	//债权推荐选择带回，勿动-------------结束
	function tijiao(t){
		var isSub = $("#isSub").val();
		if(isSub == "0"){
			$("#state").val("1");
			var MONEY = $("#MONEY").val();
			
			var mateId = $("#mateId").val();
			var strs = new Array(); //定义一数组
			var qishuNum= 0.0; 
			//var strs2= new Array(); //定义一数组
			strs = mateId.split(","); //字符分割    
			if(strs != ""){
				for (var i=0;i<strs.length ;i++ ){  
					if(strs[i] != null && strs[i] != ""){
						//alert("Num====>"+$("#num"+strs[i]).value);
						qishuNum = qishuNum + Number($("#num"+strs[i]).val())*100000;
						//alert(";qishuNum====>"+qishuNum);
					}
				}
			}
			//alert("MONEY===>" + parseFloat(MONEY) + ";qishuNum====>"+qishuNum);
			if(parseFloat(MONEY) != qishuNum/100000){
				alertMsg.error("推荐额度总和必须等于未推荐金额");
				return false;
			}else{
				$("#isSub").val("1");
				return true;
			}
		}else{
			t.disabled = "disabled";
		}
	}
	function baocun(t){
		var isSub = $("#isSub").val();
		if(isSub == "0"){
			$("#state").val("0");
			var MONEY = $("#MONEY").val();
			
			var mateId = $("#mateId").val();
			var strs = new Array(); //定义一数组
			var qishuNum= 0.0; 
			//var strs2= new Array(); //定义一数组
			strs = mateId.split(","); //字符分割    
			if(strs != ""){
				for (var i=0;i<strs.length ;i++ ){  
					if(strs[i] != null && strs[i] != ""){
						//qishuNum += Number($("#num"+strs[i]).value);
						qishuNum = qishuNum + Number($("#num"+strs[i]).val())*100000;
					}
				}
			}
			if(parseFloat(MONEY) < qishuNum/100000){
				alertMsg.error("推荐额度总和不能大于未推荐金额");
				return false;
			}else{
				$("#isSub").val("1");
				return true;
			}
		}else{
			t.disabled = "disabled";
		}
	}