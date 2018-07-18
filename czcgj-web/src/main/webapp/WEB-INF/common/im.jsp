<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />

<!--sdk-->
<script src="${ctx}/module/IM/sdk/strophe.js"></script>
<script src="${ctx}/module/IM/sdk/easemob.im-1.1.js"></script>
<script src="${ctx}/module/IM/sdk/easemob.im-1.1.shim.js"></script><!--兼容老版本sdk需引入此文件-->

<!--config-->
<script src="${ctx}/module/IM/js/easemob.im.config.js"></script>