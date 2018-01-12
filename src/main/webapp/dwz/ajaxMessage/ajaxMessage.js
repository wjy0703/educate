var size = 0;
$(function(){   
		      
			run();//加载页面时启动定时器   
			function run(){    
				interval = setInterval(getLoginMessage, 1000*300);   
		    }
});

function getLoginMessage(){
	if(isMess == true){
		$.ajax({
			  url: "message/ajaxGetLoginMessage",
			  cache: false,
			  global: false,
			  async: false,
			  success: function(data){
				if(data.success){
					var isShow = false;
					var serCount = data.count;
					if(serCount != size){
						isShow = true;
					}
					var message = fomatEachData(data.data);
					if(isShow){
						$.messager.lays(300, 150);
 						$.messager.show('通知消息 <<span style="color: red">'+serCount+ '</span>> 条', message, 0);
 						}
 					}
 				  }
 			}); 
	}
} 

function ajaxGetExpireMessage(){
	
	$.get("message/ajaxGetExpireMessage",{r:new Date().getTime()},function(data, status){
		if(status=="success"){
			var expireMessage = fomatEachDataHref(data.expireData);
			$("#indexMessage_content_01").html(expireMessage);
			$("#indexMessage_content_02").html(expireMessage);
			$("#indexMessage_content_03").html(expireMessage);
			$("#indexMessage_content_04").html(expireMessage);
			$("#indexMessage_content_05").html(expireMessage);
			$("#indexMessage_content_06").html(expireMessage);
		}
	});		
}

function fomatEachData(data){
	var html = "";
	var messSize = 0;
	$.each(data, function(i, item){
		size = i + 1;
		var readOnclick = '';
		var style = '';
		var isRead = item.ISREAD;
		if(isRead == item.TYPE){
			style = 'cursor:pointer';
			readOnclick = 'onclick="messageOpenTab(\''+item.TARGET_REL+'\', \''+item.TARGET_URL+'\', \''+item.MESSAGE_TITLE+'\', \''+item.ID+'\')"';
		}
		if(item.TYPE_NAME == '消息公告'){
			messSize = messSize + 1;
		}
		var text = '【'+item.CREATE_TIME+'】'+item.MESSAGE_CONTENT;
		html += '<li style="'+style+'" title="'+item.MESSAGE_CONTENT+'"'+readOnclick+' >'+text+'</li><div class="divider"></div>';
	});
	$("#messageSize").html("您有"+messSize+"条未读消息!");
	return html;
}

function fomatEachDataHref(data){
	var html = "";
	$.each(data, function(i, item){
		size = i + 1;
		var readOnclick = '';
		var isRead = item.ISREAD;
		if(isRead == '读写'){
			readOnclick = 'onclick="messageOpenTab(\''+item.TARGET_REL+'\', \''+item.TARGET_URL+'\', \''+item.MESSAGE_TITLE+'\', \''+item.EVENT_ID+'\')"';
		}
		var text = '【'+item.TYPE_NAME+'】【'+item.CREATE_TIME+'】'+item.MESSAGE_CONTENT;
		html += '<a href="#" title="'+item.MESSAGE_CONTENT+'"'+readOnclick+' >'+text+'...</a><div class="divider"></div>';
	});
	return html;
}
		
function messageOpenTab(tabid, url, title, evntId){
	var urlNew = url+"?id="+evntId;
	navTab.openTab(tabid, urlNew,{title:title, fresh:false, external:false});
}