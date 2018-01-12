<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><span>《${businessinfo.businame}》,套餐购买历史</span></li>
		</ul>
	</div>
	
	<table class="table" width="100%"  layoutH="75" nowrapTD="false" targetType="dialog">
		<thead>
			<tr>
				<th width="25%">购买日期</th>
				<th width="25%">操作人</th>
				<th width="80" >套餐名称</th>
				<th width="80" >周期（月）</th>
				<th width="80">套餐价格</th>
				<th width="80">支付价格</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${buyreat}" var="buy" varStatus="st">
				<tr>
					<td align="center"><fmt:formatDate value='${buy.createtime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td align="center">${buy.createuser}</td>
					<td>${buy.reatname}</td>
					<td><sen:vtoName coding="cyckes" value="${buy.cycke}"/></td>
					<td>${buy.price}</td>
					<td>${buy.paymoney}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
