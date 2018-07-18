<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<script
    src="${ctx}/common/scripts/angularjs/1.3.16/angular.min.js"></script>
<script
    src="${ctx}/common/scripts/angularjs/1.3.16/angular-touch.min.js"></script>
<script
    src="${ctx}/common/scripts/angularjs/1.3.16/angular-animate.min.js"></script>

<script src="${ctx}/common/scripts/angularjs/tm.pagination.js"></script>

<script src="${ctx}/common/scripts/angularjs/myHttpInterceptor.js"></script>