<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<meta charset="UTF-8">

<script src="${ctx}/common/scripts/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script src="${ctx}/common/scripts/common/base.js?v=<%=System.currentTimeMillis()%>"></script>
<script src="${ctx}/common/scripts/layer/layer.js" type="text/javascript"></script>
