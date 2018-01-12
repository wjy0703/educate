<%@ page import="java.io.PrintWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="/WEB-INF/bjdv-tags.tld" prefix="bjdv"%>
<%@ taglib uri="/WEB-INF/enumToName.tld" prefix="sen"%>


<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="fp" value="http://210.51.3.64:8888/CHPxhFile" />