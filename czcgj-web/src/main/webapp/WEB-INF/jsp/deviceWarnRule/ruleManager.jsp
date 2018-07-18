<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="deviceWarnRuleApp">
<head>
    <base href="/"/>
    <title>设备规则设置</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/bootstrap-table.jsp"/>

    <jsp:include page="../../common/form.jsp"/>

    <style type="text/css">
        tr > th{
            font-size: 15px;
            font-weight: normal;
        }
    </style>
</head>

<body ng-controller="deviceWarnRuleAppController">
<div>
    <div class="main-div">
        <div class="center-Panel" style="overflow-x: hidden">
            <div class="titlePanel">
                <div class="toolbar">
                    <div class="btn-group">
                        <a class="btn btn-default lr-add" ng-click="addRule()"><i class="fa fa-plus"></i>&nbsp;新增</a>
                    </div>
                </div>
            </div>
            <div class="bootstrap-table">
                <div class="fixed-table-container" style="padding-bottom: 0px;">
                    <div class="fixed-table-body">
                        <table id="table" class="table table-hover table-striped JColResizer">
                            <thead>
                            <tr>
                                <th style="text-align: center; width: 50px; ">序号</th>
                                <th style="text-align: center; width: 50px; ">数据流Key</th>
                                <th style="text-align: center; width: 50px; ">比对条件</th>
                                <th style="text-align: center; width: 50px; ">数据值</th>
                                <th style="text-align: center; width: 50px; ">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="rule in rules">
                                <td style="text-align: center; " ng-cloak>{{$index + 1}}</td>
                                <td style="text-align: center; " ng-cloak>
                                    <input ng-model="rule.data_key" type="text"/>
                                    <span style="color:red">*</span></td>
                                <td style="text-align: center; " ng-cloak>
                                    <select ng-model="rule.show_type">
                                        <option value="{{type}}" ng-repeat="type in show_types"
                                                ng-selected="type == rule.show_type">{{type}}
                                        </option>
                                    </select>
                                    <span style="color:red">*</span>
                                </td>
                                <td style="text-align: center; " ng-cloak><input ng-model="rule.data_value"
                                                                                 type="text"/><span
                                        style="color:red">*</span>
                                </td>
                                <td style="text-align: center; " ng-cloak><a ng-click="delRule(rule)">删除</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <br>
            <center>
                <input type="button" class="btn btn-large btn-primary" value="保  存"
                       ng-click="saveRule()"/>
            </center>
        </div>
    </div>

</div>
<script src="${ctx}/module/deviceWarnRule/scripts/deviceWarnRule.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
