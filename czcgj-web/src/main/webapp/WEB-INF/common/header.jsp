<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=edge" />
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<script type="text/javascript">
    var contextpath = "${ctx}";
</script>
<script src="${ctx}/common/scripts/jquery/1.11.1/jquery-1.11.1.min.js"></script>
<script src="${ctx}/common/scripts/common/base.js?v=<%=System.currentTimeMillis()%>"></script>
<script src="${ctx}/common/scripts/layer/layer.js" type="text/javascript"></script>
