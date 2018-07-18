<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<script src="${ctx}/common/scripts/angularjs/ng-table/ng-table.min.js"></script>

<link href="${ctx}/common/style/angularjs/ng-table/ng-table.min.css" rel="stylesheet" type="text/css"/>