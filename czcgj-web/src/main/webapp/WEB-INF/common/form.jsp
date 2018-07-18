<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>

<script type="text/javascript" src="${ctx}/common/select2/js/select2.full.js"></script>
<link href="${ctx}/common/select2/css/select2.min.css" type="text/css" rel="stylesheet"/>

<link href="${ctx}/common/bootstrap/css/bootstrap.css" rel="stylesheet"/>

<link href="${ctx}/common/adminlte/dist/css/AdminLTE.min.css" rel="stylesheet"/>

<link href="${ctx}/common/super-style/form.css" type="text/css" rel="stylesheet"/>