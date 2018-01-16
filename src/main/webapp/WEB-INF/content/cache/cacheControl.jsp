<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
  $(function(){
     $('button.cache').click(function(){
     		var $this = $(this);
     	    var cacheRelease = $this.attr('data');
     	    var textMsg = $this.html();
     	    alertMsg.confirm("确认清除"+textMsg+"缓存么？", {
				okCall: function(){ 
				     $.ajax({
						  url: "${ctx}/menus/invalidate/"+cacheRelease,
						  cache: false,
						  global: false,
						  async: true,
						  success: function(data){
						     
						     if(data == 'success'){
						     	alertMsg.correct("清楚"+textMsg+"缓存成功");
						     }
						  }
					  });
		        }
		   });
		   return false;
     });
  });
</script>
<div class="panel">
	<!-- <h1>新增客户咨询</h1> -->
	<div class="pageFormContent">
			<div class="pageFormContent" layoutH="56">
				<input type="hidden" name="jkhtId" value="${jkhtId}" />
				<input type="hidden" name="jksqId" value="${jksqId}" />
				<table width="100%">

					<tr>
						<td> 
						   <button class = "cache" href="#" data="menu">菜单</button>
						</td>
						<td> 
						   <button class = "cache" href="#" data="enums">枚举类</button>
						</td>
					</tr>
					<tr>
						<td> &nbsp;
						</td>
						<td> 
						</td>
					</tr>
					<tr>
						<td> 
						   <button class = "cache" href="#" data="user">员工</button>
						</td>
						<td> 
						   <button class = "cache" href="#" data="address">省份(城市)</button>
						</td>
					</tr>
					<tr>
						<td> &nbsp;
						</td>
						<td> 
						</td>
					</tr>
					<tr>
					<td> 
						</td>
						<td> 
						   <button class = "cache" href="#" data="all">所有</button>
						</td>
					</tr>
					
					
				</table>
			
			</div>
	</div>

</div>
