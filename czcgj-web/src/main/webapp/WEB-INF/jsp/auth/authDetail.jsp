<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>帮帮</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>
</head>
<body>
<div class="submenu">
    <!--
<nav>
<ul class="title-main">
    <li class="active"><a href="javascript:void(0)">角色详情</a></li>
</ul>
</nav>
-->
    <div class="main-div" id="maindiv1" style="display:;">
        <table>
            <tbody>
            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;角色名称</label>
                </td>
                <td class="valuetd"><input type="text"  style="width: 80%;"
                                           class="form-control" name="title" id="title"
                                           readonly="readonly" value="${authority_name}"></td>
            </tr>
            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;角色功能</label>
                </td>
                <td>
                    <table class="function" style="width: 80%;">
                        <tbody>
                        <tr>
                            <th width="100" style="text-align: center">模块</th>
                            <th style="text-align: center">详细</th>
                        </tr>
                        <c:forEach items="${res_detail}" var="reslist">
                            <tr>
                                <td class="left"><input disabled="disabled"
                                        <c:if test="${reslist.is_checked}"> checked="checked" </c:if>
                                                        type="checkbox"/>${reslist.name }:
                                </td>
                                <td><c:forEach items="${reslist.children}" var="res">
                                    <div>
                                        <input type="checkbox" id="${res.int_id}" name="selects"
                                               value="${res.int_id}" disabled="disabled"
                                        <c:if test="${res.is_checked}"> checked="checked" </c:if>>${res.name}</div>
                                </c:forEach></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;角色描述</label>
                </td>
                <td class="valuetd"><textarea readonly="readonly"
                                              name="content" id="container" class="form-control"
                                              style="width: 80%; height: 80px;">${authority_description}</textarea></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>