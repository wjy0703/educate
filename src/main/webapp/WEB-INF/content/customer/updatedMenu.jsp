<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="accordion" fillSpace="sideBar">


<c:forEach items="${menus}" var = "menu">
	<div class="accordionHeader">
		<h2>
			<span>Folder</span>${menu.menuName}
		</h2>
	</div>
	<div class="accordionContent">
		<ul class="tree treeFolder">
		   <c:forEach items="${menu.subMenu}" var = "subMenu">
		   		<li><a href="${pageContext.request.contextPath}${subMenu.menuUrl}" ${subMenu.attrTarget} ${subMenu.attrRel} ${subMenu.attrTitle} ${subMenu.attrExternal} ${subMenu.attrFresh}>${subMenu.menuName}</a></li>
		   </c:forEach>
		</ul>
   </div>
</c:forEach>

</div>