<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div class="pageHeader">
		<input type="hidden" id="result" name="result" value="${menuStr}" />
		<div class="searchBar">
			<div class="subBar">
				<ul>
					<li id="buttonView" style="display:none" ><div class="button">
							<div class="buttonContent">
								<button type="button" multLookup="orgId" warn="请选择一个菜单" onclick="aaa()">选择带回</button>
							</div>
						</div></li>
						<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">返回</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
</div>
<div class="pageContent">
<div style=" float:left; display:block; width:90%; height:320px;  background:#FFF;">
<ul class="tree treeFolder treeCheck expand" oncheck="kkk">
<li><a tname="全部" tvalue="all">全部</a>
<ul>
	${result1}
</ul>
</li>
</ul>
</div>
<div id="divCheck"></div>
</div>
<script type="text/javascript">
//var result="";
function kkk(){
	var json = arguments[0], che = '';
	var check = json.checked;
	var result = document.getElementById("result").value;
	$(json.items).each(function(i){
		if(this.value != "all"){
			che = "{id:'"+this.value+"', name:'"+this.name+"'}|";
			if(check){
				if(result.indexOf(che) == -1){
					result += che;
				}
			}else{
				result = result.replace(che,''); 
			}
		}
	});
	document.getElementById("result").value = result;
	document.getElementById("buttonView").style.display = "block";
}
function aaa(){
	var checkList = new Array(); //定义一数组
	var result = document.getElementById("result").value;
	result = result.substring(0,result.length-1);//去除最后竖线
	checkList = result.split("|"); //字符分割    
	if(checkList != ""){
		for (var i=0;i<checkList.length ;i++ ){
			if(checkList[i] != ""){
				var checkBox=document.createElement("input");
				checkBox.setAttribute("type","checkbox");
				checkBox.setAttribute("name","orgId");
				checkBox.setAttribute("value",checkList[i]);
				document.getElementById('divCheck').appendChild(checkBox); 
				checkBox.setAttribute("checked",true);
			}
		}
	}
}
</script>