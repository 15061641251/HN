<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="authAddApp">
<head>

    <title>帮帮</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>

</head>
<body ng-controller="authAddController">
<div class="submenu">
    <!--
    <nav>
    <ul class="title-main">
        <li class="active"><a href="javascript:void(0)">新增角色</a></li>
    </ul>
    </nav>
     -->
    <div class="main-div" id="maindiv1">
        <table>
            <tbody>
            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color: red;">*</span>角色名称
                </label></td>
                <td class="valuetd"><input type="text" style="width: 80%;"
                                           class="form-control" name="title" id="title"
                                           ng-model="name" ng-cloak></td>
            </tr>
            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color: red;">*</span>角色功能
                </label></td>
                <td>
                    <table class="function" style="width: 80%;">
                        <tbody>
                        <tr>
                            <th width="100" style="text-align: center">模块</th>
                            <th style="text-align: center">详细</th>
                        </tr>

                        <tr ng-repeat="reslist in resources">
                            <td class="left" ng-cloak><label><input
                                    ng-model="reslist.is_checked"
                                    ng-change="reslistCheck(reslist)" type="checkbox"
                                    id="{{res.int_id}}" name="selects" value={{res.int_id}}/>&nbsp;{{reslist.name
                                }}:</label></td>
                            <td>
                                <div ng-repeat="res in reslist.children" ng-cloak>
                                    <label><input ng-model="res.is_checked"
                                                  ng-change="resCheck(res,reslist)" type="checkbox"
                                                  id="{{res.int_id}}" name="selects" value={{res.int_id}}/>&nbsp;{{res.name}}</label>&nbsp;&nbsp;
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr class="valuetr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;角色描述</label>
                </td>
                <td class="valuetd"><textarea name="content" id="container" class="form-control"
                                              style="width: 80%; height: 80px;" ng-model="description"
                                              ng-cloak></textarea></td>
            </tr>
            </tbody>
        </table>
        <div style="text-align: center; margin-top: 20px;">
            <a class="btn btn-primary" ng-click="addAuth()">
                &nbsp;新增</a>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="${ctx }/common/scripts/angularjs/underscore.js"></script>
<script type="text/javascript"
        src="${ctx }/module/auth/scripts/authAdd.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript"
        src="${ctx }/common/scripts/angularjs/angularFormPost.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>