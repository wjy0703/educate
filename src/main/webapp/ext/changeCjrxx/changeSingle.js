	$(function(){
		$("#customerName").blur(function(self){
			var name = $("#customerName").val();
			var $selectBh = $("#tzsqbh");
			//查找编号
			$selectBh.children().remove();
			$.ajax({
			     type: 'POST',
			     url: 'changeCjrxx/getTzsqBhByCjrxxId',
			     data:{name:name},
			     success:function(data){
			    	if(!data)
			    		return ;
			    	var html ='';
			    	for(var index = 0 ; index < data.length ; index++ ){
			    		html += ("<option value='"+data[index]+"' >"+data[index]+"</option>");
			    	}
			    	var $refCombox = $selectBh.parents("div.combox:first");
			    	$selectBh.html(html).insertAfter($refCombox);
			        $refCombox.remove();
			    	$selectBh.trigger("refChange").trigger("change").combox();
			     }
			});
			
			$.ajax({
			     type: 'POST',
			     url: 'changeCjrxx/getCreateByByName',
			     data:{name:name},
			     success:function(data){
			    	var $oldCrmSelect = $("#oldCrmName");
			    	$oldCrmSelect.children().remove();
			    	if(!data)
			    		return ;
			    	for(var index = 0 ; index < data.length ; index++ ){
			    		$("<option value='"+data[index]+"' >"+data[index]+"</option>").appendTo($oldCrmSelect);
			    	}
			    	
			     }
			});
		});
	});
